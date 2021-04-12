package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaDTO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaListDTO;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RasvjetaServiceImpl implements RasvjetaService {
    @Autowired
    private RasvjetaDAO rasvjetaDAO;

    @Override
    public Optional<Rasvjeta> findById(Integer id) {
        return rasvjetaDAO.findById(id);
    }

    @Override
    public RasvjetaListDTO findAll() {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.findAll();
        Integer count = rasvjetaDAO.findCount();
        return mapRasvjetaToRasvjetaListDTO(rasvjeta, count);
    }

    @Override
    public RasvjetaListDTO findPaginated(Integer page) {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.findPaginated(page);
        Integer count = rasvjetaDAO.findCount();
        return mapRasvjetaToRasvjetaListDTO(rasvjeta, count);
    }

    @Override
    public Optional<Rasvjeta> update(RasvjetaPutCommand rasvjetaCommand) {
        return rasvjetaDAO.update(mapPutCommandToRasvjeta(rasvjetaCommand));
    }

    private Rasvjeta mapPutCommandToRasvjeta(RasvjetaPutCommand command) {

        return new Rasvjeta
            .Builder(command.getIdHist())
            .materijal(command.getMaterijal())
            .stanje(command.getStanje())
            .source(command.getSource())
            .mjernoMjesto(command.getMjernoMjesto())
            .vod(command.getVod())
            .kategorija(command.getKategorija())
            .vrstaRasvjetnogMjesta(command.getVrstaRasvjetnogMjesta())
            .razdjelnik(command.getRazdjelnik())
            .trosilo(command.getTrosilo())
            .vrstaSvjetiljke(command.getVrstaSvjetiljke())
            .brojSvjetiljki(command.getBrojSvjetiljki())
            .grlo(command.getGrlo())
            .vrstaStakla(command.getVrstaStakla())
            .polozajKabela(command.getPolozajKabela())
            .godinaIzgradnje(command.getGodinaIzgradnje())
            .oznakaUgovora(command.getOznakaUgovora())
            .fid(command.getFid())
            .timeStart(command.getTimeStart())
            .timeEnd(command.getTimeEnd())
            .userRole(command.getUserRole())
            .build();
    }

    private RasvjetaListDTO mapRasvjetaToRasvjetaListDTO(List<Rasvjeta> rasvjeta, Integer count) {

        return new RasvjetaListDTO(rasvjeta, count);

    }

}
