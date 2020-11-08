/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forme;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import klase.Film;
import klase.Reziser;
import kontroler.Kontroler;

/**
 *
 * @author Mihailo
 */
public class FrmFilm extends javax.swing.JFrame {

    /**
     * Creates new form FrmFilm
     */
    public FrmFilm() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Forma za rad sa filmovima");
        
        ucitajCB();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIFilmID = new javax.swing.JLabel();
        txtFilmID = new javax.swing.JTextField();
        lblNaziv = new javax.swing.JLabel();
        txtNaziv = new javax.swing.JTextField();
        txtDatumIzdanja = new javax.swing.JTextField();
        lblDatumIzdanja = new javax.swing.JLabel();
        lblProsecnaOcena = new javax.swing.JLabel();
        txtProsecnaOcena = new javax.swing.JTextField();
        lblReziser = new javax.swing.JLabel();
        lblOpis = new javax.swing.JLabel();
        cbReziser = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtOpis = new javax.swing.JTextArea();
        btnDodaj = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblIFilmID.setText("ID:");

        lblNaziv.setText("Naziv:");

        lblDatumIzdanja.setText("Datum izdanja:");

        lblProsecnaOcena.setText("Prosecna ocena:");

        lblReziser.setText("Reziser:");

        lblOpis.setText("Opis:");

        txtOpis.setColumns(20);
        txtOpis.setLineWrap(true);
        txtOpis.setRows(5);
        jScrollPane1.setViewportView(txtOpis);

        btnDodaj.setText("Dodaj film");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDodaj)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblIFilmID, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblDatumIzdanja)
                                .addComponent(lblProsecnaOcena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblReziser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblOpis, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDatumIzdanja, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                                .addComponent(txtFilmID, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtNaziv)
                                .addComponent(txtProsecnaOcena)
                                .addComponent(cbReziser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIFilmID)
                    .addComponent(txtFilmID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNaziv)
                    .addComponent(txtNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatumIzdanja)
                    .addComponent(txtDatumIzdanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProsecnaOcena)
                    .addComponent(txtProsecnaOcena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReziser)
                    .addComponent(cbReziser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpis)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(btnDodaj)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        //odradi validaciju polja prilikom unosa
        
        Film film = new Film();
        
        film.setFilmID(Integer.parseInt(txtFilmID.getText().trim()));
        film.setNaziv(txtNaziv.getText().trim());
        film.setDatumIzdanja(new Date()); // OBAVEZNO PITATI ZA PREPORUKE STO SE TICE RADA SA DATUMIMA
        film.setOpis(txtOpis.getText().trim());
        film.setReziser((Reziser)cbReziser.getSelectedItem());
        
        JOptionPane.showMessageDialog(this,"Unet film: " + film.getNaziv(), "Uspesan unos filma!", JOptionPane.INFORMATION_MESSAGE);
        
        txtFilmID.setText(null);
        txtNaziv.setText(null);
        txtDatumIzdanja.setText(null);
        txtOpis.setText(null);
        txtProsecnaOcena.setText(null);
        cbReziser.setSelectedIndex(0);
    }//GEN-LAST:event_btnDodajActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmFilm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmFilm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmFilm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmFilm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmFilm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JComboBox<Object> cbReziser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDatumIzdanja;
    private javax.swing.JLabel lblIFilmID;
    private javax.swing.JLabel lblNaziv;
    private javax.swing.JLabel lblOpis;
    private javax.swing.JLabel lblProsecnaOcena;
    private javax.swing.JLabel lblReziser;
    private javax.swing.JTextField txtDatumIzdanja;
    private javax.swing.JTextField txtFilmID;
    private javax.swing.JTextField txtNaziv;
    private javax.swing.JTextArea txtOpis;
    private javax.swing.JTextField txtProsecnaOcena;
    // End of variables declaration//GEN-END:variables

    private void ucitajCB() {
        popuniCBReziser();
    }

    private void popuniCBReziser() {
        cbReziser.removeAllItems();
        List<Reziser> reziseri = new Kontroler().vratiSveRezisere();
        
        for (Reziser reziser : reziseri) {
            cbReziser.addItem(reziser);
        }
    }
}
