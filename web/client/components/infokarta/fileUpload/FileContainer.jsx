import React from 'react';
import PropTypes from 'prop-types';
import { Button } from 'react-bootstrap';

class FileContainer extends React.Component {
  static propTypes = {
      file: PropTypes.object
  };

  static defaultProps = {
      file: {}
  };

  render() {


      const styles = {
          mainContainer: {
              display: "flex",
              flexDirection: "row",
              border: "1px solid #dddddd",
              borderRadius: "16px",
              padding: "10px",
              marginBottom: "10px"
          },
          informationContainer: {
              display: "flex",
              flexDirection: "column",
              paddingLeft: "10px",
              justifyContent: "space-between"
          },
          fileInformation: {
              display: "flex",
              flexDirection: "column"
          },
          imageBorder: {
              border: "1px solid #dddddd",
              borderRadius: "8px"
          }
      };

      return (
          <div style={styles.mainContainer}>
              <img src={`http://localhost:8080/mapstore/rest/config/pokojnici/download/media/slika/${this.props.file.fid}?thumbnail=true`} style={styles.imageBorder} height="150px" width="150px"/>
              <div style={styles.informationContainer}>
                  <div style={styles.fileInformation}>
                      <span><b>Naziv dokumenta: </b>{this.props.file.naziv}</span>
                      <span><b>Vrsta dokumenta: </b>{this.props.file.tip}</span>
                      <span><b>Prenio/la: </b>{/* this.props.file.uploader */} </span>
                      {/* TODO dodat uploader kad se uploada file i prikazat ovde */}
                  </div>
                  <Button
                      bsStyle="success"
                      href={`http://localhost:8080/mapstore/rest/config/pokojnici/download/media/slika/${this.props.file.fid}`}
                      onclick={`http://localhost:8080/mapstore/rest/config/pokojnici/download/media/slika/${this.props.file.fid}`}
                      target="_blank"
                  >Otvori original</Button>
              </div>
          </div>
      );
  }
}

export default FileContainer;
