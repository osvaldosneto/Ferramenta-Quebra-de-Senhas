package std;

import java.util.ArrayList;

/** Classe responsável por guardar todas as variáveis utilizadas no processo
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */

public final class Global {

    private ArrayList<Escravo> escravos;
    private ArrayList<Comandos> comandos;
    private String nomeArquivo;

    public Global(){
        escravos = new ArrayList();
        comandos = new ArrayList();
    }


    /**Metodo responsável por adicionar escravos a lista de escravos
     *
     * @param escravo - escravo para adicionar
     */
    public synchronized final void AddEscravos(Escravo escravo){
        escravos.add(escravo);
    }

    /**Método responsável por adicionar o comando a lista de comandos
     *
     * @param c - comando a adicionar
     */
    public synchronized final void AddComandos(Comandos c){
        comandos.add(c);
    }

    /**Metodo responsável por retornar o tamanho da lista de comando
     *
     * @return comandos.size - tamanho da lista de comandos
     */
    public synchronized final int ComandosSize(){
        return comandos.size();
    }

    /**Metodo responsável por remover o escravo pré selecionado
     *
     * @param escravo - escravo para remover
     */
    public synchronized final void RemoverEscravo(Escravo escravo){
        escravos.remove(escravo);
    }

    /**Metodo responsável por retornar a llista de escravos
     *
     * @return listadeEscravos - lista de rescravos
     */
    public synchronized final ArrayList<Escravo> getEscravos() {
        return escravos;
    }

    /**Método responsável por retornar a lista de comandos
     *
     * @return comandos - lista de comandos
     */
    public synchronized final ArrayList<Comandos> getComandos() {
        return comandos;
    }

    /**Metodo responsável por retornar a lista de escravos livres
     *
     * @return lista - lista de escravos livres a retornar
     */
    public synchronized final ArrayList<Escravo> getEscravosLivres(){
        Escravo e;
        ArrayList<Escravo> lista = new ArrayList<Escravo>();
        for (int i = 0; i < escravos.size(); i++) {
            e = escravos.get(i);
            if (!e.getEstado()){
                lista.add(e);
            }
        }
        return lista;
    }

    /**Metodo responsável por retornar os escravos que estão trabalhando
     *
     * @return lista - lista de escravos trabalhando
     */
    public synchronized final ArrayList<Escravo> getEscravosTrabalhando(){
        Escravo e;
        ArrayList<Escravo> lista = new ArrayList<Escravo>();
        for (int i = 0; i < escravos.size(); i++) {
            e = escravos.get(i);
            if (e.getEstado()){
                lista.add(e);
            }
        }
        return lista;
    }

    /**Metodo responsável por retornar o escravo
     *
     * @param nome - nome do escravo a retornar
     * @return e - escravo a retornar
     */
    public synchronized Escravo getEscravo(int nome) {
        Escravo e = new Escravo(0);
        for (int i = 0; i < escravos.size(); i++) {
            e = escravos.get(i);
            if (e.getNome() == nome) return e;
        }
        return e;
    }

    public synchronized String getNomeArquivo() {
        return nomeArquivo;
    }

    public synchronized void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
