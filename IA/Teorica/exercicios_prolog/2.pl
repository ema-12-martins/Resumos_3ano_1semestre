aluno(joao,calculo).
aluno(maria,calculo).
aluno(joel,programacao).
aluno(joel,estrutura).
frequencia(joao,puc).
frequencia(maria,puc).
frequencia(joel,ufrj).
professor(carlos,calculo).
professor(ana_paula,estrutura).
professor(pedro,programacao).
funcionario(pedro,ufrj).
funcionario(ana_paula,puc).
funcionario(carlos,puc).

alunos_do_professor(A,P):-professor(P,C),aluno(A,C).
associados_a_faculdade(A,F):-funcionario(A,F);frequencia(A,F).