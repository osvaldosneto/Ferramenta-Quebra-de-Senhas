package std;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/** Classe responsável por conectar ao servidor e disparar as threads tx e rx
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */
public class Cliente {

    /**construtor do cliente - conecta ao socket do servidor que está na escuta
     *
     * @param args - String com primeiro campo IP e segundo porta
     */
    public static void main(String[] args){

        String porta = "12345";
        String ip = args[0];
        Socket conexao;
        String portaTx, portaRx;
        MinhaThreadTX threadTX;
        MinhaThreadRX threadRX;
        Global g = new Global();

        try{
            conexao = new Socket(ip,Integer.parseInt(porta));
            DataInputStream entrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
            portaTx = entrada.readUTF();
            portaRx = entrada.readUTF();

            threadTX = new MinhaThreadTX(Integer.parseInt(portaTx), Integer.parseInt(portaRx), ip, g);
            threadTX.start();
            threadRX = new MinhaThreadRX(Integer.parseInt(portaRx), ip, g);
            threadRX.start();

        } catch (IOException e) {
            System.out.println("Ocorreu algo errado, verifique se o ip está correto.");
        }
    }
}
