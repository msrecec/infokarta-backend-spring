package it.geosolutions.mapstore.model;

import java.util.Arrays;

public class PokojnikSlika {
    private Integer fid;
    private String naziv;
    private byte[] slika;
    private String tip;
    private Integer fk;

    @Override
    public String toString() {
        return "PokojnikSlika{" +
            "fid=" + fid +
            ", naziv='" + naziv + '\'' +
            ", slika=" + Arrays.toString(slika) +
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

    public byte[] getSlika() {
        return slika;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
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
