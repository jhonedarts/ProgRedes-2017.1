/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.IOException;
import model.Parser;

/**
 *
 * @author Jhansen & Jhone
 */
public class Main {
    public static void main(String[] args) {
        Parser p;
        try {
            p = new Parser();
            p.init();
        } catch (IOException ex){
            System.out.println("Erro: " + ex);
        }
    }
}