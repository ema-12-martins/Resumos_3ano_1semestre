quadrado :-
    write('Digite um numero (digite "stop" para parar): '),
    read(Input),
    nl,
    (Input \= stop ->
        (
            Numero is float(Input),
            Resultado is Numero * Numero,
            write('O quadrado é: '),
            write(Resultado),
            nl,
            quadrado
        );
        true
    ).

listar_valores(X):-
    X>0,
    Y is X+1,
    printarValores(0,Y).

printarValores(X,Y):-
    X>=Y,
    !.
printarValores(X,Y):-
    X<Y,
    write(X),
    nl,
    Z is X+1,
    printarValores(Z,Y).

elevado(_,0,Resultado):-
    Resultado is 1.
elevado(X,Y,Resultado):-
    Y>0,
    YAux is Y-1,
    elevado(X,YAux,ResAux),
    Resultado is X*ResAux.
