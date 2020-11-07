/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klase;

import java.util.Objects;

/**
 *
 * @author Mihailo
 */
public class Glumac {
    private int glumacID;
    private String ime;
    private String prezime;
    private String biografija;
    
    public Glumac() {
        
    }

    public Glumac(int glumacID, String ime, String prezime, String biografija) {
        this.glumacID = glumacID;
        this.ime = ime;
        this.prezime = prezime;
        this.biografija = biografija;
    }
    
    

    public int getGlumacID() {
        return glumacID;
    }

    public void setGlumacID(int glumacID) {
        this.glumacID = glumacID;
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

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.glumacID;
        hash = 29 * hash + Objects.hashCode(this.ime);
        hash = 29 * hash + Objects.hashCode(this.prezime);
        hash = 29 * hash + Objects.hashCode(this.biografija);
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
        final Glumac other = (Glumac) obj;
        if (!Objects.equals(this.ime, other.ime)) {
            return false;
        }
        if (!Objects.equals(this.prezime, other.prezime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Glumac{" + "glumacID=" + glumacID + ", ime=" + ime + ", prezime=" + prezime + ", biografija=" + biografija + '}';
    }
    
    
}
