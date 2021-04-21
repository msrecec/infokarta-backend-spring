package it.geosolutions.mapstore.model.rasvjeta;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.geosolutions.mapstore.serializers.PGgeometrySerializer;
import org.postgis.PGgeometry;

import java.time.LocalDateTime;

public class Rasvjeta {

    public static class Builder {
        @JsonSerialize(using = PGgeometrySerializer.class)
        private PGgeometry geom;
        private Integer fid;
        private String materijal;
        private String stanje;
        private String source;
        private String mjernoMjesto;
        private String vod;
        private String kategorija;
        private String vrstaRasvjetnogMjesta;
        private String razdjelnik;
        private String trosilo;
        private String vrstaSvjetiljke;
        private String brojSvjetiljki;
        private String grlo;
        private String vrstaStakla;
        private String polozajKabela;
        private String godinaIzgradnje;
        private String oznakaUgovora;
        private Integer idHist;
        private LocalDateTime timeStart;
        private LocalDateTime timeEnd;
        private String userRole;

        public Builder(Integer idHist) { this.idHist = idHist; }

        public Builder geom(PGgeometry geom) {
            this.geom = geom;
            return this;
        }

        public Builder materijal(String materijal) {
            this.materijal = materijal;
            return this;
        }
        public Builder stanje(String stanje) {
            this.stanje = stanje;
            return this;
        }
        public Builder source(String source) {
            this.source = source;
            return this;
        }
        public Builder mjernoMjesto(String mjernoMjesto) {
            this.mjernoMjesto = mjernoMjesto;
            return this;
        }
        public Builder vod(String vod) {
            this.vod = vod;
            return this;
        }
        public Builder kategorija(String kategorija) {
            this.kategorija = kategorija;
            return this;
        }
        public Builder vrstaRasvjetnogMjesta(String vrstaRasvjetnogMjesta) {
            this.vrstaRasvjetnogMjesta = vrstaRasvjetnogMjesta;
            return this;
        }
        public Builder razdjelnik(String razdjelnik) {
            this.razdjelnik = razdjelnik;
            return this;
        }
        public Builder trosilo(String trosilo) {
            this.trosilo = trosilo;
            return this;
        }
        public Builder vrstaSvjetiljke(String vrstaSvjetiljke) {
            this.vrstaSvjetiljke = vrstaSvjetiljke;
            return this;
        }
        public Builder brojSvjetiljki(String brojSvjetiljki) {
            this.brojSvjetiljki = brojSvjetiljki;
            return this;
        }
        public Builder grlo(String grlo) {
            this.grlo = grlo;
            return this;
        }
        public Builder vrstaStakla(String vrstaStakla) {
            this.vrstaStakla = vrstaStakla;
            return this;
        }
        public Builder polozajKabela(String polozajKabela) {
            this.polozajKabela = polozajKabela;
            return this;
        }
        public Builder godinaIzgradnje(String godinaIzgradnje) {
            this.godinaIzgradnje = godinaIzgradnje;
            return this;
        }
        public Builder oznakaUgovora(String oznakaUgovora) {
            this.oznakaUgovora = oznakaUgovora;
            return this;
        }
        public Builder fid(Integer fid) {
            this.fid = fid;
            return this;
        }
        public Builder timeStart(LocalDateTime timeStart) {
            this.timeStart = timeStart;
            return this;
        }
        public Builder timeEnd(LocalDateTime timeEnd) {
            this.timeEnd = timeEnd;
            return this;
        }
        public Builder userRole(String userRole) {
            this.userRole = userRole;
            return this;
        }

        public Rasvjeta build() {
            Rasvjeta rasvjeta = new Rasvjeta();

            rasvjeta.geom = this.geom;
            rasvjeta.fid = this.fid;
            rasvjeta.materijal = this.materijal;
            rasvjeta.stanje = this.stanje;
            rasvjeta.source = this.source;
            rasvjeta.mjernoMjesto = this.mjernoMjesto;
            rasvjeta.vod = this.vod;
            rasvjeta.kategorija = this.kategorija;
            rasvjeta.vrstaRasvjetnogMjesta = this.vrstaRasvjetnogMjesta;
            rasvjeta.razdjelnik = this.razdjelnik;
            rasvjeta.trosilo = this.trosilo;
            rasvjeta.vrstaSvjetiljke = this.vrstaSvjetiljke;
            rasvjeta.brojSvjetiljki = this.brojSvjetiljki;
            rasvjeta.grlo = this.grlo;
            rasvjeta.vrstaStakla = this.vrstaStakla;
            rasvjeta.polozajKabela = this.polozajKabela;
            rasvjeta.godinaIzgradnje = this.godinaIzgradnje;
            rasvjeta.oznakaUgovora = this.oznakaUgovora;
            rasvjeta.idHist = this.idHist;
            rasvjeta.timeStart = this.timeStart;
            rasvjeta.timeEnd = this.timeEnd;
            rasvjeta.userRole = this.userRole;

            return rasvjeta;

        }

    }

    @JsonSerialize(using = PGgeometrySerializer.class)
    private PGgeometry geom;
    private Integer fid;
    private String materijal;
    private String stanje;
    private String source;
    private String mjernoMjesto;
    private String vod;
    private String kategorija;
    private String vrstaRasvjetnogMjesta;
    private String razdjelnik;
    private String trosilo;
    private String vrstaSvjetiljke;
    private String brojSvjetiljki;
    private String grlo;
    private String vrstaStakla;
    private String polozajKabela;
    private String godinaIzgradnje;
    private String oznakaUgovora;
    private Integer idHist;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private String userRole;

    public Rasvjeta() {

    }

    @Override
    public String toString() {
        return "Rasvjeta{" +
            "geom=" + geom +
            ", fid=" + fid +
            ", materijal='" + materijal + '\'' +
            ", stanje='" + stanje + '\'' +
            ", source='" + source + '\'' +
            ", mjernoMjesto='" + mjernoMjesto + '\'' +
            ", vod='" + vod + '\'' +
            ", kategorija='" + kategorija + '\'' +
            ", vrstaRasvjetnogMjesta='" + vrstaRasvjetnogMjesta + '\'' +
            ", razdjelnik='" + razdjelnik + '\'' +
            ", trosilo='" + trosilo + '\'' +
            ", vrstaSvjetiljke='" + vrstaSvjetiljke + '\'' +
            ", brojSvjetiljki='" + brojSvjetiljki + '\'' +
            ", grlo='" + grlo + '\'' +
            ", vrstaStakla='" + vrstaStakla + '\'' +
            ", polozajKabela='" + polozajKabela + '\'' +
            ", godinaIzgradnje='" + godinaIzgradnje + '\'' +
            ", oznakaUgovora='" + oznakaUgovora + '\'' +
            ", idHist=" + idHist +
            ", timeStart=" + timeStart +
            ", timeEnd=" + timeEnd +
            ", userRole='" + userRole + '\'' +
            '}';
    }

    public PGgeometry getGeom() {
        return geom;
    }

    public void setGeom(PGgeometry geom) {
        this.geom = geom;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getMaterijal() {
        return materijal;
    }

    public void setMaterijal(String materijal) {
        this.materijal = materijal;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMjernoMjesto() {
        return mjernoMjesto;
    }

    public void setMjernoMjesto(String mjernoMjesto) {
        this.mjernoMjesto = mjernoMjesto;
    }

    public String getVod() {
        return vod;
    }

    public void setVod(String vod) {
        this.vod = vod;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getVrstaRasvjetnogMjesta() {
        return vrstaRasvjetnogMjesta;
    }

    public void setVrstaRasvjetnogMjesta(String vrstaRasvjetnogMjesta) {
        this.vrstaRasvjetnogMjesta = vrstaRasvjetnogMjesta;
    }

    public String getRazdjelnik() {
        return razdjelnik;
    }

    public void setRazdjelnik(String razdjelnik) {
        this.razdjelnik = razdjelnik;
    }

    public String getTrosilo() {
        return trosilo;
    }

    public void setTrosilo(String trosilo) {
        this.trosilo = trosilo;
    }

    public String getVrstaSvjetiljke() {
        return vrstaSvjetiljke;
    }

    public void setVrstaSvjetiljke(String vrstaSvjetiljke) {
        this.vrstaSvjetiljke = vrstaSvjetiljke;
    }

    public String getBrojSvjetiljki() {
        return brojSvjetiljki;
    }

    public void setBrojSvjetiljki(String brojSvjetiljki) {
        this.brojSvjetiljki = brojSvjetiljki;
    }

    public String getGrlo() {
        return grlo;
    }

    public void setGrlo(String grlo) {
        this.grlo = grlo;
    }

    public String getVrstaStakla() {
        return vrstaStakla;
    }

    public void setVrstaStakla(String vrstaStakla) {
        this.vrstaStakla = vrstaStakla;
    }

    public String getPolozajKabela() {
        return polozajKabela;
    }

    public void setPolozajKabela(String polozajKabela) {
        this.polozajKabela = polozajKabela;
    }

    public String getGodinaIzgradnje() {
        return godinaIzgradnje;
    }

    public void setGodinaIzgradnje(String godinaIzgradnje) {
        this.godinaIzgradnje = godinaIzgradnje;
    }

    public String getOznakaUgovora() {
        return oznakaUgovora;
    }

    public void setOznakaUgovora(String oznakaUgovora) {
        this.oznakaUgovora = oznakaUgovora;
    }

    public Integer getIdHist() {
        return idHist;
    }

    public void setIdHist(Integer idHist) {
        this.idHist = idHist;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
