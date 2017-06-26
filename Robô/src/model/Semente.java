/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author Jhansen & Jhone
 */
public class Semente {
    private final String url;
    private boolean visitado;

    public Semente(String url, boolean visitado) {
        this.url = url;
        this.visitado = visitado;
    }
    
    public String getUrl() {
        return url;
    }

    public boolean getVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.url);
        hash = 59 * hash + (this.visitado ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Semente other = (Semente) obj;
        if (this.visitado != other.visitado) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return url + " " + visitado + "\n";
    }
}