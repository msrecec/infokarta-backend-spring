package it.geosolutions.mapstore.pojo;

public class Groblje {
    private Integer fid;
    private String naziv;

    @Override
    public String toString() {
        return "Groblje{" +
            "fid=" + fid +
            ", naziv='" + naziv + '\'' +
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
}
