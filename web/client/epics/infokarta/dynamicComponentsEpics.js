import Rx from "rxjs";
import { get } from 'lodash';

import {
    GET_COLUMNS_FOR_INSERT_FROM_DATABASE,
    GET_ITEM_FOR_EDIT_FROM_DATABASE,
    SAVE_EDITED_ITEM,
    clearDynamicComponentStore,
    insertSuccessful,
    insertUnsuccessful,
    showDynamicModal,
    hideDynamicModal,
    alternateModalVisibility,
    acquireCurrentPluginName,
    clearActivePlugin
} from "../../actions/infokarta/dynamicComponents";

import {
    SET_CONTROL_PROPERTY,
    TOGGLE_CONTROL
} from "../../actions/controls";

import dynamicComponentsApi from "../../api/infokarta/dynamicComponentsApi";

export const fetchEditDataAndSendToModal = (action$, {getState = () => {}} = {}) =>
    action$.ofType(GET_ITEM_FOR_EDIT_FROM_DATABASE)
        .switchMap(({ fid = {} }) => {
            const activePlugin = get(getState(), "dynamicComponents.activePlugin");
            return Rx.Observable.fromPromise(dynamicComponentsApi.getItem(activePlugin, fid)
                .then(data => data))
                .switchMap((response) => {
                    console.log('!!!', response);
                    return Rx.Observable.of(
                        showDynamicModal("Edit", response)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('Error while getting data for edit:', error)
                    );
                });
        });

export const fetchColumnsFromDeceasedForInsert = (action$, {getState = () => {}} = {}) =>
    action$.ofType(GET_COLUMNS_FOR_INSERT_FROM_DATABASE)
        .switchMap(({}) => {
            const activePlugin = get(getState(), "dynamicComponents.activePlugin");
            return Rx.Observable.fromPromise(dynamicComponentsApi.getColumns(activePlugin)
                .then(data => data))
                .switchMap((columns) => {
                    console.log('!!!', columns);
                    return Rx.Observable.of(
                        showDynamicModal("Insert", columns)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('Error while getting columns for insert:', error)
                    );
                });
        });

export const saveEditedItemToDatabase = (action$, {getState = () => {}} = {}) =>
    action$.ofType(SAVE_EDITED_ITEM)
        .switchMap(({ item = {} }) => {
            const activePlugin = get(getState(), "dynamicComponents.activePlugin");
            return Rx.Observable.fromPromise(dynamicComponentsApi.saveEdit(activePlugin, item)
                .then(data => data))
                .mergeMap((response) => {
                    console.log('!!!', response);
                    return Rx.Observable.of(
                        clearDynamicComponentStore(),
                        insertSuccessful("Unos potvrđen", "Vaša izmjena je uspješno pohranjena u bazu podataka.")
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('Error while getting columns for insert:', error)
                    );
                });
        });

export const clearActivePluginOnDrawerMenuClose = (action$) =>
    action$.ofType(TOGGLE_CONTROL)
        .switchMap(({}) => {
            return Rx.Observable.of(
                clearActivePlugin()
            );
        });

// export const clearActivePluginOnDrawerMenuClose = (action$) =>
//     action$.ofType(TOGGLE_CONTROL)
//         .switchMap(({}) => {
//             return Rx.Observable.of(
//                 clearActivePlugin()
//             );
//         });

// export const insertNewDeceased = (action$, {getState = () => {}} = {}) =>
//     action$.ofType(INSERT_DECEASED)
//         .switchMap(({ itemToInsert = {} }) => {
//             let gravePickerToolStore = get(getState(), "gravePickerTool");
//             let temp = gravePickerToolStore.chosenGrave;
//             return Rx.Observable.fromPromise(pokojniciApi.insertPokojnik(itemToInsert, temp)
//                 .then(data => data))
//                 .mergeMap((response) => {
//                     if (response.status === 200) {
//                         console.log('insert response: ', response.data);
//                         return Rx.Observable.of(
//                             sendSearchRequestForDeceased(),
//                             clearDynamicComponentStore(),
//                             clearGravePickerToolStore(),
//                             insertSuccessful("Unos potvrđen", "Nova stavka je uspješno pohranjena u bazu podataka.")
//                         );
//                     }
//                     return Rx.Observable.of(
//                         alternateModalVisibility(insertConfirmationModalName, insertModalName),
//                         insertUnsuccessful("Došlo je do greške", "Nova stavka nije pohranjena u bazu. Error: " + response.status)
//                     );
//                 })
//                 .catch((error) => {
//                     return Rx.Observable.of(
//                     /* eslint-disable no-console */
//                         console.error('error while inserting new deceased', error)
//                     );
//                 });
//         });

