# Inteligencia Artificial
A inteligencia artificial pode ser dividida em dois tipos:
**Weak AI**-> Sistemas desenhados e treinados para resolver problemas especificos e limitados. Ex: Reconhecimento de voz, jogos, recomendações de produtos... Não tem consciencia do que realmente está a ser feito. Operam dentro de limites definidos e não tem capacidade de aprender nem adaptacao. 
**Strong AI**->Tenta igualar ou seperar a inteligencia humana nas suas variadas capacidades(raciocinio, compreensao, aprendizagem...). Tem a capacidade de pensar autonomamente, aprender continuamente com novas informaçoes e ter consciencia de si mesma e do ambiente ao seu redor.


<pre>
Dados->Informacao->Conhecimento->Sabedoria
</pre>
**Dados**->São factos/observaçeos, que por si só nao tem significado. Ex.10
**Informacao**->Refere-se aos dados organizados e contextualizados de modo a terem significado. Ex. 10 anos
**Conhecimento**-> Compreender e aplicar as informaçoes. Ex. Se tem 10 anos é uma criança
**Sabedoria**->Aplicaçao do conhecimento para tomar decisoes adequadas para diferentes situaçoes. Ex. Se é criança, nao pode ser este filme.

<pre>
Programacao: Input + Program = Output
Machine Learning: Input + Output = Model
</pre>

# IA Generativa
Maquina com a capacidade de perceber, aprender e fazer qualquer atividade humana. Pode fazer diversas coisas como, textos, audio e gerar dados. Ex.ChatGPt.

# Metodosdde resolucao de problemas de procura
**Agente**-> Algo que perceciona o ambiente através de sensores e atua no ambiente atravez de **atuadores**. Agente = Arquitetura + Programa.
Este formula um objetivo e um problema. Posteriormente procura uma sequencia de acoes que resolvam o problema, executando-as uma de cada vez. Este ciclo repete-se -> **problema de procura**

Metodo paar definir o problema:
-> Representacao do Estado
-> Estado inicial(atual)
-> Estado objetivos (estado ou estados que queremos alcancar)
-> Operadores(nome, pre-condicao, efeitos, custo)
-> Custo da solucao

A solucao ideial é a sequencia de acoes que fornece o menor custo de caminho para alcançar o objetivo.

# Tipos de problemas
**Estado Unico**-> Ambiente deterministico, totalmente observavel. O agente sabe onde estará. A solucao é uma sequencia.
**Multiplos Estados**-> Ambiente deterministico, nao acessivel. O agente nao sabe onde esta. A solucao é uma sequencia.
**Contigencia**-> Ambiente nao deterministico e/ou parcialmente acessivel. Por cada acao realizada, o agente recolhe informacoes atraves do seu sensor e so depois decidde a proxima acao a executar.
**Exploracao**-> Espaço de estados desconhecidos.

Ex: Problema de estado unico
Estado: 8 estados (posicao do robo e lixo (e quadrados))
Estado Inicial: Qualquer 1
Operadores: Esquerda, direita e aspirar
Teste Objetivo: Nao há lixo em nenhum dos quadrados
Custo da solucao: Cada acao custa 1 (custo total = numero de passos da solucao)

# Tipos de solucao
Apos executar a funcao de custo, o custo pode ser:
**Satisfatorio**-> É uma solução
**Semi-otima**->É a que tem proximidade ao menos custo de todas as solucoes
**Otima**->Tem o menor custo de entre todas as solucoes.