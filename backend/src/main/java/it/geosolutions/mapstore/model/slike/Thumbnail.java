package it.geosolutions.mapstore.model.slike;

public class Thumbnail extends Slika {

    private String lokacija;


    @Override
    public String toString() {
        return "Thumbnail{" +
            "fid=" + super.getFid() +
            ", naziv='" + super.getNaziv() + '\'' +
            ", tip='" + super.getTip() + '\'' +
            ", mediaFid=" + super.getMediaFid() +
            ", lokacija='" + lokacija +
            '}';
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }
}
