package com.chen.baseextend.widget.photopicker.event;

import com.chen.baseextend.widget.photopicker.entity.Photo;

import java.util.List;

/**
 * @author alan
 * @date 2019/2/12
 */
public class PhotoChangeEvent {

    public List<Photo> photos;

    public PhotoChangeEvent(List<Photo> photos) {
        this.photos = photos;
    }
}
