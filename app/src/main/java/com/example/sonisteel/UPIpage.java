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



    final int UPI_PAYMENT = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_p_ipage);


        final TextView textView;
        final Button btn;
        final EditText msg;
        final TextView id;


        textView = findViewById(R.id.amt);
        btn = findViewById(R.id.btnpay);
        id = findViewById(R.id.upiid);
        msg = findViewById(R.id.mes);



        Intent i = getIntent();

        int total = i.getIntExtra("TOTALCOST", 0);
        textView.setText(Integer.toString(total));









        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = getIntent();

                int noofkadhai = i.getIntExtra("STAINLESSKADHAI", 0);
                int noofhandi = i.getIntExtra("HANDI", 0);
                int nooftops = i.getIntExtra("SETOFTOPS", 0);
                String name = i.getStringExtra("NAME");
                String add = i.getStringExtra("ADDRESS");
                int phone = i.getIntExtra("PHONE", 0);
                int total = i.getIntExtra("TOTALCOST", 0);








                payUsingUpi(Integer.toString(total), id.getText().toString(), name, msg.getText().toString());

            }
        });
    }


    void payUsingUpi(String amount, String upiId, String name, String note) {


        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)     //add "1" here to check transaction by paying just 1 rs.
                .appendQueryParameter("tr","261433")
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);


        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if( chooser.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(chooser, 1,null);
        } else {
            Toast.makeText(UPIpage.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ResultCOde",Integer.toString(resultCode));
        Log.d("Requestcode",Integer.toString(requestCode));
        if(data==null) {
            Log.d("Data", "i have to check why");
        }

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(UPIpage.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(UPIpage.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
                Intent i = getIntent();

                int noofkadhai = i.getIntExtra("STAINLESSKADHAI", 0);
                int noofhandi = i.getIntExtra("HANDI", 0);
                int nooftops = i.getIntExtra("SETOFTOPS", 0);
                String name = i.getStringExtra("NAME");
                String add = i.getStringExtra("ADDRESS");
                int phone = i.getIntExtra("PHONE", 0);
                int total = i.getIntExtra("TOTALCOST", 0);
                addToDatabase(noofkadhai,noofhandi,nooftops,name,add,phone,total);

            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(UPIpage.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(UPIpage.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(UPIpage.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public void addToDatabase(int noofkhadai,int noofhandi,int nooftops,String name,String add,int phone,int total)

    {
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("OrderDetails");

                OrderDetails orderDetails=new OrderDetails();
                orderDetails.setName(name);
                orderDetails.setAdd(add);
                orderDetails.setPhone(phone);

                HashMap<String,Integer> hashMap=new HashMap<>();


                if(noofkhadai!=0)
                {
                    hashMap.put("Stain less Kadhai",noofkhadai);

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
                DatabaseReference dr1=FirebaseDatabase.getInstance().getReference().child("OrderDetailsForFutureReference");
                dr1.push().setValue(orderDetails);

                Intent i=new Intent(UPIpage.this,OrderPlaced.class);
                startActivity(i);
    }


}



































