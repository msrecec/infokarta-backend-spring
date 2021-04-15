import React from "react";
import PropTypes from "prop-types";

import { displayFeatureInfo, buildCarouselFromURLs } from "../../utils/infokarta/ComponentConstructorUtil";

import FileComponentParent from '../../components/infokarta/fileUpload/ParentComponent';

import { Tabs, Tab, Button, Glyphicon } from 'react-bootstrap';

let sourceArray = [];

class PaginationComponent extends React.Component {
    static propTypes = {
        item: PropTypes.object,
        showDetails: PropTypes.bool,
        closeDetailsView: PropTypes.func,
        editItem: PropTypes.func,
        editModalName: PropTypes.string,
        title: PropTypes.string,
        additionalTitle: PropTypes.string,
        fieldsToExclude: PropTypes.array
    };

    static defaultProps = {
        item: {},
        showDetails: false,
        additionalTitle: ""
    };

    // componentDidUpdate(prevProps) {
    //     if (prevProps.item !== this.props.item && this.props.item !== {}) {
    //         sourceArray = [];
    //         for (const [key, value] of Object.entries(this.props.item)) {
    //             if (key.toUpperCase().includes('SOURCE') && value) {
    //                 sourceArray.push(value);
    //             }
    //         }
    //     }
    // }

    render() {
        if (this.props.item) {
            sourceArray = [];
            for (const [key, value] of Object.entries(this.props.item)) {
                if (key.toUpperCase().includes('SOURCE') && value) {
                    sourceArray.push(value);
                }
            }
        }

        const tabContentStyle = {
            padding: "10px",
            overflow: "auto",
            maxHeight: "480px",
            height: "auto"
        };

        const closeButtonStyle = {
            position: "absolute",
            right: "0",
            padding: "12px"
        };

        const fileComponentParent = (<FileComponentParent
            itemId={this.props.item.fid ? this.props.item.fid : null}
        />);

        const stickyTitle = {
            position: "sticky",
            top: "-10px",
            background: "white",
            boxShadow: "0 2px 2px -1px #dddddd",
            display: "flex",
            alignItems: "flex-end"
        }; // https://css-tricks.com/position-sticky-and-table-headers/

        return (
            <div style={this.props.showDetails ? {display: "block", transition: "all .2s linear"} : {display: "none", transition: "all .2s linear"} }>
                <Button
                    bsStyle="link"
                    onClick={() => this.props.closeDetailsView()}
                    style={closeButtonStyle}
                >
                    <Glyphicon glyph="arrow-down" /> Zatvori
                </Button>
                <Tabs defaultActiveKey={1} id="detail-and-doc-tabs">
                    <Tab eventKey={1} title="Detalji" style={tabContentStyle}>
                        <div>
                            <div style={stickyTitle}>
                                <h3 style={{marginBottom: "3px"}}>{(this.props.additionalTitle && this.props.item[this.props.additionalTitle]) ? `${this.props.title} - ${this.props.item[this.props.additionalTitle]}` : `${this.props.title}`}</h3>
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
                            <hr style={{marginTop: "0px"}}/>
                            {sourceArray.length ? buildCarouselFromURLs(sourceArray) : null}
                        </div>
                    </Tab>
                    <Tab eventKey={2} title="Dokumenti" style={tabContentStyle}>
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
