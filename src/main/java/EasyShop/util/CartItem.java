/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop.util;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError; 
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CartItem {
    
    private JTextField f;
    private JLabel tlbl;
    private JLabel tCount;
    private JPanel pnl;
    private JButton btn;
    private Connection con ;
    private int panelcoint = 0;
   
    private LinkedList<cartData> allProducts = new LinkedList<cartData>();
    private LinkedList<String> allBarcodes = new LinkedList<>();
    private LinkedList<NewPanel> catritemsList = new LinkedList();
    
    public CartItem(Connection c, JTextField f ,JPanel p, JButton b, JLabel tlbl, JLabel tCount) {
        btn = b;
        con = c;
        pnl =p;
        this.f = f;
        this.tlbl =tlbl;
        this.tCount = tCount;
    }
    
    
    
    public LinkedList<cartData> getAllProds(){
        return allProducts;
    }
    
    public void clearAllItems(){
        pnl.removeAll();
        pnl.add(f);
        pnl.add(btn);
        pnl.add(tlbl);
        tCount.setText("0");
        pnl.add(tCount);
        pnl.repaint();
        allBarcodes.clear();
        catritemsList.clear();
        allProducts.clear();
        panelcoint = 0;
    }
    
    public void updateCount(int val, int index){
        cartData d = allProducts.get(index);
        d.setCount(val);
        allProducts.remove(index);
        allProducts.add(index, d);
        
    }
    
//----------------------------------------- Cart View ---------------------------------------------------------------------

    private class NewPanel extends JPanel{
        JLabel name;
        JLabel icon;
        JLabel price;
        JLabel x;
        JTextField quant;
        int index;

        public NewPanel(String pname,String pPrice, String pquannt, String image, int index) {
            
            this.index = index;
            setLayout(new GridBagLayout());
            setBounds(getXLoc(), getYLoc(), 200, 200);
            
            x     = new JLabel("X");
            icon  = new JLabel();
            name  = new JLabel(pname, SwingConstants.CENTER);
            price  = new JLabel(pname, SwingConstants.CENTER);
            price.setText(pPrice);
            quant = new JTextField(pquannt);
            quant.setHorizontalAlignment(SwingConstants.CENTER);
            quant.setBorder(null);
            icon.setIcon(new ImageIcon("bin/icons/"+image));
            setActionListners();
            setCompBound();
            addToView();
            
        }
        
        public void updateIndex(int index){
            this.index = index;
            this.setBounds(getXLoc(), getYLoc(), 200, 200);
        }
        
        private void setActionListners(){
            x.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object[] options = {"Reduce by 1", "Remove All", "Cancle"};
                    int n = JOptionPane.showOptionDialog(null,
                        "Remove item from cart ?",
                        "",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);
                    if(n != 2){
                        removeFromCart(index);
                        updateTotal();
                    }
                }
            }); 
            quant.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e){
                        if(e.getKeyCode()==10){
                            String val = quant.getText();
                            int v = Integer.parseInt(val);
                            int tv = allProducts.get(index).totalQuant;
                            if(v<=tv && v >0){
                                allProducts.get(index).count=v;
                            }else{
                                quant.setText(""+allProducts.get(index).count);
                                JOptionPane.showMessageDialog(null, "Max Products are "+tv);
                            }
                            updateTotal();
                            f.setText("");
                            f.requestFocus();
                        }                        
                    }
            });
            
        }
        
        private void updateTotal(){
            int total = 0;
            for (cartData allProduct : allProducts) {
                total += allProduct.getCount()*Integer.parseInt(allProduct.getUnitPrice());
            }
            tCount.setText(""+ total);
        }
        
        private void setCompBound(){
            x.setForeground(Color.red);
            name.setFont(new Font(Font.SERIF, Font.PLAIN,  14));
            icon.setBounds (0, 0, 150,  150);
            x.setBounds    (0, 10, 10,   10);
            name.setBounds (0, 151, 200, 20);
            price.setBounds(0, 172, 200, 20);
            quant.setBounds(0, 192, 200, 20);
        }
        
        private void addToView(){
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            this.add(icon, gbc);
            gbc.insets = new Insets(-15, 10, 100, 10);
            gbc.gridx = 1;
            gbc.gridy = 0;
            this.add(x, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 0, 0);
            this.add(name, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            this.add(price, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            this.add(quant, gbc);
            this.setBackground(Color.WHITE);
            this.setVisible(true);
        }
        
        private int getYLoc(){
            int offset = index/5;
            int h = 200*offset;
            return 190*offset;
        }
        
        private int getXLoc(){
            int offset = index%5;
            return (200*offset);
        }
    }   
    
//-------------------------------------------- Scan & Show -----------------------------------------------------------------
    public void retriveProductbarcode(String barcode){
        int val = checkExisting(barcode);
        if(val == -1){
            try {
                    int count;
                    PreparedStatement ps = this.con.prepareStatement("SELECT barcode,price,name,quantity"
                            + " FROM cartInfo WHERE barcode ='" + barcode+"'");
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        count = rs.getInt(4);
                        if( count > 0){
                            addItem(rs);
                        }else{
                            JOptionPane.showMessageDialog(null, "No more Products Available");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Product not found Add to inventory first");
                    }
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error while adding to the cart.\nRestart the app.");
                     System.out.println("ERROR while adding item to cart");
                     System.out.println("Error code "+ex.getErrorCode());
                     System.out.println("Error cause "+ex.getCause());
                     System.out.println("Error detatis "+ex.toString());
                }
        }else{
            int v = allProducts.get(val).count;
            int tv = allProducts.get(val).totalQuant;
            if(v<tv){
                allProducts.get(val).count++;
                catritemsList.get(val).quant.setText(""+allProducts.get(val).count);
                tCount.setText(""+(Integer.parseInt(tCount.getText())+Integer.parseInt(allProducts.get(val).getUnitPrice())));
            }else{
                JOptionPane.showMessageDialog(null, "No more product Exists");
            }
        }
        f.setText("");
        f.requestFocus();
    }
    
    private int checkExisting(String barcode){
        return allBarcodes.indexOf(barcode);
    }    
    
    
    public void addItem(ResultSet rs){
        try {
            NewPanel p = new NewPanel(rs.getString(3),rs.getString(2),""+1,"imgnotfound.jpg",panelcoint);
            allProducts.add(new cartData( rs.getString(2), rs.getString(3), 1, rs.getString(1),rs.getInt(4)));
            panelcoint++;
            tCount.setText(""+(Integer.parseInt(tCount.getText())+rs.getInt(2)));
            pnl.add(p);
            pnl.updateUI();
            catritemsList.add(p);
            allBarcodes.add(rs.getString(1));
        } catch (SQLException ex) {
            Logger.getLogger(CartItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateViewOnline(String barcode, JLabel name,JTextField count, JLabel img,JLabel x){
        cartData data = new cartData();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("ProductsItems");
        ref.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot ds) {
                for(DataSnapshot d : ds.getChildren()){
                    if(barcode.toLowerCase().equals(d.child("barcode").getValue().toString().toLowerCase())){
                       data.barcode = barcode;
                       data.name = d.child("name").getValue().toString();
                       name.setText(d.child("name").getValue().toString());
                       name.setVisible(true);
                       data.count = 1;
                       count.setText("1");
                       count.setVisible(true);
                       x.setVisible(true);
                       retriveCatImage(d.child("cat_id").getValue().toString(),img,x, data);
                       break;
                    }
                }
                allProducts.add(data);
                
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        });
    }
    
    private void retriveCatImage(String ID, JLabel img, JLabel x, cartData data){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("CategoryItems/"+ID);
        ref.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot ds) {
                try {
                    URL url = new URL(ds.child("imgURL").getValue().toString());
                    BufferedImage image = ImageIO.read(url);
                    Image dimg = image.getScaledInstance(145,150,Image.SCALE_SMOOTH);
                    allProducts.add(data);
                    img.setIcon(new ImageIcon(dimg));
                    img.setVisible(true);
                    x.setVisible(true);
                    f.setText("");
                    Toolkit.getDefaultToolkit().beep();
                }catch (MalformedURLException ex) {
                } catch (IOException ex) {
                    img.setIcon(new ImageIcon("bin/Icons/sale.png"));
                    img.setVisible(true);
                    x.setVisible(true);
                    f.setText("");
                    Toolkit.getDefaultToolkit().beep();
                }catch(Exception e){
                }
                
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        });
    }
 //------------------------------------------- Remove Item --------------------------------------------------------------------
    
    private void removeFromCart(int index){
        catritemsList.remove(index);
        allBarcodes.remove(index);
        allProducts.remove(index);
        pnl.remove(index+4);
        for (int i = index; i < catritemsList.size(); i++) {
            NewPanel p = catritemsList.get(i);
            p.updateIndex(i);
        }
        panelcoint--;
        pnl.repaint();
    }
    
//------------------------------------------ Update database ------------------------------------------------------------------
 
    public void downloadProducts(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("ProductsItems").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                int i =0;
                for(DataSnapshot d : ds.getChildren()){
                    try {
                        ProductData pd = new ProductData(d);
                        PreparedStatement p = con.prepareStatement("insert or replace into cartInfo values('"+pd.getBarcode()+
                        "','"+pd.getSalesprice()+"','"+pd.getPrice()+"','"+pd.getName()+"','"
                        +pd.getCat_id()+"',"+pd.getQuantity()+",'"+pd.getId()+"')");
                        p.execute();
                        p.close();
                        i++;
                    } catch (SQLException ex) {
                        System.out.println("Error while downloading Product Details");
                        System.out.println("Error Code "+ex.getErrorCode());
                        System.out.println("Error Details "+ex.toString());
                    }
                }JOptionPane.showMessageDialog(null, "All Products Downloaded");
                
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        });
        
    }
    
    public void downloadCats(){
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
        ref1.child("CategoryItems").addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        for(DataSnapshot d : ds.getChildren()){
                            try {
                                String img = d.child("imgid").getValue().toString();
                                String cat = d.getKey().toString();
                                String sql = "insert OR replace into imageTable VALUES('"+img+"','"+cat+"');";
                                PreparedStatement ps = con.prepareStatement(sql); 
                                ps.execute();
                                ps.close();
                                URL url = new URL(d.child("imgURL").getValue().toString());
                                BufferedImage image = ImageIO.read(url);
                                Image dimg = image.getScaledInstance(145,150,Image.SCALE_SMOOTH);
                                String extension = "";
                                int i = img.lastIndexOf('.');
                                if (i > 0) {
                                    extension = img.substring(i+1);
                                }
                                ImageIO.write(toBufferedImage(dimg), extension, new File("bin/"+d.child("imgid").getValue().toString()));
                            } catch (SQLException ex) {
                                System.out.println("Download localDB Cat Exceptionn code = "+ex.getErrorCode());
                                System.out.println("Cause = "+ex.getCause());
                                System.out.println(ex.toString());
                                System.out.println(ex.getSQLState());
                            } catch (MalformedURLException ex) {
                                System.out.println("Download localDB Error: URL Fault");
                                System.out.println(ex.getCause());
                            } catch (IOException ex) {
                                System.out.println("Cart Error: IO EX");
                                System.out.println(ex.getCause());
                                System.out.println(ex.toString());
                            }
                        }JOptionPane.showMessageDialog(null, "All Images Downloaded");
                    }
                    @Override
                    public void onCancelled(DatabaseError de) {
                        JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
                    }
                } );
    }
    
    private BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
            return (BufferedImage) img;
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
}