import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { get } from "lodash";
import { acquireCurrentPluginName } from '../../actions/infokarta/dynamicComponents';

class BasePluginEmitter extends React.Component {
  static propTypes = {
      pluginName: PropTypes.string,
      sendPluginName: PropTypes.func
  };


  componentDidMount() {
      console.log(this.props.pluginName);
      this.props.sendPluginName(this.props.pluginName);
  }

  render() {
      return (<div />);
  }
}

const PluginNameEmitter = connect(() => {
    return {};
}, {
    sendPluginName: acquireCurrentPluginName
})(BasePluginEmitter);
export default PluginNameEmitter;
