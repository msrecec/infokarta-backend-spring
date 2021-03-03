import React, { useState } from 'react';
import PropTypes from 'prop-types';
import {Table, Button, Modal, Form} from 'react-bootstrap';

const style = {
    overflow: "scroll",
    maxHeight: "500px"
};

// let itemToEdit = null;

// funkcija za uljepsat headere pomoću regexa, mijenja underscore sa razmakon i uvecava prvo slovo
const beautifyHeader = (header) => {
    const regex = /([_])/g;
    const capitalisedHeader = header.charAt(0).toUpperCase() + header.slice(1);
    return capitalisedHeader.replaceAll(regex, ' ');
};

// TODO poslat item za edit u state > dodat kontrolu za modal u state > ucitat item u modal i upalit ga
// https://stackoverflow.com/a/35641680

// const loadModalData = (item) => {
// showModal();
// console.log(item);
// itemToEdit = item;
// };

class TableComponent extends React.Component {
  static propTypes = {
      items: PropTypes.array
  };

  render() {
      return (
          <div style={style}>
              <Table striped bordered hover size="sm">
                  <thead>
                      <tr>
                          <th key="#">#</th> {/* koristi se tako da edit botun ne pomakne sve udesno za jedno misto */}
                          {this.props.items[0] ?
                              Object.keys(this.props.items[0]).map((header) =>
                                  <th key={header}>{beautifyHeader(header)}</th>
                              ) : null}
                      </tr>
                  </thead>
                  <tbody>
                      {this.props.items.map((item) =>
                          <tr key={item.fid}>
                              <td><Button variant="Primary">Uredi</Button></td>
                              {Object.values(item).map((field) =>
                                  <td>{field}</td> // TODO nac neki smisleni key za postavit ovde, ispitat jel potrebno uopce
                              )}
                          </tr>
                      )}
                  </tbody>
              </Table>
              {/* <ModalComponent /> */}
          </div>
      );
  }
}

// const ModalComponent = () => {
//     const [show, setShow] = useState(false);

//     const closeModal = () => setShow(false);
//     const showModal = () => setShow(true);

//     return (
//         <Modal show={show} onHide={closeModal}>
//             <Modal.Header closeButton>
//                 <Modal.Title>Uređivanje stavke</Modal.Title>
//             </Modal.Header>
//             <Modal.Body>{itemToEdit.ime_i_prezime}</Modal.Body>
//             <Modal.Footer>
//                 <Button variant="secondary" onClick={closeModal}>
//                   Zatvori
//                 </Button>
//                 <Button variant="primary" onClick={closeModal}> {/* dodat edit funkciju */}
//                   Spremi promjene
//                 </Button>
//             </Modal.Footer>
//         </Modal>
//     );
// };

export default TableComponent;
