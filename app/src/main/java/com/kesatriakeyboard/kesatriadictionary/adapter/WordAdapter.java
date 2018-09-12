package com.kesatriakeyboard.kesatriadictionary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kesatriakeyboard.kesatriadictionary.R;
import com.kesatriakeyboard.kesatriadictionary.model.WordModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private ArrayList<WordModel> mData = new ArrayList<>();
    private final OnItemClickListener listener;
    private Context context;

    public WordAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(ArrayList<WordModel> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public WordModel getModel(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word_row, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        WordModel model = mData.get(position);

        holder.tvWord.setText(model.getWord());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textview_word)
        TextView tvWord;

        WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            WordModel model = mData.get(position);
            listener.onItemClick(model);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(WordModel model);
    }
}
