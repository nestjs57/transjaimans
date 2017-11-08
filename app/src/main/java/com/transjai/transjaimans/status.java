package com.transjai.transjaimans;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class status extends Fragment implements OnMapReadyCallback {


    private static final int REQUEST_LOCATION = 1;
    private String mCurrentLocStr;
    private TextView mLocTextView;
    private GoogleMap mMapView;
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 1000;
    private List<LatLng> listOfLatLng = new ArrayList<>();
    private LocationRequest mRequest;
    public static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    private GoogleApiClient mApiClient;
    private Polygon polygon;
    private MarkerOptions mapMarker;
    int currentPt;
    int mAnimationZoom = 15;

//    int mPinDrawables[] = new int[]{R.drawable.pin_01,
//            R.drawable.pin_02,
//            R.drawable.pin_03,
//            R.drawable.pin_04,
//            R.drawable.pin_05,
//            R.drawable.pin_06,
//            R.drawable.pin_07};
    private int pinCount = 0;
    private ImageView mClearMarkersBtn;
    private ImageView mAnimationBtn;
    private ImageView mGeoCodingBtn;
    private ValueAnimator vAnimator;
    private LatLng defaultLocation;


    private Circle circle;
    private Switch simpleSwitch;
    //
    private static SeekBar seekBar;
    private static TextView txtMin;
    public status() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_status, container, false);

//
//        FragmentManager fragmentMgr = getActivity().getSupportFragmentManager();
//        SupportMapFragment mMapViewFragment = (SupportMapFragment) fragmentMgr.findFragmentById(R.id.mMapView);
//        mMapViewFragment.getMapAsync(this);

        setSwitch(v);
        setSeekBar(v);

        FragmentManager fragmentMgr = getFragmentManager();
        SupportMapFragment mMapViewFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mMapViewFragment.getMapAsync(this);



        return v;
    }



    private void setSwitch(final View v) {
        simpleSwitch = (Switch) v.findViewById(R.id.switch1);
        simpleSwitch.setChecked(true);
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Toast.makeText(getActivity(), "The Switch is "+b,Toast.LENGTH_SHORT).show();

                if (b){
                    Snackbar.make(v, "เปิดสถานะการรับงานเรียบร้อยแล้ว...", Snackbar.LENGTH_SHORT).show();
                }else{

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.item_dialog);
                    dialog.setCancelable(true);

                    TextView textView2 = (TextView)dialog.findViewById(R.id.txtcontent);
                    textView2.setText("ต้องการจะปิดสถานะการรับงานหรือไม่ ?");

                    dialog.show();

                    Button button1 = (Button)dialog.findViewById(R.id.button1);
                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            simpleSwitch.setChecked(false);
                            dialog.cancel();
                        }
                    });
                    Button button2 = (Button)dialog.findViewById(R.id.button2);
                    button2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            simpleSwitch.setChecked(true);
                            dialog.cancel();
                        }
                    });

                }
            }
        });
    }

    private void setSeekBar(View v) {
        seekBar = (SeekBar) v.findViewById(R.id.seekbar);
        txtMin = (TextView) v.findViewById(R.id.txtmin);
        seekBar.setProgress(50);
        txtMin.setText(seekBar.getProgress()+" km.");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                progress_value = i;
                txtMin.setText(i+" km.");


                if (i<=20){
                    circle.setRadius(i*100);
                    LatLng location = new LatLng(13.910518999999999, 100.5468817);
                    mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13+(-i/30)));
                }
                else if (i<=30){
                    circle.setRadius(i*100);
                    LatLng location = new LatLng(13.910518999999999, 100.5468817);
                    mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12+(-i/50)));
                }else if (i<=40){
                    circle.setRadius(i*100);
                    LatLng location = new LatLng(13.910518999999999, 100.5468817);
                    mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 11+(-i/80)));
                }else{
                    circle.setRadius(i*100);
                    LatLng location = new LatLng(13.910518999999999, 100.5468817);
                    mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 11+(-i/60)));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtMin.setText(progress_value+" km.");

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapView = googleMap;

        Toast.makeText(getActivity(), "onMapReady", Toast.LENGTH_SHORT).show();
        circle = mMapView.addCircle(new CircleOptions()
                .center(new LatLng(13.910518999999999, 100.5468817))
                .radius(5000)
                .strokeColor(Color.parseColor("#ff8800"))
                .strokeWidth(5)
                .fillColor(Color.argb(20, 50, 10, 255) ));
        LatLng location = new LatLng(13.910518999999999, 100.5468817);
        mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 11));
        circle.remove();
//        LatLng location = new LatLng(-33.87365, 151.20689);
//        circle = mMapView.addCircle(new CircleOptions().center(location)
//                .strokeColor(Color.parseColor("#E91E63")).radius(100));
//
//        vAnimator = new ValueAnimator();
//        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
//        vAnimator.setIntValues(0, 100);
//        vAnimator.setDuration(1000);
//        vAnimator.setEvaluator(new IntEvaluator());
//        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float animatedFraction = valueAnimator.getAnimatedFraction();
//                // Log.d("animation_codemobiles", "" + animatedFraction);
//                circle.setRadius(animatedFraction * 100);
//            }
//        });
//        vAnimator.start();
//        mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));



    }


}
