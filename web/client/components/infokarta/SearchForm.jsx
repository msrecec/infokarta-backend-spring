import React from 'react';
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

class SearchComponent extends React.Component {
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
          <Form style={formStyle} id="dynamicForm">
              {this.props.buildData ?
                  this.props.buildData.map((field) => {
                      return field.type === "text" ?
                          ( // ako polje ima type = text, napravi klasično text input polje
                              <FormGroup
                                  key={field.label}
                                  controlId={field.label}
                                  style={fieldContainerStyle}
                              >
                                  <ControlLabel>{field.label}</ControlLabel>
                                  <FormControl type={field.type} value={this.state[field.value]} onChange={(e) => this.handleChange(field.value, e)}/>
                              </FormGroup>)
                          : ( // ako polje nema type = text, napravi select polje
                              <FormGroup
                                  key={field.label}
                                  controlId={field.label}
                                  style={fieldContainerStyle}
                              >
                                  <ControlLabel>{field.label}</ControlLabel>
                                  <FormControl
                                      componentClass={field.type}
                                      placeholder={this.state[field.selectValues[0]]}
                                      onChange={(e) => this.handleChange(field.value, e)}
                                      id="select"
                                  >
                                      {field.selectValues.map((option) => {
                                          return <option value={option}>{option}</option>;
                                      })}
                                  </FormControl>
                              </FormGroup>
                          );
                  }
                  ) : null}
              <FormGroup
                  key="searchActions"
                  controlId="searchActions"
                  style={buttonContainerStyle}
              >
                  <Button bsStyle="success" onClick={() => this.props.search(this.state)} style={buttonStyle}>Pretraži</Button>
                  <Button bsStyle="info" onClick={() => this.clear()} style={buttonStyle}>Obriši podatke</Button>
              </FormGroup>
          </Form>
      );
  }

  handleChange(field, e) {
      this.setState({ [field]: e.target.value });
  }

  clear() {
      for (let field in this.state) {
          if ({}.hasOwnProperty.call(this.state, field)) {
              console.log(field);
              // https://eslint.org/docs/rules/guard-for-in
          }
      }
      let form = document.getElementById("dynamicForm");
      let selectTags = form.getElementsByTagName("select");

      for (let i = 0; i < selectTags.length; i++) {
          selectTags[i].selectedIndex = 0;
      }
  }
}

export default SearchComponent;
