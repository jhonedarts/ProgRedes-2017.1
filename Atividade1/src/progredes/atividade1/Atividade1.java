/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progredes.atividade1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 *
 * @author aluno
 */
public class Atividade1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Atividade1 at = new Atividade1();
        at.progRedes();
    }
    
    public void progRedes(){
        URL url = null;
        try{
            System.setProperty("http.proxyHost", "10.65.16.2"); 
            System.setProperty("http.proxyPort", "3128");
            
            url = new URL("http://www.aprenderexcel.com.br//imagens/noticia/385/2901-1.jpg");
            //url = new URL("http://www.uefs.br");
            URLConnection con = url.openConnection();
            
            System.out.println("Tamanho: " + con.getContentLength());
            System.out.println("Modificado: " + new Date(con.getLastModified()));
            System.out.println("Tipo: " + con.getContentType());
                        
            DataInputStream in =  new DataInputStream(con.getInputStream());
            String titulo = "";
            
            if(con.getContentType().contains("html")){                
                while(in.available()>0){             
                    titulo = in.readLine().trim(); 
                    if (titulo.contains("<title>")){
                        titulo = titulo.replace("<title>", "");
                        titulo = titulo.replace("</title>", "");
                        System.err.println("Título: "+titulo);
                        return;
                    }
                } 
            }else{
                InputStream is = url.openStream();
                String tipo = con.getContentType().substring(con.getContentType().lastIndexOf('/')+1);
                FileOutputStream arquivo = new FileOutputStream("/home/aluno/Documentos/a."+tipo);
                int bytes = 0;
 
                while ((bytes = is.read()) != -1) {
                    arquivo.write(bytes);
                }
                is.close();
                arquivo.close();
                System.err.println("Salvei!");
            }
            
        }catch(MalformedURLException e){
            
        } catch (IOException ex) {
        }
    }
    
}
