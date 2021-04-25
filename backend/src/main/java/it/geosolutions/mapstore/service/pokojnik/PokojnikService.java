package it.geosolutions.mapstore.service.pokojnik;

import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikDTO;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;
import it.geosolutions.mapstore.utils.search.SearchEntity;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PokojnikService {
    EntityListDTO findJoinedSearch(Map<String, Object> params, SearchEntity entity,
                                   Integer page, List<SearchEntity> orderedEntities);

    PokojnikAndGrobDTO getPokojnikAndGrobWithoutGeomByPokojnikFid(Integer fid);
    PokojnikAndGrobDTO getPokojnikAndGrobByPokojnikFid(Integer fid);

    default PokojnikDTO mapPokojnikToPokojnikDTO(Pokojnik pokojnik) {
        return new PokojnikDTO(pokojnik.getFid(), pokojnik.getFk(), pokojnik.getIme(), pokojnik.getPrezime(), pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(),
            pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(), pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(),
            pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(), pokojnik.getDob(), pokojnik.getUzrok_smrti(),
            pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(), pokojnik.getOznaka_grobnice(),
            pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(), pokojnik.getDatum_usluge());
    }

    default List<PokojnikDTO> mapPokojnikListToPokojnikDTOList(List<Pokojnik> pokojnici) {
        List<PokojnikDTO> pokojniciDTO = new ArrayList<>();

        for(Pokojnik p : pokojnici) {
            pokojniciDTO.add(mapPokojnikToPokojnikDTO(p));
        }

        return pokojniciDTO;
    }

}
