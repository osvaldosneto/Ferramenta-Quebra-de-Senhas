package std;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MinhaThreadJohn extends Thread {

    private ProcessBuilder pb;
    private Process pr;
    public Global g;
    private String nomeArquivo;
    private String caracteres;

    /**Metodo responsável por rodar a thread
     *
     * @param caracteres - número de caracteres em que a pesquisa irá trabalhar
     * @param g - variáveis globais
     * @param nomeArquivo - nome do arquivo para salvar
     */
    public MinhaThreadJohn(String nomeArquivo, Global g, String caracteres){
        this.nomeArquivo = nomeArquivo;
        this.g = g;
        this.caracteres = caracteres;
    }

    /**Método responssável por rodar a thread john
     *
     */
    @Override
    public void run(){
        ArrayList<String> commands = new ArrayList<String>();
        ArrayList<String> retorno = new ArrayList<String>();
        String line;
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add("john -i=All"+caracteres +" "+ nomeArquivo);
        System.out.println("john -i=All"+caracteres +" "+ nomeArquivo);
        this.pb = new ProcessBuilder(commands);
        try {
            pr = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        commands.clear();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add("john -show "+ nomeArquivo);
        this.pb = new ProcessBuilder(commands);
        try{
            System.out.println("john -show "+ nomeArquivo);
            pr = pb.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            while ((line = r.readLine()) != null) {
                retorno.add(line);
                System.out.println(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.setSenhas(retorno);
    }
}