package it.geosolutions.mapstore.dto.pokojnik;

import it.geosolutions.mapstore.dto.grobovi.GrobDTOWithoutGeom;

public class PokojnikAndGrobWithoutGeomDTO {
    PokojnikDTO pokojnikDTO;
    GrobDTOWithoutGeom grobDTOWithoutGeom;

    public PokojnikAndGrobWithoutGeomDTO(PokojnikDTO pokojnikDTO, GrobDTOWithoutGeom grobDTOWithoutGeom) {
        this.pokojnikDTO = pokojnikDTO;
        this.grobDTOWithoutGeom = grobDTOWithoutGeom;
    }

    @Override
    public String toString() {
        return "PokojnikAndGrobWithoutGeomDTO{" +
            "pokojnikDTO=" + pokojnikDTO +
            ", grobDTOWithoutGeom=" + grobDTOWithoutGeom +
            '}';
    }

    public PokojnikDTO getPokojnikDTO() {
        return pokojnikDTO;
    }

    public void setPokojnikDTO(PokojnikDTO pokojnikDTO) {
        this.pokojnikDTO = pokojnikDTO;
    }

    public GrobDTOWithoutGeom getGrobDTOWithoutGeom() {
        return grobDTOWithoutGeom;
    }

    public void setGrobDTOWithoutGeom(GrobDTOWithoutGeom grobDTOWithoutGeom) {
        this.grobDTOWithoutGeom = grobDTOWithoutGeom;
    }
}
