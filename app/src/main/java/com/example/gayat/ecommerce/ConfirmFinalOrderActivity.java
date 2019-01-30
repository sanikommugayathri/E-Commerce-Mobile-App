package com.example.gayat.ecommerce;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gayat.ecommerce.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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
        Toast.makeText(this, "Total Price = " + totalAmount, Toast.LENGTH_SHORT).show();

        confirmBTN = (Button) findViewById(R.id.confirm_button);
        nameET = (EditText) findViewById(R.id.shipment_name);
        phoneET = (EditText) findViewById(R.id.shipment_phonenumber);
        addressET = (EditText) findViewById(R.id.shipment_address);
        cityET = (EditText) findViewById(R.id.shipment_city);


        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Check();
            }
        });
    }


    private void Check()
    {
        if(TextUtils.isEmpty(nameET.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Full Name", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phoneET.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Phone Number", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(addressET.getText().toString()))
        {
            Toast.makeText(this, "Please provide your Address", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(cityET.getText().toString()))
        {
            Toast.makeText(this, "Please provide your City", Toast.LENGTH_SHORT).show();
        }

        else
        {
            ConfirmOrder();
        }
    }


    private void ConfirmOrder()
    {
        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameET.getText().toString());
        ordersMap.put("phone", phoneET.getText().toString());
        ordersMap.put("address", addressET.getText().toString());
        ordersMap.put("city", cityET.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "Not Shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ConfirmFinalOrderActivity.this, "Your order has been placed successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                }

            }
        });
    }
}
