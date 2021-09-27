package com.example.mmreviews;

public class PlaceReview {

    String placeName;
    int reviewNumber;
    float r, avarage;
    String user;
    String comment;
    int likeNo; //treba implementirati

    public PlaceReview(){

    }
    public PlaceReview(String name, float r, int reviewNumber, float avarage, String user, String comment, int likeNo) {
        this.placeName = name;
        this.r = r;
        this.reviewNumber = reviewNumber;
        this.avarage = avarage;
        this.user = user;
        this.comment = comment;
        this.likeNo = likeNo;
    }

    public int getLikeNo() {
        return likeNo;
    }

    public void setLikeNo(int likeNo) {
        this.likeNo = likeNo;
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

    public float getR() {
        return r;
    }

    public void setR(float r) {
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
