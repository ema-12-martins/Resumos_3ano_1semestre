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

Quando a Alice que saber o certificado do Bob, primeiro obtem o certificado do Bob. Depois aplica a chave publica da CA ao certificado para verificar a validade do certificado e extrai de la a chave publica do Bob.





