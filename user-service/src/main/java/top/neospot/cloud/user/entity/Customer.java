package top.neospot.cloud.user.entity;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private Integer id ;
    private String name ;
    private int age ;

    //建立从Customer到Order之间一对多关系,因为一个客户可能会有多个订单。我们将多个订单放在一个list中。
    private List<Order> orders = new ArrayList<Order>() ;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", orders=" + orders +
                '}';
    }
}
