package com.example.user.rheeddaly;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    int price = 0;
    EditText edit_text_name;
    CheckBox addWhippedCream;
    CheckBox addChocolate;
    TextView quantity_text_view;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantity_text_view = (TextView)findViewById(R.id.quantity_text_view);
        quantity_text_view.setText("2");
    }

    public void increment(View view) {
        incrementQuantity(quantity);
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        decrementQuantity(quantity);
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {


        edit_text_name = (EditText) findViewById(R.id.edit_text_name);
        name = edit_text_name.getText().toString();

        addWhippedCream = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        addChocolate = (CheckBox)findViewById(R.id.chocolate_checkbox);


        boolean hasWhippedCream = addWhippedCream.isChecked();
        boolean hasChocolate = addChocolate.isChecked();

        price  = CalculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        restartForm();
    }

    public void restartForm() {
        edit_text_name.setText("");
        addWhippedCream.setChecked(false);
        addChocolate.setChecked(false);
        quantity_text_view.setText("2");
        quantity = 2;

    }

    public String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate)
    {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";

        return priceMessage;
    }

    public int CalculatePrice(boolean hasWhippedCream, boolean hasChocolate) {

        int basePrice = 5;

        if(hasWhippedCream) {
            basePrice += 1;
        }

        if(hasChocolate) {
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    public void incrementQuantity(int number) {
        if(quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrementQuantity(int number) {
        if(quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();;
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {

        quantity_text_view.setText("" + number);
    }
}
