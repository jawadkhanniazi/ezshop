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
  class ShopData{
        private String address;
        private String bikerID;
        private String bikerSubId;
        private String name;
        private String ownerId;
        private String subscriptionId;
        
        public ShopData(DataSnapshot d){
            this.address = d.child("address").getValue().toString();
            this.bikerID = d.child("address").getValue().toString();
            this.bikerSubId = d.child("address").getValue().toString();
            this.name = d.child("address").getValue().toString();
            this.ownerId = d.child("address").getValue().toString();
            this.subscriptionId = d.child("address").getValue().toString();
        }

        public String getAddress() {
            return address;
        }

        public String getBikerID() {
            return bikerID;
        }

        public String getBikerSubId() {
            return bikerSubId;
        }

        public String getName() {
            return name;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public String getSubscriptionId() {
            return subscriptionId;
        }
        
    }