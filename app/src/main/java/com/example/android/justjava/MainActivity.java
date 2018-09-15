/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.Random;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * defining a context as static to use the context methods
     * inside the non-activity classes.
     */
    public static Context context = null;
    /**
     * Creating member variable for the ViewModel
     */
    private OrderViewModel orderViewModel;
    int quantity = 1; //initial quantity.

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        /**
         * Add the RecyclerView
         */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        final OrderListAdapter adapter = new OrderListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /**
         * get a ViewModel from the ViewModelProvider.
         */
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        /**
         * add an observer for the LiveData returned by getAllOrders().
         * The onChanged() method fires when the observed data changes and the activity is in the foreground.
         */
        orderViewModel.getmAllCoffeeOrder().observe(this, new Observer<List<CoffeeOrder>>() {
            @Override
            public void onChanged(@Nullable final List<CoffeeOrder> orders) {
                // Update the cached copy of the words in the adapter.
                adapter.setmCoffeeOrder(orders);
            }
        });
    }
    /**
     * this method deletes all the content of the database table
     */
    public void clearHistory(View view){
        orderViewModel.deleteAll();
    }
    /**
     * This method increase the quantity by 1.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast message = Toast.makeText(getApplicationContext(),
                    "you can NOT order more than 100 cup",
                    Toast.LENGTH_SHORT);
            message.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            message.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity();
    }

    /**
     * This method decrease the quantity by 1.
     */
    public void decrement(View view) {
        //add condition to avoid negative numbers for quantity
        if (quantity == 1) {
            Toast message = Toast.makeText(getApplicationContext(),
                    "you can NOT order less than 1 cup",
                    Toast.LENGTH_SHORT);
            message.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            message.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity();
    }
    /**
     * This method submits an order.
     */
    public void submitOrder(View view) {
        /**
         * get user inputs name , with cream? , with chocolate?
         */
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        /** creating new order instance from CoffeeOrder.*/
        CoffeeOrder newOrder = new CoffeeOrder();
        /** set the order values.*/
        newOrder.setOrder(nameEditText.getText().toString(),
                quantity,
                whippedCreamCheckBox.isChecked(),
                chocolateCheckBox.isChecked());
        /** generate the order id */
        Random r = new Random();
        newOrder.setOrderID(r.nextInt());
        /**
         * get the order summary string.
         */
        String orderSummary = newOrder.getOrderSummary();
        /** display the order summary string. */
        TextView order_summary_text_view = (TextView) findViewById(R.id.orderSummary_text_view);
        order_summary_text_view.setText(orderSummary);
        /**
         * send the order by mail if the checkbox send_mail is checked
         */
        CheckBox sendMail = (CheckBox) findViewById(R.id.send_mail);
        boolean doSendMail = sendMail.isChecked();
        if (doSendMail) {
            String[] mailto = new String[1];
            mailto[0] = "coffeeHouse@gmail.com";
            composeEmail(mailto, "Ordered by " + newOrder.getCostumerName(), orderSummary);
        }
        /**
         * this line will insert the order object to the database
         */
        orderViewModel.insertOrder(newOrder);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(quantity));
    }

    /**
     * this method creates the email message
     * @param addresses : array of strings : of email addresses to send the message to
     * @param subject : string : the subject of the email
     * @param message : string : the body of the email
     */
    public void composeEmail(String[] addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses); //email address to send to
        intent.putExtra(Intent.EXTRA_SUBJECT, subject); // subject of the email
        intent.putExtra(Intent.EXTRA_TEXT, message); // email body
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}