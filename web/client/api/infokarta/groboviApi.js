import axios from "../../libs/ajax";

const Api = {
    searchGraves: function(searchParameters, pageNumber) {
        let url = 'http://localhost:8080/mapstore/rest/config/grobovi?';

        for (const [key, value] of Object.entries(searchParameters)) {
            url += key + '=' + value + '&';
        }

        // if (pageNumber) {
        //     url += 'page=' + pageNumber;
        // } else {
        //     url += 'page=1';
        // }

        return axios.get(url)
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    },
    getGrobAndLinkedPokojnici: function(fid) {
        let grobUrl = 'http://localhost:8080/mapstore/rest/config/grobovi/' + fid + '?geom=false';
        let pokojniciUrl = 'http://localhost:8080/mapstore/rest/config/pokojnici?grobFid=' + fid;

        let containerObject = {};
        return axios.get(grobUrl)
            .then(function(response) {
                containerObject.grob = response.data;
                return axios.get(pokojniciUrl);
            }).then(function(response) {
                containerObject.pokojnici = response.data;
                return containerObject;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    }
};

export default Api;
