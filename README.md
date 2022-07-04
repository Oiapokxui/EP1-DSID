# Primeiros passos (Deve ser executado em cada máquina que possivelmente rodará spark)

- Baixe o zip do spark, extraia na raiz do projeto.
- Baixe a pasta com os dados e a disponibilize em `/tmp/ep1-dsid/`
- Crie uma variável chamada `SPARK_HOME` no seu ambiente cujo valor é caminho da pasta extraída do spark.
- Copie o arquivo `spark-defaults.conf` para a pasta `$SPARK_HOME/conf`.
- Copie o arquivo `log4j2.properties` para a pasta `$SPARK_HOME/conf`.
- Compile a aplicação com:
    - ```shell 
  ./gradlew build

# Como rodar em cluster standalone

É necessário que todos os nós do cluster estejam na mesma rede

## Como iniciar o nó mestre

```shell
$SPARK_HOME/sbin/start-master.sh
```

## Como iniciar os nós workers

Defina a variável de ambiente `MASTER_URL=spark://<HOST>:7077`, Onde: <HOST> é o nome do host ou seu endereço de IP (o nome deve ser resolvível em um endereço
de IP para que funcione).

```shell
$SPARK_HOME/sbin/start-worker.sh $MASTER_URL
```

## Rodando a aplicação

Após inicialização dos nós Master e Worker do cluster, basta rodar o spark-submit em qualquer uma dos nós do cluster, passando a url do master e o jar
construído (as variáveis de ambiente citadas acima
devem estar inicializadas):

```shell
$SPARK_HOME/bin/spark-submit --master $MASTER_URL --deploy-mode client <APP_JAR>
```

Onde <APP_JAR> é o caminho para o uber jar criado após build do projeto.

# Como rodar em modo local

Defina a variável `MASTER_URL=local[*]` e então execute:

```shell
./gradlew bootRun
```

# Conceitos

Jobs são compostos de:

- Um conjunto de computações (diversas tasks) que um usuário gostaria de rodar na máquina Borg ;
- Um conjunto de alloc, que é um recurso computacional a ser usado para rodar o job.

Tasks são programas que rodam no Linux que rodam em uma única máquina, \
independente do número de processos criados pelo programa.

Collections são conjuntos de jobs ou tasks e Instances são unidades de tasks ou allocs.

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

# Tiers de prioridade

- Free tier (priority ≤ 99)
- Best-effort Batch (beb) (priorities 100–115)
- Mid-tier (priorities 116–119)
- Production tier (priorities 120–359)
- Monitoring tier (priorities ≥ 360)

# Perguntas

1. Como é a requisição de recursos computacionais (memória e CPU) do cluster durante o tempo?
2. As diversas categorias de jobs possuem características diferentes (requisição de recursos computacionais, frequência de submissão, etc.)?
3. Quantos jobs são submetidos por hora?
4. Quantas tarefas são submetidas por hora?
5. Quanto tempo demora para a primeira tarefa de um job começar a ser executada?

# Possíveis caminhos para resolver

1. Analisar os campos `resource_requests` da [tabela de instâncias](#header-da-tabela-de-instncias).
2. Agrupar por [tipos de prioridade](#tiers-de-prioridade) e fazer análises.
3. Ordenar pelo campo `time`, pegar o valor mínimo (tirando 0) e o máximo, tirar a diferença, calcular quantas horas isso equivale e criar
   um map (long -> long) com essas tantas entradas. A partir disso, para cada entrada na tabela, calcular em qual das horas ela se encaixa (dividir por
   3600_000_000 microssegundos por hora e subtrair pela quantidade de horas do time inicial) e incrementar em um a entrada do map que corresponde àquela hora.
5. Similar, só que em cima da tabela de instances.
6. Agrupar instancias por collection_id, ordenar por tempo e pegar a primeira entrada do tipo SUBMIT que aparece no grupo.
