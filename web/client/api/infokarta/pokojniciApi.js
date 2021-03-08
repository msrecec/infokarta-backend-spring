import axios from "../../libs/ajax";

const Api = {
    getPokojnici: function() {
        const url = "http://localhost:8080/mapstore/rest/config/pokojnici";
        return axios
            .get(url)
            .then(function(response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function(error) {
                console.log(error);
            });
    },

    getPokojniciByPage: function(pageNumber) {
        console.log('api: ', pageNumber);
        const url = "http://localhost:8080/mapstore/rest/config/pokojnici/page/" + pageNumber;
        console.log('api: ', url);
        return axios
            .get(url)
            .then(function(response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function(error) {
                console.log(error);
            });
    }
};

export default Api;
