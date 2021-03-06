import React from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";

import { displayFeatureInfo, buildCarouselFromURLs } from "../../../utils/infokarta/ComponentConstructorUtil";
import { zoomToLampFromLighting } from '../../../actions/infokarta/lighting';
import { zoomToActivePluginSegment } from '../../../actions/infokarta/dynamicComponents';

import FileComponentParent from '../fileUpload/ParentComponent';

import { Tabs, Tab, Button, Glyphicon } from 'react-bootstrap';

class BaseRasvjetaDetails extends React.Component {
    static propTypes = {
        items: PropTypes.object,
        showDetails: PropTypes.bool,
        closeDetailsView: PropTypes.func,
        editItem: PropTypes.func,
        fieldsToExclude: PropTypes.array,
        zoomToItem: PropTypes.func
    };

    static defaultProps = {
        items: {},
        showDetails: false,
        fieldsToExclude: [],
        zoomToItem: () => {}
    };

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const styles = {
            tabContentStyle: {
                padding: "10px",
                overflow: "auto",
                maxHeight: "480px",
                height: "auto"
            },
            closeButtonStyle: {
                position: "absolute",
                right: "0",
                padding: "12px"
            },
            stickyTitleStyle: {
                position: "sticky",
                top: "-10px",
                background: "white",
                boxShadow: "0 2px 2px -1px #dddddd",
                display: "flex",
                flexDirection: "column",
                alignItems: "flex-start",
                zIndex: 100,
                marginBottom: "4px"
            } // https://css-tricks.com/position-sticky-and-table-headers/
        };

        const fileComponentParentLighting = (<FileComponentParent
            itemId={this.props.items && this.props.items.fid ? this.props.items.fid : null}
        />);

        let sourceArray = [];
        if (this.props.items) {
            for (const [key, value] of Object.entries(this.props.items)) {
                if (key.toUpperCase().includes('SOURCE') && value) {
                    sourceArray.push(value);
                }
            }
        }

        return (
            <div style={this.props.showDetails ? {display: "block"} : {display: "none"} }>
                <Button
                    bsStyle="link"
                    onClick={() => this.props.closeDetailsView()}
                    style={styles.closeButtonStyle}
                >
                    <Glyphicon glyph="arrow-down" /> Zatvori
                </Button>
                <Tabs defaultActiveKey={1} id="detail-and-doc-tabs">
                    <Tab eventKey={1} title="Detalji" style={styles.tabContentStyle}>
                        <div className="myElem" onLoad = {this.runAfterRender()}>
                            <div style={styles.stickyTitleStyle}>
                                <h3>Rasvjetno tijelo</h3>
                                <Button
                                    bsStyle="link"
                                    onClick={() => this.props.editItem(this.props.items.fid)}
                                >
                                    <Glyphicon glyph="pencil" /> Uredi podatke
                                </Button>
                            </div>


                            {this.props.items ? displayFeatureInfo(this.props.items, this.props.fieldsToExclude) : null}
                            <br />
                            {sourceArray.length ? buildCarouselFromURLs(sourceArray) : null}
                        </div>

                    </Tab>
                    <Tab eventKey={2} title="Dokumenti" style={styles.tabContentStyle}>
                        <div>
                            {fileComponentParentLighting}
                        </div>
                    </Tab>
                </Tabs>
            </div>
        );
    }
    runAfterRender = () => {
        const myElem = document.getElementsByClassName("myElem");
        if (myElem && this.props.items && this.props.items.geom) {
            this.props.zoomToItem(this.props.items.geom);
        }
    }
}

const RasvjetaDetails = connect(() => {
    return {};
}, {
    zoomToItem: zoomToActivePluginSegment
})(BaseRasvjetaDetails);

export default RasvjetaDetails;
