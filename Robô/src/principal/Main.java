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
import java.util.LinkedList;
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
    private static String busca;
    
    private static HashMap<String, Integer> sitesL = new HashMap<>();
    private static HashMap<Integer, String> sitesLI = new HashMap<>();
    private static int ct=0;
    
    public static void main(String[] args) {
        busca = ("Bahia");
        Parser p;
        String dominio = "futebol  gol \n" +
"jogador \n" +
"torcedor/torcida \n" +
"juiz/arbitro \n" +
"estaio \n" +
"chute \n" +
"jogo/partida \n" +
"atacante/centroavante \n" +
"goleiro \n" +
"zagueiro/beque \n" +
"drible \n" +
"tecnico/treinador \n" +
"penalti \n" +
"time/clube \n" +
"volante \n" +
"ataque \n" +
"campo \n" +
"artilheiro/goleador \n" +
"falta \n" +
"campeão \n" +
"meio de campo \n" +
"escalação \n" +
"passe/toque \n" +
"lateral \n" +
"escanteio/tiro de canto \n" +
"trave/travessão \n" +
"campeonato \n" +
"carrinho \n" +
"placar \n" +
"defesa \n" +
"cartão \n" +
"chuteira \n" +
"frango \n" +
"arquibancada/numerada \n" +
"classico \n" +
"vitoria \n" +
"titular \n" +
"substituição \n" +
"bandeira/auxiliar \n" +
"mundial \n" +
"comentarista \n" +
"bicicleta \n" +
"titulo \n" +
"acrescimos \n" +
"gramado/grama \n" +
"area \n" +
"salao/quadra \n" +
"seleção \n" +
"treino \n" +
"impedimento \n" +
"tiro de meta \n" +
"capitão \n" +
"vestiario \n" +
"derrota \n" +
"formação/tatica \n" +
"prorrogação \n" +
"banco de reservas \n" +
"tempo regulamentar \n" +
"apito \n" +
"comemoração \n" +
"rebaixamento \n" +
"explusão \n" +
"empate \n" +
"pelada \n" +
"mata-mata \n" +
"gol contra \n" +
"divisão \n" +
"concentração \n" +
"reserva \n" +
"uniforme \n" +
"gandula \n" +
"gol de placa \n" +
"voleio \n" +
"cruzamento \n" +
"ingresso \n" +
"rede \n" +
"contra-ataque \n" +
"gol olimpico \n" +
"jogo de corpo \n" +
"barreira \n" +
"repescagem \n" +
"drible da vaca \n" +
"comissão tecnica \n" +
"intervalo \n" +
"pontos corridos \n" +
"varzea \n" +
"gaveta \n" +
"letra \n" +
"armador \n" +
"circulo central \n" +
"lançamento \n" +
"massagista \n" +
"jogada ensaiada \n" +
"caneta \n" +
"diretor tecnico \n" +
"dividida \n" +
"caneleira \n" +
"maca \n" +
"society";
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
            (new Thread(new Parser(new ArrayList(seeds.subList(0, roboDiv)), dominio))).start();
            (new Thread(new Parser(new ArrayList(seeds.subList(roboDiv, roboDiv * 2)), dominio))).start();
            (new Thread(new Parser(new ArrayList(seeds.subList(roboDiv *2, roboDiv *3)), dominio))).start();
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
                        if (!sitesL.containsKey(inv.getSite())){
                            sitesL.put(key, ct);
                            sitesLI.put(ct, key);
                            ct++;
                        }
                        arquivo.write("<"+sitesL.get(inv.getSite())+" ,"+inv.getPeso()+">, ");
                    }
                }
                arquivo.write("</lista invertida>");
                gravar.close();
                arquivo.close();
            } catch (IOException ex) {
                System.err.println("Erro: " + ex);
            }
            buscar(busca);
        }
    }
    
    public static void buscar(String busca){
        List <List<Invertida>> listB = new LinkedList<>();
        String[] termosB = busca.split(" ");
        for(String key: invertidas.keySet()){
            for (String term : termosB){
                if (term.equalsIgnoreCase(key)){
                    listB.add(invertidas.get(key));                          
                }
            }            
        }
        List<Invertida> intersectList = new LinkedList<>();
        boolean deVerdade = true;
        boolean[] isBusca = new boolean[listB.size()-1];
        for (boolean b: isBusca)
            b =false;
        for (Invertida gab: listB.get(0)){
            Invertida yara = new Invertida(gab.getSite(), gab.getPeso());
            for (int i=1;i<listB.size();i++){
                for (int j=1; j<listB.size(); j++){
                    for (Invertida mariela: listB.get(1)){
                        if (mariela.getSite().equals(gab.getSite())){
                            yara.setPeso(gab.getPeso() + mariela.getPeso());
                            isBusca[j]=true;
                        }
                    }
                }
            }
            for (int k=0; k<isBusca.length;k++){
                if (!isBusca[k]){
                    deVerdade = false;
                }
            }
            if (deVerdade){
                intersectList.add(yara);
            }
        }
        //ordenar
        
        //printar resultados
        int countt = 1;
        System.out.println(intersectList.size()+" resultados para: "+busca);
        System.out.println("");
        for (Invertida ana: intersectList){
            System.out.println(countt+": "+ana.getSite());
        }
            
    }
}