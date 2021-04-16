import React from "react";
import PropTypes from "prop-types";

import { displayFeatureInfo, buildCarouselFromURLs } from "../../utils/infokarta/ComponentConstructorUtil";

import FileComponentParent from '../../components/infokarta/fileUpload/ParentComponent';

import { Tabs, Tab, Button, Glyphicon } from 'react-bootstrap';

class PaginationComponent extends React.Component {
    static propTypes = {
        items: PropTypes.object,
        showDetails: PropTypes.bool,
        fieldsToExclude: PropTypes.array,
        closeDetailsView: PropTypes.func,
        editItem: PropTypes.func,
        editModalName: PropTypes.string,
        title: PropTypes.string,
        additionalTitle: PropTypes.string,
        tabPanelMaxHeight: PropTypes.string
    };

    static defaultProps = {
        items: [],
        showDetails: false,
        additionalTitle: "",
        fieldsToExclude: [],
        tabPanelMaxHeight: "480px"
    };

    componentDidUpdate(prevProps) {
        this.elementList = [];
        console.log('!!! CDU items', prevProps.items, this.props.items);
        if (this.props.items.length && prevProps.items !== this.props.items) {
            for (let i = 0; i < this.props.items.length; i++) {
                let sourceArray = [];
                for (const [key, value] of Object.entries(this.props.items[i])) {
                    if (key.toUpperCase().includes('SOURCE') && value) {
                        sourceArray.push(value);
                    }
                }
                this.elementList.push(
                    <div>
                        {i > 0 ? <h3>{this.props.additionalTitle}</h3> : null}
                        <hr style={{margin: "2px"}}/>
                        {displayFeatureInfo(this.props.items[i], this.props.fieldsToExclude)}
                        <hr style={{margin: "2px"}}/>
                        {sourceArray.length ? buildCarouselFromURLs(sourceArray) : null}
                    </div>
                );
            }
            console.log('!!! element list', this.elementList);
            this.forceUpdate(); // toliko o reaktivnosti u reactu lol (vue: 1 | react: 0)
        }
    }

    render() {
        const styles = {
            tabContentStyle: {
                padding: "10px",
                overflow: "auto",
                maxHeight: this.props.tabPanelMaxHeight,
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
                zIndex: 100
            } // https://css-tricks.com/position-sticky-and-table-headers/
        };

        // const fileComponentParent = (<FileComponentParent
        //     itemId={this.props.items[0].fid ? this.props.items[0].fid : null}
        // />);

        console.log('!!! element list in render', this.elementList);
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
                        <div>
                            <div style={styles.stickyTitleStyle}>
                                <h3>{this.props.title}</h3>
                                <Button
                                    bsStyle="link"
                                    onClick={() => this.props.editItem(this.props.editModalName, this.props.items[0])}
                                >
                                    <Glyphicon glyph="pencil" /> Uredi podatke
                                </Button>
                            </div>
                            {this.elementList}
                        </div>
                    </Tab>
                    <Tab eventKey={2} title="Dokumenti" style={styles.tabContentStyle}>
                        <div>
                            {/* {fileComponentParent} */}
                        </div>
                    </Tab>
                </Tabs>
            </div>
        );
    }

    elementList = [];
}

export default PaginationComponent;
