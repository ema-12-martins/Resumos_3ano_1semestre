existe(_, [], false).  
existe(X, [X|_], true).  
existe(X, [_|T], Res) :-
    existe(X, T, Res). 

insere(X, Lista, Res) :-
    \+ existe(X, Lista, true), 
    append([X], Lista, Res).
insere(X, Lista, Lista) :-
    existe(X, Lista, true).
 

