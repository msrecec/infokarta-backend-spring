package it.geosolutions.mapstore.model;

public class SlikaMeta {
    private Integer fid;
    private String naziv;
    private String original;
    private String thumbnail;
    private String tip;
    private Integer fk;

    @Override
    public String toString() {
        return "SlikaMeta{" +
            "fid=" + fid +
            ", naziv='" + naziv + '\'' +
            ", original='" + original + '\'' +
            ", thumbnail='" + thumbnail + '\'' +
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

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
