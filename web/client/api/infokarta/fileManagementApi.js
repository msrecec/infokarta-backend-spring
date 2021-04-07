import axios from "../../libs/ajax";

const fileManagementApi = {
    uploadFile: function(entityName, documentType, fileName, file, entityFid) {
        const url = `http://localhost:8080/mapstore/rest/config/${entityName}/upload/media/${documentType}?entityFid=${entityFid}`;

        const body = {
            name: fileName,
            file: file
        };

        const header = {
            "Content-Type": "multipart/form-data;"
        };

        return axios.post(
            url,
            body,
            {
                headers: header
            })
            .then(function(response) {
                console.log(response.data);
                return response;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    },
    getFile: function(entityName, documentType, fid, thumbnail) {
        let url = '';
        if (thumbnail) {
            url = `http://localhost:8080/mapstore/rest/config/${entityName}/download/media/${documentType}/${fid}?thumbnail=${thumbnail}`;
        } else {
            url = `http://localhost:8080/mapstore/rest/config/${entityName}/download/media/${documentType}/${fid}`;
        }
        return axios.get(url)
            .then(function(response) {
                console.log(response.data);
                return response;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    },
    getMeta: function(entityName, documentType, fid) {
        const url = `http://localhost:8080/mapstore/rest/config/${entityName}/download/media/${documentType}/meta/${fid}`;
        return axios.get(url)
            .then(function(response) {
                console.log(response.data);
                return response;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    },
    getMetaByEntityFid: function(entityName, documentType, entityFid) {
        console.log('!!!', entityName, documentType, entityFid);
        const url = `http://localhost:8080/mapstore/rest/config/${entityName}/download/media/${documentType}/meta?entityFid=${entityFid}`;
        return axios.get(url)
            .then(function(response) {
                return response;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error(error);
            });
    }
};

export default fileManagementApi;
