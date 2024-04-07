/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FAkses;

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
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author PC
 */
public class FMenu extends javax.swing.JFrame {

    String status;
    int count;

    /**
     * Creates new form FMenu
     */
    public FMenu() {

        jLabel4 = new javax.swing.JLabel();
        per = new javax.swing.JLabel();

        // Pengaturan properti dan tampilan JLabel jLabel4
        initComponents();

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

        hakakses();
        levellogin();
        tampil2();
        tampil();
//        tampilstock();
        tprofit();
    }

    public void tprofit() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT profitSMI, profit_smi FROM tb_penjualan_cabang, tb_penjualan_gudang";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            int totalProfit = 0;

            while (rs.next()) {
                int profitCabang = rs.getInt("profitSMI");
                int profitGudang = rs.getInt("profit_smi");
                int totalProfitPerBaris = profitCabang + profitGudang;
                totalProfit += totalProfitPerBaris;
            }

            if (totalProfit > 0) {
                String totalProfitText = String.valueOf(totalProfit);
                jLabel6.setText(totalProfitText);
            } else {
                jLabel6.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void tampilstock() {
//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
//            String sql = "SELECT stock FROM tb_data_alat";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//
//                count++;
//
//            }
//
//            if (count > 0) {
//                String countText = String.valueOf(count);
//                stock.setText(countText);
//
//            } else {
////                jLabel6.setText("TIDAK ADA PERIZINAN!!!");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void tampil2() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM user";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                count++;

            }

            if (count > 0) {
                String countText = String.valueOf(count);
                user.setText(countText + " USER");

            } else {
//                jLabel6.setText("TIDAK ADA PERIZINAN!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tampil() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_pengelolaan WHERE status = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "belum_diizinkan");
            ResultSet rs = stmt.executeQuery();

            int count = 0; // Menginisialisasi count dengan 0

            while (rs.next()) {
                String status = rs.getString("status");

                if (status.equalsIgnoreCase("belum_diizinkan")) {
                    count++;
                }
            }

            if (count > 0) {
                String countText = String.valueOf(count);
                jLabel4.setText(countText);
                jLabel9.setText("PENGAMBILAN DATA ALAT ");
                per.setText("MEMERLUKAN " + countText + " PERIZINAN!!");

                Timer blinkTimer = new Timer(500, new ActionListener() {
                    private boolean isVisible = true;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        isVisible = !isVisible;
                        jLabel4.setVisible(isVisible);
                        jLabel4.revalidate();
                        jLabel4.repaint();

                        jLabel9.setVisible(isVisible);
                        jLabel9.revalidate();
                        jLabel9.repaint();

                        per.setVisible(isVisible);
                        per.revalidate();
                        per.repaint();
                    }
                });
                blinkTimer.start();
            } else {
                // jLabel6.setText("TIDAK ADA PERIZINAN!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tampilizin() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_pengelolaan_cabang WHERE status = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "belum_diizinkan");
            ResultSet rs = stmt.executeQuery();

            int count = 0; // Menginisialisasi count dengan 0

            while (rs.next()) {
                String status = rs.getString("status");

                if (status.equalsIgnoreCase("belum_diizinkan")) {
                    count++;
                }
            }

            if (count > 0) {
                String countText = String.valueOf(count);
                jLabel12.setText(countText);
//                jLabel9.setText("PENGAMBILAN DATA ALAT ");
//                per.setText("MEMERLUKAN " + countText + " PERIZINAN!!");

                Timer blinkTimer = new Timer(500, new ActionListener() {
                    private boolean isVisible = true;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        isVisible = !isVisible;

                        jLabel12.setVisible(isVisible);
                        jLabel12.revalidate();
                        jLabel12.repaint();

                        per.setVisible(isVisible);
                        per.revalidate();
                        per.repaint();
                    }
                });
                blinkTimer.start();
            } else {
                // jLabel6.setText("TIDAK ADA PERIZINAN!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//

    private void levellogin() {
        String upperCaseText = SESSION.SessionLevel.getSessionLevel().toUpperCase();
        jLabel2.setText(upperCaseText);

    }

    private void hakakses() {
        String status = SESSION.SessionLevel.getSessionLevel();

        if (status.equals("Admin")) {
            tampil();
            tampilizin();
        } else if (status.equals("Gudang")) {

            jLabel11.setVisible(false);
            jLabel5.setVisible(false);
            jLabel4.setVisible(false);
            jLabel12.setVisible(false);
            jLabel9.setVisible(false);
            per.setVisible(false);

        } else if (status.equals("Direktur")) {
            tampil();
            tampilizin();
        } else {
            jLabel11.setVisible(false);
            jLabel12.setVisible(false);
            jLabel5.setVisible(false);
            jLabel4.setVisible(false);
            jLabel9.setVisible(false);
            per.setVisible(false);

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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        menus = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        per = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        stock = new javax.swing.JLabel();
        user = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        menus1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(menus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 190, 660));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/sidebar.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 360, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("0");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 20, 30));

        per.setFont(new java.awt.Font("Lucida Fax", 1, 9)); // NOI18N
        per.setForeground(new java.awt.Color(255, 255, 255));
        per.setText(" PERIZINAN!!!");
        per.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                perMouseClicked(evt);
            }
        });
        jPanel1.add(per, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 180, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/close.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 20, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("0");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 417, -1, 20));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/email.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, 60, 60));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(12, 31, 70));
        jLabel2.setText("ADMIN");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 60, 20));

        jLabel9.setFont(new java.awt.Font("Lucida Fax", 1, 9)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText(" PERIZINAN!!!");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 180, -1));

        stock.setFont(new java.awt.Font("Lucida Fax", 0, 24)); // NOI18N
        stock.setForeground(new java.awt.Color(51, 51, 51));
        stock.setText("stock ");
        jPanel1.add(stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 300, 110, -1));

        user.setFont(new java.awt.Font("Lucida Fax", 0, 24)); // NOI18N
        user.setForeground(new java.awt.Color(51, 51, 51));
        user.setText("user");
        jPanel1.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 470, 110, -1));

        jLabel6.setFont(new java.awt.Font("Lucida Fax", 1, 24)); // NOI18N
        jLabel6.setText("0");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 300, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/email.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, 60, 60));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Dashboard Direktur - Clean - 1025x746.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 830, 610));

        menus1.setLayout(new javax.swing.BoxLayout(menus1, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(menus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 190, 660));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:

        String status = SESSION.SessionLevel.getSessionLevel();

        if (status.equals("Admin")) {
            per.setForeground(new java.awt.Color(204, 0, 0));
            other.FPengelolaanDataCabang mu = new other.FPengelolaanDataCabang();
            mu.setVisible(true);
            dispose();
        } else if (status.equals("Gudang")) {

        } else if (status.equals("Direktur")) {

        } else {

        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

    private void perMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_perMouseClicked
        // TODO add your handling code here:
        String status = SESSION.SessionLevel.getSessionLevel();

        if (status.equals("Admin")) {
            per.setForeground(new java.awt.Color(204, 0, 0));
            other.FPengelolaanDataGudang mu = new other.FPengelolaanDataGudang();
            mu.setVisible(true);
            dispose();
        } else if (status.equals("Gudang")) {

        } else if (status.equals("Direktur")) {

        } else {

        }
    }//GEN-LAST:event_perMouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        other.FPengelolaanDataGudang mu = new other.FPengelolaanDataGudang();
        mu.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
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
                new FMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel menus1;
    private javax.swing.JLabel per;
    private javax.swing.JLabel stock;
    private javax.swing.JLabel user;
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
                masterdata.Laporanpusatsmi mu = new masterdata.Laporanpusatsmi();
                mu.setVisible(true);
                dispose();
            }
        });

        submasterdata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                masterdata.MasterData mu = new masterdata.MasterData();
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
                Transaksi.Fpenjualan mu = new Transaksi.Fpenjualan();
                mu.setVisible(true);
                dispose();
            }
        });

        subSetor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                Transaksi.Fsetorsmi mu = new Transaksi.Fsetorsmi();
                mu.setVisible(true);
                dispose();
            }
        });

        subUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika menu "Gudang" diklik, buka form Gudang
                Data.fuser mu = new Data.fuser();
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
