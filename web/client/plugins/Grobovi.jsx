import React from 'react';
import { createPlugin } from '../utils/PluginsUtils';
// import { connect } from 'react-redux';

const style = {
    position: "absolute",
    background: "white",
    zIndex: 1000,
    top: 50,
    left: 50,
    padding: 10
};

const Component = () => <div style={style}>Hello</div>;

export default createPlugin('Grobovi', {
    component: Component
});
