package tama.organizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Tama on 24.05.2016.
 */
public class ZadanieAdapter extends BaseAdapter{
    private ArrayList<Zadanie> listaZadan;
    private LayoutInflater zadInf;

    public ZadanieAdapter(Context c, ArrayList<Zadanie> zadania){
            listaZadan=zadania;
            zadInf= LayoutInflater.from(c);
    }

    public void add(Zadanie noweZadanie){
        listaZadan.add(noweZadanie);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout zadLay = (LinearLayout)zadInf.inflate(R.layout.zadanie, parent, false);
        TextView zadanieNazwa = (TextView)zadLay.findViewById(R.id.nazwa);
        TextView zadanieOpis = (TextView) zadLay.findViewById(R.id.opis);
        Zadanie obecneZadanie = listaZadan.get(position);
        zadanieNazwa.setText(obecneZadanie.getNazwa());
        zadanieOpis.setText(obecneZadanie.getOpis());
        zadLay.setTag(position);
        return zadLay;
    }
}
