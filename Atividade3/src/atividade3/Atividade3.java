/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividade3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author aluno
 */
public class Atividade3 implements Runnable{
    
    public static void main(String[] args) {
        Atividade3 at3 = new Atividade3();
        new Thread(at3).start();
    }

    @Override
    public void run() {
        try {
            Socket cliente = new Socket("127.0.0.1", 4444);
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            String recebe;
            while (true){
                String x = JOptionPane.showInputDialog("Digite o número");
                out.write(x);
                out.newLine();
                out.flush();
                recebe = in.readLine();
                System.out.println(recebe);
                if(recebe.contains("Parabéns")){
                    out.write("tchau");
                    out.newLine();
                    out.flush();
                    break;
                }                
            }
        } catch (IOException ex) {
            System.err.println("Erro: " + ex);
        }        
    }      
}