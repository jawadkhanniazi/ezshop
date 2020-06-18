/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop.front_end;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 *
 * @author kaka
 */
public class sjdkf12 {
    public DatabaseReference ref;
    public  sjdkf12(){
       FileInputStream refFile = null;
        try {
            refFile = new FileInputStream("bin/Icons/test");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refFile))
                .setDatabaseUrl("https://test-667dc.firebaseio.com/")
                .build();
            FirebaseApp sec =FirebaseApp.initializeApp(options,"backup");
            ref = FirebaseDatabase.getInstance(sec).getReference();
        } catch (FileNotFoundException ex) {
            System.out.println("initFirebase Error: File not Found");
            System.exit(0); 
        } catch (IOException ex) {
            System.out.println("initFirebase Error: IO Exception");

        } 
    }
}
