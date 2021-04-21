package it.geosolutions.mapstore.dto.pokojnik;

import it.geosolutions.mapstore.dto.grobovi.GrobDTOWithoutGeom;

public class PokojnikAndGrobWithoutGeomDTO {
    PokojnikDTO pokojnik;
    GrobDTOWithoutGeom grob;

    public PokojnikAndGrobWithoutGeomDTO(PokojnikDTO pokojnikDTO, GrobDTOWithoutGeom grobDTOWithoutGeom) {
        this.pokojnik = pokojnikDTO;
        this.grob = grobDTOWithoutGeom;
    }

    @Override
    public String toString() {
        return "PokojnikAndGrobWithoutGeomDTO{" +
            "pokojnik=" + pokojnik +
            ", grob=" + grob +
            '}';
    }

    public PokojnikDTO getPokojnik() {
        return pokojnik;
    }

    public void setPokojnik(PokojnikDTO pokojnik) {
        this.pokojnik = pokojnik;
    }

    public GrobDTOWithoutGeom getGrob() {
        return grob;
    }

    public void setGrob(GrobDTOWithoutGeom grob) {
        this.grob = grob;
    }
}
