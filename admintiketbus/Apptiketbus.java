/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package apptiketbus;

/**
 *
 * @author uSeR
 */
public class Apptiketbus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Register rg = new Register();
        rg.setVisible(true);
        rg.pack();
        rg.setLocationRelativeTo(null);
        rg.setDefaultCloseOperation(Register.EXIT_ON_CLOSE);
    }
    
}
