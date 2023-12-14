se_existe([], _, false).
se_existe([H|_], X, Resultado) :-
    H =:= X,
    Resultado = true.
se_existe([_|T], X, Resultado) :-
    se_existe(T, X, Resultado).


