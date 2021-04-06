import React from 'react';
import PropTypes from 'prop-types';
import { Button } from 'react-bootstrap';

class FileComponent extends React.Component {
  static propTypes = {
      file: PropTypes.object
  };

  static defaultProps = {
      file: {}
  };

  render() {
      const containerStyle = {
          display: "flex",
          flexDirection: "row"
      };

      const fileInformationStyle = {
          display: "flex",
          flexDirection: "column"
      };

      return (
          <div style={containerStyle}>
              <img src="http://localhost:8080/mapstore/rest/config/pokojnici/download/media/slika/1?thumbnail=true"  height="200px" width="auto"/>
              <div style={fileInformationStyle}>
                  <span>Naziv dokumenta: {this.props.file.naziv}</span>
                  <span>Vrsta dokumenta: {this.props.file.tip}</span>
                  <span>Prenio/la: {/* this.props.file.uploader */} </span>
                  {/* TODO dodat uploader kad se uploada file i prikazat ovde */}
                  <Button bsStyle="success" onclick="http://localhost:8080/mapstore/rest/config/pokojnici/download/media/slika/1">Preuzmi original</Button>
              </div>
          </div>
      );
  }
}

export default FileComponent;
