package it.geosolutions.mapstore.service.pokojnik;

import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobWithoutGeomDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikDTO;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;

public interface PokojnikService {
    public PokojnikAndGrobWithoutGeomDTO getPokojnikAndGrobWithoutGeomByPokojnikFid(Integer fid);
    public PokojnikAndGrobDTO getPokojnikAndGrobByPokojnikFid(Integer fid);

    default PokojnikDTO mapPokojnikToPokojnikDTO(Pokojnik pokojnik) {
        return new PokojnikDTO(pokojnik.getFid(), pokojnik.getFk(), pokojnik.getIme(), pokojnik.getPrezime(), pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(),
            pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(), pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(),
            pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(), pokojnik.getDob(), pokojnik.getUzrok_smrti(),
            pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(), pokojnik.getOznaka_grobnice(),
            pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(), pokojnik.getDatum_usluge());
    }
}
