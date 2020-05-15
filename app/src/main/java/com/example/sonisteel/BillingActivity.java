package com.example.sonisteel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BillingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        Bundle bundle=getIntent().getExtras();
        ArrayList<String>arrayList=bundle.getStringArrayList("ProductList");
        ListView listView1=findViewById(R.id.listview1);
        ArrayAdapter<String> items1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView1.setAdapter(items1);

        ArrayList<String>arrayList2=bundle.getStringArrayList("QuantityList");
        ListView listView2=findViewById(R.id.listview2);
        ArrayAdapter<String> items2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList2);
        listView2.setAdapter(items2);

    }
}
