package it.geosolutions.mapstore.dto.pokojnik;

import it.geosolutions.mapstore.dto.grobovi.GrobDTO;

public class PokojnikAndGrobDTO {
    PokojnikDTO pokojnik;
    GrobDTO grob;

    public PokojnikAndGrobDTO(PokojnikDTO pokojnikDTO, GrobDTO grobDTO) {
        this.pokojnik = pokojnikDTO;
        this.grob = grobDTO;
    }

    @Override
    public String toString() {
        return "PokojnikAndGrobDTO{" +
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

    public GrobDTO getGrob() {
        return grob;
    }

    public void setGrob(GrobDTO grob) {
        this.grob = grob;
    }
}
