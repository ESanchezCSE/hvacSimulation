package com.example.hvacsimulation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button getInfoButton;

    EditText editTargetTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get input from text boxes
        editTargetTemp = (EditText) findViewById(R.id.editNewTargetTemp);

        //Button
        getInfoButton = (Button) findViewById(R.id.getInfoBTN);

        getInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newTargetTemp = Integer.parseInt(editTargetTemp.getText().toString());
                basicWrite(newTargetTemp);
            }
        });

        basicRead();

    }

    // Functions used to determine weather the sun or the moon should be out.
    public void updateDayOrNight(String toThis){
        int inum = Integer.parseInt(toThis);
        if((inum >= 6 && inum <= 8) || (inum >= 18 && inum <= 20)) {//Display Dusk or Dawn
            //ImageView
            ImageView img = (ImageView) findViewById(R.id.imageCurrTime);
            img.setImageResource(R.drawable.ic_baseline_brightness_4_24);
        } else if(inum > 8 && inum < 18){//Display day cycle
            ImageView img = (ImageView) findViewById(R.id.imageCurrTime);
            img.setImageResource(R.drawable.ic_baseline_brightness_5_24);
        } else {//Display night
            ImageView img = (ImageView) findViewById(R.id.imageCurrTime);
            img.setImageResource(R.drawable.ic_baseline_brightness_2_24);
        }
    }

    // Functions used to display the current temperature outside of the house.
    public void updateCurrTemp(String toThis) {
        TextView textView = (TextView) findViewById(R.id.currTempText);
        textView.setText(toThis);
    }

    // Functions used to display the current temperature inside of the house.
    public void updateCurrInsideTemp(String toThis) {
        TextView textView = (TextView) findViewById(R.id.currInsideTempText);
        textView.setText(toThis);
    }

    // Functions used to display the current target temp inside of the house.
    public void updateTargetTemp(String toThis) {
        TextView textView = (TextView) findViewById(R.id.targetTempText);
        textView.setText(toThis);
    }

    public void basicWrite(int number){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("home");

        myRef.child("targetTemp").setValue(number);
    }

    public void basicRead() {
        int time = 0;
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("home");

        //myRef.setValue("Hello, World!");
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String outsideTempValue = dataSnapshot.child("currOutsideTemp").getValue().toString();
                String currentHour = dataSnapshot.child("hour").getValue().toString();
                String insideTempValue = dataSnapshot.child("currInsideTemp").getValue().toString();
                String targetTempValue = dataSnapshot.child("targetTemp").getValue().toString();

                Log.d(TAG, "Value is: " + outsideTempValue);
                updateCurrTemp(outsideTempValue);
                updateCurrInsideTemp(insideTempValue);
                updateTargetTemp(targetTempValue);
                updateDayOrNight(currentHour);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }
}
