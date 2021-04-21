import axios from "../../libs/ajax";

let header = { "Content-Type": "application/json;charset=UTF-8" };

const Api = {
    searchPokojnici: function(searchParameters, pageNumber = 1) {
        let url = 'http://localhost:8080/mapstore/rest/config/pokojnici?';

        //
        if (searchParameters.ime) {
            url += 'ime=' + searchParameters.ime + '&';
        }
        if (searchParameters.prezime) {
            url += 'prezime=' + searchParameters.prezime + '&';
        }
        if (searchParameters.godina_ukopa) {
            url += 'pocgodinaukopa=' + searchParameters.godina_ukopa + '&';
            url += 'kongodinaukopa=' + searchParameters.godina_ukopa + '&';
        }
        if (searchParameters.groblje) {
            url += 'groblje=' + searchParameters.groblje + '&';
        }

        // for (const [key, value] of Object.entries(searchParameters)) {
        //     url += key + '=' + value + '&';
        // }

        url += 'page=' + pageNumber;

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
    // getPokojniciColumns: function() {
    //     let url = 'http://localhost:8080/mapstore/rest/config/pokojnici/columns?variables=true';
    //     return axios.get(url)
    //         .then(function(response) {
    //             console.log('get pokojnici columns !!!', response.data);
    //             return Object.keys(response.data);
    //         }).catch(function(error) {
    //         /* eslint-disable no-console */
    //             console.error(error);
    //         });
    // },
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

        console.log(itemToInsert, '!!! item u apiju');
        itemToInsert.ime_i_prezime = itemToInsert.ime + " " + itemToInsert.prezime;

        return axios.post(
            url,
            itemToInsert,
            {
                headers: header
            })
            .then(function(response) {
                return response;
            }).catch(function(error) {
            /* eslint-disable no-console */
                console.error(error);
            });
    },
    getGraveCoordinates: function(graveId) {
        let url = 'http://localhost:8080/mapstore/rest/config/grobovi?';
        url += 'fid=' + graveId;

        if (graveId > 0) {
            return axios.get(
                url,
                {
                    headers: header
                })
                .then(function(response) {
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
        console.error('ERROR: fid is not valid');
        return null;
    },
    getPokojnikAndLinkedGrob: function(pokojnikFid) { // TODO fix once api call is done !!!!!!!!!!!!
        let pokojniciUrl = `http://localhost:8080/mapstore/rest/config/pokojnici/${pokojnikFid}?grob=true&geom=true`;
        return axios.get(pokojniciUrl)
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    }
};

export default Api;
