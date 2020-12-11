/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import communication.Communication;
import domain.Actor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmActor;
import view.form.util.FormMode;

/**
 *
 * @author Mihailo
 */
public class ActorController {
    private final FrmActor frmActor;
    
    public ActorController(FrmActor frmActor) {
        this.frmActor = frmActor;
        addActionListener();
    }
    
    private void addActionListener() {
        frmActor.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
            
            private void add() {
                try {
                    validateForm();
                    Actor actor = new Actor() {
                        {
                            setFirstName(frmActor.getTxtActorFirstName().getText().trim());
                            setLastName(frmActor.getTxtActorLastName().getText().trim());
                            setBiography(frmActor.getBiography().getText().trim());
                        }
                    };
                    
                    Communication.getInstance().insertActor(actor);
                    JOptionPane.showMessageDialog(frmActor, "Actor successfully saved!");
                    frmActor.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(FrmActor.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmActor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmActor.addBtnEnableChangesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpComponents(FormMode.FORM_EDIT);
            }
        });

        frmActor.addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmActor.dispose();
            }
        });

        frmActor.addBtnDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                int check = JOptionPane.showConfirmDialog(frmActor, "Are you sure?", 
                        "Delete actor", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    Actor actor = makeActorFromForm();
                    try {
                        Communication.getInstance().deleteActor(actor);
                        JOptionPane.showMessageDialog(frmActor, "Actor deleted successfully!\n", 
                                "Delete actor", JOptionPane.INFORMATION_MESSAGE);
                        frmActor.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmActor, ex.getMessage(), "Delete actor", 
                                JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(ActorController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        frmActor.addBtnUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

            private void update() {
                try {
                    validateForm();
                    int check = JOptionPane.showConfirmDialog(frmActor, "Are you sure?", 
                        "Update actor", JOptionPane.YES_NO_OPTION);
                
                    if(check == JOptionPane.YES_OPTION){
                        
                        Actor actor = makeActorFromForm();
                        Communication.getInstance().updateActor(actor);
                        JOptionPane.showMessageDialog(frmActor, "Actor updated successfully!\n", 
                                "Update actor", JOptionPane.INFORMATION_MESSAGE);
                        frmActor.dispose();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ActorController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmActor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
                
            }
        });
    }
    
    public void openForm(FormMode formMode) {
        frmActor.setTitle("Actor form");
        setUpComponents(formMode);
        frmActor.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmActor.setVisible(true);
    }

    private void setUpComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmActor.getTxtActorID().setText("auto");
                
                frmActor.getBtnCancel().setEnabled(true);
                frmActor.getBtnDelete().setEnabled(false);
                frmActor.getBtnUpdate().setEnabled(false);
                frmActor.getBtnEnableChanges().setEnabled(false);
                frmActor.getBtnAdd().setEnabled(true);

                frmActor.getTxtActorID().setEditable(false);
                frmActor.getTxtActorFirstName().setEditable(true);
                frmActor.getTxtActorLastName().setEditable(true);
                frmActor.getBiography().setEditable(true);
                break;
            case FORM_VIEW:
                frmActor.getBtnCancel().setEnabled(true);
                frmActor.getBtnDelete().setEnabled(true);
                frmActor.getBtnUpdate().setEnabled(false);
                frmActor.getBtnEnableChanges().setEnabled(true);
                frmActor.getBtnAdd().setEnabled(false);
                
                frmActor.getTxtActorID().setEditable(false);
                frmActor.getTxtActorFirstName().setEditable(false);
                frmActor.getTxtActorLastName().setEditable(false);
                frmActor.getBiography().setEditable(false);

                Actor actor = (Actor) MainCoordinator.getInstance().getParam(Constants.PARAM_ACTOR);
                frmActor.getTxtActorID().setText(actor.getActorID() + "");
                frmActor.getTxtActorFirstName().setText(actor.getFirstName());
                frmActor.getTxtActorLastName().setText(actor.getLastName());
                frmActor.getBiography().setText(actor.getBiography());
                break;
            case FORM_EDIT:
                frmActor.getBtnCancel().setEnabled(true);
                frmActor.getBtnDelete().setEnabled(false);
                frmActor.getBtnUpdate().setEnabled(true);
                frmActor.getBtnEnableChanges().setEnabled(false);
                frmActor.getBtnAdd().setEnabled(false);

                frmActor.getTxtActorID().setEditable(false);
                frmActor.getTxtActorFirstName().setEditable(true);
                frmActor.getTxtActorLastName().setEditable(true);
                frmActor.getBiography().setEditable(true);
                break;
        }
    }
    
    private void validateForm() throws Exception{
        if (frmActor.getTxtActorFirstName().getText().trim().isEmpty() || frmActor.getTxtActorFirstName() == null
                || frmActor.getTxtActorLastName().getText().trim().isEmpty() 
                || frmActor.getTxtActorLastName() == null) {
            throw new Exception("Invalid input");
        }
    }
    
    private Actor makeActorFromForm() {
        Actor actor = new Actor(){
            {
                setActorID(Integer.parseInt(frmActor.getTxtActorID().getText().trim()));
                setFirstName(frmActor.getTxtActorFirstName().getText().trim());
                setLastName(frmActor.getTxtActorLastName().getText().trim());
                setBiography(frmActor.getBiography().getText().trim());
            }
        };
        
        return actor;
    }
}
