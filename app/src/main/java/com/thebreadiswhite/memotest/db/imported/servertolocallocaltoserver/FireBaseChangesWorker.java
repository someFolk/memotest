package com.morsela.tel.db.servertolocallocaltoserver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.firebase.database.*;
import com.morsela.tel.application.AppDataDBH;
import com.morsela.tel.badge.BadgeDBH;
import com.morsela.tel.colleague.ColleagueDBH;
import com.morsela.tel.contact.ContactDBH;
import com.morsela.tel.course.course.CourseDBH;
import com.morsela.tel.course.option.OptionDBH;
import com.morsela.tel.course.question.QuestionDBH;
import com.morsela.tel.crafttools.crafttool.CraftToolDBH;
import com.morsela.tel.crafttools.toolbox.ToolboxDBH;
import com.morsela.tel.db.DatabaseHolderInstances;
import com.thebreadiswhite.memotest.db.instance.firebase.FirebaseInstance;
import com.morsela.tel.db.DatabaseHolder;
import com.morsela.tel.machine.MachineDBH;
import com.morsela.tel.participant.ParticipantDBH;
import com.morsela.tel.positionable.PositionableDBH;
import com.morsela.tel.task.TaskDBH;
import com.morsela.tel.taskpackage.TaskPackage;
import com.morsela.tel.taskpackage.TaskPackageDBH;
import com.morsela.tel.troubleshooting.TroubleshootingDBH;
import com.morsela.tel.user.UserDBH;

public class FireBaseChangesWorker extends Service
{

    /// LIFE CYCLE ///

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    DatabaseHolderInstances databaseHolderInstances;
    private boolean firstChild = true;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        databaseHolderInstances = new DatabaseHolderInstances(context);
        listenForChanges();
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
        FirebaseInstance.getDatabase().getReference().removeEventListener(eventListener);
    }

    @Override
    public void onStart(Intent intent, int startid) {
        //Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }

    public void listenForChanges()
    {
        LocalUpdateDBH localUpdateDBH = new LocalUpdateDBH(context);

        String lastKey = localUpdateDBH.getLastItemsOrderByTimestampWithLimit(1).getFirebaseKey();


        DatabaseReference rootRef = FirebaseInstance.getDatabase().getReference(LocalUpdateDBH.TABLE_NAME);

        Query query = rootRef.orderByKey().startAt(lastKey);

        Log.d("__________1________", lastKey);

        query.addChildEventListener(eventListener);
    }

    public void performUpdate(LocalUpdate localUpdate)
    {
        String node = localUpdate.getNode();
        DatabaseHolder currentdbh = (DatabaseHolder) databaseHolderInstances.getDatabaseHolder(node);

        boolean isExist = currentdbh.checkIsDataAlreadyInDBorNot(localUpdate.getTargetKey());
        if(! isExist)
        {
            currentdbh.firebasePullByKeyAndInsertLocally(localUpdate.getTargetKey(), localUpdate.getType());

            LocalUpdateDBH localUpdateDBH = new LocalUpdateDBH(getApplicationContext());
            localUpdate.setIsDone(true);
            localUpdateDBH.insertLocally(localUpdate);
        }
    }

    // The event listener variable.
    ChildEventListener eventListener = new ChildEventListener()
    {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
            if(firstChild)
            {
                firstChild = false;
                return;
            }

            LocalUpdate single = (LocalUpdate) dataSnapshot.getValue(LocalUpdate.class);
            if(single != null)
            {
                single.setFirebaseKey(dataSnapshot.getKey());
                Log.d("_______________________", single.getFirebaseKey() + " / " + single.getTargetKey());
            }
            performUpdate(single);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
            //Toast.makeText(context, "Changed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
        {
            //Toast.makeText(context, "removed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
            //Toast.makeText(context, "moved", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            //Toast.makeText(context, "cancelled", Toast.LENGTH_LONG).show();
        }
    };

}
