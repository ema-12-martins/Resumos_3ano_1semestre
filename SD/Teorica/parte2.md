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
Para receber linhas do socket e escrever para ele.
~~~
BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
rw.write();
rw.flush();

BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
br.readLine();
~~~

Para garantir que os dados sao enviados, usar o flush pois caso contrario, apenas estao escritos no buffer.
o readLine le linhas ate ao \n e se nao houver linhas, desolver null.

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

# Serealizacao
Converte estruturas de dados em arrays de bytes que podem ser transferidos atravez da rede. Quando chegam ao destino sao convertidos novamente nas estruturas de dados.

Leva á abstracao das estruturas. Pode ser usado em diferentes processadores e arquiteturas. Podem haver diferentes versoes da aplicacao e estas devem conseguir comunicar.

## Problemas na representacao de tipos basicos de dados
O primeiro está na representacao de números pois podermos ter diferentes endianess. O segundo esta na representacao de strings, como uso UTF-8 (caracteres normais 1 byte e acentuados 2 bytes) ou Latin1. 

Para resolver o problema ha 2 solucoes possiveis. A primeira é chegar a acordo do que sera passado pela rede. Ao mandar converte para a representacao da rede e quando recebe converte para a representacao local. Assim so tem que ter 1 codigo de conversao. Tem a desvantagem que 2 maquinas que estejam a comunicar igual mas diferente com a rede tem se estar a converter 2x desnecessariamente. A outra alternativa é enviar os dados com uma etiqueta a dizer qual a representacao dos dados. Assim o recetor sabe se tem de fazer conversao ou se ja estao a chegar no formato pretendido. É melhor no caso dos sistemas forem homogeneos, ter a mesma representacao. 

Na rede podemos mandar **texto**. Isto leva a incluir alguma redundancia para descodificar. No entanto é facilmente legivel por pessoas e portanto, facil de utilizar. Exp: HTTP e JSON.
A alternativa é a utilizacao de formatos **binarios** que sao mais compactos e mais eficientes. Em contrapartida sao mais dificeis de entender e mais frageis pois a alteraco de 1 byte pode invalidar toda a mensagem. Ex. Java Data*Stream. Ao escolher formatos binarios, temos de nos preocupar com o alinhamento dos bytes na memoria. 
~~~
_ _ _ _
_ _ 0 1
2 3 _ _
_ _ _ _
~~~
Cada palavra sao tipicamente 4 bytes. Neste caso temos um inteiro de 4 bytes. Neste caso o CPU tinha de ler os primeiros 4, os segundos 4 e depois escolher os 2 ultimos e ou 2 primeiros bytes do outro. Isto leva a que os acessos sejam muito mais lentos. Isto leva a que muitos formatos usem enchimento para que sejam alinhados á palavra.

A representacao pode ter uma descricao **implicita** ou **explicita** dos seus tipos. A explicita temos etiquetas que descrevem a natureza dos dados. Na implicita, a interpretacao dos dados depende extritamente do programa. **O explicito ocupa mais espaco mas sera mais facilmetne processado. Os implicitos sao mais eficientes mas temos de ter conhecimento do codigo para serem corretamente interpretados.**

## Representacao de tipos de dados compostos
Os dados nao estao contiguos em memoria. Temos de percorrer e enumerar cada um dos seus componentes. Os apontadores nao tem sentido quando passados para uma maquina diferente, bem como os locks.

Se mandarmos structs so temos de enumerar os elementos. Se for classes e objetos temos de identificar qual o objeto, atraves de uma etiqueta.

Se tivermos elementos opcionais, primeiro metemos um bool que diz se esta presente ou nao, seguido dos dados.

Nas colecoes, podemos primeiro dizer quantos elementos vamos mandar e em seguida mandamos os elementos. A outra opcao é mandar os elementos seguidos de um valor terminal que diz a que estrutura pertence.

Se tivermos grafos, ou seja, 2 apontadores para a mesma coisa, podemos copiar x vezes o componente. Porem, como no primeiro caso tinhamos apontadores, se alterassemos num sitio alterava no outro, coisa que nao acontece do lado das copias. A outra hipotese ent é usar um mapa auxiliar. Na serealiazacao temos um mapa de Objetos para inteiros. Assim, vemos se o objeto ja existe no mapa ou nao. - indica que o que vem é um novo objeto e #n indica a referencia a algo que ja esta no mapa. Despois escreve os valores dos campos. Para descodificar usa-se um mapa de inteiros para objetos. Isto é implementado no OuputStrem e InputStream.

## Codigo
~~~
public class Binary{
    int i;
    float f;
    String s;

    transient ReentrantLock l;#O codigo nao tenta transmiti-lo
}

public void write(DataOutputStream dos) throws IOException{
    dos.writeInt(i);
    dos.writeFloat(f);
    dos.writeUTF(s);
}

public void read(DataInputStream dis) throws IOException{
    i=dis.readInt();
    f=dis.readFloat();
    s=dis.readUTF();
}

#Binary i = new Binary();
~~~
Pode ser problematica para programas com estruturas maiores. Pode haver problemas na ordem das chamadas dos metodos read e write. Para resolver o problema, usar capacidades de reflexao das linguagens e nao manualmente.

~~~
class C{
    int i;
    float f;
}
~~~
Vamos usar a capacidade de reflexion. Se usarmos **var f= C.class.getDeclaredFiels()** retorna var = Field[2] {int C.i, float, C.f}. Se fizer f[0].getType retorna int e se fizer f[0].getName retorna i. Se fizer f[0].setInt(120) muda do campo para 120. Se fizer f[0].getInt, obtenho o valor do campo.
~~~
public class Reflect{
    public static JsonObject write(Object o) throws IOException, IllegalAccessException{
        JsonObjectBuilder b = Json.createObjectBuilder();
        for(Field f: o.getClass().getDeclaredFields()){
            if(f.getType().equals(Integer.TYPE)){
                b.add(f.getName(),f.getInt(o));
            }
            .......
        }
    }

}
~~~
O ObjectInputStream e ObjectOutputStream fazem refletion internamente.

A ultima opcao é a geracao automatica dos codigos das travessias feito por uma descricao abstrata do tipo de dados, como é exemplo o protobuf. Cria logo todo o codigo para gerar a conversao.
~~~
menssage C{
    required int32 i=1;
    required float f=2;
    optional string s=3;
}
~~~

### Program vs Data first
Program First: O conteudo das mensagens é inferido do programa existente. No priemiro caso pela sequencia das operacoes de leitura e de escrita e no segundo caso pelas proprias estruturas de dados. Bom para desenvolvimento mas restrito a uma unica linguagem

Data First: Faz uma descricao dos objetos independente da linguaguem primeiro e depois gerar os programas das travessias a cada linguaguem que queremos. Da independecia e documentacao do protocolo que é transmitido atraves da rede.

### Versioning
Como lidar com versoes diferentes da mesma estrutura de dados. Para resolver o problema de compatibilidades temos duas hipoteses. Deixar que as estruturas sejam modificadas com novas versoes do programa. Por exemplo, acrescentando items opcionais. É o que usa o protobuf. A outra versao é ter uma estrutura que verifica se as estruturas sao compativeis. Se nao forem, da origem a um erro previsivel e nao ha observacao de dados corrumpidos.
~~~
private static final long serialVersionUID=2L;
~~~

### Streaming vs Object model
Streaming: A conversao é feita a medida que é feita a travessia, por ordem que estao no programa escrito. Dificil de usar quando temos campos que podem aparecer em diferente ordem. Podemos escrever todos seguidos mais ao receber temos de ver a ordem. Pode ter valores de campos opcionais. É mais eficiente pois os dados sao copiados apenas uma vez e nao precisam de ser guardado integralmente em memoria.

Object model: Temos um modelo de dados carregado em memoria que pode ser consultado por uma ordem arbitraria. È criado um objeto intermedio com todos os objetos juntos. Assim podemos consultar pela ordem que queremos. Temos de copiar os dados 2x, um para o intermedio e outro para o final.

# Threads
Os processos agrupam os recursos que estao em uso como memoria e ficheiros abertos e as threads contem o contexto de execusao.

## Client-Servidor
A thread do cliente espera pelas respostas. A thread do servidor espera por pedidos do cliente.

## Single-Threaded
Caso mais simples um servidor tem apenas uma thread. Ele atende os pedidos de 1 cliente e quando acabar de atender esse cliente, pode atender os pedidos de outro cliente 
~~~
var sock = new ServerSocket(12345);
while(true){
    var sock Socket = socket.accept();
    #Executa cenas para esse cliente
    #Recebe pedidos e devolve Resposta
    #Ao terminal fecha o socket
}
~~~

## Thread-per-connection
Cada vez que um cliente estabelece uma sessao com o cliente num thread em separado. Assim temos pedidos de clientes diferentes a serem processados concorrentemente.
~~~
var sock = new ServerSocket(12345);
while(true){
    var sock Socket = socket.accept();
    new Thread(()->
    #codigo da thread
    ).start();
}
~~~

## Thread-per-request
Podemos fazer uma melhoria ao anterior, nao tendo apenas uma thread por cliente mas no servidor varias threads a tratar de pedidos do cliente. Temos de ter um lock ao escrever as respostas no cliente para ter a certeza que estas nao se misturam no socket. Como os pedidos podem ser resolvidos em qualquer ordem pelo que temos de ter um identificador. Tem o mal de estarmos constantemente a criar e a matar threads e temos ainda o risco de a thread nao poder ser feita.

## Thread Pool
Em vez de ter uma thread por pedido, temos um conjunto de threads que estou a espera para processar pedidos. Assim temos um maximo de threads em execussao em simultaneo. Quando sao livres, voltam a ficar desocupados. Tem a vantagem de reduzir o  custo pois reutiliza threas. Limita ainda o numero de pedidos a executar no mesmo servidor, nunca ficando em subcarga. Temos de fazer controlo de concorrencia a escrever as respostas.

## 1-to-n Notifications
Ao realizar o pedido e a resposta a ser lenta, pode bloquear a escrita de outros nas suas respetivas threads. A solucao é nao permitir que no mesmo socket estejam a escrever threads de diferentes clientes. Para isso associamos a cada sessao um segundo thread e temos uma fila de respostas do cliente. Cada resposta vai para um socket que é lido por um intermediario que manda para o buffer de cada um dos clientes. Quando se recebe uma resposta, acorda-se a thread que esta á espera de respostas.

****
## Multi-threaded
O cliente pode ter varias threads a mandar varios pedidos em simultaneo. Mas para isso temos de ter uma seccao critica para escrever para o socket. O receive tambem poderia nao estar a ser executado pela ordem certa. So da para fazer 1 pedido de cada vez

## Multi-threaded with Dispatcher
Os pedidos vao para um buffer que por sua vez escreve para o socket. Nas respostas, acontece a mesma coisa. 

## Callbacks
Podemos por uma condicao para perceber se o cliente/servidor esta a receber um pedido ou uma resposta. Nesse caso, ambos fazem pedidos e recebem respostas.

# Migracao de codigo
Threads: contexto de processo + stack

Processo: threads + contexto memoria + contexto SO

SO: Conjunto de recursos partilhados

Temos de ter a procupacao de saber onde é que uma thread esta a executar num determinado momento. A segunda é em que medida é que nao passa as froteiras entre diversos processos ou mesmo maquinas.

Num computador se queremos digitar um numero, por cada digitacao viamos se era um numero. Em sistemas distribuidos nao podemos fazer isso pois teriamos por cada digito de conectar o servidor. Outro exemplo é queries em bases de dados. No distribuido, estariamos a mandar os dados para o cliente processar.

Para evitar este problema usa-se a migracao de codigo. O cliente nao trabalhar diretamente sobre os dados. O cliente manda algum codigo para o servidor para que seja feita a querie, nao enviando os dados de novo para o cliente. No JS, o servidor manda codigo para o cliente para que cada validacao seja feita por codigo do servidor mas executado no cliente. Assim diminui a latencia e a quantidade de dados transmitida entre o cliente e o servidor.

Temos de ter a seguranca. Temos de ver o que o codigo enviado pode fazer, vendo se nao é malicioso. Alem disso temos de ver a eficiencia. Queremos que o codigo enviado seja quanto possivel codigo nativo de onde vai correr. 

## Virtualizacao
aplicacao->bibliotecas->SO->hardware

Utilizacao de sistemas que compilam varias linguagens para um **byte code** que é representacao intermedia do codigo. Resolve o problema de diferentes SO e bibliotecas, oferecendo interfaces comuns a todo o lado. Exemplo é o JS.

Outra alternativa é usar virtualizacao ao nivel do sistema e nao so na aplicacao. Aqui o SO junto com a aplicacao e com a bibliotecas é corrido debaixo de um sistema de virtualizacao que garante o controlo de acessos. Existem 2 versoes. Umas correm diretamento sobre o hardware, outras sobre o SO. Chama-se **hipervisor**, como maquinas virtuais. Para usar **sistemas de virtualizacao de SO** precisamos de **cloud computing** que combinam com virtualizacao.

## Cloud Computing
Capacidade de programar a propria estrutura, criando e configurando novos servidores é feita num contexto distribuido como resposta a clientes.
 
## Edge and Fog computing
Edge: Em vez de fornecer logo o conteudo, faz uma copia para servidores que vivem na periferia da rede (ISP e instituicoes). Assim, os clientes acedem á copia local desse recurso.

Fog: Edge + Cloud. Os computadores de edge sao geridos como uma cloud, ou seja, tem virtualizacao. Assim, atraves de programas podem dar inicio a servidores virtuais dentro destas estruturas locais. Assim, a aplicacao distribuida coloca diversos servidores na cloud bem como na periferia perto dos utilizadores finais.





