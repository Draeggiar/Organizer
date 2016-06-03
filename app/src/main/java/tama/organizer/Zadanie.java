package tama.organizer;

/**
 * Created by Tama on 24.05.2016.
 */
public class Zadanie {
    private int id;
    private String nazwa;
    private String opis;


    public Zadanie(){}

    public Zadanie(int id, String nazwa, String opis){
        this.id = id;
        this.nazwa = nazwa;
        this.opis = opis;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
