/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop.util;

import com.google.firebase.database.DataSnapshot;

/**
 *
 * @author kaka
 */
public class ProductData{
        private String name;
        private String barcode;
        private String cat_id;
        private String id;
        private String companyName;
        private String sub_category_id;
        private String shop_id;
        private String size;
        private int quantity;
        private String price;
        private String salesprice;
        private String soldItems = "0";
        private String showinapp = "1";
        private String imgURL = "https://firebasestorage.googleapis.com/v0/b/easyshop-fe4c7.appspot.com/o/Images%2Fezimg.png?alt=media&token=d05b165c-4c6c-48ea-97db-349109902621";
        private String imgid = "";
        private String description = "";
        
        

    public ProductData(String name, String barcode, String cat_id, String id, String companyName,
            String shop_id, int quantity, String price, String salesPrice, String sub_catagory_id,
            String imgid, String url, String si, String size, String description,String showinapp) {
        soldItems = si;
        this.name = name;
        this.salesprice = salesPrice;
        this.barcode = barcode;
        this.cat_id = cat_id;
        this.id = id;
        this.companyName = companyName;
        this.shop_id = shop_id;
        this.quantity = quantity;
        this.price = price;
        this.sub_category_id = sub_catagory_id;
        this.imgid = imgid;
        this.size = size;
        if(!url.isEmpty())
            imgURL = url;
        this.description = description;
        this.showinapp = showinapp;
    } 
        
        
              
    public ProductData(){}

    public String getImgURL() {
        return imgURL;
    }

    public String getImgid() {
        return imgid;
    }   
    
    public ProductData(DataSnapshot d){
            this.barcode = d.child("barcode").getValue().toString();
            this.cat_id = d.child("cat_id").getValue().toString();
            this.companyName = d.child("companyName").getValue().toString();
            if(d.hasChild("description"))
                this.description = d.child("description").getValue().toString();
            this.id = d.child("id").getValue().toString();
            this.imgURL = d.child("imgURL").getValue().toString();
            if(d.hasChild("imgid"))
                this.imgid = d.child("imgid").getValue().toString();
            this.name = d.child("name").getValue().toString();
            this.price = d.child("price").getValue().toString();
            this.quantity = Integer.parseInt(d.child("quantity").getValue().toString());
            this.salesprice = d.child("salesprice").getValue().toString();
            this.shop_id = d.child("shop_id").getValue().toString();
            this.size = d.child("size").getValue().toString();
            if(d.hasChild("soldItems"))
                this.soldItems = d.child("soldItems").getValue().toString();
            this.sub_category_id = d.child("sub_category_id").getValue().toString();
        }

    public String getSoldItems(){
        return this.soldItems;
    }
    
    public void setSoldItems(String si){
        this.soldItems = si;
    }

    public String getSalesprice(){
        return this.salesprice;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
    @Override
    public String toString() {
        return "ProductData{" + "\nname=" + name + ", \ncat_id=" + cat_id + ", \nid=" + id + ", \nsub_category_id=" + sub_category_id + ", \nshop_id=" + shop_id + '}';
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sid){
        this.sub_category_id = sid;
    }
    
    public String getShowinapp() {
        return showinapp;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cid){
        this.cat_id = cid;
    }
    
    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String sid){
        this.shop_id = sid;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
        
        
    }