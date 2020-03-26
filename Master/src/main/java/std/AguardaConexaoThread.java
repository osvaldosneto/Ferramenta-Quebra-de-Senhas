package std;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** Classe responsável por aguardar as conexões
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */

public class AguardaConexaoThread extends Thread {

    private ServerSocket servidor;
    private Socket conexao;
    private DataOutputStream saida;
    private MinhaThreadTX threadTX;
    private MinhaThreadRX threadRX;
    private Global variaveis;

    /**Metodo construtor da classe
     *
      * @param variaveis - variaveis a utilizar no processo
     */
    public AguardaConexaoThread(Global variaveis){
        this.variaveis = variaveis;
        this.ConectandoSocket();
    }

    /**Metodo responsável por estabilizar os sockets das comunicações
     *
     */
    public void ConectandoSocket() {
        try {
            this.servidor = new ServerSocket(12345);
            this.conexao = servidor.accept();
            ServerSocket servidorRx = new ServerSocket(0);
            ServerSocket servidorTx = new ServerSocket(0);
            int portRx = servidorRx.getLocalPort();
            int portTx = servidorTx.getLocalPort();

            this.saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeUTF(Integer.toString(portRx));
            saida.writeUTF(Integer.toString(portTx));
            this.threadTX = new MinhaThreadTX(variaveis, servidorTx);
            this.threadRX = new MinhaThreadRX(variaveis, servidorRx);
            threadRX.start();
            threadTX.start();

            Escravo e = new Escravo(portTx);
            variaveis.AddEscravos(e);

            servidor.close();
            conexao.close();
            saida.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true) {
            this.ConectandoSocket();
        }
    }
}