/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaksi;

import FAkses.MenuItem;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author PC
 */
public class Fsetorsmi extends javax.swing.JFrame {

    String cekd;
    String st;

    /**
     * Creates new form Fpenjualan
     */
    public Fsetorsmi() {
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
        cek();
        menus.add(jPanel1);
        cbidpenjualangudang.setVisible(false);
        cbidpenjualancabang.setVisible(false);
        cbidpenjualancabang.setVisible(false);
        tmp_penjualan_cabang();
        tmp_penjualan_gudang();
        idapa.setVisible(false);
        hakakses();
        pilih();
    }

    public void tanggal() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(date);
        txttanggalsetor.setText(currentDate);
    }

    public void jam() {
        // Mendapatkan waktu saat ini
        LocalTime waktu = LocalTime.now();

        // Mendefinisikan format waktu yang diinginkan
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Mengubah waktu menjadi string dalam format yang diinginkan
        String waktuFormatted = waktu.format(formatter);

        // Menampilkan waktu dalam format yang diinginkan di TextField
        jamsetor.setText(waktuFormatted);
    }

    public void cek() {

        jCheckBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jCheckBox1.isSelected()) {
                    cbidpenjualangudang.setVisible(true);
                    cekd = "gudang";
                    idapa.setVisible(true);
                    idapa.setText("gudang");
                    jCheckBox2.setVisible(false);
                } else {
                    cbidpenjualangudang.setVisible(false);
                    jCheckBox2.setVisible(true);

                }
            }
        });

        jCheckBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jCheckBox2.isSelected()) {
                    cbidpenjualancabang.setVisible(true);
                    cekd = "cabang";
                    idapa.setVisible(true);
                    idapa.setText("cabang");
                    jCheckBox1.setVisible(false);
                } else {
                    cbidpenjualancabang.setVisible(false);
                    jCheckBox1.setVisible(true);
                }
            }
        });

    }

    public void tmp_penjualan_cabang() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT id_penjualan FROM tb_penjualan_cabang ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String add = rs.getString("id_penjualan");

                cbidpenjualancabang.addItem(add);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        cbidpenjualancabang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = cbidpenjualancabang.getSelectedItem().toString();

                try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                    String sql = "SELECT * FROM tb_penjualan_cabang WHERE id_penjualan = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, id);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        String id_cabang = rs.getString("id_cabang");
                        String kode_alat = rs.getString("kode_alat");
                        String nama_alat = rs.getString("nama_alat");
                        String merek = rs.getString("merek");
                        String hargajual = rs.getString("hargajual");
                        String jumlahjual = rs.getString("jumlahjual");
                        String hargaSetorSMI = rs.getString("hargaSetorSMI");
                        String pokok = rs.getString("pokok");
                        String profitSMI = rs.getString("profitSMI");
                        String tgl_jual = rs.getString("tgl_jual");
                        textid.setText(id_cabang);
                        kd_alat.setText(kode_alat);
                        namaalat.setText(nama_alat);
                        txthargajual.setText(hargajual);
                        txtmerek.setText(merek);
                        jumljual.setText(jumlahjual);
                        tanggalterpakai.setText(tgl_jual);
                        hargasetorsmi.setText(hargaSetorSMI);
                        jam();
                        tanggal();
                        txtpokok.setText(pokok);
                        profit.setText(profitSMI);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public void tmp_penjualan_gudang() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT id_penjualan FROM tb_penjualan_gudang ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String add = rs.getString("id_penjualan");

                cbidpenjualangudang.addItem(add);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        cbidpenjualangudang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = cbidpenjualangudang.getSelectedItem().toString();

                try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                    String sql = "SELECT * FROM tb_penjualan_gudang WHERE id_penjualan = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, id);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        String id_gudang = rs.getString("id_gudang");
                        String kode_alat = rs.getString("kode_alat");
                        String nama_alat = rs.getString("nama_alat");
                        String merek = rs.getString("merek");
                        String hargajual = rs.getString("harga_jual");
                        String jumlahjual = rs.getString("jumlah_jual");
                        String hargaSetorSMI = rs.getString("harga_setor_smi");
                        String pokok = rs.getString("pokok");
                        String profitSMI = rs.getString("profit_smi");
                        String tgl_jual = rs.getString("tgl_jual");
                        textid.setText(id_gudang);
                        kd_alat.setText(kode_alat);
                        namaalat.setText(nama_alat);
                        txthargajual.setText(hargajual);
                        txtmerek.setText(merek);
                        jumljual.setText(jumlahjual);
                        tanggalterpakai.setText(tgl_jual);
                        hargasetorsmi.setText(hargaSetorSMI);
                        jam();
                        tanggal();
                        txtpokok.setText(pokok);
                        profit.setText(profitSMI);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static String generateIDsetorgudang() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String id_penjualan = null;
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String idToCheck = String.format("IDSETRGD%03d", newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM tb_setor_gudang WHERE id_setor = ?";
                PreparedStatement checkIdStatement = conn.prepareStatement(checkIdQuery);
                checkIdStatement.setString(1, idToCheck);
                ResultSet resultSet = checkIdStatement.executeQuery();

                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }

                if (count == 0) {
                    id_penjualan = idToCheck;
                    idExists = false;
                } else {
                    newId++;
                }
            }

            return id_penjualan;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateIDsetorCabang() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String id_penjualan = null;
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String idToCheck = String.format("IDSETRCB%03d", newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM tb_setor_cabang WHERE id_setor = ?";
                PreparedStatement checkIdStatement = conn.prepareStatement(checkIdQuery);
                checkIdStatement.setString(1, idToCheck);
                ResultSet resultSet = checkIdStatement.executeQuery();

                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }

                if (count == 0) {
                    id_penjualan = idToCheck;
                    idExists = false;
                } else {
                    newId++;
                }
            }

            return id_penjualan;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void hakakses() {
        String status = SESSION.SessionLevel.getSessionLevel();

        if (status.equals("Admin")) {
            cbidpenjualangudang.setEnabled(false);
            kd_alat.setEnabled(false);
            namaalat.setEnabled(false);
            txthargajual.setEnabled(false);
            txtmerek.setEnabled(false);
            textid.setEnabled(false);
            tanggalterpakai.setEnabled(false);
            jumljual.setEnabled(false);
            jamsetor.setEnabled(false);
            txtjumlahsetor.setEnabled(false);
            cbidpenjualancabang.setEnabled(false);
            hargasetorsmi.setEnabled(false);
            txtpokok.setEnabled(false);
            profit.setEnabled(false);
            txttanggalsetor.setEnabled(false);
        } else if (status.equals("Gudang")) {

        } else if (status.equals("Direktur")) {

        } else {
            cbidpenjualangudang.setEnabled(false);
            kd_alat.setEnabled(false);
            namaalat.setEnabled(false);
            txthargajual.setEnabled(false);
            txtmerek.setEnabled(false);
            textid.setEnabled(false);
            tanggalterpakai.setEnabled(false);
            jumljual.setEnabled(false);
            jamsetor.setEnabled(false);
            txtjumlahsetor.setEnabled(false);
            cbidpenjualancabang.setEnabled(false);
            hargasetorsmi.setEnabled(false);
            txtpokok.setEnabled(false);
            profit.setEnabled(false);
            txttanggalsetor.setEnabled(false);
        }
    }
    
        public void pilih() {
        jComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jComboBox2.getSelectedIndex();
                // contains menyamakan data yang hampir mirip di combobox
                if (selectedIndex == 1) {
                    jLabel34.setText("Gudang");

                    tampil_gudang();
                } else if (selectedIndex == 2) {
                    tampil_cabang();
                    jLabel34.setText("Cabang");

                }
            }
        });
    }
        
     public void tampil_gudang() {
        String cabang = SESSION.SessionCabang.getSessionCabang();
        String sta = SESSION.SessionStatus2.getSessionStatus2();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_setor_gudang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(cabang);
            System.out.println(sta);

            ResultSet rs = stmt.executeQuery();

            // Menghapus data sebelumnya dari model tabel
            DefaultTableModel tableModel = (DefaultTableModel) rSTableMetro1.getModel();
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("id_setor");
            tableModel.addColumn("id_penjualan");
            tableModel.addColumn("id_gudang");
            tableModel.addColumn("kode_alat");
            tableModel.addColumn("nama_alat");
            tableModel.addColumn("harga_jual");
            tableModel.addColumn("merek");
            tableModel.addColumn("jumlah_jual");
            tableModel.addColumn("tanggal_terpakai");
            tableModel.addColumn("harga_setor");
            tableModel.addColumn("jam");
            tableModel.addColumn("jumlah_setor");
            tableModel.addColumn("tanggal_setor");
            tableModel.addColumn("pokok");
            tableModel.addColumn("profit");

            while (rs.next()) {
                String id_setor = rs.getString("id_setor");
                String id_penjualan = rs.getString("id_penjualan");
                String id_cabang = rs.getString("id_cabang");
                String kode_alat = rs.getString("kode_alat");
                String nama_alat = rs.getString("nama_alat");
                String harga_jual = rs.getString("harga_jual");
                String merek = rs.getString("merek");               
                String jumlah_jual = rs.getString("jumlah_jual");
                String tanggal_terpakai = rs.getString("tanggal_terpakai");
                String harga_setor = rs.getString("harga_setor");
                String jam = rs.getString("jam");
                String jumlah_setor = rs.getString("jumlah_setor");
                String tanggal_setor = rs.getString("tanggal_setor");
                String pokok = rs.getString("pokok");
                String profit2 = rs.getString("profit");

                Object[] rowData = {id_setor,id_penjualan, id_cabang, kode_alat, nama_alat, harga_jual, merek, tanggal_terpakai, jumlah_jual, harga_setor, jam, jumlah_setor, tanggal_setor, pokok, profit2};
                tableModel.addRow(rowData);
            }

            // Set model tabel pada objek JTable
            rSTableMetro1.setModel(tableModel);

            // Mengatur lebar kolom
            TableColumnModel columnModel = rSTableMetro1.getColumnModel();
            columnModel.getColumn(4).setPreferredWidth(20); // Kolom Jumlah

            // Mengatur tinggi baris
            rSTableMetro1.setRowHeight(30); // Tinggi baris menjadi 30 piksel
        } catch (SQLException e) {
//            e.printStackTrace();
        }
     }

    
     public void tampil_cabang() {
        String cabang = SESSION.SessionCabang.getSessionCabang();
        String sta = SESSION.SessionStatus2.getSessionStatus2();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_setor_cabang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(cabang);
            System.out.println(sta);

            ResultSet rs = stmt.executeQuery();

            // Menghapus data sebelumnya dari model tabel
            DefaultTableModel tableModel = (DefaultTableModel) rSTableMetro1.getModel();
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("id_setor");
            tableModel.addColumn("id_penjualan");
            tableModel.addColumn("id_cabang");
            tableModel.addColumn("kode_alat");
            tableModel.addColumn("nama_alat");
            tableModel.addColumn("harga_jual");
            tableModel.addColumn("merek");
            tableModel.addColumn("jumlah_jual");
            tableModel.addColumn("tanggal_terpakai");
            tableModel.addColumn("harga_setor");
            tableModel.addColumn("jam");
            tableModel.addColumn("jumlah_setor");
            tableModel.addColumn("tanggal_setor");
            tableModel.addColumn("pokok");
            tableModel.addColumn("profit");

            while (rs.next()) {
                String id_setor = rs.getString("id_setor");
                String id_penjualan = rs.getString("id_penjualan");
                String id_cabang = rs.getString("id_cabang");
                String kode_alat = rs.getString("kode_alat");
                String nama_alat = rs.getString("nama_alat");
                String harga_jual = rs.getString("harga_jual");
                String merek = rs.getString("merek");               
                String jumlah_jual = rs.getString("jumlah_jual");
                String tanggal_terpakai = rs.getString("tanggal_terpakai");
                String harga_setor = rs.getString("harga_setor");
                String jam = rs.getString("jam");
                String jumlah_setor = rs.getString("jumlah_setor");
                String tanggal_setor = rs.getString("tanggal_setor");
                String pokok = rs.getString("pokok");
                String profit2 = rs.getString("profit");

                Object[] rowData = {id_setor,id_penjualan, id_cabang, kode_alat, nama_alat, harga_jual, merek, tanggal_terpakai, jumlah_jual, harga_setor, jam, jumlah_setor, tanggal_setor, pokok, profit2};
                tableModel.addRow(rowData);
            }

            // Set model tabel pada objek JTable
            rSTableMetro1.setModel(tableModel);

            // Mengatur lebar kolom
            TableColumnModel columnModel = rSTableMetro1.getColumnModel();
            columnModel.getColumn(4).setPreferredWidth(20); // Kolom Jumlah

            // Mengatur tinggi baris
            rSTableMetro1.setRowHeight(30); // Tinggi baris menjadi 30 piksel
        } catch (SQLException e) {
//            e.printStackTrace();
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
        menus = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        cbidpenjualangudang = new javax.swing.JComboBox<>();
        cbidpenjualancabang = new javax.swing.JComboBox<>();
        namaalat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        idapa = new javax.swing.JLabel();
        txtmerek = new javax.swing.JTextField();
        tanggalterpakai = new javax.swing.JTextField();
        kd_alat = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rSTableMetro1 = new rojerusan.RSTableMetro();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        txthargajual = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        txtjumlahsetor = new javax.swing.JTextField();
        txttanggalsetor = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jamsetor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtpokok = new javax.swing.JTextField();
        hargasetorsmi = new javax.swing.JTextField();
        profit = new javax.swing.JTextField();
        jumljual = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        textid = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(menus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 190, 620));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/sidebar.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jCheckBox1.setText("id penjualan gudang");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        jPanel1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, -1));

        jCheckBox2.setText("id penjualan cabang");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, -1, -1));

        cbidpenjualangudang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id_penjualan_gudang" }));
        jPanel1.add(cbidpenjualangudang, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 130, -1));

        cbidpenjualancabang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID_penjualan_cabang" }));
        cbidpenjualancabang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbidpenjualancabangItemStateChanged(evt);
            }
        });
        cbidpenjualancabang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbidpenjualancabangActionPerformed(evt);
            }
        });
        jPanel1.add(cbidpenjualancabang, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, 140, -1));

        namaalat.setEditable(false);
        jPanel1.add(namaalat, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 130, -1));

        jLabel5.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel5.setText("merek");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 80, -1));

        jLabel8.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel8.setText("harga setor smi");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 170, 100, -1));

        idapa.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        idapa.setText("id");
        jPanel1.add(idapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 100, -1));
        jPanel1.add(txtmerek, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 340, 130, -1));

        tanggalterpakai.setEditable(false);
        tanggalterpakai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tanggalterpakaiActionPerformed(evt);
            }
        });
        jPanel1.add(tanggalterpakai, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 140, -1));
        jPanel1.add(kd_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 130, -1));

        jLabel11.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel11.setText("pokok");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 220, 100, -1));

        jLabel9.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel9.setText("profit smi");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 270, 80, -1));

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

        rSMaterialButtonRectangle1.setText("simpan");
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 280, 140, 50));

        txthargajual.setEditable(false);
        jPanel1.add(txthargajual, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 130, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pilih table", "table penjualan gudang", "table penjualan cabang" }));
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 450, 120, -1));
        jPanel1.add(txtjumlahsetor, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 340, 140, -1));
        jPanel1.add(txttanggalsetor, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 340, 140, -1));

        jLabel12.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel12.setText("kode alat");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 80, -1));
        jPanel1.add(jamsetor, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 140, -1));

        jLabel10.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel10.setText("nama alat");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 80, -1));

        jLabel13.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel13.setText("tanggal setor");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 80, -1));

        jLabel14.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel14.setText("harga jual");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 80, -1));

        jLabel15.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel15.setText("jumlah jual");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 220, 100, -1));

        jLabel16.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel16.setText("jam setor");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 80, -1));
        jPanel1.add(txtpokok, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 240, 140, -1));
        jPanel1.add(hargasetorsmi, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, 140, -1));
        jPanel1.add(profit, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, 140, -1));
        jPanel1.add(jumljual, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, 140, -1));

        jLabel17.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel17.setText("jumlah di setor");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, 100, -1));
        jPanel1.add(textid, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 140, -1));

        jLabel18.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel18.setText("tanggal terpakai");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 110, -1));

        jLabel19.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel19.setText("Id");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 20, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/close.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 0, -1, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel4.setText("FORM SETOR");

        jLabel34.setText("Jlabel");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addComponent(jLabel34))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 160, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Form Alat - Clean.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
        String idsetor = generateIDsetorgudang();
        String idsetor2 = generateIDsetorCabang();
        String cbgudang = cbidpenjualangudang.getSelectedItem().toString();
        String cbcabang = cbidpenjualancabang.getSelectedItem().toString();
        String id = textid.getText();
        String kodealat = kd_alat.getText();
        String nama_alat = namaalat.getText();
        String harga_jualc = txthargajual.getText();
        BigDecimal harga_jual = null;

        String merek = txtmerek.getText();
        String jumlah_jualc = jumljual.getText();
        int jumlah_jual = Integer.parseInt(jumlah_jualc);

        Date tglterpakai = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            tglterpakai = dateFormat.parse(tanggalterpakai.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String harga_setorc = hargasetorsmi.getText();
        BigDecimal harga_setor = null;

        String jamc = jamsetor.getText();

        String jumlah_setorc = txtjumlahsetor.getText();
        int jumlah_setor = Integer.parseInt(jumlah_setorc);

        Date tglsetor = null;

        try {
            tglsetor = dateFormat.parse(txttanggalsetor.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String pokokc = txtpokok.getText();
        BigDecimal pokok = null;

        String pftc = profit.getText();
        BigDecimal pft = null;

        if (cekd.equals("gudang")) {
            if (kodealat.isEmpty() || nama_alat.isEmpty() || harga_jualc.isEmpty() || merek.isEmpty() || jumlah_jualc.isEmpty() || harga_setorc.isEmpty() || pokokc.isEmpty() || pftc.isEmpty() || jumlah_setorc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua wajib diisi");
            } else {
                try {
                    harga_jual = new BigDecimal(harga_jualc);
                    harga_setor = new BigDecimal(harga_setorc);
                    pokok = new BigDecimal(pokokc);
                    pft = new BigDecimal(pftc);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Format harga, pokok, atau profit tidak valid");
                    return;
                }

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                    String sql = "INSERT INTO tb_setor_gudang (id_setor, id_penjualan, id_gudang, kode_alat, nama_alat, harga_jual, merek, jumlah_jual, tanggal_terpakai, harga_setor, jam, jumlah_setor, tanggal_setor, pokok, profit) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, idsetor);
                    statement.setString(2, cbgudang);
                    statement.setString(3, id);
                    statement.setString(4, kodealat);
                    statement.setString(5, nama_alat);
                    statement.setBigDecimal(6, harga_jual);
                    statement.setString(7, merek);
                    statement.setInt(8, jumlah_jual);
                    statement.setDate(9, new java.sql.Date(tglterpakai.getTime()));
                    statement.setBigDecimal(10, harga_setor);
                    statement.setString(11, jamc);
                    statement.setInt(12, jumlah_setor);
                    statement.setDate(13, new java.sql.Date(tglsetor.getTime()));
                    statement.setBigDecimal(14, pokok);
                    statement.setBigDecimal(15, pft);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (cekd.equals("cabang")) {
            if (kodealat.isEmpty() || nama_alat.isEmpty() || harga_jualc.isEmpty() || merek.isEmpty() || jumlah_jualc.isEmpty() || harga_setorc.isEmpty() || pokokc.isEmpty() || pftc.isEmpty() || jumlah_setorc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua wajib diisi");
            } else {
                try {
                    harga_jual = new BigDecimal(harga_jualc);
                    harga_setor = new BigDecimal(harga_setorc);
                    pokok = new BigDecimal(pokokc);
                    pft = new BigDecimal(pftc);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Format harga, pokok, atau profit tidak valid");
                    return;
                }

                try (Connection conn = Koneksi.KoneksiDatabase.getKoneksi()) {
                    String sql = "INSERT INTO tb_setor_cabang (id_setor, id_penjualan, id_cabang, kode_alat, nama_alat, harga_jual, merek, jumlah_jual, tanggal_terpakai, harga_setor, jam, jumlah_setor, tanggal_setor, pokok, profit) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, idsetor2);
                    statement.setString(2, cbcabang);
                    statement.setString(3, id);
                    statement.setString(4, kodealat);
                    statement.setString(5, nama_alat);
                    statement.setBigDecimal(6, harga_jual);
                    statement.setString(7, merek);
                    statement.setInt(8, jumlah_jual);
                    statement.setDate(9, new java.sql.Date(tglterpakai.getTime()));
                    statement.setBigDecimal(10, harga_setor);
                    statement.setString(11, jamc);
                    statement.setInt(12, jumlah_setor);
                    statement.setDate(13, new java.sql.Date(tglsetor.getTime()));
                    statement.setBigDecimal(14, pokok);
                    statement.setBigDecimal(15, pft);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void cbidpenjualancabangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbidpenjualancabangActionPerformed
        // TODO add your handling code here:

//        
    }//GEN-LAST:event_cbidpenjualancabangActionPerformed

    private void cbidpenjualancabangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbidpenjualancabangItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_cbidpenjualancabangItemStateChanged

    private void tanggalterpakaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tanggalterpakaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tanggalterpakaiActionPerformed

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
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Fpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Fpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Fpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Fpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Fsetorsmi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbidpenjualancabang;
    private javax.swing.JComboBox<String> cbidpenjualangudang;
    private javax.swing.JTextField hargasetorsmi;
    private javax.swing.JLabel idapa;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jamsetor;
    private javax.swing.JTextField jumljual;
    private javax.swing.JTextField kd_alat;
    private javax.swing.JPanel menus;
    private javax.swing.JTextField namaalat;
    private javax.swing.JTextField profit;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSTableMetro rSTableMetro1;
    private javax.swing.JTextField tanggalterpakai;
    private javax.swing.JTextField textid;
    private javax.swing.JTextField txthargajual;
    private javax.swing.JTextField txtjumlahsetor;
    private javax.swing.JTextField txtmerek;
    private javax.swing.JTextField txtpokok;
    private javax.swing.JTextField txttanggalsetor;
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
