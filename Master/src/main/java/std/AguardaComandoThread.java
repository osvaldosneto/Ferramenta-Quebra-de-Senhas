package std;

import java.util.ArrayList;
import java.util.Scanner;

/** Classe responsável por aguardar os comandos serem listados
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */

public class AguardaComandoThread extends Thread  {

    public Global variaveis;
    public Scanner leitor;

    /** Método construtor
     *
     * @param variaveis Global - Objeto responsável por armazenar todas as variáveis necessárias no projeto
     */
    public AguardaComandoThread(Global variaveis){
        this.variaveis = variaveis;
        this.leitor = new Scanner(System.in);
    }

    /** Método Run responsável por manter a thread rodando
     *
     */
    @Override
    public void run() {
        while (true) {
            System.out.println("\nDigite: \n [1] Para encerrar tarefa john\n [2] Para iniciar tarefa john \n [3] Para listar trabalhadores \n\n");
            int op = leitor.nextInt();
            switch (op) {
                case 1:
                    EncerraProcesso();
                    break;
                case 2:
                    iniciaTrabalho();
                    break;
                case 3:
                    ListarCaracteristicas();
                    break;
                default:
                    System.out.println("Opção Inválida insira nova opção.\n");
                    break;
            }
        }
    }

    /** Método responsável por armazenar em uma lista de comando a ação de encerrar o processo
     *
     */

    public synchronized void EncerraProcesso(){
        ArrayList<Escravo> es = variaveis.getEscravosTrabalhando();
        if(es.size()==0){
            System.out.println("Nenhum trabalhador executando tarefa no momento.");
        } else {
            for (int i = 0; i < es.size(); i++) {
                Comandos com = new Comandos();
                Escravo escravo = es.get(i);
               // escravo.setEstado(0);
                com.setEscravo(escravo.getNome());
                com.setAcao("rmv");
                variaveis.AddComandos(com);
            }
        }
    }

    /**Metodo responsável por listar as características de cada processo
     *
     */
    public void ListarCaracteristicas(){
        ArrayList<Escravo> escravos = variaveis.getEscravos();
        if(escravos.size()==0){
            System.out.println("Nenhum trabalhador disponível para executar tarefa.");
        } else {
            System.out.println("\n<<<<<Listagem dos processos>>>>>");
            for (int i = 0; i < escravos.size(); i++) {
                Comandos com = new Comandos();
                Escravo escravo = escravos.get(i);
                com.setEscravo(escravo.getNome());
                com.setAcao("ack");
                variaveis.AddComandos(com);
            }
        }
        try{
            this.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**Metodo responsável por selecionar e incerir na lista de comando a palavra chave para inicio de trabalho
     *
     */
    public void iniciaTrabalho(){
        ArrayList<Escravo> escravos = variaveis.getEscravosLivres();
        if(escravos.size()==0){
            System.out.println("Nenhum trabalhador em Espera.");
        } else {
            System.out.println("Informe o nome do arquivo a enviar para análise.");
            String nomeArquivo = leitor.next();
            System.out.println("Você possui " + escravos.size() + " processos em espera.");
            for (int i = 0; i < escravos.size(); i++) {
                Escravo e = escravos.get(i);
                e.setEstado(1);
                Comandos c = new Comandos();
                c.setEscravo(e.getNome());
                c.setNomeArquivo(nomeArquivo);
                c.setAcao("work");
                System.out.println("\nInforme o numero de caracteres que o processo " + e.getNome() + " deve executar:");
                System.out.println("Este número deve ser entre 5 e 8.");
                int caracteres = leitor.nextInt();
                while ((caracteres < 5) || (caracteres > 8)) {
                    System.out.println("Numero inválido, favor insira novamente.");
                    caracteres = leitor.nextInt();
                }
                c.setNumeroCaracteres(caracteres);
                variaveis.AddComandos(c);
            }
        }
    }
}