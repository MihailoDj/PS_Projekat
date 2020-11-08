/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import java.util.List;
import javax.swing.JOptionPane;
import klase.Film;
import klase.Korisnik;
import klase.Reziser;
import repozitorijumi.RepozitorijumFilmova;
import repozitorijumi.RepozitorijumKorisnika;
import repozitorijumi.RepozitorijumRezisera;

/**
 *
 * @author Mihailo
 */
public class Kontroler {
    private final RepozitorijumKorisnika repozitorijumKorisnika;
    private final RepozitorijumRezisera repozitorijumRezisera;
    private final RepozitorijumFilmova repozitorijumFilmova;

    public Kontroler() {
        this.repozitorijumKorisnika = new RepozitorijumKorisnika();
        this.repozitorijumRezisera = new RepozitorijumRezisera();
        this.repozitorijumFilmova = new RepozitorijumFilmova();
    }
    
    public Korisnik prijaviKorisnika(String korisnickoIme, String lozinka) throws Exception{
        List<Korisnik> korisnici = repozitorijumKorisnika.vratiSve();
        
        for (Korisnik korisnik : korisnici) {
            if (korisnik.getKorisnickoIme().equals(korisnickoIme) && korisnik.getLozinka().equals(lozinka)) {
                return korisnik;
            }
        }
        
        throw new Exception("Takav korisnik ne postoji!");
    }
    
    public List<Reziser> vratiSveRezisere() {
        return repozitorijumRezisera.vratiSveRezisere();
    }
    
    public void dodajFilm(Film film) {
        repozitorijumFilmova.dodajFilm(film);
    }
}
