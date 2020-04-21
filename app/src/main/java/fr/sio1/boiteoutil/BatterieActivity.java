package fr.sio1.boiteoutil;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BatterieActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private Button btRetour;
    private TextView infoNivCharge;
    private TextView infoTpsCharge;
    private TextView infoTechno;
    private TextView etatCharge;
    private BatteryManager batteryManager;
    private Handler myHandler;
    private IntentFilter intentFilter;
    private Intent batterieStatus;
    private String messageTempsCharge;
    private long tempsSeconde;
    private static int periode = 500; //période du CallBack en ms

    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            //fonctions réalisées en CallBack
            batterieStatus = getApplicationContext().registerReceiver(null, intentFilter);
            int status = batterieStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            //vérifie que la batterie est en charge
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                int chargePlug = batterieStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                if (chargePlug == BatteryManager.BATTERY_PLUGGED_USB) {
                    etatCharge.setText("En charge sur USB");
                } else if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC) {
                    etatCharge.setText("En charge sur secteur");
                } else {
                    etatCharge.setText("En charge sur support inconnu");
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    //calcule le temps pour la charge total uniquement pour les version supérieures à Pi
                    tempsSeconde = batteryManager.computeChargeTimeRemaining() / 1000;
                    if (tempsSeconde == 0) {
                        //0  le calcul ne se fait pas immédiatement
                        messageTempsCharge = "Calcul en cours";
                    } else {
                        messageTempsCharge = tempsSeconde + " s";
                    }
                }
            } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                etatCharge.setText("Complètement chargée");
                messageTempsCharge = "C'est fini";
            } else {
                etatCharge.setText("Fonctionne sur batterie");
                messageTempsCharge = "N'est pas en charge";
            }
            infoTpsCharge.setText(messageTempsCharge);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                //récupère le % de charge uniquement pour les version > LOLLIPOP
                int percentage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                infoNivCharge.setText(percentage + " %");
            }
            //relance le timer
            myHandler.postDelayed(this, periode);
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.P)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batterie);

        btRetour = findViewById(R.id.info_bat_ret);
        infoNivCharge = findViewById(R.id.info_bat_nivcharge);
        infoTpsCharge = findViewById(R.id.info_bat_fincharge);
        infoTechno = findViewById(R.id.info_bat_techno);
        etatCharge = findViewById(R.id.info_bat_texte_etatcharge);
        batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);

        //Récupération EXTRA
        intentFilter = new IntentFilter(intent.ACTION_BATTERY_CHANGED);
        batterieStatus = getApplicationContext().registerReceiver(null, intentFilter);
        String techno = batterieStatus.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
        infoTechno.setText(techno);

        //lancement de la tache de callBack
        myHandler = new Handler();
        myHandler.postDelayed(myRunnable, periode);

        btRetour.setOnClickListener(this);
    }

    public void onPause() {
        super.onPause();
        if (myHandler != null){
            myHandler.removeCallbacks(myRunnable);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btRetour){
            intent = new Intent(BatterieActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
