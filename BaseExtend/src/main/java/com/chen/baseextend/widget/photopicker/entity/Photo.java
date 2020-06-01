package com.chen.baseextend.widget.photopicker.entity;

import java.io.Serializable;

/**
 * Created by donglua on 15/6/30.
 */
public class Photo implements Serializable {

  private int id;
  private String path;

  private boolean isCheck;

  public boolean isCheck() {
    return isCheck;
  }

  public void setCheck(boolean check) {
    isCheck = check;
  }

  public Photo(int id, String path) {
    this.id = id;
    this.path = path;
  }
  public Photo(int id, String path,boolean check) {
    this.id = id;
    this.path = path;
    this.isCheck = check;
  }

  public Photo() {
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Photo)) return false;

    Photo photo = (Photo) o;

    return id == photo.id;
  }

  @Override public int hashCode() {
    return id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
