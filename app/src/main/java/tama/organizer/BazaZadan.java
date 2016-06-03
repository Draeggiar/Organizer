package tama.organizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/**
 * Created by Tama on 26.05.2016.
 */
public class BazaZadan{
    private DatabaseManager dbManager;
    private static SQLiteDatabase dbZadanie;
    private String[] ZADANIE_REKORD = {DatabaseManager.ZAD_ID,
                                        DatabaseManager.ZAD_NAZWA,
                                        DatabaseManager.ZAD_OPIS};
    private Context context;

    public BazaZadan(Context context){
        this.context = context;
        dbManager = new DatabaseManager(context);
    }

    public void open() throws SQLException{
        String path = context.getDatabasePath(dbManager.DB_NAZWA).toString();
        dbZadanie = SQLiteDatabase.openOrCreateDatabase(path, null, null);
    }

    public void close(){
        dbZadanie.close();
    }

    private Zadanie parseZadanie(Cursor k){
        Zadanie zad = new Zadanie();
        zad.setId((k.getInt(0)));
        zad.setNazwa((k.getString(1)));
        zad.setOpis((k.getString(2)));
        return zad;
    }

    public synchronized Zadanie dodajZadanie(String nazwa, String opis){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseManager.ZAD_NAZWA, nazwa);
        cv.put(DatabaseManager.ZAD_OPIS, opis);
        long zadID = dbZadanie.insert(DatabaseManager.NAZWA_TABELI, null, cv);
        Cursor kursor = dbZadanie.query(DatabaseManager.NAZWA_TABELI,
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

        Cursor c = dbZadanie.rawQuery("SELECT * FROM " + DatabaseManager.NAZWA_TABELI , null);

        int Column1 = c.getColumnIndex(DatabaseManager.ZAD_ID);
        int Column2 = c.getColumnIndex(DatabaseManager.ZAD_NAZWA);
        int Column3 = c.getColumnIndex(DatabaseManager.ZAD_OPIS);

        // Check if our result was valid.
        c.moveToFirst();
        if (c != null) {
            // Loop through all Results
            int i = 1;
            do {
                zadania.add(new Zadanie(c.getInt(Column1), c.getString(Column2), c.getString(Column3)));
            } while (c.moveToNext());
        }
        return zadania;
    }

    public static void usunZadanie(Zadanie zad){
        long id = zad.getId();
        dbZadanie.delete(DatabaseManager.NAZWA_TABELI, DatabaseManager.ZAD_ID+" = "+id, null);
    }
}
