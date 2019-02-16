package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.donation.DataDonation;
import com.example.manasatpc.bloadbank.u.data.rest.posts.Data2Posts;
import com.example.manasatpc.bloadbank.u.data.rest.posts.post.Post;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDonation extends RecyclerView.Adapter<AdapterDonation.DonationViewHolder> {
    private showDetial mListener ;

    private Context context;
    private ArrayList<DataDonation> dataDonations = new ArrayList<>();

    public AdapterDonation(Context context, ArrayList<DataDonation> dataDonations,showDetial mListener) {
        this.context = context;
        this.dataDonations = dataDonations;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_donation, parent, false);
        final DonationViewHolder donationViewHolder = new DonationViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = donationViewHolder.getAdapterPosition();
                DataDonation dataDonation = dataDonations.get(position);
                if (mListener !=null)mListener.itemShowDetail(dataDonation);

            }
        });
        return donationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        holder.TVCity.setText( dataDonations.get(position).getCity().getName());
        holder.TVHospital.setText(dataDonations.get(position).getHospitalName());
        holder.TVNamePatient.setText(dataDonations.get(position).getPatientName());
        Glide.with(context)
                .load(dataDonations.get(position).getBloodType())
                .error(R.drawable.no_image)
                .centerCrop().into(holder.IMTypeBlood);
    }

    @Override
    public int getItemCount() {
        return dataDonations.size();
    }

    @OnClick({R.id.BT_Details, R.id.BT_Call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.BT_Details:
                break;
            case R.id.BT_Call:
                break;
        }
    }
    public interface showDetial {
        void itemShowDetail(DataDonation position);
    }
    class DonationViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.IM_Type_Blood)
        CircleImageView IMTypeBlood;
        @BindView(R.id.TV_Name_Patient)
        TextView TVNamePatient;
        @BindView(R.id.TV_Hospital)
        TextView TVHospital;
        @BindView(R.id.TV_City)
        TextView TVCity;
        @BindView(R.id.BT_Details)
        Button BTDetails;
        @BindView(R.id.BT_Call)
        Button BTCall;


        public DonationViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);



        }
    }
}
