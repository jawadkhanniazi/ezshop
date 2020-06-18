/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop;

import EasyShop.front_end.login;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

public class Main {

    /**
     * @param args the command line arguments
     */
    private static PrintStream out ;
    public static void main(String[] args) {
        
        //AHIB
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd- HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            out= new PrintStream("bin/logs/log"+dtf.format(now).toString()+".txt"); 
            System.setOut(out);
            System.out.println("******************************************************************************************************");
            System.out.println("New Log  Dated "+dtf.format(now));
            System.out.println("******************************************************************************************************");
            System.out.println("Internet Connection ");
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println("Error during init process Error Details are as following ");
            System.out.println("Error occured in "+ex.getClass());
            System.out.println("Error Cause "+ex.getCause());
            System.out.println("Error Details "+ ex.toString());               
        }
        try{
            new login().setVisible(true); 
        }catch(Exception e){
            System.out.println("Un handled Exception -->");
            System.out.println("Error Cause "+e.getCause());
            System.out.println("Error Details "+e.toString());
            e.printStackTrace();
        }   
    }
    
}
