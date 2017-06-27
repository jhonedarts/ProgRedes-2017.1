/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Jhansen & Jhone
 */
public class Centroide {
    private String termo;
    private int peso;
    private int ocorrencia;

    public Centroide(String termo, int peso, int ocorrencia) {
        this.termo = termo;
        this.peso = peso;
        this.ocorrencia = ocorrencia;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(int ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    @Override
    public String toString() {
        return "<termo>" + termo + " - " + ocorrencia + " - " + peso + "</termo>\n";
    }
}