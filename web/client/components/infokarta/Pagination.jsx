import React from "react";
import PropTypes from "prop-types";
import { Pagination } from "react-bootstrap";

let active = 1;
// active triba bit van da bi funkcionira
// vjerojatno jer ga re-render obriše svaki put

class PaginationComponent extends React.Component {
    static propTypes = {
        totalNumber: PropTypes.number,
        sendPageNumber: PropTypes.func
    };

    static defaultProps = {
        totalNumber: 1
    };

    render() {
        let numberOfPages = Math.ceil(this.props.totalNumber / 30);
        let items = [];

        function setNumber(currentNumber) {
            active = currentNumber;
            this.props.sendPageNumber(currentNumber);
        }

        for (let number = 1; number <= numberOfPages; number++) {
            items.push(
                <Pagination.Item
                    key={number}
                    active={number === active}
                    onClick={setNumber.bind(this, number)}
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
