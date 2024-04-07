/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import static Data.FGudang.cbkode_alat;
import FAkses.MenuItem;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class FCabang extends javax.swing.JFrame {

    String cek;
    int count = 0;
    String cekdata;
    String status2;

    /**
     * Creates new form FCabang
     */
    public FCabang() {
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

// Tambahkan panel ke dalam container
        menus.add(jPanel1);
        cabang();
        tanggal();
//        otomatisidcabang();
        lbidkirim.setVisible(false);
        jumlah.setEnabled(false);
        diteri.setVisible(false);
        ambiltext.setVisible(false);
        combobox();
        tampilt();
        realtime();
        hakakses();
    }

    public void realtime() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT SUM(jumlah) AS total_jumlah FROM tb_cabang WHERE Cabang = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Cabang2.getText());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int totalJumlah = rs.getInt("total_jumlah");
                String jumlahString = String.valueOf(totalJumlah);
                stock2.setText(jumlahString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tampilt() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_cabang WHERE Cabang = ? ";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Cabang2.getText());

            ResultSet rs = stmt.executeQuery();

            // Menghapus data sebelumnya dari model tabel
            DefaultTableModel tableModel = (DefaultTableModel) rSTableMetro1.getModel();
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("ID cabang");
            tableModel.addColumn("ID gudang");
            tableModel.addColumn("Kode Alat");
            tableModel.addColumn("Nama Alat");
            tableModel.addColumn("Stock");
            tableModel.addColumn("Harga");
            tableModel.addColumn("Merek");
            tableModel.addColumn("Tanggal terima");
            tableModel.addColumn("Tanggal kirim");
            tableModel.addColumn("Cabang");

            // Mengisi data ke model tabel
            while (rs.next()) {
                String id = rs.getString("id_cabang");
                String id_gudang = rs.getString("id_gudang");
                String kodeAlat = rs.getString("kode_alat");
                String namaAlat = rs.getString("nama_alat");
                int jumlah = rs.getInt("jumlah");
                int harga = rs.getInt("harga_alat");
                String merek = rs.getString("merek");
                String tglteriima = rs.getString("tgl_terima");
                String tglkiriim = rs.getString("tgl_kirim");
                String Cabangx = rs.getString("Cabang");

                Object[] rowData = {id, id_gudang, kodeAlat, namaAlat, jumlah, harga, merek, tglteriima, tglkiriim, Cabangx,};
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void combobox() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT status2, status, Cabang FROM tb_pengelolaan_cabang WHERE Cabang = ? AND status = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Cabang2.getText());
            ps.setString(2, "Di_izinkan");

            ResultSet rs = ps.executeQuery();
            int hitungMenerimaKiriman = 0;
            int hitungMengambilGudang = 0;
            int hitungMengambilCabang = 0;

            // Menghapus semua item di combobox sebelum menambahkan item yang diperoleh dari database
            jComboBox1.removeAllItems();
            while (rs.next()) {
                String status2 = rs.getString("status2");
                if (status2.equals("menerima_kiriman")) {
                    hitungMenerimaKiriman++;
                } else if (status2.equals("ambil_data_digudang")) {
                    hitungMengambilGudang++;
                } else if (status2.equals("ambil_data_dicabang")) {
                    hitungMengambilCabang++;
                }
            }

            // Menambahkan item ke combobox sesuai dengan jumlah hitung
            if (hitungMenerimaKiriman > 0) {
                jComboBox1.addItem("Menerima dari Kiriman ada " + hitungMenerimaKiriman);
            }
            if (hitungMengambilGudang > 0) {
                jComboBox1.addItem("Mengambil dari gudang ada " + hitungMengambilGudang);
            }
            if (hitungMengambilCabang > 0) {
                jComboBox1.addItem("Mengambil dari cabang ada " + hitungMengambilCabang);
            }
//
            jComboBox1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedIndex = jComboBox1.getSelectedItem().toString();
                    // contains menyamakan data yang hampir mirip di combobox
                    if (selectedIndex.contains("Menerima dari Kiriman ada ")) {
                        String st1 = "menerima_kiriman";
                        SESSION.SessionStatus2.setSessionStatus2(st1);
                        JOptionPane.showMessageDialog(null, "Mengambil data kiriman");
                        other.FdataDiizinkantigapoint mu = new other.FdataDiizinkantigapoint();
                        mu.setVisible(true);
                    } else if (selectedIndex.contains("Mengambil dari gudang ada ")) {
                        String st2 = "ambil_data_digudang";
                        SESSION.SessionStatus2.setSessionStatus2(st2);
                        JOptionPane.showMessageDialog(null, "Mengambil gudang");
                        other.FdataDiizinkantigapoint mu = new other.FdataDiizinkantigapoint();
                        mu.setVisible(true);
                    } else if (selectedIndex.contains("Mengambil dari cabang ada ")) {
                        String st3 = "ambil_data_dicabang";
                        SESSION.SessionStatus2.setSessionStatus2(st3);
                        JOptionPane.showMessageDialog(null, "Mengambil cabang");
                        other.FdataDiizinkantigapoint mu = new other.FdataDiizinkantigapoint();
                        mu.setVisible(true);
                    }

                }
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Event listener untuk combobox
//        String selectedItem = (String) jComboBox1.getSelectedItem();
//
//        if (selectedItem.0) {
//
//        } else if (selectedItem.1) {
//            JOptionPane.showMessageDialog(null, "Mengambil gudang");
//        } else if (selectedItem.2)) {
//            JOptionPane.showMessageDialog(null, "Mengambil cabang");
//        }
    public void tanggal() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(date);
        tanggalnow.setText(currentDate);
    }

    public void cabang() {
        String Cabang3 = SESSION.SessionCabang.getSessionCabang();
        Cabang2.setForeground(Color.BLUE);

        jLabel18.setText("Selamat Datang Dicabang");
        Cabang2.setText(Cabang3);
    }

    public void generateCabangID(String Cabang) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String idCabang = null;
            String idToCheck = "ID" + Cabang.toLowerCase() + "%04d";
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String formattedId = String.format(idToCheck, newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM tb_pengelolaan_cabang WHERE id_cabang = ?";
                PreparedStatement checkIdStatement = conn.prepareStatement(checkIdQuery);
                checkIdStatement.setString(1, formattedId);
                ResultSet resultSet = checkIdStatement.executeQuery();

                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }

                if (count == 0) {
                    idCabang = formattedId;
                    idExists = false;
                } else {
                    newId++;
                }
            }
            txtidcabang.setText(idCabang);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean cekStockCabang(String kode_alat, int jumlahAmbil) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT jumlah FROM tb_cabang WHERE kode_alat = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kode_alat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int stok = rs.getInt("jumlah");
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

    public boolean cekStokgudang(String kode_alat, int jumlahAmbil) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT SUM(jumlah) AS total_jumlah FROM tb_gudang WHERE kode_alat = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kode_alat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int totalJumlah = rs.getInt("total_jumlah");
                if (jumlahAmbil > totalJumlah) {
                    JOptionPane.showMessageDialog(null, "Stok tidak mencukupi");

                    return false;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String generateNewIDCBG(String Cabang) {
        String newIDgd = "";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            // Query untuk mendapatkan semua id_cabang yang ada di tabel
            String sql = "SELECT id_cabang FROM tb_cabang";
            PreparedStatement stmt = con.prepareStatement(sql);

            // Menjalankan query dan mendapatkan hasil
            ResultSet rs = stmt.executeQuery();

            // Membuat HashSet untuk menyimpan semua id_cabang yang ada di tabel
            Set<String> existingIDs = new HashSet<>();
            while (rs.next()) {
                existingIDs.add(rs.getString("id_cabang"));
            }

            // Mencari nomor berikutnya yang belum ada di HashSet
            int nextNumber = 1;
            boolean idExists = true;
            while (idExists) {
                String formattedId = String.format("ID%s%04d", Cabang.toLowerCase(), nextNumber);
                if (!existingIDs.contains(formattedId)) {
                    newIDgd = formattedId;
                    idExists = false;
                } else {
                    nextNumber++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newIDgd;
    }

    private void hakakses() {
        String status = SESSION.SessionLevel.getSessionLevel();

        if (status.equals("Admin")) {
            txtidcabang.setEnabled(false);
            txtidgudang.setEnabled(false);
            txtkode.setEnabled(false);
            txtnamaalat.setEnabled(false);
            txtharga.setEnabled(false);
            txtmerek.setEnabled(false);
            tglkirim.setEnabled(false);
            jumlah.setEnabled(false);
            jComboBox1.setEnabled(false);
            rSMaterialButtonRectangle4.setEnabled(false);
            rSMaterialButtonRectangle3.setEnabled(false);
            rSMaterialButtonRectangle2.setEnabled(false);
            rSMaterialButtonRectangle1.setEnabled(false);
        } else if (status.equals("Gudang")) {

        } else if (status.equals("Direktur")) {

        } else {
            txtidcabang.setEnabled(false);
            txtidgudang.setEnabled(false);
            txtkode.setEnabled(false);
            txtnamaalat.setEnabled(false);
            txtharga.setEnabled(false);
            txtmerek.setEnabled(false);
            tglkirim.setEnabled(false);
            jumlah.setEnabled(false);
            jComboBox1.setEnabled(false);
            rSMaterialButtonRectangle4.setEnabled(false);
            rSMaterialButtonRectangle3.setEnabled(false);
            rSMaterialButtonRectangle2.setEnabled(false);
            rSMaterialButtonRectangle1.setEnabled(false);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        backtocabang = new javax.swing.JButton();
        tanggalnow = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rSTableMetro1 = new rojerusan.RSTableMetro();
        diteri = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtidcabang = new javax.swing.JTextField();
        txtidgudang = new javax.swing.JTextField();
        txtkode = new javax.swing.JTextField();
        txtnamaalat = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txtmerek = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        tglkirim = new javax.swing.JTextField();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        stockk = new javax.swing.JLabel();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        menus = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbidkirim = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        rSMaterialButtonRectangle3 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle4 = new rojerusan.RSMaterialButtonRectangle();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        Cabang2 = new javax.swing.JLabel();
        stock2 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        ambiltext = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel2.setText("id cabang");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, -1, -1));

        jLabel3.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel3.setText("kode alat");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, -1, -1));

        jLabel4.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel4.setText("nama alat");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, -1, -1));

        jLabel5.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel5.setText("harga alat");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, -1, -1));

        jLabel6.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel6.setText("merek");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, -1, 20));
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(364, 95, -1, -1));

        backtocabang.setText("PILIH CABANG");
        backtocabang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backtocabangActionPerformed(evt);
            }
        });
        jPanel1.add(backtocabang, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 150, 30));

        tanggalnow.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        tanggalnow.setText("date now");
        jPanel1.add(tanggalnow, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, -1, 20));

        jLabel9.setFont(new java.awt.Font("Lucida Fax", 0, 13)); // NOI18N
        jLabel9.setText("notifikasi diizinkan ");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 80, -1, 20));

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
        jScrollPane1.setViewportView(rSTableMetro1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 510, 770, 190));

        diteri.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        diteri.setForeground(new java.awt.Color(0, 255, 0));
        diteri.setText("diterima");
        jPanel1.add(diteri, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, -1, -1));

        jLabel15.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel15.setText("id gudang");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, -1));

        txtidcabang.setEditable(false);
        jPanel1.add(txtidcabang, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 150, -1));

        txtidgudang.setEditable(false);
        jPanel1.add(txtidgudang, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 150, -1));

        txtkode.setEditable(false);
        jPanel1.add(txtkode, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 150, -1));

        txtnamaalat.setEditable(false);
        jPanel1.add(txtnamaalat, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 150, -1));

        txtharga.setEditable(false);
        jPanel1.add(txtharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, 150, -1));

        txtmerek.setEditable(false);
        jPanel1.add(txtmerek, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, 150, -1));

        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });
        jPanel1.add(jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, 150, 20));

        tglkirim.setEditable(false);
        jPanel1.add(tglkirim, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 150, -1));

        rSMaterialButtonRectangle1.setText("SIMPAN");
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 280, 140, 50));

        stockk.setFont(new java.awt.Font("Lucida Fax", 1, 14)); // NOI18N
        stockk.setForeground(new java.awt.Color(204, 0, 51));
        stockk.setText("0");
        jPanel1.add(stockk, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, -1, -1));

        rSMaterialButtonRectangle2.setText("lihat data kiriman");
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 190, 50));

        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(menus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 180, 580));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/sidebar.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lbidkirim.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        lbidkirim.setText("ID KIRIM");
        jPanel1.add(lbidkirim, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, -1, -1));

        jTextField1.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jTextField1.setText("SEARCH");
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 430, 120, 30));

        rSMaterialButtonRectangle3.setText("Ambil data Dari gudang");
        rSMaterialButtonRectangle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle3ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 420, 190, 50));

        rSMaterialButtonRectangle4.setText("Minta Data kecabang");
        rSMaterialButtonRectangle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle4ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 420, 190, 50));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/close.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, -1, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N
        jLabel18.setText("Selamat Datang Dicabang");

        Cabang2.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N
        Cabang2.setText("KOTA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cabang2, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cabang2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 400, 30));

        stock2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        stock2.setForeground(new java.awt.Color(204, 0, 0));
        stock2.setText("0");
        jPanel1.add(stock2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 310, 50, 20));

        jLabel19.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel19.setText("stock pusat");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, -1, -1));

        ambiltext.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        ambiltext.setForeground(new java.awt.Color(204, 0, 0));
        ambiltext.setText("ambil");
        jPanel1.add(ambiltext, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, -1, -1));

        jLabel20.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel20.setText("stock Gudang");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, -1, -1));

        jLabel16.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel16.setText("jumlah");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 160, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ada Notif" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 110, 110, -1));

        jLabel13.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel13.setText("tanggal  kirim");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, -1, 20));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Form Alat - Clean.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 730));

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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        // TODO add your handling code here:
        cekdata = "ambil";
        status2 = "menerima_kiriman";
        diteri.setVisible(true);
        ambiltext.setVisible(false);
        other.FdataDikirim mu = new other.FdataDikirim();
        mu.setVisible(true);
        generateCabangID(Cabang2.getText());

    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahActionPerformed

    private void backtocabangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backtocabangActionPerformed
        // TODO add your handling code here:
        other.Map mu = new other.Map();
        mu.setVisible(true);
        dispose();
        FCabang.diteri.setVisible(false);

    }//GEN-LAST:event_backtocabangActionPerformed

    private void rSMaterialButtonRectangle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3ActionPerformed
        // TODO add your handling code here:
//        cariStokBarang();
        cekdata = "ambil";
        status2 = "ambil_data_digudang";
        FCabang.diteri.setVisible(false);
        ambiltext.setVisible(true);
        jumlah.setText("");

        other.FdataGudang mu = new other.FdataGudang();
        mu.setVisible(true);
        generateCabangID(Cabang2.getText());
    }//GEN-LAST:event_rSMaterialButtonRectangle3ActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:

        //hapus id data kiriman jika berhasil di simpan
        try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String query = "DELETE FROM tb_kirim_cabang WHERE id_kirim = ?";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, lbidkirim.getText());
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
//                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                } else {
//                    JOptionPane.showMessageDialog(null, "Data dengan ID kiriman tersebut tidak ditemukan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }//tutup

        if (cekdata.equals("ambil")) {
            //open . simpan ke_pengelolaan_cabang
            if (txtidcabang.getText()
                    .isEmpty() || txtidgudang.getText().isEmpty() || txtkode.getText().isEmpty() || txtnamaalat.getText().isEmpty() || txtharga.getText().isEmpty() || txtmerek.getText().isEmpty() || tglkirim.getText().isEmpty() || jumlah.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi");
            } else {

                String rego = txtharga.getText();
                int harga = Integer.parseInt(rego);

                String jmlh = jumlah.getText();
                int jumlah = Integer.parseInt(jmlh);

                Date tglterima = new Date();
                Date tgl = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (cekStokgudang(txtkode.getText(), jumlah)) {

                    try {
                        tgl = dateFormat.parse(tglkirim.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
//                JOptionPane.showMessageDialog(null, "ID diganti menjadi: " + newIDgd);

                        String sql = "INSERT INTO tb_pengelolaan_cabang (id_cabang, id_gudang, kode_alat, nama_alat, harga_alat, jumlah, merek, tgl_terima, tgl_kirim, Cabang, status, status2) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement statement = con.prepareStatement(sql);
                        statement.setString(1, txtidcabang.getText());
                        statement.setString(2, txtidgudang.getText());
                        statement.setString(3, txtkode.getText());
                        statement.setString(4, txtnamaalat.getText());
                        statement.setInt(5, harga);
                        statement.setInt(6, jumlah);
                        statement.setString(7, txtmerek.getText());
                        statement.setDate(8, new java.sql.Date(tglterima.getTime()));
                        statement.setDate(9, new java.sql.Date(tgl.getTime()));
                        statement.setString(10, Cabang2.getText());
                        statement.setString(11, "Belum_Diizinkan");
                        statement.setString(12, status2);

                        JOptionPane.showMessageDialog(null, "Menunggu persetujuan admin");
                        JOptionPane.showMessageDialog(null, "Tunggu notifikasi admin");
                        other.Map mu = new other.Map();
                        mu.setVisible(true);
                        dispose();
                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
//                    JOptionPane.showMessageDialog(null, "Berhasil menyimpan data");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "pengambilan data tidak boleh lebih dari stock");
                }
            }
            //tutup

        } else if (cekdata.equals("notif")) {

            //cek id pengelolaan di cabang sudah di izinkan atau belum 
            try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                String idCabang = txtidcabang.getText();

                // Mengecek status izin ID cabang
                String query = "SELECT status FROM tb_pengelolaan_cabang WHERE id_cabang = ?";
                PreparedStatement checkStatusStatement = con.prepareStatement(query);
                checkStatusStatement.setString(1, idCabang);
                ResultSet resultSet = checkStatusStatement.executeQuery();

                if (resultSet.next()) {
                    String status = resultSet.getString("status");
                    if (status.equals("Di_izinkan")) {

                        JOptionPane.showMessageDialog(null, "Data sudah diizinkan oleh admin");

                        if (txtidcabang.getText().isEmpty() || txtidgudang.getText().isEmpty() || txtkode.getText().isEmpty() || txtnamaalat.getText().isEmpty() || txtharga.getText().isEmpty() || txtmerek.getText().isEmpty() || jumlah.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Semua field harus diisi");
                        } else {

                            String rego = txtharga.getText();
                            int harga = Integer.parseInt(rego);

                            String jmlh = jumlah.getText();
                            int jumlah = Integer.parseInt(jmlh);

                            String newIDgd = generateNewIDCBG(Cabang2.getText());

                            String sessionTglTerima = SESSION.Sessiontglterima.getSessiontglterima();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Pola tanggal yang sesuai dengan format string
                            Date tglTerima = null;
                            try {
                                tglTerima = format.parse(sessionTglTerima);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date tglkirimm = null;
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                            try {
                                tglkirimm = dateFormat.parse(tglkirim.getText());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            try {
//                JOptionPane.showMessageDialog(null, "ID diganti menjadi: " + newIDgd);

                                String sql = "INSERT INTO tb_cabang (id_cabang, id_gudang, kode_alat, nama_alat, harga_alat, jumlah, merek, tgl_terima, tgl_kirim, Cabang ) "
                                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                                PreparedStatement statement = con.prepareStatement(sql);
                                statement.setString(1, newIDgd);
                                statement.setString(2, txtidgudang.getText());
                                statement.setString(3, txtkode.getText());
                                statement.setString(4, txtnamaalat.getText());
                                statement.setInt(5, harga);
                                statement.setInt(6, jumlah);
                                statement.setString(7, txtmerek.getText());
                                statement.setDate(8, new java.sql.Date(tglTerima.getTime()));
                                statement.setDate(9, new java.sql.Date(tglkirimm.getTime()));
                                statement.setString(10, Cabang2.getText());

                                JOptionPane.showMessageDialog(null, "berhasil di simpan");
                                JOptionPane.showMessageDialog(null, "Lihat data di cabang " + Cabang2.getText());
                                other.Map mu = new other.Map();
                                mu.setVisible(true);
                                dispose();
                                int rowsInserted = statement.executeUpdate();
                                if (rowsInserted > 0) {
//                    JOptionPane.showMessageDialog(null, "Berhasil menyimpan data");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                                String sql = "DELETE FROM tb_pengelolaan_cabang WHERE id_cabang = ?";
                                PreparedStatement statement = conn.prepareStatement(sql);
                                statement.setString(1, txtidcabang.getText());

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
                                new Data.FCabang().setVisible(true);
                                this.dispose();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                        //tutup
                    } else if (status.equals("Belum_Diizinkan")) {
                        JOptionPane.showMessageDialog(null, "Data belum diizinkan oleh admin");

                    } else {
                        JOptionPane.showMessageDialog(null, "Data tidak valid");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Data telah dihapus oleh admin");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //tutup

        }
        FCabang.diteri.setVisible(false);

    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void rSMaterialButtonRectangle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle4ActionPerformed
        // TODO add your handling code here:
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(date);
        tanggalnow.setText(currentDate);
        ambiltext.setText("ambil");
        jumlah.setText("");
        cekdata = "ambil";
        status2 = "ambil_data_dicabang";
        FCabang.diteri.setVisible(false);
        ambiltext.setVisible(true);
        other.FdataCabang mu = new other.FdataCabang();
        mu.setVisible(true);
        generateCabangID(Cabang2.getText());
    }//GEN-LAST:event_rSMaterialButtonRectangle4ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        cekdata = "notif";

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
//            java.util.logging.Logger.getLogger(FCabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FCabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FCabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FCabang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                other.Map mu = new other.Map();
                mu.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Cabang2;
    private javax.swing.JLabel ambiltext;
    public static javax.swing.JButton backtocabang;
    public static javax.swing.JLabel diteri;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JTextField jTextField1;
    public static javax.swing.JTextField jumlah;
    public static javax.swing.JLabel lbidkirim;
    private javax.swing.JPanel menus;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle3;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle4;
    private rojerusan.RSTableMetro rSTableMetro1;
    public static javax.swing.JLabel stock2;
    public static javax.swing.JLabel stockk;
    private javax.swing.JLabel tanggalnow;
    public static javax.swing.JTextField tglkirim;
    public static javax.swing.JTextField txtharga;
    public static javax.swing.JTextField txtidcabang;
    public static javax.swing.JTextField txtidgudang;
    public static javax.swing.JTextField txtkode;
    public static javax.swing.JTextField txtmerek;
    public static javax.swing.JTextField txtnamaalat;
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
