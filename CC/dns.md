# DNS Domain Name System
Na internet temos host e routers. Estes sao enderecados com enderecos IP (32 ou 128 bit). Estes tambem tem nomes. Para mapear os enderecos IP nos nomes temos o DNS.

O DNS é uma base de dados distribuida implementada numa **hierarquia de servidores de nomes**. É um protocolo da camada de aplicacao.

# Servicos DNS
1. Traducao de nomes dos hosts para enderecos IP.
2. Aliases dos hosts(nomes alternativos).
3. Definicao do servidor mail
4. Distribuicao de carga: Servdores Web replicados, ou seja, temos um conjunto de endereços IP associados a um unico nome.

# Porque nao centralizar o DNS
Se centralizasse-mos o DNS, teriamos um ponto de falha unico, teriamos um maior volume de trafego, teriamos uma base de dados centralizada distante e levaria a uma dificil manutencao.

# Base de Dados distribuida e hierarquica
![Hierarquia DNS](https://www.researchgate.net/publication/307715471/figure/fig2/AS:406051263401987@1473821573735/Figura-2-Hierarquia-dos-servidores-raizes-do-DNS.png)

Imaginemos que um cliente quer o IP do endereco www.amazaon.com:
1. Cliente interrogra um root server para descobrir servidores DNS para o dominio de topo com.
2. Cliente interroga servidor DNS de com para obter o servidor DNS de amazon.com.
3. Cliente interroga servidor DNS de amazon.com para obter o endereco IP de www.amazon.com.
4. Cliente guarda toda a informacao obtida nesta interacao em cache (servidores DNS, enderecos IP...).

# DNS: Root servers
Sao contactados pelos servidores de nomes locais que nao conseguem resolver um nome.

O root server pode contactar servidor DNS autoritativo se o mapeamento do nome nao é conhecido. Assim, obtem o mapeamento (nome, servidor DNS) e retorna esse mapeamento ao servidor de nomes local.

# Dominios de topo (TLD-Top level domain servers)
Responsaveis por .com, .org, .net, .edu ... e por todos os dominios de topo de paises como .pt, .uk ...

# Servidores DNS autoritativos
Servidores DNS das organizacoes com autoridade sobre dominios de nomes local e sobre mapeamentos nome/endereco. Determinmam varios parametros temporais (como TTL em segundos).

Podem ser geridos pela propria organizacao ou pelo seu ISP.
Podem conter a base de dados original ou copias oficiais dessa base de dados. 

# Servidores de Nomes Local
Pode pertencer á hierarquia. Casa ISP tem um ., tambem designado por default name server.

**Quando um host formula uma interrogacao DNS ela deve ser sempre dirigida ao seu servidor DNS local.** Funciona como um proxy, redirecionando a query para a hierarquia quando necessario, **forwarder**. Faz caching. Pode ainda acumular funcoes de servidor autoritativo.

# Como procurar
**Modo iterativo**: Temos 2 hipoteses, ou o servidor contactado responde com o nome do servidor a contactar ou diz que nao conhece e da o nome a quem perguntar.

**Modo recursivo**: Coloca o fardo na resolucao no servidor de nomes contactado. Pois tem de descobrir quem é.

# Modo de operacao do DNS
Todas as aplicacoes consultam DNS. 

O DNS funciona sobre **UDP**. Existem multiplos servidores por cada dominio, tendo um servidor **primario** e um ou mais **secundarios**. Os secundarios mantem, de forma automarica, **replicas dos primarios**.

Os servidores e os clientes **armazenam as respostas obtidas durante um certo tempo (TTL)** para nao andarem sempre a perguntar a mesma coisa, **caching**. Os servidores de nomes TLD sao normalmente armazenados em cache nos servidores DNS locais. Existem mecanismos de atualizaxao dinamica e notificacao (update/notify) ja definidos pelo IEFT, como o **RFC 2136**.

# DNS resource records (RR):
o formato RR é (name,value,type,ttl). 

**Type=A**: O name é o nome de um host. O value é o endereco IP.

**Type=NS**: O name é um nome de um dominio. O value é o nome do host do servidor DNS autoritativo para o dominio.

**Type=CNAME**: O name é um alias para outro nome canonico. o value é o nome canonico.

**Type=MX**: O nome é o dominio e o value é o nome do servidor de email associado ao nome.

# Mensagens
O protocolo tem duas mensagens (query e o reply), exatamente com o mesmo formato. O formato é binario, nao texto. Nunca se repete 2x a mesma label. Usam-se apontadores sempre que possive.

****

O cabecalho da mensagem tem:

**identification**: 16bits.

**Flags**: Se é query ou reply, se é recursion desired, se é recursion available e se reply is authoritative.

****

O resto: 

**questions**: Nome e tipo de interrogacao

**answers**: RRS de resposta a interrogacao

**autority**: Records com os nomes de servidores autoritativos.

**addicional information**: Informacao adicional nao requerida, mas que pode ser muito util.

![Menssagem DNS](https://electronicspost.com/wp-content/uploads/2016/05/2.23.png)

# Seguranca: Ataques DNS
 **Bombardeamento servidores root com trafego**: Atualemente ja nao tem sucesso pois ha filtragem do trafego.

 **Bombardeamento servidores TLD**: Mais perigosos.

 **Ataques redirect**: man-in-middle (interpretar queries) e dns poisoning (enviar respostas falsas ao servidor DNS -> envenenar caches).

 **Ataque exploit**: Enviar queries com falso IP origem. 

# Comandos
Com o **nslookup** podemos ter acesso a varias informacoes.

**set type=A**:Obter endero de IP dado o nomes.

**set type=PTR**:Obter nome dado IP.

**set type=MX**: Obter servidores e-mail de um dominio

**set type=NS**: Obter servicos de DNS de um dominio.

# Mapeamento inverso 
Cada seção do endereço IP é mapeada para uma parte correspondente na zona de domínio reverso. Por exemplo, o endereço IP 192.0.2.1 seria mapeado para 1.2.0.192.in-addr.arpa na zona reversa para IPv4.
~~~
..arpa
|
└── in-addr.arpa
    |
    └── 0
        |
        └── 168
            |
            └── 192
                |
                └── PTR
                    |
                    └── 192.168.0.100

~~~
Nota que 100=PTR










