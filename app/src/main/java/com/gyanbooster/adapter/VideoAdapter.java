package com.gyanbooster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.video.Queries;

import java.util.ArrayList;


/**
 * Created by Dell on 1/31/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Queries> queriesArrayList;

    public VideoAdapter(Context context, ArrayList<Queries> queriesArrayList) {
        this.context = context;
        this.queriesArrayList = queriesArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_video_reviews, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Queries data = queriesArrayList.get(position);
        if (data != null) {
            holder.txtUserName.setText(data.getFront_user_fname());
            holder.txtDate.setText(data.getDate());
            holder.txtQuery.setText(data.getQus());

            if (data.getUser_image() != null) {
                String thumbnail = Constants.THUMBNAIL_BASE_URL + data.getUser_image();
                Glide.with(context).load(thumbnail).placeholder(R.mipmap.avatar).dontAnimate().into(holder.imgUserImage);
            }

        }


    }


    @Override
    public int getItemCount() {
        return queriesArrayList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUserImage;
        private TextView txtUserName;
        private TextView txtDate;
        private TextView txtQuery;
        private TextView txtReply;


        public ViewHolder(View view) {
            super(view);
            imgUserImage = view.findViewById(R.id.imgUserImage);
            txtUserName = view.findViewById(R.id.txtUserName);
            txtDate = view.findViewById(R.id.txtDate);
            txtQuery = view.findViewById(R.id.txtQuery);
            txtReply = view.findViewById(R.id.txtReply);


        }
    }
}
