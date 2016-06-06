package tama.organizer.Baza;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tama on 26.05.2016.
 */
public class DatabaseManager extends SQLiteOpenHelper{
    private static final int  DB_WERSJA = 1;
    public static final String DB_NAZWA = "Zadanie.db";
    public static String ZAD_ID="id", ZAD_NAZWA="nazwa", ZAD_OPIS="opis", NAZWA_TABELI="ZADANIE";
    private static final String CREATE_TABLE_TYP = "CREATE TABLE "+NAZWA_TABELI+"(" +
                                ZAD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                                ZAD_NAZWA+" TEXT," +
                                ZAD_OPIS+" TEXT)";

    public DatabaseManager(Context context) {
        super(context, DB_NAZWA, null, DB_WERSJA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TYP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAZWA_TABELI);
        onCreate(db);
    }
}
