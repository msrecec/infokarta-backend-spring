import React from "react";
import PropTypes from "prop-types";
import { Pagination } from "react-bootstrap";

// let active = 1;
// active triba bit van rendera da bi funkcionira

const style = {
    display: "flex",
    width: "100%",
    justifyContent: "center"
};

const noZindex = {
    zIndex: "0"
}; // postavljanjen zIndexa na nulu se aktivan item ne prikazuje dok je plugin sakriven

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
        let items = [];

        function setNumber(currentNumber) {
            // active = currentNumber;
            this.props.sendPageNumber(currentNumber);
        }

        for (let number = 1; number <= numberOfPages; number++) {
            items.push(
                <Pagination.Item
                    key={number}
                    active={number === this.props.active}
                    onClick={setNumber.bind(this, number)}
                    style={noZindex}
                >
                    {number}
                </Pagination.Item>
            );
        }

        return (
            <div>
                <Pagination style={style}>
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
