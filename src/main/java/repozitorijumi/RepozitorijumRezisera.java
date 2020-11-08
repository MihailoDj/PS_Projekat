/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repozitorijumi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import klase.Reziser;

/**
 *
 * @author Mihailo
 */
public class RepozitorijumRezisera {
    private final List<Reziser> reziseri;
    
    public RepozitorijumRezisera() {
        reziseri = new ArrayList<Reziser>(){
            {
                add(new Reziser(1, "reziser1", "reziser1", new Date()));
                add(new Reziser(2, "reziser2", "reziser2", new Date()));
                add(new Reziser(3, "reziser3", "reziser3", new Date()));
                add(new Reziser(4, "reziser4", "reziser4", new Date()));
            }  
        };
    }
    
    public List<Reziser> vratiSveRezisere() {
        return reziseri;
    }
}
