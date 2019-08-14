package com.thebreadiswhite.memotest.modules.memotest;

import android.content.Context;
import com.thebreadiswhite.memotest.db.DatabaseHolder;

import java.util.List;

public class MemotestDBH extends DatabaseHolder<Memotest>
{
    public static final String TABLE_NAME = "memotest";
    public static final String SERVER_APP_PREFIX = "memotest";
    public static final Class BASE_CLASS = Memotest.class;

    // The dao we use to
    MemotestDao dao;

    @SuppressWarnings("unchecked")
    public MemotestDBH(Context context)
    {
        super(
                context,
                "MemotestStackDBH",
                TABLE_NAME,
                Memotest.class
        );

        // Call immediately
        super.init((DatabaseHolder)this,BASE_CLASS, SERVER_APP_PREFIX);
        dao = db.getRoomInstance().memotest();

    }

    public List<Memotest> selectAllChilds(int parentID)
    {
        return dao.getAllChilds(parentID);
    }

    @Override
    public Memotest selectById(int id)
    {
        return dao.getById(id);
    }

    @Override
    public List<Memotest> selectAll()
    {
        return dao.getAll();
    }

    @Override
    public void update(Memotest memotest)
    {
        dao.update(memotest);
    }

    @Override
    public int insert(Memotest memotest)
    {
        String key = firestoreInsert(memotest);
        memotest.setServerKey(key);
        long id = dao.insert(memotest);
        return (int) id;
    }

    @Override
    public void delete(Memotest memotest)
    {
        dao.delete(memotest);
    }

    public int count()
    {
        return dao.count();
    }
}
