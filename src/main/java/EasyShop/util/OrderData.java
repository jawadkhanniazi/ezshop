package EasyShop.util;
import java.util.LinkedList;

/**
 *
 * @author kaka
 */
public class OrderData {
    public  String orderId;
        public String productname;
        public String price;
        public String orderP;
        public String quantity;
        public String shopid;
        public String ordercomplete;
        public String sizeoforder;
        public String pid;
        public String key;
        public boolean hasMore;
        public LinkedList<OrderData> nextOrders = new LinkedList<>();
        public LinkedList<String> orderIDs = new  LinkedList<>();

        @Override
        
        public String toString() {
            return "orderData{" + "\n\torderId=" + orderId + "\n\tKey=" + key + ", \n\tproductname=" + productname + ", \n\tTotalPrice=" + price + ", \n\tquantity=" + quantity + ", \n\tshopId=" + shopid + ", \n\tordercomplete=" + ordercomplete + ", \n\tsizeoforder=" + sizeoforder + ", \n\thasMore=" + hasMore + ", \n\tnextOrder="+nextOrders.toString()+"}\n\n";
        }        

        public String getOrderP() {
            return orderP;
        }

        public void setOrderP(String orderP) {
        this.orderP = orderP;
    }
        public OrderData(){
            this.hasMore = false;
        }
        public OrderData(String orderId, String productname,String pid, String TotalPrice, String quant, String shopId, String size) {
            this.orderId = orderId;
            this.productname = productname;
            this.price = TotalPrice;
            this.quantity = quant;
            this.shopid = shopId;
            this.ordercomplete = "0";
            this.sizeoforder=size;
            this.pid = pid;
        }        
        public OrderData(String orderId, String productname,String pid, String TotalPrice, String quant, String shopId, String size, String key) {
            this.orderId = orderId;
            this.productname = productname;
            this.price = TotalPrice;
            this.quantity = quant;
            this.shopid = shopId;
            this.ordercomplete = "0";
            this.sizeoforder=size;
            this.pid = pid;
            this.key = key;
        }        
        public String getOrderId() {
            return orderId;
        }
        public String getProductname() {
            return productname;
        }
        public String getPrice() {
            return price;
        }
        public String getQuantity() {
            return quantity;
        }
        public String getShopid() {
            return shopid;
        }
        public String getOrdercomplete() {
            return ordercomplete;
        }
        public String getSizeoforder() {
            return sizeoforder;
        }
}
