import axios from "../../libs/ajax";

const Api = {
    getPokojnici: function() {
        const url = 'http://localhost:8080/mapstore/rest/config/pokojnici';
        return axios.get(url)
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
            /* eslint-disable no-console */
                console.error(error);
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

        if (searchParameters.graveyard) {
            url += 'groblje=' + searchParameters.graveyard + '&';
        }

        if (searchParameters.yearOfDeathFrom) {
            url += 'pocgodinaukopa=' + searchParameters.yearOfDeathFrom + '&';
        } else {
            url += 'pocgodinaukopa=' + 1000 + '&';
        }

        if (searchParameters.yearOfDeathTo) {
            url += 'kongodinaukopa=' + searchParameters.yearOfDeathTo + '&';
        } else {
            url += 'kongodinaukopa=' + new Date().getFullYear() + '&';
        }

        if (searchParameters.page) {
            url += 'page=' + searchParameters.page;
        } else {
            url += 'page=1';
        }

        return axios.get(url)
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
            /* eslint-disable no-console */
                console.error(error);
            });
    },
    editPokojnik: function(pokojnik) {
        let url = 'http://localhost:8080/mapstore/rest/config/pokojnici';
        let header = { "Content-Type": "application/json;charset=UTF-8" };
        console.log(pokojnik);
        return axios.put(
            url,
            pokojnik,
            {
                headers: header
            })
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
            /* eslint-disable no-console */
                console.error(error);
            });

    }
};

export default Api;
