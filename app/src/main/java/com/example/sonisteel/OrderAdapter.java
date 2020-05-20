package com.example.sonisteel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<OrderDetails> orderDetails;



    public OrderAdapter(Context c,ArrayList<OrderDetails> orderDetails)
    {
        this.context=c;
        this.orderDetails=orderDetails;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.details,parent,false);
        return new MyViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderDetails orderDetails=this.orderDetails.get(position);
        holder.name.setText(orderDetails.getName());
        holder.address.setText(orderDetails.getAdd());
        holder.phone.setText( "" +   orderDetails.getPhone());
        holder.total.setText( "" +   orderDetails.getTotal());
        holder.payment.setText(orderDetails.getModeOfPayment());

        HashMap<String,Integer> hm=orderDetails.getHm();
        //holder.products.setText(hm.toString());
        Set keys=hm.keySet();
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            String key=(String)i.next();
            int value=(Integer)hm.get(key);
            holder.products.append(key+" -----> "+value+"\n");
        }










    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,phone,total,payment;
        TextView products;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameis);
            address=itemView.findViewById(R.id.addis);
            phone=itemView.findViewById(R.id.phoneis);
            total=itemView.findViewById(R.id.totalis);
            payment=itemView.findViewById(R.id.paymentmodeis);
            products=itemView.findViewById(R.id.productis);





        }
    }
}
