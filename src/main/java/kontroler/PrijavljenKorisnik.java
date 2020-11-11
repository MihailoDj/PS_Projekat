/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import klase.Korisnik;

/**
 *
 * @author Mihailo
 */
public class PrijavljenKorisnik {
    private static PrijavljenKorisnik prijavljenKorisnik;
    private Korisnik korisnik;
    
    public PrijavljenKorisnik() {
        
    }
    
    public static PrijavljenKorisnik vratiInstancu() {
        if (prijavljenKorisnik == null)
            prijavljenKorisnik = new PrijavljenKorisnik();
        
        return prijavljenKorisnik; 
    }
    
    public void podesiPrijavljenogKorisnika(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    
    public Korisnik vratiPrijavljenogKorisnika() {
        return korisnik;
    }
}
