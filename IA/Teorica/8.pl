prefixo([],_).
prefixo([H1|T1],[H2|T2]):-
    H1=H2,
    prefixo(T1,T2).

sufixo(L,L).
sufixo(L1,[_|L2]):-
    sufixo(L1,L2).

pares([], []).
pares([H|T], S):-
    pares(T, Aux),
    ((H mod 2) =:= 0 ->
        S = [H|Aux];
        S = Aux
    ).

contem([l]).
contem([H|T]):-
    (H=1,!);
    contem(T).

traduz([],[]).
traduz([H|T],Lista):-
    traducao(H,Aux),
    traduz(T,ListaAux),
    Lista=[Aux|ListaAux].
traducao(one,um).
traducao(two,dois).
traducao(three,tres).
traducao(four,quatro).
traducao(five,cinco).
traducao(six,seis).
traducao(seven,sete).
traducao(eight,oito).
traducao(nine,nove).