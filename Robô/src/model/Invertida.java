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
public class Invertida {
    private String site;
    private int peso;

    public Invertida(String site, int peso) {
        this.site = site;
        this.peso = peso;
    }

    public String getSite() {
        return site;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int i) {
        this.peso = i;
    }
    
}

