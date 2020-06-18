/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop.util;

/**
 *
 * @author kaka
 */
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class PrintBill implements Printable {
    private int bill;
    private int amount;
    private LinkedList<cartData> det = new LinkedList<>();
    public PrintBill(LinkedList<cartData> l,int amount, int bill){
        det = l;
        this.amount=amount;
        this.bill = bill;
    }
      
    @Override
    public int print(Graphics graphics, PageFormat pageFormat,int pageIndex)throws PrinterException 
    {
        int r= det.size();
        Graphics2D g2d = (Graphics2D) graphics;                    
        double width = pageFormat.getImageableWidth();                               
        g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY());
        try{
            int y=5;
            int yShift = 6;

            g2d.setFont(new Font("Monospaced",Font.BOLD,10));
            String sName = "";
              try {
                  BufferedReader reader = new BufferedReader(new FileReader(
                            "bin/Creds/shopName.txt"));
                  sName = reader.readLine();
                  sName= sName.trim();
              } catch (FileNotFoundException ex) {
                  JOptionPane.showMessageDialog(null, "Error while reading shop name.\nYou can create one in settings.");
              } catch (IOException ex) {
                  JOptionPane.showMessageDialog(null, "Error while reading shop name.\nYou can create one in settings.");
                }
            g2d.drawString(sName,15,y);y+=yShift;
            g2d.drawString(" Item Name                  Price   ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=10;

            for(int s=0; s<r; s++){
                cartData d = det.get(s);
                String name = d.getName();
                int quant = d.getCount();
                int price = Integer.parseInt(d.getUnitPrice());
                g2d.drawString(name + "                             ",10,y);
                g2d.drawString("         "+quant+" * "+price,25,y);
                g2d.drawString(""+quant*price,150,y);y+=yShift;

            }

            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Total amount:               "+bill+"   ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Cash      :                 "+amount+"   ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Balance   :                 "+(amount-bill)+"   ",10,y);y+=yShift;

            g2d.drawString("*************************************",10,y);y+=yShift;
            g2d.drawString("Order Online via EZshop app",28,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
            g2d.drawString("       THANK YOU COME AGAIN            ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;      
          }
      catch(NumberFormatException e){
      e.printStackTrace();
      }
    if(pageIndex == 0)
        return PAGE_EXISTS;
    return NO_SUCH_PAGE;                
    }
    
    public static void main(String[] args) {
     LinkedList<cartData> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new cartData(""+i, "Prod"+i, 1, ""+i, 1));
        }
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new PrintBill(list,1000,1000),new PrintFormat(list.size()*0.179).getPageFormat(pj));
        try {
             pj.print();
        }
         catch (PrinterException ex) {
             ex.printStackTrace();
        }
    }
}
