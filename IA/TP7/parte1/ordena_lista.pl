% Caso base: uma lista vazia já está ordenada
ordena([], []).

% Insere um elemento ordenadamente em uma lista
insere_ordenado(X, [], [X]).
insere_ordenado(X, [H|T], [X,H|T]) :- X =< H.
insere_ordenado(X, [H|T], [H|SortedTail]) :- X > H, insere_ordenado(X, T, SortedTail).

% Regra recursiva para ordenar a lista
ordena([H|T], Sorted) :-
    ordena(T, SortedTail),
    insere_ordenado(H, SortedTail, Sorted).


	
