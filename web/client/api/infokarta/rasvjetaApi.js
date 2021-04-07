import axios from '../../libs/ajax';

const LighingApi = {
    getLightingData: function() {
        const url = 'http://localhost:8080/mapstore/rest/config/rasvjeta';
        return axios.get(url)
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
                console.error(error);
            });
    }
};

export default LighingApi;
