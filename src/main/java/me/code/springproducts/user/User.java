package me.code.springproducts.user;

import lombok.Getter;
import lombok.Setter;
import me.code.springproducts.product.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {

    private String name, password, salt;
    private List<Product> favorites;

    public User(String name, String password, String salt) {
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.favorites = new ArrayList<>();
    }
}
