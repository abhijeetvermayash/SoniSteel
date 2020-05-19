package com.example.sonisteel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UPIpage extends AppCompatActivity {

    final int UPI_PAYMENT = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_p_ipage);

        Intent i = getIntent();
        final int noofkadhai = i.getIntExtra("STAINLESSKADHAI", 0);
        final int noofhandi = i.getIntExtra("HANDI", 0);
        final int nooftops = i.getIntExtra("SETOFTOPS", 0);
        final String name = i.getStringExtra("NAME");
        final String add = i.getStringExtra("ADDRESS");
        final int phone = i.getIntExtra("PHONE", 0);
        final int total = i.getIntExtra("TOTALCOST", 0);
        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("OrderDetails");



        TextView textView;
        Button btn;
        final EditText msg;
        final TextView id;




        textView = findViewById(R.id.amt);
        btn = findViewById(R.id.btnpay);
        id = findViewById(R.id.upiid);
        msg = findViewById(R.id.mes);


        //textView.setText(Integer.toString(total));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri uri = Uri.parse("upi://pay").buildUpon()
                        .appendQueryParameter("pa", id.getText().toString())
                        .appendQueryParameter("pn", name)
                        .appendQueryParameter("tn", msg.getText().toString())
                        //.appendQueryParameter("am", Integer.toString(total))
                        .appendQueryParameter("am",Integer.toString(1))
                        .appendQueryParameter("cu", "INR")
                        .build();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(uri);

                Intent chooser = Intent.createChooser(i, "Pay With");

                if (null != chooser.resolveActivity(getPackageManager())) {
                    startActivityForResult(chooser, UPI_PAYMENT);
                } else {
                    Toast.makeText(UPIpage.this, "No UPI Application Found Please Install Any To Continue", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){

            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case UPI_PAYMENT:
                    if((RESULT_OK==resultCode)||(resultCode==11))
                    {
                        if(data!=null)
                        {
                            String trxt=data.getStringExtra("response");
                            Log.d("UPI","onActivityResult: "+trxt);
                            ArrayList<String> datalist=new ArrayList<>();
                            datalist.add(trxt);
                            upiPaymentDataOperation(datalist);
                        }
                        else
                        {
                            Log.d("UPI","OnActivityResult: "+"Returned Data is null nulllllll");
                            ArrayList<String> datalist=new ArrayList<>();
                            datalist.add("Nothing");
                            upiPaymentDataOperation(datalist);
                        }
                    }
                    else
                    {
                        Log.d("UPI","OnActivityResult: "+"Returned Data is null");
                        ArrayList<String> datalist=new ArrayList<>();
                        datalist.add("Nothing");
                        upiPaymentDataOperation(datalist);

                    }
                    break;



            }
        }

        private void upiPaymentDataOperation(ArrayList<String> data)
        {
            if(isConnectionAvailable(UPIpage.this))
            {
                String str=data.get(0);
                Log.d("UPIPAYMENT","upiPaymentDataOperation: "+str);
                String paymentcancel="";
                if(str==null) str="discard";
                String status="";
                String approvalRefNo="";
                String response[]=str.split("&");
                for (int i=0;i<response.length;i++) {
                    String equalstr[] = response[i].split("=");
                    if (equalstr.length >= 2) {
                        if (equalstr[0].toLowerCase().equals("Status".toLowerCase())) {
                            status = equalstr[1].toLowerCase();
                        } else if (equalstr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalstr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                            approvalRefNo = equalstr[1];
                        }
                    } else {
                        paymentcancel = "Payment Cancel by user.";
                    }
                }
                if(status.equals("success")) {
                            Toast.makeText(UPIpage.this, "Transaction Succesfull", Toast.LENGTH_SHORT).show();
                            Log.d("UPI", "responseStr: " + approvalRefNo);


                        }
                        else if("Payment Cancel by user.".equals(paymentcancel))
                        {
                            Toast.makeText(UPIpage.this, "payment Cancel by user", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(UPIpage.this, "Transaction failed! Please try later", Toast.LENGTH_SHORT).show();
                        }
                    }
            else
            {
                Toast.makeText(UPIpage.this, "Internet Connection is not available,check your connection ", Toast.LENGTH_SHORT).show();
            }
                }


                 /*int a=0;
                OrderDetails orderDetails=new OrderDetails();
                orderDetails.setName(name);
                orderDetails.setAdd(add);
                orderDetails.setPhone(phone);

                HashMap<String,Integer> hashMap=new HashMap<>();


                if(noofkadhai!=0)
                {
                    hashMap.put("Stain less Kadhai",noofhandi);

                }

                if(noofhandi!=0)
                {
                    hashMap.put("Handi with Copper base",noofhandi);

                }
                if(nooftops!=0)
                {
                    hashMap.put("Set Of Tops",nooftops);


                }


                orderDetails.setHm(hashMap);
                orderDetails.setTotal(total);
                orderDetails.setModeOfPayment("Payement Through UPI");
                dr.push().setValue(orderDetails);

                Intent i=new Intent(UPIpage.this,OrderPlaced.class);
                startActivity(i); */







        public static boolean isConnectionAvailable(Context context)
        {
            ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
            if(activeNetwork!=null)
            {
                return  true;

            }
            else
            {
                return  false;
            }
        }







}



































