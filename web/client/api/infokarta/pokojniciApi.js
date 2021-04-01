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
    searchPokojnici: function(searchParameters, pageNumber) {
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

        if (searchParameters.yearOfDeathFrom && searchParameters.yearOfDeathTo) {
            url += 'pocgodinaukopa=' + searchParameters.yearOfDeathFrom + '&';
            url += 'kongodinaukopa=' + searchParameters.yearOfDeathTo + '&';
        } else if (searchParameters.yearOfDeathFrom) {
            url += 'pocgodinaukopa=' + searchParameters.yearOfDeathFrom + '&';
            url += 'kongodinaukopa=' + new Date().getFullYear() + '&';
        } else if (searchParameters.yearOfDeathTo) {
            url += 'pocgodinaukopa=' + 0 + '&';
            url += 'kongodinaukopa=' + searchParameters.yearOfDeathTo + '&';
        }

        if (pageNumber) {
            url += 'page=' + pageNumber;
        } else {
            url += 'page=1';
        }

        let header = { "Content-Type": "application/json;charset=UTF-8" };
        return axios.get(
            url,
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
        let url = 'http://localhost:8080/mapstore/rest/config/pokojnici/columns?variables=true';
        return axios.get(url)
            .then(function(response) {
                return Object.keys(response.data);
            }).catch(function(error) {
            /* eslint-disable no-console */
                console.error(error);
            });
    },
    insertPokojnik: function(itemToInsert, graveId) {
        let url = 'http://localhost:8080/mapstore/rest/config/pokojnici?';

        if (itemToInsert.graveyard) {
            url += 'groblje=' + itemToInsert.graveyard + '&';
            delete itemToInsert.graveyard;
        }

        if (itemToInsert.graveNumber) {
            url += 'rbr=' + itemToInsert.graveNumber;
            delete itemToInsert.graveNumber;
        }

        if (graveId) {
            itemToInsert.fk = graveId;
        }

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
    },
    getGraveCoordinates: function(graveId) {
        let url = 'http://localhost:8080/mapstore/rest/config/grobovi?';
        url += 'fid=' + graveId + '&geom=true';

        let header = { "Content-Type": "application/json;charset=UTF-8" };
        if (graveId !== 0) {
            return axios.get(
                url,
                {
                    headers: header
                })
                .then(function(response) {
                    console.log(response.data.coordinates);
                    let coordinates = {
                        coordinates: {
                            x: response.data.coordinates[0],
                            y: response.data.coordinates[1]
                        },
                        zoom: 16,
                        crs: "EPSG:3765"
                    };
                    return coordinates;
                }).catch(function(error) {
                /* eslint-disable no-console */
                    console.error(error);
                });
        }
        console.log('ERROR: fid is not valid');
        return null;
    }
};

export default Api;
