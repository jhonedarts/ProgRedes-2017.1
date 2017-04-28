/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Jhansen
 */
public class Sementes {
    private final String url;
    private final boolean visitado;

    public Sementes(String url, boolean visitado) {
        this.url = url;
        this.visitado = visitado;
    }
    
    public String getUrl() {
        return url;
    }

    public boolean getVisitado() {
        return visitado;
    }

    @Override
    public String toString(){
        return url + " " + visitado;
    }
}