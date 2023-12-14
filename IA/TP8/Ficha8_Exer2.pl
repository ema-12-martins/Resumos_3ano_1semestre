biblioteca(1, uminhogeral, braga).
biblioteca(2, luciocracveiro, braga).
biblioteca(3, municipal, porto).
biblioteca(4, publica, viana).
biblioteca(5, ajuda, lisboa).
biblioteca(6, cidade, coimbra).

livros(1, gameofthrones, 1). 
livros(2, codigodavinci, 2).
livros(3, setimoselo, 1).
livros(4, fireblood, 4).
livros(5, harrypotter, 6).
livros(6, senhoradosneis, 7).
livros(7, oalgoritmomestre, 9).

leitores(1, pedro, m).
leitores(2, joao, m).
leitores(3, lucia, f).
leitores(4, sofia, f).
leitores(5, patricia, f).
leitores(6, diana, f).

requisicoes(1,2,3,data(2022,5,17)).
requisicoes(2,1,2,data(2022,7,10)).
requisicoes(3,1,3,data(2021,11,2)).
requisicoes(4,1,4,data(2022,2,1)).
requisicoes(5,5,3,data(2022,4,23)).
requisicoes(6,4,2,data(2021,3,9)).
requisicoes(7,4,1,data(2022,5,5)).
requisicoes(8,2,6,data(2021,7,18)).
requisicoes(9,5,7,data(2022,4,12)).

devolucoes(2, data(2022, 7,26)).
devolucoes(4, data(2022,2,4)).
devolucoes(5, data(2022, 6, 13)).
devolucoes(1, data(2022, 5, 23)).
devolucoes(6, data(2022, 4, 9)).


%biblioteca(id, nome, localidade)
%devolucoes(id_requisicao, data(A,M, D))
%requisicoes(id_requisicao,id_leitor, id_livro, data(A,M,D))
%leitores(id, nome, genero)
%livros( id, nome, biblioteca)



%......................1.............................
leitores_feminino(Num):-
	findall(CodLeitor,leitores(CodLeitor,_,f),LeitorasFemininas),
	length(LeitorasFemininas,Num).
%.........................2.............................
requisitados_sem_biblio(Livros) :-
    findall(IdLivro,(requisicoes(_, IdLivro, _,_), livros(IdLivro, _, Biblio), not(biblioteca(Biblio,_,_))),Livros).

%.........................3..............................
requisicoes_braga(Lista) :-
    findall((Livro, Leitor),
            (requisicoes(_, IdLeitor, IdLivro, _),
             livros(IdLivro, Livro, IdBiblioteca),
             biblioteca(IdBiblioteca, _, braga),
             leitores(IdLeitor, Leitor, _)),
            Lista).
%............................4........................
livros_nao_requisitados(Lista) :-
    findall(Nome,
        (livros(Id, Nome, _),
        \+ requisicoes(_, _, Id, _)),
        Lista).
%...........................5.............................
livros_2022(Lista):-
	findall((NomeLivro,data(2022,M,D)),
	(requisicoes(_,_,IdLivro,data(2022,M,D)),
	livros(IdLivro,NomeLivro,_)
	),
	Lista).
%.........................6.........................
leitores_requisitaram_verao(Lista):-
	findall(NomeLeitor,
	(requisicoes(_,IdLeitor,_,data(_,M,_)),
	leitores(IdLeitor,NomeLeitor,_),
	M>6,
	M<10
	),
	Lista).
%.......................7...............................
mais_de_15_dias(A1,M1,D1,A2,M2,D2,true):-
	Dias1 is A1*365+M1*31+D1,
	Dias2 is A2*365+M2*31+D2,
	Res is Dias2-Dias1,
	Res>15.
mais_de_15_dias(_,_,_,_,_,_,false).

depois_data_limite(Lista):-
	setof(IdLeitor,
	(requisicoes(IdRequisicao,IdLeitor,_,data(A1,M1,D1)),
	devolucoes(IdRequisicao, data(A2,M2,D2)),
	mais_de_15_dias(A1,M1,D1,A2,M2,D2,false)
	),
	Lista).

nomes([],Lista,Lista).
nomes([H|T],Lista,Return):-
	leitores(H,Nome,_),
	append([Nome],Lista,Aux),
	nomes(T,Aux,Return).

depois_data_limite_nomes(Return):-
	depois_data_limite(Lista),
	nomes(Lista,[],Return).
	
	
	








