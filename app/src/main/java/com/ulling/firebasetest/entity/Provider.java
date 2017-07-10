package com.ulling.firebasetest.entity;

/**
 * @author : KILHO
 * @project : FirebaseTest
 * @date : 2016. 11. 19.
 * @description :
 * @since :
 */

public class Provider {
  public String provider = "";
  public String providerId = "";
  public String photoUrl = "";

  public Provider() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
  }

  @Override
  public String toString() {
    return "Provider{" +
        "provider='" + provider + '\'' +
        ", providerId='" + providerId + '\'' +
        ", photoUrl='" + photoUrl + '\'' +
        '}';
  }
}
