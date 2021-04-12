import React from "react";
import PropTypes from "prop-types";

import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

import FileComponentParent from '../../components/infokarta/fileUpload/ParentComponent';

import { Tabs, Tab } from 'react-bootstrap';

class PaginationComponent extends React.Component {
    static propTypes = {
        item: PropTypes.object,
        showDetails: PropTypes.string
    };

    static defaultProps = {
        item: {},
        showDetails: "none"
    };

    render() {
        const style = {
            overflow: "auto",
            maxHeight: "600px",
            minWidth: "580px",
            display: this.props.showDetails
        };

        const tabContentStyle = {
            padding: "10px",
            overflow: "auto",
            height: "auto"
        };

        const fileComponentParent = (<FileComponentParent
            itemId={this.props.item.fid ? this.props.item.fid : null}
        />);

        return (
            <div style={style}>
                <Tabs defaultActiveKey={1} id="detail-and-doc-tabs">
                    <Tab eventKey={1} title="Detalji stavke">
                        <div style={tabContentStyle}>
                            {displayFeatureInfo(this.props.item)}
                        </div>
                    </Tab>
                    <Tab eventKey={2} title="Dokumenti vezani uz stavku">
                        <div style={tabContentStyle}>
                            {fileComponentParent}
                        </div>
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

export default PaginationComponent;
