package com.transjai.transjaimans;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class account extends Fragment {


    private LinearLayout item1;
    private LinearLayout item2;
    private ImageView imageView;
    private Dialog dialog;
    private TextView txtDate;
    private TextView txtIncome;
    private TextView total;
    private TextView txtx;
    private TextView txts;
    private TextView txtm;
    private TextView txtl;
    private TextView totalBox;
    private TextView txtFrom;
    private TextView txtTo;


    private String String_push = "";
    private String status = "";
    private String day = "";
    private String time = "";
    private String income = "";
    private String s = "";
    private String m = "";
    private String l = "";
    private String box = "";
    private String from = "";
    private String to = "";

    public account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        bindWidget(v);
        showfirebase(v);


        return v;
    }

    private void bindWidget(final View v) {
        item1 = (LinearLayout)  v.findViewById(R.id.item1);
        item2 = (LinearLayout)  v.findViewById(R.id.item2);
        txtx = (TextView) v.findViewById(R.id.txtx);

        txtDate = (TextView) v.findViewById(R.id.txtdate);
        txtIncome = (TextView) v.findViewById(R.id.txtincome);
        total = (TextView) v.findViewById(R.id.txttotal);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.account_dialog);
                imageView = (ImageView) dialog.findViewById(R.id.imageUser);

                dialog.setCancelable(true);
                Glide.with(getActivity()).load("https://scontent.fbkk5-5.fna.fbcdn.net/v/t1.0-9/12376459_1058731514179747_5170932025511786588_n.jpg?oh=3af05f5250a13388b894da030039d55f&oe=5AA8AF97").transform(new CircleTransform(getActivity())).into(imageView);
                dialog.show();
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.account_dialog);
                txts = (TextView) dialog.findViewById(R.id.txts);
                txtm = (TextView) dialog.findViewById(R.id.txtm);
                txtl = (TextView) dialog.findViewById(R.id.txtl);
                totalBox = (TextView) dialog.findViewById(R.id.totalBox);
                txtFrom = (TextView) dialog.findViewById(R.id.txtForm);
                txtTo = (TextView) dialog.findViewById(R.id.txtTo);
                imageView = (ImageView) dialog.findViewById(R.id.imageUser);


                txts.setText(s);
                txtm.setText(m);
                txtl.setText(l);
                int i = Integer.parseInt(txts.getText().toString())+
                        Integer.parseInt(txtm.getText().toString())+
                        Integer.parseInt(txtl.getText().toString());
                totalBox.setText(i+" Box");
                txtFrom.setText(from);
                txtTo.setText(to);
                Glide.with(getActivity()).load("https://scontent.fbkk5-5.fna.fbcdn.net/v/t1.0-9/12376459_1058731514179747_5170932025511786588_n.jpg?oh=3af05f5250a13388b894da030039d55f&oe=5AA8AF97").transform(new CircleTransform(getActivity())).into(imageView);

                dialog.show();

                //showfirebase(v);
                //Toast.makeText(getActivity(), total+"2", Toast.LENGTH_SHORT);
            }
        });
    }
    private void showfirebase(final View v) {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemsnap : dataSnapshot.child("order").getChildren()) {

                    String_push = itemsnap.getKey();

                        status = (String) itemsnap.child("status").getValue();
                        if (status.equals("Arrived")){
                            day = (String) itemsnap.child("Day").getValue();
                            time = (String) itemsnap.child("Time").getValue();
                            income = (String) itemsnap.child("income").getValue();
                            s = (String) itemsnap.child("NumOfSize_s").getValue();
                            m = (String) itemsnap.child("NumOfSize_m").getValue();
                            l = (String) itemsnap.child("NumOfSize_l").getValue();
                            from = (String) itemsnap.child("Location_From").getValue();
                            to = (String) itemsnap.child("Location_To").getValue();

                            txtDate.setText(day+" "+time);
                            txtIncome.setText("+ "+income.substring(0,income.length()-1));

                            String x = (txtx.getText().toString().substring(2,txtx.getText().toString().length()));
                            String y = (txtIncome.getText().toString()).substring(2,txtIncome.getText().toString().length());
                            int i = Integer.parseInt(x)+Integer.parseInt(y);
                            total.setText(i+"");




                        }else{
                            item2 = (LinearLayout)  v.findViewById(R.id.item2);
                            item2.setVisibility(View.INVISIBLE);
                        }
                        //NumOfSize_s = (String) itemsnap.child("NumOfSize_s").getValue();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT);
            }
        });

    }

}
