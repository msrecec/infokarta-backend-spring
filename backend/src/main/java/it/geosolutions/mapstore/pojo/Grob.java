package it.geosolutions.mapstore.pojo;

import org.postgis.PGgeometry;

public class Grob {
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
}
