package tama.organizer.Baza;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tama on 05.07.2016.
 */
public final class DatabaseManager extends SQLiteOpenHelper {
    private static final String DB_NAZWA = "Zadania.db";
    private static final int DB_WERSJA = 1;
    public static final String TABLE_ZADANIE = "Zadania";
    public static final String ZAD_ID ="id", ZAD_NAZWA ="nazwa", ZAD_OPIS ="opis",
                                ZAD_DL ="dlugosc", ZAD_SZ ="szerokosc";
    private static final String CREATE_TABLE_ZADANIE = "CREATE TABLE "+TABLE_ZADANIE+"("+
            ZAD_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ZAD_NAZWA +" TEXT,"+
            ZAD_OPIS +" TEXT,"+
            ZAD_SZ +" TEXT,"+
            ZAD_DL +" TEXT"+")";

    public DatabaseManager(Context context) {
        super(context, DB_NAZWA, null, DB_WERSJA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ZADANIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZADANIE);
        onCreate(db);
    }
}
