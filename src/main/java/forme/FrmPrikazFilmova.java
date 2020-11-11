/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forme;

import forme.komponente.tabele.FilmTableModel;
import forme.util.RezimRadaForme;
import java.awt.Component;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import klase.Film;
import klase.Reziser;
import kontroler.Kontroler;

/**
 *
 * @author Mihailo
 */
public class FrmPrikazFilmova extends javax.swing.JDialog {

    /**
     * Creates new form FrmPrikazFilmova
     */
    public FrmPrikazFilmova(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        pripremiProzor();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblFilmovi = new javax.swing.JTable();
        btnInformacije = new javax.swing.JButton();
        btnDodaj = new javax.swing.JButton();
        btnUkloni = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblFilmovi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Naziv", "Datum izdanja", "Prosečna ocena", "Režiser"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblFilmovi);

        btnInformacije.setText("Informacije");
        btnInformacije.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformacijeActionPerformed(evt);
            }
        });

        btnDodaj.setText("Dodaj film");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajActionPerformed(evt);
            }
        });

        btnUkloni.setText("Ukloni film");
        btnUkloni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUkloniActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInformacije, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDodaj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUkloni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnDodaj)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUkloni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnInformacije)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInformacijeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformacijeActionPerformed
        int red = tblFilmovi.getSelectedRow();
        
        if (red != -1) {
            Film film = ((FilmTableModel)tblFilmovi.getModel()).vratiFilm(red);
            new FrmFilm(null, true, RezimRadaForme.FORMA_PRIKAZ, film).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Za prikaz informacija morate izabrati film", 
                    "Greška", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnInformacijeActionPerformed

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        FilmTableModel ftm = (FilmTableModel) tblFilmovi.getModel();
        ftm.dodajFilm(new Film());
        
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnUkloniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUkloniActionPerformed
        int red = tblFilmovi.getSelectedRow();
        
        if (red != -1) {
            //TODO da li ste sigurni
            
            FilmTableModel ftm = (FilmTableModel) tblFilmovi.getModel();
            ftm.ukloniFilm(red);
        } else {
            JOptionPane.showMessageDialog(this, "Morate izabrati film.", "Greška!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUkloniActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnInformacije;
    private javax.swing.JButton btnUkloni;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFilmovi;
    // End of variables declaration//GEN-END:variables

    private void pripremiProzor() {
        setTitle("Prikaz filmova");
        setLocationRelativeTo(null);
        
        popuniTabeluFilmova();
    }

    public void popuniTabeluFilmova() {
        List<Film> filmovi = Kontroler.vratiInstancu().vratiSveFilmove();
        FilmTableModel ftm = new FilmTableModel(filmovi);
        tblFilmovi.setModel(ftm);
        
        List<Reziser> reziseri = Kontroler.vratiInstancu().vratiSveRezisere();
        JComboBox cbReziseri = new JComboBox(reziseri.toArray());
        
        TableColumnModel tcm = tblFilmovi.getColumnModel();
        TableColumn tcReziser = tcm.getColumn(5);
        
        tcReziser.setCellEditor(new DefaultCellEditor(cbReziseri));
    }
}
