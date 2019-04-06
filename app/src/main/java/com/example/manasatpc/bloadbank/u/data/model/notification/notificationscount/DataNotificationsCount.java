
package com.example.manasatpc.bloadbank.u.data.model.notification.notificationscount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataNotificationsCount {

    @SerializedName("notifications-count")
    @Expose
    private Integer notificationsCount;

    public Integer getNotificationsCount() {
        return notificationsCount;
    }

    public void setNotificationsCount(Integer notificationsCount) {
        this.notificationsCount = notificationsCount;
    }

}
