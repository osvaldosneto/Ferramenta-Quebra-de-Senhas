package std;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class MinhaThreadRX extends Thread {

    private int porta;
    private String nomeArquivo;
    private String ip;
    private Socket conexao;
    private DataInputStream entrada;
    private Global g;
    private MinhaThreadJohn minhaThreadJohn;
    private String[] recebida;
    private ProcessBuilder pb;
    private Process pr;

    /**Metodo responsável por conectar a porta TX do servidor
     *
     * @param portaTx - porta tx do servidor
     * @param ip - ip do servidor
     * @param portaTx - porta de transmissão de dados
     */
    public MinhaThreadRX(int portaTx, String ip, Global g){
        this.porta = portaTx;
        this.g = g;
        this.ip = ip;
        try {
            this.conexao = new Socket(ip, porta);
            this.entrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Metodo responsável por rodar a thread
     *
     */
    @Override
    public void run(){
        while (true){
           try {
               recebida = entrada.readUTF().split(",");
              // System.out.println("Comando Recebido:" + recebida[0]);
               if (recebida[0].equals("work")) {
                   System.out.println("Comando recebido -> INICIA PROCESSO JOHN");
                   this.iniciaProcesso();
               } else if (recebida[0].equals("rmv")) {
                   System.out.println("Comando recebido -> ENCERRA PROCESSO JOHN");
                   this.finalizaProcesso();
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    /**Metodo responsável por finalizar o processo
     *
     */
    public void finalizaProcesso(){
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add("pkill john");
        System.out.println("pkill john");
        ProcessBuilder pb = new ProcessBuilder(commands);
        try {
            Process pr = pb.start();
        }catch (IOException e) {
            System.out.println("Problemas ao finalisar o processo.");
        }
        System.out.println("Processo finalizado com sucesso.");
    }

    /**Metodo responsável por iniciar thread que execta aplicação john
     *
     */
    public void iniciaProcesso(){
        this.RecebeFiletxt();
        minhaThreadJohn = new MinhaThreadJohn(nomeArquivo, g, recebida[1]);
        minhaThreadJohn.start();
    }

    /**Metodo responsável por receber o arquivo de texto
     *
     */
    private void RecebeFiletxt(){
        try {
            this.nomeArquivo = Integer.toString(conexao.getLocalPort())+".txt";
            InputStream entradas = conexao.getInputStream();
            System.out.println("Transferindo arquivo para análise...");
            File f1 = new File(nomeArquivo);
            FileOutputStream out = new FileOutputStream(f1);
            int c;
            while ((c = entradas.read()) != 4) {
                out.write(c);
            }
            System.out.println("Arquivo transferido com sucesso.");
        }catch (IOException e) {
            System.out.println("Problemas na recepção de arquivo.");
            e.printStackTrace();
        }
    }
}