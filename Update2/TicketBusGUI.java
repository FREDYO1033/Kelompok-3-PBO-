/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coba;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class TicketBusGUI extends JFrame implements ActionListener {
    private JLabel labelNama, labelNomor, labelTempatDuduk, labelTujuan, labelJumlahTiket, labelMetodePembayaran;
    private JTextField fieldNama, fieldNomor, fieldJumlahTiket;
    private JComboBox<String> comboTempatDuduk, comboTujuan, comboMetodePembayaran;
    private JButton buttonPesan, buttonSelesai;

    private HashMap<String, Integer> hargaTiketVIP, hargaTiketEkonomi;

    public TicketBusGUI() {
        setTitle("Pemesanan Tiket Bus");
        setSize(400, 300);
        setLayout(null);

        labelNama = new JLabel("Nama Pembeli:");
        labelNama.setBounds(20, 20, 100, 20);
        add(labelNama);

        fieldNama = new JTextField();
        fieldNama.setBounds(130, 20, 200, 20);
        add(fieldNama);

        labelNomor = new JLabel("Nomor Pembeli:");
        labelNomor.setBounds(20, 50, 100, 20);
        add(labelNomor);

        fieldNomor = new JTextField();
        fieldNomor.setBounds(130, 50, 200, 20);
        add(fieldNomor);

        labelTempatDuduk = new JLabel("Tempat Duduk:");
        labelTempatDuduk.setBounds(20, 80, 100, 20);
        add(labelTempatDuduk);

        String[] tempatDuduk = {"VIP", "Ekonomi"};
        comboTempatDuduk = new JComboBox<>(tempatDuduk);
        comboTempatDuduk.setBounds(130, 80, 200, 20);
        comboTempatDuduk.addActionListener(this);
        add(comboTempatDuduk);

        labelTujuan = new JLabel("Tujuan:");
        labelTujuan.setBounds(20, 110, 100, 20);
        add(labelTujuan);

        String[] tujuan = {"Bandung - Bogor", "Bandung - Jakarta", "Bandung - Cimahi", "Bandung - Cililitan"};
        comboTujuan = new JComboBox<>(tujuan);
        comboTujuan.setBounds(130, 110, 200, 20);
        add(comboTujuan);

        labelJumlahTiket = new JLabel("Jumlah Tiket:");
        labelJumlahTiket.setBounds(20, 140, 100, 20);
        add(labelJumlahTiket);

        fieldJumlahTiket = new JTextField();
        fieldJumlahTiket.setBounds(130, 140, 200, 20);
        add(fieldJumlahTiket);

        labelMetodePembayaran = new JLabel("Metode Pembayaran:");
        labelMetodePembayaran.setBounds(20, 170, 120, 20);
        add(labelMetodePembayaran);

        String[] metodePembayaran = {"Bank BRI", "Bank BNI", "Tunai"};
        comboMetodePembayaran = new JComboBox<>(metodePembayaran);
        comboMetodePembayaran.setBounds(150, 170, 180, 20);
        add(comboMetodePembayaran);

        buttonPesan = new JButton("Pesan");
        buttonPesan.setBounds(50, 220, 100, 30);
        buttonPesan.addActionListener(this);
        add(buttonPesan);

        buttonSelesai = new JButton("Selesai");
        buttonSelesai.setBounds(200, 220, 100, 30);
        buttonSelesai.addActionListener(this);
        add(buttonSelesai);

        hargaTiketVIP = new HashMap<>();
        hargaTiketVIP.put("Bandung - Bogor", 560000);
        hargaTiketVIP.put("Bandung - Jakarta", 750000);
        hargaTiketVIP.put("Bandung - Cimahi", 500000);
        hargaTiketVIP.put("Bandung - Cililitan", 700000);

        hargaTiketEkonomi = new HashMap<>();
        hargaTiketEkonomi.put("Bandung - Bogor", 200000);
        hargaTiketEkonomi.put("Bandung - Jakarta", 350000);
        hargaTiketEkonomi.put("Bandung - Cimahi", 250000);
        hargaTiketEkonomi.put("Bandung - Cililitan", 500000);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonPesan) {
            String nama = fieldNama.getText();
            String nomor = fieldNomor.getText();
            String tempatDuduk = (String) comboTempatDuduk.getSelectedItem();
            String tujuan = (String) comboTujuan.getSelectedItem();
            int jumlahTiket = Integer.parseInt(fieldJumlahTiket.getText());
            String metodePembayaran = (String) comboMetodePembayaran.getSelectedItem();

            int totalHarga = 0;
            if (tempatDuduk.equals("VIP")) {
                totalHarga = hargaTiketVIP.get(tujuan) * jumlahTiket;
            } else {
                totalHarga = hargaTiketEkonomi.get(tujuan) * jumlahTiket;
            }

            int pajak = 0;
            if (metodePembayaran.equals("Bank BRI")) {
                pajak = 2500;
            } else if (metodePembayaran.equals("Bank BNI")) {
                pajak = 2000;
            }

            double diskon = 0;
            if (totalHarga > 1000000) {
                diskon = 0.03;
            }
            if (totalHarga > 1250000) {
                diskon = 0.05;
            }

            double biayaPembayaran = totalHarga + pajak - (totalHarga * diskon);

            JOptionPane.showMessageDialog(this, "===== Bukti Pembayaran =====\n" +
                    "Nama Pembeli: " + nama + "\n" +
                    "Nomor Pembeli: " + nomor + "\n" +
                    "Tempat Duduk: " + tempatDuduk + "\n" +
                    "Tujuan: " + tujuan + "\n" +
                    "Jumlah Tiket: " + jumlahTiket + "\n" +
                    "Total Harga: Rp " + totalHarga + "\n" +
                    "Metode Pembayaran: " + metodePembayaran + "\n" +
                    "Biaya Pembayaran: Rp " + biayaPembayaran);
        } else if (e.getSource() == buttonSelesai) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        TicketBusGUI frame = new TicketBusGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }}