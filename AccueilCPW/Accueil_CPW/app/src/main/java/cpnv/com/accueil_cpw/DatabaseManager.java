package cpnv.com.accueil_cpw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Blob;

import static android.content.ContentValues.*;

/**
 * Created by tiffany.di-domenico on 13.03.2018.
 * Accueil_CPW
 * Source ;
 * https://www.youtube.com/watch?v=eYJq1TO07y4
 * https://www.youtube.com/watch?v=BX_0RItiwcc
 * https://www.youtube.com/watch?v=LHMGamTPEpE
 */

public class DatabaseManager extends SQLiteOpenHelper{

    //Nom de la base
    private static final String DATABASE_NAME = "CosWorld.db";

    /** --------- TABLE T_USERS -------- **/
    private static final String TABLE_NAME = "T_Users"; // Nom de la table

    //Declare les colonnes de la table T_Users
    private static final String COL1 = "idUser";
    private static final String COL2 = "pseudo";
    private static final String COL3 = "genre";
    private static final String COL4 = "pays";
    private static final String COL5 = "description";
    private static final String COL6 = "idPos";
    private static final String COL7 = "image1";
    private static final String COL8 = "image2";
    private static final String COL9 = "image3";
    private static final String COL10 = "image4";
    private static final String COL11 = "image5";
    private static final String COL12 = "image6";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    /** ------ Creation de la table utilisateur -------**/
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Création de la table USER avec les différentes colonnes nécessaires
        String createTableUsers = "CREATE TABLE " + TABLE_NAME
                + " (" + COL1 +  " INTEGER PRIMARY KEY, "
                + COL2 + " TEXT NOT NULL, "
                + COL3 + " TEXT NOT NULL, "
                + COL4 + " TEXT NOT NULL, "
                + COL5 + " TEXT NOT NULL, "
                + COL6 + " INT NOT NULL, "
                + COL7 + " TEXT, "
                + COL8 + " TEXT, "
                + COL9 + " TEXT, "
                + COL10 + " TEXT, "
                + COL11 + " TEXT, "
                + COL12 + " TEXT);";

        //On crée la table pays pour remplir le spinner
        String createTablePays = "CREATE TABLE T_PAYS (idPay INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";
        //On ajoute des pays dans la table
        String insertPays = "INSERT INTO T_PAYS (name) VALUES ('Allemagne'), ('Belgique'), ('Danemark'), ('Espagne'), ('Finlande'), ('France'), ('Italie'), ('Pays-Bas'), ('Pologne'), ('Portugal'), ('Roumanie'), ('Royaume-Uni'), ('Suisse');";

        //Execution
        db.execSQL(createTableUsers);
        db.execSQL(createTablePays);
        db.execSQL(insertPays);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME); //Supprime la table si elle existe lors d'une upgrade
        onCreate(db);
    }


    /** -------- Ajout de donnee dans la table utilisateur ----------- **/
    public boolean addData(String id, String pseudo, String genre, String pays, String description, int idPos, String image1, String image2, String image3, String image4, String image5, String image6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, 1);
        contentValues.put(COL2, pseudo);
        contentValues.put(COL3, genre);
        contentValues.put(COL4, pays);
        contentValues.put(COL5, description);
        contentValues.put(COL6, idPos);
        contentValues.put(COL7, image1);
        contentValues.put(COL8, image2);
        contentValues.put(COL9, image3);
        contentValues.put(COL10, image4);
        contentValues.put(COL11, image5);
        contentValues.put(COL12, image6);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }else {
            return true;
        }
    }


    /** ------------ Affiche des donees dans la table utilisateur -------------- **/
    public Cursor showData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;

    }

    /** ---------- Update les donnees dans la table utilisateur -------------- **/
    public boolean updateData(String pseudo, String genre, String pays, String description, int idPos, String image1, String image2, String image3, String image4, String image5, String image6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, pseudo);
        contentValues.put(COL3, genre);
        contentValues.put(COL4, pays);
        contentValues.put(COL5, description);
        contentValues.put(COL6, idPos);
        contentValues.put(COL7, image1);
        contentValues.put(COL8, image2);
        contentValues.put(COL9, image3);
        contentValues.put(COL10, image4);
        contentValues.put(COL11, image5);
        contentValues.put(COL12, image6);
        db.update(TABLE_NAME, contentValues, COL1 + " = 1", null);
        return true;
    }

    /** ------- Affiche les pays dans le spinner ------ **/
    public Cursor showPays(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM T_PAYS", null);
        return data;

    }


}
