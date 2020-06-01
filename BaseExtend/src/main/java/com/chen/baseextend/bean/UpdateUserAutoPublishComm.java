package com.chen.baseextend.bean;


import com.chen.basemodule.network.base.BaseRequest;

/**
 * @author alan
 * @date 2018/11/21
 */
public class UpdateUserAutoPublishComm extends BaseRequest {


    public String autoPublishCommunityId;

    public UpdateUserAutoPublishComm(String autoPublishCommunityId) {
        this.autoPublishCommunityId = autoPublishCommunityId;
    }
}
