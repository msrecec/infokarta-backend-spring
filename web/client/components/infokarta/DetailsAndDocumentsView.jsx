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
        fieldsToExclude: PropTypes.array,
        closeDetailsView: PropTypes.func,
        editItem: PropTypes.func,
        editModalName: PropTypes.string,
        title: PropTypes.string,
        additionalTitle: PropTypes.string
    };

    static defaultProps = {
        item: {},
        showDetails: false,
        additionalTitle: "",
        fieldsToExclude: []
    };

    render() {
        if (this.props.item) {
            sourceArray = [];
            for (const [key, value] of Object.entries(this.props.item)) {
                if (key.toUpperCase().includes('SOURCE') && value) {
                    sourceArray.push(value);
                }
            }
        }

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
                alignItems: "flex-start"
            } // https://css-tricks.com/position-sticky-and-table-headers/
        };

        const fileComponentParent = (<FileComponentParent
            itemId={this.props.item.fid ? this.props.item.fid : null}
        />);

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
                        {/* TODO prosirit tab da prima listu objekata te je prikazuje ovde */}
                        <div>
                            <div style={styles.stickyTitleStyle}>
                                <h3 >{(this.props.additionalTitle && this.props.item[this.props.additionalTitle]) ? `${this.props.title} - ${this.props.item[this.props.additionalTitle]}` : `${this.props.title}`}</h3>
                                <Button
                                    bsStyle="link"
                                    onClick={() => this.props.editItem(this.props.editModalName, this.props.item)}
                                >
                                    <Glyphicon glyph="pencil" /> Uredi podatke
                                </Button>
                            </div>
                            <hr style={{marginTop: "0px"}}/>
                            {displayFeatureInfo(this.props.item, this.props.fieldsToExclude)}
                            <hr style={{marginTop: "0px"}}/>
                            {sourceArray.length ? buildCarouselFromURLs(sourceArray) : null}
                        </div>
                    </Tab>
                    <Tab eventKey={2} title="Dokumenti" style={styles.tabContentStyle}>
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
