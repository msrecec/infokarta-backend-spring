import axios from "../../libs/ajax";

const fileManagementApi = {
    uploadFile: function(entityName, documentType, file, entityFid) {
        const url = `http://localhost:8080/mapstore/rest/config/${entityName}/upload/media/${documentType}?entityFid=${entityFid}`;

        const header = {
            "Content-Type": "multipart/form-data;"
        };

        let formData = new FormData();
        formData.append("file", file);

        return axios({
            method: "post",
            url: url,
            data: formData,
            timeout: 8000,
            headers: header
        }).then(function(response) {
            return response.status;
        }).catch(function(error) {
            /* eslint-disable no-console */
            console.error('uploadFile: ', error);
            return error.status;
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
                return response;
            }).catch(function(error) {
                /* eslint-disable no-console */
                console.error('getFile: ', error);
            });
    },
    getMeta: function(entityName, documentType, fid) {
        if (entityName && documentType && fid) {
            const url = `http://localhost:8080/mapstore/rest/config/${entityName}/download/media/${documentType}/meta/${fid}`;
            return axios.get(url)
                .then(function(response) {
                    return response;
                }).catch(function(error) {
                    /* eslint-disable no-console */
                    console.error('getMeta: ', error);
                });
        }
        return null;
    },
    getMetaByEntityFid: function(entityName, documentType, entityFid) {
        console.log(entityName, documentType, entityFid);
        if (entityName && documentType && entityFid) {
            const url = `http://localhost:8080/mapstore/rest/config/${entityName}/download/media/${documentType}/meta?entityFid=${entityFid}`;
            return axios.get(url)
                .then(function(response) {
                    return response;
                }).catch(function(error) {
                    /* eslint-disable no-console */
                    console.error('getMetaByEntityFid: ', error);
                });
        }
        return null;

    }
};

export default fileManagementApi;
