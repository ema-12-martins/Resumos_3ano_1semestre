apaga1_aux(_, [], Lista, Lista).
apaga1_aux(X, [X | T], Lista, Res) :-
    append(Lista, T, Res).  % Remove a primeira ocorrência de X da lista e unifica o resultado em Res
apaga1_aux(X, [H | T], Lista, Res) :-
    append(Lista, [H], ResAux),  % Adiciona o elemento H à lista temporária
    apaga1_aux(X, T, ResAux, Res).  % Continua verificando o restante da lista

apaga1(X, Lista, Res) :-
    apaga1_aux(X, Lista, [], Res).  % Chama o predicado auxiliar para remover a primeira ocorrência de X

