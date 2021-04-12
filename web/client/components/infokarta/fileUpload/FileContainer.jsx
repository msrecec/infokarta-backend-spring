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
              marginBottom: "10px",
              maxWidth: "500px"
          },
          informationContainer: {
              display: "flex",
              flexDirection: "column",
              paddingLeft: "10px",
              justifyContent: "space-between",
              maxWidth: "500px"
          },
          fileInformation: {
              display: "flex",
              flexDirection: "column",
              maxWidth: "300px"
          },
          imageBorder: {
              border: "1px solid #dddddd",
              borderRadius: "8px"
          },
          wrap: {
              wordWrap: "break-word"
          }
      };

      return (
          <div style={styles.mainContainer}>
              <img src={`http://localhost:8080/mapstore/rest/config/pokojnici/download/media/slika/${this.props.file.fid}?thumbnail=true`} style={styles.imageBorder} height="150px" width="150px"/>
              <div style={styles.informationContainer}>
                  <div style={styles.fileInformation}>
                      <span><b>Naziv dokumenta: </b><i style={styles.wrap}>{this.props.file.naziv}</i></span>
                      <span><b>Vrsta dokumenta: </b><i style={styles.wrap}>{this.props.file.tip}</i></span>
                      <span><b>Prenio/la: </b>{/* this.props.file.uploader */} </span>
                      {/* TODO dodat uploader kad se uploada file i prikazat ovde */}
                  </div>
                  <Button
                      bsStyle="success"
                      href={`http://localhost:8080/mapstore/rest/config/pokojnici/download/media/slika/${this.props.file.fid}`}
                      target="_blank"
                      style={{width: "300px"}}
                  >Otvori original</Button>
              </div>
          </div>
      );
  }
}

export default FileContainer;
