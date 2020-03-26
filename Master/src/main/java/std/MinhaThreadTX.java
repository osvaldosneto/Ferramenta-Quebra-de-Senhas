package std;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/** Classe responsável por fazer a comunicação entre trabalhador e servidor
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */

public class MinhaThreadTX extends Thread {

    private Global variaveis;
    private Socket conexao;
    private ServerSocket servidor;
    private DataOutputStream saida;
    private Scanner leitor;
    private boolean conectado;

    /**Metodo construtor
     *
     * @param v - variaveis globais declaradas na classe global
     * @param servidor - Socket servidor
     */
    public MinhaThreadTX(Global v, ServerSocket servidor) {
        this.variaveis = v;
        this.servidor = servidor;
        this.leitor = new Scanner(System.in);
        this.ConectandoSocket();
        this.conectado = true;
      //  this.escravo = variaveis.getEscravo(conexao.getLocalPort());
    }

    /**Metodo responsável por conectar ao socket trabalhador
     *
     */
    public void ConectandoSocket(){
        try {
            this.conexao = servidor.accept();
            this.saida = new DataOutputStream(conexao.getOutputStream());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Metodo responsável por rodar a thread
     *
     */
    @Override
    public void run(){
        while(conectado){
            ArrayList<Comandos> c = variaveis.getComandos();
            while(c.size()>0) {
                try {
                    Comandos com = c.get(0);
                    if (com.getEscravo() == conexao.getLocalPort()) {
                        Comandos rmv = c.remove(0);
                        if (rmv.getAcao().equals("rmv")) {
                            this.EncerraProcesso();
                        } else if (rmv.getAcao().equals("work")) {
                            this.IniciaTrabalho(rmv);
                        } else {
                            this.ListaTrabalhadores(rmv);
                        }
                    }
                }catch(IndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }

    /**Metodo responsável por listar os trabalhadores
     *
     *
     * @param c - comando a executar (enviar ao trabalhador)
     */
    public void ListaTrabalhadores(Comandos c){
        Escravo escravo = variaveis.getEscravo(conexao.getLocalPort());
        try {
            saida.writeUTF(c.getAcao());
        } catch (IOException e) {
            variaveis.RemoverEscravo(escravo);
            this.conectado = false;
            this.CloseConexion();
        }
        String status;
        if(escravo.getEstado()){
            status = "Trabalhando";
        } else {
            status = "Em Espera";
        }
        System.out.println("Id : " + escravo.getNome() + " >>> Estado : " + status);
    }

    /**Metodo responsável por informar que o escravo deve encerrar o processo
     *
     *
     */
    public void EncerraProcesso(){
        Escravo escravo = variaveis.getEscravo(conexao.getLocalPort());
        try {
            saida.writeUTF("rmv");
        } catch (IOException e) {
            variaveis.RemoverEscravo(escravo);
            this.conectado = false;
            this.CloseConexion();
        }
    }

    /**Metodo responsável por comunicar o trabalhador que ele deve iniciar o trabalho
     *
     *
     * @param c - comando a executar (enviar ao trabalhador)
     */
    public void IniciaTrabalho(Comandos c){
        Escravo escravo = variaveis.getEscravo(conexao.getLocalPort());
        try {
            saida.writeUTF("work,"+c.numeroCaracteres);
            escravo.setEstado(1);
            try{
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FileInputStream in = new FileInputStream(c.getNomeArquivo());
            OutputStream out = conexao.getOutputStream();
            int d;
            while ((d = in.read()) != -1) {
                out.write(d);
            }
            out.write(4);
        } catch (IOException e) {
            variaveis.RemoverEscravo(escravo);
            this.conectado = false;
            this.CloseConexion();
        }
    }

    /**Metodo responsável por encerrar a comunicação cliente servidor]
     *
     */
    public void CloseConexion(){
        try {
            saida.close();
            conexao.close();
            servidor.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}