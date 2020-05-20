package com.example.sonisteel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Button btn1,btn2,btn3,btn4;

        final TextView quan1,quan2,quan3,quan4;

        ImageButton add1,add2,add3,add4,
                remove1,remove2,remove3,remove4;







        quan1=findViewById(R.id.q1);
        quan2=findViewById(R.id.q2);
        quan3=findViewById(R.id.q3);
        quan4=findViewById(R.id.q4);


        add1=findViewById(R.id.add1);
        add2=findViewById(R.id.add2);
        add3=findViewById(R.id.add3);
        add4=findViewById(R.id.add4);


        remove1=findViewById(R.id.remove1);
        remove2=findViewById(R.id.remove2);
        remove3=findViewById(R.id.remove3);
        remove4=findViewById(R.id.remove4);

        btn1=findViewById(R.id.disc1);
        btn2=findViewById(R.id.disc2);
        btn3=findViewById(R.id.disc3);
        btn4=findViewById(R.id.disc4);












      btn1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
              builder.setMessage("Discription  "+"\n\n"+"Colour: Silver\n"+"Usage: Home,Hotel/Restaurent\n"+"Number Of Handle: 2\n"+
              "Material: Stainless Steel\n"+"Finishing: Mirror Polished\n"+"Size: 14 inch\n").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                  }
              });
              AlertDialog alertDialog=builder.create();
              alertDialog.show();
          }
      });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Discription  "+"\n\n"+"Size:Set of 5(300ml,550ml,800ml,1100ml,1500ml\n"+"Usage: Home,Hotel/Restaurent\n"+
                        "Material: Stainless Steel\n"+"Finishing: Mirror Polished\n").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Discription  "+"\n\n"+"Size:Set of 5(10'' to 14'')\n"+"Usage: Home,Hotel/Restaurent\n"+
                        "Material: Stainless Steel\n"+"Finishing: Mirror Polished\n").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Discription  "+"\n\n"+"Size:Set of 3(500ml,800ml,1500ml)\n"+"Usage: Home,Hotel/Restaurent\n"+
                        "Material: Stainless Steel 304\n"+"Finishing: Mirror Polished\n"+"Durable\n").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });






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


        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity4=Integer.parseInt(quan4.getText().toString());

                quantity4=quantity4+1;
                quan4.setText(Integer.toString(quantity4));

            }
        });

        remove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity4=Integer.parseInt(quan4.getText().toString());
                if(quantity4>0)
                    quantity4=quantity4-1;
                else
                    quantity4=0;
                quan3.setText(Integer.toString(quantity4));

            }
        });








        fb.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        Intent i=new Intent(MainActivity.this,Cart.class);
        i.putExtra("STAINLESSKADHAI",Integer.parseInt(quan1.getText().toString()));
        i.putExtra("SETOFTOPS",Integer.parseInt(quan2.getText().toString()));
        i.putExtra("HANDI",Integer.parseInt(quan3.getText().toString()));
        i.putExtra("SETOFDISH",Integer.parseInt(quan4.getText().toString()));





        startActivity(i);
    }
});







    }
}
