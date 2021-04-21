package it.geosolutions.mapstore.model.pokojnik;

public class Pokojnik {

    public static class Builder {
        private Integer fid;
        private Integer fk;
        private String ime;
        private String prezime;
        private String ime_i_prezime;
        private String prezime_djevojacko;
        private String ime_oca;
        private String nadimak;
        private String oib;
        private String spol;
        private String datum_rodjenja;
        private String bracno_stanje;
        private String mjesto_stanovanja;
        private String adresa_stanovanja;
        private String ime_i_prezime_bracnog_druga;
        private String dob;
        private String uzrok_smrti;
        private String mjesto_smrti;
        private String datum_smrti;
        private String datum_kremiranja;
        private String datum_ukopa;
        private String oznaka_grobnice;
        private String groblje;
        private String naknadni_upisi_i_biljeske;
        private String godina_ukopa;
        private String usluga;
        private Integer racun;
        private String datum_usluge;

        public Builder(Integer fid) {this.fid = fid;}

        public Builder fk(Integer fk) {
            this.fk = fk;
            return this;
        }
        public Builder ime(String ime) {
            this.ime = ime;
            return this;
        }
        public Builder prezime(String prezime) {
            this.prezime = prezime;
            return this;
        }
        public Builder ime_i_prezime(String ime_i_prezime) {
            this.ime_i_prezime = ime_i_prezime;
            return this;
        }
        public Builder prezime_djevojacko(String prezime_djevojacko) {
            this.prezime_djevojacko = prezime_djevojacko;
            return this;
        }
        public Builder ime_oca(String ime_oca) {
            this.ime_oca = ime_oca;
            return this;
        }
        public Builder nadimak(String nadimak) {
            this.nadimak = nadimak;
            return this;
        }
        public Builder oib(String oib) {
            this.oib = oib;
            return this;
        }
        public Builder spol(String spol) {
            this.spol = spol;
            return this;
        }
        public Builder datum_rodjenja(String datum_rodjenja) {
            this.datum_rodjenja = datum_rodjenja;
            return this;
        }
        public Builder bracno_stanje(String bracno_stanje) {
            this.bracno_stanje = bracno_stanje;
            return this;
        }
        public Builder mjesto_stanovanja(String mjesto_stanovanja) {
            this.mjesto_stanovanja = mjesto_stanovanja;
            return this;
        }
        public Builder adresa_stanovanja(String adresa_stanovanja) {
            this.adresa_stanovanja = adresa_stanovanja;
            return this;
        }
        public Builder ime_i_prezime_bracnog_druga(String ime_i_prezime_bracnog_druga) {
            this.ime_i_prezime_bracnog_druga = ime_i_prezime_bracnog_druga;
            return this;
        }
        public Builder dob(String dob) {
            this.dob = dob;
            return this;
        }
        public Builder uzrok_smrti(String uzrok_smrti) {
            this.uzrok_smrti = uzrok_smrti;
            return this;
        }
        public Builder mjesto_smrti(String mjesto_smrti) {
            this.mjesto_smrti = mjesto_smrti;
            return this;
        }
        public Builder datum_smrti(String datum_smrti) {
            this.datum_smrti = datum_smrti;
            return this;
        }
        public Builder datum_kremiranja(String datum_kremiranja) {
            this.datum_kremiranja = datum_kremiranja;
            return this;
        }
        public Builder datum_ukopa(String datum_ukopa) {
            this.datum_ukopa = datum_ukopa;
            return this;
        }
        public Builder oznaka_grobnice(String oznaka_grobnice) {
            this.oznaka_grobnice = oznaka_grobnice;
            return this;
        }
        public Builder groblje(String groblje) {
            this.groblje = groblje;
            return this;
        }
        public Builder naknadni_upisi_i_biljeske(String naknadni_upisi_i_biljeske) {
            this.naknadni_upisi_i_biljeske = naknadni_upisi_i_biljeske;
            return this;
        }
        public Builder godina_ukopa(String godina_ukopa) {
            this.godina_ukopa = godina_ukopa;
            return this;
        }
        public Builder usluga(String usluga) {
            this.usluga = usluga;
            return this;
        }
        public Builder racun(Integer racun) {
            this.racun = racun;
            return this;
        }
        public Builder datum_usluge(String datum_usluge) {
            this.datum_usluge = datum_usluge;
            return this;
        }

        public Pokojnik build() {
            Pokojnik pokojnik = new Pokojnik();

            pokojnik.fid = this.fid;
            pokojnik.fk = this.fk;
            pokojnik.ime = this.ime;
            pokojnik.prezime = this.prezime;
            pokojnik.ime_i_prezime = this.ime_i_prezime;
            pokojnik.prezime_djevojacko = this.prezime_djevojacko;
            pokojnik.ime_oca = this.ime_oca;
            pokojnik.nadimak = this.nadimak;
            pokojnik.oib = this.oib;
            pokojnik.spol = this.spol;
            pokojnik.datum_rodjenja = this.datum_rodjenja;
            pokojnik.bracno_stanje = this.bracno_stanje;
            pokojnik.mjesto_stanovanja = this.mjesto_stanovanja;
            pokojnik.adresa_stanovanja = this.adresa_stanovanja;
            pokojnik.ime_i_prezime_bracnog_druga = this.ime_i_prezime_bracnog_druga;
            pokojnik.dob = this.dob;
            pokojnik.uzrok_smrti = this.uzrok_smrti;
            pokojnik.mjesto_smrti = this.mjesto_smrti;
            pokojnik.datum_smrti = this.datum_smrti;
            pokojnik.datum_kremiranja = this.datum_kremiranja;
            pokojnik.datum_ukopa = this.datum_ukopa;
            pokojnik.oznaka_grobnice = this.oznaka_grobnice;
            pokojnik.groblje = this.groblje;
            pokojnik.naknadni_upisi_i_biljeske = this.naknadni_upisi_i_biljeske;
            pokojnik.godina_ukopa = this.godina_ukopa;
            pokojnik.usluga = this.usluga;
            pokojnik.racun = this.racun;
            pokojnik.datum_usluge = this.datum_usluge;

            return pokojnik;

        }

    }

    private Integer fid;
    private Integer fk;
    private String ime;
    private String prezime;
    private String ime_i_prezime;
    private String prezime_djevojacko;
    private String ime_oca;
    private String nadimak;
    private String oib;
    private String spol;
    private String datum_rodjenja;
    private String bracno_stanje;
    private String mjesto_stanovanja;
    private String adresa_stanovanja;
    private String ime_i_prezime_bracnog_druga;
    private String dob;
    private String uzrok_smrti;
    private String mjesto_smrti;
    private String datum_smrti;
    private String datum_kremiranja;
    private String datum_ukopa;
    private String oznaka_grobnice;
    private String groblje;
    private String naknadni_upisi_i_biljeske;
    private String godina_ukopa;
    private String usluga;
    private Integer racun;
    private String datum_usluge;

    public Pokojnik() {}

    @Override
    public String toString() {
        return "Pokojnik{" +
            "fid=" + fid +
            ", fk=" + fk +
            ", ime='" + ime + '\'' +
            ", prezime='" + prezime + '\'' +
            ", ime_i_prezime='" + ime_i_prezime + '\'' +
            ", prezime_djevojacko='" + prezime_djevojacko + '\'' +
            ", ime_oca='" + ime_oca + '\'' +
            ", nadimak='" + nadimak + '\'' +
            ", oib='" + oib + '\'' +
            ", spol='" + spol + '\'' +
            ", datum_rodjenja='" + datum_rodjenja + '\'' +
            ", bracno_stanje='" + bracno_stanje + '\'' +
            ", mjesto_stanovanja='" + mjesto_stanovanja + '\'' +
            ", adresa_stanovanja='" + adresa_stanovanja + '\'' +
            ", ime_i_prezime_bracnog_druga='" + ime_i_prezime_bracnog_druga + '\'' +
            ", dob='" + dob + '\'' +
            ", uzrok_smrti='" + uzrok_smrti + '\'' +
            ", mjesto_smrti='" + mjesto_smrti + '\'' +
            ", datum_smrti='" + datum_smrti + '\'' +
            ", datum_kremiranja='" + datum_kremiranja + '\'' +
            ", datum_ukopa='" + datum_ukopa + '\'' +
            ", oznaka_grobnice='" + oznaka_grobnice + '\'' +
            ", groblje='" + groblje + '\'' +
            ", naknadni_upisi_i_biljeske='" + naknadni_upisi_i_biljeske + '\'' +
            ", godina_ukopa='" + godina_ukopa + '\'' +
            ", usluga='" + usluga + '\'' +
            ", racun=" + racun +
            ", datum_usluge='" + datum_usluge + '\'' +
            '}';
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFk() {
        return fk;
    }

    public void setFk(Integer fk) {
        this.fk = fk;
    }

    public String getIme_i_prezime() {
        return ime_i_prezime;
    }

    public void setIme_i_prezime(String ime_i_prezime) {
        this.ime_i_prezime = ime_i_prezime;
    }

    public String getPrezime_djevojacko() {
        return prezime_djevojacko;
    }

    public void setPrezime_djevojacko(String prezime_djevojacko) {
        this.prezime_djevojacko = prezime_djevojacko;
    }

    public String getIme_oca() {
        return ime_oca;
    }

    public void setIme_oca(String ime_oca) {
        this.ime_oca = ime_oca;
    }

    public String getNadimak() {
        return nadimak;
    }

    public void setNadimak(String nadimak) {
        this.nadimak = nadimak;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public String getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(String datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
    }

    public String getBracno_stanje() {
        return bracno_stanje;
    }

    public void setBracno_stanje(String bracno_stanje) {
        this.bracno_stanje = bracno_stanje;
    }

    public String getMjesto_stanovanja() {
        return mjesto_stanovanja;
    }

    public void setMjesto_stanovanja(String mjesto_stanovanja) {
        this.mjesto_stanovanja = mjesto_stanovanja;
    }

    public String getAdresa_stanovanja() {
        return adresa_stanovanja;
    }

    public void setAdresa_stanovanja(String adresa_stanovanja) {
        this.adresa_stanovanja = adresa_stanovanja;
    }

    public String getIme_i_prezime_bracnog_druga() {
        return ime_i_prezime_bracnog_druga;
    }

    public void setIme_i_prezime_bracnog_druga(String ime_i_prezime_bracnog_druga) {
        this.ime_i_prezime_bracnog_druga = ime_i_prezime_bracnog_druga;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUzrok_smrti() {
        return uzrok_smrti;
    }

    public void setUzrok_smrti(String uzrok_smrti) {
        this.uzrok_smrti = uzrok_smrti;
    }

    public String getMjesto_smrti() {
        return mjesto_smrti;
    }

    public void setMjesto_smrti(String mjesto_smrti) {
        this.mjesto_smrti = mjesto_smrti;
    }

    public String getDatum_smrti() {
        return datum_smrti;
    }

    public void setDatum_smrti(String datum_smrti) {
        this.datum_smrti = datum_smrti;
    }

    public String getDatum_kremiranja() {
        return datum_kremiranja;
    }

    public void setDatum_kremiranja(String datum_kremiranja) {
        this.datum_kremiranja = datum_kremiranja;
    }

    public String getDatum_ukopa() {
        return datum_ukopa;
    }

    public void setDatum_ukopa(String datum_ukopa) {
        this.datum_ukopa = datum_ukopa;
    }

    public String getOznaka_grobnice() {
        return oznaka_grobnice;
    }

    public void setOznaka_grobnice(String oznaka_grobnice) {
        this.oznaka_grobnice = oznaka_grobnice;
    }

    public String getGroblje() {
        return groblje;
    }

    public void setGroblje(String groblje) {
        this.groblje = groblje;
    }

    public String getNaknadni_upisi_i_biljeske() {
        return naknadni_upisi_i_biljeske;
    }

    public void setNaknadni_upisi_i_biljeske(String naknadni_upisi_i_biljeske) {
        this.naknadni_upisi_i_biljeske = naknadni_upisi_i_biljeske;
    }

    public String getGodina_ukopa() {
        return godina_ukopa;
    }

    public void setGodina_ukopa(String godina_ukopa) {
        this.godina_ukopa = godina_ukopa;
    }

    public String getUsluga() {
        return usluga;
    }

    public void setUsluga(String usluga) {
        this.usluga = usluga;
    }

    public Integer getRacun() {
        return racun;
    }

    public void setRacun(Integer racun) {
        this.racun = racun;
    }

    public String getDatum_usluge() {
        return datum_usluge;
    }

    public void setDatum_usluge(String datum_usluge) {
        this.datum_usluge = datum_usluge;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
