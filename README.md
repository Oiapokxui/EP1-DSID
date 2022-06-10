# Conceitos

Jobs são compostos de:

- Um conjunto de computações (diversas tasks) que um usuário gostaria de rodar na máquina Borg ;
- Um conjunto de alloc, que é um recurso computacional a ser usado para rodar o job.

Tasks são programas que rodam no Linux que rodam em uma única máquina, \
independente do número de processos criados pelo programa.

Collections são conjuntos de jobs ou tasks e Instances são unidades de jobs ou tasks.

# Ordem hierárquica de objetos do Borg:

- Collection 1 - N Job
- Job 1 - (0,1) Conjuntos de Allocs
- Job 1 - N Task
- Task 1 - (0,1) instância de Alloc (Ou você atrela a task a um alloc ou aos recursos de uma máquina)
- Task 1 - (0,N) Máquina (Uma task pode rodar em várias ou nenhuma máquina)

# Header dos dados

## Header da tabela de coleções

| time | type* | collection_id | priority |
|------|-------|---------------|----------|

## Header da tabela de instâncias

| time | type* | collection_id | priority | instance_index | resource_requests.cpu | resource_requests.memory |
|------|-------|---------------|----------|----------------|-----------------------|--------------------------|

# Perguntas

1. Como é a requisição de recursos computacionais (memória e CPU) do cluster durante o tempo?
2. As diversas categorias de jobs possuem características diferentes (requisição de recursos computacionais, frequência de submissão, etc.)?
3. Quantos jobs são submetidos por hora?
4. Quantas tarefas são submetidas por hora?
5. Quanto tempo demora para a primeira tarefa de um job começar a ser executada?

# Possíveis caminhos para resolver

1. Analisar os campos `resource_requests` da tabela de instâncias
2. Agrupar por `type` e fazer análises.
3. Possível heurística: Ordenar pelo campo `time`, pegar o valor mínimo (tirando 0) e o máximo, tirar a diferença, calcular quantas horas isso equivale e criar
   um map (long -> long) com essas tantas entradas. A partir disso, para cada entrada na tabela, calcular em qual das horas ela se encaixa
   (acho que dá pra fazer com mod) e incrementar em um entrada do map que corresponde àquela hora.
4. similar, só precisa entender como se diferencia uma entrada que é um job de uma que é uma task.
5. ?
 