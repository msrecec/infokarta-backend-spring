import React from 'react';
import PropTypes from 'prop-types';
import {Button, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import { cloneDeep } from 'lodash';

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
      pageNumber: PropTypes.number,
      openInsertForm: PropTypes.func,
      resetPagination: PropTypes.func
  };

  static defaultProps = {
      buildData: [],
      pageNumber: 1
  };

  constructor(props) {
      super(props);
      this.handleChange = this.handleChange.bind(this);

      this.state = {};
  }

  componentDidUpdate(prevProps) {
      // kad se promijeni stranica paginacije, pokreni search ponovno
      if (prevProps.pageNumber !== this.props.pageNumber) {
          this.setState({ page: this.props.pageNumber });
          let temp = cloneDeep(this.state);
          temp.page = this.props.pageNumber;
          this.props.search(temp);
          // cloneDeep workaround jer je setState asinkron i updatea se tek nakon componentDidUpdate
          // zato kopiramo state i pripisujemo mu trenutni pageNumber i saljemo u search
      }
  }

  render() {
      return (
          <div>
              <Form style={formStyle} id="dynamicForm">
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
                                      <FormControl type={field.type} value={this.state[field.value]} onChange={(e) => this.handleChange(field.value, e)}/>
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
                                          placeholder={this.state[field.selectValues[0]]}
                                          onChange={(e) => this.handleChange(field.value, e)}
                                      >
                                          {field.selectValues.map((option) => {
                                              return <option value={option}>{option}</option>;
                                          })}
                                      </FormControl>
                                  </FormGroup>
                              );
                      }
                      ) : null}
              </Form>
              <Form>
                  <FormGroup
                      key="searchActions"
                      controlId="searchActions"
                      style={formStyle}
                  >
                      <Button bsStyle="success" onClick={() => this.search()} style={buttonStyle}>Pretraži</Button>
                      <Button bsStyle="info" onClick={() => this.clear()} style={buttonStyle}>Obriši podatke</Button>
                      <Button bsStyle="info" onClick={() => this.insertNew()} style={buttonStyle}>Unesi novu stavku</Button>
                  </FormGroup>
              </Form>
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

      this.search({});
  }

  search(searchParams = this.state) {
      this.props.search(searchParams);
      this.props.resetPagination(1);
  }

  insertNew() {
      this.props.openInsertForm();
  }
}

export default SearchComponent;
