package tama.organizer.Baza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import tama.organizer.Zadania.Zadanie;

/**
 * Created by Tama on 05.07.2016.
 */
public final class BazaZadan {
    private DatabaseManager dbManager;
    private SQLiteDatabase dbZadanie;
    private String[] ZADANIE_REKORD ={DatabaseManager.ZAD_ID, DatabaseManager.ZAD_NAZWA,
                                    DatabaseManager.ZAD_OPIS, DatabaseManager.ZAD_SZ,
                                    DatabaseManager.ZAD_DL};

    public BazaZadan(Context context){
        dbManager = new DatabaseManager(context);
    }

    public void open() throws SQLException{
        dbZadanie = dbManager.getWritableDatabase();
    }

    public void close(){
        dbZadanie.close();
    }

    private Zadanie parseZadanie(Cursor k){
        Zadanie zad = new Zadanie();
        zad.setId((k.getInt(0)));
        zad.setNazwa((k.getString(1)));
        zad.setOpis((k.getString(2)));
        zad.setSzerokosc(k.getString(3));
        zad.setDlugosc(k.getString(4));
        return zad;
    }

    public synchronized Zadanie dodajZadanie(String nazwa, String opis, String szerokosc, String dlugosc){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseManager.ZAD_NAZWA, nazwa);
        cv.put(DatabaseManager.ZAD_OPIS, opis);
        cv.put(DatabaseManager.ZAD_SZ, szerokosc);
        cv.put(DatabaseManager.ZAD_DL, dlugosc);
        long zadID = dbZadanie.insert(DatabaseManager.TABLE_ZADANIE, null, cv);
        Cursor kursor = dbZadanie.query(DatabaseManager.TABLE_ZADANIE,
                ZADANIE_REKORD,
                DatabaseManager.ZAD_ID+" = "+zadID, null, null, null, null);
        kursor.moveToFirst();
        Zadanie noweZadanie = parseZadanie(kursor);
        kursor.close();
        return noweZadanie;
    }

    public ArrayList<Zadanie> zwrocZadania(){
        ArrayList<Zadanie> zadania = new ArrayList<>();
        //Cursor kursor = dbZadanie.query(DatabaseManager.NAZWA_TABELI, ZADANIE_REKORD,
        //        null, null, null, null, null);

        Cursor c = dbZadanie.rawQuery("SELECT * FROM " + DatabaseManager.TABLE_ZADANIE , null);

        //int Column1 = c.getColumnIndex(DatabaseManager.ZAD_ID);
        //int Column2 = c.getColumnIndex(DatabaseManager.ZAD_NAZWA);
        //int Column3 = c.getColumnIndex(DatabaseManager.ZAD_OPIS);

        // Check if our result was valid.
        c.moveToFirst();
        while (c.moveToNext())
            zadania.add(parseZadanie(c));
        c.close();
        return zadania;
    }

    public void usunZadanie(Zadanie zad){
        long id = zad.getId();
        dbZadanie.delete(DatabaseManager.TABLE_ZADANIE, DatabaseManager.ZAD_ID+" = "+id, null);
    }
}
