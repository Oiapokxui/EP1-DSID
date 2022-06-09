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

## Header da tabela de coleções

| time | type* | collection_id | priority | instance_index | resource_requests.cpu | resource_requests.memory |
|------|-------|---------------|----------|----------------|-----------------------|--------------------------|

# Perguntas

- Como é a requisição de recursos computacionais (memória e CPU) do cluster durante o tempo?
    - Analisar os últimos campos da tabela de coleções.
- As diversas categorias de jobs possuem características diferentes (requisição de recursos computacionais, frequência de submissão, etc.)?
- Quantos jobs são submetidos por hora?
- Quantas tarefas são submetidas por hora?
- Quanto tempo demora para a primeira tarefa de um job começar a ser executada?
