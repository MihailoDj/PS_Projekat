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
public class Korisnik {
    private Long korisnikID;
    private String korisnickoIme;
    private String lozinka;
    private Long ulogaID;

    public Korisnik() {
        
    }
    
    public Korisnik(Long korisnikID, String korisnickoIme, String lozinka, Long ulogaID) {
        this.korisnikID = korisnikID;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ulogaID = ulogaID;
    }

    public Long getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(Long korisnikID) {
        this.korisnikID = korisnikID;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Long getUlogaID() {
        return ulogaID;
    }

    public void setUlogaID(Long ulogaID) {
        this.ulogaID = ulogaID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.korisnikID);
        hash = 47 * hash + Objects.hashCode(this.korisnickoIme);
        hash = 47 * hash + Objects.hashCode(this.lozinka);
        hash = 47 * hash + Objects.hashCode(this.ulogaID);
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
        final Korisnik other = (Korisnik) obj;
        if (!Objects.equals(this.korisnickoIme, other.korisnickoIme)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Korisnik{" + "korisnikID=" + korisnikID + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + ", ulogaID=" + ulogaID + '}';
    }
    
    
}
