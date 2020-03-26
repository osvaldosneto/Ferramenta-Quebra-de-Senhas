package std;

/** Classe responsável por invocar as threads AguardaConexaoThread e AguardaComandoThead
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */

public class Master {

    private volatile Global variaveis;
    private AguardaConexaoThread aguardaConexaoThread;
    private AguardaComandoThread aguardaComandoThread;

    public Master (){
        variaveis = new Global();

        //inicia thread aguardando comando
        this.aguardaComandoThread = new AguardaComandoThread(variaveis);
        this.aguardaComandoThread.start();

        //inicia thread aguardando conexão
        this.aguardaConexaoThread = new AguardaConexaoThread(variaveis);
        this.aguardaConexaoThread.start();
    }
}


