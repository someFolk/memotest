package com.morsela.tel.db.instance.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.morsela.tel.db.DatabaseHolder;
import com.morsela.tel.db.servertolocallocaltoserver.FireBaseChangesWorker;
import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdate;
import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdateDBH;
import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdateType;
import com.morsela.tel.db.util.Field;
import com.morsela.tel.db.util.Table;

import java.io.File;
import java.util.ArrayList;


// IMPORTANT NOTE
// RawQuery Runs the provided SQL and returns a Cursor over the result set.
// END OF IMPORTANT NOTE

public class LocalDatabase extends SQLiteOpenHelper
{
    private static LocalDatabase sInstance;
    private LocalUpdateDBH localUpdateDBH;
    private FireBaseChangesWorker worker;

    // Mandatory DB values
    private static final String DATABASE_NAME = "teldatabase";
    private static final int DATABASE_VERSION = 1;

    // Helpers for better formatting and reading of the queries
    private static final String SPACE = " ";
    private static final String COMMA = ",";

    private LocalDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // Singleton
        sInstance = this;

        worker = new FireBaseChangesWorker();

        // Setting up local-update, It is acceptable that this object sits on the local database
        // because every action we perform is evolving local-update
        localUpdateDBH = new LocalUpdateDBH(context);
    }

    public static synchronized LocalDatabase getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new LocalDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    public static boolean doesDatabaseExist(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    public FireBaseChangesWorker getWorker()
    {
        return worker;
    }

    // For the purpose of sending queries
    // private because of the purpose of not letting any access
    // from the outside have any way you the database.
    private void execute(String query)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void createTable(Table table)
    {
        execute(getCreateTableSyntax(table));
    }

    public void add(Table table, ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table.getName(), null, values);
        db.close();
    }

    public Cursor getLastItemsOrderByTimestampWithLimit(Table table, int limit)
    {
        String selectQuery= "SELECT * FROM " + table.getName() + " ORDER BY "+ DatabaseHolder.KEY_TIMESTAMP +" DESC LIMIT 1"; // " + limit;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        try
        {
            // This will ensure that who ever called the method will
            // get the data.
            return cursor;
        }finally
        {
            // This will ensure to close the connection
            // after the data have been delivered.
            // but it crashes.. lol
            //cursor.close();
        }
    }


    public Cursor getAllItems(Table table) {

        // Select All Query
        String selectQuery =
                "SELECT * FROM" +
                SPACE +
                table.getName() +
                SPACE +
                "ORDER BY "+ DatabaseHolder.KEY_TIMESTAMP +" ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // TODO: >>>>>>>>>>>>> PLEASE check if you can close cursor and than deploy it.
        try
        {
            // This will ensure that who ever called the method will
            // get the data.
            return cursor;
        }finally
        {
            // This will ensure to close the connection
            // after the data have been delivered.
            // but it crashes.. lol
            //cursor.close();
        }
    }

    public void update(Table table, ContentValues cv, String targetFirebaseKey)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(table.getName(), cv, DatabaseHolder.KEY_KEY +"='" + targetFirebaseKey + "'", null);

        LocalUpdate update = new LocalUpdate(table.getName(), targetFirebaseKey, LocalUpdateType.UPDATE);

        localUpdateDBH.insert(update, false);
    }

    public Cursor getItemByKey(Table table, String key)
    {
        // Init
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Field> fields = table.getFields();
        int size = fields.size();

        // Getting ready fields name on an array.
        String[] fieldsToString = new String[size];
        for(int i = 0; i < size; i++)
        {
            fieldsToString[i] = fields.get(i).getName();
        }

        // Pulling data from DB
        Cursor cursor = db.query(table.getName(),
                fieldsToString,
                DatabaseHolder.KEY_KEY+"=?",
                new String[]{String.valueOf(key)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // TODO: >>>>>>>>>>>>> PLEASE check if you can close cursor and than deploy it.
        try
        {
            // This will ensure that who ever called the method will
            // get the data.
            return cursor;
        }finally
        {
            // This will ensure to close the connection
            // after the data have been delivered.
            //if(cursor != null){
            //    cursor.close();
            //}
        }
    }

    public Cursor getItemById(Table table, String key)
    {
        // Init
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Field> fields = table.getFields();
        int size = fields.size();

        // Getting ready fields name on an array.
        String[] fieldsToString = new String[size];
        for(int i = 0; i < size; i++)
        {
            fieldsToString[i] = fields.get(i).getName();
        }


        // Pulling data from DB
        Cursor cursor = db.query(table.getName(),
                fieldsToString,
                 DatabaseHolder.KEY_KEY +"=?",
                new String[]{String.valueOf(key)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // TODO: >>>>>>>>>>>>> PLEASE check if you can close cursor and than deploy it.
        try
        {
            // This will ensure that who ever called the method will
            // get the data.
            return cursor;
        }finally
        {
            // This will ensure to close the connection
            // after the data have been delivered.
            //if(cursor != null){
            //    cursor.close();
            //}
        }
    }

    public Cursor getItemByField(Table table, Field field, String value)
    {
        // Init
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Field> fields = table.getFields();
        int size = fields.size();

        // Getting ready fields name on an array.
        String[] fieldsToString = new String[size];
        for(int i = 0; i < size; i++)
        {
            fieldsToString[i] = fields.get(i).getName();
        }

        // Pulling data from DB
        Cursor cursor = db.query(table.getName(),
                fieldsToString,
                field.getName() + "=?",
                new String[]{String.valueOf(value)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // TODO: >>>>>>>>>>>>> PLEASE check if you can close cursor and than deploy it.
        try
        {
            // This will ensure that who ever called the method will
            // get the data.
            return cursor;
        }finally
        {
            // This will ensure to close the connection
            // after the data have been delivered.
            //if(cursor != null){
            //    cursor.close();
            //}
        }
    }

    // Thats the get by field (int).
    // need to implement by string or such if necessary
    public Cursor getAllItemsByFieldByKey(Table table, Field field, String key)
    {
        // Select All Query
        String selectQuery = "SELECT * FROM " + table.getName() + " WHERE " + field.getName() + " = " + "\"" + key + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // TODO: >>>>>>>>>>>>> PLEASE check if you can close cursor and than deploy it.
        try
        {
            // This will ensure that who ever called the method will
            // get the data.
            return cursor;
        }finally
        {
            // This will ensure to close the connection
            // after the data have been delivered.
            // but it crashes.. lol
            //cursor.close();
        }
    }

    private String getCreateTableSyntax(Table table)
    {
        // To know if the last field we inserting
        boolean separator = true;

        String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS"
                        + SPACE
                        + table.getName()
                        + "("
                ;

        for(Field single: table.getFields())
        {
            // If separator is FALSE than it means
            // this is the first run of the loop and a separator
            // should not be applied.
            // When it's TRUE than it's not the first run and a
            // separator should be applied.
            if(!separator)
            {
                CREATE_TABLE += COMMA;
            }else
            {
                if(!separator) break;
                separator = false;
            }

            // There is configuration to apply.
            if(single.getConfiguration() == null)
            {
                CREATE_TABLE +=
                        single.getName()
                                + SPACE
                                + single.getType().getValue();
            }
            else
            {
                CREATE_TABLE +=
                        single.getName()
                                + SPACE
                                + single.getType().getValue()
                                + SPACE
                                + single.getConfiguration();
            }
        }

        CREATE_TABLE += ")";

        return CREATE_TABLE;
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Leave empty for bug free software.
        // This method run only once and this
        // is when the database is being created for the first time.
        // This method remains empty because I handle the creation of the
        // Database and tables on my own.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: figure out the fuck is that
    }

    public void updateWithLocalUpdate(LocalUpdate localUpdate)
    {
        localUpdateDBH.insert(localUpdate, true);
    }
}
