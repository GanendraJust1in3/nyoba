/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nizarislamudin
 */
public class input_data_karyawan extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();        
    private Connection con;
    private Statement stat;
    private ResultSet res;
    PreparedStatement pst;
    Statement statLgn;
    
    /**
     * Creates new form INPUT
     */
public input_data_karyawan() {
        initComponents();
        koneksi();
        comboboxdatabase();
        
        table.addColumn("Nama");
        table.addColumn("Alamat");
        table.addColumn("Tanggal Lahir");
        table.addColumn("Jabatan");
        table.addColumn("Gaji Pokok");
        table.addColumn("Gaji Lembur");
        tampilData();
        
        tabelkaryawan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelkaryawan.getTableHeader().setOpaque(false);
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
     
    private void Clear(){
        txNAMA.setText(null);
        txALAMAT.setText(null);
        txPOKOK.setText(null);
        txLEMBUR.setText(null);
  }
          
private void comboboxdatabase(){
    try{
        String query ="select * from data_jabatan";
        pst=con.prepareStatement(query);
        res=pst.executeQuery();
        
        while(res.next()){
        String nama_jabatan=res.getString("jabatan");
        txJABATAN.addItem(nama_jabatan);
        }
}catch(Exception e){
}
}

public void SetGaji(){
    Object jabatan;
    jabatan = txJABATAN.getSelectedItem();
    System.out.println(jabatan);
    try{
    Connection c = Koneksi.getKoneksi();
    Statement s = c.createStatement();
    String sql="select * from data_gaji WHERE jabatan='"+jabatan+"'";
    ResultSet r = s.executeQuery(sql);
    while(r.next()){
        txPOKOK.setText(r.getString("gajipokok"));
        txLEMBUR.setText(r.getString("gajilembur"));
    }
    }catch(Exception e){
}
}
private void simpan(){
        try{
            koneksi();
            statLgn = con.createStatement();
            String SQL = "insert into data_karyawan values('"
                    +'0'+"','"
                    +txNAMA.getText()+"','"
                    +txALAMAT.getText()+"','"
                    +txTTL.getText()+"','"
                    +txJABATAN.getSelectedItem()+"','"
                    +txPOKOK.getText()+"','"
                    +txLEMBUR.getText()+"')";
            statLgn.executeUpdate(SQL);
            tampilData();
            statLgn.close();
            con.close();
            Clear();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukkan");
            
        }catch(SQLException exc){
            System.err.println(exc.getMessage());
        }
}

private void edit(){
        try{
            koneksi();
            statLgn = con.createStatement();
            String SQL = "UPDATE data_karyawan SET id = '"+txID.getText()
                    + "',nama = '"+txNAMA.getText()+"', "
                    + "alamat = '"+txALAMAT.getText()+"', "
                    + "tanggallahir = '"+txTTL.getText()+"', "
                    + "jabatan = '"+txJABATAN.getSelectedItem()+"', "
                    + "gajipokok = '"+txPOKOK.getText()+"', "
                    + "gajilembur = '"+txLEMBUR.getText()+"' "
                    + "WHERE id = '"+txID.getText()+"'";
            statLgn.executeUpdate(SQL);
            tampilData();
            statLgn.close();
            con.close();
            Clear();
            JOptionPane.showMessageDialog(null, "Berhasil Merubah");
           
        }catch(Exception exc){
            System.err.println(exc.getMessage());
        }    
}

private void hapus(){
    try{
       koneksi();
       statLgn = con.createStatement();
       String SQL = "DELETE FROM data_karyawan WHERE nama = '"+txNAMA.getText()+"'";
       statLgn.executeUpdate(SQL);
       tampilData();
       statLgn.close();
       con.close();
       Clear();
       JOptionPane.showMessageDialog(null, "Berhasil Menghapus");

   }catch(Exception exc){
       System.err.println(exc.getMessage());
   }    
}

private void Search(String key){
        
    int row = tabelkaryawan.getRowCount();
   for(int a = 0 ; a < row ; a++){
       table.removeRow(0);
   }

   String cari = search.getText();

   String query = "SELECT * FROM `data_karyawan` WHERE "
           + "`nama`  LIKE '%"+cari+"%' OR "
           + "`alamat` LIKE '%"+cari+"%' OR"
           + "`tanggallahir` LIKE '%"+cari+"%' OR"
           + "`jabatan` LIKE '%"+cari+"%' ";
                
       try{
           Connection connect = Koneksi.getKoneksi();//memanggil koneksi
           Statement sttmnt = connect.createStatement();//membuat statement
           ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
           
           while (rslt.next()){
                //menampung data sementara
                   
                    String Nama = rslt.getString("nama");
                    String Alamat = rslt.getString("alamat");
                    String TanggalLahir = rslt.getString("tanggallahir");
                    String Jabatn = rslt.getString("jabatan");
                    String Pokok = rslt.getString("gajipokok");
                    String Lembur = rslt.getString("gajilembur");
                    
                //masukan semua data kedalam array
                String[] data = {Nama,Alamat,TanggalLahir,Jabatn,Pokok,Lembur};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tabelkaryawan.setModel(table);
       
    }catch(Exception e){
           System.out.println(e);
    }
}
     
public void tampilData(){
        //untuk mengahapus baris setelah input
        int row = tabelkaryawan.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String query = "SELECT * FROM `data_karyawan` ";
        
        try{
            Connection connect = Koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
            
            while (rslt.next()){
                //menampung data sementara
                   
                    String Nama = rslt.getString("nama");
                    String Alamat = rslt.getString("alamat");
                    String TanggalLahir = rslt.getString("tanggallahir");
                    String Jabatn = rslt.getString("jabatan");
                    String Pokok = rslt.getString("gajipokok");
                    String Lembur = rslt.getString("gajilembur");
                    
                //masukan semua data kedalam array
                String[] data = {Nama,Alamat,TanggalLahir,Jabatn,Pokok,Lembur};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tabelkaryawan.setModel(table);
            
        }catch(Exception e){
            System.out.println(e);
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

        dateChooser1 = new com.raven.datechooser.DateChooser();
        txID = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txJABATAN = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txNAMA = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelkaryawan = new javax.swing.JTable();
        tmblbatal = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txALAMAT = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txPOKOK = new javax.swing.JTextField();
        txLEMBUR = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txTTL = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        SelamatDatang1 = new javax.swing.JLabel();
        panelRound1 = new Custom_Palette_Class.PanelRound();
        SelamatDatang2 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        panelRound4 = new Custom_Palette_Class.PanelRound();
        tombolsimpan = new rojerusan.RSMaterialButtonRectangle();
        tombolhapus = new rojerusan.RSMaterialButtonRectangle();
        tomboledit = new rojerusan.RSMaterialButtonRectangle();
        tombolreset = new rojerusan.RSMaterialButtonRectangle();
        tomboltanggal = new Custom_Palette_Class.PanelRound();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        dateChooser1.setForeground(new java.awt.Color(0, 26, 114));
        dateChooser1.setTextRefernce(txTTL);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ImageAsset/income.png")).getImage());
        setUndecorated(true);
        setType(java.awt.Window.Type.POPUP);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Jabatan");

        txJABATAN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txJABATAN.setForeground(new java.awt.Color(37, 37, 37));
        txJABATAN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txJABATANActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Alamat");

        txNAMA.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txNAMA.setForeground(new java.awt.Color(37, 37, 37));
        txNAMA.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Nama");

        tabelkaryawan.setFocusable(false);
        tabelkaryawan.setRowHeight(25);
        tabelkaryawan.setSelectionBackground(new java.awt.Color(0, 120, 165));
        tabelkaryawan.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelkaryawan.getTableHeader().setReorderingAllowed(false);
        tabelkaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkaryawanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelkaryawan);

        tmblbatal.setBackground(new java.awt.Color(204, 0, 0));
        tmblbatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tmblbatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tmblbatalMouseClicked(evt);
            }
        });
        tmblbatal.setLayout(new java.awt.GridBagLayout());

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("X");
        tmblbatal.add(jLabel9, new java.awt.GridBagConstraints());

        txALAMAT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txALAMAT.setForeground(new java.awt.Color(37, 37, 37));
        txALAMAT.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Gaji Pokok");

        txPOKOK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txPOKOK.setForeground(new java.awt.Color(37, 37, 37));
        txPOKOK.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        txPOKOK.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txPOKOK.setFocusable(false);

        txLEMBUR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txLEMBUR.setForeground(new java.awt.Color(37, 37, 37));
        txLEMBUR.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        txLEMBUR.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txLEMBUR.setFocusable(false);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("Gaji Lembur");

        txTTL.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txTTL.setForeground(new java.awt.Color(37, 37, 37));
        txTTL.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txTTL.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        txTTL.setEnabled(false);
        txTTL.setFocusable(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Tanggal Lahir");

        SelamatDatang1.setFont(new java.awt.Font("Gilroy ExtraBold", 1, 24)); // NOI18N
        SelamatDatang1.setForeground(new java.awt.Color(0, 26, 114));
        SelamatDatang1.setText("Input Data Karyawan");

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

        SelamatDatang2.setFont(new java.awt.Font("Gilroy ExtraBold", 1, 24)); // NOI18N
        SelamatDatang2.setForeground(new java.awt.Color(0, 26, 114));
        SelamatDatang2.setText("Tabel Data Karyawan");

        search.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        search.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(37, 37, 37));
        jLabel13.setText("Cari :");

        panelRound4.setBackground(new java.awt.Color(225, 225, 225));
        panelRound4.setRoundBottomLeft(10);
        panelRound4.setRoundBottomRight(10);
        panelRound4.setRoundTopLeft(10);
        panelRound4.setRoundTopRight(10);

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        tombolsimpan.setBackground(new java.awt.Color(0, 26, 114));
        tombolsimpan.setBorder(null);
        tombolsimpan.setText("SIMPAN");
        tombolsimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolsimpanActionPerformed(evt);
            }
        });

        tombolhapus.setBackground(new java.awt.Color(0, 26, 114));
        tombolhapus.setBorder(null);
        tombolhapus.setText("HAPUS");
        tombolhapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombolhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolhapusActionPerformed(evt);
            }
        });

        tomboledit.setBackground(new java.awt.Color(0, 26, 114));
        tomboledit.setBorder(null);
        tomboledit.setText("EDIT");
        tomboledit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tomboledit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tomboleditActionPerformed(evt);
            }
        });

        tombolreset.setBackground(new java.awt.Color(0, 26, 114));
        tombolreset.setText("RESET");
        tombolreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolresetActionPerformed(evt);
            }
        });

        tomboltanggal.setBackground(new java.awt.Color(0, 26, 114));
        tomboltanggal.setRoundBottomLeft(10);
        tomboltanggal.setRoundBottomRight(10);
        tomboltanggal.setRoundTopLeft(10);
        tomboltanggal.setRoundTopRight(10);
        tomboltanggal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tomboltanggalMouseClicked(evt);
            }
        });
        tomboltanggal.setLayout(new java.awt.GridBagLayout());

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("...");
        tomboltanggal.add(jLabel14, new java.awt.GridBagConstraints());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tutorial Singkat");

        jLabel2.setText("1. Tetapkan karyawan yang menggunakan salah satu");

        jLabel3.setText("jabatan lalu isikan data karyawan pada kolom yang ");

        jLabel5.setText("2. Anda bisa merubah/hapus data dengan cara menekan");

        jLabel8.setText("data yang mau di rubah/ hapus pada tabel yang tersedia");

        jLabel15.setText("lalu rubah data dan tekan edit begitu pula untuk hapus. ");

        jLabel16.setText("3. Untuk menghapus kolom yang terisi tekan reset");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(SelamatDatang1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tmblbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(SelamatDatang2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tombolsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tomboledit, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tombolhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tombolreset, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txJABATAN, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txPOKOK, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(txLEMBUR, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelRound4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txNAMA, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(txALAMAT, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txTTL, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tomboltanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel5)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel15)
                                                    .addComponent(jLabel8)))
                                            .addComponent(jLabel16))))))
                        .addGap(22, 22, 22))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tmblbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(SelamatDatang1)))
                .addGap(16, 16, 16)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txLEMBUR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txPOKOK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txJABATAN, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tombolsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(tomboledit, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(tombolhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tombolreset, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txALAMAT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txTTL, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txNAMA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabel8)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16))
                                    .addComponent(tomboltanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelamatDatang2)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tmblbatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tmblbatalMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_tmblbatalMouseClicked

    private void tabelkaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelkaryawanMouseClicked
        // TODO add your handling code here:
        try {
            koneksi();
            int row =tabelkaryawan.getSelectedRow();
            String tabel_klik=(tabelkaryawan.getModel().getValueAt(row, 0).toString());
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet sql = stm.executeQuery("select * from data_karyawan where nama='"+tabel_klik+"'");
            if(sql.next()){
                String ID = sql.getString("id");
                txID.setText(ID);
                String NAMA = sql.getString("nama");
                txNAMA.setText(NAMA);
                String ALAMAT = sql.getString("alamat");
                txALAMAT.setText(ALAMAT);
                String LAHIR = sql.getString("tanggallahir");
                txTTL.setText(LAHIR);
                String JABATAN = sql.getString("jabatan");
                txJABATAN.setSelectedItem(JABATAN);
                String POKOK = sql.getString("gajipokok");
                txPOKOK.setText(POKOK);
                String LEMBUR = sql.getString("gajilembur");
                txLEMBUR.setText(LEMBUR);
            }
        } catch (Exception e) {}
    }//GEN-LAST:event_tabelkaryawanMouseClicked

    private void txJABATANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txJABATANActionPerformed
        // TODO add your handling code here:
        SetGaji();
        String yho;
        yho= (String) txJABATAN.getSelectedItem();
        if(yho==" "){
            txPOKOK.setText(" ");
        }
    }//GEN-LAST:event_txJABATANActionPerformed

    private void tombolsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolsimpanActionPerformed
        simpan();
    }//GEN-LAST:event_tombolsimpanActionPerformed

    private void tombolhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolhapusActionPerformed
        hapus();
    }//GEN-LAST:event_tombolhapusActionPerformed

    private void tomboleditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tomboleditActionPerformed
        edit();
    }//GEN-LAST:event_tomboleditActionPerformed

    private void tombolresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolresetActionPerformed
        Clear();
    }//GEN-LAST:event_tombolresetActionPerformed

    private void tomboltanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tomboltanggalMouseClicked
        dateChooser1.showPopup();
    }//GEN-LAST:event_tomboltanggalMouseClicked

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        String key = search.getText();

        if(key!=""){
            Search(key);
        }else{
            tampilData();
        }
    }//GEN-LAST:event_searchKeyReleased

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchActionPerformed

    
    
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
            java.util.logging.Logger.getLogger(input_data_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(input_data_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(input_data_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(input_data_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new input_data_karyawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SelamatDatang1;
    private javax.swing.JLabel SelamatDatang2;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private Custom_Palette_Class.PanelRound panelRound1;
    private Custom_Palette_Class.PanelRound panelRound4;
    private javax.swing.JTextField search;
    private javax.swing.JTable tabelkaryawan;
    private javax.swing.JPanel tmblbatal;
    private rojerusan.RSMaterialButtonRectangle tomboledit;
    private rojerusan.RSMaterialButtonRectangle tombolhapus;
    private rojerusan.RSMaterialButtonRectangle tombolreset;
    private rojerusan.RSMaterialButtonRectangle tombolsimpan;
    private Custom_Palette_Class.PanelRound tomboltanggal;
    private javax.swing.JTextField txALAMAT;
    private javax.swing.JTextField txID;
    private javax.swing.JComboBox<String> txJABATAN;
    private javax.swing.JTextField txLEMBUR;
    private javax.swing.JTextField txNAMA;
    private javax.swing.JTextField txPOKOK;
    private javax.swing.JTextField txTTL;
    // End of variables declaration//GEN-END:variables
}
