package com.transjai.transjaimans;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class InfomationOrder extends Activity {

    private ImageView imgUser;
    private ImageButton btncall;
    private TextView txtForm;
    private TextView txtTo;

    private TextView txtDateTime;
    private TextView txtNameStore;
    private TextView txtS;
    private TextView txtM;
    private TextView txtL;
    private TextView txtDetail;
    private TextView txtNote;
    private TextView txtIncome;
    private TextView totalBox;

    //

    private String String_push = "";
    private Boolean continueFirebase = false;

    //setText
    private String date = "";
    private String time = "";
    private String nameStore = "";
    private String s = "";
    private String m = "";
    private String l = "";
    private String detail = "";
    private String note = "";
    private String income = "";
    private String form = "";
    private String to = "";

    String latFalse = "";
    String lngFalse = "";

    String latTrue = "";
    String lngTrue = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_order);

        BindWidget();
        SetEvent();

        ShowFirebase(false,false);


    }

    private void setTextEvent(String form,String to,String name, String date, String time, String s, String m, String l, String detail, String note, String income) {
        BindWidget();
        txtDateTime.setText(date + time);
        txtNameStore.setText("Name Store : " +name);
        txtS.setText(s);
        txtM.setText(m);
        txtL.setText(l);
        txtDetail.setText(detail);
        txtNote.setText(note);
        txtIncome.setText(income);
        totalBox.setText(Integer.parseInt(s) + Integer.parseInt(m) + Integer.parseInt(l) + " Boxs");
        txtDetail.setText(detail);
        txtNote.setText(note);
        txtIncome.setText(income);
        txtForm.setText(to);
        txtTo.setText(form);

    }

    private void BindWidget() {
        btncall = (ImageButton) findViewById(R.id.btncall);
        txtForm = (TextView) findViewById(R.id.txtForm);
        txtTo = (TextView) findViewById(R.id.txtTo);
        imgUser = (ImageView) findViewById(R.id.imageUser);
        Glide.with(getApplication()).load("https://scontent.fbkk5-5.fna.fbcdn.net/v/t1.0-9/12376459_1058731514179747_5170932025511786588_n.jpg?oh=3af05f5250a13388b894da030039d55f&oe=5AA8AF97").transform(new CircleTransform(getApplication())).into(imgUser);


        //setText
        txtDateTime = (TextView) findViewById(R.id.dateTime);
        txtDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtS = (TextView) findViewById(R.id.txts);
        txtM = (TextView) findViewById(R.id.txtm);
        txtL = (TextView) findViewById(R.id.txtl);
        txtDetail = (TextView) findViewById(R.id.txtDetail);
        txtNote = (TextView) findViewById(R.id.txtNote);
        txtIncome = (TextView) findViewById(R.id.txtIncomes);
        totalBox = (TextView) findViewById(R.id.totalBox);
        txtNameStore = (TextView) findViewById(R.id.txtNameStore);
        txtForm = (TextView) findViewById(R.id.txtForm);
        txtTo = (TextView) findViewById(R.id.txtTo);



    }

    private void SetEvent() {


        btncall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "0869605898", null)));
            }
        });

        txtForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFirebase(true,false);
                GoGoogleMap(latTrue, lngTrue);            }
        });
        txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFirebase(true,true);
                GoGoogleMap(latFalse, lngFalse);

            }
        });
    }

    private void ShowFirebase(final Boolean aBoolean , final Boolean continueFirebase) {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        dbref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot itemsnap : dataSnapshot.child("order").getChildren()) {

                    String_push = itemsnap.getKey() + "";


                    date = (String) itemsnap.child("Day").getValue();
                    time = (String) itemsnap.child("Time").getValue();
                    nameStore = (String) itemsnap.child("Name").getValue();
                    s = (String) itemsnap.child("NumOfSize_s").getValue();
                    m = (String) itemsnap.child("NumOfSize_m").getValue();
                    l = (String) itemsnap.child("NumOfSize_l").getValue();
                    detail = (String) itemsnap.child("Detail").getValue();
                    note = (String) itemsnap.child("Note").getValue();
                    income = (String) itemsnap.child("income").getValue();
                    form = (String) itemsnap.child("Location_From").getValue();
                    to = (String) itemsnap.child("Location_To").getValue();
                    setTextEvent(form,to,nameStore,date, time, s, m, l, detail, note, income);


                    latFalse = (String) itemsnap.child("latCur_start").getValue();
                    lngFalse = (String) itemsnap.child("lngCur_start").getValue();

                    latTrue = (String) itemsnap.child("lat_choose_end").getValue();
                    lngTrue = (String) itemsnap.child("lng_choose_end").getValue();

//                    if (aBoolean == true && continueFirebase == true) {
//
//                            latFalse = (String) itemsnap.child("latCur_start").getValue();
//                            lngFalse = (String) itemsnap.child("lngCur_start").getValue();
////                            Toast.makeText(getApplication(),latFalse,Toast.LENGTH_SHORT).show();
//                    }else {
//                             latTrue = (String) itemsnap.child("lat_choose_end").getValue();
//                             lngTrue = (String) itemsnap.child("lng_choose_end").getValue();
////                            Toast.makeText(getApplication(),"2",Toast.LENGTH_SHORT).show();
//                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplication(), "Connected", Toast.LENGTH_SHORT);
            }
        });

    }

    private void GoGoogleMap(String lat, String ln) {


//        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+lat+","+ln));
//        startActivity(i);

        String latitude = lat;
        String longitude = ln;
        String label = "ABC Label";
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

}
