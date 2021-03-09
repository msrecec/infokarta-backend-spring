package it.geosolutions.mapstore.pojo;

public class Groblje {
    private Integer brojGrobova;
    private String naziv;

    @Override
    public String toString() {
        return "Groblje{" +
            "brojGrobova=" + brojGrobova +
            ", naziv='" + naziv + '\'' +
            '}';
    }

    public Integer getBrojGrobova() {
        return brojGrobova;
    }

    public void setBrojGrobova(Integer brojGrobova) {
        this.brojGrobova = brojGrobova;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
