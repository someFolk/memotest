package com.thebreadiswhite.memotest.db.multidatabase.instance.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;
import com.thebreadiswhite.memotest.Memotest;
import com.thebreadiswhite.memotest.MemotestStack;
import com.thebreadiswhite.memotest.db.memotest.memotest.MemotestDao;
import com.thebreadiswhite.memotest.db.memotest.stack.MemotestStackConverter;
import com.thebreadiswhite.memotest.db.memotest.stack.MemotestStackDAO;
import com.thebreadiswhite.memotest.util.systems.positionable.db.PositionableDao;

// Database annotation
@Database(entities = {Memotest.class, MemotestStack.class}, version = 1, exportSchema = false)

// Type Converters
@TypeConverters(MemotestStackConverter.class)

public abstract class RoomInstance extends androidx.room.RoomDatabase
{

    private static final String DATABASE_NAME = "progit";

    // Room Instance
    private static RoomInstance INSTANCE;

    public static RoomInstance getAppDatabase(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), RoomInstance.class, DATABASE_NAME)

                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .fallbackToDestructiveMigration()

                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance()
    {
        INSTANCE = null;
    }

    // System Daos
    public abstract MemotestDao memotest();

    public abstract MemotestStackDAO memotestStack();

    //    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
    //        @Override
    //        public void migrate(SupportSQLiteDatabase database) {
    //            //database.execSQL("ALTER TABLE users "
    //            //        +"ADD COLUMN address TEXT");
    //
    //        }
    //    };

    public abstract PositionableDao positionable();
}