/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coba;

/**
 *
 * @author USER
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class tiketbus {
    private JTextField nameField;
    private JTextField phoneField;
    private JComboBox<String> seatTypeComboBox;
    private JComboBox<String> destinationComboBox;
    private JTextField seatCodeField;
    private JTextField numTicketsField;
    private JComboBox<String> paymentMethodComboBox;
    private JTextField amountPaidField;
    private JTextArea receiptArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BusTicketApp::new);
    }

    public tiketbus() {
        createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame("Bus Ticket Purchase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel headerLabel = new JLabel("Bus Ticket Purchase");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(headerLabel);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panel.add(createLabelWithTextField("Name:", nameField = new JTextField(20)));
        panel.add(createLabelWithTextField("Phone Number:", phoneField = new JTextField(20)));

        panel.add(createLabelWithComboBox("Seat Type:", seatTypeComboBox = new JComboBox<>(new String[]{"VIP", "Economy"})));
        panel.add(createLabelWithComboBox("Destination:", destinationComboBox = new JComboBox<>(new String[]{"Bandung-Bogor", "Bandung-Jakarta", "Bandung-Cimahi", "Bandung-Cililitan"})));

        panel.add(createLabelWithTextField("Seat Code:", seatCodeField = new JTextField(20)));
        panel.add(createLabelWithTextField("Number of Tickets:", numTicketsField = new JTextField(20)));

        panel.add(createLabelWithComboBox("Payment Method:", paymentMethodComboBox = new JComboBox<>(new String[]{"BRI", "BNI", "Cash"})));

        panel.add(createLabelWithTextField("Amount Paid:", amountPaidField = new JTextField(20)));

        JButton calculateButton = new JButton("Calculate Total Price");
        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateButton.addActionListener(new CalculateButtonListener());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(calculateButton);

        receiptArea = new JTextArea(10, 30);
        receiptArea.setEditable(false);
        receiptArea.setBorder(BorderFactory.createTitledBorder("Receipt"));
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(scrollPane);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private JPanel createLabelWithTextField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(textField);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private JPanel createLabelWithComboBox(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(comboBox);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String seatType = (String) seatTypeComboBox.getSelectedItem();
            String destination = (String) destinationComboBox.getSelectedItem();
            String seatCode = seatCodeField.getText();
            int numTickets = Integer.parseInt(numTicketsField.getText());
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
            int amountPaid = Integer.parseInt(amountPaidField.getText());

            double price = calculatePrice(seatType, destination, numTickets);
            double discount = calculateDiscount(price);
            double paymentFee = calculatePaymentFee(paymentMethod);
            double totalPrice = price - discount + paymentFee;

            double change = amountPaid - totalPrice;

            String receipt = generateReceipt(name, phone, seatType, destination, seatCode, numTickets, paymentMethod, totalPrice, amountPaid, change);
            receiptArea.setText(receipt);
        }

        private double calculatePrice(String seatType, String destination, int numTickets) {
            double price = 0;
            if (seatType.equals("VIP")) {
                switch (destination) {
                    case "Bandung-Bogor":
                        price = 560000;
                        break;
                    case "Bandung-Jakarta":
                        price = 750000;
                        break;
                    case "Bandung-Cimahi":
                        price = 500000;
                        break;
                    case "Bandung-Cililitan":
                        price = 700000;
                        break;
                }
            } else if (seatType.equals("Economy")) {
                switch (destination) {
                    case "Bandung-Bogor":
                        price = 200000;
                        break;
                    case "Bandung-Jakarta":
                        price = 350000;
                        break;
                    case "Bandung-Cimahi":
                        price = 250000;
                        break;
                    case "Bandung-Cililitan":
                        price = 500000;
                        break;
                }
            }
            return price * numTickets;
        }

        private double calculateDiscount(double price) {
            if (price > 1250000) {
                return price * 0.05;
            } else if (price > 1000000) {
                return price * 0.03;
            }
            return 0;
        }

        private double calculatePaymentFee(String paymentMethod) {
            switch (paymentMethod) {
                case "BRI":
                    return 2500;
                case "BNI":
                    return 2000;
                case "Cash":
                    return 0;
            }
            return 0;
        }

        private String generateReceipt(String name, String phone, String seatType, String destination, String seatCode, int numTickets, String paymentMethod, double totalPrice, int amountPaid, double change) {
            return "==== Receipt ====\n" +
                    "Name: " + name + "\n" +
                    "Phone Number: " + phone + "\n" +
                    "Seat Type: " + seatType + "\n" +
                    "Destination: " + destination + "\n" +
                    "Seat Code: " + seatCode + "\n" +
                    "Number of Tickets: " + numTickets + "\n" +
                    "Payment Method: " + paymentMethod + "\n" +
                    "Total Price: " + totalPrice + "\n" +
                    "Amount Paid: " + amountPaid + "\n" +
                    "Change: " + change + "\n" +
                    "=================\n";
        }
    }
}
