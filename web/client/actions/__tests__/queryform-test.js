/**
 * Copyright 2015, GeoSolutions Sas.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

const expect = require('expect');
const {
    ADD_FILTER_FIELD,
    ADD_GROUP_FIELD,
    REMOVE_FILTER_FIELD,
    UPDATE_FILTER_FIELD,
    UPDATE_EXCEPTION_FIELD,
    UPDATE_LOGIC_COMBO,
    REMOVE_GROUP_FIELD,
    CHANGE_CASCADING_VALUE,
    EXPAND_ATTRIBUTE_PANEL,
    EXPAND_SPATIAL_PANEL,
    addFilterField,
    addGroupField,
    removeFilterField,
    updateFilterField,
    updateExceptionField,
    updateLogicCombo,
    removeGroupField,
    changeCascadingValue,
    expandAttributeFilterPanel,
    expandSpatialFilterPanel
} = require('../queryform');

describe('Test correctness of the queryform actions', () => {

    it('addFilterField', () => {
        let groupId = 1;

        var retval = addFilterField(groupId);

        expect(retval).toExist();
        expect(retval.type).toBe(ADD_FILTER_FIELD);
        expect(retval.groupId).toBe(1);
    });

    it('addGroupField', () => {
        let groupId = 1;
        let index = 0;

        var retval = addGroupField(groupId, index);

        expect(retval).toExist();
        expect(retval.type).toBe(ADD_GROUP_FIELD);
        expect(retval.groupId).toBe(1);
        expect(retval.index).toBe(0);
    });

    it('removeFilterField', () => {
        let rowId = 100;

        let retval = removeFilterField(rowId);

        expect(retval).toExist();
        expect(retval.type).toBe(REMOVE_FILTER_FIELD);
        expect(retval.rowId).toBe(100);
    });

    it('updateFilterField', () => {
        let rowId = 100;
        let fieldName = "fieldName";
        let fieldValue = "fieldValue";

        let retval = updateFilterField(rowId, fieldName, fieldValue);

        expect(retval).toExist();
        expect(retval.type).toBe(UPDATE_FILTER_FIELD);
        expect(retval.rowId).toBe(100);
        expect(retval.fieldName).toBe("fieldName");
        expect(retval.fieldValue).toBe("fieldValue");
    });

    it('updateExceptionField', () => {
        let rowId = 100;
        let message = "message";

        let retval = updateExceptionField(rowId, message);

        expect(retval).toExist();
        expect(retval.type).toBe(UPDATE_EXCEPTION_FIELD);
        expect(retval.rowId).toBe(100);
        expect(retval.exceptionMessage).toBe("message");
    });

    it('updateLogicCombo', () => {
        let groupId = 100;
        let logic = "OR";

        let retval = updateLogicCombo(groupId, logic);

        expect(retval).toExist();
        expect(retval.type).toBe(UPDATE_LOGIC_COMBO);
        expect(retval.groupId).toBe(100);
        expect(retval.logic).toBe("OR");
    });

    it('removeGroupField', () => {
        let groupId = 100;

        let retval = removeGroupField(groupId);

        expect(retval).toExist();
        expect(retval.type).toBe(REMOVE_GROUP_FIELD);
        expect(retval.groupId).toBe(100);
    });

    it('changeCascadingValue', () => {
        let attributes = [];

        let retval = changeCascadingValue(attributes);

        expect(retval).toExist();
        expect(retval.type).toBe(CHANGE_CASCADING_VALUE);
        expect(retval.attributes.length).toBe(0);
    });

    it('expandAttributeFilterPanel', () => {
        let expanded = false;

        let retval = expandAttributeFilterPanel(expanded);

        expect(retval).toExist();
        expect(retval.type).toBe(EXPAND_ATTRIBUTE_PANEL);
        expect(retval.expand).toBe(false);
    });

    it('expandSpatialFilterPanel', () => {
        let expanded = false;

        let retval = expandSpatialFilterPanel(expanded);

        expect(retval).toExist();
        expect(retval.type).toBe(EXPAND_SPATIAL_PANEL);
        expect(retval.expand).toBe(false);
    });
});
