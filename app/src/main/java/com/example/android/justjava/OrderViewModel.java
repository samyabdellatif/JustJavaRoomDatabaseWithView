/**
 * A ViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration
 * changes. Separating your app's UI data from your Activity and Fragment classes lets you better
 * follow the single responsibility principle:
 * Your activities and fragments are responsible for drawing data to the screen, while your
 * ViewModel can take care of holding and processing all the data needed for the UI.
 */
package com.example.android.justjava;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * a ViewModel class that extends AndroidViewModel
 */
public class OrderViewModel extends AndroidViewModel {
    /**
     * a private member variable to hold a reference to the repository.
     */
    private OrderRepository mRepository;
    /**
     *a private LiveData member variable to cache the list of CoffeeOrder objects.
     */
    private LiveData<List<CoffeeOrder>> mAllCoffeeOrder;
    /**
     * a constructor that gets a reference to the repository
     * and gets the list of words from the repository.
     */
    public OrderViewModel(@NonNull Application application) {
        super(application);
        mRepository = new OrderRepository(application);
        mAllCoffeeOrder = mRepository.getmAllCoffeeOrder();
    }
    /**
     * a "getter" method for all the words. This completely hides the implementation from the UI.
     */
    LiveData<List<CoffeeOrder>> getmAllCoffeeOrder(){
        return mAllCoffeeOrder;
    }
    /**
     * a wrapper insert() method that calls the Repository's insert() method. In this way,
     * the implementation of insert() is completely hidden from the UI.
     */
    public void insertOrder(CoffeeOrder coffeeOrder){
        mRepository.insertOrder(coffeeOrder);
    }

    /**
     * a wrapper insert() method that calls the Repository's insert() method. In this way,
     * the implementation of insert() is completely hidden from the UI.
     */
    public void deleteAll(){
        mRepository.deleteAll();
    }


}
