import React from "react";
import PropTypes from "prop-types";

import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

import FileComponentParent from '../../components/infokarta/fileUpload/ParentComponent';

import { Tabs, Tab, Button } from 'react-bootstrap';

class PaginationComponent extends React.Component {
    static propTypes = {
        item: PropTypes.object,
        showDetails: PropTypes.string,
        closeDetailsView: PropTypes.func
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
                <div style={{display: "flex", flexDirection: "column"}}>
                    <Button bsStyle="link" onClick={() => this.props.closeDetailsView()} style={{width: "5%", margin: "0 auto"}}>Zatvori</Button>
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
            </div>
        );
    }
}

export default PaginationComponent;
