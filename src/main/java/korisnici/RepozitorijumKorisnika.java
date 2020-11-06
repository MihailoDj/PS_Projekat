/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisnici;

import klase.Korisnik;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mihailo
 */
public class RepozitorijumKorisnika {
     private final List<Korisnik> korisnici;
    
    public RepozitorijumKorisnika() {
        korisnici = new ArrayList<Korisnik>() {
            {
                add(new Korisnik(1, "admin", "admin", true));
                add(new Korisnik(2, "korisnik", "korisnik", false));
            }
        };
    }
    
    public List<Korisnik> vratiSve() {
        
        return korisnici;
    }
}
