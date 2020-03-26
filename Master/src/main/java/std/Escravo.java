package std;

/** Classe responsável por guardar as iniformações do escravo
 * @author Osvaldo da Silva Neto
 * Version 1.0
 */

public class Escravo {

    private int nome;
    private int estado;

    /**Metodo consrutor do escravo
     *
     * @param nome - identificação do escravo
     */
    public Escravo(int nome){
        this.nome = nome;
        this.estado = 0;
    }

    /**Metodo responsável por setar o nome do escravo
     *
     * @param nome - nome do escravo para setar
     */
    public synchronized void setNome(int nome) {
        this.nome = nome;
    }

    /**Metodo responsável por setar o estado do escravo
     * 1 está ocupado
     * 0 está livre
     *
     * @param estado - 0 ou 1 ocupado ou livre
     */
    public synchronized void setEstado(int estado) {
        this.estado = estado;
    }

    /**Metodo responsável por retornar o nome do escravo
     *
     * @return nome - nome do escravo
     */
    public synchronized int getNome() {
        return nome;
    }

    /**Metodo responsável por informar se o escravo está ou não ocupado
     *
      * @return true se está ocupado e false caso esteja livre
     */
    public synchronized boolean getEstado(){
        if (this.estado==1){
            return true;
        } else {
            return false;
        }
    }
}
