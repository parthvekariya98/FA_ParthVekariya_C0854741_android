package com.example.fa_parthvekariya_c0854741_android;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fa_parthvekariya_c0854741_android.databinding.PlaceListBinding;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {
    Context context;
    PlaceClickListener mListener;
    ArrayList<PlaceModel> placeList;

    public interface PlaceClickListener{
        void onDeleteClickListener(int position);
        void onUpdateClickListener(int position);
        void onViewDetailsClickListener(int position);
    }


    PlaceAdapter(Context context, PlaceClickListener mListener){
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(PlaceListBinding.inflate((LayoutInflater.from(context)), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            PlaceModel currentPlace = placeList.get(position);
            Log.d("test-->",currentPlace.getVisited());
            if (currentPlace.getVisited().equals("true")) {
                holder.itemView.getRoot().setCardBackgroundColor(Color.LTGRAY);
            } else {
                holder.itemView.getRoot().setCardBackgroundColor(Color.WHITE);
            }

            holder.setDataToView(currentPlace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public void doRefresh(ArrayList<PlaceModel> placeList) {
        this.placeList = placeList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        PlaceListBinding itemView;
        public MyViewHolder(@NonNull PlaceListBinding item_view) {
            super(item_view.getRoot());
            this.itemView = item_view;
            init();
        }

        private void init() {
            itemView.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDeleteClickListener(getAdapterPosition());
                }
            });

            itemView.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onUpdateClickListener(getAdapterPosition());
                }
            });

            itemView.btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onViewDetailsClickListener(getAdapterPosition());
                }
            });
        }

        public void setDataToView(PlaceModel placeModel) {
            itemView.placeTitle.setText(placeModel.getPlaceName());
            itemView.placeAddress.setText(placeModel.getPlaceAddress());
            itemView.placeDescription.setText(placeModel.getPlaceDescription());
        }
    }

}

