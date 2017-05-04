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
 * @author Jhansen e Jhone
 */
public class Parser {
    private HashMap<String, Integer> tags;
    private ArrayList<Site> sites;
    private Document doc;
    private HashMap<String, Boolean> stoplist;//nem precisa da lista de seeds
    private final ArrayList<Semente> seeds;
    private int numTermos = 0;
    public Parser() throws IOException{
        BufferedReader sourceBr = new BufferedReader(new FileReader(new File("stoplist.txt"))); //tem que ler em UTF-8
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
        this.sites = new ArrayList<Site>();
        this.tags = new HashMap();
        tags.put("title", 10);
        tags.put("h1", 7);
        tags.put("h2", 6);
        tags.put("h3", 5);
        tags.put("h4", 4);
        tags.put("h5", 4);
        tags.put("h6", 4);
        tags.put("a", 5);
        tags.put("big", 3);
        tags.put("b", 3);
        tags.put("em", 3);
        tags.put("i", 3);
        tags.put("u", 3);
        tags.put("strong", 3);
        tags.put("strike", 3);
        tags.put("center", 3);
        tags.put("small", 2);
        tags.put("sub", 2);
        tags.put("sup", 2);
        tags.put("font", 2);
        tags.put("address", 2);
        tags.put("meta", 2);        
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
                        seeds.add(new Semente(linha, false));
                        //System.out.println(linha);
                    }
                }
            }
            lerXML.close();
        } catch (IOException e) {
            System.err.println("Erro: " + e);
        }
        
        for (int index = 0; index < seeds.size(); index++) {
            parserHTML(seeds.get(index).getUrl());  
            seeds.get(index).setVisitado(true);
            System.out.println(seeds.get(index).getUrl()+" - visitado");
            if(index==4){
                break;
            }
        }
        //salvar sites em xml, ou bin se nada der certo
    }
    /**
     * Método responsável por fazer o parser na página HTML
     * @param url 
     */
    public void parserHTML(String url){
        //por enquanto, imprimindo apenas o título e adicionando na lista de url's visitadas, só pra testar
        try {
            doc = (Document) Jsoup.connect(url).get();
            //System.out.println(doc.title());
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                //System.out.println(link.attr("abs:href")+"  -  "+link.text().trim());
                //coloca as urls encontradas na lista de sementes
                if(!seeds.contains(new Semente(link.attr("abs:href"), false)) && !seeds.contains(new Semente(link.attr("abs:href"), true)))
                    this.seeds.add(new Semente(link.attr("abs:href"), false));//se nao existe coloca
            }
            HashMap<String, Centroide> words = getCentroide();
            Site site = new Site(doc.title(),doc.text(),words.size(), numTermos, words);
            sites.add(site);            
        } catch (IOException ex) {
            System.err.println("Erro: " + ex);
        }        
    }
    
    public void imprimeLista(){
        System.out.println(seeds.toString());
    }
    
    private HashMap<String, Centroide> getCentroide(){
        HashMap<String, Centroide> words = new HashMap();
        numTermos = 0;
        Elements es = doc.getAllElements();//receber um vetor de Strings das palavras que tem na tag html na posição i de tags
        for (Element e:es){
            //System.out.println(e.tag()+" - "+e.text());
            String palavras[] = e.text().split("\\s+|\\n+|\\t+|\\r+|\\.+|\\,+|\\;+|\\:+|\\(+|\\)+|#+|\"+|'+|[+|]+");
                    //+ "issimo|íssimo|issimos|íssimos|issima|íssima|issimas|íssimas|manha|manhã|noite|exemplo|minutos|segundos");
            for(String str:palavras){
                str =str.trim();
                numTermos++;
                Centroide cent = words.get(str);
                Boolean stopLContains = stoplist.get(str);
                if (stopLContains == null && !str.isEmpty()){ //se nao está na stoplist
                    if(cent==null){//se não esta na lista de centroides
                        int peso =0;
                        if (tags.get(e.tagName())!=null)
                            peso = tags.get(e.tagName());
                        else
                            peso = 1;
                        cent = new Centroide(str, peso, 1);
                        words.put(str, cent);
                    }else{// se já esta na lista de centroides, incrementa a ocorrencia
                        cent.setOcorrencia(cent.getOcorrencia()+1);
                        if(tags.get(e.tagName())!=null){
                            cent.setPeso(cent.getPeso() + tags.get(e.tagName()));
                        }else{
                            cent.setPeso(cent.getPeso() +1);
                        }
                    }
                    //System.out.println(e.tag()+""+cent.getConteudo()+" - "+cent.getOcorrencia()+" - "+cent.getPeso());
                }
            }
        }
        return words;
    }
}