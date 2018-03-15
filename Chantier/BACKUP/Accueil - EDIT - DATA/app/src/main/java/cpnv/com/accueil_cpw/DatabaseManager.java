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

import static android.content.ContentValues.*;

/**
 * Created by tiffany.di-domenico on 13.03.2018.
 * Accueil_CPW
 */

public class DatabaseManager extends SQLiteOpenHelper{

    //Nom de la base
    private static final String DATABASE_NAME = "CosWorld.db";

    private static final String TABLE_NAME = "T_Users"; // Nom de la table

    //Declare les colonnes
    private static final String COL1 = "idUser";
    private static final String COL2 = "pseudo";
    private static final String COL3 = "genre";
    private static final String COL4 = "pays";
    private static final String COL5 = "description";

    public DatabaseManager(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    /** ------ Creation de la table utilisateur -------**/
    @Override
    public void onCreate(SQLiteDatabase db) {

        //commande de creation
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (idUser INTEGER PRIMARY KEY, "
                + " pseudo TEXT NOT NULL, description TEXT NOT NULL)";
        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME); //Supprime la table si elle existe
        onCreate(db);
    }

    /** -------- Ajout de donnee dans la table utilisateur ----------- **/
    public boolean addData(String id, String pseudo, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, 1);
        contentValues.put(COL2, pseudo);
        contentValues.put(COL5, description);

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
    public boolean updateData(String pseudo, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, pseudo);
        contentValues.put(COL5, description);
        db.update(TABLE_NAME, contentValues, COL1 + " = 1", null);
        return true;
    }


}
