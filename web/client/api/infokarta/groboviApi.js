import axios from "../../libs/ajax";

const Api = {
    searchGraves: function(searchParameters, pageNumber) {
        let url = 'http://localhost:8080/mapstore/rest/config/grobovi?';
        console.log(searchParameters);

        for (const [key, value] of Object.entries(searchParameters)) {
            url += key + '=' + value + '&';
        }
        console.log(url);

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
    }
};

export default Api;
