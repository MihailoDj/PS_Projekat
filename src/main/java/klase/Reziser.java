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
public class Reziser {
    private int reziserID;
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    
    public Reziser() {
        
    }
    
    public Reziser(int reziserID, String ime, String prezime, Date datumRodjenja) {
        this.reziserID = reziserID;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
    }
    
    public int getReziserID() {
        return reziserID;
    }

    public void setReziserID(int reziserID) {
        this.reziserID = reziserID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.reziserID;
        hash = 37 * hash + Objects.hashCode(this.ime);
        hash = 37 * hash + Objects.hashCode(this.prezime);
        hash = 37 * hash + Objects.hashCode(this.datumRodjenja);
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
        final Reziser other = (Reziser) obj;
        if (!Objects.equals(this.ime, other.ime)) {
            return false;
        }
        if (!Objects.equals(this.prezime, other.prezime)) {
            return false;
        }
        if (!Objects.equals(this.datumRodjenja, other.datumRodjenja)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getIme() + " " + getPrezime();
    }
    
    
}
