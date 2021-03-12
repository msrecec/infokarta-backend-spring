import React from 'react';
import PropTypes from 'prop-types';
import {Button, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import { cloneDeep } from 'lodash';

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
      search: PropTypes.func,
      pageNumber: PropTypes.number
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
              // https://eslint.org/docs/rules/guard-for-in
              this.setState({ [field]: "" });
          }
      }
      let form = document.getElementById("dynamicForm");
      let selectTags = form.getElementsByTagName("select");

      for (let i = 0; i < selectTags.length; i++) { // TODO https://reactjs.org/docs/refs-and-the-dom.html#callback-refs
          selectTags[i].selectedIndex = 0;
      }

      this.props.search();
  }
}

export default SearchComponent;
