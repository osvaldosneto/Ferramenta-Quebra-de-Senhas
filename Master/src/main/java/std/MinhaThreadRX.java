package std;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MinhaThreadRX extends Thread {

    public Global variaveis;
    private Socket conexao;
    private ServerSocket servidor;
    private DataInputStream entrada;

    /** Método construtor
     *
     * @param variaveis Global - Objeto responsável por armazenar todas as variáveis necessárias no projeto
     * @param servidorRx - servidor responsável pela recepção dos dados
     */
    public MinhaThreadRX(Global variaveis, ServerSocket servidorRx) {
        this.variaveis = variaveis;
        servidor = servidorRx;
        this.ConectandoSocket();
    }


    /**Metodo por conectar os sockets
     *
     */
    public void ConectandoSocket(){
        try {
            this.conexao = servidor.accept();
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
        while(true) {
            try {
                String in = entrada.readUTF();
                if(in.equals("porta")){
                    in = entrada.readUTF();
                    Escravo esc = variaveis.getEscravo(Integer.parseInt(in));
                    System.out.println("\nProcesso "+ in + " finalizado.");
                    esc.setEstado(0);
                    in = entrada.readUTF();
                }
                System.out.println(in);
            } catch (IOException e) {
                break;
            }
        }
    }
}