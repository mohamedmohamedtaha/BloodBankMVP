package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.notification.notificationslist.Data2NotificationsList;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterNotificationList extends RecyclerView.Adapter<AdapterNotificationList.NotificationListViewHolder> {

    private Context context;
    ArrayList<Data2NotificationsList> data2NotificationsListArrayList = new ArrayList<>();
    private showDetails mListener;

    public AdapterNotificationList(Context context, ArrayList<Data2NotificationsList> data2NotificationsListArrayList, showDetails mListener) {
        this.context = context;
        this.data2NotificationsListArrayList = data2NotificationsListArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public NotificationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_send_notification, parent, false);
        final NotificationListViewHolder viewHolder = new NotificationListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Data2NotificationsList data2NotificationsList = data2NotificationsListArrayList.get(position);
                if (mListener != null) mListener.showItemDetails(data2NotificationsList);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListViewHolder holder, int position) {
        holder.TVShowId.setText(data2NotificationsListArrayList.get(position).getId().toString());
        try {
            holder.TVDay.setText(HelperMethod.formatDay("yyyy-MM-dd HH:mm:ss",data2NotificationsListArrayList.get(position).getUpdatedAt()));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        try {
            String date = HelperMethod.formatDateFromDateString("yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd", data2NotificationsListArrayList.get(position).getUpdatedAt());
            holder.TVDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();

        }

    }

    @Override
    public int getItemCount() {
        if (data2NotificationsListArrayList != null) {
            return data2NotificationsListArrayList.size();
        }
        return 0;
    }

    class NotificationListViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.TV_Date)
        TextView TVDate;
        @BindView(R.id.TV_Show_Id)
        TextView TVShowId;
        @BindView(R.id.TV_Day)
        TextView TVDay;


        public NotificationListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }

    private String formatDate(Date dataObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(dataObject);
    }

    public interface showDetails {
        void showItemDetails(Data2NotificationsList data2NotificationsList);
    }


}