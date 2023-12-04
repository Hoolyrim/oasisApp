package kr.co.company.vegan;
// Post.java

import java.util.Date;

public class Post {
    private String title;
    private String content;
    private String imageUrl;
    private long timestamp;  // 작성 시간
    private long modifiedTimestamp;  // 수정 시간

    public Post() {
        // Default constructor required for Firebase
    }

    public Post(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.timestamp = new Date().getTime(); // 현재 시간을 타임스탬프로 저장
        this.modifiedTimestamp = this.timestamp; // 초기에는 작성 시간으로 초기화
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

    public long getTimestamp() {
        return timestamp;
    }

    public long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setModifiedTimestamp() {
        this.modifiedTimestamp = new Date().getTime(); // 현재 시간으로 수정 시간 업데이트
    }
}
