package com.example.sonisteel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import static com.example.sonisteel.OrderAdapter.*;

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
            new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(orderAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ItemTouchHelper.SimpleCallback itemTouch =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            orderDetails.remove(viewHolder.getAdapterPosition());
            orderAdapter.notifyDataSetChanged();






        }
    };


}
