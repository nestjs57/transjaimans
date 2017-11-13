package com.transjai.transjaimans;


import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
public class order extends Fragment {


    private Dialog dialog;
    private Button btnDialogConfirm;
    private Button btnDialogCancel;
    private ImageButton btnDetail;

    private String String_push = "";
    private String status = "";
    private Boolean chk = false;
    private String statusDialog = "";
    private String updateDialog = "";

    private LinearLayout linear2;
    private LinearLayout linear3;
    private LinearLayout linear4;
    private LinearLayout linear5;
    private LinearLayout linear6;
    private LinearLayout linearAll;
    private Button btnfinish;

    public order() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        bindWidjet(v);
        setEvent();
        showfirebase();

        return v;
    }

    private void setEvent() {
        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chk = false;
                final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                dbref.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot itemsnap : dataSnapshot.child("order").getChildren()) {

                            String_push = itemsnap.getKey();
                            status = (String) itemsnap.child("status").getValue();
                            if (chk == false) {
                                if (status.equals("delivering")) {
                                    status = "delivering";
                                    updateDialog = "arrived_at_destination";
                                    showDialog(status, updateDialog);
                                } else if (status.equals("arrived_at_destination")) {

                                    status = "arrived at destination";
                                    updateDialog = "setup_complete";
                                    showDialog(status, updateDialog);

                                } else if (status.equals("setup_complete")) {
                                    status = "setup_complete";
                                    updateDialog = "getBack";
                                    showDialog(status, updateDialog);
                                } else if (status.equals("getBack")) {
                                    status = "getBack";
                                    updateDialog = "Arrived";
                                    showDialog(status, updateDialog);
                                }
                                chk = true;
                            }

                        }
                    }

                    private void showDialog(String status, final String update) {

                        dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.item_dialog);
                        dialog.setCancelable(true);

                        TextView textView2 = (TextView) dialog.findViewById(R.id.txtcontent);
                        textView2.setText("ต้องการยืนยันการเสร็จสิ้นของสถานะ " + status + " หรือไม่ ?");
                        dialog.show();
                        Button button1 = (Button) dialog.findViewById(R.id.button1);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(View v) {
                                final DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference();
                                dbreff.child("order").child(String_push).child("status").setValue(update);
                                linear2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_outline));
                                dialog.cancel();
                            }
                        });
                        Button button2 = (Button) dialog.findViewById(R.id.button2);
                        button2.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void bindWidjet(View v) {
        linear2 = (LinearLayout) v.findViewById(R.id.linear2);
        linear3 = (LinearLayout) v.findViewById(R.id.linear3);
        linear4 = (LinearLayout) v.findViewById(R.id.linear4);
        linear5 = (LinearLayout) v.findViewById(R.id.linear5);
        linearAll = (LinearLayout) v.findViewById(R.id.linearAll);
        btnfinish = (Button) v.findViewById(R.id.nextStep);
        linearAll.setVisibility(View.INVISIBLE);


        btnDetail = (ImageButton) v.findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfomationOrder.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out); //ใหม่ , เก่า
            }


        });

    }

    private void showfirebase() {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        dbref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemsnap : dataSnapshot.child("order").getChildren()) {

                    String_push = itemsnap.getKey();
                    status = (String) itemsnap.child("status").getValue();
                    if (status.equals("find_driver")) {

                    } else if (status.equals("delivering")) {
                        linearAll.setVisibility(View.VISIBLE);

                    } else if (status.equals("arrived_at_destination")) {
                        linear2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_outline));
                        linearAll.setVisibility(View.VISIBLE);
                    } else if (status.equals("setup_complete")) {
                        linear2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_outline));
                        linear3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_outline));
                        linearAll.setVisibility(View.VISIBLE);
                    } else if (status.equals("getBack")) {
                        linearAll.setVisibility(View.VISIBLE);
                        linear2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_outline));
                        linear3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_outline));
                        linear4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_outline));
                        linearAll.setVisibility(View.VISIBLE);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT);
            }
        });

    }

    public void openDialog() {
        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        //dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle("งานเสร็จเรียบร้อยแล้ว");
        dialog.show();
    }


}
