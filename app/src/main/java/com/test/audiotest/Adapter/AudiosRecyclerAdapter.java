package com.test.audiotest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.audiotest.Model.AudioFile;
import com.test.audiotest.databinding.ItemAudioBinding;

import java.util.ArrayList;
import java.util.List;

import carbon.widget.ImageView;

public class AudiosRecyclerAdapter extends RecyclerView.Adapter<AudiosRecyclerAdapter.AudiosRecyclerVH> {
    List<AudioFile> list = new ArrayList<>();
    Context context;
    AudioItemEvents audioItemEvents;

    public AudiosRecyclerAdapter(Context context, AudioItemEvents audioItemEvents) {
        this.context = context;
        this.audioItemEvents = audioItemEvents;
    }

    public void setList(List<AudioFile> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface AudioItemEvents {
        void onPlay(AudioFile item, ImageView imageView);
    }

    @NonNull
    @Override
    public AudiosRecyclerAdapter.AudiosRecyclerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new AudiosRecyclerVH(ItemAudioBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AudiosRecyclerAdapter.AudiosRecyclerVH holder, int position) {
        AudioFile item = list.get(position);

        holder.itemAudioBinding.name.setText(item.getName());

        holder.itemAudioBinding.playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                audioItemEvents.onPlay(item, (ImageView) view);


                //changing tags for checking item being played or paused
                if (view.getTag().equals("played")) {
                    view.setTag("paused");
                } else {
                    view.setTag("played");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AudiosRecyclerVH extends RecyclerView.ViewHolder {

        ItemAudioBinding itemAudioBinding;

        public AudiosRecyclerVH(ItemAudioBinding itemAudioBinding) {
            super(itemAudioBinding.getRoot());
            this.itemAudioBinding = itemAudioBinding;
        }
    }
}
