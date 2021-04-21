package it.geosolutions.mapstore.model.groblje;

public class Groblje {

    public static class Builder {
        private Integer fid;
        private String naziv;

        public Builder(Integer fid) {
            this.fid = fid;
        }
        public Builder naziv(String naziv) {
            this.naziv = naziv;
            return this;
        }
        public Groblje build() {
            Groblje groblje = new Groblje();

            groblje.fid = this.fid;
            groblje.naziv = this.naziv;

            return groblje;
        }

    }

    private Integer fid;
    private String naziv;

    public Groblje() {}

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
