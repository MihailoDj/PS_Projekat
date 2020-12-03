/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import controller.Controller;
import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;
import domain.MovieGenre;
import domain.Production;
import domain.ProductionCompany;
import domain.Role;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmMovie;
import view.form.component.table.MovieGenreTableModel;
import view.form.component.table.ProductionTableModel;
import view.form.component.table.RoleTableModel;
import view.form.util.FormMode;

/**
 *
 * @author Mihailo
 */
public class MovieController {
    private final FrmMovie frmMovie;
    private Movie movie;

    public MovieController(FrmMovie frmMovie) {
        this.frmMovie = frmMovie;
        addActionListeners();
        addListSelectionListener();
    }

    private void addActionListeners() {
        frmMovie.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblRoles(movie.getRoles());
                fillTblMovieGenres(movie.getMovieGenres());
                fillTblProduction(movie.getProductions());
            }
        });
        frmMovie.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
            
            private void add() {
                try {
                    validateForm();
                    
                    List<Role> roles = ((RoleTableModel)frmMovie.getTblRoles().getModel()).getAll();
                    List<MovieGenre> movieGenres = ((MovieGenreTableModel)frmMovie.getTblMovieGenre().getModel()).getAll();
                    List<Production> productions = ((ProductionTableModel)frmMovie.getTblProduction().getModel()).getAll();
                    
                    Movie movie = new Movie() {
                        {
                            setMovieID(0);
                            setName(frmMovie.getTxtName().getText().trim());
                            setDescription(frmMovie.getTxtDescription().getText().trim());
                            setScore(Double.parseDouble(frmMovie.getTxtScore().getText()));
                            setDirector((Director) frmMovie.getCbDirector().getSelectedItem());
                            setReleaseDate(frmMovie.getReleaseDate().getDate());
                            setRoles(roles);
                            setMovieGenres(movieGenres);
                            setProductions(productions);
                        }
                    };
                    
                    Controller.getInstance().insertMovie(movie);
                    JOptionPane.showMessageDialog(frmMovie, "Movie successfully saved!");
                    frmMovie.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(FrmMovie.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmMovie.addBtnEnableChangesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmMovie.addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmMovie.dispose();
            }
        });

        frmMovie.addBtnDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure?", 
                        "Delete movie", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    Movie movie = makeMovieFromForm();
                    try {
                        Controller.getInstance().deleteMovie(movie);
                        JOptionPane.showMessageDialog(frmMovie, "Movie deleted successfully!\n", "Delete movie", JOptionPane.INFORMATION_MESSAGE);
                        frmMovie.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Delete movie", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        frmMovie.addBtnUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

            private void update() {
                try {
                    validateForm();
                    int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure?", 
                        "Update movie", JOptionPane.YES_NO_OPTION);
                
                    if(check == JOptionPane.YES_OPTION){
                        
                        Movie movie = makeMovieFromForm();
                        Controller.getInstance().updateMovie(movie);
                        JOptionPane.showMessageDialog(frmMovie, "Movie updated successfully!\n", "Update movie", JOptionPane.INFORMATION_MESSAGE);
                        frmMovie.dispose();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
                
            }
        });
        frmMovie.addBtnAddActorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Role role = new Role() {
                    {
                        setMovie(movie);
                        setRoleName(frmMovie.getTxtRoleName().getText().trim());
                        setActor((Actor)frmMovie.getCbActors().getSelectedItem());
                    }
                };
                
                movie.getRoles().add(role);
                fillTblRoles(movie.getRoles());
            }
        });
        frmMovie.addBtnRemoveActorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = frmMovie.getTblRoles().getSelectedRow();
                Role role = ((RoleTableModel)frmMovie.getTblRoles().getModel()).getRoleAt(selRow);
                
                movie.getRoles().remove(role);
                fillTblRoles(movie.getRoles());
            }
        });
        frmMovie.addBtnAddGenreActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovieGenre movieGenre = new MovieGenre() {
                    {
                        setMovie(movie);
                        setGenre((Genre)frmMovie.getCbGenres().getSelectedItem());
                    }
                };
                
                movie.getMovieGenres().add(movieGenre);
                fillTblMovieGenres(movie.getMovieGenres());
            }
        });
        frmMovie.addBtnRemoveGenreActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = frmMovie.getTblMovieGenre().getSelectedRow();
                MovieGenre movieGenre = ((MovieGenreTableModel)frmMovie.getTblMovieGenre().getModel()).getMovieGenreAt(selRow);
                
                movie.getMovieGenres().remove(movieGenre);
                fillTblMovieGenres(movie.getMovieGenres());
            }
        });
        frmMovie.addBtnAddProductionCompanyActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Production productionCompany = new Production() {
                    {
                        setMovie(movie);
                        setProductionCompany((ProductionCompany)frmMovie.getCbProductionCompanies().getSelectedItem());
                    }
                };
                
                movie.getProductions().add(productionCompany);
                fillTblProduction(movie.getProductions());
            }
        });
        frmMovie.addBtnRemoveProductionCompanyActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = frmMovie.getTblProduction().getSelectedRow();
                Production productionCompany = ((ProductionTableModel)frmMovie.getTblProduction().getModel()).getProductionAt(selRow);
                
                movie.getProductions().remove(productionCompany);
                fillTblProduction(movie.getProductions());
            }
        });
    }
    
    private void addListSelectionListener() {
        frmMovie.getTableRolesAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovie.getTblRoles().getSelectedRow() != -1)
                    frmMovie.getBtnRemoveActor().setEnabled(true);
                else
                    frmMovie.getBtnRemoveActor().setEnabled(false);
            }
        });
        frmMovie.getTableMovieGenresAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovie.getTblMovieGenre().getSelectedRow() != -1)
                    frmMovie.getBtnRemoveGenre().setEnabled(true);
                else
                    frmMovie.getBtnRemoveGenre().setEnabled(false);
            }
        });
        frmMovie.getTableProductionAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovie.getTblProduction().getSelectedRow() != -1)
                    frmMovie.getBtnRemoveProductionCompany().setEnabled(true);
                else
                    frmMovie.getBtnRemoveProductionCompany().setEnabled(false);
            }
        });
    }

    public void openForm(FormMode formMode) {
        frmMovie.setTitle("Movie form");
        prepareView(formMode);
        frmMovie.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmMovie.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        fillCbDirector();
        fillCbActors();
        fillCbGenres();
        fillCbProductionCompanies();
        setupComponents(formMode);
    }
    
    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                movie = new Movie();
                
                frmMovie.getTxtMovieID().setText("auto");
                frmMovie.getTxtScore().setText(String.valueOf(0));
                
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(false);
                frmMovie.getBtnUpdate().setEnabled(false);
                frmMovie.getBtnEnableChanges().setEnabled(false);
                frmMovie.getBtnAdd().setEnabled(true);

                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(true);
                frmMovie.getTxtDescription().setEditable(true);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(true);
                frmMovie.getReleaseDate().setEnabled(true);
                break;
            case FORM_VIEW:
                movie = (Movie) MainCoordinator.getInstance().getParam(Constants.PARAM_MOVIE);
                
                frmMovie.getTxtMovieID().setText(movie.getMovieID() + "");
                frmMovie.getTxtName().setText(movie.getName());
                frmMovie.getTxtDescription().setText(movie.getDescription());
                frmMovie.getTxtScore().setText(String.valueOf(movie.getScore()));
                frmMovie.getReleaseDate().setDate(movie.getReleaseDate());
                frmMovie.getCbDirector().getModel().setSelectedItem(movie.getDirector());
                
                RoleTableModel rtm = new RoleTableModel(movie.getRoles());
                frmMovie.getTblRoles().setModel(rtm);
                frmMovie.getTblRoles().setEnabled(false);
                
                MovieGenreTableModel mgtm = new MovieGenreTableModel(movie.getMovieGenres());
                frmMovie.getTblMovieGenre().setModel(mgtm);
                frmMovie.getTblMovieGenre().setEnabled(false);
                
                ProductionTableModel ptm = new ProductionTableModel(movie.getProductions());
                frmMovie.getTblProduction().setModel(ptm);
                frmMovie.getTblProduction().setEnabled(false);
                
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(true);
                frmMovie.getBtnUpdate().setEnabled(false);
                frmMovie.getBtnEnableChanges().setEnabled(true);
                frmMovie.getBtnAdd().setEnabled(false);
                
                
                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(false);
                frmMovie.getTxtDescription().setEditable(false);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(false);
                frmMovie.getReleaseDate().setEnabled(false);
                
                frmMovie.getCbActors().setEnabled(false);
                frmMovie.getCbGenres().setEnabled(false);
                frmMovie.getCbProductionCompanies().setEnabled(false);
                frmMovie.getBtnAddActor().setEnabled(false);
                frmMovie.getBtnAddGenre().setEnabled(false);
                frmMovie.getBtnAddProductionCompany().setEnabled(false);
                
                break;
            case FORM_EDIT:
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(false);
                frmMovie.getBtnUpdate().setEnabled(true);
                frmMovie.getBtnEnableChanges().setEnabled(false);
                frmMovie.getBtnAdd().setEnabled(false);

                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(true);
                frmMovie.getTxtDescription().setEditable(true);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(true);
                frmMovie.getReleaseDate().setEnabled(true);
                
                frmMovie.getCbActors().setEnabled(true);
                frmMovie.getCbGenres().setEnabled(true);
                frmMovie.getCbProductionCompanies().setEnabled(true);
                frmMovie.getBtnAddActor().setEnabled(true);
                frmMovie.getBtnAddGenre().setEnabled(true);
                frmMovie.getBtnAddProductionCompany().setEnabled(true);
                
                frmMovie.getTblRoles().setEnabled(true);
                frmMovie.getTblMovieGenre().setEnabled(true);
                frmMovie.getTblProduction().setEnabled(true);
                break;
        }
    }

    private Movie makeMovieFromForm() {
        Movie movie = new Movie(){
            {
                setMovieID(Integer.parseInt(frmMovie.getTxtMovieID().getText().trim()));
                setName(frmMovie.getTxtName().getText().trim());
                setDescription(frmMovie.getTxtDescription().getText().trim());
                setScore(Double.parseDouble(frmMovie.getTxtScore().getText()));
                setDirector((Director) frmMovie.getCbDirector().getSelectedItem());
                setReleaseDate(frmMovie.getReleaseDate().getDate());
                
                setRoles(((RoleTableModel)frmMovie.getTblRoles().getModel()).getAll());
                setMovieGenres(((MovieGenreTableModel)frmMovie.getTblMovieGenre().getModel()).getAll());
                setProductions(((ProductionTableModel)frmMovie.getTblProduction().getModel()).getAll());
            }
        };
        
        return movie;
    }
    
    public void validateForm() throws Exception{
        if (frmMovie.getTxtName().getText().trim().isEmpty() || frmMovie.getTxtName() == null 
                || frmMovie.getReleaseDate().getDate() == null) {
            throw new Exception("Invalid input");
        }
    }
    
    private void fillCbDirector() {
        try {
            frmMovie.getCbDirector().removeAllItems();
            List<Director> directors = Controller.getInstance().selectAllDirectors();
            frmMovie.getCbDirector().setModel(new DefaultComboBoxModel<>(directors.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading directors", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void fillCbActors() {
        try {
            frmMovie.getCbActors().removeAllItems();
            List<Actor> actors = Controller.getInstance().selectAllActors();
            frmMovie.getCbActors().setModel(new DefaultComboBoxModel<>(actors.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading actors", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void fillCbGenres() {
        try {
            frmMovie.getCbGenres().removeAllItems();
            List<Genre> genres = Controller.getInstance().selectAllGenres();
            frmMovie.getCbGenres().setModel(new DefaultComboBoxModel<>(genres.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading genres", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void fillCbProductionCompanies() {
        try {
            frmMovie.getCbProductionCompanies().removeAllItems();
            List<ProductionCompany> productionCompanies = Controller.getInstance().selectAllProductionCompanies();
            frmMovie.getCbProductionCompanies().setModel(new DefaultComboBoxModel<>(productionCompanies.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading production companies", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void fillTblRoles(List<Role> roles) {
        RoleTableModel rtm = new RoleTableModel(roles);
        frmMovie.getTblRoles().setModel(rtm);

        setUpRoleTableColumns();
        
    }
    
    private void fillTblMovieGenres(List<MovieGenre> movieGenres) {
        MovieGenreTableModel mgtm = new MovieGenreTableModel(movieGenres);
        frmMovie.getTblMovieGenre().setModel(mgtm);

        setUpMovieGenreTableColumns();
    }
    
    private void fillTblProduction(List<Production> productionCompanies) {
        ProductionTableModel pctm = new ProductionTableModel(productionCompanies);
        frmMovie.getTblProduction().setModel(pctm);
        
        setUpProductionTableColumns();
    }
    
    private void setUpProductionTableColumns() {
        TableColumnModel tcm = frmMovie.getTblProduction().getColumnModel();
        
        frmMovie.getTblProduction().setAutoCreateRowSorter(true);
        frmMovie.getTblProduction().getTableHeader().setResizingAllowed(false);

        frmMovie.getTblProduction().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(300);
    }
    
    public void setUpRoleTableColumns(){
        TableColumnModel tcm = frmMovie.getTblRoles().getColumnModel();
        
        frmMovie.getTblRoles().setAutoCreateRowSorter(true);
        frmMovie.getTblRoles().getTableHeader().setResizingAllowed(false);

        frmMovie.getTblRoles().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
    }
    
    public void setUpMovieGenreTableColumns() {
        TableColumnModel tcm = frmMovie.getTblMovieGenre().getColumnModel();
        
        frmMovie.getTblMovieGenre().setAutoCreateRowSorter(true);
        frmMovie.getTblMovieGenre().getTableHeader().setResizingAllowed(false);

        frmMovie.getTblMovieGenre().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(300);
    }
}
