/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Invertida;
import model.Parser;
import model.Semente;
import model.Site;

/**
 *
 * @author Jhansen & Jhone
 */
public class Main {
    public static void main(String[] args) {
        Parser p;
        int roboDiv=10;
        ArrayList<Semente> seeds = new ArrayList<>();
        HashMap<String, List<Invertida>> invertidas = new HashMap<>(); //termo - lista(site,peso); ex: "arroz" - <uefs.br, 12>
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
            (new Thread(new Parser(seeds.subList(0, roboDiv), invertidas))).start();
            (new Thread(new Parser(seeds.subList(roboDiv, roboDiv * 2), invertidas))).start();
            (new Thread(new Parser(seeds.subList(roboDiv *2, roboDiv *3), invertidas))).start();
        } catch (IOException ex){
            System.out.println("Erro: " + ex);
        }
        
        //gravar
        try{
            Writer arquivo = new FileWriter("Sementes.xml");
            BufferedWriter gravar = new BufferedWriter(arquivo);
            arquivo.write("<seeds>\n");
            for(Semente x : seeds){
                arquivo.write("\t<url>"+x.getUrl()+"</url>\n");
            }
            arquivo.write("</seeds>");
            gravar.close();
            arquivo.close();
            
        } catch (IOException ex) {
            System.err.println("Erro: " + ex);
        }
    }
    
}