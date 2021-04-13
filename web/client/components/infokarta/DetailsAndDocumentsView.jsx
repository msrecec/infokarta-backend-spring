import React from "react";
import PropTypes from "prop-types";

import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

import FileComponentParent from '../../components/infokarta/fileUpload/ParentComponent';

import { Tabs, Tab, Button, Glyphicon } from 'react-bootstrap';

class PaginationComponent extends React.Component {
    static propTypes = {
        item: PropTypes.object,
        showDetails: PropTypes.string,
        closeDetailsView: PropTypes.func,
        editItem: PropTypes.func,
        editModalName: PropTypes.string,
        title: PropTypes.string,
        fieldsToExclude: PropTypes.array
    };

    static defaultProps = {
        item: {},
        showDetails: "none"
    };

    render() {
        const style = {
            display: this.props.showDetails,
            maxHeight: "520px"
        };

        const tabContentStyle = {
            padding: "10px",
            overflow: "auto",
            maxHeight: "450px"
        };

        const closeButtonStyle = {
            position: "absolute",
            right: "0",
            padding: "12px"
        };

        const fileComponentParent = (<FileComponentParent
            itemId={this.props.item.fid ? this.props.item.fid : null}
        />);

        return (
            <div style={style}>
                <Button
                    bsStyle="link"
                    onClick={() => this.props.closeDetailsView()}
                    style={closeButtonStyle}
                >
                    <Glyphicon glyph="arrow-down" /> Zatvori
                </Button>
                <Tabs defaultActiveKey={1} id="detail-and-doc-tabs">
                    <Tab eventKey={1} title="Detalji stavke" style={tabContentStyle}>
                        <div>
                            <div style={{display: "flex", alignItems: "flex-end"}}>
                                <h2 style={{marginBottom: "3px"}}>{this.props.title}</h2>
                                <Button
                                    bsStyle="link"
                                    onClick={() => this.props.editItem(this.props.editModalName, this.props.item)}
                                    style={{paddingBottom: "0px"}}
                                >
                                    <Glyphicon glyph="pencil" /> Uredi podatke
                                </Button>
                            </div>
                            {/* TODO dodat nacin da se osvjezi details tab nakon edita */}
                            <hr style={{marginTop: "0px"}}/>
                            {displayFeatureInfo(this.props.item, this.props.fieldsToExclude)}
                        </div>
                    </Tab>
                    <Tab eventKey={2} title="Dokumenti vezani uz stavku" style={tabContentStyle}>
                        <div>
                            {fileComponentParent}
                        </div>
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

export default PaginationComponent;
