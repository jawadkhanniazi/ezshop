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
public class SubCatData {
    String id;
    String category_id;
    String description;
    String imageId;
    String imgURL = "https://firebasestorage.googleapis.com/v0/b/easyshop-fe4c7.appspot.com/o/Images%2Fezimg.png?alt=media&token=d05b165c-4c6c-48ea-97db-349109902621";
    String name;
    String shopid;
    String showinapp ="1";

    public SubCatData(DataSnapshot d){
        System.out.println("Into ds");
        id = d.child("id").getValue().toString();
        category_id = d.child("category_id").getValue().toString();
        description = d.child("description").getValue().toString();
        imageId = d.child("imageId").getValue().toString();
        imgURL = d.child("imgURL").getValue().toString();
        name = d.child("name").getValue().toString();
        shopid = d.child("shopid").getValue().toString();
    }
    
    public SubCatData(){
        
    }
    
    public SubCatData(String id, String category_id, String description, String imageId, String name, String shopid, String imgURL) {
        this.id = id;
        this.category_id = category_id;
        this.description = description;
        this.imageId = imageId;
        this.name = name;
        this.shopid = shopid;
        if(!imgURL.isEmpty())
            this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        return "SubCatData{" + "\nid=" + id + ", \ncategory_id=" + category_id + ", \ndescription=" + description + ", \nimageId=" + imageId + ", \nimgURL=" + imgURL + ", \nname=" + name + ", \nshopid=" + shopid + '}';
    }
    
    public void setShopid(String shopid){
        this.shopid = shopid;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    public void setcatId(String id){
        this.category_id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageId() {
        return imageId;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getName() {
        return name;
    }

    public String getShopid() {
        return shopid;
    }

    public String getShowinapp() {
        return showinapp;
    }
    
}
