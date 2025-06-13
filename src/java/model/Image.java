/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Image {
    private long id;
    private long postId;
    private String imageUrl;
    private String cloudinaryPublicId;
    private boolean isThumbnail;

    public Image() {
    }

    public Image(long id, long postId, String imageUrl, String cloudinaryPublicId, boolean isThumbnail) {
        this.id = id;
        this.postId = postId;
        this.imageUrl = imageUrl;
        this.cloudinaryPublicId = cloudinaryPublicId;
        this.isThumbnail = isThumbnail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCloudinaryPublicId() {
        return cloudinaryPublicId;
    }

    public void setCloudinaryPublicId(String cloudinaryPublicId) {
        this.cloudinaryPublicId = cloudinaryPublicId;
    }

    public boolean isThumbnail() {
        return isThumbnail;
    }

    public void setThumbnail(boolean isThumbnail) {
        this.isThumbnail = isThumbnail;
    }

    
}
