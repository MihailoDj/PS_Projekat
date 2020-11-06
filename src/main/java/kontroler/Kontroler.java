/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import java.util.List;
import klase.Korisnik;
import korisnici.RepozitorijumKorisnika;

/**
 *
 * @author Mihailo
 */
public class Kontroler {
    private final RepozitorijumKorisnika repozitorijumKorisnika;

    public Kontroler() {
        this.repozitorijumKorisnika = new RepozitorijumKorisnika();
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
}
