/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;

import static Frame.Koneksi.getKoneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author nizarislamudin
 */
public class menu_teller extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();
    private Connection con;
    private Statement stat;
    private ResultSet res;
    PreparedStatement pst;
    Statement statLgn;
    Timer t ;
    SimpleDateFormat st ; 

    /**
     * Creates new form Transaksi_Penggajian
     */
    public menu_teller() {
        initComponents();
        koneksi();
        comboboxdatabase();
        Waktu();
        
        tabwel.setModel(table);
        table.addColumn("NAMA");
        table.addColumn("JABATAN");
        table.addColumn("GAJI POKOK");
        table.addColumn("GAJI BERSIH");
        table.addColumn("TANGGAL");
        this.setExtendedState(MAXIMIZED_BOTH);
        muncultabel();
        labeluser.setText(Koneksi.getUsername());
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
    
private void muncultabel(){   
    int row = tabwel.getRowCount();
    for(int a = 0 ; a < row ; a++){
        table.removeRow(0);
    }
    // Memberi nama pada setiap kolom tabel
        try{
        // Mengambil data dari database
        res=stat.executeQuery("select * from data_transaksi");

        while (res.next())
        {
        // Mengambil data dari database berdasarkan nama kolom pada tabel
        // Lalu di tampilkan ke dalam JTable
        table.addRow(new Object[]{
        res.getString("nama"),
        res.getString("jabatan"),
        res.getString("gajipokok"),
        res.getString("gajibersih"),
        res.getString("tanggal")
        });
        }

        }catch (Exception e){
    }
}
    
private void comboboxdatabase(){
try{
    String query="select * from data_karyawan";
    pst=con.prepareStatement(query);
    res=pst.executeQuery();

    while(res.next()){
    String name=res.getString("nama");
    txnama.addItem(name);
    }
    }catch(Exception e){
    }
}
String karyawan = "CARI NAMA PEGAWAI";
private void Clear(){
        txid.setText(null);
        jabatan.setText(null);
        txnama.setSelectedItem(karyawan);
        pokok.setText(null);
        lembur.setText(null);
        perjam.setText(Integer.toString(nol));
        txtunjangan.setText(null);
        txbersih.setText(null);
  }

public void settext(){
    Object muncul;
    muncul = txnama.getSelectedItem();
    System.out.println(muncul);
    try{
    Connection c = Koneksi.getKoneksi();
    Statement s = c.createStatement();
    String sql="select * from data_karyawan WHERE nama ='"+muncul+"'";
    ResultSet r = s.executeQuery(sql);
        while(r.next()){
            jabatan.setText(r.getString("jabatan"));
            pokok.setText(r.getString("gajipokok"));
            lembur.setText(r.getString("gajilembur"));
        }
    }catch(Exception e){
    }
}
public int jumlahlembur,hasil;
public int jumlahgaji,gajibersih;
public int nol = 0;

public void Hitung(String ketikmuncul){
    txtunjangan.setText("");
    try {
        String sql="select * from data_karyawan WHERE gajilembur ='"+lembur.getText()+"'";
        java.sql.Connection conn=(java.sql.Connection)Koneksi.getKoneksi();
        java.sql.Statement s = conn.createStatement();
        ResultSet r = s.executeQuery(sql);
            
        while(r.next()){
               int gaji = r.getInt("gajilembur");
                jumlahlembur = Integer.parseInt(perjam.getText());
                
                hasil = gaji * jumlahlembur ;
                
                
            }
    }catch(Exception e){
        System.out.println(e);
}
    txtunjangan.setText("");
    try {
        String sql="select * from data_karyawan WHERE gajipokok ='"+pokok.getText()+"'";
        java.sql.Connection conn=(java.sql.Connection)Koneksi.getKoneksi();
        java.sql.Statement s = conn.createStatement();
        ResultSet r = s.executeQuery(sql);
            
        while(r.next()){
               int gajipk = r.getInt("gajipokok");
                
                gajibersih = gajipk + hasil ;
                
            }
    }catch(Exception e){
        System.out.println(e);
}
    txtunjangan.setText(Integer.toString(hasil));
    txbersih.setText(Integer.toString(gajibersih));
}

private void edit(){
    try{
            koneksi();
            statLgn = con.createStatement();
            String SQL = "update data_transaksi SET id = '"+txid.getText()+"', "
                    + "nama = '"+txnama.getSelectedItem()+"', "
                    + "jabatan = '"+jabatan.getText()+"', "
                    + "gajipokok = '"+pokok.getText()+"', "
                    + "gajilembur = '"+lembur.getText()+"', "
                    + "jumlahlembur = '"+perjam.getText()+"', "
                    + "tunjanganlembur = '"+txtunjangan.getText()+"', "
                    + "gajibersih = '"+txbersih.getText()+"', "
                    + "tanggal = '"+jDate.getText()+"' WHERE id = '"+txid.getText()+"'";
            statLgn.executeUpdate(SQL);
            String sql = "update laporan_transaksi SET id = '"+txid.getText()+"', "
                    + "nama = '"+txnama.getSelectedItem()+"', "
                    + "jabatan = '"+jabatan.getText()+"', "
                    + "gajipokok = '"+pokok.getText()+"', "
                    + "gajilembur = '"+lembur.getText()+"', "
                    + "jumlahlembur = '"+perjam.getText()+"', "
                    + "tunjanganlembur = '"+txtunjangan.getText()+"', "
                    + "gajibersih = '"+txbersih.getText()+"', "
                    + "tanggal = '"+jDate.getText()+"' WHERE id = '"+txid.getText()+"'";
            statLgn.executeUpdate(sql);
            muncultabel();
            statLgn.close();
            con.close();
            Clear();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dirubah");
           
        }catch(Exception exc){
            System.err.println(exc.getMessage());
        }
}

private void simpan(){
        try{
            koneksi();
            statLgn = con.createStatement();
            String SQL = "insert into data_transaksi values('"
                    +'0'+"','"
                    +txnama.getSelectedItem()+"','"
                    +jabatan.getText()+"','"
                    +pokok.getText()+"','"
                    +lembur.getText()+"','"
                    +perjam.getText()+"','"
                    +txtunjangan.getText()+"','"
                    +txbersih.getText()+"','"
                    +jDate.getText()+"')";
            statLgn.executeUpdate(SQL);
            String sql = "insert into laporan_transaksi values('"
                    +'0'+"','"
                    +txnama.getSelectedItem()+"','"
                    +jabatan.getText()+"','"
                    +pokok.getText()+"','"
                    +lembur.getText()+"','"
                    +perjam.getText()+"','"
                    +txtunjangan.getText()+"','"
                    +txbersih.getText()+"','"
                    +jDate.getText()+"')";
            statLgn.executeUpdate(sql);
            muncultabel();
            statLgn.close();
            con.close();
            Clear();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            
        }catch(SQLException exc){
            System.err.println(exc.getMessage());
        }
}

private void hapus() {
     try{
            koneksi();
            statLgn = con.createStatement();
            String SQL = "DELETE FROM data_transaksi WHERE nama = '"+txnama.getSelectedItem()+"'";
            statLgn.executeUpdate(SQL);
            muncultabel();
            statLgn.close();
            con.close();
            Clear();
            JOptionPane.showMessageDialog(null, "Berhasil Menghapus");
           
        }catch(Exception exc){
            System.err.println(exc.getMessage());
        }
}

public void Waktu(){
  t = new Timer(0, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        Date dt  =new Date();
        st = new SimpleDateFormat("hh:mm:ss a");
        
        String tt = st.format(dt);
        Time.setText(tt);
        
        }
    });
    t.start();
}

private void Search(String key){
    int row = tabwel.getRowCount();
    for(int a = 0 ; a < row ; a++){
        table.removeRow(0);
    }

    String cari = search.getText();

    String query = "SELECT * FROM `data_transaksi` WHERE "
            + "`nama`  LIKE '%"+cari+"%' OR "
            + "`jabatan` LIKE '%"+cari+"%' OR"
            + "`tanggal` LIKE '%"+cari+"%' OR"
            + "`gajipokok` LIKE '%"+cari+"%' ";

   try{
       Connection connect = Koneksi.getKoneksi();//memanggil koneksi
       Statement sttmnt = connect.createStatement();//membuat statement
       ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query

       while (rslt.next()){
            //menampung data sementara

                String Nama = rslt.getString("nama");
                String Jabatan = rslt.getString("jabatan");
                String GajiPokok = rslt.getString("gajipokok");
                String GajiBersih = rslt.getString("gajibersih");
                String Tgl = rslt.getString("tanggal");

            //masukan semua data kedalam array
            String[] data = {Nama,Jabatan,GajiPokok,GajiBersih,Tgl};
            //menambahakan baris sesuai dengan data yang tersimpan diarray
            table.addRow(data);
        }
            //mengeset nilai yang ditampung agar muncul di table
            tabwel.setModel(table);


    }catch(Exception e){
           System.out.println(e);
    }
}

private void cetakstruk(){
    //Menangkap isi pada kolom jTextField kedalam variable string
    String tgl = jDate.getText();
    String karyawan = (String) txnama.getSelectedItem();
    String namajabatan = (String) jabatan.getText();
    String gajip = pokok.getText();
    String gajil = lembur.getText();
    String tunjangan = txtunjangan.getText();
    String gajib = txbersih.getText();
    
try {
    String reportPath = "src/iReport/Slip_gaji.jasper";
    Connection conn = getKoneksi();

    //Memasukan variable string dengan isi dari jTextField ke paramaters yang dibuat di iReport
    HashMap<String, Object>paramaters = new HashMap<>();   
    paramaters.put("tgl",tgl);
    paramaters.put("nama",karyawan);
    paramaters.put("jabatan",namajabatan);
    paramaters.put("pokok",gajip);
    paramaters.put("lembur",gajil);
    paramaters.put("tlembur",tunjangan);
    paramaters.put("bersih",gajib);
    
    JasperPrint print = JasperFillManager.fillReport(reportPath, paramaters, conn);
    JasperViewer viewer = new JasperViewer(print,false);
    viewer.setVisible(true);
}catch (Exception e){
    JOptionPane.showMessageDialog(this,"Error displaying report: "+ e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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

        txid = new javax.swing.JTextField();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        garis = new javax.swing.JPanel();
        jDate = new javax.swing.JTextField();
        jabatan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        perjam = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabwel = new javax.swing.JTable();
        txnama = new javax.swing.JComboBox<>();
        panelRound1 = new Custom_Palette_Class.PanelRound();
        jLabel4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pokok = new javax.swing.JTextField();
        lembur = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtunjangan = new javax.swing.JTextField();
        txbersih = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        BAYAR = new rojerusan.RSMaterialButtonRectangle();
        SelamatDatang2 = new javax.swing.JLabel();
        panelRound3 = new Custom_Palette_Class.PanelRound();
        search = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        EDIT = new rojerusan.RSMaterialButtonRectangle();
        HAPUS = new rojerusan.RSMaterialButtonRectangle();
        RESET = new rojerusan.RSMaterialButtonRectangle();
        labeluser = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        SIMPAN = new rojerusan.RSMaterialButtonRectangle();
        jMenuBar1 = new javax.swing.JMenuBar();
        Menu = new javax.swing.JMenu();
        Menu_Laporan = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        Menu_About = new javax.swing.JMenuItem();
        Menu_Logout = new javax.swing.JMenuItem();

        txid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 26, 114), 3, true));
        txid.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txid.setEnabled(false);
        txid.setFocusable(false);

        dateChooser1.setForeground(new java.awt.Color(0, 0, 153));
        dateChooser1.setAutoscrolls(true);
        dateChooser1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dateChooser1.setTextRefernce(jDate);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ImageAsset/income.png")).getImage());
        setUndecorated(true);

        garis.setBackground(new java.awt.Color(255, 255, 255));
        garis.setPreferredSize(new java.awt.Dimension(1920, 517));

        jDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jDate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        jDate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jDate.setFocusable(false);

        jabatan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jabatan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jabatan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        jabatan.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(37, 37, 37));
        jLabel7.setText("Jumlah Jam Lembur");

        perjam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        perjam.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        perjam.setText("0");
        perjam.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        perjam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                perjamKeyReleased(evt);
            }
        });

        tabwel.setModel(new javax.swing.table.DefaultTableModel(
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
        tabwel.setFocusable(false);
        tabwel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabwelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabwel);

        txnama.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txnama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CARI NAMA PEGAWAI" }));
        txnama.setToolTipText("");
        txnama.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txnama.setMinimumSize(new java.awt.Dimension(64, 31));
        txnama.setPreferredSize(new java.awt.Dimension(64, 32));
        txnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txnamaActionPerformed(evt);
            }
        });

        panelRound1.setBackground(new java.awt.Color(204, 204, 204));
        panelRound1.setRoundBottomLeft(15);
        panelRound1.setRoundBottomRight(15);
        panelRound1.setRoundTopLeft(15);
        panelRound1.setRoundTopRight(15);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 26, 114));
        jLabel4.setText("Gaji Pokok");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 26, 114));
        jLabel15.setText("Gaji Lembur/jam");

        pokok.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pokok.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pokok.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        pokok.setFocusable(false);

        lembur.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lembur.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lembur.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        lembur.setFocusable(false);
        lembur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lemburActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 26, 114));
        jLabel17.setText("Gaji Sementara");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pokok, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lembur))
                        .addGap(15, 15, 15))))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(pokok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(lembur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(37, 37, 37));
        jLabel11.setText("Tunjangan Lembur");

        txtunjangan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtunjangan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtunjangan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        txtunjangan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtunjangan.setFocusable(false);
        txtunjangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtunjanganActionPerformed(evt);
            }
        });

        txbersih.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txbersih.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txbersih.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        txbersih.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txbersih.setFocusable(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(37, 37, 37));
        jLabel10.setText("Gaji Bersih");

        BAYAR.setBackground(new java.awt.Color(0, 26, 114));
        BAYAR.setText("BAYAR");
        BAYAR.setFont(new java.awt.Font("Roboto Medium", 1, 18)); // NOI18N
        BAYAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAYARActionPerformed(evt);
            }
        });

        SelamatDatang2.setFont(new java.awt.Font("Gilroy ExtraBold", 1, 24)); // NOI18N
        SelamatDatang2.setForeground(new java.awt.Color(0, 26, 114));
        SelamatDatang2.setText("Transaksi Gaji");

        panelRound3.setBackground(new java.awt.Color(225, 225, 225));
        panelRound3.setRoundBottomLeft(10);
        panelRound3.setRoundBottomRight(10);
        panelRound3.setRoundTopLeft(10);
        panelRound3.setRoundTopRight(10);

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        search.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        search.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(37, 37, 37));
        jLabel8.setText("Cari :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(37, 37, 37));
        jLabel9.setText("Jabatan");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(37, 37, 37));
        jLabel12.setText("Nama");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(37, 37, 37));
        jLabel13.setText("Tgl Transaksi");

        EDIT.setBackground(new java.awt.Color(0, 26, 114));
        EDIT.setText("EDIT");
        EDIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EDITActionPerformed(evt);
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

        labeluser.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labeluser.setForeground(new java.awt.Color(37, 37, 37));
        labeluser.setText("Username");

        Time.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Time.setForeground(new java.awt.Color(37, 37, 37));
        Time.setText("00:00:00 PM");

        SIMPAN.setBackground(new java.awt.Color(0, 26, 114));
        SIMPAN.setText("SIMPAN");
        SIMPAN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SIMPANActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout garisLayout = new javax.swing.GroupLayout(garis);
        garis.setLayout(garisLayout);
        garisLayout.setHorizontalGroup(
            garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(garisLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, garisLayout.createSequentialGroup()
                        .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))
                    .addGroup(garisLayout.createSequentialGroup()
                        .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(garisLayout.createSequentialGroup()
                                .addComponent(BAYAR, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(233, 233, 233)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(perjam, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(garisLayout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(garisLayout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(txtunjangan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(garisLayout.createSequentialGroup()
                                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, garisLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jDate, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, garisLayout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txnama, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, garisLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(84, 84, 84)
                                        .addComponent(jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(22, 22, 22)
                                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(search))
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(EDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HAPUS, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(RESET, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(labeluser)
                            .addComponent(Time)
                            .addComponent(SIMPAN, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(garisLayout.createSequentialGroup()
                        .addComponent(SelamatDatang2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        garisLayout.setVerticalGroup(
            garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(garisLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(SelamatDatang2)
                .addGap(22, 22, 22)
                .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(garisLayout.createSequentialGroup()
                        .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, garisLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jDate)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, garisLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, garisLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(garisLayout.createSequentialGroup()
                                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtunjangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(perjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addGroup(garisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(txbersih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(BAYAR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22))
                    .addGroup(garisLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(labeluser)
                        .addGap(18, 18, 18)
                        .addComponent(Time)
                        .addGap(18, 18, 18)
                        .addComponent(SIMPAN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(HAPUS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(RESET, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Menu.setText("Menu");

        Menu_Laporan.setText("Hasil Transaksi");
        Menu_Laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_LaporanActionPerformed(evt);
            }
        });
        Menu.add(Menu_Laporan);

        jMenuItem1.setText("Juknis");
        Menu.add(jMenuItem1);

        Menu_About.setText("Tentang");
        Menu_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_AboutActionPerformed(evt);
            }
        });
        Menu.add(Menu_About);

        Menu_Logout.setText("Keluar");
        Menu_Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_LogoutActionPerformed(evt);
            }
        });
        Menu.add(Menu_Logout);

        jMenuBar1.add(Menu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(garis, javax.swing.GroupLayout.PREFERRED_SIZE, 1290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(garis, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtunjanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtunjanganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtunjanganActionPerformed

    private void lemburActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lemburActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lemburActionPerformed

    private void txnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txnamaActionPerformed
        settext();
        String yho;
        yho= (String) txnama.getSelectedItem();
        if(yho==" "){
            jabatan.setText(" ");
            pokok.setText(" ");
            lembur.setText(" ");
        }
    }//GEN-LAST:event_txnamaActionPerformed

    private void tabwelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabwelMouseClicked
        try {
            koneksi();
            int row =tabwel.getSelectedRow();
            String tabel_klik=(tabwel.getModel().getValueAt(row, 0).toString());
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet sql = stm.executeQuery("select * from data_transaksi where nama ='"+tabel_klik+"'");
            if(sql.next()){

                String JMLHMBUR = sql.getString("jumlahlembur");
                perjam.setText(JMLHMBUR);

                String BERSIH = sql.getString("gajibersih");
                txbersih.setText(BERSIH);

                String TNJGLEMBUR = sql.getString("tunjanganlembur");
                txtunjangan.setText(TNJGLEMBUR);

                String TANGGAL = sql.getString("tanggal");
                jDate.setText(TANGGAL);

                String id = sql.getString("id");
                txid.setText(id);

                String NAME = sql.getString("nama");
                txnama.setSelectedItem(NAME);

                String JABATAN = sql.getString("jabatan");
                jabatan.setText(JABATAN);

                String POKOK = sql.getString("gajipokok");
                pokok.setText(POKOK);

                String LEMBUR = sql.getString("gajlembur");
                lembur.setText(LEMBUR);
            }
            } catch (Exception e) {
        }
    }//GEN-LAST:event_tabwelMouseClicked

    private void perjamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_perjamKeyReleased
        String key = perjam.getText();
        System.out.println(key);

        if(key!=""){
            Hitung(key);
        }else{
            Clear();
        }
    }//GEN-LAST:event_perjamKeyReleased

    private void BAYARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAYARActionPerformed
        JOptionPane.showMessageDialog(null, "Transaksi Berhasil");
        cetakstruk();
    }//GEN-LAST:event_BAYARActionPerformed

    private void EDITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EDITActionPerformed
        cetakstruk();
        edit();
    }//GEN-LAST:event_EDITActionPerformed

    private void HAPUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HAPUSActionPerformed
        hapus();
    }//GEN-LAST:event_HAPUSActionPerformed

    private void RESETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RESETActionPerformed
        Clear();
    }//GEN-LAST:event_RESETActionPerformed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        String key = search.getText();
        System.out.println(key);
        
        if(key!=""){
            Search(key);
        }else{
            muncultabel();
        }
    }//GEN-LAST:event_searchKeyReleased

    private void Menu_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_AboutActionPerformed
        about_apps a = new about_apps();
        a.setVisible(true);
    }//GEN-LAST:event_Menu_AboutActionPerformed

    private void Menu_LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_LogoutActionPerformed
        this.dispose();
        login a = new login();
        a.setVisible(true);
    }//GEN-LAST:event_Menu_LogoutActionPerformed

    private void Menu_LaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_LaporanActionPerformed
        laporan_gaji_teller a = new laporan_gaji_teller();
        a.setVisible(true);
    }//GEN-LAST:event_Menu_LaporanActionPerformed

    private void SIMPANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SIMPANActionPerformed
        simpan();
    }//GEN-LAST:event_SIMPANActionPerformed

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
            java.util.logging.Logger.getLogger(menu_teller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_teller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_teller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_teller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_teller().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle BAYAR;
    private rojerusan.RSMaterialButtonRectangle EDIT;
    private rojerusan.RSMaterialButtonRectangle HAPUS;
    private javax.swing.JMenu Menu;
    private javax.swing.JMenuItem Menu_About;
    private javax.swing.JMenuItem Menu_Laporan;
    private javax.swing.JMenuItem Menu_Logout;
    private rojerusan.RSMaterialButtonRectangle RESET;
    private rojerusan.RSMaterialButtonRectangle SIMPAN;
    private javax.swing.JLabel SelamatDatang2;
    private javax.swing.JLabel Time;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JPanel garis;
    private javax.swing.JTextField jDate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jabatan;
    private javax.swing.JLabel labeluser;
    private javax.swing.JTextField lembur;
    private Custom_Palette_Class.PanelRound panelRound1;
    private Custom_Palette_Class.PanelRound panelRound3;
    private javax.swing.JTextField perjam;
    private javax.swing.JTextField pokok;
    private javax.swing.JTextField search;
    private javax.swing.JTable tabwel;
    private javax.swing.JTextField txbersih;
    private javax.swing.JTextField txid;
    private javax.swing.JComboBox<String> txnama;
    private javax.swing.JTextField txtunjangan;
    // End of variables declaration//GEN-END:variables
}