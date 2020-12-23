/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.component.table;
import domain.Movie;
import domain.Review;
import domain.User;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mihailo
 */
public class ReviewTableModel extends AbstractTableModel{

    private final List<Review> reviews;
    private final String[] columnNames = new String[]{"ID", "Movie", "Review", "Score", "Posted on", "Posted by"};
    private final Class[] columnClasses = new Class[]{Integer.class, Movie.class, String.class, Double.class, LocalDateTime.class, User.class};
    
    public ReviewTableModel(List<Review> reviews) throws Exception {
        this.reviews = reviews;
    }
    
    @Override
    public int getRowCount() {
        return reviews.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Review review = reviews.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return review.getReviewID();
            case 1: return review.getMovie();
            case 2: return review.getReviewText();
            case 3: return review.getReviewScore();
            case 4: return review.getReviewDate();
            case 5: return review.getUser();
            default: return "N/A"; 
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public Review getReviewAt(int row) {
        return reviews.get(row);
    }
}
