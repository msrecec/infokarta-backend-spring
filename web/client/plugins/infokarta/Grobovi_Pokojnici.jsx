// import React from "react";
// import { connect } from "react-redux";
// import { get } from "lodash";

// import Message from "../../components/I18N/Message";
// import { Glyphicon, Button } from "react-bootstrap";

// // actions
// import {
//     setSearchParametersForGraves,
//     resetSearchParametersForGraves,
//     zoomToGraveFromGraves
// } from "../../actions/infokarta/graves";

// import {
//     setSearchParametersForDeceased,
//     resetSearchParametersForDeceased,
//     // editDeceased,
//     // insertDeceased,
//     zoomToGraveFromDeceased,
//     setPageForDeceased
// } from "../../actions/infokarta/deceased";

// import {
//     enableGravePickMode
// } from "../../actions/infokarta/gravePickerTool";

// import {
//     getDataForDetailsView,
//     clearDetailsAndDocsView
// } from "../../actions/infokarta/detailsAndDocuments";

// import {
//     getItemForEditFromDatabase
// } from "../../actions/infokarta/dynamicComponents";

// // utils
// import { createPlugin } from "../../utils/PluginsUtils";
// import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

// // reducers
// import graves from "../../reducers/infokarta/graves";
// import deceased from "../../reducers/infokarta/deceased";
// import dynamicComponents from "../../reducers/infokarta/dynamicComponents";
// import gravePickerTool from "../../reducers/infokarta/gravePickerTool";
// import detailsAndDocuments from "../../reducers/infokarta/detailsAndDocuments";
// import fileManagement from "../../reducers/infokarta/fileManagement";

// // epics
// import { deceasedAndGravesEpic } from "../../epics/infokarta/combinedEpics";

// // components
// import TableComponent from "../../components/infokarta/Table";
// import EditModal from "../../components/infokarta/EditModal";
// import InsertModal from "../../components/infokarta/InsertModal";
// import InsertConfirmationModal from "../../components/infokarta/InsertConfirmationModal";
// import SearchComponent from "../../components/infokarta/SearchForm";
// import PaginationComponent from "../../components/infokarta/Pagination";
// import GravePickerModal from '../../components/infokarta/pokojnici/GravePickerModal';
// import PokojniciDetails from '../../components/infokarta/PokojniciDetails';
// import GroboviDetails from "../../components/infokarta/GroboviDetails";
// import PluginNameEmitter from '../../components/infokarta/PluginNameEmitter';

// const fieldsToIncludeGrobovi = ["grobnica", "redniBroj", "groblje"];
// const fieldsToIncludePokojnici = ["ime", "prezime", "datum_rodjenja", "datum_smrti"];
// const fieldsToExcludeInsertPokojnici = ["fid", "fk", "ime_i_prezime", "IME I PREZIME", "groblje", "oznaka_grobnice"];
// const readOnlyFields = ["fid", "fk", "groblje", "oznaka_grobnice"];
// const searchFormDataGrobovi = [
//     {
//         label: "Groblje",
//         type: "select",
//         value: "groblje",
//         selectValues: ["", "Primošten", "Prhovo", "Široke", "Kruševo"]
//     }
// ];
// const searchFormDataPokojnici = [
//     {
//         label: "Ime",
//         type: "text",
//         value: "ime"
//     },
//     {
//         label: "Prezime",
//         type: "text",
//         value: "prezime"
//     },
//     {
//         label: "Godina smrti",
//         type: "text",
//         value: "godina_ukopa"
//     },
//     {
//         label: "Groblje",
//         type: "select",
//         value: "groblje",
//         selectValues: ["", "Primošten", "Prhovo", "Široke", "Kruševo"]
//     }
// ];

// let deceasedMode = true;

// const Grobovi_Pokojnici = ({
//     gravesData,
//     gravesPage,
//     gravesTotalNumber,

//     deceasedData,
//     deceasedPage,
//     deceasedTotalNumber,

//     showDetails,
//     detailViewItems,

//     setGravesSearchParameters = () => {},
//     resetGravesSearchParameters = () => {},

//     sendGravesPageNumber = () => {},
//     setupEditModal = () => {},
//     sendEditedData = () => {},
//     // sendNewData = () => {},
//     // setupInsertModal = () => {},
//     sendZoomData = () => {},
//     // startChooseMode = () => {},
//     sendDataToDetailsView = () => {},
//     closeDetailsView = () => {}
// }) => {

//     const search = (<SearchComponent
//         buildData={deceasedMode ? searchFormDataPokojnici : searchFormDataGrobovi}
//         search={setGravesSearchParameters}
//         // openInsertForm={setupInsertModal}
//         resetSearchParameters={resetGravesSearchParameters}
//         disableInsert
//     />);

//     const table = (<TableComponent
//         items={deceasedMode ? deceasedData : gravesData}
//         // fieldsToInclude={fieldsToIncludeGrobovi ? fieldsToIncludeGrobovi : []}
//         sendDataToDetailsView={sendDataToDetailsView}
//         showDetails={showDetails}
//         zoomToItem={sendZoomData}
//     />);

//     const pagination = (<PaginationComponent
//         totalNumber={totalNumber}
//         setPageNumber={sendGravesPageNumber}
//         active={typeof page === "number" ? page : 1}
//     />);

//     const editModal = (<EditModal
//         // fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
//         readOnlyFields={readOnlyFields ? readOnlyFields : []}
//         editItem={sendEditedData}
//     />);

//     const detailsAndDocs = (<GroboviDetails
//         items={detailViewItems}
//         showDetails={showDetails}
//         closeDetailsView={closeDetailsView}
//         editItem={setupEditModal}
//         // fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
//     />);

//     const pluginNameEmitter = (<PluginNameEmitter
//         pluginName={deceasedMode ? "pokojnici" : "grobovi"}
//     />);

//     const styles = {
//         showGraveDetailsStyle: {
//             height: "0px",
//             display: "none"
//         },
//         hideGraveDetailsStyle: {
//             height: "600px",
//             transition: "all .2s linear"
//         },
//         showDeceasedDetailsStyle: {
//             height: "150px",
//             transition: "all .2s linear"
//         },
//         hideDeceasedDetailsStyle: {
//             height: "600px",
//             transition: "all .2s linear"
//         }
//     };

//     return (
//         <div style={{"padding": "10px"}}>
//             {pluginNameEmitter}
//             <div style={showDetails ? styles.showGraveDetailsStyle : styles.hideGraveDetailsStyle}>
//                 {search}
//                 {table}
//                 {pagination}
//             </div>
//             {detailsAndDocs}
//             {editModal}
//             {/* {insertModal}
//             {insertConfirmationModal} */}
//         </div>
//     );
// };

// export default createPlugin("Grobovi_Pokojnici", {
//     component: connect((state) => ({
//         gravesData: get(state, "graves.data"),
//         gravesPage: get(state, "graves.pageNumber"),
//         gravesTotalNumber: get(state, "graves.totalNumber"),

//         deceasedData: get(state, "deceased.data"),
//         deceasedPage: get(state, "deceased.pageNumber"),
//         deceasedTotalNumber: get(state, "deceased.totalNumber"),
//         chosenGrave: get(state, "gravePickerTool.graveData"),

//         showDetails: get(state, "detailsAndDocuments.showDetails"),
//         detailViewItems: get(state, "detailsAndDocuments.items"),
//         gravesEditModalShow: get(state, "dynamicComponents.modals.groboviEdit")
//     }), {
//         setGravesSearchParameters: setSearchParametersForGraves,
//         resetSearchParameters: resetSearchParametersForGraves,
//         // sendPageNumber: setPageForDeceased,
//         setupEditModal: getItemForEditFromDatabase,
//         // sendEditedData: editDeceased,
//         sendZoomData: zoomToGraveFromGraves,
//         sendDataToDetailsView: getDataForDetailsView,
//         closeDetailsView: clearDetailsAndDocsView
//     })(Grobovi_Pokojnici),
//     containers: {
//         DrawerMenu: {
//             name: "Grobovi_Pokojnici",
//             position: 5,
//             text: <Message msgId="grobovi_pokojnici"/>,
//             icon: <Glyphicon glyph="user"/>,
//             action: () => ({type: ""}),
//             priority: 1,
//             doNotHide: true
//         }
//     },
//     epics: deceasedAndGravesEpic,
//     reducers: {
//         deceased,
//         graves,
//         gravePickerTool,
//         dynamicComponents,
//         detailsAndDocuments,
//         fileManagement
//     }
// });
