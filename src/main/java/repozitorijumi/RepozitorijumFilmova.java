/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repozitorijumi;

import java.util.ArrayList;
import java.util.List;
import klase.Film;

/**
 *
 * @author Mihailo
 */
public class RepozitorijumFilmova {
    private final List<Film> filmovi;
    
    public RepozitorijumFilmova() {
        filmovi = new ArrayList<Film>();
    }
    
    public void dodajFilm(Film film) {
        filmovi.add(film);
    }
    
    public List<Film> vratiSveFilmove() {
        return filmovi;
    }
}
