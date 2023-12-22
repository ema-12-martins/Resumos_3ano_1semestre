# O que podem fazer os maus
**Espionagem**: Intersecao indevida de mensagens.

**Insercao**: Inserir mensagens numa comunicacao.

**Disfarce**: Pode fingir (spoof) endereços de origem nos pacotes(ou outro campo do pacote).

**Desviar sessoes (hijacking)**: Tomar conta de conexoes que estao a decorrer, remover o emissor ou o recetor, colocando-se no lugar deles.

**Negacao de servico**: Impedir premeditadamente que um serviço seja usado por outros(Ex. sobrecarregando-o de algum modo).

# Propriedades de uma comunicacao segura
**Confidencialidade**: Só o emissor e o recetor indicado devem perceber o conteudo das mensagens.

**Autenticacao**: Emissor e recetor pretendem confirmar a identidade um ao outro.

**Integridade das mensagens**: Emissot e recetor querem garantir que a mensagem nao foi alterada (no percurso pela rede, antes do envio ou depois da rececao) sem que tal possa ser imediatamente detetado.

**Nao Repudio**: Evidencias que impeçam intervenientes de negar a comunicacao.

**Acesso e Disponibilidade**: Serviços devem estar acessiveis e com disponibilidade para os seus utilizadores.

# Tipos de criptografia
**Criptografia de chave simetrica**: emissor e recetor usam a mesma chave

**Criptografia de chave publica**: Uma chave cifra (publica) e outra decifra (privada).

**Cifra de substituicao**: Substituir uma coisa por outra.

# Criptografia de chave simétrica
Ataques:
1. **Baseado no texto cifrado**: Trudy tem textos cifrados que pode analisar. Pode ser por forca bruta (procura em todas as chaves possiveis) ou por analise estatistica.
2. **Baseado num texto conhecido**: Trudy conhece o texto original e o cifrado.
3. **Baseado num texto previamente escolhido**: Trudy consegue obter uma versao cifrada de um texto escolhido por ela.

Na chave simetrica, a Alice e o Bob conhecem a mesma chave (Ks). Usam Ks para cifrar e Ks para decodificar.

Algoritmos de codificacao:
1. **DES**: Chave 56 bits que processa blocos de 64 bits.
2. **3-DES**: Usa 3 chaves DES sequencialmente.
3. **AES**: Processa blocos de 128


No **ECB** o mesmo padrao de entrada da o mesmo de saida. No **CBC** antes de cifrar, mistura XOR o bloco de texto de entrada com o bloco anteriormente cifrado.

Este tipo de criptografia exige que o recetor e o emissor conhecam a mesma chave secreta.

# Criptografia de chave publica
Cifra com a chave publica e so quem tem a chave privada correspondente é que pode decifrar. Usar priemiro a chave publica e depois a privada ou vice versa tem de dar o mesmo resultado.

**Autenticacao**: Primeiro a Alice diz que é ela ao Bob. Depois o Bob manda uma mensagem que a Alice responde cifrando com a sua chave privada. Depois o Bob pede a chave publica e ela responde devolvendo a chave publica.

# Infraestruturas de chaves publicas (PKI)

**Chaves simetricas**: Temos que as 2 entidades ter o segredo pelo que se usam **centros de distribuicao** de chaves que sejam de confianca e que atuam como entidades intermediarias.

**Chaves Publicas**: A **Autoridade de Certificacao (CA)** de confianca para que sao mesmo eles a falar.

# Autoridade de Certificacao (CA)
Associa a chave publcia a uma determinda entidade.

Quando a Alice que saber o certificado do Bob, primeiro obtem o certificado do Bob(este pode ser dado por ele mesmo ou por outros sitios). Depois aplica a chave publica da CA ao certificado para verificar a validade do certificado e extrai de la a chave publica do Bob.

# Assinatura Digital
Quando a Alice recebe uma menssagem quer garantir que esta veio do Bob, que esta nao veio alterada e que o Bob nao pode negar que enviou a mensagem **nao repudio**. Por outras palavras, a Alice consegue provar a qualquer que foi o Bob qua enviou e que nao poderia ter sido mais ninguem a manda-la.

**A assinatura digital garante a integridade, autenticaçao do originador e o nao repudio do originador**

Existem varios tipos de assinaturas digitais:
1. **Usando apenas criptografia de chave publica**: Bob "assina" a mensagem cifrando-a com a sua chave privada. Garante a autenticacao do originador, nao repudio do originador e a integridade. Nao é muito usado por questoes de desempenho.
2. **Criptografia de chave publica e uso de funcao Hash**: Primeiro a menssagem é cifrada com a Hash e so depois é que é passada a chave primaria. Depois junta-se a mensagem com o obtido no passo anterior e damos xor. Do outro lado, ao receber, como temos a mensagem inteira podemos calcular o Hash e decifrando o resto da mensagem, vemos se temos os dois sumarios iguais A funcao hash dada uma mensagem grande, da um suamrio de tamanho fixo.
Algoritmos de Hash usados sao o MD5, SHA-1, SHA-2 e o SHA-3.

A assinatura digital garante entao que so o Bob pode ter assinado pois so ele conhece a sua chave privada.

# Integridade e Autenticacao
**MAC**:Envia o sumario da mensagem e do segredo juntos. O processo é similar ao anterior mas nao se cifra com chaves privadas. Nao garante o nao repudio pois o segredo é partilhado entre a Alice e o Bob. Como ambos conhecem o Hash e qualquer um deles pode produzir o Hash, nao sendo uma assinatura digital.

# Seguranca SSL/TLS
TLS (Transport Layer Security) é um protocolo de nivel 4. Garante a seguranca ao nivel de transporte para qualquer aplicacao TCP. Da servicos de seguranca como a autenticacao so servidor e, opcionalmente, do cliente e a confidencialidade dos dados.

Para **autenticar um servidor**... O cliente conhece as chaves publicas de autoridades de certificacao da sua confianca (CA). Obtem o certificado do servidor emitido por uma CA sua conhecida. Extrai a chave publica do certificado depois de verificada validade.

Relativamente á **confidencialidade** (cifragem dos dados da sessao)... O cliente gera chave de sessao, cifra-a com a chave publica do servidor e envia-a para o servidor. Servidor decifra a chave de sessao usando a sua chave primaria. Ambos - cliente e servidor- na posse da chave de sessao, podem cifrar todos os dados trocados.

Relativamente á **autenticacao do cliente**, esta pode ser feita com base em certificados do cliente

# Como funciona TLS
**Handshake inicial**:
1. Estabelece coneccao TCP (SYN).
2. Autentica Alice usando o certificado assinado por uma CA.
3. Cria chave mestra, encripta-a(usando a chave publica da Alice), e envia-a á Alice.

**Calculo das chaves**:
A Alice e o Bob criam 4 chaves a partir da chave mestra. Chave cifragem do Bob->Alice, chave de cifragem Alice->Bob, chave MAC Alice->Bob e chave MAC Bob->Alice.

Exemplo: Se o bob quiser mandar uma mensagem, entao primeiro usa a chave MAC para criar o sumario que junta á mensagem. Depois usa a chave de cifragem sobre a soma anterior. Depois, o TLS junta umcabecalho.

# Detalhadamente
1. Cliente envia “nonce” e lista de algoritmos criptográficos que suporta
2. Servidor escolhe da lista um algoritmo simétrico (ex: AES), um algoritmo de chave
pública (ex: RSA), um algoritmo MAC (ex: HMAC_SHA256, HMAC com SHA-256),
e envia ao cliente as suas escolhas, com o seu “nonce” e o seu certificado de
chave pública
3. Cliente verifica o certificado, extrai chave pública do servidor, gera um PMS (Pré-
Master Secret), cifra PMS com chev pública do servidor, e envia ao servidor
4. [paralelo] Cliente e servidor calcula de forma independente o MS (Master Secret)
a partir do PMS e dos “nonces”. MS é fatiado para gerar 4 chaves. Se for usado
um algoritmo CBC, derivam-se também dois vetores de inicialização (um em cada
sentido). Mensagens entre cliente e servidor são confidenciais e autenticadas
5. Cliente envia um MAC de todas as mensagens de Handshake (*)
6. Servidor envia um MAC de todas as mensagens de Handshake (*)

# TLS 1.3
Autenticacao: Usando criptografia assimetrica ou por criptografia simetrica com chave pre-partilhada(PSK).

Confidencialidade: Os dados enviados no canal so sao visiveis nas extremidades. TLS nao esconde tamanhos.

Integridade: Os dados nao podem ser modificados no canal sem que seje isso seja verificado nas extremidades.



