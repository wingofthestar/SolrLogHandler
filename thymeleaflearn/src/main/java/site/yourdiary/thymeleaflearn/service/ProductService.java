package site.yourdiary.thymeleaflearn.service;

import org.springframework.stereotype.Service;
import site.yourdiary.thymeleaflearn.pojo.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    public List<Product> getProduct(){
        Product p1 = new Product(1, "牙膏", 5, new Date(), "北京");
        Product p2 = new Product(2, "脸盆", 10, new Date(), "河北");
        Product p3 = new Product(3, "洗衣机", 3450, new Date(), "浙江");
        Product p4 = new Product(4, "电脑", 5000, new Date(), "深圳");
        Product p5 = new Product(5, "杯子", 5, new Date(), "江苏");
        List<Product> productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
        productList.add(p4);
        productList.add(p5);
        return productList;
    }
}
