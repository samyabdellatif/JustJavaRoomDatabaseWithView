/**
 * A Repository is a class that abstracts access to multiple data sources.
 * The Repository is not part of the Architecture Components libraries,
 * but is a suggested best practice for code separation and architecture.
 * A Repository class handles data operations.
 * It provides a clean API to the rest of the app for app data.
 */
package com.example.android.justjava;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;
/**
 * Create a public class called WordRepository.
 */
public class OrderRepository {

    /** Add member variables for the DAO and the list of CoffeeOrder objects. */
    private  DaoAccess mDaoAccess;
    private LiveData<List<CoffeeOrder>> mAllCoffeeOrder;

    /**Add a constructor that gets a handle to the database and initializes the member variables.*/
    OrderRepository(Application application) {
        OrdersDatabase db = OrdersDatabase.getDatabase(application);
        mDaoAccess = db.daoAccess();
        mAllCoffeeOrder = mDaoAccess.getAllOrders();
    }
    /**
     * Add a wrapper for getAllWords().
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     */
    LiveData<List<CoffeeOrder>> getmAllCoffeeOrder() {
        return mAllCoffeeOrder;
    }

    /**
     * Add a wrapper for the insert() method.
     * You must call this on a non-UI thread or your app will crash.
     * Room ensures that you don't do any long-running operations on the main thread, blocking the UI.
     * @param order : CoffeeOrder object to insert into the orders_table
     */
    public void insertOrder(CoffeeOrder order) {
        new insertAsyncTask(mDaoAccess).execute(order);
    }
    public void deleteAll() {
        new deleteAsyncTask(mDaoAccess).execute();
    }
    /**
     * AsyncTask enables proper and easy use of the UI thread. This class allows you to perform
     * background operations and publish result on the UI thread without having to manipulate
     * threads and/or handlers.
     * AsyncTask must be subclassed to be used. The subclass will override at least one method
     * in this case we override doInBackground method.
     * insertAsyncTask : inserts an order to the database.
     * deleteAsyncTask : deletes all the contents of the database.
     */
    private static class insertAsyncTask extends android.os.AsyncTask<CoffeeOrder, Void, Void> {

        private DaoAccess mAsyncTaskDao;

        insertAsyncTask(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CoffeeOrder... params) {
            mAsyncTaskDao.insertOrder(params[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<CoffeeOrder, Void, Void> {

        private DaoAccess mAsyncTaskDao;

        deleteAsyncTask(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CoffeeOrder... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
