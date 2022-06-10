package com.test.audiotest.Repo;

import androidx.lifecycle.LiveData;

import com.test.audiotest.Model.AudioFile;
import com.test.audiotest.Database.MyDatabase;

import java.util.List;

public class MyRepo {

    MyDatabase myDatabase;

    public MyRepo(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }

   public void insert(AudioFile audioFile){
        myDatabase.movieDao().insert(audioFile);
    }

   public LiveData<List<AudioFile>> getList(){
      return myDatabase.movieDao().getList();
    }
}
