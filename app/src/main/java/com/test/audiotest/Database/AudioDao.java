package com.test.audiotest.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.test.audiotest.Model.AudioFile;

import java.util.List;

@Dao
public interface AudioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AudioFile audioFile);

    @Query("Select * from AudioFile")
    LiveData<List<AudioFile>> getList();

}
