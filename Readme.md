# Ferramenta distribuída para quebra de senha

> Engenharia de Telecomunicações - Sistemas Distribuídos (STD29006)
>
> Instituto Federal de Santa Catarina - campus São José



Recentemente a senha usada por [Ken Thompson](https://pt.wikipedia.org/wiki/Ken_Thompson), um dos criadores do sistema operacional Unix e da linguagem C, e encontrada no [código fonte do Unix BSD versão 3](https://github.com/dspinellis/unix-history-repo/blob/BSD-3-Snapshot-Development/etc/passwd), foi quebrada por [entusiastas de tecnologia](https://leahneukirchen.org/blog/archive/2019/10/ken-thompson-s-unix-password.html). Esses fizeram uso de ferramentas bem conhecidas para quebra de senha, como o [John](https://www.openwall.com/john/) e o [hashcat](https://hashcat.net/wiki/). 

As senhas de [Dennis Ritchie](https://pt.wikipedia.org/wiki/Dennis_Ritchie) e [Brian W. Kernighan](https://en.wikipedia.org/wiki/Brian_Kernighan) (outros criadores do Unix), de [Stephen Bourne](https://en.wikipedia.org/wiki/Stephen_R._Bourne) (criador da *Bourne shell*) foram facilmente quebradas em 2014, mas a quebra da senha de Ken Thompson não foi possível na época. A senha só foi quebrada recentemente por Nigel Williams e isso levou 4 dias com o aplicativo hashcat e fazendo uso da GPU AMD Radeon Vega64.

No site [arstechnica](https://arstechnica.com/information-technology/2019/10/forum-cracks-the-vintage-passwords-of-ken-thompson-and-other-unix-pioneers/) tem um artigo com mais detalhes sobre o caso.

## Usuários e senhas no Linux

No arquivo `/etc/passwd` é armazenado dados sobre todas as contas de usuários do sistema. O arquivo contém o login, nome completo e outras informações básicas. Cada um desses campos é separado por um caractere delimitador, no caso, o caractere `:`. Abaixo são listadas algumas linhas desse arquivo.

```
root:x:0:0:root:/root:/bin/bash
daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
bin:x:2:2:bin:/bin:/usr/sbin/nologin
sys:x:3:3:sys:/dev:/usr/sbin/nologin
sshd:x:110:65534::/run/sshd:/usr/sbin/nologin
mysql:x:114:117:MySQL Server,,,:/nonexistent:/bin/false
aluno:x:1000:1004:Aluno de STD:/home/aluno:/bin/bash
professor:x:1001:1005:Professor de STD,,,:/home/professor:/bin/bash
```

No passado o *hash* criptográfico da senha do usuário também ficava armazenada nesse arquivo, porém por questões de segurança, atualmente essa informação foi movida para o arquivo `/etc/shadow`. É por isso que o segundo campo do arquivo `/etc/passwd` contém um `x`. Abaixo são listadas algumas linhas do arquivo `/etc/shadow`.

```
root:*:17647:0:99999:7:::
daemon:*:17647:0:99999:7:::
bin:*:17647:0:99999:7:::
sys:*:17647:0:99999:7:::
sshd:*:17647:0:99999:7:::
mysql:!:17983:0:99999:7:::
aluno:$6$vsBsTKe9$a7JUthduydWa.48saUk6AuxMpXVWQkkLvsM8WC8tqSMsrcc2n4885l5AFzeLHnpCkeKi/485W9l9UgS0FJ115/:17717:0:99999:7:::
professor:$6$9EnyPnsOug46R$7jX575L2i4eIacC9aVBT8dyIttxiXeIY9Btqan4LKcXfc5WSKzJOsuJHaeTS9TLnzRP.CbbEwIKAXLH9iArKe0:17788:0:99999:7:::
```

Maiores detalhes sobre esses dois arquivos podem ser obtidos no artigo da [Wikipedia que explica sobre o comando passwd](https://en.wikipedia.org/wiki/Passwd).

Na listagem acima é possível ver que somente os usuários aluno e professor possuem senha. O super usuário `root` não tem senha por padrão e só será possível executar tarefas administrativas nesse computador por meio do comando `sudo`.

O campo senha é dividido em 3 subcampos, sendo cada um desses separados pelo caractere delimitador `$`, sendo `$id$salt$hash`:
- **id** - indica qual foi o algoritmo usado. Exemplos:  1 - MD5, 2y - Blowfish, 5 - SHA-256, 6 - SHA-512, etc.
- **salt** - uma *string* aleatória que deve ser combinada com a senha propriamente dita e assim, servir de entrada para o algoritmo que gera *hash* criptográfico. Dessa forma, se dois usuários escolherem uma mesma senha, o *salt* será diferente para cada, e assim teremos dois *hash* diferentes. Ou seja, comparando os dois *hash* resultantes, não será possível inferir que tais usuários possuem a mesma senha.
- **hash** - o resultado do algoritmo de *hash* aplicado sobre o *salt* + senha.

Na listagem apresentada acima, foi usado o algoritmo SHA-512, padrão nas atuais distribuições Linux. Pode-se usar o aplicativo `mkpasswd` para gerar *hash* como esses da listagem acima com o seguinte comando: `mkpasswd -m SHA-512`. 

Pode-se ainda informar a senha e o *salt*. Exemplo: `mkpasswd aluno vsBsTKe9 -m SHA-512`.

## Quebrando senhas

A quebra de senhas pode ser feita por diferentes tipos de ataque, como:

- **ataque de dicionário** - arquivo com um conjunto de palavras é varrido linha por linha até verificar se existe alguma correspondência com a senha que se deseja quebrar (por exemplo, gerando o *hash* de cada palavra do dicionário).
- **ataque de força bruta** - ciente que não seria possível montar um dicionário com todas as possíveis senhas (e.g. fj31Mx%4xA pode ser algo não convencial para se ter em um dicionário), a busca exaustiva consiste em gerar e comparar todos os possíveis caracteres até achar uma correspondência.

### John The Ripper - password cracker

[John The Ripper](https://www.openwall.com/john) é um software livre voltado para quebra de senhas. É possível instalar no Linux com o seguinte comando `sudo apt install john`. O aplicativo possui três modos de operação:

- **single crack** - combina informações da conta do usuário (e.g. nome completo, home, etc.) como forma de descobrir mais rapidamente, caso o usuário tenha feito uso de informações triviais ao criar sua senha.
- **dicionário** - varre palavras em um arquivo e busca por correspondência.
- **incremental** - ataque de força bruta onde serão testadas todas as possíveis combinações de caracteres. Com o intuito de reduzir o espaço de busca, é interessante especificar parâmetros para delimitar a quantidade de caracteres, bem como o conjunto de caracteres que será testado. Essas configurações podem ser feitas no arquivo `/etc/john/john.conf` ou em `$HOME/.john/john.conf`. 


Na [documentação oficial](https://www.openwall.com/john/doc/EXAMPLES.shtml) são apresentados vários exemplos de como usar o aplicativo. Abaixo é listado um subconjunto desses. Supondo que a listagem das senhas que fora apresentada acima estivesse no arquivo `senhas.txt`, seria possível usar o `john` das seguintes formas:

- **Quebra em modo automático:** 
  - `john senhas.txt`
  - tentará primeiro o modo *single crack*, depois o dicionário e por fim o incremental.
- **Verificando as senhas quebradas:** 
  - `john --show senhas.txt` 
  - Ou ainda, listando o conteúdo do arquivo `$HOME/.john/john.pot`
- **Informando um arquivo como dicionário:**
  - `john --wordlist:dicionario.txt senhas.txt`
- **Modo incremental e limitando a procurar somente por dígitos:**
  - `john -i=digits senhas.txt`

#### Personalizando o john por meio de arquivo de configuração

É possível personalizar como o `john` irá operar por meio do arquivo de configuração em `/etc/john/john.conf` ou em `$HOME/.john/john.conf`. O trecho abaixo cria um conjunto de regras para o modo incremental. 
```
[Incremental:All5]
File = $JOHN/ascii.chr
MinLen = 0
MaxLen = 5
CharCount = 95

[Incremental:All6]
File = $JOHN/ascii.chr
MinLen = 6
MaxLen = 6
CharCount = 95
 
[Incremental:All7]
File = $JOHN/ascii.chr
MinLen = 7
MaxLen = 7
CharCount = 95
 
[Incremental:All8]
File = $JOHN/ascii.chr
MinLen = 8
MaxLen = 8
CharCount = 95
```
Quando usadas as regras do conjunto `Incremental:All5`, então o `john` gerará senhas com no máximo 5 caracteres. Exemplo: `a`, `a0`, `aaa`, `Zzz10`, `12345`, etc. 

```
john -i=All5 senhas.txt
```

Quando usadas as regras do conjunto `Incremental:All6`, então o `john` gerará senhas com no mínimo 6 e como máximo 6 caracteres. Exemplo: `123456`, `abcdef`, `123abc`, `senha1`, etc. 
```
john -i=All6 senhas.txt
```



## Solução a ser desenvolvida

Desenvolva uma solução para permitir que processos, executados em máquinas diferentes, possam cooperar para, por exemplo, quebrar senhas. 

Na solução a ser desenvolvida deve existir um único processo mestre esse será o responsável por distribuir tarefas para os processos trabalhadores. Os processos trabalhadores deverão notificar o mestre assim que concluírem uma tarefa. 

O processo mestre poderá enviar comandos aos trabalhadores para que encerrem uma tarefa que esteja em andamento, ciente que isso resultará em perda de trabalho. 

O processo mestre só poderá enviar tarefas para processos trabalhadores que estiverem no estado *em espera*. 

O processo mestre poderá enviar arquivos para os processos trabalhadores. Por exemplo, para enviar um arquivo com um dicionário de palavras ou mesmo o arquivo que contém os hash das senhas.

Sendo assim, a solução deverá prover uma interface com o usuário que permita a esse:
- Saber quantos processos trabalhadores estão online e a situação de cada um desses, podendo ser, trabalhando ou em espera.
- Enviar tarefas ou arquivos para os processos trabalhadores.
- Solicitar o encerramento de tarefas para um processo trabalhador específico ou para todos os processos trabalhadores que estão no estado *trabalhando*.

### Entregas

- A solução deverá ser desenvolvida em Java 8 ou em Python3 e o cenário de testes a ser montado deverá ser exclusivamente sobre o sistema operacional Linux.
  - Todos os softwares que forem utilizados deverão ser software livre e preferencialmente, que possam ser instalados por meio de gerenciados de pacotes como o [`apt`](https://en.wikipedia.org/wiki/APT_(software)) ou [`snappy`](https://en.wikipedia.org/wiki/Snappy_(package_manager)).
  - A solução poderá ser desenvolvida usando sockets, RPC, RMI e fila de mensagens.
  - É bem possível que precise fazer uso de *threads* e arquivos.
- Documentação em [Markdown](https://guides.github.com/features/mastering-markdown/) necessária para a execução de um cenário com 1 processo mestre e pelo menos 3 processos trabalhadores. Deve-se aqui indicar o funcionamento da aplicação de computação distribuída que será usada como exemplo. Se desejar, use a aplicação para quebra de senha, descrita na seção anterior.
  - Deixe no arquivo `Cenario.md` na raiz do repositório.
- Relatório técnico, indicando os motivos das escolhas tecnológicas e detalhando o mecanismo escolhido para que os processos se encontrem (e.g. como os processos trabalhadores farão para encontrar o processo mestre ou o inverso).



## Material de apoio

- Classe `ProcessBuilder` em Java 
  - https://www.mkyong.com/java/how-to-execute-shell-command-from-java/
  - https://docs.oracle.com/javase/8/docs/api/java/lang/ProcessBuilder.html
  - http://docente.ifsc.edu.br/mello/std/process-and-nio2.pdf
- Módulo `Subprocess` em Python3
  - https://www.pythonforbeginners.com/os/subprocess-for-system-administrators
  - https://docs.python.org/3.5/library/subprocess.html
- Dicionário de senhas 
  - https://github.com/berzerk0/Probable-Wordlists
  - http://docente.ifsc.edu.br/mello/std/Top304Thousand-probable-v2.txt
- Parallel and distributed processing with John the Ripper
  - https://openwall.info/wiki/john/parallelization
- Pequenos exemplos em Python3 sobre conceitos de orientação a objetos que foram trabalhados em Java na disciplina POO29004
  - https://github.com/std29006/oo-java-e-python
- Threads em Python3 com o módulo `threading`
  - https://www.tutorialspoint.com/python3/python_multithreading.htm
