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

/**
 *
 * @author Jhansen & Jhone
 */
public class Main {
    private static ArrayList<Semente> seeds = new ArrayList<>();
    private static HashMap<String, List<Invertida>> invertidas = new HashMap<>(); //termo - lista(site,peso); ex: "arroz" - <uefs.br, 12>
    private static int count=0;
    public static void main(String[] args) {
        Parser p;
        int roboDiv=10;
        
        
        try {
            String linha;
            try {
                BufferedReader lerXML;
                try(FileReader f = new FileReader("sementes.xml")){
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
            (new Thread(new Parser(new ArrayList(seeds.subList(0, roboDiv))))).start();
            (new Thread(new Parser(new ArrayList(seeds.subList(roboDiv, roboDiv * 2))))).start();
            (new Thread(new Parser(new ArrayList(seeds.subList(roboDiv *2, roboDiv *3))))).start();
        } catch (IOException ex){
            System.out.println("Erro: " + ex);
        }
        
        //gravar
        
    }
    
    public synchronized static void gravar(List<Semente> seedsNew, HashMap<String, List<Invertida>> invertidasNew){
        seeds.addAll(seedsNew);
        if(invertidas.isEmpty()){
            invertidas.putAll(invertidasNew);
        }else{
            for (String key: invertidas.keySet()){
                List<Invertida> lis = invertidasNew.get(key);
                if (lis != null)
                    lis.addAll(invertidas.get(key)); //mescla as listas internas
            }
            invertidas.putAll(invertidasNew);// mescla as listas invertidas, sobrescrevendo as listas internas de invertidas
        }                                   // por invertidasNew
        count++;
        if (count == 3){ // quando os 3 robos pararem de fazer merda, gravamos
            try{
                //salva sementes
                Writer arquivo = new FileWriter("out/sementes.xml");
                BufferedWriter gravar = new BufferedWriter(arquivo);
                arquivo.write("<seeds>\n");
                for(Semente x : seeds){
                    arquivo.write("\t<url>"+x.getUrl()+"</url>\n");
                }
                arquivo.write("</seeds>");
                gravar.close();
                arquivo.close();
                
                //salva a lista invertida
                arquivo = new FileWriter("out/invertidas.txt");
                gravar = new BufferedWriter(arquivo);
                arquivo.write("<lista invertida>");
                for(String key: invertidas.keySet()){
                    List <Invertida> list = invertidas.get(key);
                    arquivo.write("\n\t"+key+"\n");
                    for(Invertida inv: list){
                        arquivo.write("<"+inv.getSite()+" ,"+inv.getPeso()+">, ");
                    }
                }
                arquivo.write("</lista invertida>");
                gravar.close();
                arquivo.close();
            } catch (IOException ex) {
                System.err.println("Erro: " + ex);
            }
        }
    }
    
}