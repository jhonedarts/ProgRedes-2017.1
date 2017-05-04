/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Parser;

/**
 *
 * @author Jhansen e Jhone
 */
public class Main {
    public static void main(String[] args) {
        Parser p;
        try {
            p = new Parser();
            p.init();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}