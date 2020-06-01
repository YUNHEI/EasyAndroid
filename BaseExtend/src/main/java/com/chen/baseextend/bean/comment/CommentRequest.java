package com.chen.baseextend.bean.comment;

import com.chen.basemodule.network.base.BaseRequest;

import java.io.Serializable;

public class CommentRequest extends BaseRequest implements Serializable {


    public String enterpriseId;
    public String informationId;
    public String reply;
    public String type;
    public String commentator;
    public String crtTime;
    public String crtUserId;
    public String crtUserName;
    public int replyCount;
    public String eid;
    public String id;
    public Integer likes;
    public String replyCommentId;
    public String belongCommentId;
    public String updTime;
    public String updUserId;
    public String updUserName;
    public String avatar;
    public String toCommentId;
    public String toNickName;
    public String toComment;
    public String description;
    public int hostStatus;

    public CommentRequest() {
    }

    public CommentRequest(String informationId, String reply, String type) {
        this.informationId = informationId;
        this.reply = reply;
        this.type = type;
    }

    public CommentRequest(String informationId) {
        this.informationId = informationId;
    }
}
