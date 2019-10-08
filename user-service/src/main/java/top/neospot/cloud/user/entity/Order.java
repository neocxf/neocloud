package top.neospot.cloud.user.entity;

/**
 * 订单
 */
public class Order {
    private Integer id ;
    private String orderno ;
    private float price  ;
    //建立从Order到Customer之间多对一关联关系
    private Customer customer ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderno='" + orderno + '\'' +
                ", price=" + price +
                ", customer=" + customer +
                '}';
    }
}