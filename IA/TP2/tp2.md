# 1.i
Ir pelos caminhos que tem menor heuristica

S.Vitor -> 2
Este S.Mamede -> 7
Caminho:Gualtar->S.Vitor

Como a partir de S.Vitor só tem 1 caminho possivel para seguir.
Caminho:Gualtar->S.Vitor->S.Vicente->Nogueiro

# 1.ii
O A* pode voltar atras temos de ter em consideracao do o percurso. f(n)=g(n)+h(n) em que g(n) é o custo total ate chegar aquele ponto e h(n) é a heuristica.

Gualtar -> 0+8=8
Caminho: Gualtar

S.Vitor -> 8+2=10
Este S.Mamede -> 6+7=13
Caminho:Gualtar->S.Vitor

S.Vicente -> 6+6=12
Caminho: Gualtar-> S.Vitor -> S.Vicente

Nogueiro -> 8+0
Caminho: Gualtar->S.Vitor->S.Vicente->Nogueiro