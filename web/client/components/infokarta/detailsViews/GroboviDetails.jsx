import React from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";

import { displayFeatureInfo, buildCarouselFromURLs } from "../../../utils/infokarta/ComponentConstructorUtil";
import { zoomToGraveFromGraves } from "../../../actions/infokarta/graves";

import FileComponentParent from '../fileUpload/ParentComponent';
import Table from '../Table';

import { Tabs, Tab, Button, Glyphicon } from 'react-bootstrap';

class BaseGroboviDetails extends React.Component {
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
                maxHeight: "780px",
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
        const fileComponentParentGraves = (<FileComponentParent
            itemId={this.props.items.grob && this.props.items.grob.fid ? this.props.items.grob.fid : null}
        />);

        let sourceArray = [];
        if (this.props.items.grob) {
            for (const [key, value] of Object.entries(this.props.items.grob)) {
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
                                <h3>Grobnica - {this.props.items.grob ? this.props.items.grob.grobnica : null}</h3>
                                <Button
                                    bsStyle="link"
                                    onClick={() => this.props.editItem(this.props.items.grob.fid)}
                                >
                                    <Glyphicon glyph="pencil" /> Uredi podatke
                                </Button>
                            </div>
                            <div>
                                {this.props.items.grob ? displayFeatureInfo(this.props.items.grob, this.props.fieldsToExclude) : null}
                                <br />
                                {sourceArray.length ? buildCarouselFromURLs(sourceArray) : null}
                            </div>
                            <h3>Umrle osobe</h3>
                            <Table items={this.props.items.pokojnici} fieldsToInclude={["ime", "prezime"]}/>
                        </div>
                    </Tab>
                    <Tab eventKey={2} title="Dokumenti" style={styles.tabContentStyle}>
                        <div>
                            {fileComponentParentGraves}
                        </div>
                    </Tab>
                </Tabs>
            </div>
        );
    }
    runAfterRender = () => {
        const myElem = document.getElementsByClassName("myElem");
        if (myElem) {
            this.props.zoomToItem(this.props.items.grob.geom);
            console.log(":D:D:D", this.props);
        }
    }
}
const GroboviDetails = connect((state) => {
    return {

    };
}, {
    zoomToItem: zoomToGraveFromGraves
})(BaseGroboviDetails);

export default GroboviDetails;
