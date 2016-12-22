package com.jonass.filmespopulares.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JonasS on 16/12/2016.
 */

public class Filme implements Parcelable {
    public static final String PARCELABLE_KEY = "Info";

    private String id;
    private String imagem_path;
    private String titulo;
    private String sinopse;
    private String avaliacao;
    private String lancamento;
    private String capa_path;

    public Filme(String id, String imagem_path, String titulo, String sinopse, String avaliacao, String lancamento, String capa_path) {
        this.id = id;
        this.imagem_path = imagem_path;
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.avaliacao = avaliacao;
        this.lancamento = lancamento;
        this.capa_path = capa_path;
    }

    public static final Parcelable.Creator<Filme> CREATOR
            = new Parcelable.Creator<Filme>() {
        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

    private Filme(Parcel in) {
        titulo = in.readString();
        imagem_path  = in.readString();
        sinopse = in.readString();
        avaliacao = in.readString();
        lancamento = in.readString();
        capa_path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(imagem_path);
        dest.writeString(sinopse);
        dest.writeString(avaliacao);
        dest.writeString(lancamento);
        dest.writeString(capa_path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagem_path() {
        return imagem_path;
    }

    public void setImagem_path(String imagem_path) {
        this.imagem_path = imagem_path;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public String getCapa_path(){
        return capa_path;
    }

    public void setCapa_path(String capa_path){
        this.capa_path = capa_path;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", imagem='" + imagem_path + '\'' +
                ", titulo='" + titulo + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", avaliacao='" + avaliacao + '\'' +
                ", lancamento='" + lancamento + '\'' +
                ", capa='" + capa_path + '\'' +
                '}';
    }
}
