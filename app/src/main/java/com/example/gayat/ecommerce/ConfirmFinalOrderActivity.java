package com.example.gayat.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ConfirmFinalOrderActivity extends AppCompatActivity
{
    private EditText nameET, phoneET, addressET, cityET;
    private Button confirmBTN;

    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");

        confirmBTN = (Button) findViewById(R.id.confirm_button);
        nameET = (EditText) findViewById(R.id.shipment_name);
        phoneET = (EditText) findViewById(R.id.shipment_phonenumber);
        addressET = (EditText) findViewById(R.id.shipment_address);
        cityET = (EditText) findViewById(R.id.shipment_city);
    }
}
