import React from "react";
import PropTypes from "prop-types";
import { Pagination } from "react-bootstrap";

const style = {
    display: "flex",
    width: "100%",
    justifyContent: "center",
    margin: "10px 0"
};

class PaginationComponent extends React.Component {
    static propTypes = {
        setPageNumber: PropTypes.func,
        totalNumber: PropTypes.number,
        itemsPerPage: PropTypes.number,
        active: PropTypes.number
    };

    static defaultProps = {
        itemsPerPage: 30,
        totalNumber: 1
    };

    render() {
        let numberOfPages = Math.ceil(this.props.totalNumber / this.props.itemsPerPage);

        //
        let paginationForm = document.getElementsByClassName("dynamicFormPagination");
        let tempPagination = paginationForm[0];
        if (tempPagination) {
            if (tempPagination.childNodes) {
                for (const value of Object.values(tempPagination.childNodes)) {
                    value.style.zIndex = 0;
                }
            }
        }
        // workaround da prekine prikazivat aktivne elemente paginacije dok je drawerMenu zatvoren
        // zakomentirajte od prvog komentara do tu da vidite u cemu je stvar

        return (
            <div>
                <Pagination
                    className="dynamicFormPagination"
                    style={style}
                    prev next first last ellipsis boundaryLinks
                    bsSize="small"
                    items={numberOfPages}
                    maxButtons={5}
                    activePage={this.props.active}
                    onSelect={this.props.setPageNumber}
                />
            </div>
        );
    }
}

export default PaginationComponent;
