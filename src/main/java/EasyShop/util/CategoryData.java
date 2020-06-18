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
public class CategoryData {
    private String id;
    private String imgURL = "https://firebasestorage.googleapis.com/v0/b/easyshop-fe4c7.appspot.com/o/Images%2Fezimg.png?alt=media&token=d05b165c-4c6c-48ea-97db-349109902621";
    private String imgid;
    private String name;
    private String shop_id;
    private String showinapp="1";
    private String description ;

    public CategoryData(String id, String imgid, String name, String shop_id, String description, String url) {
        this.id = id;
        this.imgid = imgid;
        this.name = name;
        this.shop_id = shop_id;
        this.description = description;
        if(!url.isEmpty())
            imgURL = url;
    }
    
    public CategoryData(){}
    
    public CategoryData(DataSnapshot d){
        System.out.println("Into cat const");
        id = d.child("id").getValue().toString();
        imgURL = d.child("imgURL").getValue().toString();
        imgid = d.child("imgid").getValue().toString();
        name = d.child("name").getValue().toString();
        shop_id = d.child("shop_id").getValue().toString();
        description = d.child("description").getValue().toString();
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public String getId() {
        return id;
    }
    
    public void setID(String id){
        this.id = id;
    }
    
    public String getImgURL() {
        return imgURL;
    }

    public String getImgid() {
        return imgid;
    }

    public String getName() {
        return name;
    }

    public String getShop_id() {
        return shop_id;
    }
    
    public void setShop_id(String s){
        shop_id = s;
    }

    public String getShowinapp() {
        return showinapp;
    }

    @Override
    public String toString() {
        return "CatagoryData{" + "\nid=" + id + ", \nimgURL=" + imgURL + ", \nimgid=" + imgid + ", \nname=" + name + ", \nshop_id=" + shop_id + '}';
    }
    
    
}
