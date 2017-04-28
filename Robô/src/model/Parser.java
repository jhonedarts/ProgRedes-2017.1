/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Jhansen
 */
public class Parser {
    
    private Document doc;
    private final ArrayList<Sementes> seeds;
    
    public Parser(){
        this.seeds = new ArrayList<>();
    }
    /**
     * Método para abrir o arquivo XML e extrair as url's
     * Cada url extraída é enviada pro método parserHTML()
     */
    public void init(){
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
                        this.parserHTML(linha);
                    }
                }
            }
            lerXML.close();
        } catch (IOException e) {
            System.err.println("Erro: " + e);
        }
    }
    /**
     * Método responsável por fazer o parser na página HTML
     * @param url 
     */
    public void parserHTML(String url){
        //por enquanto, imprimindo apenas o título e adicionando na lista de url's visitadas, só pra testar
        try {
            doc = (Document) Jsoup.connect(url).get();
            System.out.println(doc.title());
            this.seeds.add(new Sementes(url, true));
        } catch (IOException ex) {
            System.err.println("Erro: " + ex);
        }        
    }
    
    public void imprimeLista(){
        System.out.println(seeds.toString());
    }
}