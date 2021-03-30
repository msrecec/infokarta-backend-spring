package it.geosolutions.mapstore.model;

import java.util.Arrays;

public class SlikaMeta {
    private Integer fid;
    private String naziv;
    private String lokacija;
    private String tip;
    private Integer fk;

    @Override
    public String toString() {
        return "SlikaMeta{" +
            "fid=" + fid +
            ", naziv='" + naziv + '\'' +
            ", slika=" + lokacija +
            ", tip='" + tip + '\'' +
            ", fk=" + fk +
            '}';
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getFk() {
        return fk;
    }

    public void setFk(Integer fk) {
        this.fk = fk;
    }
}
