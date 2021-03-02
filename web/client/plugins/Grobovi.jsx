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
    const [pokojnici2, setPokojnici2] = useState("Ovo su pokojnici2");
    const [mock, setMock] = useState("Mock");
    const getPokojnici = () => {
        // const url = 'http://localhost:8080/mapstore/rest/config/pokojnici';
        const url = 'http://localhost:8080/mapstore/rest/config/pokojnici/1';
        axios.get(url).then(function(response) {
        console.log(response.data)
        // setPokojnici(response.data[0].ime_i_prezime)
        }).catch(function(error) {
            console.log(error)
            setPokojnici(error)
        })
    }
    const getPokojnici2 = () => {
        const url = 'http://localhost:8080/mapstore/rest/config/pokojnici';
        axios.get(url).then(function(response) {
        console.dir(response.data[0].ime_i_prezime)
        setPokojnici2(response.data[0].ime_i_prezime)
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
        getPokojnici2();
    }, []);
    return (
        <div style={style}>
            <div>{pokojnici}</div>
            <button onClick={getPokojnici}>Dohvati pokojnike</button>
            <div>{pokojnici2}</div>
            <button onClick={getPokojnici}>Dohvati pokojnike2</button>
            <div>{mock}</div>
            <button onClick={getMock}>Dohvati mock</button>
        </div>
    );
}

export default createPlugin('Grobovi', {
    component: Component
});
