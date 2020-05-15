package com.example.sonisteel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("OrderDetails");



        Intent i=getIntent();
       final int noofkadhai=i.getIntExtra("STAINLESSKADHAI",0);
        final int noofhandi=i.getIntExtra("HANDI",0);
        final int nooftops=i.getIntExtra("SETOFTOPS",0);

        final EditText name,add,phone;
        final Button button;

        name=findViewById(R.id.e1);
        add=findViewById(R.id.e2);
        phone=findViewById(R.id.e3);
        button=findViewById(R.id.btn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=0;
                OrderDetails orderDetails=new OrderDetails();
                orderDetails.setName(name.getText().toString());
                orderDetails.setAdd(add.getText().toString());
                orderDetails.setPhone(Integer.parseInt(phone.getText().toString()));

                HashMap<String,Integer> hashMap=new HashMap<>();
                HashMap<String,Object> hashimage=new HashMap<>();

                if(noofkadhai!=0)
                {
                    hashMap.put("Stain less Kadhai",noofhandi);
                    hashimage.put("Stain less Kadhai",R.drawable.kadai);

                    a=a+noofkadhai*200;
                }

                if(noofhandi!=0)
                {
                    hashMap.put("Handi with Copper base",noofhandi);
                    hashimage.put("Handi with copper base",R.drawable.handi);
                    a=a+noofhandi*300;
                }
                if(nooftops!=0)
                {
                    hashMap.put("Set Of Tops",nooftops);
                    hashimage.put("Set Of Tops",R.drawable.stainless);
                    a=a+nooftops*800;
                }

                orderDetails.setHm(hashMap);
                orderDetails.setTotal(a);

                dr.child("Order").setValue(orderDetails);

            }
        });
















    }

}
