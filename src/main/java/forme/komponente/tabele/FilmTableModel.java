/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.komponente.tabele;

import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import klase.Film;
import klase.Reziser;
import kontroler.Kontroler;

/**
 *
 * @author Mihailo
 */
public class FilmTableModel extends AbstractTableModel{
    private final List<Film> filmovi;
    private final String[] naziviKolona = {
        "ID", 
        "Naziv", 
        "Datum izdanja", 
        "Opis",
        "Prosečna ocena",
        "Režiser"
    };
    private final Class[] klaseKolona = {
        Integer.class,
        String.class,
        Date.class,
        String.class,
        Double.class,
        Reziser.class
    };
    
    public FilmTableModel(List<Film> filmovi) {
        this.filmovi = filmovi;
    }

    @Override
    public int getRowCount() {
        if (filmovi == null) 
            return 0;
         else 
            return filmovi.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Film film = filmovi.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return film.getFilmID();
            case 1: return film.getNaziv();
            case 2: return film.getDatumIzdanja();
            case 3: return film.getOpis();
            case 4: return film.getProsecnaOcena();
            case 5: return film.getReziser();
            default: return "N/A";
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Film film = filmovi.get(rowIndex);
        
        switch(columnIndex) {
            case 1:
                film.setNaziv(String.valueOf(value));
                break;
            case 2:
                film.setDatumIzdanja(new Date());
                break;
            case 3:
                film.setOpis(String.valueOf(value));
                break;
            case 5:
                film.setReziser((Reziser) value);
                break;
        }
    }

    @Override
    public String getColumnName(int column) {
        return naziviKolona[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 5;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return klaseKolona[columnIndex];
    }

    public void dodajFilm(Film film) {
        filmovi.add(film);
        fireTableRowsInserted(filmovi.size()-1, filmovi.size()-1);
    }
    
    public Film vratiFilm(int red) {
        return filmovi.get(red);
    }
        
    public void ukloniFilm(int red) {
        filmovi.remove(red);
        fireTableRowsDeleted(red, red);
    }
}
