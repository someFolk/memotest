package com.thebreadiswhite.memotest.modules.stack;

import android.content.Context;
import com.thebreadiswhite.memotest.db.DatabaseHolder;

import java.util.List;

public class MemotestStackDBH extends DatabaseHolder<MemotestStack>
{
    public static final String TABLE_NAME = "stack";
    public static final String SERVER_APP_PREFIX = "memotest";
    public static final Class BASE_CLASS = MemotestStack.class;

    // The dao we use to
    MemotestStackDAO dao;

    @SuppressWarnings("unchecked")
    public MemotestStackDBH(Context context)
    {
        super(
                context,
                "MemotestStackDBH",
                TABLE_NAME,
                BASE_CLASS
             );
        dao = db.getRoomInstance().memotestStack();
        super.init((DatabaseHolder)this,BASE_CLASS, SERVER_APP_PREFIX);
    }

    @Override
    public MemotestStack selectById(int id)
    {
        return dao.getStackById(id);
    }

    @Override
    public List<MemotestStack> selectAll()
    {
        return dao.getAllStacks();
    }

    @Override
    public void update(MemotestStack stack)
    {
        dao.update(stack);
    }

    @Override
    public int insert(MemotestStack stack)
    {
        String key = firestoreInsert(stack);
        stack.setServerKey(key);
        long id = dao.insert(stack);
        return (int) id;

    }

    @Override
    public void delete(MemotestStack stack)
    {
        dao.deleteStack(stack);
    }
}
