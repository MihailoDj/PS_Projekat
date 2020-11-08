/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klase;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Mihailo
 */
public class Film {
    private int filmID;
    private String naziv;
    private Date datumIzdanja;
    private String opis;
    private double prosecnaOcena;
    private Reziser reziser;
    
    public Film() {
        
    }

    public Film(int filmID, String naziv, Date datumIzdanja, String opis, double prosecnaOcena, Reziser reziser) {
        this.filmID = filmID;
        this.naziv = naziv;
        this.datumIzdanja = datumIzdanja;
        this.opis = opis;
        this.prosecnaOcena = 10;
        this.reziser = reziser;
    }
    
    

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatumIzdanja() {
        return datumIzdanja;
    }

    public void setDatumIzdanja(Date datumIzdanja) {
        this.datumIzdanja = datumIzdanja;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public Reziser getReziser() {
        return reziser;
    }

    public void setReziser(Reziser reziser) {
        this.reziser = reziser;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.filmID;
        hash = 29 * hash + Objects.hashCode(this.naziv);
        hash = 29 * hash + Objects.hashCode(this.datumIzdanja);
        hash = 29 * hash + Objects.hashCode(this.opis);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.prosecnaOcena) ^ (Double.doubleToLongBits(this.prosecnaOcena) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.reziser);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Film other = (Film) obj;
        if (!Objects.equals(this.naziv, other.naziv)) {
            return false;
        }
        if (!Objects.equals(this.datumIzdanja, other.datumIzdanja)) {
            return false;
        }
        if (!Objects.equals(this.reziser, other.reziser)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Film{" + "filmID=" + filmID + ", naziv=" + naziv + ", datumIzdanja=" + datumIzdanja + ", opis=" + opis + ", prosecnaOcena=" + prosecnaOcena + ", reziser=" + reziser + '}';
    }
    
    
}
