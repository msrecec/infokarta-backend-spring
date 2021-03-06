/**
 * Copyright 2016, GeoSolutions Sas.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

import DebugUtils from '../utils/DebugUtils';
import { combineEpics, combineReducers } from '../utils/PluginsUtils';
import { createEpicMiddleware } from 'redux-observable';
import ListenerEnhancer from '@carnesen/redux-add-action-listener-enhancer';
import { routerMiddleware, connectRouter } from 'connected-react-router';
import { persistMiddleware, persistEpic } from '../utils/StateUtils';
import localConfig from '../reducers/localConfig';
import locale from '../reducers/locale';
import browser from '../reducers/browser';

const standardEpics = {};

const appStore = (
    {
        initialState = {
            defaultState: {},
            mobile: {}
        },
        appReducers = {},
        appEpics = {},
        rootReducerFunc = ({ state, action, allReducers }) => allReducers(state, action)
    },
    plugins = {},
    storeOpts = {}
) => {

    const history = storeOpts.noRouter ? null : require('./History').default;
    const allReducers = combineReducers(plugins, {
        ...appReducers,
        localConfig,
        locale,
        locales: () => null,
        browser,
        // deceased: require('../reducers/infokarta/deceased'),
        // dynamicModalControl: require('../reducers/infokarta/dynamicModalControl'),
        // dynamicModalControl: require('../reducers/infokarta/dynamicModalControl'),
        // TODO: missing locale default reducer
        ...(!storeOpts.noRouter && { router: connectRouter(history) })
    });
    const rootEpic = persistEpic(combineEpics(plugins, { ...standardEpics, ...appEpics }));
    const optsState = storeOpts.initialState || { defaultState: {}, mobile: {} };
    const defaultState = { ...initialState.defaultState, ...optsState.defaultState };
    const mobileOverride = { ...initialState.mobile, ...optsState.mobile };
    const epicMiddleware = persistMiddleware(createEpicMiddleware(rootEpic));
    const rootReducer = (state, action) => {
        return rootReducerFunc({
            state,
            action,
            allReducers,
            mobileOverride
        });
    };
    let store;
    let enhancer;
    if (storeOpts && storeOpts.notify !== false) {
        enhancer = ListenerEnhancer;
    }
    if (storeOpts && storeOpts.persist) {
        storeOpts.persist.whitelist.forEach((fragment) => {
            const fragmentState = localStorage.getItem('mapstore2.persist.' + fragment);
            if (fragmentState) {
                defaultState[fragment] = JSON.parse(fragmentState);
            }
        });
        if (storeOpts.onPersist) {
            setTimeout(() => { storeOpts.onPersist(); }, 0);
        }
    }

    let middlewares = [epicMiddleware];
    if (!storeOpts.noRouter) {
        // Build the middleware for intercepting and dispatching navigation actions
        const reduxRouterMiddleware = routerMiddleware(history);
        middlewares = [...middlewares, reduxRouterMiddleware];
    }

    store = DebugUtils.createDebugStore(rootReducer, defaultState, middlewares, enhancer);
    if (storeOpts && storeOpts.persist) {
        const persisted = {};
        store.subscribe(() => {
            storeOpts.persist.whitelist.forEach((fragment) => {
                const fragmentState = store.getState()[fragment];
                if (fragmentState && persisted[fragment] !== fragmentState) {
                    persisted[fragment] = fragmentState;
                    localStorage.setItem('mapstore2.persist.' + fragment, JSON.stringify(fragmentState));
                }
            });
        });
    }
    return store;
};

export default appStore;
