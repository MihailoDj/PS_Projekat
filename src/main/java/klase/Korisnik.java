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
    private int korisnikID;
    private String korisnickoIme;
    private String lozinka;
    private boolean admin;

    public Korisnik() {
        
    }
    
    public Korisnik(int korisnikID, String korisnickoIme, String lozinka, boolean admin) {
        this.korisnikID = korisnikID;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.admin = admin;
    }

    public int getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(int korisnikID) {
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
    
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.korisnikID;
        hash = 73 * hash + Objects.hashCode(this.korisnickoIme);
        hash = 73 * hash + Objects.hashCode(this.lozinka);
        hash = 73 * hash + (this.admin ? 1 : 0);
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
        return "Korisnik{" + "korisnikID=" + korisnikID + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + ", admin=" + admin + '}';
    }

    
}
