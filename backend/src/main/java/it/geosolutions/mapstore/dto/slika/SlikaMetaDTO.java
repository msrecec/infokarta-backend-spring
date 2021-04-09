package it.geosolutions.mapstore.dto.slika;

public class SlikaMetaDTO {
    private Integer fid;
    private String naziv;
    private String tip;
    private Integer fk;

    public SlikaMetaDTO(Integer fid, String naziv, String tip, Integer fk) {
        this.fid = fid;
        this.naziv = naziv;
        this.tip = tip;
        this.fk = fk;
    }

    @Override
    public String toString() {
        return "SlikaMetaDTO{" +
            "fid=" + fid +
            ", naziv='" + naziv + '\'' +
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
