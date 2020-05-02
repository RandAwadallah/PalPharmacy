package com.app.palpharmacy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.palpharmacy.R;
import com.app.palpharmacy.activities.PharmacyDetailsActivity;
import com.app.palpharmacy.model.Pharmacy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    RequestOptions option;
    private Context mContext;
    private List<Pharmacy> mData;

    private List<Pharmacy> mDatafiltered;

    public RecyclerViewAdapter(Context mContext, List<Pharmacy> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDatafiltered=mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.anime_row_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, PharmacyDetailsActivity.class);
                i.putExtra("name", mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("city", mData.get(viewHolder.getAdapterPosition()).getCity());
                i.putExtra("governorate", mData.get(viewHolder.getAdapterPosition()).getGovernorate());
                i.putExtra("owner", mData.get(viewHolder.getAdapterPosition()).getOwner());
                i.putExtra("opens", mData.get(viewHolder.getAdapterPosition()).getOpens());
                i.putExtra("anime_img", mData.get(viewHolder.getAdapterPosition()).getImage_url());

                mContext.startActivity(i);

            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(mDatafiltered.get(position).getName());
        holder.tv_city.setText(mDatafiltered.get(position).getCity());
//        holder.opens.setText(mDatafiltered.get(position).getOpens());



        // Load Image from the internet and set it into Imageview using Glide

        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(option).into(holder.img_thumbnail);


    }

    @Override
    public int getItemCount() {
        return mDatafiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key= constraint.toString();
                if(Key.isEmpty()) {
                    mDatafiltered= mData;
                }
                else{
                    List<Pharmacy> lstfiltered = new ArrayList<>();
                    for(Pharmacy row : mData) {
                        if (row.getName().toLowerCase().contains(Key.toLowerCase())) {
                            lstfiltered.add(row);
                        }
                    }
                    mDatafiltered= lstfiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=mDatafiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mDatafiltered = (List<Pharmacy>) results.values;
                notifyDataSetChanged();



            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView img_thumbnail;
        TextView tv_city;
        LinearLayout view_container;


        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            tv_name = itemView.findViewById(R.id.anime_name);
            tv_city = itemView.findViewById(R.id.city);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }

}
