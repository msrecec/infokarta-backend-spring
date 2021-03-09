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
        const url = 'http://localhost:8080/mapstore/rest/config/pokojnici?ime='+ searchParameters.name + '&prezime=' + searchParameters.surname + ' &page=' + searchParameters.pageNumber;
        return axios.get(url).then(function(response) {
            console.log(response.data);
            return response.data;
        }).catch(function(error) {
            console.log(error);
        });
    },
};

export default Api;
