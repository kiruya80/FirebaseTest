
package com.ulling.firebasetest.entites.instagram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hashtag {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("allow_following")
    @Expose
    private Boolean allowFollowing;
    @SerializedName("is_following")
    @Expose
    private Boolean isFollowing;
    @SerializedName("is_top_media_only")
    @Expose
    private Boolean isTopMediaOnly;
    @SerializedName("profile_pic_url")
    @Expose
    private String profilePicUrl;
    @SerializedName("edge_hashtag_to_media")
    @Expose
    private EdgeHashtagToMedia edgeHashtagToMedia;
    @SerializedName("edge_hashtag_to_top_posts")
    @Expose
    private EdgeHashtagToTopPosts edgeHashtagToTopPosts;
    @SerializedName("edge_hashtag_to_content_advisory")
    @Expose
    private EdgeHashtagToContentAdvisory edgeHashtagToContentAdvisory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAllowFollowing() {
        return allowFollowing;
    }

    public void setAllowFollowing(Boolean allowFollowing) {
        this.allowFollowing = allowFollowing;
    }

    public Boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public Boolean getIsTopMediaOnly() {
        return isTopMediaOnly;
    }

    public void setIsTopMediaOnly(Boolean isTopMediaOnly) {
        this.isTopMediaOnly = isTopMediaOnly;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public EdgeHashtagToMedia getEdgeHashtagToMedia() {
        return edgeHashtagToMedia;
    }

    public void setEdgeHashtagToMedia(EdgeHashtagToMedia edgeHashtagToMedia) {
        this.edgeHashtagToMedia = edgeHashtagToMedia;
    }

    public EdgeHashtagToTopPosts getEdgeHashtagToTopPosts() {
        return edgeHashtagToTopPosts;
    }

    public void setEdgeHashtagToTopPosts(EdgeHashtagToTopPosts edgeHashtagToTopPosts) {
        this.edgeHashtagToTopPosts = edgeHashtagToTopPosts;
    }

    public EdgeHashtagToContentAdvisory getEdgeHashtagToContentAdvisory() {
        return edgeHashtagToContentAdvisory;
    }

    public void setEdgeHashtagToContentAdvisory(EdgeHashtagToContentAdvisory edgeHashtagToContentAdvisory) {
        this.edgeHashtagToContentAdvisory = edgeHashtagToContentAdvisory;
    }

}
