package com.test.audiotest.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;

import com.test.audiotest.Model.AudioFile;
import com.test.audiotest.Adapter.AudiosRecyclerAdapter;
import com.test.audiotest.R;
import com.test.audiotest.Utils.GetFilePathFromDevice;
import com.test.audiotest.ViewModel.MyViewModel;
import com.test.audiotest.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import carbon.widget.ImageView;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    List<AudioFile> list;

    AudiosRecyclerAdapter adapter;

    MyViewModel myViewModel;

    AudioFile activeAudioFile;

    ImageView activeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.importFileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_upload = new Intent();
                intent_upload.setType("audio/*");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);


                activityResultLaunch.launch(intent_upload);

            }
        });

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        setRecycler();

        myViewModel.getList().observe(this, new Observer<List<AudioFile>>() {
            @Override
            public void onChanged(List<AudioFile> audioFiles) {
                adapter.setList(audioFiles);
            }
        });

    }

    private void setRecycler() {

        adapter = new AudiosRecyclerAdapter(MainActivity.this, new AudiosRecyclerAdapter.AudioItemEvents() {
            @Override
            public void onPlay(AudioFile item, ImageView imageView) {


                if (imageView.getTag().equals("played")) {
                    if (activeAudioFile == null || activeAudioFile.getId() == item.getId()) {

                        //first time or no active music being played
                        mediaPlayer = null;
                        createMediaPlayer(Uri.parse(item.getUriPath()), item);
                        imageView.setImageResource(R.drawable.pause);
                        imageView.setTint(Color.parseColor(getResources().getString(R.string.black)));

                    } else if (activeAudioFile.getId() != item.getId()) {

                        //pause_img
                        imageView.setImageResource(R.drawable.pause);
                        imageView.setTint(Color.parseColor(getResources().getString(R.string.black)));

                        //play_img
                        activeImageView.setImageResource(R.drawable.play);
                        activeImageView.setTint(Color.parseColor(getResources().getString(R.string.orange)));
                        activeImageView.setTag("played");

                        //stopmediaplayer
                        mediaPlayer.stop();
                        mediaPlayer = null;

                        //restart new one
                        createMediaPlayer(Uri.parse(item.getUriPath()), item);

                    }
                } else {
                    //pausing the music being played
                    activeImageView.setImageResource(R.drawable.play);
                    activeImageView.setTint(Color.parseColor(getResources().getString(R.string.orange)));
                    mediaPlayer.stop();
                }

                activeAudioFile = item;
                activeImageView = imageView;

            }
        });
        binding.recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recycler.setAdapter(adapter);

    }


    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    list = new ArrayList<>();
                    String fileName = "";
                    if (result.getResultCode() == RESULT_OK) {

                        Uri uri = result.getData().getData();

                        String scheme = uri.getScheme();
                        if (scheme.equals("file")) {
                            fileName = uri.getLastPathSegment();
                        } else if (scheme.equals("content")) {
                            fileName = queryName(getContentResolver(), uri);
                        }

                        AudioFile audioFile = new AudioFile();
                        audioFile.setUriPath(uri.toString());
                        audioFile.setName(fileName);
                        audioFile.setFilePath(GetFilePathFromDevice.getPath(MainActivity.this, uri));

                        myViewModel.insert(audioFile);

                    }
                }

            });


    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    MediaPlayer mediaPlayer;

    public void createMediaPlayer(Uri uri, AudioFile audioFile) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(audioFile.getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}



