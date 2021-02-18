package fr.sio1.boiteoutil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NetworkTest {
    Network[] networks;
    private NetworkMonitorAutoDetect.ConnectivityManagerDelegate connectivityManagerDelegate;
    private NetworkMonitorAutoDetect.WifiManagerDelegate wifiManagerDelegate;
    private NetworkMonitorAutoDetect.ConnectionType connectionType;
    private String wifiSSID;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConnectivityManager cm = (ConnectivityManager)appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        networks = cm.getAllNetworks();
        assertEquals(1, networks.length);
        for (int i = 0; i < networks.length; i++){
            NetworkCapabilities caps = cm.getNetworkCapabilities(networks[i]);
            Log.i ("ESS",networks[i].toString());
            assertEquals("true", String.valueOf(caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)));
            assertEquals("false", String.valueOf(caps.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)));
            assertEquals("false", String.valueOf(caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)));
     //       assertEquals("false", String.valueOf(caps.hasTransport(NetworkCapabilities.T)));
            //assertEquals("", networks[i].toString());
        }
        connectivityManagerDelegate = new NetworkMonitorAutoDetect.ConnectivityManagerDelegate(appContext);
        wifiManagerDelegate = new NetworkMonitorAutoDetect.WifiManagerDelegate(appContext);

        final NetworkMonitorAutoDetect.NetworkState networkState = connectivityManagerDelegate.getNetworkState();
        connectionType = NetworkMonitorAutoDetect.getConnectionType(networkState);
        Log.i("ESS",connectionType.toString());
//        wifiSSID = NetworkMonitorAutoDetect.getWifiSSID(networkState);
    }
    public void addition_isCorrect() {
        //assertEquals (1,1);
    }

}
