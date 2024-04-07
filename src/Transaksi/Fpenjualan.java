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
public class Fpenjualan extends javax.swing.JFrame {

    String cek;
    String st;

    /**
     * Creates new form Fpenjualan
     */
    public Fpenjualan() {
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
        cbiDgudang.setVisible(false);
        cbiDcabang.setVisible(false);
        cbCabang.setVisible(false);
        jLabel12.setVisible(false);
//        comboboxcabang();
        comboboxidgudang();
        idmrket();
        sss.setVisible(false);
//        listenercabang();
        cbiDcabang.setVisible(false);
        hakakses();
        pilih();
        tampil();
    }

    public void comboboxidgudang() {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT id_gudang FROM tb_gudang ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String add = rs.getString("id_gudang");

                cbiDgudang.addItem(add);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        cbiDgudang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Cabang2 = cbiDgudang.getSelectedItem().toString();

                try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                    String sql = "SELECT * FROM tb_gudang WHERE id_gudang = ? ";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, Cabang2);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        String kalat = rs.getString("kode_alat");
                        String nalat = rs.getString("nama_alat");
                        String harga = rs.getString("harga");
                        String stockgudang = rs.getString("jumlah");
                        String merek = rs.getString("merek");

                        kd_alat.setText(kalat);
                        namaalat.setText(nalat);
                        hargaalat.setText(harga);
                        stockdi.setText(stockgudang);
                    }
                    sss.setText("gudang");
                    sss.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }

    public void cek() {
        cbCabang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                cbiDcabang.setVisible(true);

            }
        });
        jCheckBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jCheckBox1.isSelected()) {
                    cbiDgudang.setVisible(true);
                    jCheckBox2.setVisible(false);
                    cbiDcabang.setVisible(false);

                    st = "idgdng";
                } else {
                    cbiDgudang.setVisible(false);
                    jCheckBox2.setVisible(true);
//                    resetPanel(jPanel1);
                    cbiDcabang.setVisible(false);

                }
            }
        });

        jCheckBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jCheckBox2.isSelected()) {
                    st = "id_cabang";

                    cbCabang.setVisible(true);
                    jLabel12.setVisible(true);
                    jCheckBox1.setVisible(false);
                    cbCabang.setEnabled(true);
                    jCheckBox2.setEnabled(false);
                    cbCabang.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String cabang2 = cbCabang.getSelectedItem().toString();

                            try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                                String sql = "SELECT * FROM tb_cabang WHERE Cabang = ?";
                                PreparedStatement ps = con.prepareStatement(sql);
                                ps.setString(1, cabang2);

                                ResultSet rs = ps.executeQuery();

                                cbiDcabang.removeAllItems();
                                while (rs.next()) {
                                    String add = rs.getString("id_cabang");
                                    cbiDcabang.addItem(add);
                                    cbCabang.setEnabled(false);

                                }

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            cbiDcabang.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    String id = cbiDcabang.getSelectedItem().toString();

                                    try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                                        String query = "SELECT * FROM tb_cabang WHERE id_cabang = ?";
                                        PreparedStatement preparedStatement = con.prepareStatement(query);
                                        preparedStatement.setString(1, id);

                                        ResultSet resultSet = preparedStatement.executeQuery();

                                        if (resultSet.next()) {
                                            String kalat = resultSet.getString("kode_alat");
                                            String nalat = resultSet.getString("nama_alat");
                                            String harga = resultSet.getString("harga_alat");
                                            String stockgudang = resultSet.getString("jumlah");
                                            String merek = resultSet.getString("merek");

                                            kd_alat.setText(kalat);
                                            namaalat.setText(nalat);
                                            hargaalat.setText(harga);
                                            stockdi.setText(stockgudang);
                                        }
                                        sss.setText("cabang");
                                        sss.setVisible(true);
                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }
                    });

                } else {
                    cbiDcabang.setVisible(false);
                    cbCabang.setVisible(false);
                    jLabel12.setVisible(false);
                    jCheckBox1.setVisible(true);
//                    resetPanel(jPanel1);

                }
            }
        });

    }

//    public void resetPanel(JPanel panel) {
//        Component[] components = panel.getComponents();
//
//        for (Component component : components) {
//            if (component instanceof JTextField) {
//                JTextField textField = (JTextField) component;
//                textField.setText("");
//            } else if (component instanceof JComboBox) {
//                JComboBox comboBox = (JComboBox) component;
//                comboBox.setSelectedIndex(0);
//            } else if (component instanceof JRadioButton) {
//                JRadioButton radioButton = (JRadioButton) component;
//                radioButton.setSelected(false);
////            } else if (component instanceof JCheckBox) {
////                JCheckBox checkBox = (JCheckBox) component;
////                checkBox.setSelected(false);
//            }
//            // Tambahkan kondisi untuk komponen lain yang ingin Anda reset nilainya
//        }
//    }
    public void idmrket() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT id_user , level FROM user WHERE level = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Marketing");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String add = rs.getString("id_user");

                jComboBox1.addItem(add);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        jComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = jComboBox1.getSelectedItem().toString();

                try (Connection con = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
                    String sql = "SELECT nama  FROM user WHERE id_user = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, id);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        String nama = rs.getString("nama");
                        jTextField6.setText(nama);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static String generateidpenjualanGudang() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String id_penjualan = null;
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String idToCheck = String.format("IDPNGD%03d", newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM tb_penjualan_gudang WHERE id_penjualan = ?";
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

    public static String generateidpenjualanCabang() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String id_penjualan = null;
            int newId = 1;
            boolean idExists = true;

            while (idExists) {
                String idToCheck = String.format("IDPNCB%03d", newId);
                String checkIdQuery = "SELECT COUNT(*) AS count FROM tb_penjualan_cabang WHERE id_penjualan = ?";
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

    public String cariMerekBerdasarkanKode(String kodeAlat) {
        String merek = "";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
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

    private void hakakses() {
        String status = SESSION.SessionLevel.getSessionLevel();

        if (status.equals("Admin")) {
            cbiDgudang.setEnabled(false);
            kd_alat.setEnabled(false);
            namaalat.setEnabled(false);
            hargaalat.setEnabled(false);
            jTextField3.setEnabled(false);
            jTextField4.setEnabled(false);
            cbiDcabang.setEnabled(false);
            cbCabang.setEnabled(false);
            jComboBox1.setEnabled(false);
            jTextField6.setEnabled(false);
            stockdi.setEnabled(false);
            jTextField2.setEnabled(false);
            jButton1.setEnabled(false);
            rSMaterialButtonRectangle1.setEnabled(false);
        } else if (status.equals("Gudang")) {

        } else if (status.equals("Direktur")) {

        } else {
            cbiDgudang.setEnabled(false);
            kd_alat.setEnabled(false);
            namaalat.setEnabled(false);
            hargaalat.setEnabled(false);
            jTextField3.setEnabled(false);
            jTextField4.setEnabled(false);
            cbiDcabang.setEnabled(false);
            cbCabang.setEnabled(false);
            jComboBox1.setEnabled(false);
            jTextField6.setEnabled(false);
            stockdi.setEnabled(false);
            jTextField2.setEnabled(false);
            jButton1.setEnabled(false);
            rSMaterialButtonRectangle1.setEnabled(false);
        }
    }

    public void pilih() {
        jComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jComboBox2.getSelectedIndex();
                // contains menyamakan data yang hampir mirip di combobox
                if (selectedIndex == 1) {
                    jLabel3.setText("Gudang");

                    tampil2();
                } else if (selectedIndex == 2) {
                    tampil();
                    jLabel3.setText("Cabang");

                }
            }
        });
    }

    public void tampil() {
        String cabang = SESSION.SessionCabang.getSessionCabang();
        String st = SESSION.SessionStatus2.getSessionStatus2();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_penjualan_cabang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(cabang);
            System.out.println(st);

            ResultSet rs = stmt.executeQuery();

            // Menghapus data sebelumnya dari model tabel
            DefaultTableModel tableModel = (DefaultTableModel) rSTableMetro1.getModel();
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("id_penjualan");
            tableModel.addColumn("id_cabang");
            tableModel.addColumn("kode_alat");
            tableModel.addColumn("nama_alat");
            tableModel.addColumn("merek");
            tableModel.addColumn("id_user");
            tableModel.addColumn("nama_marketing");
            tableModel.addColumn("hargajual");
            tableModel.addColumn("jumlahjual");
            tableModel.addColumn("total");
            while (rs.next()) {
                String id_penjualan = rs.getString("id_penjualan");
                String id_cabang = rs.getString("id_cabang");
                String kode_alat = rs.getString("kode_alat");
                String nama_alat = rs.getString("nama_alat");
                String merek = rs.getString("merek");
                String id_user = rs.getString("id_user");
                String nama_marketing = rs.getString("nama_marketing");
                String hargajual = rs.getString("hargajual");
                String jumlahjual = rs.getString("jumlahjual");
                String total = rs.getString("total");

                Object[] rowData = {id_penjualan, id_cabang, kode_alat, nama_alat, merek, id_user, nama_marketing, hargajual, jumlahjual, total};
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

    public void tampil2() {
        String cabang = SESSION.SessionCabang.getSessionCabang();
        String st = SESSION.SessionStatus2.getSessionStatus2();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2", "root", "")) {
            String sql = "SELECT * FROM tb_penjualan_gudang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(cabang);
            System.out.println(st);

            ResultSet rs = stmt.executeQuery();

            // Menghapus data sebelumnya dari model tabel
            DefaultTableModel tableModel = (DefaultTableModel) rSTableMetro1.getModel();
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("id_penjualan");
            tableModel.addColumn("id_gudang");
            tableModel.addColumn("kode_alat");
            tableModel.addColumn("nama_alat");
            tableModel.addColumn("merek");
            tableModel.addColumn("id_user");
            tableModel.addColumn("nama_marketing");
            tableModel.addColumn("harga_jual");
            tableModel.addColumn("jumlah_jual");
            tableModel.addColumn("total");
            while (rs.next()) {
                String id_penjualan = rs.getString("id_penjualan");
                String id_cabang = rs.getString("id_gudang");
                String kode_alat = rs.getString("kode_alat");
                String nama_alat = rs.getString("nama_alat");
                String merek = rs.getString("merek");
                String id_user = rs.getString("id_user");
                String nama_marketing = rs.getString("nama_marketing");
                String hargajual = rs.getString("harga_jual");
                String jumlahjual = rs.getString("jumlah_jual");
                String total = rs.getString("total");

                Object[] rowData = {id_penjualan, id_cabang, kode_alat, nama_alat, merek, id_user, nama_marketing, hargajual, jumlahjual, total};
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
        cbCabang = new javax.swing.JComboBox<>();
        cbiDgudang = new javax.swing.JComboBox<>();
        cbiDcabang = new javax.swing.JComboBox<>();
        namaalat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        kd_alat = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rSTableMetro1 = new rojerusan.RSTableMetro();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        sss = new javax.swing.JLabel();
        hargaalat = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        stockdi = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(menus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 190, 610));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/sidebar.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jCheckBox1.setText("ambil Id gudang");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        jPanel1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, -1));

        jCheckBox2.setText("ambil Id cabang");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, -1, -1));

        cbCabang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "cabang", "Bandung", "Bali", "Jogja", "Semarang", "Jakarta", "Jatim", "Solo" }));
        cbCabang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCabangItemStateChanged(evt);
            }
        });
        jPanel1.add(cbCabang, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 90, -1));

        cbiDgudang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id gudang" }));
        jPanel1.add(cbiDgudang, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 130, -1));

        cbiDcabang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH ID CABANG" }));
        cbiDcabang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbiDcabangItemStateChanged(evt);
            }
        });
        cbiDcabang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbiDcabangActionPerformed(evt);
            }
        });
        jPanel1.add(cbiDcabang, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 220, -1));

        namaalat.setEditable(false);
        jPanel1.add(namaalat, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 130, -1));

        jLabel8.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel8.setText("harga alat");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 80, -1));

        jLabel5.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel5.setText("jumlah jual");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 80, -1));
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 340, 130, -1));

        jLabel6.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel6.setText("nama marketing");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 220, 110, -1));

        jLabel7.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel7.setText("total");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, 80, -1));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 340, 130, -1));

        jTextField4.setEditable(false);
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 390, 130, -1));

        jLabel9.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel9.setText("nama alat");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 80, -1));
        jPanel1.add(kd_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 130, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id marketing" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 220, -1));

        jLabel11.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel11.setText("kode alat");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 80, -1));

        jLabel12.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel12.setText("cabang");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 80, -1));
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, 220, -1));

        jLabel13.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel13.setText("id marketing");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 80, -1));

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

        sss.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        sss.setText("mana");
        jPanel1.add(sss, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 270, 50, -1));

        hargaalat.setEditable(false);
        jPanel1.add(hargaalat, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 130, -1));

        jLabel15.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel15.setText("harga jual");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, 80, -1));

        stockdi.setEditable(false);
        stockdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockdiActionPerformed(evt);
            }
        });
        jPanel1.add(stockdi, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 220, -1));

        jLabel16.setFont(new java.awt.Font("Lucida Fax", 0, 11)); // NOI18N
        jLabel16.setText("Stock di ");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 50, -1));

        jButton1.setText("totalkan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 340, 80, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pilih table", "table penjualan gudang", "table penjualan cabang" }));
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 450, 120, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/close.png"))); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 0, -1, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel4.setText("FORM PENJUALAN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 150, 30));

        jLabel3.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel3.setText("CABANG");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, 30));

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
        String jumlah_jual = jTextField3.getText();
        int jumlahjual = Integer.parseInt(jumlah_jual);
        // Mengambil nilai dari TextField atau variabel yang sesuai
        double hargaJual = Double.parseDouble(jTextField2.getText());
        double pokok2 = Double.parseDouble(hargaalat.getText());

        double pokok1 = pokok2 * jumlahjual;
        // Menghitung 'HARGA SETOR SMI' (60% dari 'HARGA JUAL')
        double hargaSetorSMI2 = 0.6 * hargaJual;
        // Menghitung 'PROFIT SMI' ('HARGA SETOR SMI' dikurangi 'POKOK')
        double profitSMI2 = pokok1 - hargaSetorSMI2;

        BigDecimal profitSMI = new BigDecimal(profitSMI2);

        BigDecimal pokok = new BigDecimal(pokok2);

        BigDecimal hargaSetorSMI = new BigDecimal(hargaSetorSMI2);

        String idpenjualan = generateidpenjualanGudang();
        String idpenjualan2 = generateidpenjualanCabang();

        String id_gudang = cbiDgudang.getSelectedItem().toString();
        String id_cabang = cbiDcabang.getSelectedItem().toString();
        String id_marketing = jComboBox1.getSelectedItem().toString();
        String nama_marketing = jTextField6.getText();

        String kodealat = kd_alat.getText();
        String merek = cariMerekBerdasarkanKode(kodealat);
        String nama_alat = namaalat.getText();
        Date tgljual = new Date();
        String hargajualc = jTextField2.getText();
        BigDecimal hargajual = new BigDecimal(hargajualc);
        String totalc = jTextField4.getText();
        BigDecimal total = new BigDecimal(totalc);

        // Lanjutan kode yang ada setelah ini...
        if (st.equals("idgdng")) {
            if (kodealat.isEmpty() || nama_alat.isEmpty() || nama_marketing.isEmpty() || jumlah_jual.isEmpty() || kodealat.isEmpty() || hargajualc.isEmpty() || nama_alat.isEmpty() || kodealat.isEmpty() || totalc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua wajib diisi");
            } else {
                try (Connection conn = Koneksi.KoneksiDatabase.getKoneksi()) {
                    String sql = "INSERT INTO tb_penjualan_gudang ("
                            + "id_penjualan, id_gudang, kode_alat, nama_alat, merek, id_user, nama_marketing, harga_jual, jumlah_jual, total, harga_setor_smi, pokok, profit_smi, tgl_jual)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";

                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, idpenjualan);
                    statement.setString(2, id_gudang);
                    statement.setString(3, kodealat);
                    statement.setString(4, nama_alat);
                    statement.setString(5, merek);
                    statement.setString(6, id_marketing);
                    statement.setString(7, nama_marketing);
                    statement.setBigDecimal(8, hargajual);
                    statement.setInt(9, jumlahjual);
                    statement.setBigDecimal(10, total);
                    statement.setBigDecimal(11, hargaSetorSMI);
                    statement.setBigDecimal(12, pokok);
                    statement.setBigDecimal(13, profitSMI);
                    statement.setDate(14, new java.sql.Date(tgljual.getTime()));

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (st.equals("id_cabang")) {
            if (kodealat.isEmpty() || nama_alat.isEmpty() || nama_marketing.isEmpty() || jumlah_jual.isEmpty() || kodealat.isEmpty() || hargajualc.isEmpty() || nama_alat.isEmpty() || kodealat.isEmpty() || totalc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua wajib diisi");
            } else {
                try (Connection conn = Koneksi.KoneksiDatabase.getKoneksi()) {
                    String sql = "INSERT INTO tb_penjualan_cabang ("
                            + "id_penjualan, id_cabang, kode_alat, nama_alat, merek, id_user, nama_marketing, hargajual, jumlahjual, total, hargaSetorSMI, pokok, profitSMI, tgl_jual)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, idpenjualan2);
                    statement.setString(2, id_cabang);
                    statement.setString(3, kodealat);
                    statement.setString(4, nama_alat);
                    statement.setString(5, merek);
                    statement.setString(6, id_marketing);
                    statement.setString(7, nama_marketing);
                    statement.setBigDecimal(8, hargajual);
                    statement.setInt(9, jumlahjual);
                    statement.setBigDecimal(10, total);
                    statement.setBigDecimal(11, hargaSetorSMI);
                    statement.setBigDecimal(12, pokok);
                    statement.setBigDecimal(13, profitSMI);
                    statement.setDate(14, new java.sql.Date(tgljual.getTime()));

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                    }
                    new Fpenjualan().setVisible(true);
                    dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void stockdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockdiActionPerformed

    private void cbiDcabangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbiDcabangActionPerformed
        // TODO add your handling code here:

//        
    }//GEN-LAST:event_cbiDcabangActionPerformed

    private void cbiDcabangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbiDcabangItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_cbiDcabangItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int jumlahjual = Integer.parseInt(jTextField3.getText());
        String hargajualc = jTextField2.getText();
        BigDecimal hargajual = new BigDecimal(hargajualc);

        int total = jumlahjual * hargajual.intValue();

        jTextField4.setText(String.valueOf(total));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbCabangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCabangItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cbCabangItemStateChanged

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

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
                new Fpenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbCabang;
    private javax.swing.JComboBox<String> cbiDcabang;
    private javax.swing.JComboBox<String> cbiDgudang;
    private javax.swing.JTextField hargaalat;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField kd_alat;
    private javax.swing.JPanel menus;
    private javax.swing.JTextField namaalat;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSTableMetro rSTableMetro1;
    private javax.swing.JLabel sss;
    private javax.swing.JTextField stockdi;
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
