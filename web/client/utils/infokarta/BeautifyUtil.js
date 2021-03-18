export const beautifyHeader = (header) => {
    const regex = /([_])/g;
    const capitalisedHeader = header[0].toUpperCase() + header.slice(1).toLowerCase();
    return capitalisedHeader.replaceAll(regex, ' ');
};
