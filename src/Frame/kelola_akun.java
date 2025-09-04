/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.Statement;
import Frame.Koneksi;
import Frame.login;
import java.sql.DriverManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nizarislamudin
 */
public class kelola_akun extends javax.swing.JFrame {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    Statement statLgn;
    
    /**
     * Creates new form Register
     */
    public kelola_akun() {
        initComponents();
        koneksi();
         txID.setEnabled(false);
         autonumber();
         muncultabel();
    }
    private void koneksi() {
	try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:/db_bluescope","root","");
            stat=con.createStatement();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    public String status = "Pilih Status";
     
    private void Reset(){
        txUsername.setText(null);
        txGmail.setText(null);
        txPassword.setText(null);
        txConPassword.setText(null);
        txLevel.setSelectedItem(status);
        autonumber();
  }
     
    private void autonumber(){
        try{
            Connection c = Koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM login ORDER BY id DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String NoID = r.getString("id").substring(2);
                String ID = "" +(Integer.parseInt(NoID)+1);
                String Zero = "";
                
                if (ID.length()==1) 
                {Zero = "00";}
                else if(ID.length()==2)
                {Zero = "0";}
                else if(ID.length()==3)
                {Zero = "";}
                
                txID.setText("AD" + Zero + ID);
            }else{
                txID.setText("AD001");
            }
            r.close();
            s.close();
        }catch(Exception e){
            System.out.println("autonumber error");
        }
    }
    
private void muncultabel(){
    DefaultTableModel tb= new DefaultTableModel();
    // Memberi nama pada setiap kolom tabel
    tb.addColumn("ID");
    tb.addColumn("Username");
    tb.addColumn("Email");
    tb.addColumn("Password");
    tb.addColumn("Status");
    tabeldata.setModel(tb);
        try{
        // Mengambil data dari database
        res=stat.executeQuery("select * from login");

        while (res.next())
        {
        // Mengambil data dari database berdasarkan nama kolom pada tabel
        // Lalu di tampilkan ke dalam JTable
        tb.addRow(new Object[]{
        res.getString("id"),
        res.getString("username"),
        res.getString("gmail"),
        res.getString("password"),
        res.getString("level"),
        });
        }

        }catch (Exception e){
    }
} 

private void daftar(){
    String id = txID.getText().toString().trim();
    String username = txUsername.getText().toString().trim();
    String Gmail = txGmail.getText().toString().trim();
    String password = txPassword.getText().toString().trim();
    String conPassword = txConPassword.getText().toString().trim();
    String level = txLevel.getSelectedItem().toString().trim();

    if (!password.equals(conPassword)){
        JOptionPane.showMessageDialog(null, "Kata Sandi Tidak Cocok");
     }else if (password.equals("") || username.equals("")){
        JOptionPane.showMessageDialog(null, "Username dan Password Tidak Boleh Kososng");
    }else{
        try{
            Connection c = Koneksi.getKoneksi();
            String sql = "INSERT INTO login VALUES (?, ?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, id);
            p.setString(2, Gmail);
            p.setString(3, username);
            p.setString(4, password);
            p.setString(5, level);
            p.executeUpdate();
            p.close();
            Reset();
            JOptionPane.showMessageDialog(null, "Berhasil Membuat Akun");
            muncultabel();
        }catch(SQLException e){
            System.out.println("Error");
        }
    }
}

private void edit(){
try{
   koneksi();
   statLgn = con.createStatement();
   String SQL = "update login SET id = '"+txID.getText()+
           "', username = '"+txUsername.getText()+
           "', gmail = '"+txGmail.getText()+
           "', password = '"+txPassword.getText()+
           "', level = '"+txLevel.getSelectedItem()+ 
           "'WHERE id = '"+txID.getText()+"'";

   statLgn.executeUpdate(SQL);
   muncultabel();
   statLgn.close();
   con.close();
   JOptionPane.showMessageDialog(null, "Berhasil Merubah");
   Reset();

}catch(Exception exc){
   System.err.println(exc.getMessage());
}
}

private void hapus(){
try{
    koneksi();
    statLgn = con.createStatement();
    String SQL = "DELETE FROM login WHERE id = '"+txID.getText()+"'";
    statLgn.executeUpdate(SQL);
    muncultabel();
    statLgn.close();
    con.close();
    Reset();
    JOptionPane.showMessageDialog(null, "Berhasil Menghapus");

}catch(Exception exc){
    System.err.println(exc.getMessage());
}
}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txID = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        txPassword = new javax.swing.JTextField();
        txConPassword = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txUsername = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabeldata = new javax.swing.JTable();
        txLevel = new javax.swing.JComboBox<>();
        EDIT = new rojerusan.RSMaterialButtonRectangle();
        SelamatDatang1 = new javax.swing.JLabel();
        panelRound1 = new Custom_Palette_Class.PanelRound();
        DAFATAR = new rojerusan.RSMaterialButtonRectangle();
        HAPUS = new rojerusan.RSMaterialButtonRectangle();
        RESET = new rojerusan.RSMaterialButtonRectangle();
        tmblbatal = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txGmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        txID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txID.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 26, 114), 3, true));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ImageAsset/income.png")).getImage());
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        panel.setBackground(new java.awt.Color(255, 255, 255));

        txPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 26, 114)));

        txConPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txConPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 26, 114)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Username");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Password");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Konfirmasi Password");

        txUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txUsername.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 26, 114)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Masuk Sebagai");

        tabeldata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabeldata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeldataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabeldata);

        txLevel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "TELLER" }));
        txLevel.setPreferredSize(new java.awt.Dimension(64, 31));

        EDIT.setBackground(new java.awt.Color(0, 26, 114));
        EDIT.setText("EDIT");
        EDIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EDITActionPerformed(evt);
            }
        });

        SelamatDatang1.setFont(new java.awt.Font("Gilroy ExtraBold", 1, 24)); // NOI18N
        SelamatDatang1.setForeground(new java.awt.Color(0, 26, 114));
        SelamatDatang1.setText("Data Akun");

        panelRound1.setBackground(new java.awt.Color(225, 225, 225));
        panelRound1.setRoundBottomLeft(10);
        panelRound1.setRoundBottomRight(10);
        panelRound1.setRoundTopLeft(10);
        panelRound1.setRoundTopRight(10);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        DAFATAR.setBackground(new java.awt.Color(0, 26, 114));
        DAFATAR.setText("DAFTAR");
        DAFATAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DAFATARActionPerformed(evt);
            }
        });

        HAPUS.setBackground(new java.awt.Color(0, 26, 114));
        HAPUS.setText("HAPUS");
        HAPUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HAPUSActionPerformed(evt);
            }
        });

        RESET.setBackground(new java.awt.Color(0, 26, 114));
        RESET.setText("RESET");
        RESET.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RESETActionPerformed(evt);
            }
        });

        tmblbatal.setBackground(new java.awt.Color(204, 0, 0));
        tmblbatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tmblbatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tmblbatalMouseClicked(evt);
            }
        });
        tmblbatal.setLayout(new java.awt.GridBagLayout());

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("X");
        tmblbatal.add(jLabel10, new java.awt.GridBagConstraints());

        txGmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txGmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 26, 114)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Gmail");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(SelamatDatang1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tmblbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txLevel, javax.swing.GroupLayout.Alignment.LEADING, 0, 280, Short.MAX_VALUE)
                                        .addComponent(txConPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txGmail, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txUsername, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addGap(22, 22, 22)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)))
                        .addGap(22, 22, 22))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(DAFATAR, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HAPUS, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RESET, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(SelamatDatang1))
                    .addComponent(tmblbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txGmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txConPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DAFATAR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HAPUS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RESET, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabeldataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeldataMouseClicked
        try {
            koneksi();
            int row =tabeldata.getSelectedRow();
            String tabel_klik=(tabeldata.getModel().getValueAt(row, 0).toString());
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet sql = stm.executeQuery("select * from login where id='"+tabel_klik+"'");
            if(sql.next()){
                String id = sql.getString("id");
                txID.setText(id);
                String Gmail = sql.getString("gmail");
                txGmail.setText(Gmail);
                String user = sql.getString("username");
                txUsername.setText(user);
                String pass = sql.getString("password");
                txPassword.setText(pass);
                txConPassword.setText(pass);
                String lvl = sql.getString("level");
                txLevel.setSelectedItem(lvl);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tabeldataMouseClicked

    private void DAFATARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DAFATARActionPerformed
        daftar();
    }//GEN-LAST:event_DAFATARActionPerformed

    private void EDITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EDITActionPerformed
        edit();
    }//GEN-LAST:event_EDITActionPerformed

    private void HAPUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HAPUSActionPerformed
        hapus();
    }//GEN-LAST:event_HAPUSActionPerformed

    private void RESETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RESETActionPerformed
        Reset();
    }//GEN-LAST:event_RESETActionPerformed

    private void tmblbatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tmblbatalMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_tmblbatalMouseClicked

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
            java.util.logging.Logger.getLogger(kelola_akun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kelola_akun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kelola_akun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kelola_akun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kelola_akun().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle DAFATAR;
    private rojerusan.RSMaterialButtonRectangle EDIT;
    private rojerusan.RSMaterialButtonRectangle HAPUS;
    private rojerusan.RSMaterialButtonRectangle RESET;
    private javax.swing.JLabel SelamatDatang1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private Custom_Palette_Class.PanelRound panelRound1;
    private javax.swing.JTable tabeldata;
    private javax.swing.JPanel tmblbatal;
    private javax.swing.JTextField txConPassword;
    private javax.swing.JTextField txGmail;
    private javax.swing.JTextField txID;
    private javax.swing.JComboBox<String> txLevel;
    private javax.swing.JTextField txPassword;
    private javax.swing.JTextField txUsername;
    // End of variables declaration//GEN-END:variables
}
