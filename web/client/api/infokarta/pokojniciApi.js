import axios from '../../libs/ajax';

const Api = {
    getPokojnici: function() {
        const url = 'http://localhost:8080/mapstore/rest/config/pokojnici';
        return axios.get(url).then(function(response) {
            console.log(response.data);
            return response.data;
        }).catch(function(error) {
            console.log(error);
        });
    },
    searchPokojnici: function(searchParameters) {
        let url = 'http://localhost:8080/mapstore/rest/config/pokojnici?';
        if (searchParameters.name) {
            url += 'ime=' + searchParameters.name + '&';
        }
        if (searchParameters.surname) {
            url += 'prezime=' + searchParameters.surname + '&';
        }
        if (searchParameters.yearOfDeathFrom) {
            url += 'pocgodinaukopa=' + searchParameters.yearOfDeathFrom + '&';
        }
        if (searchParameters.yearOfDeathTo) {
            url += 'kongodinaukopa=' + searchParameters.yearOfDeathTo + '&';
        }
        if (searchParameters.pageNumber) {
            url += 'page=' + searchParameters.pageNumber;
        } else {
            url += 'page=1';
        }
        console.log('url: ', url);
        return axios.get(url).then(function(response) {
            console.log(response.data);
            return response.data;
        }).catch(function(error) {
            console.log(error);
        });
    }
};

export default Api;
