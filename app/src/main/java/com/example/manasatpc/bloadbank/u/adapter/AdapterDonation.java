package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.Data2DonationRequests;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterDonation extends RecyclerView.Adapter<AdapterDonation.DonationViewHolder> implements Filterable {
    private showDetials mListener ;
    private makeCall makeCall;
    private Context context;
    private ArrayList<Data2DonationRequests> dataDonations = new ArrayList<>();
    ArrayList<Data2DonationRequests> data2DonationRequestsArrayListSearch = new ArrayList<>();
    Data2DonationRequests data2DonationRequests;

    public AdapterDonation(Context context, ArrayList<Data2DonationRequests> dataDonations,showDetials mListener,makeCall makeCall) {
        this.context = context;
        this.dataDonations = dataDonations;
        this.mListener = mListener;
        this.makeCall = makeCall;
        this.data2DonationRequestsArrayListSearch = dataDonations;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_donation, parent, false);
        final DonationViewHolder donationViewHolder = new DonationViewHolder(view);
      /*  view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = donationViewHolder.getAdapterPosition();
                DataDonation dataDonation = dataDonations.get(position);
                if (mListener !=null)mListener.itemShowDetail(dataDonation);

            }
        });*/
        return donationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        data2DonationRequests = data2DonationRequestsArrayListSearch.get(position);
        holder.TVCity.setText( data2DonationRequests.getCity().getName());
       holder.TVHospital.setText(data2DonationRequests.getHospitalName());
        holder.TVNamePatient.setText(data2DonationRequests.getPatientName());
        holder.IMTypeBlood.setText(data2DonationRequests.getBloodType().getName());
    }

    @Override
    public int getItemCount() {
        return data2DonationRequestsArrayListSearch.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    data2DonationRequestsArrayListSearch = dataDonations;
                    notifyDataSetChanged();
                }else {
                    ArrayList filterdList = new ArrayList();
                    for (Data2DonationRequests row : dataDonations){
                        //change this to filter according
                        if (row.getCity().getName().toLowerCase().contains(charString.toLowerCase()) ||
                            row.getBloodType().getName().toLowerCase().contains(charString.toLowerCase())){
                            filterdList.add(row);
                        }
                    }
                    data2DonationRequestsArrayListSearch = filterdList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data2DonationRequestsArrayListSearch;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data2DonationRequestsArrayListSearch = (ArrayList)results.values;
                notifyDataSetChanged();

            }
        };
    }

    /*  @OnClick({R.id.BT_Details, R.id.BT_Call})
      public void onViewClicked(View view) {
          switch (view.getId()) {
              case R.id.BT_Details:
                  break;
              case R.id.BT_Call:
                  int position;
                  break;
          }
      }*/
    public interface showDetials {
        void itemShowDetail(Data2DonationRequests position);
    }
    public interface makeCall {
        void itemMakeCall(Data2DonationRequests position);
    }
    class DonationViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.IM_Type_Blood)
        TextView IMTypeBlood;
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
            BTCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Data2DonationRequests dataDonation = dataDonations.get(position);
                    if (mListener != null)makeCall.itemMakeCall(dataDonation);

                }
            });
            BTDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Data2DonationRequests dataDonation = dataDonations.get(position);
                    if (mListener !=null)mListener.itemShowDetail(dataDonation);

                }
            });



        }
    }
}
