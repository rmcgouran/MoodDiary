package com.rmcgouran.mooddiary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.rmcgouran.mooddiary.R;
import com.rmcgouran.mooddiary.calcTime;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//First class

public class voiceListAdapter extends RecyclerView.Adapter<voiceListAdapter.voiceViewHolder> {

    private File[] allFiles;
    private com.rmcgouran.mooddiary.calcTime calcTime;

    private onItemListClick onItemListClick;



    public voiceListAdapter(File[] allFiles, onItemListClick onItemListClick) {
        this.allFiles = allFiles;
        this.onItemListClick = onItemListClick;
    }

    @NonNull
    @Override
    public voiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        calcTime = new calcTime();
        return new voiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull voiceViewHolder holder, int position) {
        holder.cardvoice.setText(allFiles[position].getName());
        holder.cardTitle.setText(calcTime.getHowLong(allFiles[position].lastModified()));
    }

    @Override
    public int getItemCount() {
        return allFiles.length;
    }

    //Inner Class

    public class voiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Adding components
        private MaterialCardView card;
        private TextView cardTitle;
        private Button cardvoice;
        private String recordFile;


        public voiceViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            cardTitle = itemView.findViewById(R.id.card_title);
            cardvoice = itemView.findViewById(R.id.card_voice);

            card.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListClick.onClickListener(allFiles[getAdapterPosition()], getAdapterPosition());
        }
    }


    public interface onItemListClick{
        void onClickListener(File file, int position);

    }



}

