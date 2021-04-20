import axios from '../../libs/ajax';

const LighingApi = {
    getLightingData: function(searchParameters, pageNumber) {
        let url = 'http://localhost:8080/mapstore/rest/config/rasvjeta?';
        if (searchParameters.material) {
            url += 'materijal=' + searchParameters.material + '&';
        }

        if (searchParameters.state) {
            url += 'stanje=' + searchParameters.state + '&';
        }

        if (searchParameters.socket) {
            url += 'grlo=' + searchParameters.socket + '&';
        }

        // Provjeri ispravnost urla

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
    },

    editLightingData: function(lighting) {
        let url = 'http://localhost:8080/mapstore/rest/config/rasvjeta';
        let header = { "Content-Type": "application/json;charset=UTF-8" };
        lighting.timeStart = null; // server response: 500 error ako nije null

        return axios.put(
            url,
            lighting,
            {
                headers: header
            })
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
                console.error(error);
            });
    },
    getLightingForContainerObject: function(fid) {

        let rasvjetaId = 'http://localhost:8080/mapstore/rest/config/rasvjeta/' + fid/* + '?geom=false' */;
        console.log("Reci mislavu da doda geom=false funkcionalnost na žarulje");
        return axios.get(rasvjetaId)
            .then(function(response) {
                delete response.data.geom;

                return response.data;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    }
};

export default LighingApi;
