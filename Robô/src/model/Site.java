
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;

/**
 *
 * @author Jhansen & Jhone
 */
public class Site {
    private String titulo;
    private boolean dominio;
    private String texto;
    private int numTermosDif;
    private int numTermos;
    HashMap<String, Termo> termos;

    public Site(String titulo, String texto, int numTermosDif, int numTermos, HashMap<String, Termo> termos) {
        this.titulo = titulo;
        this.dominio = false;
        this.texto = texto;
        this.numTermosDif = numTermosDif;
        this.numTermos = numTermos;
        this.termos = termos;
    }

    public Site() {
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

    public HashMap<String, Termo> getCentroide() {
        return termos;
    }

    public void setCentroide(HashMap<String, Termo> centroide) {
        this.termos = centroide;
    }
    
    public void dominio(String dominio){
        for(String x : termos.keySet()){
            if(x.equalsIgnoreCase(dominio)){
                this.dominio = true;
                break;
            }
        }
    }
    
    public boolean getDominio(){
        return dominio;
    }

    @Override
    public String toString() {
        return termos.toString();
    }
}