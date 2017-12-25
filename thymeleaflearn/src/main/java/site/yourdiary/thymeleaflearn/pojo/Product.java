package site.yourdiary.thymeleaflearn.pojo;

import java.util.Date;

public class Product {
    private int id;
    private String name;
    private int price;
    private Date date;
    private String address;

    public Product() {
    }

    public Product(int id, String name, int price, Date date, String address) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", address='" + address + '\'' +
                '}';
    }
}
