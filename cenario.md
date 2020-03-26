> Engenharia de Telecomunicações - Sistemas Distribuídos (STD29006)
>
> Instituto Federal de Santa Catarina - campus São José
>
> Osvaldo da Silva Neto



## Detalhamento do Funcionamento

A princípio nosso sistema conectará com três workers (escravos), onde toda a parte de gerenciamento destes será feita pelo próprio servidor (master), ja a principal função dos workers será rodar nossa aplicação john (aplicação de quebra de senha) com os arquivos enviados pelo usuário através do servidor.
Para execução de nosso sistema existem algumas regras necessárias que irão garantir o pleno funcionamento, são elas;

- 1 - Tenha certeza de que os arquivos necessários para o processamento estejam no caminho ou no diretório do projeto;

- 2 - O usuário deverá primeiramente colocar o servidor(master) em execução antes dos workers(escravos);

- 3 - Ao rodar a aplicação apenas o servidor master será responsável por designar comandos aos workers, potanto os workers irão aguardar ser chamado para execução ou cancelamento de sua tarefa.


## Manual de Uso

Ao ligar o servidor será aberto um menu oferecendo as opções de Listar processos, Encerrar processos e Iniciar processo, após selecionar a opção desejada o usuário terá então uma série de opções para preencher.

Caso o usuário selecione a opção de listar os processos, a ação a ser desenvolvida será a exibição de uma lista com os processos ativos e sua situação (ocupado ou em espera).

Caso o usuário selecione a opção de encerrar processos, será enviado um comando para que os mesmos acabem encerrando suas atividade, vale resaltar que neste caso todo o desnvolvimento da operação será perdida.

Por fim se o usuário selecionar Iniciar processo, então será aberto um campo para responder o nome do arquivo a enviar, logo após será perguntado os números de caracteres que o usuário deseja aplicar para configuração de nosso aplicativo John, vale resaltar que este número representa a quantidade de caracteres que nossa senha possa ter.

 
![](/draw/diag.png)

Como podemos ver no diagrama acima, a utilização do nosso servidor será de total interação com o usuário, ja nossos trabalhadores irão apenas aguardar serem designados para a excução de suas tarefas (quebra de senha - john), no momento em que todos os passos da opção 2 forem preenchidos, nosso servidor envia uma mensagem de alerta ao nosso trabalhador onde ao interpreta-la ele iniciará a transferência de arquivo e após o processamento da aplicação john com seus devidos numeros de caracteres a pesquisar.
Segue abaixo diagrama dos passos que nossos trabalhadores irão executar.

![](/draw/diag2.png)

Vale resaltar que a única initerface de comunicação com os trabalhadores serão por meio da comunicação com o servidor. Os trabalhadores apenas irão executar toda a tarefa que o servidor ordenar, seja ela de iniciar ou cancelar o processamento dos dados.

## Procedimento Para Execução do Processo Com Três Trabalhadores e Um Master

Primeiramente o usuário deve colocar o servidor no ar, pois é através dele que toda a comunicação irá se estabelecer.
Pra dar início aos trabalhadores devemos informar qual o ip de destino eles devem se conectar, esta informação é passada pela linha de comando ao iniciar o processo dos servidores, executando estes procedimentos com três clientes vamos ter um cenário como ilustrado na figura abaixo.

![](/draw/caso.png)

Tendo em vista que todas as conexões ocorreram sem nenhum problema, agora podemos entrar com as opções válidas em nosso sistema.
Vale lembrar que a princípio temos apenas três opções, Inicia Processo, Lista Processo e Finaliza Processo.
Tendo em vista que nenhum dos trabalhadores estão ocupados, nossa única opção no momento é lista-los ou iniciar alguma tarefa.

Caso iniciarmos uma nova tarefa, nosso servidor irá perguntar qual nome de arquivo devo enviar, vale resaltar que o arquivo deve ser informado corretamente, caso contrário deve-se reiniciar todo o procedimento, após a inserção do nome do arquivo, o usuário será questionado com quantos caracteres nossa aplicação john deve ser executada, lembrando que este procedimento será feito três vezes, pois neste momento temos três trabalhadores em modo de espera.
Ao entrar com estas informações nosso servidor irá enviar o arquivo para todos os trabalhadores com seus respectivos números de caracteres para iniciar o processo da aplicação John.
Considerando que tudo esteja funcionando perfeitamente até o momento, então agora podemos listar os processos e acompanha-los suas devidas situações. Se por acaso algum dos trabalhadores acabar sua tarefa ele irá retornar uma mensagem informando as senhas quebradas pelo aplicativo.
Portanto segue abaixo um breve script com os passos a seguir:

- 1 - Inicie primeiramente nosso servidor (master), ele é responsável por aguardar a conexão, isto pode ser feito compilando o programa pela linha de comando sem nenhum parâmetro ou até mesmo pela IDE.

> java -jar "nome do arquivo.jar"

- 2 - Inicie todos os trabalhadores, este procedimento é necessário o envio por linha de comando do ip de nosso servidor, este passo pode ser executado pela linha de comando depois de obter o arquivo .jar, no seguinte formato.

> java -jar "nome do arquivo.jar" "ip do servidor"

- 3 - Como toda a interação será feita no servidor (master) portanto deve-se seguir os passos oferecidos no menu.

> 1 - Encerrar tarefa.
>
> 2 - Iniciar tarefa.
>
> 3 - Listar processos.
>

- 4 - Caso selecionado opção Inicia tarefa john, irá aparecer outra opção para inserir o número de caraceres que a aplicação john irá executar, vale lembrar que este número deve ser entre 5 e 8, pois nossa aplicação irá funcionar com o formato de modo incremental limitando o incrementador, caso contrário não será aceito. Após este passo todas as aplicações que ainda estiverem processando o john estarão em estado "Trabalahdo".

- 5 - Como ultima opção encerrar tarefas john, neste passo nossa aplicação será encerrada, voltando ao estado de "em espera", vale lembrar que caso nossa aplicação não quebre a senha será retornado a situação onde o sistema parou, então só após receber esta mensagem nossa aplicação irá liberar o trabalhador para receber nova tarefa, **portanto aguarde retorno antes de enviar nova operação ao cliente.**

Obs.: Tenha certeza de que o **nome do arquivo a analisar seja inserido corretamente**, caso contrário será necessário reenviar o arquivo ou reiniciar o sistema.




















