package com.example.sonisteel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeeDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<OrderDetails> orderDetails;
    private OrderAdapter orderAdapter;

    DatabaseReference refe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_details);




        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderDetails = new ArrayList<OrderDetails>();

        refe = FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        refe.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                OrderDetails order=dataSnapshot1.getValue(OrderDetails.class);
                orderDetails.add(order);
            }
            orderAdapter=new OrderAdapter(SeeDetails.this,orderDetails);
            recyclerView.setAdapter(orderAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
