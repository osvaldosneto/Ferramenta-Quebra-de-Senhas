package std;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/** Classe responsável por conectar ao servidor - envio de mensagens
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */

public class MinhaThreadTX extends Thread {

    private int porta;
    private int portaRx;
    private String ip;
    private Socket conexao;
    private Global g;
    private DataOutputStream saida;

    /**Construtor da classe MinhaThreadTX
     *
     * @param portaTx - porta de comunicação
     * @param portaRx - porta de comunicação
     * @param ip - ip do servidor
     * @param g - local de armazenamento das variáveis utilizadas
     */
    public MinhaThreadTX(int portaTx, int portaRx, String ip, Global g){
        this.porta = portaTx;
        this.g = g;
        this.ip = ip;
        this.portaRx = portaRx;
        this.ConectandoSocket();
    }

    /**Método responsável por conectar o socket
     *
     */
    public void ConectandoSocket(){
        try {
            conexao = new Socket(ip, porta);
            this.saida = new DataOutputStream(conexao.getOutputStream());
            System.out.println("Conectado ao servidor.");
            System.out.println("Aguardando comandos...");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Método responssável por rodar a thread tx
     *
     */
    @Override
    public void run(){
        while(true) {
            ArrayList<String> lista = g.getSenhas();
            if (lista.size() > 0) {
                try {
                    this.sleep(2000);
                    saida.writeUTF("porta");
                    saida.writeUTF(Integer.toString(portaRx));
                    for(int i=0; i<lista.size(); i++){
                        saida.writeUTF(lista.get(i));
                    }
                    lista.clear();
                } catch (IOException | InterruptedException e) {
                    System.out.println("Ocorreu um problema.");
                }
            }
        }
    }
}