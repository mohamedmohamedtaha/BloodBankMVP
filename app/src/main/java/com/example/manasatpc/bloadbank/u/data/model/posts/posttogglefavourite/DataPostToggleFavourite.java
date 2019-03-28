
package com.example.manasatpc.bloadbank.u.data.model.posts.posttogglefavourite;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPostToggleFavourite {

    @SerializedName("attached")
    @Expose
    private List<Integer> attached = null;
    @SerializedName("detached")
    @Expose
    private List<Object> detached = null;

    public List<Integer> getAttached() {
        return attached;
    }

    public void setAttached(List<Integer> attached) {
        this.attached = attached;
    }

    public List<Object> getDetached() {
        return detached;
    }

    public void setDetached(List<Object> detached) {
        this.detached = detached;
    }

}
