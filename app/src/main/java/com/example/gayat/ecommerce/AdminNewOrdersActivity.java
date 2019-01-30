package com.example.gayat.ecommerce;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gayat.ecommerce.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity
{
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef, AdminOrders.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
               new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                   @Override
                   protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model)
                   {
                       holder.userName.setText("Name: " + model.getName());
                       holder.userPhoneNumber.setText("Phone: " + model.getPhone());
                       holder.userTotalPrice.setText("Total Amount: " + model.getTotalAmount());
                       holder.userDateTime.setText("Order at: " + model.getDate() + " " + model.getTime());
                       holder.userShippingAddress.setText("Shipping Address: " + model.getAddress() + ", " + model.getCity());

                       holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v)
                           {
                               String uID = getRef(position).getKey();

                               Intent intent = new Intent(AdminNewOrdersActivity.this, AdminUserProductsActivity.class);
                               intent.putExtra("uid", uID);
                               startActivity(intent);
                           }
                       });
                   }

                   @NonNull
                   @Override
                   public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                   {
                       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                       return new AdminOrdersViewHolder(view);
                   }
               };

        ordersList.setAdapter(adapter);
        adapter.startListening();

    }


    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        private Button ShowOrdersBtn;

        public AdminOrdersViewHolder(View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            ShowOrdersBtn = itemView.findViewById(R.id.show_products_btn);
        }
    }
}
