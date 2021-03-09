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

  render() {
      console.log('bd: ', this.props.buildData);
      const searchParameters = {};
      return (
          <Form>
              <FormGroup controlId="searchActions" onClick={() => this.props.search(searchParameters)}>
                  <Button bsStyle="success">Pretraži</Button>
              </FormGroup>
              {this.props.buildData ?
                  this.props.buildData.map((field) =>
                      <FormGroup controlId={field.label}>
                          <ControlLabel>{field.label}</ControlLabel>
                          <FormControl type={field.type} defaultValue={searchParameters[field.value]}/>
                      </FormGroup>
                  ) : null}
          </Form>
      );
  }
}

const SearchComponent = connect((state) => {
    return {
    };
}, {
})(BaseSearchComponent);

export default SearchComponent;
