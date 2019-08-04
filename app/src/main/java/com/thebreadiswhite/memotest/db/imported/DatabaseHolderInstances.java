package com.morsela.tel.db;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.morsela.tel.badge.BadgeDBH;
import com.morsela.tel.colleague.Colleague;
import com.morsela.tel.colleague.ColleagueDBH;
import com.morsela.tel.contact.ContactDBH;
import com.morsela.tel.course.course.CourseDBH;
import com.morsela.tel.course.option.OptionDBH;
import com.morsela.tel.course.question.QuestionDBH;
import com.morsela.tel.crafttools.crafttool.CraftToolDBH;
import com.morsela.tel.crafttools.toolbox.ToolboxDBH;
import com.morsela.tel.db.instance.local.LocalDatabase;
import com.morsela.tel.db.servertolocallocaltoserver.LocalUpdateDBH;
import com.morsela.tel.machine.MachineDBH;
import com.morsela.tel.participant.ParticipantDBH;
import com.morsela.tel.positionable.PositionableDBH;
import com.morsela.tel.procedure.ProcedureDBH;
import com.morsela.tel.task.TaskDBH;
import com.morsela.tel.taskpackage.TaskPackageDBH;
import com.morsela.tel.troubleshooting.TroubleshootingDBH;
import com.morsela.tel.user.UserDBH;

import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseHolderInstances
{

    HashMap<String, DatabaseHolder> dbh;
    LocalDatabase localDatabase;
    LocalUpdateDBH localUpdateDBH;
    ArrayList<String> runInstallation;

    public DatabaseHolderInstances(Context context)
    {

        localDatabase = LocalDatabase.getInstance(context);
        localUpdateDBH = new LocalUpdateDBH(context);

        // Installation
        runInstallation = new ArrayList<>();
        runInstallation.add(TaskPackageDBH.TABLE_NAME);
        runInstallation.add(TaskDBH.TABLE_NAME);
        runInstallation.add(ContactDBH.TABLE_NAME);
        runInstallation.add(ColleagueDBH.TABLE_NAME);

        // All DBH goes here
        dbh = new HashMap<>();

        dbh.put( UserDBH.getTableName(), new UserDBH(context));
        dbh.put( BadgeDBH.getTableName(), new BadgeDBH(context));
        dbh.put( ColleagueDBH.getTableName(), new ColleagueDBH(context));
        dbh.put( ContactDBH.getTableName(), new ContactDBH(context));
        dbh.put( CourseDBH.getTableName(), new CourseDBH(context));
        dbh.put( OptionDBH.getTableName(), new OptionDBH(context));
        dbh.put( QuestionDBH.getTableName(), new QuestionDBH(context));
        dbh.put( MachineDBH.getTableName(), new MachineDBH(context));
        dbh.put( ParticipantDBH.getTableName(), new ParticipantDBH(context));
        dbh.put( TaskDBH.getTableName(), new TaskDBH(context));
        dbh.put( TaskPackageDBH.TABLE_NAME, new TaskPackageDBH(context));
        dbh.put( PositionableDBH.TABLE_NAME, new PositionableDBH(context));
        dbh.put( TroubleshootingDBH.getTableName(), new TroubleshootingDBH(context));
        dbh.put( CraftToolDBH.getTableName(), new CraftToolDBH(context));
        dbh.put( ToolboxDBH.getTableName(), new ToolboxDBH(context));
        dbh.put( ProcedureDBH.getTableName(), new ProcedureDBH(context));
        //dbh.put(AppDataDBH.TABLE_NAME, )

    }


    // 2. you need to modify the observer to a firebase observer to it will be easier to work with. (containing the node, key, child and such to work best with firebase.
    // 3. you are awesome! look at this code, you made it :)
    // 4. I LOVE YOU!
    // ^ LOVE you too babe

    // Part of this class is to get all data from the server once it is first run.
    public boolean getAllServerDataOnce(Context context)
    {
        // Run installation
        for(DatabaseHolder holder : dbh.values())
        {
            for(String dbhName : runInstallation)
            {
                if(holder.getTable().getName().equals(dbhName))
                {
                    holder.firebaseGetAllDataOnce(true);
                }
            }
        }

        // Fetch Server Data
        for(DatabaseHolder holder : dbh.values())
        {
            holder.firebaseGetAllDataOnce(false);
        }

        localUpdateDBH.firebaseGetAllDataOnce(true);
        return true;
    }

    public DatabaseHolder getDatabaseHolder(String tableName)
    {
        return dbh.get(tableName);
    }



}
