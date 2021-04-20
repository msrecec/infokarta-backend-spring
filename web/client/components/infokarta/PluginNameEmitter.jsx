import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { acquireCurrentPluginName } from '../../actions/infokarta/dynamicComponents';

class BasePluginEmitter extends React.Component {
  static propTypes = {
      pluginName: PropTypes.string,
      sendPluginName: PropTypes.func
  };

  componentDidMount() {
      this.props.sendPluginName(this.props.pluginName);
  }

  componentDidUpdate(prevProps) {
      if (prevProps.pluginName !== this.props.pluginName) {
          this.props.sendPluginName(this.props.pluginName);
      }
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
