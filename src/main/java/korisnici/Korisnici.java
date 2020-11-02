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
public class Korisnici {
     private final List<Korisnik> korisnici;
    
    public Korisnici() {
        korisnici = new ArrayList<>();
        korisnici.add(new Korisnik());
    }
}
