> Engenharia de Telecomunicações - Sistemas Distribuídos (STD29006)
>
> Instituto Federal de Santa Catarina - campus São José
>
>Osvaldo da Silva Neto



# Relatório Técnico


Os sistemas distribuídos estão em todo o lugar, ou melhor, acessíveis a partir de qualquer lugar. Segundo Tanenbaum, um sistema distribuído é um conjunto de computadores independentes entre si (e até diferentes), ligados através de uma rede de dados, que se apresentam aos utilizadores como um sistema único e coerente.

Os Sockets, são uma abstracção para endereços de comunicação através dos quais os processos se comunicam. Cada endereço tem um identificador único composto pelo endereço da máquina e o identificador local da porta usado pelo processo. Este identificador de porta é usado para mapear dados recebido pela máquina para processos (aplicações) específicos. Os principais tipos de sockets são, Socket_Stream ou SOCK_DGRAM. Os sockets_Stream (TCP) garantem confiabilidade na comunicação (garantia da entrega de pacotes), enquanto os sockets_DGRAM (UDP) não dão garantia na entrega de pacotes a nível da camada de transporte.

Pensando nisso, para conclusão de nosso projeto foi decidido utilizar o tipo de comunicação por socket_stream, por nos oferecer uma confiabilidade na transmissão dos dados muito maior que o Sock_Dgram, pois a utilização de protocolos TCP em uma comunicação nos garante uma taxa de entrega de praticamente 100% dos pacotes, diferente do protocolo UDP onde não existe garantia alguma.

O processo de comunicação ocorre da seguinte forma: o servidor é colocado na situação de aguardo por comunicações direcionadas para a porta em que está ouvindo. O cliente deve saber previamente qual o IP em que o servidor encontra-se, então o cliente solicita uma conexão ao servidor, se nenhum problema ocorrer, o servidor aceita a conexão gerando um socket em uma outra porta vaga, criando assim um canal de comunicação entre o cliente e o servidor, deixando a porta de escuta livre para outra conexão.

![](/draw/caso-porta.png)

Figura 01 - Portas conectadas I


Como podemos ver na figura acima existem duas portas conectadas aos clientes e outra aguardando conexão.
Uma das tarefas de nosso do servidor é ficar em loop, aguardando novas ligações e “gerando” sockets para atender as solicitações de clientes.

Depois de estabelecida a ligação entre servidor e cliente, tanto cliente quanto servidor passam a poder enviar mensagens entre si, isto permitirá que toda a aplicação aconteça de forma correta.

Tendo nossa comunicação estabelecida, damos inicio a aplicação, primeiramente acontece uma transferência de arquivo para todos os trabalhadores via OutputStream pertencente a classe "DataOutpusStream", onde serializamos os dados e os enviamos.

Após a conclusão do envio do arquivo nossos trabalhadores dão início a execução do trabalho (aplicação John), com seus devidos parâmetros. O processo é executado utilizando um objeto ProcessBuilder pertencente a classe "ProcessBuilder", este procedimento é utilizado tanto no início da execução da aplicação John quanto para interrompe-lá.
	
Ao concluir ou finalizar a execução do processo nossa aplicação retornará uma mensagem também utilizando DataOutpusStream iniformando qual situação a aplicação se encontra.


## Sistema Distribuído de Quebra de Senhas

Este projeto visa desenvolver habilidades necessárias para utilização e desenvolvimento de um sistema distribuído sendo replicado em diversas máquinas.

A patir desta idéa, nosso projeto irá desenvolver um sistema onde será possível quebrar senhas utilizando o auxilio de trabalhadores conectados ao servidor em qualquer lugar.

Nosso sistema funcionará com as atividades sendo distribuídas pelo servidor como mostrado na figura abaixo;

![](/draw/diagrama.png)

Como podemos ver nesta figura nosso servidor contará com a ajuda de alguns trabalhadores para execução de nossa tarefa principal que é a quebra de senhas fornecidas pelo usuário.]


## Aplicação Master (Sevidor)

Nesta aplicação constatou-se a necessidade de várias tarefas serem executadas ao mesmo tempo, para administra-las surgiu a idéia de disparar algumas threads ao longo do processo. Nosso processo conta com 4 threads que irão operar de forma simultânea, são elas;

- AguardaComandoThread - esta thread é responsável por capturar todos os comandos que posteriormente serão enviados aos clientes.

- AguardaConexaoThread - esta thread fica aguardando conexões na porta "12345".

- MinhaThreadRX - esta thread é responsável pela recepção de dados vinda de nossos clientes.

- MinhaThreadTx - esta thread é responsável pela emissão de dados aos nossos clientes.

Além destas Threads nosso sistema conta com algumas outras classes que são basicamente responsáveis pela organização de dados em nosso sistema, são elas;

- Comandos - responsável por organizar os comandos a serem enviados aos clientes.

- Escravo - responsável por organizar os dados referente aos clientes que estão conectados.

- Global - armazena todas as variáveis que serão compartilhadas em todo o sistema.

- Master - apenas da início as threads AguardaComandoThread, AguardaConexaoThread e criação do objeto Global.

Analisando um pouco as classes citadas acima podemos peerceber que nosso projeto acabou ficando dividido entre classes manipulam dados e outras responsáveis por armazenamento dos dados.


## Aplicação Cliente (Trabalhadores)

Seguindo o mesmo raciocínio, foi constatado nesta classe a necessidade de algumas threads, segue abaixo;

- MinhaThreadRX - esta thread é responsável pela recepção de dados vinda de nosso servidor.

- MinhaThreadTx - esta thread é responsável pela emissão de dados ao nosso servidor.

- MinhaThreadJohn - esta thread é responsável pela iniciação e termino da aplicação john em nosso cliente.

- Cliente - esta classe é responsável por conectar ao servidor e disparar as threads MinhaThreadRX, MinhaThreadTx e criar o objeto Global.

- Global - armazena todas as variáveis que serão compartilhadas em todo o sistema.





## Conclusão

Como foi observado neste projeto, ficou claro a necessiade de uma comunicação estável e eficiente, portanto toda a aplicação deve funcionar de forma sincronizada, a idéia de utilizar threads foi excencial para conclusão, pois somente assim foi possível poder executar várias tarefas ao mesmo tempo sem a necessidade de parar toda a comunicação entre servidor e cliente.

A utilização de sockets no enlace de comunicação entre cliente e servidor foi de extrema importância para a conclusão do projeto, pois foi através dele que foi implementado toda a parte de comunicação entre os processos.




