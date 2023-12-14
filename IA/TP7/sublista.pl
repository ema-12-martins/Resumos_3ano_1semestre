sublista([], _, true).
sublista(_, [], false).
sublista(L1, L2, Result) :-
    prefix(L1, L2, Res),
    Res = true,
    Result = Res.
sublista(L1, [_|T2], Result) :-
    sublista(L1, T2, Result).


prefix([], _, true). 
prefix([H1|T1], [H2|T2], Res) :-
    H1 =:= H2,
    prefix(T1, T2, Res).
prefix(_, _, false). 

