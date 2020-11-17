/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.components.table;

import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import domain.Movie;
import domain.Director;
import controller.Controller;
import java.util.GregorianCalendar;

/**
 *
 * @author Mihailo
 */
public class MovieTableModel extends AbstractTableModel{
    private final List<Movie> movies;
    private final String[] columnNames = {
        "ID", 
        "Name", 
        "Release date", 
        "Description",
        "Score",
        "Director"
    };
    private final Class[] columnClasses = {
        Integer.class,
        String.class,
        Date.class,
        String.class,
        Double.class,
        Director.class
    };
    
    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getRowCount() {
        if (movies == null) 
            return 0;
         else 
            return movies.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie movie = movies.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return movie.getMovieID();
            case 1: return movie.getName();
            case 2: return movie.getReleaseDate();
            case 3: return movie.getDescription();
            case 4: return movie.getScore();
            case 5: return movie.getDirector();
            default: return "N/A";
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Movie movie = movies.get(rowIndex);
        
        switch(columnIndex) {
            case 1:
                movie.setName(String.valueOf(value));
                break;
            case 2:
                movie.setReleaseDate((GregorianCalendar) value);
                break;
            case 3:
                movie.setDescription(String.valueOf(value));
                break;
            case 5:
                movie.setDirector((Director) value);
                break;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 5;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        fireTableRowsInserted(movies.size()-1, movies.size()-1);
    }
    
    public Movie getMovie(int row) {
        return movies.get(row);
    }
        
    public void removeMovie(int row) {
        movies.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
