import Rx from "rxjs";

import {
    clearDetailsAndDocsView
} from "../../actions/infokarta/detailsAndDocuments";

import { SET_CONTROL_PROPERTY, TOGGLE_CONTROL } from '../../actions/controls';

export const clearDetailsAndDocsViewOnPluginToggle = (action$) =>
    action$.ofType(SET_CONTROL_PROPERTY, TOGGLE_CONTROL)
        .switchMap(() => {
            return Rx.Observable.of(
                clearDetailsAndDocsView()
            );
        });
