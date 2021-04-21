import axios from '../../libs/ajax';

const header = { "Content-Type": "application/json;charset=UTF-8" };

const LighingApi = {
    getLightingData: function(searchParameters, pageNumber = 1) {
        let url = 'http://localhost:8080/mapstore/rest/config/rasvjeta?';

        for (const [key, value] of Object.entries(searchParameters)) {
            url += key + '=' + value + '&';
        }

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

    editLightingData: function(lighting) { // TODO provjera - mislin da on ne bi uopce smia dohvacat iz baze ako imaju popunjen timeStart field
        let url = 'http://localhost:8080/mapstore/rest/config/rasvjeta';
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
                /* eslint-disable no-console */
                console.error(error);
            });
    },
    getLightingForContainerObject: function(fid) {
        let rasvjetaId = 'http://localhost:8080/mapstore/rest/config/rasvjeta/' + fid;
        console.log("Reci mislavu da doda geom=false funkcionalnost na žarulje");
        return axios.get(rasvjetaId)
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    }
};

export default LighingApi;
