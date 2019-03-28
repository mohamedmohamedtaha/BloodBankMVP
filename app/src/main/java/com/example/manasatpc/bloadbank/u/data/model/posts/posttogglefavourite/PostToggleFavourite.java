
package com.example.manasatpc.bloadbank.u.data.model.posts.posttogglefavourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostToggleFavourite {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataPostToggleFavourite dataPostToggleFavourite;

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

    public DataPostToggleFavourite getDataPostToggleFavourite() {
        return dataPostToggleFavourite;
    }

    public void setDataPostToggleFavourite(DataPostToggleFavourite dataPostToggleFavourite) {
        this.dataPostToggleFavourite = dataPostToggleFavourite;
    }

}
