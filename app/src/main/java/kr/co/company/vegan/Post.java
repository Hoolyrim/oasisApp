package kr.co.company.vegan;
// Post.java

public class Post {
    private String title;
    private String content;
    private String imageUrl;

    public Post() {
        // Default constructor required for Firebase
    }

    public Post(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
