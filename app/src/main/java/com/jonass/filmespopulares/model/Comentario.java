package com.jonass.filmespopulares.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jonas on 16/01/17.
 */

public class Comentario implements Parcelable {
    private String id;
    private String autor;
    private String conteudo;

    public Comentario() {

    }

    public Comentario(String id, String autor, String conteudo) {
        this.id = id;
        this.autor = autor;
        this.conteudo = conteudo;
    }

    protected Comentario(Parcel in) {
        id = in.readString();
        autor = in.readString();
        conteudo = in.readString();
    }

    public static final Creator<Comentario> CREATOR = new Creator<Comentario>() {
        @Override
        public Comentario createFromParcel(Parcel in) {
            return new Comentario(in);
        }

        @Override
        public Comentario[] newArray(int size) {
            return new Comentario[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(autor);
        dest.writeString(conteudo);
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id='" + id + '\'' +
                ", autor='" + autor + '\'' +
                ", conteudo='" + conteudo + '\'' +
                '}';
    }
}
