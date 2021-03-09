import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';

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
      console.log('bd: ', this.props.buildData);
      return (
          <Form>
              <FormGroup controlId="searchActions" onClick={() => this.props.search(this.state)}>
                  <Button bsStyle="success">Pretraži</Button>
              </FormGroup>
              {this.props.buildData ?
                  this.props.buildData.map((field) =>
                      <FormGroup controlId={field.label}>
                          <ControlLabel>{field.label}</ControlLabel>
                          <FormControl type={field.type} value={this.state[field.value]} onChange={(e) => this.handleChange(field.value, e)}/>
                      </FormGroup>
                  ) : null}
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
}

const SearchComponent = connect((state) => {
    return {
    };
}, {
})(BaseSearchComponent);

export default SearchComponent;
