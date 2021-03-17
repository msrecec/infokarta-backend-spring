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
    },
    getPokojniciColumns: function() {
        let url = 'http://localhost:8080/mapstore/rest/config/pokojnici/columns';
        return axios.get(url)
            .then(function(response) {
                return Object.keys(response.data);
            }).catch(function(error) {
            /* eslint-disable no-console */
                console.error(error);
            });
    },
    insertPokojnik: function(itemToInsert) {
        let url = 'http://localhost:8080/mapstore/rest/config/pokojnici?';

        if (itemToInsert.graveyard) {
            url += 'groblje=' + itemToInsert.graveyard + '&';
            delete itemToInsert.graveyard;
        }

        if (itemToInsert.graveNumber) {
            url += 'rbr=' + itemToInsert.graveNumber;
            delete itemToInsert.graveNumber;
        }

        console.log('itemToInsert', itemToInsert);
        console.log('url', url);
        let header = { "Content-Type": "application/json;charset=UTF-8" };
        return axios.post(
            url,
            itemToInsert,
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
