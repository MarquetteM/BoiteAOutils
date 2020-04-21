package fr.sio1.boiteoutil;

import android.app.ProgressDialog;
import android.content.Context;

public class BarreDeProgression {
    private static ProgressDialog maProgress;

    public static void affProgress (Context context, String titre, String message, boolean isCancelable) {
        try {
            if (maProgress == null) {
                maProgress = ProgressDialog.show(context, titre,message);
                maProgress.setCancelable(isCancelable);
            }
            if (!maProgress.isShowing()){
                maProgress.show();
            }
        } catch (IllegalArgumentException ie){
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void affProgress (Context context) {
        affProgress(context, null, "Chargement ...", false);
    }

    public static void supprProgress () {
        try {
            if (maProgress != null) {
                if (maProgress.isShowing()) {
                    maProgress.dismiss();
                    maProgress = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
