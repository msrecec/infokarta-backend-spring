import React from 'react';
import PropTypes from 'prop-types';
import { Cell } from 'react-data-grid';

class CellRenderer extends React.Component {
    static propTypes = {
        value: PropTypes.any,
        rowData: PropTypes.object,
        column: PropTypes.object
    };
    static contextTypes = {
        isModified: PropTypes.func,
        isProperty: PropTypes.func,
        isValid: PropTypes.func
    };
    static defaultProps = {
        value: null,
        rowData: {},
        column: {}
    }
    constructor(props) {
        super(props);
        this.setScrollLeft = (scrollBy) => this.refs.cell.setScrollLeft(scrollBy);
    }
    render() {
        const isProperty = this.context.isProperty(this.props.column.key);
        const isModified = (this.props.rowData._new && isProperty) || this.context.isModified(this.props.rowData.id, this.props.column.key);
        const isValid = isProperty ? this.context.isValid(this.props.rowData.get(this.props.column.key), this.props.column.key) : true;
        const className = (isModified ? ['modified'] : [])
            .concat(isValid ? [] : ['invalid']).join(" ");
        const properties = this.props.rowData.properties;
        if ("properties" in this.props.rowData) {
            for (const key in properties) {
                if (properties[key] === properties.source) {
                    properties[key] = <a href={'http://213.191.153.249:8080/static/' + properties.source} target="_blank">{properties.source}</a>;
                    // item.properties.source = `"http://213.191.153.249:8080/static/${item.properties.source}"`;
                }
            }
        }
        return <Cell {...this.props} ref="cell" className={className} />;
    }
}

export default CellRenderer;
