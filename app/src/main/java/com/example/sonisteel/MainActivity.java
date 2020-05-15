package com.example.sonisteel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fb;
        fb=findViewById(R.id.fab1);

        final TextView quan1,quan2,quan3;

        ImageButton add1,add2,add3,remove1,remove2,remove3;






        quan1=findViewById(R.id.q1);
        quan2=findViewById(R.id.q2);
        quan3=findViewById(R.id.q3);

        add1=findViewById(R.id.add1);
        add2=findViewById(R.id.add2);
        add3=findViewById(R.id.add3);


        remove1=findViewById(R.id.remove1);
        remove2=findViewById(R.id.remove2);
        remove3=findViewById(R.id.remove3);













        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 int quantity1=Integer.parseInt(quan1.getText().toString());

                quantity1=quantity1+1;
                quan1.setText(Integer.toString(quantity1));

            }
        });

        remove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity1=Integer.parseInt(quan1.getText().toString());
                if(quantity1>0)
                quantity1=quantity1-1;
                else
                    quantity1=0;
                quan1.setText(Integer.toString(quantity1));

            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity2=Integer.parseInt(quan2.getText().toString());

                quantity2=quantity2+1;
                quan2.setText(Integer.toString(quantity2));

            }
        });

        remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity2=Integer.parseInt(quan2.getText().toString());
                if(quantity2>0)
                    quantity2=quantity2-1;
                else
                    quantity2=0;
                quan2.setText(Integer.toString(quantity2));

            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity3=Integer.parseInt(quan3.getText().toString());

                quantity3=quantity3+1;
                quan3.setText(Integer.toString(quantity3));

            }
        });

        remove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity3=Integer.parseInt(quan3.getText().toString());
                if(quantity3>0)
                    quantity3=quantity3-1;
                else
                    quantity3=0;
                quan3.setText(Integer.toString(quantity3));

            }
        });







fb.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        HashMap<String,Integer> hm=new HashMap<>();

        DatabaseReference dr;
        dr=FirebaseDatabase.getInstance().getReference().child("CurrentOrder");
        if(Integer.parseInt(quan1.getText().toString())>0)
        {
            CurrentOrder co=new CurrentOrder();
            hm.put("Stainless Kadhai",Integer.parseInt(quan1.getText().toString()));
            co.setHashMap(hm);
            dr.setValue(co);
        }
        if(Integer.parseInt(quan2.getText().toString())>0)
        {
            CurrentOrder co=new CurrentOrder();
            hm.put("Set of Tops",Integer.parseInt(quan2.getText().toString()));
            co.setHashMap(hm);
            dr.setValue(co);
        }
        if(Integer.parseInt(quan3.getText().toString())>0)
        {
            CurrentOrder co=new CurrentOrder();

            hm.put("Handi With Copper Base",Integer.parseInt(quan3.getText().toString()));
            co.setHashMap(hm);
            dr.setValue(co);
        }
        Intent i=new Intent(MainActivity.this,Cart.class);

        startActivity(i);
    }
});







    }
}
