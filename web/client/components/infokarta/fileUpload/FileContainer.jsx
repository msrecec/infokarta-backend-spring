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
      const containerStyle = {
          display: "flex",
          flexDirection: "row",
          border: "1px solid #dddddd",
          borderRadius: "16px",
          padding: "10px",
          marginBottom: "10px"
      };

      const informationContainerStyle = {
          display: "flex",
          flexDirection: "column",
          paddingLeft: "10px",
          justifyContent: "space-between"
      };

      const fileInformationStyle = {
          display: "flex",
          flexDirection: "column"
      };
      console.log(this.props.file);

      return (
          <div style={containerStyle}>
              <img src={`http://localhost:8080/mapstore/rest/config/pokojnici/download/media/slika/${this.props.file.fid}?thumbnail=true`}  height="120px" width="auto"/>
              <div style={informationContainerStyle}>
                  <div style={fileInformationStyle}>
                      <span>Naziv dokumenta: {this.props.file.naziv}</span>
                      <span>Vrsta dokumenta: {this.props.file.tip}</span>
                      <span>Prenio/la: {/* this.props.file.uploader */} </span>
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
