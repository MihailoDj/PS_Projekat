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
public class ProdukcijskaKuca {
    private int produkcijskaKucaID;
    private String naziv;
    
    public ProdukcijskaKuca() {
        
    }

    public ProdukcijskaKuca(int produkcijskaKucaID, String naziv) {
        this.produkcijskaKucaID = produkcijskaKucaID;
        this.naziv = naziv;
    }

    public int getProdukcijskaKucaID() {
        return produkcijskaKucaID;
    }

    public void setProdukcijskaKucaID(int produkcijskaKucaID) {
        this.produkcijskaKucaID = produkcijskaKucaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + this.produkcijskaKucaID;
        hash = 19 * hash + Objects.hashCode(this.naziv);
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
        final ProdukcijskaKuca other = (ProdukcijskaKuca) obj;
        if (!Objects.equals(this.naziv, other.naziv)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProdukcijskaKuca{" + "produkcijskaKucaID=" + produkcijskaKucaID + ", naziv=" + naziv + '}';
    }
    
    
}
