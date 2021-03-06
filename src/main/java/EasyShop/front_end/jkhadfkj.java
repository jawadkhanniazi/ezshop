/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop.front_end;

import EasyShop.util.OrderData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kaka
 */
public class jkhadfkj extends javax.swing.JFrame {
    private OrderData order;
    hjklout678 p;
    
    private int xMouse = 0;
    
    private int yMouse = 0;
    
    private String sid;
    /**
     * Creates new form OrderInfo
     */
    public jkhadfkj() {
        this.setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(this);
    }
    
    @SuppressWarnings("empty-statement")
    public jkhadfkj(OrderData od, hjklout678 pn, String sid) {
        this.sid=sid;
        p = pn;
        this.order = od;
        setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(this);
        jkh76dfg.setText(od.getOrderId());
        DefaultTableModel model = (DefaultTableModel) hjgfjh.getModel();
        model.setRowCount(0);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
        ref.orderByChild("shop_id").equalTo(sid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                Object[] o = {od.getProductname(),ds.child(od.pid+"/companyName").getValue().toString(), od.getQuantity(), od.getPrice(), ds.child(od.pid+"/size").getValue().toString()};
                model.addRow(o);
                for (OrderData no : od.nextOrders) {
                   Object[] o1 = {no.getProductname(),ds.child(no.pid+"/companyName").getValue().toString(), no.getQuantity(), no.getPrice(), ds.child(no.pid+"/size").getValue().toString()};
                   model.addRow(o1);
        }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        exit = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        hjgfjh = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jkh76dfg = new javax.swing.JLabel();
        jhgjghjg = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

        exit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        exit.setForeground(new java.awt.Color(255, 255, 255));
        exit.setText("X");
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        hjgfjh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Product Name", "Comp.", "Quantity", "Price", "Size"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        hjgfjh.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(hjgfjh);
        if (hjgfjh.getColumnModel().getColumnCount() > 0) {
            hjgfjh.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Order Information");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Order ID");

        jkh76dfg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jhgjghjg.setText("Mark Complete");
        jhgjghjg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jhgjghjgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jkh76dfg, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jhgjghjg, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jkh76dfg, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jhgjghjg)
                .addGap(0, 69, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void moveToRider(OrderData o){
        DatabaseReference reforder = FirebaseDatabase.getInstance().getReference("Order/"+o.shopid+"/Products");
        DatabaseReference refrider = FirebaseDatabase.getInstance().getReference("OrderBiker/");
        reforder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                gio908(o);
                for(int i =0;i<o.orderIDs.size(); i++){
                    refrider.child(o.shopid+"/Products/"+o.orderIDs.get(i)).setValueAsync(ds.child(o.orderIDs.get(i)).getValue());
                    reforder.child(o.orderIDs.get(i)+"/ordercomplete").setValueAsync("1");
                    ju887(i,reforder,o);
                }
                int index = p.getOnlineOrders().indexOf(order);
                p.getOnlineOrders().remove(index);
                p.reDraw();
                JOptionPane.showMessageDialog(null, "Moved to rider");  
                exit();
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        }); 
    }
    
        private void gio908(OrderData o){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
        ref.orderByChild("shop_id").equalTo(p.hjkouyth.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
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

    
    private void exit(){
        this.dispose();
    }
    
    private void ju887(int val, DatabaseReference reforder,OrderData o ){
        DatabaseReference refBiker = FirebaseDatabase.getInstance().getReference("Biker/");
        refBiker.orderByChild("shopId").equalTo(o.shopid)
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

                                } catch (Exception ex) {
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
    
    private void jhgjghjgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jhgjghjgActionPerformed
        moveToRider(order);
    }//GEN-LAST:event_jhgjghjgActionPerformed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xMouse, y-yMouse);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2MouseClicked

    private void exitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseEntered
        // TODO add your handling code here:
        exit.setForeground(Color.red);
    }//GEN-LAST:event_exitMouseEntered

    private void exitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseExited
        // TODO add your handling code here:
        exit.setForeground(Color.WHITE);
    }//GEN-LAST:event_exitMouseExited

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_exitMouseClicked

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel exit;
    private javax.swing.JTable hjgfjh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jhgjghjg;
    private javax.swing.JLabel jkh76dfg;
    // End of variables declaration//GEN-END:variables
}
