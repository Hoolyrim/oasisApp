package kr.co.company.vegan;

import java.util.List;

public class Product {
    public String productName;
    public String productDescription;
    public List<Ingredient> ingredients;

    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }

    public Product(String productName, String productDescription, List<Ingredient> ingredients) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.ingredients = ingredients;
    }
}
