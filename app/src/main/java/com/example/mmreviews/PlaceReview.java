package com.example.mmreviews;

public class PlaceReview {

    String placeName;
    int r, reviewNumber;
    float avarage;
    String user;
    String comment;
    int likeNo, dislikeNo; //treba implementirati

    public PlaceReview(){

    }
    public PlaceReview(String name, int r, int reviewNumber, float avarage, String user, String comment) {
        this.placeName = name;
        this.r = r;
        this.reviewNumber = reviewNumber;
        this.avarage = avarage;
        this.user = user;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getReviewNumber() {
        return reviewNumber;
    }

    public void setReviewNumber(int reviewNumber) {
        this.reviewNumber = reviewNumber;
    }

    public float getAvarage() {
        return avarage;
    }

    public void setAvarage(float avarage) {
        this.avarage = avarage;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
