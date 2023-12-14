alunoInscrito:-aluno(Numero,Aluno,_), not(inscrito(Numero,_)).
naoInscrito(L):-findall(Aluno,alunosInscritos(Aluno),L).
