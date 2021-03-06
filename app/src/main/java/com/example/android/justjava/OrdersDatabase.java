package com.example.android.justjava;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

@Database(entities = {CoffeeOrder.class}, version = 1,exportSchema = false)
public abstract class OrdersDatabase extends RoomDatabase {
    /**Define the DAOs that work with the database.*/
    public abstract DaoAccess daoAccess();
    /**
     * Make the databse a singleton to prevent having
     * multiple instances of the database opened at the same time.
     */
    private static volatile OrdersDatabase INSTANCE;
    static OrdersDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OrdersDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OrdersDatabase.class, "Orders_db")
                            .addCallback(sOrdersDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * To populate the database in the first run.
     */
    private static OrdersDatabase.Callback sOrdersDatabaseCallBack =
            new OrdersDatabase.Callback(){
                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    /**
     * the AsyncTask that deletes the contents of the database, then populates it with
     * a single order  id = 0 , (sami, 1, hasWhippedCream,hasChocolate)
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DaoAccess mDao;

        PopulateDbAsync(OrdersDatabase db) {
            mDao = db.daoAccess();
        }
        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            return null;
        }
    }
}