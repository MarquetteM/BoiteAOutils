package fr.sio1.boiteoutil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateActivity extends AppCompatActivity implements View.OnClickListener {
    private Button retour;
    private TextView displayDate;
    private TextView displayHeure;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        retour = (Button) findViewById(R.id.btRetour);
        displayDate = (TextView) findViewById(R.id.affDate);
        displayHeure = (TextView) findViewById(R.id.affHeure);

        retour.setOnClickListener(this);
        displayDate.setOnClickListener(this);
        displayHeure.setOnClickListener(this);

        Calendar d = Calendar.getInstance();
        String aff = d.get(Calendar.DAY_OF_MONTH) + "/" + d.get(Calendar.MONTH) + "/" + d.get(Calendar.YEAR);
        displayDate.setText(aff);

        aff = d.get(Calendar.HOUR_OF_DAY) + ":" + d.get(Calendar.MINUTE);
        displayHeure.setText(aff);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth+" / "+month+" / "+year;
                displayDate.setText(date);
            }
        };

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String heure = hourOfDay+" : "+minute;
                displayHeure.setText(heure);
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == retour) {
            Intent intent = new Intent(DateActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if (v == displayDate){
            Calendar cal = Calendar.getInstance();
            int annee = cal.get(Calendar.YEAR);
            int mois = cal.get(Calendar.MONTH);
            int jour = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dateDial = new DatePickerDialog(
                    DateActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    annee, mois, jour);
            dateDial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dateDial.show();
        }
        else if (v == displayHeure){
            Calendar cal = Calendar.getInstance();
            int heure = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            TimePickerDialog timeDial = new TimePickerDialog(
                    DateActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mTimeSetListener,
                    heure, minute, true);
            timeDial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timeDial.show();
        }
    }
}
