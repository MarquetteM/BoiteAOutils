package fr.sio1.boiteoutil;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import static java.lang.System.getProperty;


public class ReseauActivity extends AppCompatActivity {
    private Intent intent;
//    private NetworkMonitorAutoDetect netAutodect;
    private static final int READ_PHONE_STATE_CODE = 1;
    TelephonyManager tm;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reseau);

        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //gestion permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions, READ_PHONE_STATE_CODE);
            }
            else {
                getPhoneInfo();
            }
        }
        else {
            getPhoneInfo();
        }
    }

    public void retour(View v) {
        //lance l'activité saisie
        intent = new Intent(ReseauActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void test(View v) {
        //Appel networkMonitorAutoDetect
        Log.w("BTO", "On lance le basard");
        NetworkMonitorAutoDetect.Observer observer = null;
 //       netAutodect = new NetworkMonitorAutoDetect(observer,this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getPhoneInfo() {
        int ss = tm.getSimState();
        String  simState = "";
        switch (ss) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simState = "Pas de carte Sim";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simState = "Carte bloquée";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simState = "PIN requis";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simState = "PUK requis";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simState = "Carte SIM prête";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simState = "Carte SIM non reconnue";
                break;
        }
        Log.w("BTO", simState);
        String serviceProvider = tm.getSimOperatorName();
        Log.w("BTO", serviceProvider);
        String nomMobilOp = tm.getNetworkOperatorName();
        Log.w("BTO", nomMobilOp);
//        String simID = tm.getSimSerialNumber();
//        Log.w("BTO", simID);
//        String imei = tm.getImei();
//        Log.w("BTO", imei);
//        String deviceId = tm.getSubscriberId();
//        Log.w("BTO", deviceId);
//        String tmSunscribedID = tm.getSubscriberId();
//        String softVersion = tm.getDeviceSoftwareVersion();
        String pays = tm.getSimCountryIso();
        Log.w("BTO", pays);
        String mcc_mnc = tm.getSimOperator();
        Log.w("BTO", mcc_mnc);
//        String voiceMailTag = tm.getVoiceMailAlphaTag();
        boolean roamingStatus = tm.isNetworkRoaming();


        String rep;
        int repInt;

        rep = Build.MODEL;
        Log.w("BTO","Build model : "+rep);
        rep = Build.MANUFACTURER;
        Log.w("BTO","Build manuf : "+rep);
        rep = Build.DEVICE;
        Log.w("BTO","Build device : "+rep);
        rep = Build.BOARD;
        Log.w("BTO","Build board : "+rep);
        rep = Build.HARDWARE;
        Log.w("BTO","Build hardhare : "+rep);
        rep = Build.VERSION.CODENAME;
        Log.w("BTO","Build codename "+rep);
        rep = Build.VERSION.RELEASE;
        Log.w("BTO","Build release "+rep);
        repInt = Build.VERSION.SDK_INT;
        Log.w("BTO","Build sdk int "+Integer.toString(repInt));
        rep = Build.VERSION.BASE_OS;
        Log.w("BTO","Build base OS "+rep);
        rep = getProperty("os.arch");
        Log.w("BTO","OS arch "+rep);
        rep = getProperty("java.vm.version");
        Log.w("BTO","Java VM version "+rep);

        //RAM
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        float totalMem = memoryInfo.totalMem/(1024*1024);
        float freeMem = memoryInfo.availMem/(1024*1024);
        Log.w("BTO","Total mem "+Float.toString(totalMem));
        Log.w("BTO","Free mem "+Float.toString(freeMem));
        //ROM
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSizeLong();
        float totalBlock = stat.getBlockCountLong();
        float blockLibre = stat.
        Log.w("BTO", "BlockSize "+Float.toString(blockSize));
        Log.w("BTO", "totalBlock "+Float.toString(totalBlock));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhoneInfo();
                }
                else {
                    Toast.makeText(this, "La permission pour lire l'état du téléphone est requise",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}