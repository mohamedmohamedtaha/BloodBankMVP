
package com.example.manasatpc.bloadbank.u.data.rest.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("dataPosts")
    @Expose
    private DataPosts dataPosts;

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

    public DataPosts getDataPosts() {
        return dataPosts;
    }

    public void setDataPosts(DataPosts dataPosts) {
        this.dataPosts = dataPosts;
    }

}
