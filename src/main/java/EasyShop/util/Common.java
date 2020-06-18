
package EasyShop.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Common {
    public static void initFirebase(){
        FileInputStream refFile = null;
        try {
            refFile = new FileInputStream("bin/Creds/credentials.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refFile))
                .setDatabaseUrl("https://easyshop-fe4c7.firebaseio.com/")
                    .setStorageBucket("easyshop-fe4c7.appspot.com")
                .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException ex) {
            System.out.println("initFirebase Error: Creds not Found");
        } catch (IOException ex) {
            System.out.println("initFirebase Error: IO Exception");
        } finally {
            try {
                refFile.close();
            } catch (IOException ex) {
                System.out.println("initFirebase Error: File Closing");
                Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
