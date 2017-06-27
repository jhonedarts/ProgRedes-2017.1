/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.Parser;
import model.Semente;

/**
 *
 * @author Jhansen & Jhone
 */
public class Main {
    public static void main(String[] args) {
        Parser p;
        ArrayList<Semente> seeds = new ArrayList<>();
        try {
            String linha;
            try {
                BufferedReader lerXML;
                try(FileReader f = new FileReader("Sementes.xml")){
                    lerXML = new BufferedReader(f);
                    while(lerXML.ready()){
                        linha = lerXML.readLine();
                        if(linha.contains("<url>")){
                            linha = linha.replace("<url>", "");
                            linha = linha.replace("</url>", "");
                            linha = linha.trim();
                            seeds.add(new Semente(linha, false));
                        }
                    }
                }
                lerXML.close();
            } catch (IOException e) {
                System.err.println("Erro: " + e);
            }
            (new Thread(new Parser(seeds.subList(0, 10)))).start();
            (new Thread(new Parser(seeds.subList(10, 20)))).start();
            (new Thread(new Parser(seeds.subList(20, 30)))).start();
        } catch (IOException ex){
            System.out.println("Erro: " + ex);
        }
    }
}