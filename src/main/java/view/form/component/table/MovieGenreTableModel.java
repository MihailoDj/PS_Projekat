/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.component.table;

import domain.MovieGenre;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mihailo
 */
public class MovieGenreTableModel extends AbstractTableModel{
    private final List<MovieGenre> movieGenres;
    private final String[] columnNames = {
        "ID",
        "Genre name"
    };
    private final Class[] columnClasses = {
        Integer.class,
        String.class
    };
    
    public MovieGenreTableModel(List<MovieGenre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    @Override
    public int getRowCount() {
        if (movieGenres == null) 
            return 0;
         else 
            return movieGenres.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MovieGenre movieGenre = movieGenres.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return movieGenre.getGenre().getGenreID();
            case 1: return movieGenre.getGenre().getName();
            default: return "N/A";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    public MovieGenre getMovieGenreAt(int row) {
        return movieGenres.get(row);
    }
}
