import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { get } from "lodash";

class BasePluginEmitter extends React.Component {
  static propTypes = {
      pluginName: PropTypes.string
  };


  componentDidMount() {
      console.log(this.props.pluginName);
  }

  render() {
      return (<div />);
  }
}

const PluginNameEmitter = connect((state) => {
    return {

    };
})(BasePluginEmitter);
export default PluginNameEmitter;
