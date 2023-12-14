
aluno(1,joao,m).
aluno(2,antonio,m).
aluno(3,carlos,m).
aluno(4,luisa,f).
aluno(5,maria,f).
aluno(6,isabel,f).

curso(1,lei).
curso(2,miei).
curso(3,lcc).

disciplina(1,ed,2,1).
disciplina(2,ia,3,1).
disciplina(3,fp,1,2).

inscrito(1,1).
inscrito(1,2).
inscrito(5,3).
inscrito(5,5).
inscrito(2,5).

nota(1,1,15).
nota(1,2,16).
nota(1,5,20).
nota(2,5,10).
nota(3,5,8).

copia(1,2).
copia(2,3).
copia(3,4).



%inscrito(aluno,disciplina)
%nota(aluno,disciplina,nota)
%disciplina(cod,sigla,ano,curso)
%curso(cod,nome)
%aluno(cod,nome,sex)
%copia(n1,n2)

inscritos_nas_disciplinas(Num):-
    findall(Aluno, inscrito(Aluno, _), ListaAlunos),
    length(ListaAlunos, Num).
    
%...........................................................
alunos_inscritos(ListaAlunos) :-
    findall(Nome, aluno(_,Nome,_), ListaAlunos).
    
 %..............................................................
media_aluno(Num, Media) :-
    findall(Nota, (nota(Num, Disciplina, Nota), inscrito(Num, Disciplina)), Notas),
    length(Notas, Tamanho),
    Tamanho > 0,
    sum_list(Notas, Soma),
    Media is Soma / Tamanho.
 %.........................................TEM ERROOOOOO.............................

numeros_todos_alunos(ListaNumeros) :-
    findall(Numero, aluno(Numero, _, _), ListaNumeros).

soma_medias([], Soma, Soma).
soma_medias([H|T], Soma, Total) :-
    media_aluno(H, Media),
    Soma_aux is Soma + Media,
    soma_medias(T, Soma_aux, Total).

media_escola(Media) :-
    numeros_todos_alunos(ListaNumeros),
    soma_medias(ListaNumeros, 0, Soma),
    length(ListaNumeros, Tam),
    Tam > 0,
    Media is Soma / Tam.

alunos_acima([], _, []).
alunos_acima([H|T], Media, List) :-
    alunos_acima(T, Media, Aux),
    media_aluno(H, MediaA),
    MediaA > Media,
    aluno(H, Nome, _),
    append(Aux, [Nome], List).

acima_media(List) :-
    numeros_todos_alunos(ListaNumeros),
    media_escola(Media),
    alunos_acima(ListaNumeros, Media, List).

%..............................................
nomes([],Lista,Lista).
nomes([H:T],Lista,Res):-
	aluno(H,Nome,_),
	append(Lista,[H],Aux),
	nomes(T,Aux,Res).

copiam(Lista):-
	findall(Nome, (aluno(Numero, _, _),copia(Numero,_)), ListaNumeros).
	
%.......................................................


	
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
