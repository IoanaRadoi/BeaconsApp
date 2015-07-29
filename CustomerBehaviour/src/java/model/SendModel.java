package model;

/**
 * Created by Ioana.Radoi on 6/24/2015.
 */
public class SendModel {

    private String user_code;
    private String zona_name;
    private float durata;

    public SendModel() {
    }

    public SendModel(String user_code, String zona_name, float durata) {
        this.user_code = user_code;
        this.zona_name = zona_name;
        this.durata = durata;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getZona_name() {
        return zona_name;
    }

    public void setZona_name(String zona_name) {
        this.zona_name = zona_name;
    }

    public float getDurata() {
        return durata;
    }

    public void setDurata(float durata) {
        this.durata = durata;
    }
}
