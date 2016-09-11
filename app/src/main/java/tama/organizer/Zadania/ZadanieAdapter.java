package tama.organizer.Zadania;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import tama.organizer.Baza.BazaZadan;
import tama.organizer.R;

/**
 * Created by Tama on 24.05.2016.
 */
public class ZadanieAdapter extends BaseAdapter{
    private volatile ArrayList<Zadanie> listaZadan;
    private LayoutInflater zadInf;
    private BazaZadan db;

    public ZadanieAdapter(Context c, ArrayList<Zadanie> zadania){
            listaZadan=zadania;
            zadInf= LayoutInflater.from(c);
    }

    public synchronized void add(Zadanie noweZadanie){
        listaZadan.add(noweZadanie);
        notifyDataSetChanged();
    }

    public void edit(int id, Zadanie zad){
        listaZadan.set(id, zad);
        notifyDataSetChanged();
    }

    public void delete(int zadID){
        listaZadan.remove(zadID);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
       return listaZadan.size();
    }

    @Override
    public Object getItem(int position) {
        return listaZadan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LinearLayout zadLay = (LinearLayout)zadInf.inflate(R.layout.zadanie, parent, false);
        TextView zadanieNazwa = (TextView)zadLay.findViewById(R.id.nazwa);
        TextView zadanieOpis = (TextView) zadLay.findViewById(R.id.opis);
        final Zadanie obecneZadanie = listaZadan.get(position);
        zadanieNazwa.setText(obecneZadanie.getNazwa());
        zadanieOpis.setText(obecneZadanie.getOpis());
        zadLay.setTag(position);

        return zadLay;
    }
}
