import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';

const formStyle = {
    display: "flex",
    flexDirection: "row"
};

const fieldContainerStyle = {
    paddingRight: "5px"
};

const buttonContainerStyle = {
    display: "flex",
    flexDirection: "row",
    alignItems: "flex-end"
};

const buttonStyle = {
    marginRight: "5px"
};

class BaseSearchComponent extends React.Component {
  static propTypes = {
      buildData: PropTypes.array,
      search: PropTypes.func
  };

  static defaultProps = {
      buildData: []
  };

  constructor(props) {
      super(props);
      this.handleChange = this.handleChange.bind(this);

      this.state = {};
  }

  render() {
      return (
          <Form style={formStyle}>
              {this.props.buildData ?
                  this.props.buildData.map((field) =>
                      <FormGroup controlId={field.label} style={fieldContainerStyle}>
                          <ControlLabel>{field.label}</ControlLabel>
                          <FormControl type={field.type} value={this.state[field.value]} onChange={(e) => this.handleChange(field.value, e)}/>
                      </FormGroup>
                  ) : null}
              <FormGroup controlId="searchActions" style={buttonContainerStyle}>
                  <Button bsStyle="success" onClick={() => this.props.search(this.state)} style={buttonStyle}>Pretraži</Button>
                  <Button bsStyle="info" onClick={() => this.clear()} style={buttonStyle}>Obriši podatke</Button>
              </FormGroup>
          </Form>
      );
  }

  handleChange(field, e) {
      //   console.log('handleChange', field, e.target.value);
      //   let stateCopy = [...this.state];
      //   stateCopy = {
      //       field: e.target.value
      //   };
      //   console.log(stateCopy);
      this.setState({ [field]: e.target.value });
  }

  clear() {
      for (let field in this.state) {
          if ({}.hasOwnProperty.call(this.state, field)) {
              // https://eslint.org/docs/rules/guard-for-in
              console.log(field);
              this.setState({ [field]: "" });
          }
      }
  }
}

const SearchComponent = connect((state) => {
    return {
    };
}, {
})(BaseSearchComponent);

export default SearchComponent;
