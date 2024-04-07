/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class FPengelolaanDataCabang extends javax.swing.JFrame {

    /**
     * Creates new form FPengelolaanData
     */
    public FPengelolaanDataCabang() {
        initComponents();
        tampil2();
    }

    public void tampil2() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_pengelolaan_cabang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Menghapus data sebelumnya dari model tabel
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("ID cabang");
            tableModel.addColumn("ID gudang");
            tableModel.addColumn("Kode Alat");
            tableModel.addColumn("Nama Alat");
            tableModel.addColumn("Jumlah");
            tableModel.addColumn("Tanggal terima");
            tableModel.addColumn("Tanggal kirim");
            tableModel.addColumn("Cabang");
            tableModel.addColumn("Status");
            tableModel.addColumn("Status2");

            // Mengisi data ke model tabel
            while (rs.next()) {
                String id = rs.getString("id_cabang");
                String id_gudang = rs.getString("id_gudang");
                String kodeAlat = rs.getString("kode_alat");
                String namaAlat = rs.getString("nama_alat");
                int jumlah = rs.getInt("jumlah");
                String tglteriima = rs.getString("tgl_terima");
                String tglkiriim = rs.getString("tgl_kirim");
                String Cabang2 = rs.getString("Cabang");
                String status = rs.getString("status");
                String status2 = rs.getString("status2");

                Object[] rowData = {id, id_gudang, kodeAlat, namaAlat, jumlah, tglteriima, tglkiriim, Cabang2, status, status2};
                tableModel.addRow(rowData);
            }

            tampill.setModel(tableModel);

            // Mengatur warna pada sel berdasarkan status
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    String status = tableModel.getValueAt(row, 8).toString();
                    if (status.equals("Di_izinkan")) {
                        component.setBackground(Color.GREEN);
                    } else if (status.equals("Belum_Diizinkan")) {
                        component.setBackground(Color.RED);
                    } else {
                        component.setBackground(table.getBackground());
                    }

                    return component;
                }
            };

            // Mengatur renderer pada kolom status
            tampill.getColumnModel().getColumn(8).setCellRenderer(renderer);

            // Mengatur lebar kolom Jumlah menjadi 10
            tampill.getColumnModel().getColumn(4).setPreferredWidth(10);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
//
//    public void tampil() {
//        try (Connection conn = Koneksi.KoneksiDatabase.getKoneksi()) {
//            String sql = "SELECT * FROM tb_pengelolaan";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery();
//
//            // Membuat DefaultTableModel untuk menyimpan data dari ResultSet
//            DefaultTableModel tableModel = new DefaultTableModel();
//            tableModel.addColumn("ID");
//            tableModel.addColumn("Kode Alat");
//            tableModel.addColumn("Nama Alat");
//            tableModel.addColumn("Jumlah");
//            tableModel.addColumn("Harga");
//            tableModel.addColumn("Merek");
//            tableModel.addColumn("Tanggal Masuk Gudang");
//            tableModel.addColumn("Status");
//
//            // Mengisi DefaultTableModel dengan data dari ResultSet
//            while (rs.next()) {
//                String id = rs.getString("id_gudang");
//                String kodeAlat = rs.getString("kode_alat");
//                String namaAlat = rs.getString("nama_alat");
//                int jumlah = rs.getInt("jumlah");
//                int harga = rs.getInt("harga");
//                String merek = rs.getString("merek");
//                String tanggalMasuk = rs.getString("tglmskgudang");
//                String status = rs.getString("status");
//
//                tableModel.addRow(new Object[]{id, kodeAlat, namaAlat, jumlah, harga, merek, tanggalMasuk, status});
//            }
//
//            // Mengatur model tabel pada JTable
//            tampill.setModel(tableModel);
//
//            // Mengatur warna pada sel berdasarkan status
//            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
//                @Override
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//                    String status = tableModel.getValueAt(row, 7).toString();
//                    if (status.equals("diizinkan")) {
//                        component.setBackground(Color.RED);
//                    } else if (status.equals("belum_diizinkan")) {
//                        component.setBackground(Color.GREEN);
//                    } else {
//                        component.setBackground(table.getBackground());
//                    }
//
//                    return component;
//                }
//            };
//
//            // Mengatur renderer pada kolom status
//            tampill.getColumnModel().getColumn(7).setCellRenderer(renderer);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        txtidcabang = new rojerusan.RSMetroTextPlaceHolder();
        jScrollPane1 = new javax.swing.JScrollPane();
        tampill = new rojerusan.RSTableMetro();
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/close.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, -1, -1));

        rSMaterialButtonRectangle2.setBackground(new java.awt.Color(153, 0, 0));
        rSMaterialButtonRectangle2.setText("Hapus");
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 173, 46));

        rSMaterialButtonRectangle1.setText("izinkan");
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 158, 45));

        txtidcabang.setEditable(false);
        txtidcabang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidcabangActionPerformed(evt);
            }
        });
        jPanel1.add(txtidcabang, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 60, 280, -1));

        tampill.setModel(new javax.swing.table.DefaultTableModel(
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
        tampill.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tampill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tampillMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tampill);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1010, 390));

        rSMaterialButtonCircle1.setText("BACK");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 60, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
        String status = "Di_izinkan";
        String kode = txtidcabang.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "UPDATE tb_pengelolaan_cabang SET status = ? WHERE id_cabang = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, status); // Menggunakan variabel status
            statement.setString(2, kode); // Menggunakan variabel kode

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Status berhasil diupdate");
            } else {
                System.out.println("Tidak ada data yang terupdate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tampil2();
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void tampillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tampillMouseClicked
        // TODO add your handling code here:
        int row = tampill.getSelectedRow();

// Mengambil data dari tabel
        String idcabang = tampill.getValueAt(row, 0).toString();

// Mengisi data ke dalam form input
        txtidcabang.setText(idcabang);
    }//GEN-LAST:event_tampillMouseClicked

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void txtidcabangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidcabangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidcabangActionPerformed

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
        // TODO add your handling code here:
        FAkses.FMenu mu = new FAkses.FMenu();
        mu.setVisible(true);
        dispose();
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

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
//            java.util.logging.Logger.getLogger(FPengelolaanDataCabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FPengelolaanDataCabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FPengelolaanDataCabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FPengelolaanDataCabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        try {
            FlatLaf.registerCustomDefaultsSource("raven.theme"); // Opsional, jika Anda memiliki file tema kustom
            FlatLaf.setup(new FlatLightLaf()); // Mengatur FlatLaf dengan tema Flat Dark (contoh)
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FPengelolaanDataCabang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojerusan.RSTableMetro tampill;
    private rojerusan.RSMetroTextPlaceHolder txtidcabang;
    // End of variables declaration//GEN-END:variables
}
