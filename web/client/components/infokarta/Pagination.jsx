import React from "react";
import PropTypes from "prop-types";
import { Pagination } from "react-bootstrap";

class PaginationComponent extends React.Component {
    static propTypes = {
        totalNumber: PropTypes.number,
        sendPageNumber: PropTypes.func
    };

    static defaultProps = {
        totalNumber: 1
    };

    render() {
        let active = 1;
        let numberOfPages = Math.ceil(this.props.totalNumber / 30);
        let items = [];

        for (let number = 1; number <= numberOfPages; number++) {
            items.push(
                <Pagination.Item
                    key={number}
                    active={number === active}
                    onClick={() => this.props.sendPageNumber(number)}
                >
                    {number}
                </Pagination.Item>
            );
        }
        return (
            <div>
                <Pagination>
                    {/* <Pagination.First />
                    <Pagination.Prev /> */}
                    {items}
                    {/* <Pagination.Next />
                    <Pagination.Last /> */}
                </Pagination>
            </div>
        );
    }
}

export default PaginationComponent;
