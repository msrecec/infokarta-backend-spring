package it.geosolutions.mapstore.dto.grobovi;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.geosolutions.mapstore.serializers.PGgeometrySerializer;
import org.postgis.PGgeometry;

public class GrobDTO {

    public static class Builder {
        private PGgeometry geom;
        private Integer fid;
        private String source;
        private String source1;
        private String source2;
        private String source3;
        private String source4;
        private String source5;
        private String source6;
        private String source7;
        private String RedniBroj;
        private String Grobnica;
        private String brojLezaja;
        private String Groblje;
        private Integer fk;

        public Builder() {}

        public Builder geom(PGgeometry geom) {
            this.geom = geom;
            return this;
        }

        public Builder fid(Integer fid) {
            this.fid = fid;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder source1(String source1) {
            this.source1 = source1;
            return this;
        }

        public Builder source2(String source2) {
            this.source2 = source2;
            return this;
        }
        public Builder source3(String source3) {
            this.source3 = source3;
            return this;
        }
        public Builder source4(String source4) {
            this.source4 = source4;
            return this;
        }
        public Builder source5(String source5) {
            this.source5 = source5;
            return this;
        }
        public Builder source6(String source6) {
            this.source6 = source6;
            return this;
        }
        public Builder source7(String source7) {
            this.source7 = source7;
            return this;
        }
        public Builder RedniBroj(String RedniBroj) {
            this.RedniBroj = RedniBroj;
            return this;
        }
        public Builder Grobnica(String Grobnica) {
            this.Grobnica = Grobnica;
            return this;
        }
        public Builder brojLezaja(String brojLezaja) {
            this.brojLezaja = brojLezaja;
            return this;
        }
        public Builder Groblje(String Groblje) {
            this.Groblje = Groblje;
            return this;
        }

        public Builder fk(Integer fk) {
            this.fk = fk;
            return this;
        }

        public GrobDTO build() {
            GrobDTO grobDTO = new GrobDTO();

            grobDTO.geom = this.geom;
            grobDTO.fid = this.fid;
            grobDTO.source = this.source;
            grobDTO.source1 = this.source1;
            grobDTO.source2 = this.source2;
            grobDTO.source3 = this.source3;
            grobDTO.source4 = this.source4;
            grobDTO.source5 = this.source5;
            grobDTO.source6 = this.source6;
            grobDTO.source7 = this.source7;
            grobDTO.RedniBroj = this.RedniBroj;
            grobDTO.Grobnica = this.Grobnica;
            grobDTO.brojLezaja = this.brojLezaja;
            grobDTO.Groblje = this.Groblje;
            grobDTO.fk = this.fk;

            return grobDTO;
        }

    }

    @JsonSerialize(using = PGgeometrySerializer.class)
    private PGgeometry geom;
    private Integer fid;
    private String source;
    private String source1;
    private String source2;
    private String source3;
    private String source4;
    private String source5;
    private String source6;
    private String source7;
    private String RedniBroj;
    private String Grobnica;
    private String brojLezaja;
    private String Groblje;
    private Integer fk;

    public GrobDTO() {}

    public GrobDTO(PGgeometry geom, Integer fid, String source, String source1, String source2, String source3,
                   String source4, String source5, String source6, String source7, String redniBroj, String grobnica,
                   String brojLezaja, String groblje, Integer fk) {
        this.geom = geom;
        this.fid = fid;
        this.source = source;
        this.source1 = source1;
        this.source2 = source2;
        this.source3 = source3;
        this.source4 = source4;
        this.source5 = source5;
        this.source6 = source6;
        this.source7 = source7;
        RedniBroj = redniBroj;
        Grobnica = grobnica;
        this.brojLezaja = brojLezaja;
        Groblje = groblje;
        this.fk = fk;
    }

    @Override
    public String toString() {
        return "Grob{" +
            "geom=" + geom +
            ", fid=" + fid +
            ", source='" + source + '\'' +
            ", source1='" + source1 + '\'' +
            ", source2='" + source2 + '\'' +
            ", source3='" + source3 + '\'' +
            ", source4='" + source4 + '\'' +
            ", source5='" + source5 + '\'' +
            ", source6='" + source6 + '\'' +
            ", source7='" + source7 + '\'' +
            ", RedniBroj='" + RedniBroj + '\'' +
            ", Grobnica='" + Grobnica + '\'' +
            ", brojLezaja='" + brojLezaja + '\'' +
            ", Groblje='" + Groblje + '\'' +
            ", fk=" + fk +
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource1() {
        return source1;
    }

    public void setSource1(String source1) {
        this.source1 = source1;
    }

    public String getSource2() {
        return source2;
    }

    public void setSource2(String source2) {
        this.source2 = source2;
    }

    public String getSource3() {
        return source3;
    }

    public void setSource3(String source3) {
        this.source3 = source3;
    }

    public String getSource4() {
        return source4;
    }

    public void setSource4(String source4) {
        this.source4 = source4;
    }

    public String getSource5() {
        return source5;
    }

    public void setSource5(String source5) {
        this.source5 = source5;
    }

    public String getSource6() {
        return source6;
    }

    public void setSource6(String source6) {
        this.source6 = source6;
    }

    public String getSource7() {
        return source7;
    }

    public void setSource7(String source7) {
        this.source7 = source7;
    }

    public String getRedniBroj() {
        return RedniBroj;
    }

    public void setRedniBroj(String redniBroj) {
        RedniBroj = redniBroj;
    }

    public String getGrobnica() {
        return Grobnica;
    }

    public void setGrobnica(String grobnica) {
        Grobnica = grobnica;
    }

    public String getBrojLezaja() {
        return brojLezaja;
    }

    public void setBrojLezaja(String brojLezaja) {
        this.brojLezaja = brojLezaja;
    }

    public String getGroblje() {
        return Groblje;
    }

    public void setGroblje(String groblje) {
        Groblje = groblje;
    }

    public Integer getFk() {
        return fk;
    }

    public void setFk(Integer fk) {
        this.fk = fk;
    }
}
