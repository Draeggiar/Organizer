package tama.organizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tama on 26.05.2016.
 */
public class BazaZadan{
    private DatabaseManager dbManager;
    private SQLiteDatabase dbMaterial;
    private String[] ZADANIE_REKORD = {DatabaseManager.ZAD_ID,
                                        DatabaseManager.ZAD_NAZWA,
                                        DatabaseManager.ZAD_OPIS};

    public BazaZadan(Context context){
        dbManager = new DatabaseManager(context);
    }

    public void open() throws SQLException{
        dbMaterial = dbManager.getWritableDatabase();
    }

    public void close(){
        dbMaterial.close();
    }

    private Zadanie parseZadanie(Cursor k){
        Zadanie zad = new Zadanie();
        zad.setId((k.getInt(0)));
        zad.setNazwa((k.getString(1)));
        zad.setOpis((k.getString(2)));
        return zad;
    }

    public Zadanie dodajZadanie(String nazwa, String opis){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseManager.ZAD_NAZWA, nazwa);
        cv.put(DatabaseManager.ZAD_OPIS, opis);
        long zadID = dbMaterial.insert(DatabaseManager.NAZWA_TABELI, null, cv);
        Cursor kursor = dbMaterial.query(DatabaseManager.NAZWA_TABELI,
                ZADANIE_REKORD,
                DatabaseManager.ZAD_ID+" = "+zadID, null, null, null, null);
        kursor.moveToFirst();
        Zadanie noweZadanie = parseZadanie(kursor);
        kursor.close();
        return noweZadanie;
    }

    public ArrayList<Zadanie> zwrocZadania(){
        ArrayList<Zadanie> zadania = new ArrayList<>();
        Cursor kursor = dbMaterial.query(DatabaseManager.NAZWA_TABELI, ZADANIE_REKORD,
                null, null, null, null, null);
        kursor.close();
        return zadania;
    }

    public void usunZadanie(Zadanie zad){
        long id = zad.getId();
        dbMaterial.delete(DatabaseManager.NAZWA_TABELI, DatabaseManager.ZAD_ID+" = "+id, null);
    }
}
