import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import { get } from "lodash";

import {
    getColumnsForInsertFromDatabase
} from "../../actions/infokarta/dynamicComponents";

const styles = {
    formStyle: {
        display: "flex",
        flexDirection: "row",
        width: "100%",
        justifyContent: "center"
    },
    fieldStyle: {
        paddingRight: "5px"
    },
    buttonStyle: {
        marginRight: "5px"
    }
};

class BaseSearchComponent extends React.Component {
  static propTypes = {
      buildData: PropTypes.array,
      search: PropTypes.func,
      resetSearchParameters: PropTypes.func,
      openInsertForm: PropTypes.func,
      insertModalName: PropTypes.string,
      disableInsert: PropTypes.bool
  };

  static defaultProps = {
      buildData: [],
      disableInsert: false
  };

  constructor(props) {
      super(props);
      this.handleChange = this.handleChange.bind(this);

      this.state = {};
  }

  componentDidMount() {
      this.props.search({});
  }

  render() {
      return (
          <div>
              <form style={styles.formStyle} id="dynamicForm">
                  {this.props.buildData ?
                      this.props.buildData.map((field) => {
                          return field.type === "text" ?
                              ( // ako polje ima type = text, napravi klasično text input polje
                                  <FormGroup
                                      key={field.label}
                                      controlId={field.label}
                                      style={styles.fieldStyle}
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
                                      style={styles.fieldStyle}
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
                      style={styles.formStyle}
                  >
                      <Button bsStyle="success" onClick={() => this.search()} style={styles.buttonStyle}>Pretraži</Button>
                      <Button bsStyle="info" onClick={() => this.clear()} style={styles.buttonStyle}>Obriši parametre</Button>
                      {this.props.disableInsert ? null : <Button bsStyle="info" onClick={() => this.props.openInsertForm()} style={styles.buttonStyle}>Unesi novu stavku</Button> }
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
}

const SearchComponent = connect((state) => {
    return {
        insertModalName: get(state, 'dynamicComponents.activePlugin') + 'Insert'
    };
}, {
    openInsertForm: getColumnsForInsertFromDatabase
})(BaseSearchComponent);

export default SearchComponent;
