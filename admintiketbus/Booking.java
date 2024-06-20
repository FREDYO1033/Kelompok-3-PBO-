/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admintiketbus;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Booking extends JFrame {
    private JTextField nameField, phoneField;
    private JComboBox<String> classBox, destinationBox, paymentMethodBox;
    private JTextField ticketsField, paymentField;
    private JButton bookButton, printButton, resetButton;
    private JTextArea seatSelectionArea, receiptArea;
    private JPanel seatSelectionPanel;
    private JLabel totalPriceLabel;

    private static final String[] DESTINATIONS = {"Bekasi - Semarang", "Bekasi - Malang", "Bekasi - Solo", "Bekasi - Surabaya"};
    private static final String[] CLASSES = {"VIP", "Ekonomi"};
    private static final String[] PAYMENT_METHODS = {"BRI", "Mandiri", "Tunai"};
    private static final int[] VIP_PRICES = {300000, 500000, 400000, 450000};
    private static final int[] ECONOMY_PRICES = {150000, 300000, 200000, 250000};

    private ArrayList<String> selectedSeats = new ArrayList<>();
    private int totalPrice = 0;

    public Booking() {
        setTitle("Program tiket pembelian bus");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(9, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        inputPanel.add(new JLabel("\t\t Nama:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("\t\t Nomor Telepon:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("\t\t Kelas:"));
        classBox = new JComboBox<>(CLASSES);
        inputPanel.add(classBox);

        inputPanel.add(new JLabel("\t\t Tujuan:"));
        destinationBox = new JComboBox<>(DESTINATIONS);
        inputPanel.add(destinationBox);

        inputPanel.add(new JLabel("\t\t Jumlah Tiket:"));
        ticketsField = new JTextField();
        inputPanel.add(ticketsField);

        inputPanel.add(new JLabel("\t\t Metode Pembayaran:"));
        paymentMethodBox = new JComboBox<>(PAYMENT_METHODS);
        inputPanel.add(paymentMethodBox);

        inputPanel.add(new JLabel("\t\t Total Harga:"));
        totalPriceLabel = new JLabel("0");
        inputPanel.add(totalPriceLabel);

        inputPanel.add(new JLabel("\t\t Jumlah Bayar:"));
        paymentField = new JTextField();
        inputPanel.add(paymentField);

        add(inputPanel, BorderLayout.NORTH);

        seatSelectionPanel = new JPanel();
        seatSelectionPanel.setLayout(new GridLayout(0, 5));
        seatSelectionArea = new JTextArea();
        seatSelectionArea.setEditable(false);

        add(seatSelectionPanel, BorderLayout.CENTER);
        add(new JScrollPane(seatSelectionArea), BorderLayout.EAST);

        receiptArea = new JTextArea(10, 30);
        receiptArea.setEditable(false);
        add(new JScrollPane(receiptArea), BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Add padding
        bookButton = new JButton("Pembayaran");
        
        bookButton.setBackground(Color.GRAY); // Set button color
        printButton = new JButton("Lihat Bukti Pembayaran");
        printButton.setBackground(Color.GRAY); // Set button color
        resetButton = new JButton("Pesan Lagi");
        resetButton.setBackground(Color.GRAY); // Set button color
        buttonPanel.add(bookButton);
        buttonPanel.add(printButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);

        classBox.addActionListener(new ClassSelectionHandler());
        bookButton.addActionListener(new BookingHandler());
        printButton.addActionListener(new PrintHandler());
        resetButton.addActionListener(new ResetHandler());

        updateSeatLayout();
    }

    private void updateSeatLayout() {
        seatSelectionPanel.removeAll();
        seatSelectionArea.setText("");

        String selectedClass = (String) classBox.getSelectedItem();
        int seatCount = selectedClass.equals("VIP") ? 10 : 36;
        for (int i = 1; i <= seatCount; i++) {
            String seatCode = selectedClass.substring(0, 1) + i;
            JButton seatButton = new JButton(seatCode);
            seatButton.setBackground(Color.LIGHT_GRAY); // Set button color
            seatButton.setEnabled(!selectedSeats.contains(seatCode));
            seatButton.addActionListener(new SeatSelectionHandler(seatCode));
            seatSelectionPanel.add(seatButton);
        }

        seatSelectionPanel.revalidate();
        seatSelectionPanel.repaint();
    }

    private void updateTotalPrice() {
        int ticketCount = Integer.parseInt(ticketsField.getText());
        String selectedClass = (String) classBox.getSelectedItem();
        int selectedDestination = destinationBox.getSelectedIndex();
        int basePrice = selectedClass.equals("VIP") ? VIP_PRICES[selectedDestination] : ECONOMY_PRICES[selectedDestination];

        totalPrice = basePrice * ticketCount;
        if (totalPrice > 1250000) {
            totalPrice *= 0.95;
        } else if (totalPrice > 1000000) {
            totalPrice *= 0.97;
        }

        String selectedPaymentMethod = (String) paymentMethodBox.getSelectedItem();
        if (selectedPaymentMethod.equals("BRI")) {
            totalPrice += 2500;
        } else if (selectedPaymentMethod.equals("Mandiri")) {
            totalPrice += 2000;
        }

        totalPriceLabel.setText(String.valueOf(totalPrice));
    }

    private class ClassSelectionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateSeatLayout();
        }
    }

    private class SeatSelectionHandler implements ActionListener {
        private String seatCode;

        public SeatSelectionHandler(String seatCode) {
            this.seatCode = seatCode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!selectedSeats.contains(seatCode)) {
                selectedSeats.add(seatCode);
                seatSelectionArea.append(seatCode + " ");
                updateTotalPrice();
            } else {
                JOptionPane.showMessageDialog(null, "Bangku sudah terisi");
            }
        }
    }

    private class BookingHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int paymentAmount = Integer.parseInt(paymentField.getText());
                if (paymentAmount >= totalPrice) {
                    JOptionPane.showMessageDialog(null, "Tiket berhasil dipesan!\nKembalian: " + (paymentAmount - totalPrice));
                } else {
                    JOptionPane.showMessageDialog(null, "Uang Anda Tidak Mencukupi");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Jumlah bayar harus menggunakan format angka!!!");
            }
        }
    }

    private class PrintHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder receipt = new StringBuilder();
            receipt.append("========== Bukti Pembayaran ==========\n");
            receipt.append("Nama              : ").append(nameField.getText()).append("\n");
            receipt.append("Nomor Telepon     : ").append(phoneField.getText()).append("\n");
            receipt.append("Kelas             : ").append(classBox.getSelectedItem()).append("\n");
            receipt.append("Tujuan            : ").append(destinationBox.getSelectedItem()).append("\n");
            receipt.append("Jumlah Tiket      : ").append(ticketsField.getText()).append("\n");
            receipt.append("Metode Pembayaran : ").append(paymentMethodBox.getSelectedItem()).append("\n");
            receipt.append("Tempat Duduk      : ").append(seatSelectionArea.getText()).append("\n");
            receipt.append("Total Harga       : ").append(totalPriceLabel.getText()).append("\n");
            receipt.append("=====================================\n");
            receiptArea.setText(receipt.toString());
        }
    }

    private class ResetHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedSeats.clear();
            nameField.setText("");
            phoneField.setText("");
            ticketsField.setText("");
            paymentField.setText("");
            totalPriceLabel.setText("0");
            receiptArea.setText("");
            updateSeatLayout();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Booking().setVisible(true);
        });
    }
}
