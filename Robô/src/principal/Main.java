/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import model.Parser;

/**
 *
 * @author Jhansen
 */
public class Main {
    public static void main(String[] args) {
        Parser p = new Parser();
        p.init();
        p.imprimeLista();
    }
}