/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop.util;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

/**
 *
 * @author kaka
 */
public class PrintFormat{
    private double bodyHeight = 1;
    public PrintFormat(double bodyHeight){
        this.bodyHeight = bodyHeight;
    }
     public PageFormat getPageFormat(PrinterJob pj){
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();    

        double headerHeight = 5.0;                  
        double footerHeight = 5.0;        
        double width = cm_to_pp(14); 
        double height = cm_to_pp(headerHeight+bodyHeight+footerHeight); 
        paper.setSize(width, height);
        paper.setImageableArea(0,10,width,height - cm_to_pp(1));  

        pf.setOrientation(PageFormat.PORTRAIT);  
        pf.setPaper(paper);    

        return pf;
    }
    protected static double cm_to_pp(double cm)
    {            
        return toPPI(cm * 0.393600787);            
    }
 
    protected static double toPPI(double inch)
    {            
	        return inch * 72d;            
    }

}
