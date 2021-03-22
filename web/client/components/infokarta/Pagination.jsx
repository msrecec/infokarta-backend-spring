import React from "react";
import PropTypes from "prop-types";
import { Pagination } from "react-bootstrap";

const style = {
    display: "flex",
    width: "100%",
    justifyContent: "center"
};

class PaginationComponent extends React.Component {
    static propTypes = {
        totalNumber: PropTypes.number,
        sendPageNumber: PropTypes.func,
        active: PropTypes.number
    };

    static defaultProps = {
        totalNumber: 1
    };

    render() {
        let numberOfPages = Math.ceil(this.props.totalNumber / 30);
        // 30 stavki po stranici

        let paginationForm = document.getElementsByClassName("dynamicFormPagination");
        let tempPagination = paginationForm[0];
        if (tempPagination) {
            if (tempPagination.childNodes) {
                for (const [key, value] of Object.entries(tempPagination.childNodes)) {
                    value.style.zIndex = 0;
                }
            }
        }
        // workaround da prekine prikazivat aktivne elemente paginacije dok je drawerMenu zatvoren

        return (
            <div>
                <Pagination
                    className="dynamicFormPagination"
                    style={style}
                    prev next first last ellipsis boundaryLinks
                    bsSize="small"
                    items={numberOfPages}
                    maxButtons={8}
                    activePage={this.props.active}
                    onSelect={this.props.sendPageNumber}
                />
            </div>
        );
    }
}

export default PaginationComponent;
