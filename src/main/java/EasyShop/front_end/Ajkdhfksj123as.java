/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyShop.front_end;

import EasyShop.util.PrintFormat;
import EasyShop.util.PrintBill;
import EasyShop.util.cartData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Fawad
 */
public class Ajkdhfksj123as extends javax.swing.JFrame {

    /**
     * Creates new form Ajkdhfksj123as
     */
    private LinkedList<cartData> det = new LinkedList<>();
    private void init(){
        dsfjhkds.setVisible(false);
        dsfsjkd.setVisible(false);
        sdfhskjdh23.setVisible(false);
        akjh12hgc.setVisible(false);
        akjhds12.setVisible(false);
        n67sdf.setVisible(false);
        jskad12.setVisible(false);
        asdj12cx.setVisible(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void jhty56fc(int index,  cartData val){
        int price = 0;
        switch(index){
            case 0:
                pn1.setText(val.getName());
                q1.setText(val.getCount()+"x"+val.getUnitPrice());
                price = val.getCount()*Integer.parseInt(val.getUnitPrice());
                t1.setText(""+price);
                dsfjhkds.setVisible(true);
                break;
            case 1:
                pn2.setText(val.getName());
                q2.setText(val.getCount()+"x"+val.getUnitPrice());
                price = val.getCount()*Integer.parseInt(val.getUnitPrice());
                t2.setText(""+price);
                dsfsjkd.setVisible(true);
                break;
            case 2:
                pn3.setText(val.getName());
                q3.setText(val.getCount()+"x"+val.getUnitPrice());
                price = val.getCount()*Integer.parseInt(val.getUnitPrice());
                t3.setText(""+price);
                sdfhskjdh23.setVisible(true);
                break;
            case 3:
                pn4.setText(val.getName());
                q4.setText(val.getCount()+"x"+val.getUnitPrice());
                price = val.getCount()*Integer.parseInt(val.getUnitPrice());
                t4.setText(""+price);
                akjh12hgc.setVisible(true);
                break;
            case 4:
                pn5.setText(val.getName());
                q5.setText(val.getCount()+"x"+val.getUnitPrice());
                price = val.getCount()*Integer.parseInt(val.getUnitPrice());
                t5.setText(""+price);
                akjhds12.setVisible(true);
                break;
            case 5:
                pn6.setText(val.getName());
                q6.setText(val.getCount()+"x"+val.getUnitPrice());
                price = val.getCount()*Integer.parseInt(val.getUnitPrice());
                t6.setText(""+price);
                n67sdf.setVisible(true);
                break;
            case 6:
                pn7.setText(val.getName());
                q7.setText(val.getCount()+"x"+val.getUnitPrice());
                price = val.getCount()*Integer.parseInt(val.getUnitPrice());
                t7.setText(""+price);
                jskad12.setVisible(true);
                break;
            case 7:
                pn8.setText(val.getName());
                q8.setText(val.getCount()+"x"+val.getUnitPrice());
                price = val.getCount()*Integer.parseInt(val.getUnitPrice());
                t8.setText(""+price);
                asdj12cx.setVisible(true);
                break;
        }
        price = Integer.parseInt(Gtotal.getText())+ (val.getCount()*Integer.parseInt(val.getUnitPrice()));
        Gtotal.setText(""+price);
    }
    private Connection con;
    private String shopId;
    private int xMouse = 0;
    private int yMouse = 0;
    public Ajkdhfksj123as(Connection c, LinkedList<cartData> l, String shopId) {
        con = c;
        this.shopId =shopId;
        for (cartData data : l) {
            this.det.add(data);
        }
 
        this.setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(null);
        init();
        for(int i = 0; i <l.size();i++){
            jhty56fc(i,l.get(i));
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jkdsfh23jk = new javax.swing.JPanel();
        djkfsh3e = new javax.swing.JPanel();
        X = new javax.swing.JLabel();
        jsdfh342kjsd = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dsfjhkds = new javax.swing.JPanel();
        pn1 = new javax.swing.JLabel();
        q1 = new javax.swing.JLabel();
        t1 = new javax.swing.JLabel();
        dsfsjkd = new javax.swing.JPanel();
        pn2 = new javax.swing.JLabel();
        q2 = new javax.swing.JLabel();
        t2 = new javax.swing.JLabel();
        sdfhskjdh23 = new javax.swing.JPanel();
        pn3 = new javax.swing.JLabel();
        q3 = new javax.swing.JLabel();
        t3 = new javax.swing.JLabel();
        akjh12hgc = new javax.swing.JPanel();
        pn4 = new javax.swing.JLabel();
        q4 = new javax.swing.JLabel();
        t4 = new javax.swing.JLabel();
        akjhds12 = new javax.swing.JPanel();
        pn5 = new javax.swing.JLabel();
        q5 = new javax.swing.JLabel();
        t5 = new javax.swing.JLabel();
        n67sdf = new javax.swing.JPanel();
        pn6 = new javax.swing.JLabel();
        q6 = new javax.swing.JLabel();
        t6 = new javax.swing.JLabel();
        jskad12 = new javax.swing.JPanel();
        pn7 = new javax.swing.JLabel();
        q7 = new javax.swing.JLabel();
        t7 = new javax.swing.JLabel();
        asdj12cx = new javax.swing.JPanel();
        pn8 = new javax.swing.JLabel();
        q8 = new javax.swing.JLabel();
        t8 = new javax.swing.JLabel();
        jklrte = new javax.swing.JPanel();
        djskfhs12jkhkds = new javax.swing.JButton();
        jskfdh = new javax.swing.JButton();
        jkdfhs = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        w64ash = new javax.swing.JTextField();
        Gtotal = new javax.swing.JLabel();
        jskhd12adsf = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jkdsfh23jk.setBackground(new java.awt.Color(255, 255, 255));
        jkdsfh23jk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        djkfsh3e.setBackground(new java.awt.Color(0, 0, 0));
        djkfsh3e.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                djkfsh3eMouseDragged(evt);
            }
        });
        djkfsh3e.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                djkfsh3eMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                djkfsh3eMousePressed(evt);
            }
        });

        X.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        X.setForeground(new java.awt.Color(255, 255, 255));
        X.setText("X");
        X.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                XMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                XMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                XMouseExited(evt);
            }
        });

        javax.swing.GroupLayout djkfsh3eLayout = new javax.swing.GroupLayout(djkfsh3e);
        djkfsh3e.setLayout(djkfsh3eLayout);
        djkfsh3eLayout.setHorizontalGroup(
            djkfsh3eLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, djkfsh3eLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(X)
                .addContainerGap())
        );
        djkfsh3eLayout.setVerticalGroup(
            djkfsh3eLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(djkfsh3eLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(X)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Product Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Quantity");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Total");

        javax.swing.GroupLayout jsdfh342kjsdLayout = new javax.swing.GroupLayout(jsdfh342kjsd);
        jsdfh342kjsd.setLayout(jsdfh342kjsdLayout);
        jsdfh342kjsdLayout.setHorizontalGroup(
            jsdfh342kjsdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jsdfh342kjsdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(70, 70, 70)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jsdfh342kjsdLayout.setVerticalGroup(
            jsdfh342kjsdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jsdfh342kjsdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jsdfh342kjsdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn1.setText("jLabel4");

        q1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q1.setText("jLabel5");

        t1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t1.setText("jLabel6");

        javax.swing.GroupLayout dsfjhkdsLayout = new javax.swing.GroupLayout(dsfjhkds);
        dsfjhkds.setLayout(dsfjhkdsLayout);
        dsfjhkdsLayout.setHorizontalGroup(
            dsfjhkdsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsfjhkdsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(q1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        dsfjhkdsLayout.setVerticalGroup(
            dsfjhkdsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsfjhkdsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dsfjhkdsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dsfjhkdsLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(q1, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addComponent(pn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn2.setText("jLabel4");

        q2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q2.setText("jLabel5");

        t2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t2.setText("jLabel6");

        javax.swing.GroupLayout dsfsjkdLayout = new javax.swing.GroupLayout(dsfsjkd);
        dsfsjkd.setLayout(dsfsjkdLayout);
        dsfsjkdLayout.setHorizontalGroup(
            dsfsjkdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsfsjkdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(q2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        dsfsjkdLayout.setVerticalGroup(
            dsfsjkdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsfsjkdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dsfsjkdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dsfsjkdLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(q2, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addComponent(pn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn3.setText("jLabel4");

        q3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q3.setText("jLabel5");

        t3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t3.setText("jLabel6");

        javax.swing.GroupLayout sdfhskjdh23Layout = new javax.swing.GroupLayout(sdfhskjdh23);
        sdfhskjdh23.setLayout(sdfhskjdh23Layout);
        sdfhskjdh23Layout.setHorizontalGroup(
            sdfhskjdh23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sdfhskjdh23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(q3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        sdfhskjdh23Layout.setVerticalGroup(
            sdfhskjdh23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sdfhskjdh23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sdfhskjdh23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sdfhskjdh23Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(q3, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addComponent(pn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn4.setText("jLabel4");

        q4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q4.setText("jLabel5");

        t4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t4.setText("jLabel6");

        javax.swing.GroupLayout akjh12hgcLayout = new javax.swing.GroupLayout(akjh12hgc);
        akjh12hgc.setLayout(akjh12hgcLayout);
        akjh12hgcLayout.setHorizontalGroup(
            akjh12hgcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(akjh12hgcLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(q4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        akjh12hgcLayout.setVerticalGroup(
            akjh12hgcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(akjh12hgcLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(akjh12hgcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(akjh12hgcLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(q4, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addComponent(pn4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn5.setText("jLabel4");

        q5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q5.setText("jLabel5");

        t5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t5.setText("jLabel6");

        javax.swing.GroupLayout akjhds12Layout = new javax.swing.GroupLayout(akjhds12);
        akjhds12.setLayout(akjhds12Layout);
        akjhds12Layout.setHorizontalGroup(
            akjhds12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(akjhds12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(q5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        akjhds12Layout.setVerticalGroup(
            akjhds12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(akjhds12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(akjhds12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(akjhds12Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(q5, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addComponent(pn5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn6.setText("jLabel4");

        q6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q6.setText("jLabel5");

        t6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t6.setText("jLabel6");

        javax.swing.GroupLayout n67sdfLayout = new javax.swing.GroupLayout(n67sdf);
        n67sdf.setLayout(n67sdfLayout);
        n67sdfLayout.setHorizontalGroup(
            n67sdfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(n67sdfLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(q6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        n67sdfLayout.setVerticalGroup(
            n67sdfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(n67sdfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(n67sdfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(n67sdfLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(q6, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addComponent(pn6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn7.setText("jLabel4");

        q7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q7.setText("jLabel5");

        t7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t7.setText("jLabel6");

        javax.swing.GroupLayout jskad12Layout = new javax.swing.GroupLayout(jskad12);
        jskad12.setLayout(jskad12Layout);
        jskad12Layout.setHorizontalGroup(
            jskad12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jskad12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(q7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jskad12Layout.setVerticalGroup(
            jskad12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jskad12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jskad12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jskad12Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(q7, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addComponent(pn7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn8.setText("jLabel4");

        q8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q8.setText("jLabel5");

        t8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t8.setText("jLabel6");

        javax.swing.GroupLayout asdj12cxLayout = new javax.swing.GroupLayout(asdj12cx);
        asdj12cx.setLayout(asdj12cxLayout);
        asdj12cxLayout.setHorizontalGroup(
            asdj12cxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(asdj12cxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(q8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        asdj12cxLayout.setVerticalGroup(
            asdj12cxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(asdj12cxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(asdj12cxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(asdj12cxLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(q8, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                    .addComponent(pn8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jklrte.setBackground(new java.awt.Color(255, 255, 255));

        djskfhs12jkhkds.setText("Print Bill");
        djskfhs12jkhkds.setNextFocusableComponent(jskfdh);
        djskfhs12jkhkds.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                djskfhs12jkhkdsMouseClicked(evt);
            }
        });
        djskfhs12jkhkds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                djskfhs12jkhkdsKeyPressed(evt);
            }
        });

        jskfdh.setText("Loan");
        jskfdh.setNextFocusableComponent(jkdfhs);
        jskfdh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jskfdhMouseClicked(evt);
            }
        });
        jskfdh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jskfdhKeyPressed(evt);
            }
        });

        jkdfhs.setText("Ok");
        jkdfhs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jkdfhsMouseClicked(evt);
            }
        });
        jkdfhs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jkdfhsKeyPressed(evt);
            }
        });

        jLabel4.setText("Net Ammount");

        w64ash.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        w64ash.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        w64ash.setNextFocusableComponent(djskfhs12jkhkds);
        w64ash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                w64ashKeyPressed(evt);
            }
        });

        Gtotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Gtotal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Gtotal.setText("0");

        jskhd12adsf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jskhd12adsf.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jklrteLayout = new javax.swing.GroupLayout(jklrte);
        jklrte.setLayout(jklrteLayout);
        jklrteLayout.setHorizontalGroup(
            jklrteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jklrteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jskfdh, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jkdfhs, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(djskfhs12jkhkds, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jklrteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(w64ash, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jskhd12adsf, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(Gtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jklrteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jklrteLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel4)
                    .addContainerGap(292, Short.MAX_VALUE)))
        );
        jklrteLayout.setVerticalGroup(
            jklrteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jklrteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jklrteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jklrteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(w64ash, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                        .addComponent(jskhd12adsf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(Gtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jklrteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(djskfhs12jkhkds)
                    .addComponent(jskfdh)
                    .addComponent(jkdfhs))
                .addContainerGap())
            .addGroup(jklrteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jklrteLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addGap(55, 55, 55)))
        );

        javax.swing.GroupLayout jkdsfh23jkLayout = new javax.swing.GroupLayout(jkdsfh23jk);
        jkdsfh23jk.setLayout(jkdsfh23jkLayout);
        jkdsfh23jkLayout.setHorizontalGroup(
            jkdsfh23jkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(djkfsh3e, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jkdsfh23jkLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jkdsfh23jkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dsfjhkds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jsdfh342kjsd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dsfsjkd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sdfhskjdh23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(akjh12hgc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(akjhds12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(n67sdf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jskad12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(asdj12cx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jklrte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jkdsfh23jkLayout.setVerticalGroup(
            jkdsfh23jkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jkdsfh23jkLayout.createSequentialGroup()
                .addComponent(djkfsh3e, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jsdfh342kjsd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dsfjhkds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dsfsjkd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sdfhskjdh23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(akjh12hgc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(akjhds12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(n67sdf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jskad12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(asdj12cx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jklrte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jkdsfh23jk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jkdsfh23jk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jkdfhsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jkdfhsMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jkdfhsMouseClicked

    private void jskfdhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jskfdhMouseClicked
        // TODO add your handling code here:
        wer43qty56frt34();
        new afqr0(shopId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jskfdhMouseClicked

    private void XMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XMouseExited
        // TODO add your handling code here:
        X.setForeground(Color.WHITE);
    }//GEN-LAST:event_XMouseExited

    private void XMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XMouseEntered
        // TODO add your handling code here:
        X.setForeground(Color.red);
    }//GEN-LAST:event_XMouseEntered

    private void XMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_XMouseClicked

    private void djskfhs12jkhkdsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_djskfhs12jkhkdsMouseClicked
        jhty56frt34();
        this.dispose();
    }//GEN-LAST:event_djskfhs12jkhkdsMouseClicked
    
    private void jhty56frt34(){
        String amount = w64ash.getText();
        if(!amount.isEmpty()){
            int a = Integer.parseInt(amount);
            int gt = Integer.parseInt(Gtotal.getText());
            if(a>=gt){
                PrinterJob pj = PrinterJob.getPrinterJob();        
//                pj.setPrintable(new PrintBill(det,a,gt),new PrintFormat(10.0).getPageFormat(pj));
                  pj.setPrintable(new PrintBill(det,a,gt),new PrintFormat(det.size()*0.179).getPageFormat(pj));

                try {
                     pj.print();
                     wer43qty56frt34(); 
                }
                 catch (PrinterException ex) {
                     JOptionPane.showMessageDialog(this, "Printer out of Service");
                     ex.printStackTrace();
                }
            }else
                JOptionPane.showMessageDialog(this, "Please Correct Balence amount");
        }else{
            JOptionPane.showMessageDialog(this, "Please Enter Balence amount");
        }
    }
    
    private void djskfhs12jkhkdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_djskfhs12jkhkdsKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case 10:
                jhty56frt34();
                this.dispose();
                break;
            case 39:
                jskfdh.requestFocus();
                break;
            case 37:
                jkdfhs.requestFocus();
                break;
        }
    }//GEN-LAST:event_djskfhs12jkhkdsKeyPressed

    private void jskfdhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jskfdhKeyPressed
        // TODO add your handling code here:
        
        switch (evt.getKeyCode()) {
            case 10:
                wer43qty56frt34();
                new afqr0(shopId).setVisible(true);
                this.dispose();
                break;
            case 39:
                jkdfhs.requestFocus();
                break;
            case 37:
                djskfhs12jkhkds.requestFocus();
                break;
        }
    }//GEN-LAST:event_jskfdhKeyPressed

    private void jkdfhsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jkdfhsKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case 10:
                wer43qty56frt34();
                this.dispose();
                break;
            case 39:
                djskfhs12jkhkds.requestFocus();
                break;
            case 37:
                jskfdh.requestFocus();
                break;
        }
    }//GEN-LAST:event_jkdfhsKeyPressed

    private void w64ashKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_w64ashKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            try{
                int amt1 = Integer.parseInt(w64ash.getText());
                int amt2 = Integer.parseInt(Gtotal.getText());
                if(amt1-amt2 >=0){
                    jskhd12adsf.setText(""+(amt1-amt2));
                    jkdfhs.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(null, "Please Enter correct ammount");
                    w64ash.setText("");
                    w64ash.requestFocus();
                }
            }catch(HeadlessException | NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Please Enter correct ammount");
                w64ash.setText("");
                w64ash.requestFocus();
            }
        }
    }//GEN-LAST:event_w64ashKeyPressed

    private void djkfsh3eMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_djkfsh3eMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_djkfsh3eMouseClicked

    private void djkfsh3eMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_djkfsh3eMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xMouse, y-yMouse);
    }//GEN-LAST:event_djkfsh3eMouseDragged

    private void djkfsh3eMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_djkfsh3eMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_djkfsh3eMousePressed
    
    private void wer43qty56frt34(){
        try {
            for (cartData data : det) {
                PreparedStatement p = con.prepareStatement("Select quantity from cartInfo where barcode = '"+data.getBarcode()+"'");
                ResultSet r = p.executeQuery();
                int val = r.getInt(1);
                p.close();
                p = con.prepareStatement("Update cartInfo set quantity = '"+(val-data.getCount())+"' where barcode = '"+data.getBarcode()+"'");
                p.executeUpdate();            
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ajkdhfksj123as.class.getName()).log(Level.SEVERE, null, ex);
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ProductsItems");
        ref.orderByChild("shop_id").equalTo(shopId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                DatabaseReference refi = FirebaseDatabase.getInstance().getReference("ProductsItems");
                LinkedList<String> allName = new LinkedList<>();
                for (int i = 0; i < det.size(); i++)
                    allName.add(det.get(i).getBarcode().toLowerCase());
                for (DataSnapshot d : ds.getChildren()) {
                    if(d.hasChild("salesprice")){
                            String name = d.child("barcode").getValue().toString();
                            int i = allName.indexOf(name.toLowerCase());
                            if(i >=0){
                                allName.remove(i);
                               int csquant = 0;
                               if(d.hasChild("soldItems"))
                                   csquant = Integer.parseInt(d.child("soldItems").getValue().toString());
                               int nsquant = det.get(i).getCount();
                               int aquant = Integer.parseInt(d.child("quantity").getValue().toString());
                               refi.child(d.getKey()+"/soldItems").setValueAsync(csquant+nsquant);
                               refi.child(d.getKey()+"/quantity").setValueAsync(aquant-nsquant);
                            }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                JOptionPane.showMessageDialog(null, "Server not responding.\nTry again");
            }
        });
                
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Gtotal;
    private javax.swing.JLabel X;
    private javax.swing.JPanel akjh12hgc;
    private javax.swing.JPanel akjhds12;
    private javax.swing.JPanel asdj12cx;
    private javax.swing.JPanel djkfsh3e;
    private javax.swing.JButton djskfhs12jkhkds;
    private javax.swing.JPanel dsfjhkds;
    private javax.swing.JPanel dsfsjkd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jkdfhs;
    private javax.swing.JPanel jkdsfh23jk;
    private javax.swing.JPanel jklrte;
    private javax.swing.JPanel jsdfh342kjsd;
    private javax.swing.JPanel jskad12;
    private javax.swing.JButton jskfdh;
    private javax.swing.JLabel jskhd12adsf;
    private javax.swing.JPanel n67sdf;
    private javax.swing.JLabel pn1;
    private javax.swing.JLabel pn2;
    private javax.swing.JLabel pn3;
    private javax.swing.JLabel pn4;
    private javax.swing.JLabel pn5;
    private javax.swing.JLabel pn6;
    private javax.swing.JLabel pn7;
    private javax.swing.JLabel pn8;
    private javax.swing.JLabel q1;
    private javax.swing.JLabel q2;
    private javax.swing.JLabel q3;
    private javax.swing.JLabel q4;
    private javax.swing.JLabel q5;
    private javax.swing.JLabel q6;
    private javax.swing.JLabel q7;
    private javax.swing.JLabel q8;
    private javax.swing.JPanel sdfhskjdh23;
    private javax.swing.JLabel t1;
    private javax.swing.JLabel t2;
    private javax.swing.JLabel t3;
    private javax.swing.JLabel t4;
    private javax.swing.JLabel t5;
    private javax.swing.JLabel t6;
    private javax.swing.JLabel t7;
    private javax.swing.JLabel t8;
    private javax.swing.JTextField w64ash;
    // End of variables declaration//GEN-END:variables
}
