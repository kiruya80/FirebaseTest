package com.ulling.firebasetest.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : KILHO
 * @project : FirebaseTest
 * @date : 2016. 11. 19.
 * @description :
 * @since :
 */

public class User {
  //  public String uid;
  public String displayName = "";
  public String email = "";
  public String photoUrl = "";
  public String backPhotoUrl = "";
  public String introduction = "";


  public Map<String, Provider> providers = new HashMap<>();

  public User() {
  }

  @Override
  public String toString() {
    return "User{" +
        "displayName='" + displayName + '\'' +
        ", email='" + email + '\'' +
        ", photoUrl='" + photoUrl + '\'' +
        ", backPhotoUrl='" + backPhotoUrl + '\'' +
        ", introduction='" + introduction + '\'' +
        ", providers=" + providers +
        '}';
  }
}
