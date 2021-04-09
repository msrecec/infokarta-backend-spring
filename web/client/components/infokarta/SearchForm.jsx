import React from 'react';
import PropTypes from 'prop-types';
import {Button, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';

const formStyle = {
    display: "flex",
    flexDirection: "row",
    width: "100%",
    justifyContent: "center"
};

const fieldStyle = {
    paddingRight: "5px"
};

const buttonStyle = {
    marginRight: "5px"
};

class SearchComponent extends React.Component {
  static propTypes = {
      buildData: PropTypes.array,
      search: PropTypes.func,
      resetSearchParameters: PropTypes.func,
      openInsertForm: PropTypes.func,
      insertModalName: PropTypes.string
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
          <div>
              <form style={formStyle} id="dynamicForm">
                  {this.props.buildData ?
                      this.props.buildData.map((field) => {
                          return field.type === "text" ?
                              ( // ako polje ima type = text, napravi klasično text input polje
                                  <FormGroup
                                      key={field.label}
                                      controlId={field.label}
                                      style={fieldStyle}
                                  >
                                      <ControlLabel>{field.label}</ControlLabel>
                                      <FormControl
                                          type={field.type}
                                          value={this.state[field.value]}
                                          onChange={(e) => this.handleChange(field.value, e)}
                                      />
                                  </FormGroup>)
                              : ( // ako polje nema type = text, napravi select polje
                                  <FormGroup
                                      key={field.label}
                                      controlId={field.label}
                                      style={fieldStyle}
                                  >
                                      <ControlLabel>{field.label}</ControlLabel>
                                      <FormControl
                                          componentClass={field.type}
                                          value={this.state[field.value] ? this.state[field.value] : ""}
                                          onChange={(e) => this.handleChange(field.value, e)}
                                      >
                                          {field.selectValues.map((option) => {
                                              return <option value={option} key={option}>{option}</option>;
                                          })}
                                      </FormControl>
                                  </FormGroup>
                              );
                      }
                      ) : null}
              </form>
              <form>
                  <FormGroup
                      key="searchActions"
                      controlId="searchActions"
                      style={formStyle}
                  >
                      <Button bsStyle="success" onClick={() => this.search()} style={buttonStyle}>Pretraži</Button>
                      <Button bsStyle="info" onClick={() => this.clear()} style={buttonStyle}>Obriši podatke</Button>
                      <Button bsStyle="info" onClick={() => this.insertNew()} style={buttonStyle}>Unesi novu stavku</Button>
                  </FormGroup>
              </form>
          </div>
      );
  }

  handleChange(field, e) {
      this.setState({ [field]: e.target.value });
  }

  clear() {
      for (let field in this.state) {
          if ({}.hasOwnProperty.call(this.state, field)) {
              // https://eslint.org/docs/rules/guard-for-in
              this.setState({ [field]: "" });
          }
      }
      let form = document.getElementById("dynamicForm");
      let selectTags = form.getElementsByTagName("select");

      for (let i = 0; i < selectTags.length; i++) {
          selectTags[i].selectedIndex = 0;
      }

      this.props.resetSearchParameters();
  }

  search(searchParams = this.state) {
      this.props.search(searchParams);
  }

  insertNew() {
      this.props.openInsertForm();
  }
}

export default SearchComponent;
