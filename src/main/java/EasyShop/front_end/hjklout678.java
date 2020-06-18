package EasyShop.front_end;

import EasyShop.util.CartItem;
import EasyShop.util.cartData;
import EasyShop.util.CategoryData;
import static EasyShop.util.Common.initFirebase;
import EasyShop.util.OrderData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import static EasyShop.util.Common.initFirebase;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import EasyShop.util.ProductData;
import EasyShop.util.SubCatData;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sbix.jnotify.NPosition; 
import com.sbix.jnotify.NoticeType;
import com.sbix.jnotify.NoticeWindow;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class hjklout678 extends javax.swing.JFrame {

    private ek e;
    private kl kk;
    private int currentPage = 0;
    private int hjt5676 =0;
    private int mode;
    private int skip =0;
    private static Connection con;
    private class Lookup{
        LinkedList<String> id = new LinkedList<>();
        LinkedList<Integer> index = new LinkedList<>();

        @Override
        public String toString() {
            return "Lookup{" + "\nid=" + id + ",\nindex=" + index + '}';
        }
        
    }
    
    private LinkedList<ProductData> gio90 = new LinkedList<ProductData>();    
    private LinkedList<OrderData> g3 = new LinkedList<>();
    private Lookup lookup = new Lookup();
    
    public LinkedList<OrderData> getOnlineOrders(){
        return g3;
    }
    
    private void jklop23yju(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order/"+hjkouyth.getText()+"/Products");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot order, String string) {
                    try{
                        if(order.child("cancelcheck").getValue().toString().equalsIgnoreCase("0")){
                            String pname = order.child("productname").getValue().toString();
                            Integer orderSize = new Integer(order.child("sizeoforder").getValue().toString());
                            String oid = order.child("orderId").getValue().toString();
                            String Tprice = order.child("price").getValue().toString();
                            String Tquant = order.child("quantity").getValue().toString();
                            String sid = order.child("shopid").getValue().toString();
                            String pid = order.child("productid").getValue().toString();
                            OrderData od = new OrderData(oid, pname,pid, Tprice, Tquant,sid, ""+orderSize, order.getKey());
                            od.orderIDs.add(order.getKey());
                            od.setOrderP(order.child("price").getValue().toString());
                            if(skip ==0){
                                if( orderSize>1 ){
                                    skip = orderSize -1;
                                    od.hasMore=true;
                                }
                                g3.add(od);
                            }else{
                                skip --;
                                OrderData o = g3.getLast();
                                int cp = Integer.parseInt(o.getOrderP());
                                int np = Integer.parseInt(od.getPrice());
                                o.setOrderP(""+(cp+np));
                                o.orderIDs.add(order.getKey());
                                o.nextOrders.add(od);
                                g3.removeLast();
                                g3.add(o);
                            }
                            hjk65890(g3.getLast(), g3.size());
                            lookup.id.add(order.getKey());
                            lookup.index.add(g3.size()-1);
                            new NoticeWindow(NoticeType.SUCCESS_NOTIFICATION,"New Order Placed",NoticeWindow.LONG_DELAY,NPosition.BOTTOM_LEFT);           
                        }
                    }catch(Exception e){
                        System.out.println("Exceptoin ");
                        e.printStackTrace();
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot order, String string) {
                if(order.child("cancelcheck").getValue().toString().equalsIgnoreCase("1")){
                    try{
                        OrderData od = new OrderData();
                        int index = lookup.index.get(lookup.id.indexOf(order.getKey()));
                        od = g3.get(index);
                        g3.remove(index);
                        lookup.index.remove(lookup.index.get(lookup.id.indexOf(order.getKey())));
                        lookup.id.remove(lookup.id.indexOf(order.getKey()));
                        reDraw();
                    }catch(Exception e ){
                        System.out.println("ex");
                        e.printStackTrace();
                    }
                }
                else if(order.child("ordercomplete").getValue().toString().equalsIgnoreCase("0")){
                        String pname = order.child("productname").getValue().toString();
                        Integer orderSize = new Integer(order.child("sizeoforder").getValue().toString());
                        String oid = order.child("orderId").getValue().toString();
                        String Tprice = order.child("price").getValue().toString();
                        String Tquant = order.child("quantity").getValue().toString();
                        String sid = order.child("shopid").getValue().toString();
                        String pid = order.child("shopid").getValue().toString();
                        OrderData od = new OrderData(oid, pname, pid, Tprice, Tquant,sid, ""+orderSize );
                        od.orderIDs.add(order.getKey());
                        int index = lookup.id.indexOf(order.getKey());
                        if(index >=0){
                            int allorder_index = lookup.index.get(index);
                            OrderData o = g3.get(allorder_index);
                            index = o.orderIDs.indexOf(order.getKey());
                            if(index ==0){
                                o.orderId = od.orderId;
                                o.price = od.price;
                                o.shopid = od.shopid;
                                o.productname = od.productname;
                                o.quantity = od.quantity;
                            }else{
                                o.nextOrders.add(index, od);
                            }
                            g3.remove(allorder_index);
                            g3.add(allorder_index,o);
                        }else{
                            g3.add(od);
                            new NoticeWindow(NoticeType.SUCCESS_NOTIFICATION,"New Order Placed",10000,NPosition.BOTTOM_LEFT);
                            reDraw();
                            lookup.id.add(order.getKey());
                            lookup.index.add(g3.size());
                        }
                    }
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                new NoticeWindow(NoticeType.SUCCESS_NOTIFICATION,"New Order Placed",NoticeWindow.LONG_DELAY,NPosition.BOTTOM_LEFT);           
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        });
    }
    
    private void hjk65890(OrderData od,int num){       
        switch(num){
            case 1:
                hjk65899(this.o1_id , this.o1_p , od, this.ytyg);
                break;
            case 2:
                hjk65899(this.o1_id1, this.o1_p1, od, this.gtytfhgyg);
                break;
            case 3:
                hjk65899(this.o1_id2, this.o1_p2, od, this.yutuytfjhgy);
                break;
            case 4:
                hjk65899(this.o1_id3, this.o1_p3, od, this.sdfhyiuwae348628sdh);
                break;
            case 5:
                hjk65899(this.aA, this.o1_p4, od, this.o_u5);
                break;
            case 6:
                hjk65899(this.o1_id5, this.o1_p5, od, this.aB);
                break;
            case 7:
                hjk65899(this.o1_id6, this.o1_p6, od, this.Au);
                break;
            case 8:
                hjk65899(this.o1_id17, this.o1_p17, od, this.Al);
                break;
            case 9:
                hjk65899(this.o1_id18, this.o1_p18, od, this.Ak);
                break;
            case 10:
                hjk65899(this.o1_id19, this.o1_p19, od, this.AKLJ);
                break;
        }
        
    }
     
    
    
    
    LinkedList<String> uto890 = new LinkedList<>();
    LinkedList<String> uto899 = new LinkedList<>();
    LinkedList<String> nto899 = new LinkedList<>();
    LinkedList<String> nto890 = new LinkedList<>();
    private String imageFileName = "";
    private String nto8 ="";
    private String sub_nto8 ="";
    private String nto898u78file ="";
    private String nto898u78URL ="";
    private boolean subcat_found = false;
    private boolean catsaveInProgress = false;
    private boolean subcatsaveInProgress = false;
    private String nto8_path ="";
    private String sub_nto8_path ="";
    private String nto898u78file_path = "";
    
    private void yt65Cat(){
        String name = Amklop.getText();
        String desc = xdfj12.getText();
        if(name.isEmpty() || sub_nto8.isEmpty()){
            JOptionPane.showMessageDialog(this, "Fill all fields");
            subcatsaveInProgress = false;
        }else{
            String url = "";
            try {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                url = jju685AS4drfg("SubCatImages/"+ref.push().getKey(), sub_nto8_path);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error while uploading images");
                subcatsaveInProgress = false;
                JOptionPane.showMessageDialog(this, "Error while uploading images");
                return;
            }
            SubCatData sc = new SubCatData("","", desc, sub_nto8, name, "",url);
                yt65Cat(sc);
        }
    }
    
    private void yt65ckly76(){
        subcatsaveInProgress =  false; 
        Amklop.setText(""); 
        xdfj12.setText("Description");
        sub_nto8 = "";
        sdfjho90.setIcon(null);
    }
    
    private void yt65ckly76pyt6(){
        if(Adrtfgh.getText().isEmpty() || hjy65449.getText().isEmpty() || nto898u78file.isEmpty() ){
            JOptionPane.showMessageDialog(this, "ProductName & Barcode & image are mandatory");
            save_in_progress = false;
        }else{
            DatabaseReference  ref = FirebaseDatabase.getInstance().getReference();
            ref.child("ProductsItems")
                    .orderByChild("barcode")
                    .equalTo(Adrtfgh.getText())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        boolean found = false;
                        for (DataSnapshot d : ds.getChildren()) {
                            if(d.child("shop_id").getValue().toString().equalsIgnoreCase(hjkouyth.getText())){
                                found = true;
                                break;
                            }
                        }
                        if(!found){
                            if(manualSave){
                                try{
                                    nto898u78URL = jju685AS4drfg("ProductsImages/"+ref.push().getKey(), nto898u78file_path);
                                }catch(Exception ex){
                                    e.kl("File not found Error while uploading");
                                    JOptionPane.showMessageDialog(null, "Error while uploading images");
                                    save_in_progress = false;
                                    return;
                                }
                                Integer avaiableUnits = Integer.parseInt(asdew.getText());
                                existingpd = new ProductData(hjy65449.getText(), Adrtfgh.getText(), hjyt.getSelectedItem().toString(),
                                        "", Wasdf.getText(),hjkouyth.getText(),avaiableUnits,  WWa.getText(), 
                                        WWEQSD.getText(),hyt57fg.getSelectedItem().toString(),nto898u78file,
                                        nto898u78URL,"0", Wad.getText(),sdfh129sd.getText(),jCheckBox1.isSelected()?"1":"0");
                            
                            }
                            subcatSaveId = uto899.get(hyt57fg.getSelectedIndex()-1);
                            Integer avaiableUnits = Integer.parseInt(asdew.getText());
                            existingpd = new ProductData(hjy65449.getText(), Adrtfgh.getText(), existingpd.getCat_id(),
                                    "", Wasdf.getText(),hjkouyth.getText(),avaiableUnits,  WWa.getText(), 
                                    WWEQSD.getText(),existingpd.getSub_category_id(),nto898u78file,nto898u78URL,
                                    "0", Wad.getText(),sdfh129sd.getText(),jCheckBox1.isSelected()?"1":"0");
                            vcfrt45676(ref);
                        }else{
                            JOptionPane.showMessageDialog(null, "Product Already Exists.");
                            save_in_progress = false;
                        }
//                        hjAQ34();
                    }

                    @Override
                    public void onCancelled(DatabaseError de) {
                }
            }); 

        }
    }
    
    private String catID = "";
    
    private void vcfrt45676(DatabaseReference ref){
        ref.child("CategoryItems").orderByChild("name")
            .equalTo(hjyt.getSelectedItem().toString())
            .addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot ds) {
                catID = ref.child("CategoryItems").push().getKey();
                boolean found =  false;
                DataSnapshot data= null;
                for(DataSnapshot d : ds.getChildren()){
                    data =d;
                   if(d.child("shop_id").getValue().toString().equalsIgnoreCase(hjkouyth.getText())){ 
                        found = true;
                        catID = d.child("id").getValue().toString(); 
                        break;
                     }
                 }

                if(found){//verify subcat exist or not
                    existingpd.setCat_id(catID);
                   vscfrt45676(ref,catID);                                        
                }else{//Add a bransd new CAT-SUBCAT-PROD
                    CategoryData cat = new CategoryData(data);
                    String sid = ref.child("SubCategoryItems").push().getKey();
                    String pID = ref.child("ProductsItems").push().getKey();
                    cat.setID(catID); 
                    cat.setShop_id(hjkouyth.getText()); 
                    ref.child("SubCategoryItems/"+subcatSaveId)
                       .addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot ds) {
                           SubCatData s = new SubCatData(ds);
                           s.setShopid(hjkouyth.getText());
                           s.setId(sid);
                           s.setcatId(catID);  
                           existingpd.setCat_id(catID);
                           existingpd.setSub_category_id(sid);
                           existingpd.setId(pID);
                           ref.child("CategoryItems/"+catID).setValueAsync(cat);
                           ref.child("SubCategoryItems/"+sid).setValueAsync(s);
                           ref.child("ProductsItems/"+pID).setValueAsync(existingpd);
                           hjAQ34();
                           JOptionPane.showMessageDialog(null, "Product saved Successfuly");
                           nto898u78file ="";
                           save_in_progress = false;
                       }

                       @Override
                       public void onCancelled(DatabaseError de) {
                           JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
                       }
                   }); 
                }

            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
                }

        });
    }
    
    private void vscfrt45676(DatabaseReference ref, String catId){
        ref.child("SubCategoryItems").orderByChild("name")
            .equalTo(hyt57fg.getSelectedItem().toString())
            .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                String sid = ref.child("SubCategoryItems").push().getKey();
                String pID = ref.child("ProductsItems").push().getKey();
                boolean found =  false;
                DataSnapshot data= null;
                for(DataSnapshot d : ds.getChildren()){
                    data =d;
                   if(d.child("shopid").getValue().toString().equalsIgnoreCase(hjkouyth.getText())){ 
                        found = true;
                        sid = d.child("id").getValue().toString(); 
                        break;
                     }
                 }
                if(found){
                    existingpd.setSub_category_id(sid);
                    existingpd.setId(pID);
                    ref.child("ProductsItems/"+pID).setValueAsync(existingpd);
                }else{
                    SubCatData s = new SubCatData(data);
                    s.setId(sid);
                    s.setcatId(catID);
                    s.setShopid(hjkouyth.getText());
                    existingpd.setSub_category_id(sid);
                    existingpd.setId(pID);
                    ref.child("SubCategoryItems/"+sid).setValueAsync(s);
                    ref.child("ProductsItems/"+pID).setValueAsync(existingpd);
                    
                }
                hjAQ34();
                nto898u78file = "";
                save_in_progress = false;
                JOptionPane.showMessageDialog(null, "Product saved Successfuly");
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }); 
    }
    
    private String jju685AS4drfg(String name,String path) throws Exception{
            Bucket b = StorageClient.getInstance().bucket();
            Storage storage = StorageOptions.newBuilder().build().getService();
            BlobId blobId = BlobId.of(b.getName(), name);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpg").build();
            Blob blob = storage.create(blobInfo, new FileInputStream(path));
            return "https://firebasestorage.googleapis.com/v0"+blob.getMediaLink().split("/v1")[1];
    }
    
    private String updateimgURL = "";
    
    private void suki6754(){
        JHYULOP.setText("");
        klopas234.setText("");
        Nmklobvg.setText("");
        JKlopwr.setText("");
        Jkiop90875sad.setText("");
        JKLOPas1234.setIcon(null);
        aJKIy786g.setText("");
        Ajklou12.setText("");
        Nkiopgty67i.setText("");
        jklOPhsklop.setText("");
        Ajklo.setText("");
    }
    
    private void sdklo9082(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("ProductsItems")
                            .orderByChild(sdjlop.getSelectedItem().toString())
                            .equalTo(Ajklou12.getText())
                            .addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        boolean found = false;
                        for(DataSnapshot d : ds.getChildren()){
                            if(hjkouyth.getText().equalsIgnoreCase(d.child("shop_id").getValue().toString())){
                                found = true;
                                String id = d.getKey();
                                String name = Jkiop90875sad.getText();
                                String barcode = JKlopwr.getText();
                                String cname = Nmklobvg.getText();
                                String price = Nkiopgty67i.getText();
                                String sprice = aJKIy786g.getText();
                                Integer quantity = Integer.parseInt(JHYULOP.getText());
                                String url = d.child("imgURL").getValue().toString();
                                if(!updateimgURL.isEmpty()){
                                    try {
                                        url = jju685AS4drfg("ProductsImages/"+ref.push().getKey()+".jpg", updateimgURL);
                                    } catch (Exception ex) {
                                        e.kl("Error while updating image");
                                    }
                                }
                                updateimgURL ="";
                                DatabaseReference refi = FirebaseDatabase.getInstance().getReference();
                                ProductData data = new ProductData(name,barcode,d.child("cat_id").getValue().toString(),
                                                                    id,cname,hjkouyth.getText(),quantity,price,sprice,
                                                                    d.child("sub_category_id").getValue().toString(),"",
                                                                    url,d.child("soldItems").getValue().toString(),
                                                                    jklOPhsklop.getText(),sdfh129sd.getText(),"1");
                                refi.child("ProductsItems/"+id).setValueAsync(data);
                                break;
                            }
                        }if(found)
                            JOptionPane.showMessageDialog(null, "Updated Successfully");
                        else
                            JOptionPane.showMessageDialog(null, "No Product found for update");
                        suki6754();
                    }

                    @Override
                    public void onCancelled(DatabaseError de) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                
    }    
    
    private CartItem ca65;
    
    int p = 0;
    
    private class pl{
        String name;
        String barcode;
        String shop_id;
        int prof;

        public pl(String name, String barcode, String shop_id, int prof) {
            this.name = name;
            this.barcode = barcode;
            this.shop_id = shop_id;
            this.prof = prof;
        }
        
        public pl(DataSnapshot ds){
            name = ds.child("name").getValue().toString().toLowerCase();
            barcode = ds.child("barcode").getValue().toString().toLowerCase();
            shop_id = ds.child("shop_id").getValue().toString().toLowerCase();
            int sp = Integer.parseInt(ds.child("salesprice").getValue().toString());
            int price = Integer.parseInt(ds.child("price").getValue().toString());
            int sli = 0;
            if(ds.hasChild("soldItems")){
                sli = Integer.parseInt(ds.child("soldItems").getValue().toString());
            }
            prof = (sp-price)*sli;
        }
    }
    
    LinkedList<pl> k9 = new LinkedList<>();
    
    LinkedList<String> kh245dggh = new LinkedList<>();
        
    private void u90(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
        DefaultTableModel model = (DefaultTableModel) Al09.getModel();
        ref.orderByChild("shop_id").equalTo(hjkouyth.getText()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {                
                String name = ds.child("name").getValue().toString().toLowerCase();
                String bcd = ds.child("barcode").getValue().toString().toLowerCase();
                int price = Integer.parseInt(ds.child("price").getValue().toString().toLowerCase());
                int sp = Integer.parseInt(ds.child("salesprice").getValue().toString());
                int sli = 0;
                if(ds.hasChild("soldItems")){
                    sli = Integer.parseInt(ds.child("soldItems").getValue().toString());
                }
                k9.add(new pl(ds));
                kh245dggh.add(name+"-"+bcd);
                p = p+sli*(sp-price);
                Alm89.setText(""+p);
                Object[] row = {name, bcd,sli*(sp-price)};
                model.addRow(row);
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                String name = ds.child("name").getValue().toString().toLowerCase();
                String bcd = ds.child("barcode").getValue().toString().toLowerCase();
                int sp = Integer.parseInt(ds.child("salesprice").getValue().toString());
                int sli = 0;
                if(ds.hasChild("soldItems")){
                    sli = Integer.parseInt(ds.child("soldItems").getValue().toString());
                }
                int index = kh245dggh.indexOf(name+"-"+bcd);
                if(index >=0){
                    p = 0;
                    k9.remove(index);
                    k9.add(index,new pl(ds));
                    DefaultTableModel model = (DefaultTableModel) Al09.getModel();
                    model.setRowCount(0);
                    for (pl plist : k9) {
                        Object[] row = {plist.name, plist.barcode, plist.prof};
                        model.addRow(row);
                        p = p+plist.prof;
                        Alm89.setText(""+p);
                    }
                }
               
                
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        });
    }
    
    private void filter(String query){
        DefaultTableModel model = (DefaultTableModel) Al09.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        Al09.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
        int sum = 0;
        for (int i = 0; i < Al09.getRowCount(); i++) {
            sum += Integer.parseInt(Al09.getValueAt(i, 2).toString().trim());
        }
        Alm89.setText(""+sum);
    }
    
    private void u9(boolean b){
        add_salesman_name.setVisible(b);
        jdsfkh123.setVisible(b);
        dd4.setVisible(b);
        add_salesman_cont.setVisible(b);
        ds23.setVisible(b);
        add_salesman_comp.setVisible(b);
        aslkol.setVisible(b);
        jButton32.setVisible(b);
    }
    
    sjdkf12 bref = new sjdkf12(); 
    
    private CardLayout cardlayout ;
    
    private boolean wide = true;
    
    boolean initAdd = true;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        klop7876 = new javax.swing.JPanel();
        hj760 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lerkwe12 = new javax.swing.JLabel();
        dskjf23965fh = new javax.swing.JLabel();
        jfhrkjwe23 = new javax.swing.JLabel();
        yuiwe213sae = new javax.swing.JLabel();
        jk90 = new javax.swing.JPanel();
        rtrexxgfd88 = new javax.swing.JLabel();
        ytyfytrf = new javax.swing.JLabel();
        hjgfdrdtfgyg = new javax.swing.JLabel();
        hgfdesSS = new javax.swing.JLabel();
        ftfrtrs = new javax.swing.JLabel();
        ytyrtrdfy = new javax.swing.JLabel();
        ohgft = new javax.swing.JLabel();
        jhjgfgdgghggy = new javax.swing.JLabel();
        sawdwaQEW = new javax.swing.JLabel();
        klo09812j = new javax.swing.JPanel();
        jklop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        hgh7tyyg8 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        uuhhhu767tygu = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        kjhjhghjjh979 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        yuyftuyg7657g = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        rtee4w56 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        tyr56eytr6rdfdy = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        tree4wet6e = new javax.swing.JLabel();
        jButton26 = new javax.swing.JButton();
        re4w35e = new javax.swing.JComboBox<>();
        DEL_KEY = new javax.swing.JLabel();
        del_shopid = new javax.swing.JLabel();
        kloup = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        search_order_icon = new javax.swing.JLabel();
        Au = new javax.swing.JPanel();
        o1_id6 = new javax.swing.JLabel();
        o1_p6 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        aB = new javax.swing.JPanel();
        o1_id5 = new javax.swing.JLabel();
        o1_p5 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        o_u5 = new javax.swing.JPanel();
        aA = new javax.swing.JLabel();
        o1_p4 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        sdfhyiuwae348628sdh = new javax.swing.JPanel();
        o1_id3 = new javax.swing.JLabel();
        o1_p3 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        yutuytfjhgy = new javax.swing.JPanel();
        o1_id2 = new javax.swing.JLabel();
        o1_p2 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        gtytfhgyg = new javax.swing.JPanel();
        o1_id1 = new javax.swing.JLabel();
        o1_p1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        ytyg = new javax.swing.JPanel();
        o1_id = new javax.swing.JLabel();
        o1_p = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Al = new javax.swing.JPanel();
        o1_id17 = new javax.swing.JLabel();
        o1_p17 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        Ak = new javax.swing.JPanel();
        o1_id18 = new javax.swing.JLabel();
        o1_p18 = new javax.swing.JLabel();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        AKLJ = new javax.swing.JPanel();
        o1_id19 = new javax.swing.JLabel();
        o1_p19 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        dlkop = new javax.swing.JPanel();
        KLO87 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        AK90 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        hjkouyth = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        skldf821 = new javax.swing.JTextField();
        jButton30 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        klopdfger67 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        Aa = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        TotalCount = new javax.swing.JLabel();
        jdskfh132ds = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        hjyt = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        hyt57fg = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        WWEQSD = new javax.swing.JTextField();
        asdew = new javax.swing.JTextField();
        Wad = new javax.swing.JTextField();
        Adrtfgh = new javax.swing.JTextField();
        dfsh34hsd = new javax.swing.JButton();
        ksdfh345_s = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        WWa = new javax.swing.JTextField();
        Wasdf = new javax.swing.JTextField();
        hjy65449 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        sfdjkh12940 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton31 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        sdfh129sd = new javax.swing.JTextField();
        vbd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        sdfhk89076 = new javax.swing.JLabel();
        dsfljslKIOP = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        dslkfjA123 = new javax.swing.JTextField();
        dfhskj123456 = new javax.swing.JTextField();
        djskfLop = new javax.swing.JButton();
        asdlJklo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Amklop = new javax.swing.JTextField();
        xdfj12 = new javax.swing.JTextField();
        dsfklop = new javax.swing.JButton();
        fdshQo = new javax.swing.JButton();
        sdfjho90 = new javax.swing.JLabel();
        weiuyr78GHJ = new javax.swing.JPanel();
        Ajklou12 = new javax.swing.JTextField();
        jklOPhsklop = new javax.swing.JTextField();
        JKLOPas1234 = new javax.swing.JLabel();
        klopas234 = new javax.swing.JTextField();
        Nmklobvg = new javax.swing.JTextField();
        Nkiopgty67i = new javax.swing.JTextField();
        aJKIy786g = new javax.swing.JTextField();
        JHYULOP = new javax.swing.JTextField();
        AJKLOP = new javax.swing.JButton();
        AGHY = new javax.swing.JButton();
        XAa = new javax.swing.JButton();
        JKlopwr = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        sdjlop = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        Jkiop90875sad = new javax.swing.JTextField();
        Ajklo = new javax.swing.JLabel();
        Aklop = new javax.swing.JPanel();
        sdfjkhl12jh4 = new javax.swing.JPanel();
        jklop0 = new javax.swing.JLabel();
        add_salesman_name = new javax.swing.JLabel();
        dd4 = new javax.swing.JTextField();
        add_salesman_cont = new javax.swing.JLabel();
        ds23 = new javax.swing.JTextField();
        add_salesman_comp = new javax.swing.JLabel();
        aslkol = new javax.swing.JTextField();
        jdsfkh123 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        hjllop = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        hklop = new javax.swing.JTable();
        alkop12 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        sd9 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Al09 = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        Alm89 = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        QAs = new javax.swing.JPanel();
        header1 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        prod_sort_icon = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        search_order_icon1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        o_u8 = new javax.swing.JPanel();
        o1_id7 = new javax.swing.JLabel();
        o1_on7 = new javax.swing.JLabel();
        o1_oq7 = new javax.swing.JLabel();
        o1_p7 = new javax.swing.JLabel();
        inv_pp7 = new javax.swing.JLabel();
        o_u9 = new javax.swing.JPanel();
        o1_id8 = new javax.swing.JLabel();
        o1_on8 = new javax.swing.JLabel();
        o1_oq8 = new javax.swing.JLabel();
        o1_p8 = new javax.swing.JLabel();
        inv_pp6 = new javax.swing.JLabel();
        o_u10 = new javax.swing.JPanel();
        o1_id9 = new javax.swing.JLabel();
        o1_on9 = new javax.swing.JLabel();
        o1_oq9 = new javax.swing.JLabel();
        o1_p9 = new javax.swing.JLabel();
        inv_pp5 = new javax.swing.JLabel();
        o_u11 = new javax.swing.JPanel();
        o1_id10 = new javax.swing.JLabel();
        o1_on10 = new javax.swing.JLabel();
        o1_oq10 = new javax.swing.JLabel();
        o1_p10 = new javax.swing.JLabel();
        inv_pp4 = new javax.swing.JLabel();
        o_u12 = new javax.swing.JPanel();
        o1_id11 = new javax.swing.JLabel();
        o1_on11 = new javax.swing.JLabel();
        o1_oq11 = new javax.swing.JLabel();
        o1_p11 = new javax.swing.JLabel();
        inv_pp3 = new javax.swing.JLabel();
        o_u13 = new javax.swing.JPanel();
        o1_id12 = new javax.swing.JLabel();
        o1_on12 = new javax.swing.JLabel();
        o1_oq12 = new javax.swing.JLabel();
        o1_p12 = new javax.swing.JLabel();
        inv_pp2 = new javax.swing.JLabel();
        o_u14 = new javax.swing.JPanel();
        o1_id13 = new javax.swing.JLabel();
        o1_on13 = new javax.swing.JLabel();
        o1_oq13 = new javax.swing.JLabel();
        o1_p13 = new javax.swing.JLabel();
        inv_pp1 = new javax.swing.JLabel();
        o_u15 = new javax.swing.JPanel();
        o1_id14 = new javax.swing.JLabel();
        o1_on14 = new javax.swing.JLabel();
        o1_oq14 = new javax.swing.JLabel();
        o1_p14 = new javax.swing.JLabel();
        inv_pp8 = new javax.swing.JLabel();
        o_u16 = new javax.swing.JPanel();
        o1_id15 = new javax.swing.JLabel();
        o1_on15 = new javax.swing.JLabel();
        o1_oq15 = new javax.swing.JLabel();
        o1_p15 = new javax.swing.JLabel();
        inv_pp9 = new javax.swing.JLabel();
        o_u17 = new javax.swing.JPanel();
        o1_id16 = new javax.swing.JLabel();
        o1_on16 = new javax.swing.JLabel();
        o1_oq16 = new javax.swing.JLabel();
        o1_p16 = new javax.swing.JLabel();
        inv_pp10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Easy Shop Managment System");
        setMinimumSize(new java.awt.Dimension(1190, 695));

        klop7876.setBackground(new java.awt.Color(255, 255, 255));
        klop7876.setBorder(javax.swing.BorderFactory.createLineBorder(null));

        hj760.setBackground(new java.awt.Color(102, 102, 102));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("EZ SHOP");

        lerkwe12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lerkwe12MouseClicked(evt);
            }
        });

        dskjf23965fh.setText("jLabel43");
        dskjf23965fh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dskjf23965fhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout hj760Layout = new javax.swing.GroupLayout(hj760);
        hj760.setLayout(hj760Layout);
        hj760Layout.setHorizontalGroup(
            hj760Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hj760Layout.createSequentialGroup()
                .addGroup(hj760Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dskjf23965fh, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(yuiwe213sae)
                .addGap(18, 18, 18)
                .addComponent(jfhrkjwe23)
                .addGap(18, 18, 18)
                .addComponent(lerkwe12)
                .addGap(33, 33, 33))
        );
        hj760Layout.setVerticalGroup(
            hj760Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hj760Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dskjf23965fh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hj760Layout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addGroup(hj760Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(yuiwe213sae)
                    .addGroup(hj760Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lerkwe12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jfhrkjwe23, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                .addGap(33, 33, 33))
        );

        jk90.setBackground(new java.awt.Color(102, 102, 102));

        rtrexxgfd88.setBackground(new java.awt.Color(255, 255, 255));
        rtrexxgfd88.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rtrexxgfd88.setForeground(new java.awt.Color(255, 255, 255));
        rtrexxgfd88.setText(" DELETE ITEMS");
        rtrexxgfd88.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rtrexxgfd88MouseClicked(evt);
            }
        });

        ytyfytrf.setBackground(new java.awt.Color(255, 255, 255));
        ytyfytrf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ytyfytrf.setForeground(new java.awt.Color(255, 255, 255));
        ytyfytrf.setText(" SALES MANS");
        ytyfytrf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ytyfytrfMouseClicked(evt);
            }
        });

        hjgfdrdtfgyg.setBackground(new java.awt.Color(255, 255, 255));
        hjgfdrdtfgyg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        hjgfdrdtfgyg.setForeground(new java.awt.Color(255, 255, 255));
        hjgfdrdtfgyg.setText(" ADD ITEM");
        hjgfdrdtfgyg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hjgfdrdtfgygMouseClicked(evt);
            }
        });

        hgfdesSS.setBackground(new java.awt.Color(255, 255, 255));
        hgfdesSS.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        hgfdesSS.setForeground(new java.awt.Color(255, 255, 255));
        hgfdesSS.setText(" UPDATE ITEMS");
        hgfdesSS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hgfdesSSMouseClicked(evt);
            }
        });

        ftfrtrs.setBackground(new java.awt.Color(255, 255, 255));
        ftfrtrs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ftfrtrs.setForeground(new java.awt.Color(255, 255, 255));
        ftfrtrs.setText(" CART");
        ftfrtrs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ftfrtrsMouseClicked(evt);
            }
        });

        ytyrtrdfy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ytyrtrdfy.setForeground(new java.awt.Color(255, 255, 255));
        ytyrtrdfy.setText(" PROFT/LOSS");
        ytyrtrdfy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ytyrtrdfyMouseClicked(evt);
            }
        });

        ohgft.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ohgft.setForeground(new java.awt.Color(255, 255, 255));
        ohgft.setText(" Online Orders");
        ohgft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ohgftMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ohgftMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ohgftMouseExited(evt);
            }
        });

        jhjgfgdgghggy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jhjgfgdgghggy.setForeground(new java.awt.Color(255, 255, 255));
        jhjgfgdgghggy.setText("  Inventory");
        jhjgfgdgghggy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jhjgfgdgghggyMouseClicked(evt);
            }
        });

        sawdwaQEW.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sawdwaQEW.setForeground(new java.awt.Color(255, 255, 255));
        sawdwaQEW.setText(" Loan ");
        sawdwaQEW.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sawdwaQEWMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jk90Layout = new javax.swing.GroupLayout(jk90);
        jk90.setLayout(jk90Layout);
        jk90Layout.setHorizontalGroup(
            jk90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hgfdesSS, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(rtrexxgfd88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ftfrtrs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ytyfytrf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ytyrtrdfy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ohgft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(hjgfdrdtfgyg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jhjgfgdgghggy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sawdwaQEW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jk90Layout.setVerticalGroup(
            jk90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jk90Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(ohgft, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jhjgfgdgghggy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hjgfdrdtfgyg, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hgfdesSS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rtrexxgfd88, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ftfrtrs, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ytyfytrf, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ytyrtrdfy, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sawdwaQEW, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        klo09812j.setBackground(new java.awt.Color(255, 255, 255));
        klo09812j.setLayout(new java.awt.CardLayout());

        jklop.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Search");

        hgh7tyyg8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hgh7tyyg8.setForeground(new java.awt.Color(153, 153, 153));
        hgh7tyyg8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        hgh7tyyg8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hgh7tyyg8MouseClicked(evt);
            }
        });
        hgh7tyyg8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hgh7tyyg8KeyPressed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Name");

        uuhhhu767tygu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        uuhhhu767tygu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setText("Price");

        kjhjhghjjh979.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        kjhjhghjjh979.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setText("Available Quant.");

        yuyftuyg7657g.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        yuyftuyg7657g.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel42.setText("Category ");

        rtee4w56.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rtee4w56.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel44.setText("Barcode");

        tyr56eytr6rdfdy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tyr56eytr6rdfdy.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel46.setText("Available At");

        tree4wet6e.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tree4wet6e.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        jButton26.setText("Delete Product");
        jButton26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton26MouseClicked(evt);
            }
        });

        re4w35e.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "By Barcode", "By Name" }));
        re4w35e.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re4w35eActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jklopLayout = new javax.swing.GroupLayout(jklop);
        jklop.setLayout(jklopLayout);
        jklopLayout.setHorizontalGroup(
            jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jklopLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jklopLayout.createSequentialGroup()
                            .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(28, 28, 28)
                            .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(uuhhhu767tygu, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(yuyftuyg7657g, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(kjhjhghjjh979, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(35, 35, 35)
                            .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(35, 35, 35)
                            .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(rtee4w56, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tyr56eytr6rdfdy, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tree4wet6e, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jklopLayout.createSequentialGroup()
                            .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(DEL_KEY, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                .addComponent(del_shopid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jklopLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(re4w35e, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(hgh7tyyg8, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(470, Short.MAX_VALUE))
        );
        jklopLayout.setVerticalGroup(
            jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jklopLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1)
                    .addComponent(hgh7tyyg8)
                    .addComponent(re4w35e, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addGap(54, 54, 54)
                .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uuhhhu767tygu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rtee4w56, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yuyftuyg7657g, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tyr56eytr6rdfdy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kjhjhghjjh979, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tree4wet6e, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DEL_KEY, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(del_shopid, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        klo09812j.add(jklop, "searchNDel");

        kloup.setBackground(new java.awt.Color(255, 255, 255));
        kloup.setFocusable(false);
        kloup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kloupMouseClicked(evt);
            }
        });
        kloup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kloupKeyPressed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel19.setText("Order ID");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel24.setText("Price");

        search_order_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search_order_iconMouseClicked(evt);
            }
        });
        search_order_icon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_order_iconKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(search_order_icon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(search_order_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(334, 334, 334)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        o1_id6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id6.setText("jLabel19");

        o1_p6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p6.setText("jLabel25");

        jButton10.setBackground(new java.awt.Color(204, 204, 204));
        jButton10.setText("Mark Done");
        jButton10.setFocusPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton20.setText("Open");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AuLayout = new javax.swing.GroupLayout(Au);
        Au.setLayout(AuLayout);
        AuLayout.setHorizontalGroup(
            AuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AuLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id6, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton10)
                .addGap(29, 29, 29))
        );
        AuLayout.setVerticalGroup(
            AuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10)
                    .addComponent(jButton20))
                .addContainerGap())
        );

        o1_id5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id5.setText("jLabel19");

        o1_p5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p5.setText("jLabel25");

        jButton9.setBackground(new java.awt.Color(204, 204, 204));
        jButton9.setText("Mark Done");
        jButton9.setFocusPainted(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton19.setText("Open");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout aBLayout = new javax.swing.GroupLayout(aB);
        aB.setLayout(aBLayout);
        aBLayout.setHorizontalGroup(
            aBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aBLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton9)
                .addGap(29, 29, 29))
        );
        aBLayout.setVerticalGroup(
            aBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(aBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9)
                    .addComponent(jButton19))
                .addContainerGap())
        );

        aA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        aA.setText("jLabel19");

        o1_p4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p4.setText("jLabel25");

        jButton8.setBackground(new java.awt.Color(204, 204, 204));
        jButton8.setText("Mark Done");
        jButton8.setFocusPainted(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton18.setText("Open");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout o_u5Layout = new javax.swing.GroupLayout(o_u5);
        o_u5.setLayout(o_u5Layout);
        o_u5Layout.setHorizontalGroup(
            o_u5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(aA, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton8)
                .addGap(29, 29, 29))
        );
        o_u5Layout.setVerticalGroup(
            o_u5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8)
                    .addComponent(jButton18))
                .addContainerGap())
        );

        o1_id3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id3.setText("jLabel19");

        o1_p3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p3.setText("jLabel25");

        jButton7.setBackground(new java.awt.Color(204, 204, 204));
        jButton7.setText("Mark Done");
        jButton7.setFocusPainted(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton17.setText("Open");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sdfhyiuwae348628sdhLayout = new javax.swing.GroupLayout(sdfhyiuwae348628sdh);
        sdfhyiuwae348628sdh.setLayout(sdfhyiuwae348628sdhLayout);
        sdfhyiuwae348628sdhLayout.setHorizontalGroup(
            sdfhyiuwae348628sdhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sdfhyiuwae348628sdhLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton7)
                .addGap(29, 29, 29))
        );
        sdfhyiuwae348628sdhLayout.setVerticalGroup(
            sdfhyiuwae348628sdhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sdfhyiuwae348628sdhLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sdfhyiuwae348628sdhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7)
                    .addComponent(jButton17))
                .addContainerGap())
        );

        o1_id2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id2.setText("jLabel19");

        o1_p2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p2.setText("jLabel25");

        jButton6.setBackground(new java.awt.Color(204, 204, 204));
        jButton6.setText("Mark Done");
        jButton6.setFocusPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton13.setText("Open");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout yutuytfjhgyLayout = new javax.swing.GroupLayout(yutuytfjhgy);
        yutuytfjhgy.setLayout(yutuytfjhgyLayout);
        yutuytfjhgyLayout.setHorizontalGroup(
            yutuytfjhgyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(yutuytfjhgyLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton6)
                .addGap(29, 29, 29))
        );
        yutuytfjhgyLayout.setVerticalGroup(
            yutuytfjhgyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(yutuytfjhgyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(yutuytfjhgyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, yutuytfjhgyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(o1_id2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addComponent(jButton13))
                    .addComponent(o1_p2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        o1_id1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id1.setText("jLabel19");

        o1_p1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p1.setText("jLabel25");

        jButton5.setBackground(new java.awt.Color(204, 204, 204));
        jButton5.setText("Mark Done");
        jButton5.setFocusPainted(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton12.setText("Open");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gtytfhgygLayout = new javax.swing.GroupLayout(gtytfhgyg);
        gtytfhgyg.setLayout(gtytfhgygLayout);
        gtytfhgygLayout.setHorizontalGroup(
            gtytfhgygLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gtytfhgygLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton5)
                .addGap(29, 29, 29))
        );
        gtytfhgygLayout.setVerticalGroup(
            gtytfhgygLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gtytfhgygLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gtytfhgygLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5)
                    .addComponent(jButton12))
                .addContainerGap())
        );

        o1_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id.setText("jLabel19");

        o1_p.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p.setText("jLabel25");

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Mark Done");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Open");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ytygLayout = new javax.swing.GroupLayout(ytyg);
        ytyg.setLayout(ytygLayout);
        ytygLayout.setHorizontalGroup(
            ytygLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ytygLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138)
                .addComponent(jButton1)
                .addGap(10, 10, 10))
        );
        ytygLayout.setVerticalGroup(
            ytygLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ytygLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ytygLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        o1_id17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id17.setText("jLabel19");

        o1_p17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p17.setText("jLabel25");

        jButton21.setBackground(new java.awt.Color(204, 204, 204));
        jButton21.setText("Mark Done");
        jButton21.setFocusPainted(false);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setText("Open");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AlLayout = new javax.swing.GroupLayout(Al);
        Al.setLayout(AlLayout);
        AlLayout.setHorizontalGroup(
            AlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id17, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p17, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton21)
                .addGap(29, 29, 29))
        );
        AlLayout.setVerticalGroup(
            AlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton21)
                    .addComponent(jButton22))
                .addContainerGap())
        );

        o1_id18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id18.setText("jLabel19");

        o1_p18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p18.setText("jLabel25");

        jButton23.setBackground(new java.awt.Color(204, 204, 204));
        jButton23.setText("Mark Done");
        jButton23.setFocusPainted(false);
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setText("Open");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AkLayout = new javax.swing.GroupLayout(Ak);
        Ak.setLayout(AkLayout);
        AkLayout.setHorizontalGroup(
            AkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AkLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id18, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p18, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton23)
                .addGap(29, 29, 29))
        );
        AkLayout.setVerticalGroup(
            AkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton23)
                    .addComponent(jButton24))
                .addContainerGap())
        );

        o1_id19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id19.setText("jLabel19");

        o1_p19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p19.setText("jLabel25");

        jButton25.setBackground(new java.awt.Color(204, 204, 204));
        jButton25.setText("Mark Done");
        jButton25.setFocusPainted(false);
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton27.setText("Open");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AKLJLayout = new javax.swing.GroupLayout(AKLJ);
        AKLJ.setLayout(AKLJLayout);
        AKLJLayout.setHorizontalGroup(
            AKLJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AKLJLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id19, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(o1_p19, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AKLJLayout.setVerticalGroup(
            AKLJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AKLJLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AKLJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton25)
                    .addComponent(jButton27))
                .addContainerGap())
        );

        javax.swing.GroupLayout kloupLayout = new javax.swing.GroupLayout(kloup);
        kloup.setLayout(kloupLayout);
        kloupLayout.setHorizontalGroup(
            kloupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kloupLayout.createSequentialGroup()
                .addContainerGap(158, Short.MAX_VALUE)
                .addGroup(kloupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AKLJ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gtytfhgyg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yutuytfjhgy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sdfhyiuwae348628sdh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Au, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Al, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Ak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ytyg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(171, Short.MAX_VALUE))
        );
        kloupLayout.setVerticalGroup(
            kloupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kloupLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ytyg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gtytfhgyg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yutuytfjhgy, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sdfhyiuwae348628sdh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aB, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Au, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Al, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ak, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(AKLJ, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        klo09812j.add(kloup, "OnlineOrder");

        dlkop.setBackground(new java.awt.Color(255, 255, 255));

        KLO87.setText("Update Products");
        KLO87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KLO87ActionPerformed(evt);
            }
        });

        jButton16.setText("Update images");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel39.setText("Local DataBase");

        jButton15.setText("Reset Password");

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel41.setText("User Managment");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Shop ID");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Enter Shop ID");

        AK90.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AK90.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));

        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        hjkouyth.setText("jLabel16");

        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton29.setText("Clear Firebase");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Shop Name");

        skldf821.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));

        jButton30.setText("Save");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Current Id");

        javax.swing.GroupLayout dlkopLayout = new javax.swing.GroupLayout(dlkop);
        dlkop.setLayout(dlkopLayout);
        dlkopLayout.setHorizontalGroup(
            dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlkopLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dlkopLayout.createSequentialGroup()
                        .addComponent(KLO87)
                        .addGap(38, 38, 38)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dlkopLayout.createSequentialGroup()
                        .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hjkouyth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AK90, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                            .addComponent(skldf821))
                        .addGap(37, 37, 37)
                        .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(jButton30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(570, Short.MAX_VALUE))
        );
        dlkopLayout.setVerticalGroup(
            dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlkopLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(skldf821, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addComponent(jButton30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AK90, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hjkouyth, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dlkopLayout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                        .addGap(5, 5, 5)))
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dlkopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(KLO87)
                    .addComponent(jButton16)
                    .addComponent(jButton4)
                    .addComponent(jButton29))
                .addGap(18, 18, 18)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton15)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        klo09812j.add(dlkop, "services");

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        klopdfger67.setBackground(new java.awt.Color(255, 255, 255));
        klopdfger67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                klopdfger67MouseClicked(evt);
            }
        });

        jButton14.setText("Check Out");
        jButton14.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }
        });

        Aa.setText("jTextField1");
        Aa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AaKeyPressed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("Total :");

        TotalCount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TotalCount.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        TotalCount.setText("0");

        javax.swing.GroupLayout klopdfger67Layout = new javax.swing.GroupLayout(klopdfger67);
        klopdfger67.setLayout(klopdfger67Layout);
        klopdfger67Layout.setHorizontalGroup(
            klopdfger67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, klopdfger67Layout.createSequentialGroup()
                .addContainerGap(985, Short.MAX_VALUE)
                .addGroup(klopdfger67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(klopdfger67Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(4, 4, 4)
                        .addComponent(TotalCount, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(klopdfger67Layout.createSequentialGroup()
                        .addComponent(Aa, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32))
        );
        klopdfger67Layout.setVerticalGroup(
            klopdfger67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(klopdfger67Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(klopdfger67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Aa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(klopdfger67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(klopdfger67Layout.createSequentialGroup()
                        .addComponent(TotalCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addContainerGap(2973, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(klopdfger67);

        klo09812j.add(jScrollPane3, "cart");

        jdskfh132ds.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Add New Product");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Select Category");

        hjyt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        hjyt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hjytItemStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Select SubCategory");

        hyt57fg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setText("Unit Price");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setText("Sales Price");

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setText("Available Units");

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setText("Size");

        WWEQSD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        WWEQSD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        WWEQSD.setNextFocusableComponent(asdew);

        asdew.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        asdew.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        asdew.setNextFocusableComponent(dfsh34hsd);

        Wad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Wad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        Wad.setNextFocusableComponent(Wasdf);

        Adrtfgh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Adrtfgh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        Adrtfgh.setNextFocusableComponent(hjy65449);
        Adrtfgh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdrtfghKeyPressed(evt);
            }
        });

        dfsh34hsd.setText("Load Image");
        dfsh34hsd.setNextFocusableComponent(sdfh129sd);
        dfsh34hsd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dfsh34hsdMouseClicked(evt);
            }
        });
        dfsh34hsd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dfsh34hsdKeyPressed(evt);
            }
        });

        ksdfh345_s.setText("Save Product");
        ksdfh345_s.setNextFocusableComponent(Adrtfgh);
        ksdfh345_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ksdfh345_sActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Product Name");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Company Name");

        WWa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        WWa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        WWa.setNextFocusableComponent(WWEQSD);

        Wasdf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Wasdf.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        Wasdf.setNextFocusableComponent(WWa);

        hjy65449.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hjy65449.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        hjy65449.setNextFocusableComponent(Wad);

        jButton11.setText("New Cat");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        sfdjkh12940.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Barcode");

        jButton31.setText("Show Image");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Show in app");

        sdfh129sd.setText("jTextField1");
        sdfh129sd.setBorder(        javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout jdskfh132dsLayout = new javax.swing.GroupLayout(jdskfh132ds);
        jdskfh132ds.setLayout(jdskfh132dsLayout);
        jdskfh132dsLayout.setHorizontalGroup(
            jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdskfh132dsLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jdskfh132dsLayout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jdskfh132dsLayout.createSequentialGroup()
                            .addComponent(jLabel52)
                            .addGap(60, 60, 60)
                            .addComponent(asdew))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jdskfh132dsLayout.createSequentialGroup()
                            .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel50)
                                .addComponent(jLabel11)
                                .addComponent(jLabel9)
                                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(47, 47, 47)
                            .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Wad, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(WWEQSD)
                                .addComponent(WWa)
                                .addComponent(Wasdf)
                                .addComponent(hjy65449, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jdskfh132dsLayout.createSequentialGroup()
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jdskfh132dsLayout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdskfh132dsLayout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)))
                            .addGroup(jdskfh132dsLayout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)))
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Adrtfgh, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hjyt, 0, 240, Short.MAX_VALUE)
                            .addComponent(hyt57fg, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdskfh132dsLayout.createSequentialGroup()
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdskfh132dsLayout.createSequentialGroup()
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                            .addComponent(dfsh34hsd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                            .addComponent(sfdjkh12940, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ksdfh345_s, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(sdfh129sd))))
                .addContainerGap(329, Short.MAX_VALUE))
        );
        jdskfh132dsLayout.setVerticalGroup(
            jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdskfh132dsLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Adrtfgh, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox1)))
                .addGap(18, 18, 18)
                .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hjyt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hyt57fg, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdskfh132dsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hjy65449, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel53)
                            .addComponent(Wad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Wasdf, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(WWa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(WWEQSD, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(asdew, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52)))
                    .addGroup(jdskfh132dsLayout.createSequentialGroup()
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sfdjkh12940, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(sdfh129sd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jdskfh132dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dfsh34hsd)
                            .addComponent(ksdfh345_s))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton31)))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        klo09812j.add(jdskfh132ds, "addItemMain");

        vbd.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("  SubCategory Information");

        sdfhk89076.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));

        dsfljslKIOP.setText("Load Image");
        dsfljslKIOP.setNextFocusableComponent(djskfLop);
        dsfljslKIOP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dsfljslKIOPMouseClicked(evt);
            }
        });
        dsfljslKIOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dsfljslKIOPActionPerformed(evt);
            }
        });
        dsfljslKIOP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dsfljslKIOPKeyPressed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Name");

        dslkfjA123.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dslkfjA123.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        dslkfjA123.setNextFocusableComponent(dfhskj123456);

        dfhskj123456.setText("Description");
        dfhskj123456.setBorder(javax.swing.BorderFactory.createLineBorder(null));
        dfhskj123456.setNextFocusableComponent(dsfljslKIOP);
        dfhskj123456.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dfhskj123456FocusGained(evt);
            }
        });
        dfhskj123456.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dfhskj123456MouseClicked(evt);
            }
        });

        djskfLop.setText("Save Category");
        djskfLop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                djskfLopMouseClicked(evt);
            }
        });
        djskfLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                djskfLopActionPerformed(evt);
            }
        });
        djskfLop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                djskfLopKeyPressed(evt);
            }
        });

        asdlJklo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("  Category Information");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Category Name");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("SubCat Name");

        Amklop.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Amklop.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        Amklop.setNextFocusableComponent(xdfj12);

        xdfj12.setText("Description");
        xdfj12.setBorder(javax.swing.BorderFactory.createLineBorder(null));
        xdfj12.setNextFocusableComponent(dsfklop);
        xdfj12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                xdfj12FocusGained(evt);
            }
        });
        xdfj12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                xdfj12MouseClicked(evt);
            }
        });

        dsfklop.setText("Load Image");
        dsfklop.setNextFocusableComponent(fdshQo);
        dsfklop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dsfklopMouseClicked(evt);
            }
        });
        dsfklop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dsfklopActionPerformed(evt);
            }
        });
        dsfklop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dsfklopKeyPressed(evt);
            }
        });

        fdshQo.setText("Save SubCat");
        fdshQo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fdshQoMouseClicked(evt);
            }
        });
        fdshQo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fdshQoActionPerformed(evt);
            }
        });
        fdshQo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fdshQoKeyPressed(evt);
            }
        });

        sdfjho90.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout vbdLayout = new javax.swing.GroupLayout(vbd);
        vbd.setLayout(vbdLayout);
        vbdLayout.setHorizontalGroup(
            vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vbdLayout.createSequentialGroup()
                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vbdLayout.createSequentialGroup()
                        .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(vbdLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dfhskj123456)
                                    .addGroup(vbdLayout.createSequentialGroup()
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(dslkfjA123, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(vbdLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(djskfLop, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dsfljslKIOP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sdfhk89076, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(vbdLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100)
                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(vbdLayout.createSequentialGroup()
                        .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(vbdLayout.createSequentialGroup()
                                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(asdlJklo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Amklop, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(xdfj12, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(vbdLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(fdshQo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sdfjho90, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dsfklop, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 236, Short.MAX_VALUE))
        );
        vbdLayout.setVerticalGroup(
            vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vbdLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vbdLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(vbdLayout.createSequentialGroup()
                                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dslkfjA123, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(dfhskj123456, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sdfhk89076, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dsfljslKIOP)
                            .addComponent(djskfLop)))
                    .addGroup(vbdLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(vbdLayout.createSequentialGroup()
                                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(asdlJklo)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Amklop))
                                .addGap(115, 115, 115))
                            .addGroup(vbdLayout.createSequentialGroup()
                                .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(vbdLayout.createSequentialGroup()
                                        .addComponent(xdfj12, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))
                                    .addComponent(sdfjho90, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(vbdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dsfklop)
                            .addComponent(fdshQo))))
                .addContainerGap(193, Short.MAX_VALUE))
        );

        klo09812j.add(vbd, "AddItem");

        weiuyr78GHJ.setBackground(new java.awt.Color(255, 255, 255));

        Ajklou12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Ajklou12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        Ajklou12.setNextFocusableComponent(jklOPhsklop);
        Ajklou12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Ajklou12KeyPressed(evt);
            }
        });

        jklOPhsklop.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jklOPhsklop.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        jklOPhsklop.setNextFocusableComponent(klopas234);
        jklOPhsklop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jklOPhsklopKeyPressed(evt);
            }
        });

        JKLOPas1234.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));

        klopas234.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        klopas234.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        klopas234.setNextFocusableComponent(Nmklobvg);

        Nmklobvg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Nmklobvg.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        Nmklobvg.setNextFocusableComponent(Nkiopgty67i);

        Nkiopgty67i.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Nkiopgty67i.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        Nkiopgty67i.setNextFocusableComponent(aJKIy786g);

        aJKIy786g.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        aJKIy786g.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        aJKIy786g.setNextFocusableComponent(JHYULOP);

        JHYULOP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JHYULOP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        JHYULOP.setNextFocusableComponent(AJKLOP);

        AJKLOP.setText("Load Image");
        AJKLOP.setNextFocusableComponent(JKlopwr);
        AJKLOP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AJKLOPMouseClicked(evt);
            }
        });
        AJKLOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AJKLOPActionPerformed(evt);
            }
        });

        AGHY.setText("Scan BarCode");
        AGHY.setNextFocusableComponent(XAa);
        AGHY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AGHYMouseClicked(evt);
            }
        });
        AGHY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGHYActionPerformed(evt);
            }
        });

        XAa.setText("Update Product");
        XAa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XAaActionPerformed(evt);
            }
        });

        JKlopwr.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        JKlopwr.setNextFocusableComponent(XAa);
        JKlopwr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKlopwrKeyPressed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setText("Product Size");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setText("Available Units");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setText("Unit Price");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("Company Name");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel34.setText("Category Name");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel35.setText("Update Products ");

        jLabel18.setForeground(new java.awt.Color(255, 51, 0));
        jLabel18.setText("* mandatory");

        jLabel21.setForeground(new java.awt.Color(255, 51, 0));
        jLabel21.setText("*");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel47.setText("Sales Price");

        sdjlop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "barcode", "name" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Name");

        Jkiop90875sad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Jkiop90875sad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));

        Ajklo.setText("jLabel16");

        javax.swing.GroupLayout weiuyr78GHJLayout = new javax.swing.GroupLayout(weiuyr78GHJ);
        weiuyr78GHJ.setLayout(weiuyr78GHJLayout);
        weiuyr78GHJLayout.setHorizontalGroup(
            weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                                .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                                        .addComponent(sdjlop, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel21)
                                        .addGap(49, 49, 49)
                                        .addComponent(Ajklou12, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addGap(51, 51, 51)
                                        .addComponent(klopas234))
                                    .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addGap(97, 97, 97)
                                        .addComponent(Nkiopgty67i))
                                    .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Nmklobvg, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, weiuyr78GHJLayout.createSequentialGroup()
                                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel31)
                                            .addComponent(jLabel47))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(JHYULOP, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                            .addComponent(aJKIy786g)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, weiuyr78GHJLayout.createSequentialGroup()
                                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel30)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jklOPhsklop, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                            .addComponent(Jkiop90875sad)))
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(91, 91, 91))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, weiuyr78GHJLayout.createSequentialGroup()
                                .addComponent(Ajklo, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                                .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(JKLOPas1234, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AJKLOP, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(AGHY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JKlopwr, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(XAa, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(408, Short.MAX_VALUE))
        );
        weiuyr78GHJLayout.setVerticalGroup(
            weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Ajklou12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(sdjlop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(Jkiop90875sad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jklOPhsklop, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                                .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel34)
                                    .addComponent(klopas234, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(Nmklobvg, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel32))
                            .addComponent(Nkiopgty67i, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(aJKIy786g, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(JHYULOP, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18))
                    .addGroup(weiuyr78GHJLayout.createSequentialGroup()
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JKLOPas1234, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JKlopwr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(weiuyr78GHJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AJKLOP)
                            .addComponent(AGHY))
                        .addGap(51, 51, 51)
                        .addComponent(XAa)))
                .addGap(18, 18, 18)
                .addComponent(Ajklo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        klo09812j.add(weiuyr78GHJ, "update");

        Aklop.setBackground(new java.awt.Color(255, 255, 255));

        jklop0.setText("Expand");
        jklop0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jklop0MouseClicked(evt);
            }
        });

        add_salesman_name.setText("Name");

        dd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dd4ActionPerformed(evt);
            }
        });

        add_salesman_cont.setText("Contact");

        add_salesman_comp.setText("Comp.");

        jdsfkh123.setText("Save");
        jdsfkh123.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jdsfkh123MouseClicked(evt);
            }
        });

        jButton32.setText("Delete");
        jButton32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton32MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sdfjkhl12jh4Layout = new javax.swing.GroupLayout(sdfjkhl12jh4);
        sdfjkhl12jh4.setLayout(sdfjkhl12jh4Layout);
        sdfjkhl12jh4Layout.setHorizontalGroup(
            sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sdfjkhl12jh4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jklop0)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sdfjkhl12jh4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(sdfjkhl12jh4Layout.createSequentialGroup()
                                    .addComponent(add_salesman_name, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dd4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(sdfjkhl12jh4Layout.createSequentialGroup()
                                    .addGroup(sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(add_salesman_cont)
                                        .addComponent(add_salesman_comp))
                                    .addGap(34, 34, 34)
                                    .addGroup(sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ds23)
                                        .addComponent(aslkol, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jdsfkh123, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        sdfjkhl12jh4Layout.setVerticalGroup(
            sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sdfjkhl12jh4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jklop0)
                .addGap(71, 71, 71)
                .addGroup(sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_salesman_name)
                    .addComponent(dd4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_salesman_cont)
                    .addComponent(ds23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sdfjkhl12jh4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_salesman_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aslkol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdsfkh123)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton32)
                .addContainerGap())
        );

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("SalesMan Details");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Search ");

        hjllop.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hjllop.setForeground(new java.awt.Color(204, 204, 204));
        hjllop.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        hjllop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hjllopMouseClicked(evt);
            }
        });
        hjllop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hjllopKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hjllopKeyReleased(evt);
            }
        });

        hklop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Company", "Contact"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(hklop);

        javax.swing.GroupLayout AklopLayout = new javax.swing.GroupLayout(Aklop);
        Aklop.setLayout(AklopLayout);
        AklopLayout.setHorizontalGroup(
            AklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AklopLayout.createSequentialGroup()
                .addGroup(AklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AklopLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AklopLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addGroup(AklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AklopLayout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(hjllop, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                .addComponent(sdfjkhl12jh4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        AklopLayout.setVerticalGroup(
            AklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AklopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(AklopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hjllop, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(sdfjkhl12jh4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        klo09812j.add(Aklop, "Salesman");

        alkop12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel27.setText("Profit and Loss");

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel48.setText("Search Query");

        sd9.setForeground(new java.awt.Color(102, 102, 102));
        sd9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        sd9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sd9MouseClicked(evt);
            }
        });
        sd9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sd9KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sd9KeyReleased(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        Al09.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Al09.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Barcode", "Profit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Al09.setCellSelectionEnabled(true);
        Al09.setGridColor(new java.awt.Color(204, 204, 204));
        Al09.setOpaque(false);
        Al09.setRowHeight(20);
        Al09.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(Al09);
        Al09.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (Al09.getColumnModel().getColumnCount() > 0) {
            Al09.getColumnModel().getColumn(0).setPreferredWidth(150);
            Al09.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Total Profit");

        Alm89.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Alm89.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));

        jButton28.setText("Clear");
        jButton28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton28MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout alkop12Layout = new javax.swing.GroupLayout(alkop12);
        alkop12.setLayout(alkop12Layout);
        alkop12Layout.setHorizontalGroup(
            alkop12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alkop12Layout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(alkop12Layout.createSequentialGroup()
                .addContainerGap(266, Short.MAX_VALUE)
                .addGroup(alkop12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(alkop12Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sd9, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(alkop12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(alkop12Layout.createSequentialGroup()
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Alm89, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(279, Short.MAX_VALUE))
        );
        alkop12Layout.setVerticalGroup(
            alkop12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alkop12Layout.createSequentialGroup()
                .addGroup(alkop12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton28)
                    .addGroup(alkop12Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(50, 50, 50)
                        .addGroup(alkop12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sd9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(alkop12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Alm89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        klo09812j.add(alkop12, "profitloss");

        QAs.setBackground(new java.awt.Color(255, 255, 255));
        QAs.setForeground(new java.awt.Color(255, 255, 255));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel37.setText("Product Name");

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel43.setText("Barcode");

        prod_sort_icon.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        prod_sort_icon.setText("Quantity");
        prod_sort_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prod_sort_iconMouseClicked(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel56.setText("Sales Price");

        search_order_icon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search_order_icon1MouseClicked(evt);
            }
        });
        search_order_icon1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_order_icon1KeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel17.setText("Purchase Price");

        javax.swing.GroupLayout header1Layout = new javax.swing.GroupLayout(header1);
        header1.setLayout(header1Layout);
        header1Layout.setHorizontalGroup(
            header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(prod_sort_icon)
                .addGap(62, 62, 62)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel17)
                .addGap(30, 30, 30)
                .addComponent(search_order_icon1)
                .addGap(12, 12, 12))
        );
        header1Layout.setVerticalGroup(
            header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header1Layout.createSequentialGroup()
                .addGroup(header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(header1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel43)
                            .addComponent(prod_sort_icon)
                            .addComponent(jLabel56)
                            .addComponent(jLabel17)))
                    .addComponent(search_order_icon1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        o1_id7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id7.setText("jLabel19");

        o1_on7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on7.setText("jLabel19");

        o1_oq7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq7.setText("jLabel24");

        o1_p7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p7.setText("jLabel25");

        inv_pp7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp7.setText("jLabel60");

        javax.swing.GroupLayout o_u8Layout = new javax.swing.GroupLayout(o_u8);
        o_u8.setLayout(o_u8Layout);
        o_u8Layout.setHorizontalGroup(
            o_u8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id7, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(o1_on7, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(o1_oq7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(o1_p7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(inv_pp7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        o_u8Layout.setVerticalGroup(
            o_u8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on7)
                    .addComponent(o1_oq7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp7))
                .addContainerGap())
        );

        o1_id8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id8.setText("jLabel19");

        o1_on8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on8.setText("jLabel19");

        o1_oq8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq8.setText("jLabel24");

        o1_p8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p8.setText("jLabel25");

        inv_pp6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp6.setText("jLabel59");

        javax.swing.GroupLayout o_u9Layout = new javax.swing.GroupLayout(o_u9);
        o_u9.setLayout(o_u9Layout);
        o_u9Layout.setHorizontalGroup(
            o_u9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id8, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(o1_on8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(o1_oq8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(o1_p8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(inv_pp6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        o_u9Layout.setVerticalGroup(
            o_u9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on8)
                    .addComponent(o1_oq8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp6))
                .addContainerGap())
        );

        o1_id9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id9.setText("jLabel19");

        o1_on9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on9.setText("jLabel19");

        o1_oq9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq9.setText("jLabel24");

        o1_p9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p9.setText("jLabel25");

        inv_pp5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp5.setText("jLabel58");

        javax.swing.GroupLayout o_u10Layout = new javax.swing.GroupLayout(o_u10);
        o_u10.setLayout(o_u10Layout);
        o_u10Layout.setHorizontalGroup(
            o_u10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id9, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(o1_on9, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(o1_oq9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(o1_p9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(inv_pp5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        o_u10Layout.setVerticalGroup(
            o_u10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on9)
                    .addComponent(o1_oq9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp5))
                .addContainerGap())
        );

        o1_id10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id10.setText("jLabel19");

        o1_on10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on10.setText("jLabel19");

        o1_oq10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq10.setText("jLabel24");

        o1_p10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p10.setText("jLabel25");

        inv_pp4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp4.setText("jLabel57");

        javax.swing.GroupLayout o_u11Layout = new javax.swing.GroupLayout(o_u11);
        o_u11.setLayout(o_u11Layout);
        o_u11Layout.setHorizontalGroup(
            o_u11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id10, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(o1_on10, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(o1_oq10, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(o1_p10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(inv_pp4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        o_u11Layout.setVerticalGroup(
            o_u11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on10)
                    .addComponent(o1_oq10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp4))
                .addContainerGap())
        );

        o1_id11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id11.setText("jLabel19");

        o1_on11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on11.setText("jLabel19");

        o1_oq11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq11.setText("jLabel24");

        o1_p11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p11.setText("jLabel25");

        inv_pp3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp3.setText("jLabel55");

        javax.swing.GroupLayout o_u12Layout = new javax.swing.GroupLayout(o_u12);
        o_u12.setLayout(o_u12Layout);
        o_u12Layout.setHorizontalGroup(
            o_u12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u12Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id11, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(o1_on11, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(o1_oq11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(o1_p11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inv_pp3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        o_u12Layout.setVerticalGroup(
            o_u12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on11)
                    .addComponent(o1_oq11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp3))
                .addContainerGap())
        );

        o1_id12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id12.setText("jLabel19");

        o1_on12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on12.setText("jLabel19");

        o1_oq12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq12.setText("jLabel24");

        o1_p12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p12.setText("jLabel25");

        inv_pp2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp2.setText("jLabel54");

        javax.swing.GroupLayout o_u13Layout = new javax.swing.GroupLayout(o_u13);
        o_u13.setLayout(o_u13Layout);
        o_u13Layout.setHorizontalGroup(
            o_u13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id12, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(o1_on12, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(o1_oq12, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(o1_p12, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(inv_pp2, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );
        o_u13Layout.setVerticalGroup(
            o_u13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(o_u13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(o1_id12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(o1_oq12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(o1_p12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inv_pp2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u13Layout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addComponent(o1_on12)))
                .addContainerGap())
        );

        o1_id13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id13.setText("jLabel19");

        o1_on13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on13.setText("jLabel19");

        o1_oq13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq13.setText("jLabel24");

        o1_p13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p13.setText("jLabel25");

        inv_pp1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp1.setText("jLabel29");

        javax.swing.GroupLayout o_u14Layout = new javax.swing.GroupLayout(o_u14);
        o_u14.setLayout(o_u14Layout);
        o_u14Layout.setHorizontalGroup(
            o_u14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u14Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id13, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(o1_on13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(o1_oq13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(o1_p13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inv_pp1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        o_u14Layout.setVerticalGroup(
            o_u14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on13)
                    .addComponent(o1_oq13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp1))
                .addContainerGap())
        );

        o1_id14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id14.setText("jLabel19");

        o1_on14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on14.setText("jLabel19");

        o1_oq14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq14.setText("jLabel24");

        o1_p14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p14.setText("jLabel25");

        inv_pp8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp8.setText("jLabel60");

        javax.swing.GroupLayout o_u15Layout = new javax.swing.GroupLayout(o_u15);
        o_u15.setLayout(o_u15Layout);
        o_u15Layout.setHorizontalGroup(
            o_u15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u15Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id14, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(o1_on14, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(o1_oq14, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(o1_p14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(inv_pp8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        o_u15Layout.setVerticalGroup(
            o_u15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on14)
                    .addComponent(o1_oq14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp8))
                .addContainerGap())
        );

        o1_id15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id15.setText("jLabel19");

        o1_on15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on15.setText("jLabel19");

        o1_oq15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq15.setText("jLabel24");

        o1_p15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p15.setText("jLabel25");

        inv_pp9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp9.setText("jLabel59");

        javax.swing.GroupLayout o_u16Layout = new javax.swing.GroupLayout(o_u16);
        o_u16.setLayout(o_u16Layout);
        o_u16Layout.setHorizontalGroup(
            o_u16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u16Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id15, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(o1_on15, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(o1_oq15, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(o1_p15, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(inv_pp9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        o_u16Layout.setVerticalGroup(
            o_u16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on15)
                    .addComponent(o1_oq15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp9))
                .addContainerGap())
        );

        o1_id16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_id16.setText("jLabel19");

        o1_on16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_on16.setText("jLabel19");

        o1_oq16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_oq16.setText("jLabel24");

        o1_p16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o1_p16.setText("jLabel25");

        inv_pp10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inv_pp10.setText("jLabel60");

        javax.swing.GroupLayout o_u17Layout = new javax.swing.GroupLayout(o_u17);
        o_u17.setLayout(o_u17Layout);
        o_u17Layout.setHorizontalGroup(
            o_u17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(o_u17Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(o1_id16, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(o1_on16, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(o1_oq16, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(o1_p16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(inv_pp10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        o_u17Layout.setVerticalGroup(
            o_u17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, o_u17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(o_u17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(o1_id16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_on16)
                    .addComponent(o1_oq16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o1_p16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inv_pp10))
                .addContainerGap())
        );

        javax.swing.GroupLayout QAsLayout = new javax.swing.GroupLayout(QAs);
        QAs.setLayout(QAsLayout);
        QAsLayout.setHorizontalGroup(
            QAsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QAsLayout.createSequentialGroup()
                .addContainerGap(150, Short.MAX_VALUE)
                .addGroup(QAsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(o_u13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(o_u14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        QAsLayout.setVerticalGroup(
            QAsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QAsLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(o_u14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o_u17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        klo09812j.add(QAs, "allproducts");

        javax.swing.GroupLayout klop7876Layout = new javax.swing.GroupLayout(klop7876);
        klop7876.setLayout(klop7876Layout);
        klop7876Layout.setHorizontalGroup(
            klop7876Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(klop7876Layout.createSequentialGroup()
                .addComponent(jk90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(klo09812j, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(hj760, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        klop7876Layout.setVerticalGroup(
            klop7876Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(klop7876Layout.createSequentialGroup()
                .addComponent(hj760, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(klop7876Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(klop7876Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(klo09812j, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jk90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(klop7876, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(klop7876, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AGHYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AGHYMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AGHYMouseClicked
    
    private void XAaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XAaActionPerformed
        // TODO add your handling code here:
        if (!this.Ajklou12.getText().isEmpty()) {
             try{
                 int pp = Integer.parseInt(Nkiopgty67i.getText());
                 int sp = Integer.parseInt(aJKIy786g.getText());
                 int q  = Integer.parseInt(JHYULOP.getText());
                 if(sp>=pp && pp >0 && q >0){
                     PreparedStatement ps = con.prepareStatement("select id from cartInfo where barcode = '"
                             +JKlopwr.getText()+"'");
                     ResultSet rs = ps.executeQuery();
                     if(rs.next()){
                         String id = rs.getString(1);
                         if(id.equalsIgnoreCase(Ajklo.getText())){
                             sdklo9082();
                         }else{
                             JOptionPane.showMessageDialog(this, "Duplicate barcode is not allowed.");   
                         }    
                     }else
                         sdklo9082();
                    }else{
                 JOptionPane.showMessageDialog(this, "Please Fill valid Available items\nand Sales & Purchase price.");   
                }
             }catch(Exception e){
                 JOptionPane.showMessageDialog(this, "Please Fill valid amounts.");
             }
        }else{
           JOptionPane.showMessageDialog(this, "Please Fill all mandatory fields.");
        }
        
    }//GEN-LAST:event_XAaActionPerformed
    
    private void AJKLOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AJKLOPActionPerformed
        // TODO add your handling code here:
        JFileChooser jf = new JFileChooser();
        int resp = jf.showDialog(header, "Select");
        if(resp != jf.getApproveButtonMnemonic()){
            new JOptionPane().createDialog(this,"Please Select a File");
        }
        try{
            this.imageFileName = jf.getSelectedFile().getName();
            this.updateimgURL = jf.getSelectedFile().getAbsolutePath();

            java.io.File url = new java.io.File(jf.getSelectedFile().getAbsolutePath());
            BufferedImage image = ImageIO.read(url); 
            Image dimg = image.getScaledInstance(156, 177,Image.SCALE_SMOOTH);
            //////        kk.ek("Load image into frame...");
            this.JKLOPas1234.setIcon(new ImageIcon(dimg));
            this.JKLOPas1234.updateUI();

        }catch(IOException ex){
            kk.ek("UPDATE PROD IMAGE LOADING FAILED");
            kk.ek("ERROR Cause "+ex.getCause());
            kk.ek("Error Details "+ ex.toString());
            JOptionPane.showMessageDialog(null, "Error while loading image.\nTry again.");
            imageFileName = "";
        }
    }//GEN-LAST:event_AJKLOPActionPerformed

    private void AJKLOPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AJKLOPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AJKLOPMouseClicked
            
    private SwingWorker<Integer, Integer> jklop907652= new SwingWorker<Integer, Integer>() {
        @Override
        protected Integer doInBackground() throws Exception {
            while(true){
               try{
                  if(g3.size() > 0){
                      yuiwe213sae.setIcon(new ImageIcon("bin/Icons/onlineorder.png"));
                  }else{
                      yuiwe213sae.setIcon(null);
                  }
                   Thread.sleep(100);
                }catch(InterruptedException ex) {
                    yuiwe213sae.setIcon(new ImageIcon("bin/Icons/noonlineorder.png"));
                }
            }
        } 
    }; 
    
    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseClicked
        //        cart_barcode_holder.setText("725272730706");
        clkhh68gj();
        try {
            new Ajkdhfksj123as(con, ca65.getAllProds(),hjkouyth.getText()).setVisible(true);
        } catch (Exception ex) {
            kk.ek("CHECKOUT FAILED");
            kk.ek("ERROR Cause "+ex.getCause());
            kk.ek("Error Details "+ ex.toString());
        }

    }//GEN-LAST:event_jButton14MouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        //////        kk.ek("into cats");
        ca65.downloadCats();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void KLO87ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KLO87ActionPerformed
        // TODO add your handling code here:
        //////        kk.ek("Downloading Products");
        ca65.downloadProducts();
    }//GEN-LAST:event_KLO87ActionPerformed
    
    private SwingWorker<Integer, Integer> adskj= new SwingWorker<Integer, Integer>() {
        @Override
        protected Integer doInBackground() throws Exception {
            while(true){
                bref.ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                        if(ds.child("ahib").getValue().toString().equalsIgnoreCase("true"))
                        {
                            System.exit(0);
                        }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(hjklout678.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                }
            });
                Thread.sleep(1000);
            }
        } 
    };
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        OrderData o = this.g3.get(0+(10*this.currentPage));
        this.g3.remove((10*this.currentPage));
        g908(o);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
//        markCompletd(this.g3.get(1+(7*this.currentPage)),7*this.currentPage);
        OrderData o = this.g3.get(1+(7*this.currentPage));
        this.g3.remove(1+(7*this.currentPage));
        g908(o);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(2+(7*this.currentPage));
        this.g3.remove(2+(7*this.currentPage));
        g908(o);
    }//GEN-LAST:event_jButton6ActionPerformed

        private void hg57tfu897gj(String shopid){
    }
    
    private void fu897gjgfd(){
        DEL_KEY.setText("");
        kjhjhghjjh979.setText("");
        rtee4w56.setText("");
        uuhhhu767tygu.setText("");
        yuyftuyg7657g.setText("");
        tyr56eytr6rdfdy.setText("");
        tree4wet6e.setText("");
        hgh7tyyg8.setText("");
        del_shopid.setText("");
    }
    
    private boolean ghuyr569ijh = false;

    private LinkedList<String> gio90index = new LinkedList<>();
    
    private void hj67dfhh(){                                         
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
//        //        kk.ek("Attemp to download prods ");
        ref.orderByChild("shop_id").equalTo(hjkouyth.getText()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                try{
                    ProductData pd = new ProductData(ds);
                    gio90.add(pd);
                    gio90index.add(pd.getId());
                    pdrty654r(pd, gio90.size()/(1+hjt5676));
                }catch(Exception e){
                    System.out.println("Excepton ");
                    e.printStackTrace();
                }
                
//                new NoticeWindow(new Color(35, 197, 82), "New Product Added", 9000, NPosition.BOTTOM_RIGHT);
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                int index = gio90index.indexOf(ds.child("id").getValue().toString());
                ProductData d = new ProductData(ds);
                gio90.remove(index);
                gio90.add(index, d);
                hjt5676 = 0;
                QWhg34asdA();
//                new NoticeWindow(new Color(110, 200, 211), "Product Updated", 9000, NPosition.BOTTOM_RIGHT);
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                ProductData p = new ProductData(ds);
                int index = gio90index.indexOf(ds.child("id").getValue().toString());
                ProductData d = new ProductData(ds);
                gio90.remove(index);
                gio90index.remove(index);
                hjt5676 = 0;
                QWhg34asdA();
                try{
                    String sql = "delete from cartInfo WHERE barcode = '"+ds.child("barcode").getValue().toString()+"';";
                    PreparedStatement ps = con.prepareStatement(sql); 
                    ps.execute(); 
                    ps.close();
                }catch(SQLException ex){
                    kk.ek("Update Local db ");
                    kk.ek("Delete Query failed");
                    kk.ek("ERROR CODE "+ ex.getErrorCode());
                    kk.ek("ERROR Cause "+ex.getCause());
                    kk.ek("Error Details "+ ex.toString());
                }
               new NoticeWindow(new Color(250, 57, 42), "Product Removed", 9000, NPosition.BOTTOM_RIGHT);
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
                new NoticeWindow(Color.orange, "Transection Cancled", 9000, NPosition.BOTTOM_RIGHT);
            }
        }); 
    }
    
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(3+(7*this.currentPage));
        this.g3.remove(3+(7*this.currentPage));
        g908(o);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(4+(7*this.currentPage));
        this.g3.remove(4+(7*this.currentPage));
        g908(o);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(5+(7*this.currentPage));
        this.g3.remove(5+(7*this.currentPage));
        g908(o);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(6+(7*this.currentPage));
        this.g3.remove(6+(7*this.currentPage)); 
        g908(o);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void search_order_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_order_iconMouseClicked
//         TODO add your handling code here:
        new Iuy34asjkh(g3,this).setVisible(true);
    }//GEN-LAST:event_search_order_iconMouseClicked

    private void ohgftMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ohgftMouseExited
        // TODO add your handling code here:
        resetColor(ohgft);
    }//GEN-LAST:event_ohgftMouseExited

    private void ohgftMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ohgftMouseEntered
        // TODO add your handling code here:
        setColor(ohgft);
    }//GEN-LAST:event_ohgftMouseEntered

    
    private void QWhu67(){
        BufferedReader reader;
        try {
                reader = new BufferedReader(new FileReader(
                                "bin/Creds/ShopID.txt"));
                String id = reader.readLine();
                hjkouyth.setText(id.trim());
                reader.close();
        } catch (IOException ex) {
            kk.ek("SHOPID TXT NOT FOUND");
            kk.ek("ERROR Cause "+ex.getCause());
            kk.ek("Error Details "+ ex.toString());
        }
    }
    
    private static boolean njop() {
        try {
            Process process = java.lang.Runtime.getRuntime().exec("ping www.geeksforgeeks.org"); 
            int x = process.waitFor(); 
            if (x == 0) { 
               return true;
            } 
        } catch (IOException | InterruptedException e) {
        }
        return false; 
    }
    
    public void setColor(JLabel p){
        p.setBackground(new Color(0,0,0));
    }
    
    public void resetColor(JLabel p){
        p.setBackground(new Color(240,240,240));
    }
    
    private void g90(){
        JOptionPane.showMessageDialog(this, "You are working ofline please login for this featue");
    }
    
    private void lop(){
        DEL_KEY.setVisible(false);
        this.ytyg.setVisible(false);
        this.gtytfhgyg.setVisible(false);
        this.yutuytfjhgy.setVisible(false);
        this.sdfhyiuwae348628sdh.setVisible(false);
        this.o_u5.setVisible(false);
        this.aB.setVisible(false);
        this.Au.setVisible(false);
        this.o_u8.setVisible(false);
        this.o_u9.setVisible(false);
        this.o_u10.setVisible(false);
        this.o_u11.setVisible(false);
        this.o_u12.setVisible(false);
        this.o_u13.setVisible(false);
        this.o_u14.setVisible(false);
        this.o_u15.setVisible(false);
        this.o_u16.setVisible(false);
        this.o_u17.setVisible(false);
        this.jButton29.setVisible(false);
        this.jLabel41.setVisible(false);
        this.jButton15.setVisible(false);
        this.Al.setVisible(false);
        this.Ak.setVisible(false);
        this.AKLJ.setVisible(false);
        this.Ajklo.setVisible(false);
        dskjf23965fh.setIcon(new ImageIcon("bin/Icons/icons8-menu-50.png"));
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("bin/Icons/imgnotfound.jpg"));
        ohgft.setIcon(new ImageIcon("bin/Icons/icons8-purchase-order-50.png"));
        ohgft.setIcon(new ImageIcon("bin/Icons/icons8-purchase-order-50.png"));
        hjgfdrdtfgyg.setIcon(new ImageIcon("bin/Icons/icons8-add-basket-50.png"));
        hgfdesSS.setIcon(new ImageIcon("bin/Icons/icons8-update-50.png"));
        rtrexxgfd88.setIcon(new ImageIcon("bin/Icons/icons8-remove-50.png"));
        lerkwe12.setIcon(new ImageIcon("bin/Icons/icons8-gear-50.png"));
        ytyfytrf.setIcon(new ImageIcon("bin/Icons/icons8-popular-man-50.png"));
        ytyrtrdfy.setIcon(new ImageIcon("bin/Icons/icons8-profit-50.png"));
        ftfrtrs.setIcon(new ImageIcon("bin/Icons/icons8-favorite-cart-50.png"));
        search_order_icon.setIcon(new ImageIcon("bin/Icons/icons8-search-30.png"));
        search_order_icon1.setIcon(new ImageIcon("bin/Icons/icons8-search-30.png"));
        jklop0.setIcon(new ImageIcon("bin/Icons/icons8-double-left-25.png"));
        jhjgfgdgghggy.setIcon(new ImageIcon("bin/Icons/icons8-trolley-50.png"));
        prod_sort_icon.setIcon(new ImageIcon("bin/Icons/icons8-filter-and-sort-30.png"));
        jfhrkjwe23.setIcon(new ImageIcon("bin/Icons/nointernet.png"));
        sawdwaQEW.setIcon(new ImageIcon("bin/Icons/loan.png"));
        yuiwe213sae.setIcon(new ImageIcon("bin/Icons/noonlineorder.png"));
        u9(false);
        suki6754jka();
    }
    private void ohgftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ohgftMouseClicked
        // TODO add your handling code here:
        if(this.mode ==0){
            cardlayout.show(this.klo09812j, "OnlineOrder");
            search_order_icon.requestFocus();
        }
        else
            g90();
    }//GEN-LAST:event_ohgftMouseClicked

    private void ftfrtrsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ftfrtrsMouseClicked
        // TODO add your handling code here:
        this.cardlayout.show(this.klo09812j, "cart");
        Aa.requestFocus();
        suki6754jka();

    }//GEN-LAST:event_ftfrtrsMouseClicked

    private void hgfdesSSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hgfdesSSMouseClicked
        // TODO add your handling code here:
        if(this.mode ==0){
            suki6754();
            cardlayout.show(this.klo09812j, "update");
        }else
            g90();
    }//GEN-LAST:event_hgfdesSSMouseClicked

    private void hjgfdrdtfgygMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hjgfdrdtfgygMouseClicked
        if(this.mode ==0){
            hjAQ34();
            cardlayout.show(this.klo09812j, "addItemMain");
        }
        else
            g90();
    }//GEN-LAST:event_hjgfdrdtfgygMouseClicked

    //clearAddProdform
    private void hjAQ34(){
        this.hjy65449.setText("");
        this.Wasdf.setText("");
        this.WWa.setText("");
        this.WWEQSD.setText("");
        this.asdew.setText("");
        this.Adrtfgh.setText("");
        this.Wad.setText("");
        sfdjkh12940.setIcon(null);
        sdfh129sd.setText("");
        imageFileName = "";
        save_in_progress = false;
        manualSave = true;
    } 
    
    private void hjAQ3(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CategoryItems");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                if(!initAdd){
                    if(nto899.indexOf(ds.child("name").getValue().toString()) == -1){
                        nto899.add(ds.child("name").getValue().toString());
                        hjyt.addItem(ds.child("name").getValue().toString());
                        asdlJklo.addItem(ds.child("name").getValue().toString());
                        uto890.add(ds.child("id").getValue().toString()); 
                    }else{
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
            }

            @Override
            public void onCancelled(DatabaseError de) {
            }
        });
    }
    
    private void hjAQ345(){
        hyt57fg.removeAllItems();
        asdlJklo.removeAllItems();
        hjyt.removeAllItems();
        hjyt.addItem("selectOne");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CategoryItems");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot d : ds.getChildren()) {
                    if(nto899.indexOf(d.child("name").getValue().toString()) == -1){
                        nto899.add(d.child("name").getValue().toString());
                        hjyt.addItem(d.child("name").getValue().toString());
                        asdlJklo.addItem(d.child("name").getValue().toString());
                        uto890.add(d.child("id").getValue().toString()); 
                    }
                }initAdd = false;
                hjAQ3();
            }

            @Override
            public void onCancelled(DatabaseError de) {
            }
        });
    }
    
    private void yt65(CategoryData cate){
        DatabaseReference  ref = FirebaseDatabase.getInstance().getReference();
        String id = ref.child("CategoryItems").push().getKey();
        cate.setID(id);
        ref.child("CategoryItems").orderByChild("name").equalTo(cate.getName()).addListenerForSingleValueEvent(new ValueEventListener(){
             @Override
             public void onDataChange(DataSnapshot ds) {
                 boolean find =  false;
                     for(DataSnapshot d : ds.getChildren()){
                            if(d.child("shop_id").getValue().toString().equalsIgnoreCase(cate.getShop_id())){
                                find = true;
                                break;
                            }
                         }
                     if(!find){
                         ref.child("CategoryItems/"+cate.getId()).setValueAsync( cate);
                     }
                     JOptionPane.showMessageDialog(null, "Saved Seccessfuly");
                     clearCat();
                 }
             @Override
             public void onCancelled(DatabaseError de) {
                    JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
                    catsaveInProgress = false;
                 }

         });
    }

    private void yt65(){
        String name = dslkfjA123.getText().trim();
        String desc = dfhskj123456.getText();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        if(name.isEmpty()  || nto8.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Fill all fields");
            catsaveInProgress = false;
        }else{
            String url = "";
            try {
                url = jju685AS4drfg("CatImages/"+ref.push().getKey(), nto8_path);
            } catch (Exception ex) {
                kk.ek("UPLOADING Image FAILED");
                kk.ek("ERROR Cause "+ex.getCause());
                e.kl("Error Details "+ ex.toString());
                catsaveInProgress = false;
                JOptionPane.showMessageDialog(this, "Error while uploading images");
                return;
            }
            CategoryData c = new CategoryData("", nto8, name, "", desc, url);
            
            yt65(c);
        }
    }
    
    private void clearCat(){
        dfhskj123456.setText("Description");
        dslkfjA123.setText("");
        nto8 = "";
        nto8_path = "";
        sdfhk89076.setIcon(null);
        dslkfjA123.requestFocus();
        catsaveInProgress = false;
    }
    
    private void yt65Cat(SubCatData s){
        String cat_id =uto890.get(asdlJklo.getSelectedIndex());
        subcat_found = false;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String id = ref.child("SubCategoryItems").push().getKey();
        String cid = ref.child("CategoryItems").push().getKey();
        s.setId(id);
        s.setcatId(cat_id);
        ref.child("CategoryItems").orderByChild("name")
                .equalTo(nto899.get(asdlJklo.getSelectedIndex()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                CategoryData c = null;
                for(DataSnapshot d : ds.getChildren()){
                    c = new CategoryData(d);
                    if(d.child("shop_id").getValue().toString().equalsIgnoreCase(s.getShopid())){
                        subcat_found = true;
                        s.setcatId(d.getKey()); 
                        break;
                    }
                }
                if(subcat_found){
                    ref.child("SubCategoryItems/"+id).setValueAsync(s);
                }else{
                    c.setID(cid);
                    c.setShop_id(s.getShopid());
                    ref.child("CategoryItems/"+cid).setValueAsync(c);
                    s.setcatId(cid);
                    ref.child("SubCategoryItems/"+id).setValueAsync(s);
                }
                JOptionPane.showMessageDialog(null, "SubCategory Saved");
                yt65ckly76();
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        });
        
    }
    
    private void ytyfytrfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ytyfytrfMouseClicked
        // TODO add your handling code here:
        if(this.mode ==0){
            cardlayout.show(klo09812j, "Salesman");
            populateSLMTBL();
        }
        else
            g90();
        
    }//GEN-LAST:event_ytyfytrfMouseClicked

    private void rtrexxgfd88MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rtrexxgfd88MouseClicked
        // TODO add your handling code here:
        if(this.mode ==0)
            cardlayout.show(klo09812j, "searchNDel");
        else
            g90();
    }//GEN-LAST:event_rtrexxgfd88MouseClicked

    private void hgh7tyyg8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hgh7tyyg8KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            String Selectedchild = re4w35e.getSelectedIndex() ==0?"barcode":"name";
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
            ref.orderByChild("shop_id").equalTo(hjkouyth.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    boolean find = false;
                     for(DataSnapshot d : ds.getChildren()){
                         if(d.child(Selectedchild).getValue().toString().equalsIgnoreCase(hgh7tyyg8.getText())){
                              uuhhhu767tygu.setText(d.child("name").getValue().toString());
                              yuyftuyg7657g.setText(d.child("price").getValue().toString());
                              tyr56eytr6rdfdy.setText(d.child("quantity").getValue().toString());
                              kjhjhghjjh979.setText(d.child("barcode").getValue().toString());
                              DEL_KEY.setText(d.getKey());
                              find= true;
                              hg57tfu897gj(d.child("shop_id").getValue().toString());
                              hg57tfu8(d.child("cat_id").getValue().toString());
                              break;
                         }
                     }
                     if(!find)
                        JOptionPane.showMessageDialog(null, "No Product Found");
                }

                @Override
                public void onCancelled(DatabaseError de) {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }//GEN-LAST:event_hgh7tyyg8KeyPressed

    private void jButton26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton26MouseClicked
        // TODO add your handling code here:
        if(!ghuyr569ijh){
            if(DEL_KEY.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Plese Search for product first.");
            }else{
               ghuyr569ijh = true;
               DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems/"+DEL_KEY.getText());
               ref.removeValueAsync();
               fu897gjgfd();
               JOptionPane.showMessageDialog(null, "Deleted");
               ghuyr569ijh = false;   
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "Wait for Delete to complete its operation.");
        }
    }//GEN-LAST:event_jButton26MouseClicked

    private void re4w35eActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re4w35eActionPerformed
        // TODO add your handling code here:
        if(re4w35e.getSelectedIndex()==1){
            hgh7tyyg8.setText("");
            hgh7tyyg8.requestFocus();
        }
    }//GEN-LAST:event_re4w35eActionPerformed

    private void suki6754jka(){
      this.ca65 = new CartItem(con, Aa,klopdfger67, jButton14, jLabel26, TotalCount);
        clkhh68gj();
    } 
    
    private void clkhh68gj(){
        Aa.setText("");
        ca65.clearAllItems();
    }
    
    private void dsjkfy76tii(DataSnapshot ds){
        try {
                String barcode = ds.child("barcode").getValue().toString();
                String unitprice = ds.child("salesprice").getValue().toString();
                String name = ds.child("name").getValue().toString();
                String catid = ds.child("cat_id").getValue().toString();
                Integer quantity = Integer.parseInt(ds.child("quantity").getValue().toString());
                PreparedStatement p = con.prepareStatement(
                        "insert OR replace into cartInfo VALUES('"+barcode+"','"
                                +unitprice+"','"+name+"','"+catid+"',"+quantity+",'"+ds.getKey().toString()+"');");
                p.execute();
                p.close();
                String sql = "insert OR replace into imageTable VALUES('Noimg.jpg','"+catid+"');";
                PreparedStatement ps = con.prepareStatement(sql); 
                ps.execute();
                ps.close();
            } catch (SQLException ex) {
                e.kl("Error while updating local DB");
                e.kl("Error Code "+ ex.getErrorCode());
                kk.ek("Error Cause "+ex.getCause());
            }
    }
    
    private void kilo09(){        
        
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
        ref.orderByChild("shop_id").equalTo(hjkouyth.getText()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                dsjkfy76tii(ds);
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                dsjkfy76tii(ds);
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                                
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        });
    }
    
    private void hg57tfu8(String cat){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CategoryItems");
            ref.orderByChild("id").equalTo(cat).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    if(ds.getChildrenCount() >0){
                        for (DataSnapshot d : ds.getChildren()) {
                            rtee4w56.setText(d.child("name").getValue().toString());
                            break;
                        }
                    } 
                }
                

                @Override
                public void onCancelled(DatabaseError de) {
                    JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
                }
            });
    }
    
    private void lerkwe12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lerkwe12MouseClicked
        // TODO add your handling code here:
        if(this.mode ==0)
            cardlayout.show(klo09812j,"services");
        else
            g90();
        
    }//GEN-LAST:event_lerkwe12MouseClicked

    private void hgh7tyyg8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hgh7tyyg8MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_hgh7tyyg8MouseClicked
    
    private void dskjf23965fhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dskjf23965fhMouseClicked
        // TODO add your handling code here:
        if(wide){
            wide = false;
            jk90.setBounds(this.jk90.getX(), this.jk90.getY(), 60, this.jk90.getHeight());
            klo09812j.setBounds(60, this.klo09812j.getY(), this.klo09812j.getWidth()+129, this.klo09812j.getHeight());
            dlkop.setBounds(dlkop.getX()+129, dlkop.getY(), dlkop.getWidth(), dlkop.getHeight());
            klopdfger67.setBounds(klopdfger67.getX()+129,klopdfger67.getY(),klopdfger67.getWidth(),klopdfger67.getHeight());
            kloup.setBounds(kloup.getX()+129,kloup.getY(),kloup.getWidth(),kloup.getHeight());
            QAs.setBounds(QAs.getX()+129,QAs.getY(),QAs.getWidth(),QAs.getHeight());
            weiuyr78GHJ.setBounds(weiuyr78GHJ.getX()+129, this.weiuyr78GHJ.getY(), this.weiuyr78GHJ.getWidth(), this.weiuyr78GHJ.getHeight());
            vbd.setBounds(vbd.getX()+129, this.vbd.getY(), this.vbd.getWidth(), this.vbd.getHeight());
            jdskfh132ds.setBounds(jdskfh132ds.getX()+129, this.jdskfh132ds.getY(), this.jdskfh132ds.getWidth(), this.jdskfh132ds.getHeight());
            jklop.setBounds(jklop.getX()+129, this.jklop.getY(), this.jklop.getWidth(), this.jklop.getHeight());
            alkop12.setBounds(alkop12.getX()+129, this.alkop12.getY(), this.alkop12.getWidth(), this.alkop12.getHeight());
            }else{
            this.jk90.setBounds(this.jk90.getX(), this.jk90.getY(), 188, this.jk90.getHeight());
            this.klo09812j.setBounds(189, this.klo09812j.getY(), this.klo09812j.getWidth()-129, this.klo09812j.getHeight());
            dlkop.setBounds(dlkop.getX()-129, dlkop.getY(), dlkop.getWidth(), dlkop.getHeight());
            klopdfger67.setBounds(klopdfger67.getX()-129,klopdfger67.getY(),klopdfger67.getWidth(),klopdfger67.getHeight());
            kloup.setBounds(kloup.getX()-129,kloup.getY(),kloup.getWidth(),kloup.getHeight());
            QAs.setBounds(QAs.getX()-129,QAs.getY(),QAs.getWidth(),QAs.getHeight());
            weiuyr78GHJ.setBounds(weiuyr78GHJ.getX()-129, this.weiuyr78GHJ.getY(), this.weiuyr78GHJ.getWidth(), this.weiuyr78GHJ.getHeight());
            jdskfh132ds.setBounds(jdskfh132ds.getX()-129, this.jdskfh132ds.getY(), this.jdskfh132ds.getWidth(), this.jdskfh132ds.getHeight());
            vbd.setBounds(vbd.getX()-129, this.vbd.getY(), this.vbd.getWidth(), this.vbd.getHeight());
            jklop.setBounds(jklop.getX()-129, this.jklop.getY(), this.jklop.getWidth(), this.jklop.getHeight());
            alkop12.setBounds(alkop12.getX()-129, this.alkop12.getY(), this.alkop12.getWidth(), this.alkop12.getHeight());
            wide = true;
        }

    }//GEN-LAST:event_dskjf23965fhMouseClicked

    private void ytyrtrdfyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ytyrtrdfyMouseClicked
        // TODO add your handling code here:
        if(mode == 0)
            cardlayout.show(klo09812j, "profitloss");
        else{
            g90();
        }
    }//GEN-LAST:event_ytyrtrdfyMouseClicked
    
    private boolean collpsed = true;
    
    private void jklop0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jklop0MouseClicked
        // TODO add your handling code here:
        if(collpsed){
            jklop0.setText("Collaps");
            jklop0.setIcon(new ImageIcon("bin/Icons/icons8-double-right-25.png"));
            sdfjkhl12jh4.setBounds(sdfjkhl12jh4.getX()-230, sdfjkhl12jh4.getY(), sdfjkhl12jh4.getWidth()+230, sdfjkhl12jh4.getHeight());
            u9(true);
            collpsed = false;
        }else{
            sdfjkhl12jh4.setBounds(sdfjkhl12jh4.getX()+230, sdfjkhl12jh4.getY(), sdfjkhl12jh4.getWidth()-230, sdfjkhl12jh4.getHeight());
            jklop0.setText("Expand");
            jklop0.setIcon(new ImageIcon("bin/Icons/icons8-double-left-25.png"));
            u9(false);
            collpsed = true;
        }
        
    }//GEN-LAST:event_jklop0MouseClicked

    private void dd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dd4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dd4ActionPerformed

    private void jdsfkh123MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jdsfkh123MouseClicked
        // TODO add your handling code here:
        if(aslkol.getText().isEmpty()|| ds23.getText().isEmpty() || dd4.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Pleas Fill all fields");
        }else{
            String name = dd4.getText();
            String cont = ds23.getText();
            String comp = aslkol.getText();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SalesMan/"+hjkouyth.getText());
            slm s = new slm(name,cont,comp);
            ref.child( name+"-"+cont).setValueAsync(s);
            JOptionPane.showMessageDialog(null, "Save Successful");
            dd4.setText("");
            ds23.setText("");
            aslkol.setText("");
        }
    }//GEN-LAST:event_jdsfkh123MouseClicked
    private class slm{
       String name;
       String contact;
       String company;

        public slm(String name, String contact, String company) {
            this.name = name;
            this.contact = contact;
            this.company = company;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
       
        
        
    }
    private SwingWorker<Integer, Integer> asdjku789j= new SwingWorker<Integer, Integer>() {
        @Override
        protected Integer doInBackground() throws Exception {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Shop");
            while(true){
               try{
                  if(njop()){
                      if(mode == 0){
                        ref.orderByChild("id")
                                .equalTo(hjkouyth.getText())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot ds) {
                                  for (DataSnapshot d : ds.getChildren()) {
                                      ref.child(d.getKey()+"/onlineStatus").setValueAsync(""+System.currentTimeMillis());
                                      break;
                                  }

                              }

                              @Override
                              public void onCancelled(DatabaseError de) {
                                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                              }
                          }); 
                      }
                      jfhrkjwe23.setIcon(new ImageIcon("bin/Icons/internet.png"));
                  }else{
                      jfhrkjwe23.setIcon(new ImageIcon("bin/Icons/nointernet.png"));
                  }
                   Thread.sleep(100);
                }catch(InterruptedException ex) { 
                    jfhrkjwe23.setIcon(new ImageIcon("bin/Icons/nointernet.png"));
                    kk.ek("Internet icon update failed");
                    kk.ek("ERROR Cause "+ex.getCause());
                    kk.ek("Error Details "+ ex.toString());
                }
            }
        } 
    }; 
    
    
        
    public hjklout678(int mode, String jkgyt) {
        this.mode = mode;
        jkl();
        initComponents();
        if(jkgyt.isEmpty()){
            QWhu67();
        }else{
            hjkouyth.setText(jkgyt);
        }
        lop();
        adskj.execute();
        asdjku789j.execute();
        jklop907652.execute();
//        this.PNL_CARD.setBounds(PNL_CARD.getX(),PNL_CARD.getY(),PNL_CARD.getWidth(),PNL_CARD.getHeight());
        cardlayout = (CardLayout)(this.klo09812j.getLayout());
        this.setLocationRelativeTo(this);
        if(mode == 0){           
            kilo09();
            hj67dfhh();
            jklop23yju();
            hjAQ345();
            u90();
            cardlayout.show(klo09812j, "allproducts");
            search_order_icon1.requestFocus();
            AQsdRE();
        }else{
            cardlayout.show(klo09812j, "cart");
        }
    }
    
    private void jkl(){
        try {
            con = DriverManager.getConnection("jdbc:sqlite:bin/db/cartproductinfo.db","","");
        } catch (SQLException ex) {
            kk.ek("JDBC Connection failed");
            kk.ek("ERROR Cause "+ex.getCause());
            kk.ek("ERROR Cause "+ex.getErrorCode());
            kk.ek("Error Details "+ ex.toString());
        }
    }
    private void hjllopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hjllopKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_hjllopKeyPressed

    private void sdklfjdksl(String s){
        DefaultTableModel model = (DefaultTableModel) hklop.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        hklop.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(s));
    }
    
    private void hjllopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hjllopMouseClicked

    }//GEN-LAST:event_hjllopMouseClicked

    private void jhjgfgdgghggyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jhjgfgdgghggyMouseClicked
        // TODO add your handling code here:
        hjt5676 = 0;
        QWhg34asdA();
        cardlayout.show(klo09812j, "allproducts");
        search_order_icon1.requestFocus();
    }//GEN-LAST:event_jhjgfgdgghggyMouseClicked

    private void search_order_icon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_order_icon1MouseClicked
        // TODO add your handling code here:
        new sdfkh32().setVisible(true);
    }//GEN-LAST:event_search_order_icon1MouseClicked

    private void hjk65899(JLabel orderId, JLabel price, OrderData pd, JPanel panel){
        orderId.setText(pd.getOrderId()); 
        price.setText(pd.getOrderP());
        panel.setVisible(true);
    }
    
    public final void reDraw(){
        for (int i = 0; i < 10; i++) {
            try{
                hjk65890(this.g3.get(i+(10*this.currentPage)),i+1);
            }catch(Exception e){
                hiderows(i+1);
            }
        }
        
    }
    private int ordercount_movrtorider = 0;
    
    private void gio908(OrderData o){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
        ref.orderByChild("shop_id").equalTo(hjkouyth.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot d : ds.getChildren()) {
                    if(o.pid.equalsIgnoreCase(d.child("id").getValue().toString())){
                            String key = d.getKey();
                            int quant = Integer.parseInt(d.child("quantity").getValue().toString());
                            int si = Integer.parseInt(d.child("soldItems").getValue().toString());
                            int oq = Integer.parseInt(o.quantity);
                            ref.child(key+"/quantity").setValueAsync((quant-oq));
                            ref.child(key+"/soldItems").setValueAsync(""+(si+oq));
                            
                        }
                    for (OrderData nextOrder : o.nextOrders) {
                        if(nextOrder.pid.equalsIgnoreCase(d.child("id").getValue().toString())){
                            String key = d.getKey();
                            int quant = Integer.parseInt(d.child("quantity").getValue().toString());
                            int si = Integer.parseInt(d.child("soldItems").getValue().toString());
                            int oq = Integer.parseInt(nextOrder.quantity);
                            ref.child(key+"/quantity").setValueAsync((quant-oq));
                            ref.child(key+"/soldItems").setValueAsync(""+(si+oq));
                            
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }); 
    }
    
    private void g908(OrderData o){
        DatabaseReference reforder = FirebaseDatabase.getInstance().getReference("Order/"+o.shopid+"/Products");
        DatabaseReference refrider = FirebaseDatabase.getInstance().getReference("OrderBiker/");
        
        reforder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for(int i =0;i<o.orderIDs.size(); i++){
                    ordercount_movrtorider = i;
                    refrider.child(o.shopid+"/Products/"+o.orderIDs.get(i))
                                        .setValueAsync(ds.child(o.orderIDs.get(i)).getValue());
                    reforder.child(o.orderIDs.get(i)+"/ordercomplete").setValueAsync("1");
                    ju887(i, reforder, o);  
                }
                gio908(o);
                JOptionPane.showMessageDialog(null, "Moved to rider");
                reDraw();
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        }); 
    }
    
    public String gethjkouyth(){
        return hjkouyth.getText();
    } 
    
    private void ju887(int val, DatabaseReference reforder,OrderData o ){
        DatabaseReference refBiker = FirebaseDatabase.getInstance().getReference("Biker/");
        refBiker.orderByChild("shopId").equalTo(hjkouyth.getText())
                            .limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot ds) {
                            for (DataSnapshot d : ds.getChildren()) {
                                reforder.child(o.orderIDs.get(val)+"/bikeplate").setValueAsync(d.child("bikeNumber").getValue().toString());
                                reforder.child(o.orderIDs.get(val)+"/bikerphn").setValueAsync(d.child("phone").getValue().toString());
                                try {
                                    Message m = Message.builder()
                                            .putData("New Order placed", "")
                                            .setNotification(new Notification("New Order Placed",
                                                    "Please reach your shop."))
                                            .setTopic(d.child("subscriptionid").getValue().toString())
                                            .build();
                                    String response = FirebaseMessaging.getInstance().send(m);
                                    e.kl(response);

                                } catch (Exception ex) {
                                    e.kl("Error while sending notification");
                                    kk.ek(ex.toString());
                                    ex.printStackTrace();
                                }
                                break;
                            }
                            
                        }

                        @Override
                        public void onCancelled(DatabaseError de) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
    }   
    
    private void jk765rty76(){
        if(currentPage >0){
            currentPage --;
            for (int i = 0; i < 10; i++) {
                hjk65890(g3.get(i+(10*currentPage)),i+1);
            }
        }else{
            JOptionPane.showMessageDialog(this,"Already on First Page");
        }
    }
    
    private void jk765rty77(){
        int val = this.g3.size() - (1+this.currentPage)*10;
        if(val > 0){
            currentPage ++;
            for (int i = 0; i < 10; i++) {
                hjk65890(g3.get(i+(10*currentPage)),i+1);
                if((val -(i+1)) <= 0){
                    for (int j = i; j < 10; j++) {
                        hiderows(j+1);
                    }
                    break;
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "Already on Last Page");
        }
    }
    
    private void hiderows(int j) {
        switch(j){
            case 1:
                this.ytyg.setVisible(false);
                break;
            case 2:
                this.gtytfhgyg.setVisible(false);
                break;
            case 3:
                this.yutuytfjhgy.setVisible(false);
                break;
            case 4:
                this.sdfhyiuwae348628sdh.setVisible(false);
                break;
            case 5:
                this.o_u5.setVisible(false);
                break;
            case 6:
                this.aB.setVisible(false);
                break;
            case 7:
                this.Au.setVisible(false);
                break;
            case 8:
                this.Au.setVisible(false);
                break;
            case 9:
                this.Au.setVisible(false);
                break;
            case 10:
                this.Au.setVisible(false);
                break;
        }
    }
    
    private void prod_sort_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prod_sort_iconMouseClicked
        // TODO add your handling code here:
        gh65fh();
    }//GEN-LAST:event_prod_sort_iconMouseClicked

    private void AGHYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGHYActionPerformed
        // TODO add your handling code here:
        JKlopwr.requestFocus();
    }//GEN-LAST:event_AGHYActionPerformed

    private void dfsh34hsdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dfsh34hsdMouseClicked
        // TODO add your handling code here:
        JFileChooser f = new JFileChooser();
        int v = f.showDialog(this, "Select");
        if(v == f.getApproveButtonMnemonic()){
            try {
                nto898u78file = f.getSelectedFile().getName();
                nto898u78file_path = f.getSelectedFile().getAbsolutePath();
                java.io.File url = new java.io.File(f.getSelectedFile().getAbsolutePath());
                BufferedImage image = ImageIO.read(url);
                Image dimg = image.getScaledInstance(156, 177,Image.SCALE_SMOOTH);
                //debug.append("\n"+"Load image into frame...");
                this.sfdjkh12940.setIcon(new ImageIcon(dimg));
                this.sfdjkh12940.updateUI();
            } catch (Exception ex) {
                kk.ek("Error while reading prod image ");
                kk.ek("Error Details "+ex.toString());
                JOptionPane.showMessageDialog(null, "Error while reading image try again.");
                nto898u78file = "";
                nto898u78file_path="";
                sfdjkh12940.setIcon(null);
            }
        }
    }//GEN-LAST:event_dfsh34hsdMouseClicked
    
    private boolean save_in_progress = false;
    
    private void ksdfh345_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ksdfh345_sActionPerformed
        // TODO add your handling code here:
        if(!save_in_progress){
            if(hjyt.getSelectedIndex()>0 && hyt57fg.getSelectedIndex() >0){
                try{
                    save_in_progress = true;
                    int pp = Integer.parseInt(WWa.getText());
                    int sp = Integer.parseInt(WWEQSD.getText());
                    int au = Integer.parseInt(asdew.getText());
                    if(pp >0 && sp>=pp && au >0)
                        yt65ckly76pyt6();
                    else{
                        JOptionPane.showMessageDialog(this, "Enter valid Sales and purchase price\nand available items");
                        save_in_progress = false;
                    }
                }catch(HeadlessException | NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "Enter valid Sales and purchase price \nand available items");
                    save_in_progress = false;
               }
            }else{
                JOptionPane.showMessageDialog(this, "Select valid Category and subCategory.");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Wait for previous save to complete");
        }
    }//GEN-LAST:event_ksdfh345_sActionPerformed
    
    private void populateSLMTBL(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SalesMan/"+hjkouyth.getText());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    if(ds.getChildrenCount()>0){
                        DefaultTableModel m = (DefaultTableModel) hklop.getModel();
                        m.setRowCount(0);
                        for (DataSnapshot d : ds.getChildren() ) {
                            Object[] o = {d.child("name").getValue().toString()
                                            ,d.child("company").getValue().toString()
                                            ,d.child("contact").getValue().toString()}; 
                            m.addRow(o);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "No Salesman foound");
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
    }
    
    private void AQsdRE(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SalesMan/"+hjkouyth.getText());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                DefaultTableModel m = (DefaultTableModel) hklop.getModel();
                try{
                    Object[] o = {ds.child("name").getValue().toString()
                                        ,ds.child("company").getValue().toString()
                                        ,ds.child("contact").getValue().toString()}; 
                m.addRow(o);
                }catch(Exception e){
                    System.out.println("Error WHILE UPDATING SLM");
                    System.out.println(ds.getValue());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    private void hjytItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hjytItemStateChanged
        // TODO add your handling code here:
        if(!initAdd && hjyt.getSelectedIndex()>0){
            uto899.clear();
            nto890.clear();
            hyt57fg.removeAllItems();
            hyt57fg.addItem("SelectOne");
            String id = uto890.get(hjyt.getSelectedIndex()-1);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SubCategoryItems");
            ref.orderByChild("category_id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    for (DataSnapshot d : ds.getChildren()) {
                        if(nto890.indexOf(d.child("name").getValue().toString()) == -1){
                            nto890.add(d.child("name").getValue().toString());
                            hyt57fg.addItem(d.child("name").getValue().toString());
                            uto899.add(d.child("id").getValue().toString());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }//GEN-LAST:event_hjytItemStateChanged

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        clearCat();
        yt65ckly76();
        cardlayout.show(klo09812j, "AddItem");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void Ajklou12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Ajklou12KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            if(! Ajklou12.getText().isEmpty()){
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
                ref.orderByChild(sdjlop.getSelectedItem().toString())
                        .equalTo(Ajklou12.getText())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot ds) {
                            boolean find = false;
                             for(DataSnapshot d : ds.getChildren()){
                                 if(d.child("shop_id").getValue().toString().equalsIgnoreCase(hjkouyth.getText())){
                                      jklOPhsklop.setText(d.child("size").getValue().toString());
                                      Jkiop90875sad.setText(d.child("name").getValue().toString());
                                      Ajklo.setText(d.getKey());
                                      Nmklobvg.setText(d.child("companyName").getValue().toString());
                                      Nkiopgty67i.setText(d.child("price").getValue().toString());
                                      JHYULOP.setText(d.child("quantity").getValue().toString());
                                      JKlopwr.setText(d.child("barcode").getValue().toString());
                                      aJKIy786g.setText(d.child("salesprice").getValue().toString());
                                      String cat = d.child("cat_id").getValue().toString();
                                      DatabaseReference refi = FirebaseDatabase.getInstance().getReference();
                                      refi.child("CategoryItems/"+cat).addListenerForSingleValueEvent(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(DataSnapshot ds) {
                                              ////        kk.ek("into cat name");
                                              klopas234.setText(ds.child("name").getValue().toString());
                                          }

                                          @Override
                                          public void onCancelled(DatabaseError de) {
                                              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                          }
                                      }); 
                                      find= true;
                                      break;
                                 }
                             }
                             if(!find)
                                JOptionPane.showMessageDialog(null, "No Product Found");
                        }

                        @Override
                        public void onCancelled(DatabaseError de) {
        //                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
            }else{
                JOptionPane.showMessageDialog(this, "Please fill mandatory items");
            }
        }
    }//GEN-LAST:event_Ajklou12KeyPressed

    private void jklOPhsklopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jklOPhsklopKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jklOPhsklopKeyPressed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(1+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(2+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(6+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void search_order_icon1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_order_icon1KeyPressed
        // TODO add your handling code here:
        switch(evt.getKeyCode()){
            case 10:
                new sdfkh32().setVisible(true);
                break;
            case 40:
            case 37:
                ty568fdfgh();
                search_order_icon1.requestFocus();
                break;
            case 38:
            case 39:
                gh65yu67();
                search_order_icon1.requestFocus();
                break;
        }
        
    }//GEN-LAST:event_search_order_icon1KeyPressed

    private void search_order_iconKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_order_iconKeyPressed
        // TODO add your handling code here:
        ////        kk.ek(evt.getKeyCode());
        switch(evt.getKeyCode()){
            case 10:
                new Iuy34asjkh(g3,this).setVisible(true);
                break;
            case 37:
            case 40:
                jk765rty76();
                search_order_icon.requestFocus();
                break;
            case 38:
            case 39:
                jk765rty77();
                search_order_icon.requestFocus();
                break;
        }
        
    }//GEN-LAST:event_search_order_iconKeyPressed

    private void kloupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kloupKeyPressed
        // TODO add your handling code here:
        search_order_icon.requestFocus();
    }//GEN-LAST:event_kloupKeyPressed

    private void kloupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kloupMouseClicked
        // TODO add your handling code here:
        search_order_icon.requestFocus();
    }//GEN-LAST:event_kloupMouseClicked

    private void sd9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sd9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sd9KeyPressed

    private void sd9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sd9MouseClicked
        // TODO add your handling code here:
        sd9.setText("");

    }//GEN-LAST:event_sd9MouseClicked

    private void sd9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sd9KeyReleased
        // TODO add your handling code here:
        filter(sd9.getText().toLowerCase());
    }//GEN-LAST:event_sd9KeyReleased

    private void fdshQoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fdshQoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fdshQoActionPerformed

    private void dsfklopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dsfklopActionPerformed

    }//GEN-LAST:event_dsfklopActionPerformed

    private void dsfklopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dsfklopMouseClicked
        // TODO add your handling code here:
        JFileChooser f = new JFileChooser();
            int v = f.showDialog(this, "Select");
            if(v == f.getApproveButtonMnemonic()){
                sub_nto8 = f.getSelectedFile().getName();
               try {
                    java.io.File url = new java.io.File(f.getSelectedFile().getAbsolutePath());
                    BufferedImage image = ImageIO.read(url);
                    Image dimg = image.getScaledInstance(156, 177,Image.SCALE_SMOOTH);
                    //debug.append("\n"+"Load image into frame...");
                    this.sdfjho90.setIcon(new ImageIcon(dimg));
                    this.sdfjho90.updateUI();
                } catch (IOException ex) {
                    Logger.getLogger(hjklout678.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }//GEN-LAST:event_dsfklopMouseClicked

    private void xdfj12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xdfj12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_xdfj12MouseClicked

    private void djskfLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_djskfLopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_djskfLopActionPerformed

    private void dfhskj123456MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dfhskj123456MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dfhskj123456MouseClicked

    private void dsfljslKIOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dsfljslKIOPActionPerformed

    }//GEN-LAST:event_dsfljslKIOPActionPerformed

    private void dsfljslKIOPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dsfljslKIOPMouseClicked
        // TODO add your handling code here:
        JFileChooser f = new JFileChooser();
            int v = f.showDialog(this, "Select");
            if(v == f.getApproveButtonMnemonic()){
                try {
                    nto8 = f.getSelectedFile().getName();
                    java.io.File url = new java.io.File(f.getSelectedFile().getAbsolutePath());
                    BufferedImage image = ImageIO.read(url);
                    Image dimg = image.getScaledInstance(156, 177,Image.SCALE_SMOOTH);
                    //debug.append("\n"+"Load image into frame...");
                    this.sdfhk89076.setIcon(new ImageIcon(dimg));
                    this.sdfhk89076.updateUI();
                } catch (IOException ex) {
                    Logger.getLogger(hjklout678.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }//GEN-LAST:event_dsfljslKIOPMouseClicked

    private void djskfLopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_djskfLopKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10)
        {
            if(!catsaveInProgress){
                catsaveInProgress = true;
                yt65();
            }
        }
    }//GEN-LAST:event_djskfLopKeyPressed

    private void dsfljslKIOPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dsfljslKIOPKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            JFileChooser f = new JFileChooser();
            int v = f.showDialog(this, "Select");
            if(v == f.getApproveButtonMnemonic()){
                try {
                    nto8 = f.getSelectedFile().getName();
                    nto8_path = f.getSelectedFile().getAbsolutePath();
                    java.io.File url = new java.io.File(f.getSelectedFile().getAbsolutePath());
                    BufferedImage image = ImageIO.read(url);
                    Image dimg = image.getScaledInstance(156, 177,Image.SCALE_SMOOTH);
                    //debug.append("\n"+"Load image into frame...");
                    this.sdfhk89076.setIcon(new ImageIcon(dimg));
                    this.sdfhk89076.updateUI();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error while loading image try again");
                    nto8 ="";
                    nto8_path = "";
                    sdfhk89076.setIcon(null); 
                }
            }
        }
    }//GEN-LAST:event_dsfljslKIOPKeyPressed

    private void fdshQoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fdshQoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            if(!subcatsaveInProgress){
                subcatsaveInProgress = false;
                yt65Cat(); 
            }
        }
    }//GEN-LAST:event_fdshQoKeyPressed

    private void fdshQoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fdshQoMouseClicked
        // TODO add your handling code here:
        if(!subcatsaveInProgress){
                subcatsaveInProgress = false;
                yt65Cat(); 
            }
    }//GEN-LAST:event_fdshQoMouseClicked

    private void dsfklopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dsfklopKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            JFileChooser f = new JFileChooser();
            int v = f.showDialog(this, "Select");
            if(v == f.getApproveButtonMnemonic()){
                sub_nto8 = f.getSelectedFile().getName();
                sub_nto8_path = f.getSelectedFile().getAbsolutePath();
               try {
                    java.io.File url = new java.io.File(f.getSelectedFile().getAbsolutePath());
                    BufferedImage image = ImageIO.read(url);
                    Image dimg = image.getScaledInstance(156, 177,Image.SCALE_SMOOTH);
                    //debug.append("\n"+"Load image into frame...");
                    this.sdfjho90.setIcon(new ImageIcon(dimg));
                    this.sdfjho90.updateUI();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(hjyt, "Error while loading image.\nTry again later.");
                    sub_nto8 = "";
                    sub_nto8_path = "";
                    sdfjho90.setIcon(null);
                }
            }
        }
    }//GEN-LAST:event_dsfklopKeyPressed

    private void dfhskj123456FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dfhskj123456FocusGained
        // TODO add your handling code here:
        dfhskj123456.setText("");
    }//GEN-LAST:event_dfhskj123456FocusGained

    private void xdfj12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_xdfj12FocusGained
        // TODO add your handling code here:
        xdfj12.setText("");
    }//GEN-LAST:event_xdfj12FocusGained

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        OrderData o = this.g3.get(0+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(3+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(4+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(5+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void sawdwaQEWMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sawdwaQEWMouseClicked
        // TODO add your handling code here:
        if(mode == 0){
            new afqr0(hjkouyth.getText()).setVisible(true);
        }else{
            g90();
        }
    }//GEN-LAST:event_sawdwaQEWMouseClicked
    
    private void pdrty654r(ProductData pd,int num){      
        switch(num){
            case 1:
                hjk65899P(this.o1_id13, this.o1_on13, this.o1_oq13, this.o1_p13, pd, this.o_u14, inv_pp1);
                break;
            case 2:
                hjk65899P(this.o1_id12, this.o1_on12, this.o1_oq12, this.o1_p12, pd, this.o_u13, inv_pp2);
                break;
            case 3:
                hjk65899P(this.o1_id11, this.o1_on11, this.o1_oq11, this.o1_p11, pd, this.o_u12, inv_pp3);
                break;
            case 4:
                hjk65899P(this.o1_id10, this.o1_on10, this.o1_oq10, this.o1_p10, pd, this.o_u11, inv_pp4);
                break;
            case 5:
                hjk65899P(this.o1_id9, this.o1_on9, this.o1_oq9, this.o1_p9, pd, this.o_u10, inv_pp5);
                break;
            case 6:
                hjk65899P(this.o1_id8, this.o1_on8, this.o1_oq8, this.o1_p8, pd, this.o_u9, inv_pp6);
                break;
            case 7:
                hjk65899P(this.o1_id7, this.o1_on7, this.o1_oq7, this.o1_p7, pd, this.o_u8, inv_pp7);
                break;
            case 8:
                hjk65899P(this.o1_id15, this.o1_on15, this.o1_oq15, this.o1_p15, pd, this.o_u16, inv_pp9);
                break;
            case 9:
                hjk65899P(this.o1_id14, this.o1_on14, this.o1_oq14, this.o1_p14, pd, this.o_u15, inv_pp8);
                break;
            case 10:
                hjk65899P(this.o1_id16, this.o1_on16, this.o1_oq16, this.o1_p16, pd, this.o_u17, inv_pp10);
                break;
        }
        
    }
    
    private void hjk65899P(JLabel orderId, JLabel pName, JLabel pquantity, JLabel price, ProductData pd, JPanel panel, JLabel pprice){
        orderId.setText(pd.getName());
        pName.setText(pd.getBarcode());
        pquantity.setText(""+pd.getQuantity());
        pprice.setText(pd.getPrice());
        price.setText(pd.getSalesprice());
        if(pd.getQuantity()<=8)
            panel.setBackground(new Color(151,255,255));
        else{
            panel.setBackground(new Color(240,240,240));
        }
        panel.setVisible(true);
    }
    
    private void gh65fh(){
        //        kk.ek("Before sorting");
        //        kk.ek(gio90.toString());
        //        kk.ek("INDEX");
        //        kk.ek(gio90index.toString());
        if(gio90.size()>1){
            LinkedList<ProductData> s = new LinkedList<>();
            LinkedList<String> index = new LinkedList<>();
            s.add(gio90.get(0));
            index.add(gio90index.get(0));
            for(int i =1; i<gio90.size(); i++){
                ProductData val = gio90.get(i);
                String id = gio90index.get(i);
                for(int j =0; j <s.size();j++){
                    if(val.getQuantity()<=s.get(j).getQuantity()){
                        s.add(j,val);
                        index.add(j,id);
                        break;
                    }if(j+1 == s.size()){
                        s.add(val);
                        index.add(id);
                        break;
                    }
                }            
            }
            gio90 = s;
            gio90index = index;
            hjt5676 = 0;
            //        kk.ek("After sorting");
            //        kk.ek(gio90.toString());
            //        kk.ek(gio90index.toString());
            QWhg34asdA();
        }
    }
    
    private void gh65yu67(){
        int val = gio90.size() - (1+hjt5676)*10;
        if(val > 0){
            hjt5676 ++;
            for (int i = 0; i < 10; i++) {
                pdrty654r(gio90.get(i+(10*hjt5676)),i+1);
                if((val -(i+1)) <= 0){
                    for (int j = i+1; j < 10; j++) {
                        QWh(j+1);
                    }
                    break;
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "Already on Last Page");
        }
    }
    
    private void ty568fdfgh(){
        if(hjt5676 >0){
            hjt5676 --; 
            for (int i = 0; i < 10; i++) {
                pdrty654r(gio90.get(i+(10*hjt5676)),i+1);
            }
        }else{
            JOptionPane.showMessageDialog(this,"Already on First Page");
        }
    }
    
    private void QWhg34asdA(){
        for(int i =1; i <=10;i++){
            if(i <= gio90.size()){
                pdrty654r(gio90.get(i-1), i);
            }else{
                QWh(i);
            }
        }
    }
    
    private void QWh(int j) {
        switch(j){
            case 1:
                this.o_u14.setVisible(false);
                break;
            case 2:
                this.o_u13.setVisible(false);
                break;
            case 3:
                this.o_u12.setVisible(false);
                break;
            case 4:
                this.o_u11.setVisible(false);
                break;
            case 5:
                this.o_u10.setVisible(false);
                break;
            case 6:
                this.o_u9.setVisible(false);
                break;
            case 7:
                this.o_u8.setVisible(false);
                break;
            case 8:
                this.o_u16.setVisible(false);
                break;
            case 9:
                this.o_u15.setVisible(false);
                break;
            case 10:
                this.o_u17.setVisible(false);
                break;
        }
    }
    private boolean manualSave = false;
    String subcatSaveId = "";
    String catSaveId = "";
    ProductData existingpd;
    
    private void populateAddForm(DatabaseReference ref, DataSnapshot ds){
        manualSave = false;
        ksdfh345_s.setEnabled(false);
        for (DataSnapshot d : ds.getChildren()) {
            String id = ref.push().getKey();
            existingpd = new ProductData(d);
            subcatSaveId = existingpd.getSub_category_id();
            catSaveId = existingpd.getCat_id();
            existingpd.setShop_id(hjkouyth.getText());
            existingpd.setId(id);
            existingpd.setSoldItems("0");
            hjy65449.setText(d.child("name").getValue().toString());
            Wad.setText(d.child("size").getValue().toString());
            Wasdf.setText(d.child("companyName").getValue().toString());
            WWa.setText(d.child("price").getValue().toString());
            WWEQSD.setText(d.child("salesprice").getValue().toString());
            asdew.setText(d.child("quantity").getValue().toString());
            sdfh129sd.setText(d.child("description").getValue().toString());
            nto898u78URL = d.child("imgURL").getValue().toString();
            nto898u78file = "null";
            ref = FirebaseDatabase.getInstance().getReference("CategoryItems/"+existingpd.getCat_id());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    existingpd.setCat_id(ds.child("name").getValue().toString());
                    hjyt.setSelectedItem(ds.child("name").getValue().toString());
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SubCategoryItems/"+existingpd.getSub_category_id());
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot ds) {
                            existingpd.setSub_category_id(ds.child("name").getValue().toString());
                            hyt57fg.removeAllItems();
                            hyt57fg.addItem("SelectOne");
                            hyt57fg.addItem(ds.child("name").getValue().toString());
                            hyt57fg.setSelectedItem(ds.child("name").getValue().toString());
                            ksdfh345_s.setEnabled(true);
                        }

                        @Override
                        public void onCancelled(DatabaseError de) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    }); 
                    
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }); 
            if(d.child("showinapp").getValue().toString().equals("1"));
                jCheckBox1.doClick();
            break;
        }
    }
    
    private void AdrtfghKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdrtfghKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            if(!Adrtfgh.getText().trim().isEmpty()){
                try {
                    PreparedStatement ps = con.prepareStatement("select name from cartInfo where barcode ='"+Adrtfgh.getText().trim()+"'");
                    ResultSet rs = ps.executeQuery();
                    if(!rs.next()){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
                        ref.orderByChild("barcode").equalTo(Adrtfgh.getText().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot ds) {
                                    if(ds.getChildrenCount()>0){
                                        populateAddForm(ref,ds);
                                    }else{
                                        manualSave = true;
                                        hjy65449.requestFocus(); 
                                    }
                            }

                            @Override
                            public void onCancelled(DatabaseError de) {
                                JOptionPane.showMessageDialog(null, "Server Not Responding");
                            }
                        }); 
                    }else{
                        JOptionPane.showMessageDialog(null, "Product already Exist.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "null");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Enter a valid barcode");
            } 
        }
    }//GEN-LAST:event_AdrtfghKeyPressed

    private void klopdfger67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_klopdfger67MouseClicked
        // TODO add your handling code here:
        Aa.requestFocus();
    }//GEN-LAST:event_klopdfger67MouseClicked

    private void dfsh34hsdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dfsh34hsdKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            JFileChooser f = new JFileChooser();
            int v = f.showDialog(this, "Select");
            if(v == f.getApproveButtonMnemonic()){
                try {
                    nto898u78file = f.getSelectedFile().getName();
                    nto898u78file_path = f.getSelectedFile().getAbsolutePath();
                    java.io.File url = new java.io.File(f.getSelectedFile().getAbsolutePath());
                    BufferedImage image = ImageIO.read(url);
                    Image dimg = image.getScaledInstance(156, 177,Image.SCALE_SMOOTH);
                    //debug.append("\n"+"Load image into frame...");
                    this.sfdjkh12940.setIcon(new ImageIcon(dimg));
                    this.sfdjkh12940.updateUI();
                } catch (IOException ex) {
                    kk.ek("Error while readomg prod image ");
                    kk.ek("Error Details "+ex.toString());
                    JOptionPane.showMessageDialog(null, "Error while reading image try again.");
                    nto898u78file = "";
                    nto898u78file_path="";
                    sfdjkh12940.setIcon(null);
                }
            }
        }
    }//GEN-LAST:event_dfsh34hsdKeyPressed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(7+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(8+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        OrderData o = this.g3.get(9+(10*this.currentPage));
        new jkhadfkj(o,this,hjkouyth.getText()).setVisible(true);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void JKlopwrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKlopwrKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10)
            XAa.requestFocus();
    }//GEN-LAST:event_JKlopwrKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Shop").orderByChild("id").equalTo(AK90.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                if(ds.getChildrenCount() >0){
                    File fold=new File("bin/Creds/shopId.txt"); 
                    fold.delete();
                    File fnew=new File("bin/Creds/shopId.txt");
                    String source = AK90.getText();
                    hjkouyth.setText(AK90.getText());
                    try {
                        FileWriter f2 = new FileWriter(fnew, false);
                        f2.write(source);
                        f2.close();
                    }catch (IOException ex) {
                        kk.ek("ERROR While saving shop id");
                        kk.ek("ERROR Cause "+ex.getCause());
                        kk.ek("Error Details "+ ex.toString());
                    }    
                    JOptionPane.showMessageDialog(null, "Updated SuccessFuly");
                }else{
                    JOptionPane.showMessageDialog(null, "Please Enter a valid id.\nShop Doesnot exist.");
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }) ;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // TODO add your handling code here:
            PreparedStatement ps = con.prepareStatement("delete from cartInfo");
            ps.execute();
            ps.close();
            ps = con.prepareStatement("delete from ImageTable");
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            kk.ek("LOCAL DB CLEAN FAILED");
            kk.ek("ERROR Cause "+ex.getCause());
            kk.ek("ERROR Cause "+ex.getErrorCode());
            kk.ek("Error Details "+ ex.toString());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void djskfLopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_djskfLopMouseClicked
        // TODO add your handling code here:
        if(!catsaveInProgress){
            catsaveInProgress = true;
            yt65();
        }
    }//GEN-LAST:event_djskfLopMouseClicked

    private void jButton28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton28MouseClicked

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
        ref.orderByChild("shop_id").equalTo(hjkouyth.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    ref.child(dataSnapshot.getKey()).child("soldItems").setValueAsync("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }); 
    }//GEN-LAST:event_jButton28MouseClicked

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
        ref.orderByChild("shop_id").equalTo(hjkouyth.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot d : ds.getChildren()) {
                    ref.child(d.getKey()).removeValueAsync();
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        String name = skldf821.getText();
        if(!name.trim().isEmpty()){
            try {
                FileWriter f = new FileWriter("bin/creds/shopName.txt");
                f.write(name);
                f.close();
                JOptionPane.showMessageDialog(null, "Saved.");
                skldf821.setText("");
            } catch (IOException ex) {
                kk.ek("Error while updating shop Name");
                JOptionPane.showMessageDialog(null, "Error while updating shop Name");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Enter shop Name.");
        }
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        if(!manualSave){
              try {
                    URL url = new URL(existingpd.getImgURL());
                    BufferedImage image = ImageIO.read(url);
                    Image dimg = image.getScaledInstance(145,150,Image.SCALE_SMOOTH);
                    sfdjkh12940.setIcon(new ImageIcon(dimg));
                    Toolkit.getDefaultToolkit().beep();
                }catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println(existingpd.getImgURL());
                    sfdjkh12940.setIcon(new ImageIcon("bin/Icons/imgnotfound.jpg"));
                    Toolkit.getDefaultToolkit().beep();
                }
        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void hjllopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hjllopKeyReleased
        // TODO add your handling code here:
                sdklfjdksl(hjllop.getText());
    }//GEN-LAST:event_hjllopKeyReleased

    private void jButton32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton32MouseClicked
        // TODO add your handling code here:
        if(ds23.getText().isEmpty() || dd4.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Pleas Fill all fields");
        }else{
            String name = dd4.getText();
            String cont = ds23.getText();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SalesMan/"+hjkouyth.getText()+"/"+name+"-"+cont);
            ref.removeValueAsync();
            DatabaseReference refi = FirebaseDatabase.getInstance().getReference("SalesMan/"+hjkouyth.getText());
            refi.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    if(ds.getChildrenCount()>0){
                        DefaultTableModel m = (DefaultTableModel) hklop.getModel();
                        m.setRowCount(0);
                        for (DataSnapshot d : ds.getChildren() ) {
                            Object[] o = {d.child("name").getValue().toString()
                                            ,d.child("company").getValue().toString()
                                            ,d.child("contact").getValue().toString()}; 
                            m.addRow(o);
                        }
                        dd4.setText("");
                        ds23.setText("");
                        aslkol.setText("");
                        JOptionPane.showMessageDialog(null, "Removed");
                    }else{
                        JOptionPane.showMessageDialog(null, "No Salesman foound");
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }//GEN-LAST:event_jButton32MouseClicked

    private void AaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            if(!Aa.getText().isEmpty()){
                ca65.retriveProductbarcode(Aa.getText());
            }
            Aa.requestFocus();
        }else if(evt.getKeyCode() == 38){
            try {
                new Ajkdhfksj123as(con, ca65.getAllProds(),hjkouyth.getText()).setVisible(true);
            } catch (Exception ex) {
                kk.ek("CHECKOUT FAILED");
                kk.ek("ERROR Cause "+ex.getCause());
                kk.ek("Error Details "+ ex.toString());
            }
            clkhh68gj();
        }
    }//GEN-LAST:event_AaKeyPressed

    private void updateCount(JTextField cartc , int index){
        cartData item = ca65.getAllProds().get(index);
            try {
                int val = Integer.parseInt(cartc.getText());
                if(val >0){
                    String barcode = item.getBarcode();
                    PreparedStatement ps = con.prepareStatement("Select quantity from cartInfo where barcode = '"+barcode+"'");
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        int tval = rs.getInt(1);
                        if(val <= tval){
                            ca65.updateCount(val, index);
                        }else{
                            cartc.setText(""+item.getCount());
                            JOptionPane.showMessageDialog(null, "Total Count Exceded limit.\n Max items are "+tval);
                        }
                    }else{
                       cartc.setText(""+item.getCount());
                    }
                }else{
                    cartc.setText(""+item.getCount());
                }
            } catch (SQLException ex) {
                kk.ek("MANUAL UPDATE CART COUNT FAILED");
                kk.ek("ERROR Cause "+ex.getCause());
                kk.ek("ERROR CODE "+ex.getErrorCode());
                kk.ek("Error Details "+ ex.toString());
                cartc.setText(""+item.getCount());
            }catch (Exception ex) {
                cartc.setText(""+item.getCount());
            }
            
            Aa.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(hjklout678.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(hjklout678.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(hjklout678.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(hjklout678.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        initFirebase();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new hjklout678(0,"").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AGHY;
    private javax.swing.JButton AJKLOP;
    private javax.swing.JTextField AK90;
    private javax.swing.JPanel AKLJ;
    private javax.swing.JTextField Aa;
    private javax.swing.JTextField Adrtfgh;
    private javax.swing.JLabel Ajklo;
    private javax.swing.JTextField Ajklou12;
    private javax.swing.JPanel Ak;
    private javax.swing.JPanel Aklop;
    private javax.swing.JPanel Al;
    private javax.swing.JTable Al09;
    private javax.swing.JLabel Alm89;
    private javax.swing.JTextField Amklop;
    private javax.swing.JPanel Au;
    private javax.swing.JLabel DEL_KEY;
    private javax.swing.JTextField JHYULOP;
    private javax.swing.JLabel JKLOPas1234;
    private javax.swing.JTextField JKlopwr;
    private javax.swing.JTextField Jkiop90875sad;
    private javax.swing.JButton KLO87;
    private javax.swing.JTextField Nkiopgty67i;
    private javax.swing.JTextField Nmklobvg;
    private javax.swing.JPanel QAs;
    private javax.swing.JLabel TotalCount;
    private javax.swing.JTextField WWEQSD;
    private javax.swing.JTextField WWa;
    private javax.swing.JTextField Wad;
    private javax.swing.JTextField Wasdf;
    private javax.swing.JButton XAa;
    private javax.swing.JLabel aA;
    private javax.swing.JPanel aB;
    private javax.swing.JTextField aJKIy786g;
    private javax.swing.JLabel add_salesman_comp;
    private javax.swing.JLabel add_salesman_cont;
    private javax.swing.JLabel add_salesman_name;
    private javax.swing.JPanel alkop12;
    private javax.swing.JTextField asdew;
    private javax.swing.JComboBox<String> asdlJklo;
    private javax.swing.JTextField aslkol;
    private javax.swing.JTextField dd4;
    private javax.swing.JLabel del_shopid;
    private javax.swing.JTextField dfhskj123456;
    private javax.swing.JButton dfsh34hsd;
    private javax.swing.JButton djskfLop;
    private javax.swing.JPanel dlkop;
    private javax.swing.JTextField ds23;
    private javax.swing.JButton dsfklop;
    private javax.swing.JButton dsfljslKIOP;
    private javax.swing.JLabel dskjf23965fh;
    private javax.swing.JTextField dslkfjA123;
    private javax.swing.JButton fdshQo;
    private javax.swing.JLabel ftfrtrs;
    private javax.swing.JPanel gtytfhgyg;
    private javax.swing.JPanel header;
    private javax.swing.JPanel header1;
    private javax.swing.JLabel hgfdesSS;
    private javax.swing.JTextField hgh7tyyg8;
    private javax.swing.JPanel hj760;
    private javax.swing.JLabel hjgfdrdtfgyg;
    public javax.swing.JLabel hjkouyth;
    private javax.swing.JTextField hjllop;
    private javax.swing.JTextField hjy65449;
    private javax.swing.JComboBox<String> hjyt;
    private javax.swing.JTable hklop;
    private javax.swing.JComboBox<String> hyt57fg;
    private javax.swing.JLabel inv_pp1;
    private javax.swing.JLabel inv_pp10;
    private javax.swing.JLabel inv_pp2;
    private javax.swing.JLabel inv_pp3;
    private javax.swing.JLabel inv_pp4;
    private javax.swing.JLabel inv_pp5;
    private javax.swing.JLabel inv_pp6;
    private javax.swing.JLabel inv_pp7;
    private javax.swing.JLabel inv_pp8;
    private javax.swing.JLabel inv_pp9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jdsfkh123;
    private javax.swing.JPanel jdskfh132ds;
    private javax.swing.JLabel jfhrkjwe23;
    private javax.swing.JLabel jhjgfgdgghggy;
    private javax.swing.JPanel jk90;
    private javax.swing.JTextField jklOPhsklop;
    private javax.swing.JPanel jklop;
    private javax.swing.JLabel jklop0;
    private javax.swing.JLabel kjhjhghjjh979;
    private javax.swing.JPanel klo09812j;
    private javax.swing.JPanel klop7876;
    private javax.swing.JTextField klopas234;
    private javax.swing.JPanel klopdfger67;
    private javax.swing.JPanel kloup;
    private javax.swing.JButton ksdfh345_s;
    private javax.swing.JLabel lerkwe12;
    private javax.swing.JLabel o1_id;
    private javax.swing.JLabel o1_id1;
    private javax.swing.JLabel o1_id10;
    private javax.swing.JLabel o1_id11;
    private javax.swing.JLabel o1_id12;
    private javax.swing.JLabel o1_id13;
    private javax.swing.JLabel o1_id14;
    private javax.swing.JLabel o1_id15;
    private javax.swing.JLabel o1_id16;
    private javax.swing.JLabel o1_id17;
    private javax.swing.JLabel o1_id18;
    private javax.swing.JLabel o1_id19;
    private javax.swing.JLabel o1_id2;
    private javax.swing.JLabel o1_id3;
    private javax.swing.JLabel o1_id5;
    private javax.swing.JLabel o1_id6;
    private javax.swing.JLabel o1_id7;
    private javax.swing.JLabel o1_id8;
    private javax.swing.JLabel o1_id9;
    private javax.swing.JLabel o1_on10;
    private javax.swing.JLabel o1_on11;
    private javax.swing.JLabel o1_on12;
    private javax.swing.JLabel o1_on13;
    private javax.swing.JLabel o1_on14;
    private javax.swing.JLabel o1_on15;
    private javax.swing.JLabel o1_on16;
    private javax.swing.JLabel o1_on7;
    private javax.swing.JLabel o1_on8;
    private javax.swing.JLabel o1_on9;
    private javax.swing.JLabel o1_oq10;
    private javax.swing.JLabel o1_oq11;
    private javax.swing.JLabel o1_oq12;
    private javax.swing.JLabel o1_oq13;
    private javax.swing.JLabel o1_oq14;
    private javax.swing.JLabel o1_oq15;
    private javax.swing.JLabel o1_oq16;
    private javax.swing.JLabel o1_oq7;
    private javax.swing.JLabel o1_oq8;
    private javax.swing.JLabel o1_oq9;
    private javax.swing.JLabel o1_p;
    private javax.swing.JLabel o1_p1;
    private javax.swing.JLabel o1_p10;
    private javax.swing.JLabel o1_p11;
    private javax.swing.JLabel o1_p12;
    private javax.swing.JLabel o1_p13;
    private javax.swing.JLabel o1_p14;
    private javax.swing.JLabel o1_p15;
    private javax.swing.JLabel o1_p16;
    private javax.swing.JLabel o1_p17;
    private javax.swing.JLabel o1_p18;
    private javax.swing.JLabel o1_p19;
    private javax.swing.JLabel o1_p2;
    private javax.swing.JLabel o1_p3;
    private javax.swing.JLabel o1_p4;
    private javax.swing.JLabel o1_p5;
    private javax.swing.JLabel o1_p6;
    private javax.swing.JLabel o1_p7;
    private javax.swing.JLabel o1_p8;
    private javax.swing.JLabel o1_p9;
    private javax.swing.JPanel o_u10;
    private javax.swing.JPanel o_u11;
    private javax.swing.JPanel o_u12;
    private javax.swing.JPanel o_u13;
    private javax.swing.JPanel o_u14;
    private javax.swing.JPanel o_u15;
    private javax.swing.JPanel o_u16;
    private javax.swing.JPanel o_u17;
    private javax.swing.JPanel o_u5;
    private javax.swing.JPanel o_u8;
    private javax.swing.JPanel o_u9;
    private javax.swing.JLabel ohgft;
    private javax.swing.JLabel prod_sort_icon;
    private javax.swing.JComboBox<String> re4w35e;
    private javax.swing.JLabel rtee4w56;
    private javax.swing.JLabel rtrexxgfd88;
    private javax.swing.JLabel sawdwaQEW;
    private javax.swing.JTextField sd9;
    private javax.swing.JTextField sdfh129sd;
    private javax.swing.JLabel sdfhk89076;
    private javax.swing.JPanel sdfhyiuwae348628sdh;
    private javax.swing.JLabel sdfjho90;
    private javax.swing.JPanel sdfjkhl12jh4;
    private javax.swing.JComboBox<String> sdjlop;
    private javax.swing.JLabel search_order_icon;
    private javax.swing.JLabel search_order_icon1;
    private javax.swing.JLabel sfdjkh12940;
    private javax.swing.JTextField skldf821;
    private javax.swing.JLabel tree4wet6e;
    private javax.swing.JLabel tyr56eytr6rdfdy;
    private javax.swing.JLabel uuhhhu767tygu;
    private javax.swing.JPanel vbd;
    private javax.swing.JPanel weiuyr78GHJ;
    private javax.swing.JTextField xdfj12;
    private javax.swing.JLabel ytyfytrf;
    private javax.swing.JPanel ytyg;
    private javax.swing.JLabel ytyrtrdfy;
    private javax.swing.JLabel yuiwe213sae;
    private javax.swing.JPanel yutuytfjhgy;
    private javax.swing.JLabel yuyftuyg7657g;
    // End of variables declaration//GEN-END:variables

}
