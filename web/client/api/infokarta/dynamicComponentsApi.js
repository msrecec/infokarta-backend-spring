import axios from "../../libs/ajax";
// import { decodeStringToLowercase } from "../../utils/infokarta/BeautifyUtil";

const header = { "Content-Type": "application/json;charset=UTF-8" };

const Api = {
    getItem: function(layerName, fid) {
        let url = `http://localhost:8080/mapstore/rest/config/${layerName}/${fid}?geom=false`;

        return axios.get(url)
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    },
    getColumns: function(layerName) {
        let url = `http://localhost:8080/mapstore/rest/config/${layerName}/columns`;

        return axios.get(url)
            .then(function(response) {
                // const temp = response.data.map((item) => decodeStringToLowercase(item));
                return response.data;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    },
    saveEdit: function(layerName, item) {
        let url = `http://localhost:8080/mapstore/rest/config/${layerName}`;

        return axios.put(
            url,
            item,
            {
                headers: header
            })
            .then(function(response) {
                return response.data;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    }
};

export default Api;
