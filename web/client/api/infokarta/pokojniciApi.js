import axios from "../../libs/ajax";

const Api = {
    searchPokojnici: function(searchParameters, pageNumber) {
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
        //

        // for (const [key, value] of Object.entries(searchParameters)) {
        //     url += key + '=' + value + '&';
        // }

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
                return response;
            }).catch(function(error) {
            /* eslint-disable no-console */
                console.error(error);
            });
    },
    getGraveCoordinates: function(graveId) {
        let url = 'http://localhost:8080/mapstore/rest/config/grobovi?';
        url += 'fid=' + graveId + '&geom=true';

        let header = { "Content-Type": "application/json;charset=UTF-8" };
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
    getPokojnikAndLinkedGrob: function(pokojnikFid, grobFid) {
        let pokojniciUrl = 'http://localhost:8080/mapstore/rest/config/pokojnici/' + pokojnikFid;
        let grobUrl = 'http://localhost:8080/mapstore/rest/config/grobovi/' + grobFid + '?geom=false';

        // let containerObject = {};
        // return axios.get(pokojniciUrl)
        //     .then(function(response) {
        //         containerObject.pokojnik = response.data;
        //         return axios.get(grobUrl);
        //     }).then(function(response) {
        //         containerObject.grob = response.data;
        //         return containerObject;
        //     }).catch(function(error) {
        //         /* eslint-disable no-console */
        //         console.error(error);
        //     });
        let containerObject = {};
        return axios.get(pokojniciUrl)
            .then(function(response) {
                containerObject.pokojnik = response.data;
                //     return axios.get(grobUrl);
                // }).then(function(response) {
                //     containerObject.grob = response.data;
                return containerObject;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    }
};

export default Api;
