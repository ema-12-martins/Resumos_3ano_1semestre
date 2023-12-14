se_existe([], _, false).
se_existe([X|_], X, true).
se_existe([_|T], X, Resultado) :-
    se_existe(T, X, Resultado).

elem_difs_acumulado([], _, 0).
elem_difs_acumulado([H|T], Lista, Res) :-
    se_existe(Lista, H, Existe),
    Existe = false,
    elem_difs_acumulado(T, [H|Lista], ResAtual),
    Res is ResAtual + 1.
elem_difs_acumulado([_|T], Lista, Res) :-
    elem_difs_acumulado(T, Lista, Res).

elem_dif(Lista, Difs) :-
    elem_difs_acumulado(Lista, [], Difs).

