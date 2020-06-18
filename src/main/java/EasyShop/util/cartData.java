/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop.util;

import javax.swing.ImageIcon;

/**
 *
 * @author kaka
 */
public class cartData{
        String unitPrice;
        String name;
        int count;
        int totalQuant;
        String barcode;
        public String getUnitPrice() {
            return unitPrice;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int val){
            this.count = val;
        }

        public String getBarcode() {
            return barcode;
        }

        
        public cartData(){
            
        }
        
        public cartData(String unitPrice, String name, int count, String barcode, int Totlaq) {
            this.unitPrice = unitPrice;
            this.name = name;
            this.count = count;
            this.barcode = barcode;
            this.totalQuant = Totlaq;
        }

        @Override
        public String toString() {
            return "cartData{" + "\n         unitPrice=" + unitPrice + "\n         name=" + name + "\n         count=" + count + "\n         barcode=" + barcode + "  }" ;
        }
        
        
    }