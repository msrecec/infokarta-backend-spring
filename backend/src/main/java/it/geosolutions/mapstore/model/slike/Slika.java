package it.geosolutions.mapstore.model.slike;

public class Slika {

    private Integer fid;
    private String naziv;
    private String tip;
    private Integer mediaFid;

    @Override
    public String toString() {
        return "Slika{" +
            "fid=" + fid +
            ", naziv='" + naziv + '\'' +
            ", tip='" + tip + '\'' +
            ", mediaFid=" + mediaFid +
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

    public Integer getMediaFid() {
        return mediaFid;
    }

    public void setMediaFid(Integer mediaFid) {
        this.mediaFid = mediaFid;
    }
}
