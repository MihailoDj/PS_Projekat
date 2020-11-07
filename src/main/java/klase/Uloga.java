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
public class Uloga {
    private String nazivUloge;
    private Film film;
    private Glumac glumac;

    public String getNazivUloge() {
        return nazivUloge;
    }

    public void setNazivUloge(String nazivUloge) {
        this.nazivUloge = nazivUloge;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Glumac getGlumac() {
        return glumac;
    }

    public void setGlumac(Glumac glumac) {
        this.glumac = glumac;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.nazivUloge);
        hash = 43 * hash + Objects.hashCode(this.film);
        hash = 43 * hash + Objects.hashCode(this.glumac);
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
        final Uloga other = (Uloga) obj;
        if (!Objects.equals(this.nazivUloge, other.nazivUloge)) {
            return false;
        }
        if (!Objects.equals(this.film, other.film)) {
            return false;
        }
        if (!Objects.equals(this.glumac, other.glumac)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uloga{" + "nazivUloge=" + nazivUloge + ", film=" + film + ", glumac=" + glumac + '}';
    }
    
    
}
