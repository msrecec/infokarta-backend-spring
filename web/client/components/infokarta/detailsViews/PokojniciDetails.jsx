import React from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";


import { displayFeatureInfo, buildCarouselFromURLs } from "../../../utils/infokarta/ComponentConstructorUtil";
import { zoomToGraveFromDeceased } from "../../../actions/infokarta/deceased";
import { zoomToActivePluginSegment } from "../../../actions/infokarta/dynamicComponents";

import FileComponentParent from '../fileUpload/ParentComponent';

import { Tabs, Tab, Button, Glyphicon } from 'react-bootstrap';

class BasePokojniciDetails extends React.Component {
    static propTypes = {
        items: PropTypes.object,
        showDetails: PropTypes.bool,
        closeDetailsView: PropTypes.func,
        editItem: PropTypes.func,
        fieldsToExclude: PropTypes.array,
        toggleDetailViews: PropTypes.func,
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
                maxHeight: "440px",
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
        const fileComponentParentDeceased = (<FileComponentParent
            itemId={this.props.items.pokojnik && this.props.items.pokojnik.fid ? this.props.items.pokojnik.fid : null}
        />);

        let sourceArray = [];
        if (this.props.items.grob) {
            for (const [key, value] of Object.entries(this.props.items.grob)) {
                if (key.toUpperCase().includes('SOURCE') && value) {
                    sourceArray.push(value);
                }
            }
        }

        const grobFieldsToExclude = ["fid", "source", "source1", "source2", "source3", "source4", "source5", "source6", "source7", "fk", "ime_i_prezime"];
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
                                <h3>Umrla osoba{this.props.items.pokojnik ? ` - ${this.props.items.pokojnik.ime} ${this.props.items.pokojnik.prezime}` : ''}</h3>
                                <Button
                                    bsStyle="link"
                                    onClick={() => this.props.editItem(this.props.items.pokojnik.fid)}
                                >
                                    <Glyphicon glyph="pencil" /> Uredi podatke
                                </Button>
                            </div>
                            {this.props.items.pokojnik ? displayFeatureInfo(this.props.items.pokojnik, this.props.fieldsToExclude) : null}
                            <div>
                                <Button
                                    bsStyle="link"
                                    onClick={() => this.props.toggleDetailViews(this.props.items.pokojnik.fk)}
                                    // style={{display: "flex"}}
                                >
                                    <h3><u>Grobnica</u></h3>
                                </Button>
                                {this.props.items.grob ? displayFeatureInfo(this.props.items.grob, grobFieldsToExclude) : null}
                                <br />
                                {sourceArray.length ? buildCarouselFromURLs(sourceArray) : null}
                            </div>
                        </div>
                    </Tab>
                    <Tab eventKey={2} title="Dokumenti" style={styles.tabContentStyle}>
                        <div>
                            {fileComponentParentDeceased}
                        </div>
                    </Tab>
                </Tabs>
            </div>
        );
    }
    runAfterRender = () => {
        const myElem = document.getElementsByClassName("myElem");
        if (myElem) {
            /* this.props.zoomToItem(this.props.items.geom); */
            console.log("Addat zoom funkcionalnost kada Mislav implementira ");
        }
        // Nece zumirat
    }
}

const PokojniciDetails = connect((state) => {
    return {

    };
}, {
    zoomToItem: zoomToActivePluginSegment
})(BasePokojniciDetails);

export default PokojniciDetails;
