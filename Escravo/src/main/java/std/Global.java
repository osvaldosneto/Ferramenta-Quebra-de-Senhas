package std;

import java.util.ArrayList;

public class Global {

    private boolean situacao;
    private ArrayList<String> senhas;

    /**construtor da classe Global - armazena todas as variáveis
     *
     *
     */
    public Global(){
        this.situacao = true;
        this.senhas = new ArrayList<>();
    }

    /**método responsável por retornar a situação
     *
     * @return situação - trabalhando ou em espera
     */
    public synchronized boolean isSituacao() {
        return situacao;
    }

    /**método responsável por setar a situação
     *
     * @param situacao - true caso esteja livre, falso caso esteja em espera
     * @return situação - trabalhando ou em espera
     */
    public synchronized void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    /**método responsável por retornar uma lista de senhas
     *
     * @return getSenhas - lista string com as senhas
     */
    public synchronized ArrayList<String> getSenhas() {
        return senhas;
    }

    /**método responsável por setar uma lista de senhas
     *
     * @param senhas - lista de senhas a setar
     */
    public synchronized void setSenhas(ArrayList<String> senhas) {
        this.senhas = senhas;
    }
}
