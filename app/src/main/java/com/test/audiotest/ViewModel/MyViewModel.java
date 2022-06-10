package com.test.audiotest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.test.audiotest.Model.AudioFile;
import com.test.audiotest.Database.MyDatabase;
import com.test.audiotest.Repo.MyRepo;

import java.util.List;

public class MyViewModel extends ViewModel {

    MyRepo myRepo = new MyRepo(MyDatabase.getInstance());

    public MyViewModel() {

    }

    public void insert(AudioFile audioFile){
        myRepo.insert(audioFile);
    }


    public LiveData<List<AudioFile>> getList(){
        return myRepo.getList();
    }
}
