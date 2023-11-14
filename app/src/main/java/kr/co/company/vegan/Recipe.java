package kr.co.company.vegan;

public class Recipe {
    private String title;
    private String description;
    private String imageUrl;

    public Recipe() {
        // Firebase에서 객체를 가져올 때 필요
    }

    public Recipe(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

