package sg.edu.rp.c346.id20032316.bookdiary;

import java.io.Serializable;

public class Book implements Serializable {

    private int _id;
    private String title;
    private String author;
    private String review;
    private String length;
    private float stars;

    public Book(int _id, String title, String author, String review, String length, float stars) {
        this._id = _id;
        this.title = title;
        this.author = author;
        this.review = review;
        this.length = length;
        this.stars = stars;
    }

    public Book(String title, String author, String review, String length, float stars) {
        this.title = title;
        this.author = author;
        this.review = review;
        this.length = length;
        this.stars = stars;
    }

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
