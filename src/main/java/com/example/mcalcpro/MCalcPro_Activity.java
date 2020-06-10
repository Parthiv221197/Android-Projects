package com.example.mcalcpro;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ca.roumani.i2c.MPro;

public class MCalcPro_Activity extends AppCompatActivity implements TextToSpeech.OnInitListener, SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tts = new TextToSpeech(this,this);
    }

    public void buttonClicked(View v)
    {

        try {
            mp.setPrinciple(((EditText) findViewById(R.id.pBox)).getText().toString());
            mp.setAmortization(((EditText) findViewById(R.id.aBox)).getText().toString());
            mp.setInterest(((EditText) findViewById(R.id.iBox)).getText().toString());
            String s = "Monthly Payment = " + mp.computePayment("%,.2f");
            s += "\n\n";
            s += "By making this payments monthly for ";
            for(int i=0;i<=20;i++){
                s += String.format("%8d", i) + mp.outstandingAfter(i, "%,16.0f");
                s += "\n\n";}
            //s += String.format("%8d", 1) + mp.outstandingAfter(1, "%,16.0f");
            ((TextView) findViewById(R.id.output)).setText(s);
            tts.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        }
        catch(Exception e)
        {
            Toast label = Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT);
            label.show();
        }}
    MPro mp = new MPro();
    private TextToSpeech tts;

    @Override
    public void onInit(int initsStatus) {
        this.tts.setLanguage(Locale.CANADA);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];
        double a = Math.sqrt(ax*ax+ay*ay+az*az);
        if(a>20){
            ((EditText) findViewById(R.id.pBox)).setText("");
            ((EditText) findViewById(R.id.aBox)).setText("");
            ((EditText) findViewById(R.id.iBox)).setText("");
            ((TextView) findViewById(R.id.output)).setText("");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }
}


