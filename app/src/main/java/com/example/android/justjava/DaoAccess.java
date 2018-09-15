package com.example.android.justjava;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {
    /**
     * declaring a method to insert entry
     * the onConflict strategy REPLACE will
     * replace the entity with (if found) an entity
     * in the database with the same primary key.
     * @param order :object of type CoffeeOrder (table entity)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(CoffeeOrder order);

    /**
     * query to fitch all the entries from a table
     * @return list of CoffeeOrder objects
     */
    @Query("SELECT * FROM orders_table")
    LiveData<List<CoffeeOrder>> getAllOrders();

    /**
     * Delete all the data in orders_table
     */
    @Query("DELETE FROM orders_table")
    void deleteAll();
}
