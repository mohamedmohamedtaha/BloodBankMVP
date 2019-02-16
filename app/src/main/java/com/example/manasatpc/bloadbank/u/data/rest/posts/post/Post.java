
package com.example.manasatpc.bloadbank.u.data.rest.posts.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataPost dataPost;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataPost getDataPost() {
        return dataPost;
    }

    public void setDataPost(DataPost dataPost) {
        this.dataPost = dataPost;
    }

}
