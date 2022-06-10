package com.test.audiotest.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.test.audiotest.App;
import com.test.audiotest.Model.AudioFile;

@Database(entities = {AudioFile.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract AudioDao movieDao();

    private static MyDatabase sInstance;

    public static MyDatabase getInstance() {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(App.getInstance(), MyDatabase.class, MyDatabase.class.getSimpleName())
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }
}
