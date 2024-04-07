/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import FAkses.MenuItem;
import Koneksi.KoneksiDatabase;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author PC
 */
public class FGudang extends javax.swing.JFrame {

    int count;
    Connection conn = KoneksiDatabase.getKoneksi();

    /**
     * Creates new form FGudang
     */
    public FGudang() {
        initComponents();
        tampilDanMasukkanKeComboBoxManual();
        //       tampildate();
        idpp.setVisible(false);
        idgg.setVisible(false);
        textidp.setVisible(false);
        textid.setVisible(false);
        txtjumlah.setEnabled(false);

        textidp.setEnabled(false);
        textid.setEnabled(false);
        txtnama.setEnabled(false);
        textharga.setEnabled(false);
        tampil();
//        muncul();

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
        table();
        menus.add(jPanel1);
        hakakses();
    }

    public void tampil() {
        count = 0; // Reset count menjadi 0

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_pengelolaan WHERE status = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "diizinkan");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String status = rs.getString("status");

                if (status.equalsIgnoreCase("diizinkan")) {
                    count++;
                }
            }

            if (count > 0) {
                String countText = String.valueOf(count);
                notif2.setText(countText);
                notif2.setVisible(true);
                notif3.setVisible(true);

                Timer blinkTimer = new Timer(500, new ActionListener() {
                    private boolean isVisible = true;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        isVisible = !isVisible;
                        notif2.setVisible(isVisible);
                        notif2.revalidate();
                        notif2.repaint();
                    }
                });
                blinkTimer.start();
            } else {
                notif2.setVisible(false);
                notif3.setVisible(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String cariMerekBerdasarkanKode(String kodeAlat) {
        String merek = "";
        try {
            String sql = "SELECT merek FROM tb_data_alat WHERE kode_alat = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodeAlat);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                merek = rs.getString("merek");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return merek;
    }

    public void tampilDanMasukkanKeComboBoxManual() {
        try {
            String sql = "SELECT kode_alat, nama_alat FROM tb_data_alat";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String kodeAlat = rs.getString("kode_alat");
                cbkode_alat.addItem(kodeAlat);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        cbkode_alat.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedAlat = cbkode_alat.getSelectedItem().toString();
                    try {
                        String sql = "SELECT nama_alat,harga,merek,stock FROM tb_data_alat WHERE kode_alat = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, selectedAlat);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            String namaAlat = rs.getString("nama_alat");
                            String harga = rs.getString("harga");
                            String stock = rs.getString("stock");
                            String merek = rs.getString("merek");

                            txtnama.setText(namaAlat);
                            jLabel4.setText(merek);
                            jLabel11.setText(stock);
                            textharga.setText(harga);
                            txtjumlah.setEnabled(true);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static String generateIDGudang() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String id_user = null;
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String idToCheck = String.format("IDGD%03d", newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM tb_pengelolaan WHERE id_gudang = ?";
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

    public static String generateIDpengelolaan() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String id_pg = null;
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String idToCheck = String.format("IAPG%03d", newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM tb_pengelolaan WHERE id_pengelolaan = ?";
                PreparedStatement checkIdStatement = conn.prepareStatement(checkIdQuery);
                checkIdStatement.setString(1, idToCheck);
                ResultSet resultSet = checkIdStatement.executeQuery();

                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }

                if (count == 0) {
                    id_pg = idToCheck;
                    idExists = false;
                } else {
                    newId++;
                }
            }

            return id_pg;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateNewIDgd() {
        String newIDgd = "";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            // Query untuk mendapatkan semua id_gudang yang ada di tabel
            String sql = "SELECT id_gudang FROM tb_gudang";
            PreparedStatement stmt = con.prepareStatement(sql);

            // Menjalankan query dan mendapatkan hasil
            ResultSet rs = stmt.executeQuery();

            // Membuat HashSet untuk menyimpan semua id_gudang yang ada di tabel
            Set<String> existingIDs = new HashSet<>();
            while (rs.next()) {
                existingIDs.add(rs.getString("id_gudang"));
            }

            // Mencari nomor berikutnya yang belum ada di HashSet
            int nextNumber = 1;
            while (existingIDs.contains("IDGD" + String.format("%03d", nextNumber))) {
                nextNumber++;
            }

            // Membentuk nomor faktur baru
            newIDgd = "IDGD" + String.format("%03d", nextNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newIDgd;
    }

    public void table() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_gudang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Menghapus data sebelumnya dari model tabel
            DefaultTableModel tableModel = (DefaultTableModel) rSTableMetro1.getModel();
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("ID gudang");
            tableModel.addColumn("Kode Alat");
            tableModel.addColumn("Nama Alat");
            tableModel.addColumn("Jumlah");
            tableModel.addColumn("Harga");
            tableModel.addColumn("Merek");
            tableModel.addColumn("Tanggal masuk gudang");
            tableModel.addColumn("status");

            // Mengisi data ke model tabel
            while (rs.next()) {
                String id_gudang = rs.getString("id_gudang");
                String kodeAlat = rs.getString("kode_alat");
                String namaAlat = rs.getString("nama_alat");
                int jumlah = rs.getInt("jumlah");
                int harga = rs.getInt("harga");
                String merek = rs.getString("merek");
                String tglteriima = rs.getString("tglmskgudang");
                String status = rs.getString("status");

                Object[] rowData = {id_gudang, kodeAlat, namaAlat, jumlah, harga, merek, tglteriima, status,};
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean cekStok(String kode_alat, int jumlahAmbil) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT stock FROM tb_data_alat WHERE kode_alat = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kode_alat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int stok = rs.getInt("stock");
                if (jumlahAmbil > stok) {
                    JOptionPane.showMessageDialog(null, "Stok tidak mencukupi");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void hakakses() {
        String status = SESSION.SessionLevel.getSessionLevel();

        if (status.equals("Admin")) {
            cbkode_alat.setEnabled(false);
            txtjumlah.setEnabled(false);
            textharga.setEnabled(false);
            rSMaterialButtonRectangle1.setEnabled(false);
            rSMaterialButtonRectangle2.setEnabled(false);
            rSMaterialButtonRectangle3.setEnabled(false);
        } else if (status.equals("Gudang")) {

        } else if (status.equals("Direktur")) {

        } else {
            cbkode_alat.setEnabled(false);
            txtjumlah.setEnabled(false);
            textharga.setEnabled(false);
            rSMaterialButtonRectangle1.setEnabled(false);
            rSMaterialButtonRectangle2.setEnabled(false);
            rSMaterialButtonRectangle3.setEnabled(false);
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
        rSTableMetro1 = new rojerusan.RSTableMetro();
        notif2 = new javax.swing.JLabel();
        notif3 = new javax.swing.JLabel();
        textharga = new rojerusan.RSMetroTextPlaceHolder();
        txtjumlah = new rojerusan.RSMetroTextPlaceHolder();
        cbkode_alat = new rojerusan.RSComboMetro();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        idpp = new javax.swing.JLabel();
        idgg = new javax.swing.JLabel();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        textid = new javax.swing.JTextField();
        textidp = new javax.swing.JTextField();
        rSMaterialButtonRectangle3 = new rojerusan.RSMaterialButtonRectangle();
        menus = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        buttonreportgudang = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        rSTableMetro1.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        rSTableMetro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSTableMetro1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(rSTableMetro1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 510, 770, 190));

        notif2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        notif2.setForeground(new java.awt.Color(255, 255, 255));
        notif2.setText("0");
        jPanel1.add(notif2, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 70, 10, 20));

        notif3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/notification - Copy.png"))); // NOI18N
        jPanel1.add(notif3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 60, 50, 70));

        textharga.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        textharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texthargaActionPerformed(evt);
            }
        });
        jPanel1.add(textharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 180, 33));

        txtjumlah.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjumlahActionPerformed(evt);
            }
        });
        jPanel1.add(txtjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 180, 33));

        cbkode_alat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PILIH KODE" }));
        cbkode_alat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbkode_alatItemStateChanged(evt);
            }
        });
        cbkode_alat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkode_alatActionPerformed(evt);
            }
        });
        jPanel1.add(cbkode_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 180, -1));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(0, 102, 153));
        rSMaterialButtonRectangle1.setText("Lihat perizinan");
        rSMaterialButtonRectangle1.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, 140, 40));

        idpp.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        idpp.setText("ID pengelolaan");
        jPanel1.add(idpp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 90, -1));

        idgg.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        idgg.setText("ID gudang");
        jPanel1.add(idgg, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 120, 60, -1));

        rSMaterialButtonRectangle2.setText("Simpan");
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 290, 140, 40));

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel2.setText("Kode alat");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 60, -1));

        jLabel3.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel3.setText("Nama alat");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 60, 20));

        jLabel4.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel4.setText("merek");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, 50, -1));

        txtnama.setEditable(false);
        txtnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaActionPerformed(evt);
            }
        });
        jPanel1.add(txtnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 180, 30));

        textid.setEditable(false);
        textid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textidActionPerformed(evt);
            }
        });
        jPanel1.add(textid, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 140, 160, 30));
        jPanel1.add(textidp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 210, 160, 30));

        rSMaterialButtonRectangle3.setBackground(new java.awt.Color(204, 51, 0));
        rSMaterialButtonRectangle3.setText("KIRIM KE CABANG");
        rSMaterialButtonRectangle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle3ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, 140, 40));

        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(menus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 180, 610));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/close.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 0, -1, 50));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/sidebar.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel10.setText("FORM GUDANG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 160, 30));

        jLabel7.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel7.setText("Jumlah ambil");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 80, -1));

        jLabel11.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel11.setText("0");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 30, -1));

        jLabel12.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel12.setText("Stock alat :");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 90, -1));

        jLabel13.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel13.setText("Harga ");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, 100, -1));

        buttonreportgudang.setBackground(new java.awt.Color(5, 104, 158));
        buttonreportgudang.setForeground(new java.awt.Color(255, 255, 255));
        buttonreportgudang.setText("cetak");
        buttonreportgudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonreportgudangActionPerformed(evt);
            }
        });
        jPanel1.add(buttonreportgudang, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 430, 140, 30));

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Form Alat - Clean.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void texthargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texthargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_texthargaActionPerformed

    private void txtjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahActionPerformed

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
        rSMaterialButtonRectangle2.setText("simpan");
        other.FdataDiizinkan mu = new other.FdataDiizinkan();
        mu.setVisible(true);


    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        // TODO add your handling code here:
        JCalendar jCalendar = new JCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date selectedDate = jCalendar.getCalendar().getTime();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
        String id = generateIDGudang();
        String id2 = generateIDpengelolaan();
        String date = formattedDate;
        String nama = txtnama.getText();
        String kodealat = cbkode_alat.getSelectedItem().toString();
        String merekAlat = cariMerekBerdasarkanKode(kodealat);
        String jumlah0 = txtjumlah.getText();
        String harga0 = textharga.getText();

        String newIDgd = generateNewIDgd();
        int jumlah = Integer.parseInt(jumlah0);
        int harga = Integer.parseInt(harga0);

        if (cekStok(kodealat, jumlah)) {
            // Lakukan operasi pengambilan barang di sini
            // ...
            if (nama.isEmpty() || kodealat.isEmpty() || jumlah0.isEmpty() || harga0.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi.");
            } else if (!jumlah0.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Jumlah harus diisi dengan angka.");
            } else {

                String idpDicari = textidp.getText(); // idpengelolan yang dicari
                boolean statusDizinkan = false; // Status awal: tidak dizinkan

                try {
                    // Membuat koneksi ke database
                    Connection conn = Koneksi.KoneksiDatabase.getKoneksi();
                    // Membuat statement SQL untuk mencari nama dalam tabel
                    String sql = "SELECT COUNT(*) AS count FROM tb_pengelolaan WHERE id_pengelolaan = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, idpDicari);

                    // Menjalankan query dan mendapatkan hasil
                    ResultSet rs = stmt.executeQuery();
                    rs.next();
                    int count = rs.getInt("count");

                    // Memeriksa apakah nama ditemukan
                    if (count > 0) {
                        statusDizinkan = true; // Jika nama ditemukan, status menjadi dizinkan
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Menampilkan status
                if (statusDizinkan == true) {
                    // Status: Dizinkan
                    String status = "diizinkan";
                    if (nama.isEmpty() || kodealat.isEmpty() || jumlah0.isEmpty() || harga0.isEmpty() || textid.getText().isEmpty() || textidp.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "wajib di isi semua");
                    } else {
                        if (status.equals("diizinkan")) {

                            JOptionPane.showMessageDialog(null, "Data Diizinkan");

                            try (
                                    Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
//                            JOptionPane.showMessageDialog(null, "ID diganti menjadi : " + newIDgd);

                                String sql = "INSERT INTO tb_gudang ("
                                        + "id_gudang, kode_alat, nama_alat, jumlah, harga, merek, tglmskgudang, status) "
                                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                                PreparedStatement statement = con.prepareStatement(sql);
                                statement.setString(1, newIDgd);
                                statement.setString(2, kodealat);
                                statement.setString(3, nama);
                                statement.setInt(4, jumlah);
                                statement.setInt(5, harga);
                                statement.setString(6, merekAlat);
                                statement.setString(7, date);
                                statement.setString(8, status);

                                int rowsInserted = statement.executeUpdate();
                                if (rowsInserted > 0) {
                                    JOptionPane.showMessageDialog(null, "berhasil simpan data di gudang");
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try (
                                    Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                                String sql = "DELETE FROM tb_pengelolaan WHERE id_pengelolaan = ?";
                                PreparedStatement statement = conn.prepareStatement(sql);
                                statement.setString(1, textidp.getText());

                                int rowsDeleted = statement.executeUpdate();
//                            if (rowsDeleted > 0) {
//                                JOptionPane.showMessageDialog(null, "Data telah dihapus.");
//                            } else {
//                                JOptionPane.showMessageDialog(null, "Tidak ada data yang terhapus.");
//                            }
//                            cbkode_alat.setSelectedIndex(0);
//                            txtnama.setText("");
//                            textid.setText("");
//                            textidp.setText("");
//                            textharga.setText("");
//                            txtjumlah.setText("");
//                            jTextArea1.setText("");
//                            tampil();
                                new Data.FGudang().setVisible(true);
                                this.dispose();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    String status = "belum_diizinkan";
                    if (nama.isEmpty() || kodealat.isEmpty() || jumlah0.isEmpty() || harga0.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "wajib di isi semua");
                    } else {
                        if (status.equals("belum_diizinkan")) {
                            JOptionPane.showMessageDialog(null, "Maaf, ambil data harus izin admin terlebih dahulu.");
                            try (
                                    Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {

                                String sql = "INSERT INTO tb_pengelolaan ("
                                        + "id_pengelolaan, id_gudang, kode_alat, nama_alat, jumlah, harga, merek, tglmskgudang, status) "
                                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                                PreparedStatement statement = con.prepareStatement(sql);
                                statement.setString(1, id2);
                                statement.setString(2, id);
                                statement.setString(3, kodealat);
                                statement.setString(4, nama);
                                statement.setInt(5, jumlah);
                                statement.setInt(6, harga);
                                statement.setString(7, merekAlat);
                                statement.setString(8, date);
                                statement.setString(9, status);

                                int rowsInserted = statement.executeUpdate();
                                if (rowsInserted > 0) {
                                    JOptionPane.showMessageDialog(null, "Tunggu Perizinan dari Admin.");
                                }
                                                               
                                new Data.FGudang().setVisible(true);
                                this.dispose();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "pengambilan jangan melebihi stock");

        }
    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void cbkode_alatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkode_alatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkode_alatActionPerformed

    private void cbkode_alatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbkode_alatItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkode_alatItemStateChanged

    private void txtnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaActionPerformed

    private void textidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textidActionPerformed

    private void rSMaterialButtonRectangle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3ActionPerformed
        // TODO add your handling code here:
        String id = textid.getText();
        SESSION2.SessionIdGudang.setSessionIdGudang(id);
        this.dispose();
        new FKirimTOcabang().setVisible(true);

    }//GEN-LAST:event_rSMaterialButtonRectangle3ActionPerformed

    private void rSTableMetro1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSTableMetro1MouseClicked
        // TODO add your handling code here:
        Data.FGudang.idpp.setVisible(true);
        Data.FGudang.textidp.setVisible(true);
        textid.setVisible(true);
        Data.FGudang.idgg.setVisible(true);
    }//GEN-LAST:event_rSTableMetro1MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void buttonreportgudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonreportgudangActionPerformed
        // TODO add your handling code here:
        try {
            // Load file .jasper dan membuat objek JasperReport
            String reportPath = "src/Ireport/GudangReport.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);

            // Mengisi laporan dengan data
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                String sql = "SELECT * FROM tb_gudang";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

                // Menampilkan laporan dalam JasperViewer
                JasperViewer.viewReport(jasperPrint, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_buttonreportgudangActionPerformed

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
                new FGudang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonreportgudang;
    public static rojerusan.RSComboMetro cbkode_alat;
    public static javax.swing.JLabel idgg;
    public static javax.swing.JLabel idpp;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menus;
    public static javax.swing.JLabel notif2;
    public static javax.swing.JLabel notif3;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle3;
    private rojerusan.RSTableMetro rSTableMetro1;
    public static rojerusan.RSMetroTextPlaceHolder textharga;
    public static javax.swing.JTextField textid;
    public static javax.swing.JTextField textidp;
    public static rojerusan.RSMetroTextPlaceHolder txtjumlah;
    private javax.swing.JTextField txtnama;
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
