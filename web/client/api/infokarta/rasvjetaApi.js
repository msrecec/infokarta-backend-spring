import axios from '../../libs/ajax';

const LighingApi = {
    getLightingData: function(pageNumber) {
        let url = 'http://localhost:8080/mapstore/rest/config/rasvjeta?';

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
                console.error(error);
            });
    }
};

export default LighingApi;
