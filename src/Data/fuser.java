/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import static FAkses.FRegister.checkusername;
import static FAkses.FRegister.generateIDuser;
import FAkses.MenuItem;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author PC
 */
public class fuser extends javax.swing.JFrame {

    /**
     * Creates new form fuser
     */
    public fuser() {
        initComponents();
        generateIDuser();
        
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

        menus.add(jPanel1);
        tampil();
    }

    public void generateIDuser() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String id_user = null;
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String idToCheck = String.format("USER%03d", newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM user WHERE id_user = ?";
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
            txtiduser.setText(id_user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
     public void tampil() {
        String cabang = SESSION.SessionCabang.getSessionCabang();
        String st = SESSION.SessionStatus2.getSessionStatus2();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM user";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(cabang);
            System.out.println(st);

            ResultSet rs = stmt.executeQuery();

            // Menghapus data sebelumnya dari model tabel
            DefaultTableModel tableModel = (DefaultTableModel) rSTableMetro1.getModel();
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("id_user");
            tableModel.addColumn("username");
            tableModel.addColumn("nama");
            tableModel.addColumn("alamat");
            tableModel.addColumn("jenis_kelamin");
            tableModel.addColumn("no_telpon");
            tableModel.addColumn("email");
            tableModel.addColumn("level");

            // Mengisi data ke model tabel
            while (rs.next()) {
                String id_user = rs.getString("id_user");
                String username = rs.getString("username");
                String nama = rs.getString("nama");
                String alamat = rs.getString("alamat");
                String jenis_kelamin = rs.getString("jenis_kelamin");
                String no_telpon = rs.getString("no_telpon");
                String email = rs.getString("email");
                String level = rs.getString("level");

                Object[] rowData = {id_user, username, nama, alamat, jenis_kelamin, no_telpon, email,level};
                tableModel.addRow(rowData);
            }

            // Set model tabel pada objek JTable
            rSTableMetro1.setModel(tableModel);

            // Mengatur lebar kolom
            TableColumnModel columnModel = rSTableMetro1.getColumnModel();
            columnModel.getColumn(4).setPreferredWidth(10); // Kolom Jumlah

            // Mengatur tinggi baris
            rSTableMetro1.setRowHeight(30); // Tinggi baris menjadi 30 piksel
        } catch (SQLException e) {
            e.printStackTrace();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtiduser = new javax.swing.JTextField();
        txtnama = new javax.swing.JTextField();
        txtalamat = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rSTableMetro1 = new rojerusan.RSTableMetro();
        txtpassword = new javax.swing.JPasswordField();
        jk1 = new javax.swing.JRadioButton();
        jk2 = new javax.swing.JRadioButton();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        jTextField1 = new javax.swing.JTextField();
        menus = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel9.setText("nama user");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 80, -1));

        jLabel10.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel10.setText("id user");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 80, -1));

        jLabel11.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel11.setText("password");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, 80, -1));

        jLabel12.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel12.setText("alamat");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 80, -1));

        jLabel13.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel13.setText("jenis kelamin");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 80, -1));

        jLabel14.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel14.setText("no_telpon");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 80, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Gudang", "Direktur", "Marketing" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 150, -1));

        jLabel15.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel15.setText("email");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 80, -1));
        jPanel1.add(txtiduser, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 150, -1));
        jPanel1.add(txtnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 150, -1));
        jPanel1.add(txtalamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 150, -1));
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 150, -1));

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 150, -1));

        jLabel16.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel16.setText("jabatan");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 80, -1));

        jLabel17.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel17.setText("username");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 80, -1));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 510, 770, 190));
        jPanel1.add(txtpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 240, 150, -1));

        buttonGroup1.add(jk1);
        jk1.setText("laki_laki");
        jPanel1.add(jk1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, -1, -1));

        buttonGroup1.add(jk2);
        jk2.setText("perempuan");
        jPanel1.add(jk2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 90, -1));

        rSMaterialButtonRectangle1.setText("simpan");
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 280, 140, 50));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 150, -1));

        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(menus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 180, 600));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/sidebar.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/close.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 0, -1, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel4.setText("FORM USER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 160, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Form Alat - Clean.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
        String username = jTextField1.getText();
        String password = txtpassword.getText();
        String nama = txtnama.getText();
        String alamat = txtalamat.getText();
        String notel = jTextField5.getText();
        String email = jTextField6.getText();
        String jeniskelamin;

        if (jk1.isSelected()) {
            jeniskelamin = "laki_laki";
        } else if (jk2.isSelected()) {
            jeniskelamin = "perempuan";
        } else {
            JOptionPane.showMessageDialog(null, "Pilih level terlebih dahulu!");
            return;
        }

        String level = jComboBox1.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua teks wajib diisi");
        } else {
            if (checkusername(username)) {
                JOptionPane.showMessageDialog(null, "Username sudah terdaftar");
            } else {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                    String sql = "INSERT INTO user (id_user, username, password, nama, alamat, jenis_kelamin, no_telpon, email, level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setString(1, txtiduser.getText());
                        statement.setString(2, username);
                        statement.setString(3, password);
                        statement.setString(4, nama);
                        statement.setString(5, alamat);
                        statement.setString(6, jeniskelamin);
                        statement.setString(7, notel);
                        statement.setString(8, email);
                        statement.setString(9, level);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

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
            FlatLaf.registerCustomDefaultsSource("raven.theme"); // Opsional, jika Anda memiliki file tema kustom
            FlatLaf.setup(new FlatLightLaf()); // Mengatur FlatLaf dengan tema Flat Dark (contoh)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new fuser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JRadioButton jk1;
    private javax.swing.JRadioButton jk2;
    private javax.swing.JPanel menus;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSTableMetro rSTableMetro1;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtiduser;
    private javax.swing.JTextField txtnama;
    private javax.swing.JPasswordField txtpassword;
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
