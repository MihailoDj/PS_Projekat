/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import comm.Operation;
import comm.Request;
import communication.Communication;
import domain.Movie;
import domain.Review;
import domain.User;
import domain.UserMovieCollection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import util.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmMovieCollection;
import component.MovieTableModel;
import view.form.component.table.ReviewTableModel;

/**
 *
 * @author Mihailo
 */
public class MovieCollectionController {
    private final FrmMovieCollection frmMovieCollection;
    
    public MovieCollectionController (FrmMovieCollection frmMovieCollection) {
        this.frmMovieCollection = frmMovieCollection;
        addActionListeners();
        addListSelectionListeners();
        addKeyListeners();
    }
    
    private void addActionListeners() {
        frmMovieCollection.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                    UserMovieCollection collection = new UserMovieCollection();
                    collection.setUser(user);
                    
                    Request request = new Request(Operation.SELECT_COLLECTIONS, collection);
                    Communication.getInstance().sendUserRequest(request);
                } catch (Exception ex) {
                    Logger.getLogger(MovieCollectionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                MainCoordinator.getInstance().setMovieCollectionController(null);
            }
            
            
        });
        frmMovieCollection.btnDetailsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmMovieCollection.getTblCollection().getSelectedRow();
                
                if (row != -1) {
                    Movie movie = ((MovieTableModel) frmMovieCollection.getTblCollection().getModel()).getMovieAt(row);
                    MainCoordinator.getInstance().addParam(Constants.PARAM_MOVIE, movie);
                    MainCoordinator.getInstance().openUserMovieForm();
                } else {
                    JOptionPane.showMessageDialog(frmMovieCollection, "You must select a movie", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmMovieCollection.btnReviewActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frmMovieCollection.getTblCollection().getSelectedRow();
                
                Movie movie = ((MovieTableModel)frmMovieCollection.getTblCollection().getModel()).getMovieAt(selectedRow);
                User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                
                Review review = new Review();
                review.setMovie(movie);
                review.setUser(user);
                
                MainCoordinator.getInstance().addParam(Constants.PARAM_MOVIE, movie);
                MainCoordinator.getInstance().addParam(Constants.PARAM_REVIEW, review);
                MainCoordinator.getInstance().openReviewForm();
            }
        });
        frmMovieCollection.btnRemoveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmMovieCollection.getTblCollection().getSelectedRow();
                User user = ((User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                

                if (row != -1) {
                    try {
                        int check = JOptionPane.showConfirmDialog(frmMovieCollection, "Are you sure?",
                                "Remove movie", JOptionPane.YES_NO_OPTION);

                        if (check == JOptionPane.YES_OPTION) {
                            Movie movie = ((MovieTableModel) frmMovieCollection.getTblCollection().getModel()).getMovieAt(row);
{
                            UserMovieCollection umc = new UserMovieCollection() ;
                            umc.setMovie(movie);
                            umc.setUser(user);
                            
                            Request request = new Request(Operation.DELETE_COLLECTION, umc);
                            Communication.getInstance().sendUserRequest(request);
                        }

                            JOptionPane.showMessageDialog(frmMovieCollection, "Movie successfully removed",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(MovieCollectionController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(frmMovieCollection, "Unable to remove movie", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmMovieCollection, "You must select a movie", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmMovieCollection.btnReviewDetailsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frmMovieCollection.getTblReviews().getSelectedRow();
                Review review = ((ReviewTableModel)frmMovieCollection.getTblReviews().getModel()).getReviewAt(selectedRow);
                MainCoordinator.getInstance().addParam(Constants.PARAM_REVIEW, review);
                
                MainCoordinator.getInstance().openReviewDetailsForm();
            }
        });
    }
    
    private void addListSelectionListeners() {
        frmMovieCollection.tblCollectionListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovieCollection.getTblCollection().getSelectedRow() != -1){
                    try {
                        enableButtons();
                        
                        int selectedRow = frmMovieCollection.getTblCollection().getSelectedRow();
                        
                        Movie selectedMovie = ((MovieTableModel)frmMovieCollection.getTblCollection().getModel()).getMovieAt(selectedRow);
                        User user = new User();
                        user.setUserID(0l);
                        
                        Review r = new Review();
                        r.setMovie(selectedMovie);
                        r.setUser(user);
                        
                        Request request = new Request(Operation.SELECT_REVIEWS, r);
                        Communication.getInstance().sendUserRequest(request);
                    } catch (Exception ex) {
                        Logger.getLogger(MovieCollectionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                else
                    disableButtons();
            }
        });
        frmMovieCollection.tblReviewsListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovieCollection.getTblReviews().getSelectedRow() != -1)
                    frmMovieCollection.getBtnReviewDetails().setEnabled(true);
                else
                    frmMovieCollection.getBtnReviewDetails().setEnabled(false);
            }
        });
    }
    
    private void addKeyListeners() {
        frmMovieCollection.filterKeyPressed(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String filter = frmMovieCollection.getTxtFilter().getText().trim();
                filter(filter);
            }
        });
    }
    
    public void openForm() {
        prepareView();
        frmMovieCollection.setLocationRelativeTo(MainCoordinator.getInstance().getUserMainController().getFrmUserMain());
        frmMovieCollection.setTitle("My movies");
        frmMovieCollection.setVisible(true);
    }
    
    private void filter(String filter) {
        TableRowSorter<MovieTableModel> trs = new TableRowSorter<>((MovieTableModel) frmMovieCollection.getTblCollection().getModel());
        frmMovieCollection.getTblCollection().setRowSorter(trs);

        trs.setRowFilter(RowFilter.regexFilter("(?i)" + filter));
    }
    
    private void enableButtons() {
        frmMovieCollection.getBtnReview().setEnabled(true);
        frmMovieCollection.getBtnRemove().setEnabled(true);
        frmMovieCollection.getBtnDetails().setEnabled(true);
    }
    
    private void disableButtons() {
        frmMovieCollection.getBtnReview().setEnabled(false);
        frmMovieCollection.getBtnRemove().setEnabled(false);
        frmMovieCollection.getBtnDetails().setEnabled(false);
    }

    private void prepareView() {
        
    }
    
    public void fillTblCollection (List<UserMovieCollection> collections) {
        try {
            List<Movie> movies = new ArrayList<>();
            
            for (UserMovieCollection umc : collections) {
                movies.add(umc.getMovie());
            }
            
            MovieTableModel mtm = new MovieTableModel(movies);
            frmMovieCollection.getTblCollection().setModel(mtm);
            setUpTableColumns();
        } catch (Exception ex) {
            Logger.getLogger(MovieCollectionController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovieCollection, "Error loading collection", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void fillTblReviews(List<Review> reviews) {
        try {
            ReviewTableModel rtm = new ReviewTableModel(reviews);
            frmMovieCollection.getTblReviews().setModel(rtm);
            setUpReviewTableColumns();
        } catch (Exception ex) {
            Logger.getLogger(MovieCollectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setUpTableColumns() throws Exception {
        TableColumnModel tcm = frmMovieCollection.getTblCollection().getColumnModel();
        
        TableColumn tcReleaseDate = tcm.getColumn(2);
        tcReleaseDate.setCellEditor(new DateTableEditor());
        tcReleaseDate.setCellRenderer(new DateTableEditor());

        frmMovieCollection.getTblCollection().setAutoCreateRowSorter(true);
        frmMovieCollection.getTblCollection().getTableHeader().setResizingAllowed(false);

        frmMovieCollection.getTblCollection().setRowHeight(30);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(75);
        tcm.getColumn(4).setPreferredWidth(15);
        tcm.getColumn(5).setPreferredWidth(100);
        tcm.getColumn(6).setPreferredWidth(15);
        
    }
    
    private void setUpReviewTableColumns() {
        TableColumnModel tcm = frmMovieCollection.getTblReviews().getColumnModel();

        frmMovieCollection.getTblReviews().setAutoCreateRowSorter(true);
        frmMovieCollection.getTblReviews().getTableHeader().setResizingAllowed(false);

        frmMovieCollection.getTblReviews().setRowHeight(30);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(150);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(15);
        tcm.getColumn(4).setPreferredWidth(150);
        tcm.getColumn(5).setPreferredWidth(100);
    }
}
