import React, { useEffect, useState } from 'react';
import { createPlugin } from '../utils/PluginsUtils';
import axios from '../libs/ajax';
// import Api from '../api/Pokojnici'
// import { connect } from 'react-redux';

const style = {
    position: "absolute",
    background: "white",
    zIndex: 1000,
    top: 50,
    left: 50,
    padding: 10
};


// const Component = () => <div style={style}>Hello</div>;
const Component = () => {
    const [pokojnici, setPokojnici] = useState("Ovo su pokojnici");
    const [mock, setMock] = useState("Mock");
    const getPokojnici = () => {
        const url = 'http://localhost:8080/mapstore/rest/config/pokojnici';
        axios.get(url).then(function(response) {
        console.log(response.data)
        setPokojnici(response.data)
        }).catch(function(error) {
            console.log(error)
            setPokojnici(error)
        })
    }
    const getMock = () => {
        const url = 'http://localhost:3000/posts/';
        axios.get(url).then(function(response) {
        console.log(response.data)
        setMock(`${response.data[0].author} ${response.data[0].id} ${response.data[0].title}`)
        }).catch(function(error) {
            console.log(error)
            setMock(error)
        })
    }
    useEffect(() => {
        // getPokojnici();
    });
    return (
        <div style={style}>
            <div>{pokojnici}</div>
            <button onClick={getPokojnici}>Dohvati pokojnike</button>
            <div>{mock}</div>
            <button onClick={getMock}>Dohvati mock</button>
        </div>
    );
}

export default createPlugin('Grobovi', {
    component: Component
});
