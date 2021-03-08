import React from "react";
import PropTypes from "prop-types";
import { Pagination, PageItem } from "react-bootstrap";

class PaginationComponent extends React.Component {
    static propTypes = {
        numberOfPages: PropTypes.number,
        sendData: PropTypes.func
    };

    static defaultProps = {};

    render() {
        let active = 10;
        let items = [];
        for (let number = 1; number <= this.props.numberOfPages; number++) {
            items.push(
                <Pagination.Item key={number} active={number === active} /* onclick=sendData(number)*/>
                    {number}
                </Pagination.Item>
            );
        }
        console.log(this.props.numberOfPages, "da");
        console.log(items, "daa");
        const pagina = (
            <div>
                <Pagination>{items}</Pagination>
                <br />
            </div>
        );
        return (
            <div>
                {/* <Pagination>{this.items}</Pagination> */}
                {pagina}
            </div>
        );
    }
}

export default PaginationComponent;
