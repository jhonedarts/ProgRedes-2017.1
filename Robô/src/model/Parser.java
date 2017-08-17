/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import principal.Main;

/**
 *
 * @author Jhansen & Jhone
 */
public class Parser implements Runnable{
    private String dominio;
    private boolean isDominio;
    private int stop = 30;
    private final HashMap<String, Integer> tags;
    private final ArrayList<Site> sites;
    private Document doc;
    private HashMap<String, List<Invertida>> invertidas; //termo - lista(site,peso); ex: "arroz" - <uefs.br, 12>
    private final HashMap<String, Boolean> stoplist;    
    private List<Semente> seeds;
    private int numTermos = 0;
    
    public Parser(List<Semente> seeds, String dominio) throws IOException{
        this.seeds = seeds;
        this.isDominio = false;
        this.dominio = dominio;
        this.invertidas = new HashMap<>();
        BufferedReader sourceBr = new BufferedReader(new FileReader(new File("out/stoplist.txt")));
        String line;
        stoplist = new HashMap();
        while ((line = sourceBr.readLine()) != null) {
            line = line.trim();
            if(!line.equals("")){//linha vazia
                stoplist.put(line, Boolean.FALSE);
            }
        }
        sourceBr.close();
        sourceBr = new BufferedReader(new FileReader(new File("out/stoplistEn.txt")));
        while ((line = sourceBr.readLine()) != null) {
            line = line.trim();
            if(!line.equals("")){
                stoplist.put(line, Boolean.FALSE);
            }
        }
        sourceBr.close();
        this.sites = new ArrayList<>();
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
    @Override
    public void run(){
        
        //O robo visita a quantidade de semetes definida por stop
        for (int index = 0; index < stop; index++) {
            parserHTML(seeds.get(index).getUrl());  
            seeds.get(index).setVisitado(true);
            System.out.println(index+" "+seeds.get(index).getUrl()+" - visitado");
            
        }
        this.gravar();
    }
    /**
     * Método responsável por fazer o parser na página HTML
     * @param url 
     */
    public void parserHTML(String url){
        try {
            doc = (Document) Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                //coloca as urls encontradas na lista de sementes
                if(!seeds.contains(new Semente(link.attr("abs:href"), false)) && !seeds.contains(new Semente(link.attr("abs:href"), true)))
                    this.seeds.add(new Semente(link.attr("abs:href"), false));//se nao existe coloca
            }
            HashMap<String, Termo> words = getCentroide();
            //atribuindo novos termos a lista invertida
            for (String key : words.keySet()){
                Termo c = words.get(key);
                if(!invertidas.containsKey(c.getTermo())){ //se nao tem, cria o termo e inicia a lista
                    List<Invertida> lista = new ArrayList<>();
                    Invertida inv = new Invertida(url, c.getPeso()); // site, peso
                    lista.add(inv);
                    invertidas.put(c.getTermo(), lista);
                }else{ // se o termo já existe, adiciona na lista invertida
                    Invertida inv = new Invertida(url, c.getPeso()); // site, peso
                    invertidas.get(c.getTermo()).add(inv);
                }
            }
            Site site = new Site(doc.title(),doc.text(),words.size(), numTermos, words);
            site.dominio(dominio);
            if (isDominio)
                sites.add(site);
            isDominio = false;
        } catch (IOException | IllegalArgumentException ex) {
           // System.err.println("Erro: " + ex);
        }        
    }
        
    private HashMap<String, Termo> getCentroide(){
        HashMap<String, Termo> words = new HashMap();
        numTermos = 0;
        Elements es = doc.getAllElements();//receber um vetor de Strings das palavras que tem na tag html na posição i de tags
        for (Element e:es){
            //System.out.println(e.tag()+" - "+e.text());
            String palavras[] = e.text().split("\\s+|\\n+|\\t+|\\r+|\\.+|\\,+|\\;+|\\:+|\\(+|\\)+|#+|\"+|'+|[+|]+");
                    //+ "issimo|íssimo|issimos|íssimos|issima|íssima|issimas|íssimas|manha|manhã|noite|exemplo|minutos|segundos");
            for(String str:palavras){
                str =str.trim();
                numTermos++;
                Termo cent = words.get(str);
                Boolean stopLContains = stoplist.get(str);
                if (stopLContains == null && !str.isEmpty()){ //se nao está na stoplist
                    if(cent==null){//se não esta na lista de centroides
                        int peso =0;
                        if (tags.get(e.tagName())!=null)
                            peso = tags.get(e.tagName());
                        else
                            peso = 1;
                        cent = new Termo(str, peso, 1);
                        words.put(str, cent);
                        if(str.equalsIgnoreCase(dominio))
                            isDominio = true;
                    }else{// se já esta na lista de centroides, incrementa a ocorrencia
                        cent.setOcorrencia(cent.getOcorrencia()+1);
                        if(tags.get(e.tagName())!=null){
                            cent.setPeso(cent.getPeso() + tags.get(e.tagName()));
                        }else{
                            cent.setPeso(cent.getPeso() +1);
                        }
                    }
                    //System.out.println(e.tag()+" - "+cent.getConteudo()+" - "+cent.getOcorrencia()+" - "+cent.getPeso());
                }
            }
        }
        return words;
    }
    private void gravar(){
        try{
            for(Site x : sites){
                
                Writer arquivo2 = new FileWriter("out/centroides/"+x.getTitulo().replaceAll("[^a-zZ-Z1-9 ]", "")+".xml");
                BufferedWriter gravar2 = new BufferedWriter(arquivo2);
                arquivo2.write("<raiz>\n");
                arquivo2.write("\t<qtdTermo>"+x.getNumTermos()+"</qtdTermo>\n");
                arquivo2.write("\t<qtdTermoDif>"+x.getNumTermosDif()+"</qtdTermoDif>\n");
                arquivo2.write("\t<titulo>"+x.getTitulo()+"</titulo>\n");
                arquivo2.write("\t<texto>"+x.getTexto()+"</texto>\n");
                arquivo2.write("\t"+x.toString());
                arquivo2.write("</raiz>");
                gravar2.close();
                arquivo2.close();
            }
        } catch (IOException ex) {
            System.err.println("Erro: " + ex);
        }
        System.out.println("GRAVOU");
        Main.gravar(seeds, invertidas);
    }
}