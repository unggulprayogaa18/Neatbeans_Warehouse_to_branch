/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import FAkses.MenuItem;
import Koneksi.KoneksiDatabase;
import com.toedter.calendar.JCalendar;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author PC
 */
public class FKirimTOcabang extends javax.swing.JFrame {

    Connection conn = KoneksiDatabase.getKoneksi();

    /**
     * Creates new form FKirimTOcabang
     */
    public FKirimTOcabang() {
        initComponents();
        cektampil();
        tampilDanMasukkanKeComboBoxManual();
        execute();

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/sidebar.png"));
        Image image = backgroundImage.getImage();

        JPanel jPanel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

// Tambahkan panel ke dalam container
        menus.add(jPanel1);
    }

    public void getidG() {
//     cbidgudang.setText(SESSION2.SessionIdGudang.getSessionIdGudang());
//        String user = SESSION2.SessionIdGudang.getSessionIdGudang();
    }

    public void tampilDanMasukkanKeComboBoxManual() {
        try {
            String sql = "SELECT id_gudang FROM tb_gudang";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String kodeAlat = rs.getString("id_gudang");
                String user = SESSION2.SessionIdGudang.getSessionIdGudang();

                cbidgudang.addItem(kodeAlat);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        cbidgudang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedid = cbidgudang.getSelectedItem().toString();
                    try {
                        String sql = "SELECT kode_alat,nama_alat,merek,harga,jumlah FROM tb_gudang WHERE id_gudang = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, selectedid);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            String kodealat = rs.getString("kode_alat");
                            String namaAlat = rs.getString("nama_alat");
                            String harga = rs.getString("harga");
                            String jumlah = rs.getString("jumlah");

                            txtjumlahsotckgudang.setText(jumlah);
                            txtkodealat.setText(kodealat);
                            txtnamaalat.setText(namaAlat);
                            txtharga.setText(harga);

                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public String cariMerekBerdasarkanKode(String idgudang) {
        String merek = "";
        try {
            String sql = "SELECT merek FROM tb_gudang WHERE id_gudang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idgudang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                merek = rs.getString("merek");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return merek;
    }

    public static String idkirim() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String id_user = null;
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String idToCheck = String.format("IDKRM%03d", newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM tb_kirim_cabang WHERE id_kirim = ?";
                PreparedStatement checkIdStatement = conn.prepareStatement(checkIdQuery);
                checkIdStatement.setString(1, idToCheck);
                ResultSet resultSet = checkIdStatement.executeQuery();

                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }

                if (count == 0) {
                    id_user = idToCheck;
                    idExists = false;
                } else {
                    newId++;
                }
            }
            return id_user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cektampil() {
        String id = idkirim();
        contoh.setText(id);
    }

    public boolean cekStok(String kode_alat, int jumlahAmbil) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT jumlah FROM tb_gudang WHERE kode_alat = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kode_alat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int jumlh = rs.getInt("jumlah");
                if (jumlahAmbil > jumlh) {
                    JOptionPane.showMessageDialog(null, "Stok digudang tidak mencukupi");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        contoh = new javax.swing.JTextField();
        txtkodealat = new javax.swing.JTextField();
        txtnamaalat = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        rSTableMetro1 = new rojerusan.RSTableMetro();
        txtharga = new javax.swing.JTextField();
        txtjumlah = new javax.swing.JTextField();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        cbcabang = new rojerusan.RSComboMetro();
        jLabel2 = new javax.swing.JLabel();
        cbidgudang = new rojerusan.RSComboMetro();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        menus = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtjumlahsotckgudang = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contoh.setEditable(false);
        contoh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contohActionPerformed(evt);
            }
        });
        jPanel1.add(contoh, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 170, 30));

        txtkodealat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodealatActionPerformed(evt);
            }
        });
        jPanel1.add(txtkodealat, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 180, 30));

        txtnamaalat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaalatActionPerformed(evt);
            }
        });
        jPanel1.add(txtnamaalat, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 180, 30));

        rSTableMetro1.setModel(new javax.swing.table.DefaultTableModel(
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
        rSTableMetro1.setColorBackgoundHead(new java.awt.Color(5, 104, 158));
        jScrollPane1.setViewportView(rSTableMetro1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 500, 770, 200));

        txtharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargaActionPerformed(evt);
            }
        });
        jPanel1.add(txtharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 180, 30));

        txtjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjumlahActionPerformed(evt);
            }
        });
        jPanel1.add(txtjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 230, 170, 30));

        rSMaterialButtonRectangle1.setText("KIRIM");
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 280, 140, 40));

        cbcabang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PILIH CABANG", "Bandung", "Bali", "Jogja", "Semarang", "Jakarta", "Jatim", "Solo" }));
        cbcabang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbcabangActionPerformed(evt);
            }
        });
        jPanel1.add(cbcabang, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 170, -1));

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 0, 10)); // NOI18N
        jLabel2.setText("harga alat");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, -1, -1));

        cbidgudang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PILIH ID GUDANG" }));
        cbidgudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbidgudangActionPerformed(evt);
            }
        });
        jPanel1.add(cbidgudang, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 180, -1));

        jLabel3.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel3.setText("Stock gudang :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 290, -1, 20));

        jLabel4.setFont(new java.awt.Font("Lucida Fax", 0, 10)); // NOI18N
        jLabel4.setText("id kirim");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 150, -1, -1));

        jLabel5.setText("Kode alat");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, -1, -1));

        jLabel6.setFont(new java.awt.Font("Lucida Fax", 0, 10)); // NOI18N
        jLabel6.setText("nama alat");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, -1, -1));

        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(menus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 180, 630));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/sidebar.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        txtjumlahsotckgudang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtjumlahsotckgudang.setText("0");
        jPanel1.add(txtjumlahsotckgudang, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 290, 10, 20));

        jLabel7.setFont(new java.awt.Font("Lucida Fax", 0, 10)); // NOI18N
        jLabel7.setText("jumlah kirim");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/close.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 0, -1, 40));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel10.setText("FORM KIRIM TO CABANG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 230, 40));

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 0, 10)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Form Alat - Clean.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -3, 1030, 720));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void contohActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contohActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contohActionPerformed

    private void txtkodealatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodealatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodealatActionPerformed

    private void txtjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahActionPerformed

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
        String selectedValue = cbcabang.getSelectedItem().toString();
        if (selectedValue.equals("PILIH CABANG")) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih cabang");
        } else {
            JCalendar jCalendar = new JCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date selectedDate = jCalendar.getCalendar().getTime();
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
            String date = formattedDate;
            String generate = idkirim();
            String kode = txtkodealat.getText();
            String nama = txtnamaalat.getText();
            String jumlah0 = txtjumlah.getText();

            int jumlah = Integer.parseInt(jumlah0);
            String harga0 = txtharga.getText();
            int harga = Integer.parseInt(harga0);

            String id2 = cbidgudang.getSelectedItem().toString();
//        String idgudang = txtjumlah1.getText();
            String merekAlat = cariMerekBerdasarkanKode(id2);
            String status = "belum_diterima";
            String Cabang = cbcabang.getSelectedItem().toString();
            if (cekStok(txtkodealat.getText(), jumlah)) {
                try (
                        Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {

                    String sql = "INSERT INTO tb_kirim_cabang ("
                            + "id_kirim,id_gudang, kode_alat, nama_alat, jumlah, harga, merek, tglkirim, Cabang, status) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, generate);
                    statement.setString(2, id2);
                    statement.setString(3, kode);
                    statement.setString(4, nama);
                    statement.setInt(5, jumlah);
                    statement.setInt(6, harga);
                    statement.setString(7, merekAlat);
                    statement.setString(8, date);
                    statement.setString(9, Cabang);
                    statement.setString(10, status);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "data berhasil di kirim");
                    }
                    this.dispose();
                    new Data.FKirimTOcabang().setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(null, "pengambilan jangan melebihi stock");
            }
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void txtnamaalatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaalatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaalatActionPerformed

    private void txthargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthargaActionPerformed

    private void cbcabangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbcabangActionPerformed
        // TODO add your handling code here:\

    }//GEN-LAST:event_cbcabangActionPerformed

    private void cbidgudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbidgudangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbidgudangActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel9MouseClicked

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
            java.util.logging.Logger.getLogger(FKirimTOcabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FKirimTOcabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FKirimTOcabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FKirimTOcabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FKirimTOcabang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSComboMetro cbcabang;
    private rojerusan.RSComboMetro cbidgudang;
    private javax.swing.JTextField contoh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menus;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSTableMetro rSTableMetro1;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JLabel txtjumlahsotckgudang;
    private javax.swing.JTextField txtkodealat;
    private javax.swing.JTextField txtnamaalat;
    // End of variables declaration//GEN-END:variables
private void execute() {
        ImageIcon iconMaster = new ImageIcon(getClass().getResource("/img/db2.png"));
        ImageIcon iconSub = new ImageIcon(getClass().getResource("/img/db1.png"));

        MenuItem subDataAlat = new MenuItem(null, true, iconSub, "DataAlat", null);
        MenuItem subPenjualan = new MenuItem(null, true, iconSub, "Penjualan", null);
        MenuItem subSetor = new MenuItem(null, true, iconSub, "Setor", null);
        MenuItem subUser = new MenuItem(null, true, iconSub, "User", null);
        MenuItem subLaporansmi = new MenuItem(null, true, iconSub, "laporan smi", null);
        MenuItem submasterdata = new MenuItem(null, true, iconSub, "master data", null);
        MenuItem subCabang = new MenuItem(null, true, iconSub, "Cabang", null);

        subLaporansmi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                masterdata.Laporanpusatsmi mu = new  masterdata.Laporanpusatsmi();
                mu.setVisible(true);
                dispose();
            }
        });
        
        submasterdata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                masterdata.MasterData mu = new  masterdata.MasterData();
                mu.setVisible(true);
                dispose();
            }
        });
        
         // Membuat MenuItem "Gudang" dengan ActionListener
        subCabang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                other.Map mu = new other.Map();
                mu.setVisible(true);
                dispose();
            }
        });
        // Membuat MenuItem "Gudang" dengan ActionListener
        MenuItem subGudang = new MenuItem(null, true, iconSub, "Gudang", null);
        subGudang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                Data.FGudang formGudang = new Data.FGudang();
                formGudang.setVisible(true);
                dispose();
            }
        });

        subDataAlat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                Data.FAlat mu = new Data.FAlat();
                mu.setVisible(true);
                dispose();
            }
        });
        subPenjualan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                Transaksi.Fpenjualan mu = new  Transaksi.Fpenjualan();
                mu.setVisible(true);
                dispose();
            }
        });
        
           subSetor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                Transaksi.Fsetorsmi mu = new  Transaksi.Fsetorsmi();
                mu.setVisible(true);
                dispose();
            }
        });
        
         subUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                Data.fuser mu = new  Data.fuser();
                mu.setVisible(true);
                dispose();
            }
        });
         

        MenuItem menuMaster = new MenuItem(iconMaster, false, null, "Master", null, subDataAlat, subGudang, subCabang);
        MenuItem menuPenjualan = new MenuItem(iconMaster, false, null, "Penjualan", null, subPenjualan, subSetor);
        MenuItem menuDataUser = new MenuItem(iconMaster, false, null, "DataUser", null, subUser);
        MenuItem laporan = new MenuItem(iconMaster, false, null, "laporan", null, subLaporansmi, submasterdata);

        addMenu(menuMaster, menuPenjualan, menuDataUser, laporan);
    }


    private void addMenu(MenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
            menus.add(menu[i]);

            ArrayList<MenuItem> subMenu = menu[i].getSubMenu();

            for (MenuItem m : subMenu) {
                addMenu(m);
            }
        }

        menus.revalidate();
    }
}
