/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jmalmeida
 */
public class Site {
    private String titulo;
    private String texto;
    private int numTermosDif;
    private int numTermos;
    private Centroide centroide;

    public Site(String titulo, String texto, int numTermosDif, int numTermos, Centroide centroide) {
        this.titulo = titulo;
        this.texto = texto;
        this.numTermosDif = numTermosDif;
        this.numTermos = numTermos;
        this.centroide = centroide;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getNumTermosDif() {
        return numTermosDif;
    }

    public void setNumTermosDif(int numTermosDif) {
        this.numTermosDif = numTermosDif;
    }

    public int getNumTermos() {
        return numTermos;
    }

    public void setNumTermos(int numTermos) {
        this.numTermos = numTermos;
    }

    public Centroide getCentroide() {
        return centroide;
    }

    public void setCentroide(Centroide centroide) {
        this.centroide = centroide;
    }
    
    
}
