/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Jhansen
 */
public class Parser {
    private String tags[] = {"p","h3","head"};
    private ArrayList<Site> sites;
    private Document doc;
    private HashMap<String, Boolean> stoplist;//nem precisa da lista de seeds
    private final ArrayList<Semente> seeds;
    
    public Parser() throws IOException{
        BufferedReader sourceBr = new BufferedReader(new FileReader(new File("stoplist.txt")));
        String line;
        stoplist = new HashMap();
        while ((line = sourceBr.readLine()) != null) {
            line = line.trim();
            if(!line.equals("")){//linha vazia
                stoplist.put(line, Boolean.FALSE);
            }
        }
        sourceBr.close();
        sourceBr = new BufferedReader(new FileReader(new File("stoplistEn.txt")));
        while ((line = sourceBr.readLine()) != null) {
            line = line.trim();
            if(!line.equals("")){//linha vazia
                stoplist.put(line, Boolean.FALSE);
            }
        }
        sourceBr.close();
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
                        System.out.println(linha);
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
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                System.out.println(link.attr("abs:href")+"  -  "+link.text().trim());
            }
            getCentroide(doc.text());
            this.seeds.add(new Semente(url, true));
        } catch (IOException ex) {
            System.err.println("Erro: " + ex);
        }        
    }
    
    public void imprimeLista(){
        System.out.println(seeds.toString());
    }
    
    private void getCentroide(String txt){
        HashMap<String, Centroide> words = new HashMap();
        String palavras[] = txt.split("\\s+|\\n+|\\t+|\\r+|\\.+|\\,+|\\;+|\\:+|\\(+|\\)+|#+|\"+|'+|[+|]+|");
                //+ "issimo|íssimo|issimos|íssimos|issima|íssima|issimas|íssimas|manha|manhã|noite|exemplo|minutos|segundos");
        for(String str:palavras){
            str =str.trim();
            Centroide cent = words.get(str);
            Boolean stopLContains = stoplist.get(str);
            if (!stopLContains){ //se nao está na stoplist
                if(cent==null){//se não esta na lista de centroides
                    cent = new Centroide(str, 0, 1);
                    words.put(str, cent);
                    System.out.println(str);
                }else{// se já esta na lista de centroides, incrementa a ocorrencia
                    cent.setOcorrencia(cent.getOcorrencia()+1);
                    System.out.println(cent.getConteudo()+" - "+cent.getOcorrencia());
                }
            }
            //hasmap de words populada, vamos agora atribuir os pesos
            for(int i=0;i<tags.length;i++){
                String aux[] = doc.//receber um vetor de Strings das palavras que tem na tag html na posição i de tags
            }
        }
    }
}