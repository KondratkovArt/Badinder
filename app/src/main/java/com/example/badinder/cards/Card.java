package com.example.badinder.cards;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private String userId;
    private String name;
    private String profileImageUrl;
    private List<String> images;

    public Card(String userId, String name, String profileImageUrl, List<String> images) {
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.images = images;
        images.add(0, profileImageUrl);
    }

    public Card(String userId, String name, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.images = new ArrayList<>();
        this.images.add(profileImageUrl);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() { return profileImageUrl; }

    public List<String> getImages() { return images; }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileImageUrl(String profileImageUrl) {this.profileImageUrl = profileImageUrl;}

    public void setImages(List<String> images) {this.images = images; }
}
