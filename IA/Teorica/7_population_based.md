# Algoritmos geneticos

Tratamento especifico de otimos locais. Utilizar para resolver problemas complexos ou que nao se sabem bem como resolver. Nao garantem resultados otimos.

Baseiam-se na codificacao de um conjunto de solucoes possiveis. util quando o espaco de solucoes é muito grande. Os resultados sao dados como uma populacao de solucoes e nao uma solucao unica. Nao precisam de conhecimento do problema, apenas de uma forma de avaliacao do resultado. Usam transicoes probabilisticas e nao regras deterministicas.


AG processo em que os estados sucessores sao gerados pela combinacao de dois esatdos pais em vez de modificar um unico estado.

Matriz:Populacao
Linha:Individuo
Linha:Varios cromossomas.
Cromossoma: composto por genes.
Genes: Alelo

Funcao de Avaliacao:Identifica os individuos mais aptos. Melhores sao escolhidos para gerar a geracao seguinte.

Funcao firness: Recebe a solucao e devolve o valor que represena o quao boa é uma solucao.

Quem tem mais aptidao gera mais descendentes.

# Selecao
Normalmente ordena-se os cromossomas por funcao fitness  e dao probabilidades de serem escolhidos proporcionais a sua funcao. Probabilidade do melhor e a maior.

# Reproducao
**Cruzamento**:
Crossover: partes dos genes dos pais sao combinados para gerar filhos.

**Mutacao**:Aplica-se com baixa probabilidade so para garantir que outras oportunidades sao exploradas. Muda um gene.

# Funcionamento
1. Gerar uma populacao aleatoria com n individuos.
2. Avaliar cada cromossoam com a funcao firness.
3. Criar nova populacao. Escolher 2 cromossomas. Qanto maior fitness mair porbabilidade de ser selecionado. Corssover. Mutacao e por o novo cromossoma na populacao.
4. Substituir a populacao com os novos cromossomas.
5. Testar se a condicao de paragem é satisfeita. Para e devolver a melhor solucao encontrada.
6. Ir para 2

# Modelos
**steady state**: Gera n que substitui os n antigos. N menor que o numero de individuos da populacao.
**geracional**: N e n tamanho da populacao e toda é trocada.




