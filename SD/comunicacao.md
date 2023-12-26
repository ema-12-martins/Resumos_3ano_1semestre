# Comunicacao
## Networking 101: OSI model
camada de aplicacao: As aplicacoes usam as redes. Camada de **aplicacao**. Camada **middleware**.

camada de apresentacao: Preocupada com o formato dos dados que sao trocados. Arquiteturas diferentes leeem e produzem diferentes tipos de dados. (big endian e little endian ou mesmo UTF8 e ASCII ). Camada **middleware**.

camada de sessao: Preocupa com o dialogo entre os intervenientes que utilizam as redes. Como estabelecer 1 ou mais canais de ligacao para resolver uma aplicacao. Queremos reconstuir ligacoes que vivem mais do que ao nivel de transporte. Camada **middleware**.

camada de transporte: Criamos a abstracao de canais de comunicacao fim a fim. Camada **operating sistems**. Camada **middleware**.

camada  rede: Como diferentes intervenientes na comunicacao falam. Encaminhamento e possibilidade de ter rotas alternativas para o mesmo destino. Camada **operating sistems**.

camada ligacao: As redes transmitem bits de informcacao. Corrigir erros da camada fisica, introduzindo redundancias. Camada **fisica**. Camada **operating sistems**.

camada fisica: Descricao de sinais fisicos eletricos ou otico e equipamentos para os transmitir. Camada **fisica**.

## TCP/IP
Uma ligacao TPC é um canal bidirecional fiavel e que garante a ordem dos dados enviados. Quando se perde uma ligacao por falha de um dos participantes ou por explicitacao, temos a certeza que em cada um dos sentidos os dados recebidos sao um prefixo dos dados que foram enviados. 

Uma ligacao TCP é identificada por um local e um remote IP adress e um local e um remote port.

Os enderecos e portas podem ser diferentes para diferentes participantes pois pode haver traducao dos mesmos Network Address Translation (NAT).

Em cada um dos participantes, existe um buffer de emissao e um buffer de rececao. Os dados enviados sao armazenados no destino. É enviada uma resposta ao emissor de que os dados foram transmitidos.

Se se tenta ler dados mas n ha nada no buffer de leitura, ele fica a espera de receber dados.

Se tentarmos escrever dados mas o buffer do recetor fica cheio, entao comeca a encher o buffer do emissor. Quando nao houver mais espaco, a operacao de envio ira bloquear.

## Sockets
3 partes:
1. Interface para estabelecer uma nova ligacao
2. Interface para enviar e receber dados
3. Interface para terminar uma ligacao existente.

Como é que cada participante descobre o endereço do outro participante. O servidor tem um endereco que é bem conhecido pelos clientes. O cliente cujo endereco nao tem de ser previamente conhecido pelo servidor. Este vai sendo descoberto pelo estabelecimento da ligacao.

****

Server:
Socket->Bind->Listen->Accept->Receive->Send->...->Close

**Socket**: Cria o socket

**Bind**: é a atribuicao do nome. Escolha de uma porta que identifica localmente o socket.

**Listen**: Poe á escuta para responder a clientes.

**Accept**: Bloqueia a Thread que o invoca até um cliente se ter ligado.

Estas 3 operacoes sao feitas com o construtor em Java em simultaneo.
~~~
new ServerSocket(serverAdress);
~~~

****
Cliente:Socket->Connect->Send->Receive...->Close

**Connect**: Para estabelecer a ligacao com o servidor. Este passo é sincrono pois ambos tem de estar disponiveis para estabelecer a ligacao.

Em Java, a criacao do socket e o connection da-se todo junto com o seguinte codigo:

~~~
new Socket(serverAddress)
~~~
 ****
 A primeira linha escreve bytes enquanto que a segunda escreve caracteres. A terceira é para escrever e a quarta é para garantir que foi escrito.
 ~~~
 var f = new FileOutputStream("f.txt");


var w = new BufferedWriter(new OutputStreamWriter(f));

w.write("primeira linha\n")

w.flush();
~~~

Para escrever
~~~
var f = new FileInputStream("f.txt");

var r = new BufferedReader (new InputStreamReader(f));

r.readLine();

r.close();
~~~
****
Para receber linahs do socket e escrever para ele.
~~~
BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
rw.write();
rw.flush();

BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
br.readLine();
~~~

Para garantir que os dados sao enviados, usar o flush pois caso contrario, apenas estao escritos no buffer.
o readLine le linhas ate ao \n e se nao houvr linhas, desolver null.

****
Fechar um reader ou um writer fechar o socket.

O socket fechar totalmente com qualquer um dos seguintes comandos:
~~~
sock.close();
sock.getInputStream().close();
sock.getOutputStream().close();
~~~
Podem ser fechar apenas o socket num dos sentidos. Assim deixa o socket unidirecional. Usam-se entao os seguintes comandos para esse efeito.
~~~
sock.shutdownOutput();
sock.shutdownInput();
~~~