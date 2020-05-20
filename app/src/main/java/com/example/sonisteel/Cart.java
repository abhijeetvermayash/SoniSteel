package com.example.sonisteel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("OrderDetails");

        List<HashMap<String,String>> alist=new ArrayList<>();



        Intent i=getIntent();
       final int noofkadhai=i.getIntExtra("STAINLESSKADHAI",0);
        final int noofhandi=i.getIntExtra("HANDI",0);
        final int nooftops=i.getIntExtra("SETOFTOPS",0);
        final int noofdish=i.getIntExtra("SETOFDISH",0);



        final EditText name,add,phone;
        final Button button;
        ListView lst;
        TextView txt;
        final RadioGroup rgp;
        final RadioButton cod,upi;



        name=findViewById(R.id.e1);
        add=findViewById(R.id.e2);
        phone=findViewById(R.id.e3);
        button=findViewById(R.id.btn);
        lst=findViewById(R.id.lst);
        txt=findViewById(R.id.totp);
        cod=findViewById(R.id.cod);
        upi=findViewById(R.id.upi);
        rgp=findViewById(R.id.radiogrp);


        int a=0;



        if(noofkadhai!=0)
        {
            HashMap<String,String> hm=new HashMap<>();
            hm.put("Product name","Stainless Steel Kadhai");
            hm.put("Quantity",Integer.toString(noofkadhai));
            hm.put("image",Integer.toString(R.drawable.kadai));
            alist.add(hm);
            a=a+noofhandi*250;
        }
        if(noofhandi!=0)
        {    HashMap<String,String> hm=new HashMap<>();
            hm.put("Product name","Handi with Copper Base(5ps)");
            hm.put("Quantity",Integer.toString(noofhandi));
            hm.put("image",Integer.toString(R.drawable.handi));
            alist.add(hm);
            a=a+noofhandi*300;
        }
        if(nooftops!=0)
        {     HashMap<String,String> hm=new HashMap<>();
            hm.put("Product name","Set of Tops(5ps)");
            hm.put("Quantity",Integer.toString(nooftops));
            hm.put("image",Integer.toString(R.drawable.stainless));
            alist.add(hm);
            a=a+nooftops*500;

        }
        if(noofdish!=0)
        {     HashMap<String,String> hm=new HashMap<>();
            hm.put("Product name","Set of Dish");
            hm.put("Quantity",Integer.toString(noofdish));
            hm.put("image",Integer.toString(R.drawable.stainlesssteelset));
            alist.add(hm);
            a=a+nooftops*350;

        }
        txt.setText(Integer.toString(a));

        String from[]={"Product name","Quantity","image"};

        int[] to={R.id.productname,R.id.quantity,R.id.image1};

        SimpleAdapter adapter=new SimpleAdapter(getBaseContext(),alist,R.layout.listoforder,from,to);
        lst.setAdapter(adapter);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(name.getText())&&TextUtils.isEmpty(add.getText())&&TextUtils.isEmpty(phone.getText())&&rgp==null)
                {
                    Toast.makeText(Cart.this, "Please fill the required details", Toast.LENGTH_SHORT).show();
                }
                if(cod.isChecked()==false&&upi.isChecked()==false)
                {
                    Toast.makeText(Cart.this, "Please select any one mode of payment", Toast.LENGTH_SHORT).show();
                }



                if(TextUtils.isEmpty(name.getText()))
                {
                    name.setError("Please enter your name");
                }
                if(TextUtils.isEmpty(add.getText()))
                {
                    add.setError("Please enter your full Delivery Address");
                }
                if(TextUtils.isEmpty(phone.getText()))
                {



                    phone.setError("Please Enter Your Phone number");
                }

                else {
                    int a = 0;
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setName(name.getText().toString().trim());
                    orderDetails.setAdd(add.getText().toString().trim());

                    Long Phone1=Long.parseLong(phone.getText().toString().trim());
                    orderDetails.setPhone(Phone1);

                    HashMap<String, Integer> hashMapis = new HashMap<>();
                    HashMap<String, Object> hashimage = new HashMap<>();

                    if (noofkadhai != 0) {
                        hashMapis.put("Stainless Steel Kadhai", noofhandi);
                        hashimage.put("Stainless Steel Kadhai", R.drawable.kadai);

                        a = a + noofkadhai * 250;
                    }

                    if (noofhandi != 0) {
                        hashMapis.put("Handi with Copper base(Set of 5)", noofhandi);
                        hashimage.put("Handi with copper base", R.drawable.handi);
                        a = a + noofhandi * 300;
                    }
                    if (nooftops != 0) {
                        hashMapis.put("Stainless Steel Tops(Set of 5)", nooftops);
                        hashimage.put("Set Of Tops", R.drawable.stainless);
                        a = a + nooftops * 500;
                    }
                    if (noofdish != 0) {
                        hashMapis.put("Stainless Steel Dish(Set of 3)", noofdish);
                        hashimage.put("Set Of Dish", R.drawable.stainlesssteelset);
                        a = a + nooftops * 350;
                    }



                    orderDetails.setHm(hashMapis);
                    orderDetails.setTotal(a);
                    String ModeofPay="";


                    if (cod.isChecked()) {
                        ModeofPay="Cash On Delivery";
                        orderDetails.setModeOfPayment(ModeofPay);

                        dr.push().setValue(orderDetails);


                        DatabaseReference dr1 = FirebaseDatabase.getInstance().getReference().child("OrderDetailsForFutureReference");
                        dr1.push().setValue(orderDetails);

                        Intent i = new Intent(Cart.this, OrderPlaced.class);
                        startActivity(i);
                    }
                    if (upi.isChecked()) {
                        ModeofPay="Pay Through UPI";
                        Intent i = new Intent(Cart.this, UPIpage.class);
                        i.putExtra("STAINLESSKADHAI", noofkadhai);
                        i.putExtra("SETOFTOPS", nooftops);
                        i.putExtra("HANDI", noofhandi);
                        i.putExtra("SETOFDISH",noofdish);
                        i.putExtra("NAME", name.getText().toString());
                        i.putExtra("ADDRESS", add.getText().toString());
                        i.putExtra("PHONE", Long.parseLong(phone.getText().toString()));
                        i.putExtra("TOTALCOST", a);
                        startActivity(i);

                    }
                }



            }
        });
















    }

}
