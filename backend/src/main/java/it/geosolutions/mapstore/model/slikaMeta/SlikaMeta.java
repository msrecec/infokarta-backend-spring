package it.geosolutions.mapstore.model.slikaMeta;

public class SlikaMeta {

    public static class Builder {
        private Integer fid;
        private String naziv;
        private String original;
        private String thumbnail;
        private String tip;
        private Integer fk;

        public Builder(Integer fid) {
            this.fid = fid;
        }
        public Builder naziv(String naziv) {
            this.naziv = naziv;
            return this;
        }
        public Builder original(String original) {
            this.original = original;
            return this;
        }
        public Builder thumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }
        public Builder tip(String tip) {
            this.tip = tip;
            return this;
        }
        public Builder fk(Integer fk) {
            this.fk = fk;
            return this;
        }
        public SlikaMeta build() {
            SlikaMeta slikaMeta = new SlikaMeta();

            slikaMeta.fid = this.fid;
            slikaMeta.naziv = this.naziv;
            slikaMeta.original = this.original;
            slikaMeta.thumbnail = this.thumbnail;
            slikaMeta.tip = this.tip;
            slikaMeta.fk = this.fk;

            return slikaMeta;
        }

    }

    private Integer fid;
    private String naziv;
    private String original;
    private String thumbnail;
    private String tip;
    private Integer fk;

    public SlikaMeta() {}

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
