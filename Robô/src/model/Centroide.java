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
    private String conteudo;
    private int peso;
    private int ocorrencia;

    public Centroide(String conteudo, int peso, int ocorrencia) {
        this.conteudo = conteudo;
        this.peso = peso;
        this.ocorrencia = ocorrencia;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
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
        return "<termo>" + conteudo + " - " + ocorrencia + " - " + peso + "</termo>\n";
    }
}