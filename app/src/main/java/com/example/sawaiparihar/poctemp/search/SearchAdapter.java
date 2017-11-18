package com.example.sawaiparihar.poctemp.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sawaiparihar.poctemp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by sawai.parihar on 18/11/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private JsonArray mJsonArray;

    SearchAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindData(position, mJsonArray.get(position).getAsJsonObject());
    }

    @Override
    public int getItemCount() {
        return (mJsonArray == null) ? 0 : mJsonArray.size();
    }

    public void setJsonArray(JsonArray array) {
        mJsonArray = array;
    }

    public JsonArray getJsonArray() { return mJsonArray; }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIconIv;
        private TextView mTextTv;

        public MyViewHolder(View itemView) {
            super(itemView);

            mIconIv = itemView.findViewById(R.id.image_iv);
            mTextTv = itemView.findViewById(R.id.text_tv);
        }

        public void bindData(int position, JsonObject data) {
            if (data != null) {

                String photoUrl = String.format("https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg", data.get("farm").getAsString(),
                        data.get("server").getAsString(), data.get("id").getAsString(), data.get("secret").getAsString(), "z");

                Glide.with(itemView.getContext())
                        .load(photoUrl)
                        .into(mIconIv);

                mTextTv.setText(data.get("title").getAsString());
            }
        }
    }
}
