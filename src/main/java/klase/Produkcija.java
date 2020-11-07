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
public class Produkcija {
    private ProdukcijskaKuca produkcijskaKuca;
    private Film film;
    
    public Produkcija() {
        
    }

    public Produkcija(ProdukcijskaKuca produkcijskaKuca, Film film) {
        this.produkcijskaKuca = produkcijskaKuca;
        this.film = film;
    }

    public ProdukcijskaKuca getProdukcijskaKuca() {
        return produkcijskaKuca;
    }

    public void setProdukcijskaKuca(ProdukcijskaKuca produkcijskaKuca) {
        this.produkcijskaKuca = produkcijskaKuca;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.produkcijskaKuca);
        hash = 79 * hash + Objects.hashCode(this.film);
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
        final Produkcija other = (Produkcija) obj;
        if (!Objects.equals(this.produkcijskaKuca, other.produkcijskaKuca)) {
            return false;
        }
        if (!Objects.equals(this.film, other.film)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produkcija{" + "produkcijskaKuca=" + produkcijskaKuca + ", film=" + film + '}';
    }
    
    
}
