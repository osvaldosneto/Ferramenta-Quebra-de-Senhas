package std;

/** Classe responsável por armazenar toda a parte de comando enviada para o processo master
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */

public class Comandos {

    public String acao;
    public int escravo;
    public String nomeArquivo;
    public int numeroCaracteres;

    public Comandos(){}

    /**Método para retorno de ação do comando
     *
     * @return ação - informa qual operação o comando ira fazer
     */
    public synchronized String getAcao() {
        return acao;
    }

    /**Método para retorno de escravo
    *
    * @return escravo - informa qual ecravo
    */
    public synchronized int getEscravo() {
        return escravo;
    }


    /**Método para setar a ação do escravo
     *
     * @param acao - qual operação o escravo irá fazer
     */
    public synchronized void setAcao(String acao) {
        this.acao = acao;
    }

    /**Metodo setar o escravo informado
     *
     * @param escravo nome do escravo a informar
     */
    public synchronized void setEscravo(int escravo) {
        this.escravo = escravo;
    }

    /**Método responsável por informar o nome do arquivo a enviar
     *
     * @return nomeArquivo - nome do arquivo a enviar
     */
    public synchronized String getNomeArquivo() {
        return nomeArquivo;
    }

    /**Método responsável por setar o nome do arquivo
     *
     * @param nomeArquivo - nome do arquivo a enviar
     */
    public synchronized void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public synchronized int getNumeroCaracteres() {
        return numeroCaracteres;
    }

    public synchronized void setNumeroCaracteres(int numeroCaracteres) {
        this.numeroCaracteres = numeroCaracteres;
    }
}
