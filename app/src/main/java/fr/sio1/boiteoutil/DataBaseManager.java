package fr.sio1.boiteoutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

public class DataBaseManager extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NOM = "BoiteOutil.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseManager (Context context) {
        super (context, DATABASE_NOM, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource){
        try {
            TableUtils.createTable(connectionSource, Utilisateur.class);
        } catch (Exception e) {
            Log.e ("BTO", "Pb lors de la création de la base");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Utilisateur.class, true);
            onCreate(database, connectionSource);
        } catch (Exception e) {
            Log.e ("BTO", "Pb lors de l'upgrade de la base");
        }
    }

    public int ajoutUtil (Utilisateur newUtil){
        try {
            Dao<Utilisateur,Integer> maDao = getDao(Utilisateur.class);
            maDao.create(newUtil);
            return newUtil.getIdUtilisateur();
        } catch (Exception e){
            Log.e ("BTO", "Pb lors de la création d'un nouvel utilisateur");
            return 0;
        }
    }

    public List<Utilisateur> lecUtilisateurs() {
        try {
            Dao<Utilisateur,Integer> maDao = getDao(Utilisateur.class);
            List<Utilisateur> utilisateurs = maDao.queryForAll();
            return utilisateurs;
        }catch (Exception e) {
            Log.e ("BTO", "PB lecture de tous les utilisateurs ");
            return null;
        }
    }

    public List<Utilisateur> lecUtilisateur(int id) {
        try {
            Dao<Utilisateur,Integer> maDao = getDao(Utilisateur.class);
            List<Utilisateur> utilisateurs = maDao.queryForEq("idUtilisateur", id);
            return utilisateurs;
        } catch (Exception e) {
            Log.e ("BTO", "PB lors de la lecture par id");
            return null;
        }
    }

    public int majUtilisateur(Utilisateur utilisateur) {
        try {
            Dao<Utilisateur,Integer> maDao = getDao(Utilisateur.class);
            int retour = maDao.update(utilisateur);
            return retour;
        }  catch (Exception e) {
            Log.e ("BTO", "PB lors de la mise à jour d'un utilisateur");
            return 0;
        }

    }

    public int supUtilisateur (Utilisateur utilisateur) {
        try {
            Dao<Utilisateur,Integer> maDao = getDao(Utilisateur.class);
            String textErr = "Id à effacer " + utilisateur.getIdUtilisateur();
            Log.e ("BTO", textErr);
            int ret = maDao.delete(utilisateur);
            return ret;
        } catch (Exception e) {
            Log.e ("BTO", "PB lors de la suppression par id");
            return 0;
        }
    }
}
