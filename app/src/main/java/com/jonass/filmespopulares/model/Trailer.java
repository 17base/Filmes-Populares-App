package com.jonass.filmespopulares.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jonas on 16/01/17.
 */

public class Trailer implements Parcelable {
    private String id;
    private String key;
    private String titulo;
    private String site;
    private String tipo;

    public Trailer() {

    }

    public Trailer(String id, String key, String titulo, String site, String tipo) {
        this.id = id;
        this.key = key;
        this.titulo = titulo;
        this.site = site;
        this.tipo = tipo;
    }

    protected Trailer(Parcel in) {
        id = in.readString();
        key = in.readString();
        titulo = in.readString();
        site = in.readString();
        tipo = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(titulo);
        dest.writeString(site);
        dest.writeString(tipo);
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", titulo='" + titulo + '\'' +
                ", site='" + site + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
