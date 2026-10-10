# Projeto E-commerce — PostgreSQL e MongoDB

## 1. Introdução

Este projeto tem como objetivo implementar e documentar uma estrutura de banco de dados para um sistema de comércio eletrônico (e-commerce), utilizando PostgreSQL e MongoDB.

O PostgreSQL é utilizado para representar os dados relacionais e manter a integridade das informações. O MongoDB é utilizado para demonstrar uma abordagem baseada em documentos, com flexibilidade de esquema, incorporação de dados e referências entre collections.

O projeto também inclui uma rotina de backup lógico automatizado, agendamento diário por Cron, procedimento de restauração e análise de índices no MongoDB.

## 2. Descrição do domínio

O sistema representa uma loja virtual que permite cadastrar clientes, endereços, categorias, fornecedores e produtos. Também permite registrar pedidos, itens comprados, pagamentos, cupons, transportadoras e avaliações.

### 2.1 Modelo lógico PostgreSQL

O banco de dados relacional possui as seguintes tabelas:

- **clientes:** armazena nome, CPF, e-mail, telefone e data de cadastro.
- **enderecos:** armazena os endereços associados aos clientes.
- **categorias:** organiza os produtos por categoria.
- **fornecedores:** armazena os dados dos fornecedores.
- **produtos:** registra os produtos, seus preços, estoques, categorias e fornecedores.
- **pedidos:** registra as compras realizadas pelos clientes e seus respectivos status.
- **itens_pedido:** registra os produtos e as quantidades presentes em cada pedido.
- **pagamentos:** armazena método, valor, status e data do pagamento.
- **cupons:** registra códigos promocionais, percentuais de desconto e validade.
- **transportadoras:** armazena os dados das empresas de transporte.
- **avaliacoes:** registra as avaliações dos clientes sobre os produtos.

### 2.2 Integridade dos dados

O esquema utiliza chaves primárias (`PRIMARY KEY`) para identificar registros e chaves estrangeiras (`FOREIGN KEY`) para relacionar as tabelas.

As restrições `NOT NULL`, `UNIQUE` e `CHECK` impedem determinados dados inválidos ou duplicados. Valores padrão (`DEFAULT`) são usados para preencher automaticamente campos como datas, estoque e status.

## 3. Backup lógico do PostgreSQL

O script `scripts/08_backup.sh` automatiza a criação de backups utilizando o comando `pg_dump`.

O backup é gerado no formato customizado, por meio da opção `-Fc`, que permite restaurar os dados com o comando `pg_restore`.

### 3.1 Funcionamento do script

A rotina:

1. Define o banco de dados, o usuário e o diretório de armazenamento.
2. Gera um nome de arquivo com data e hora.
3. Executa o `pg_dump` em formato customizado.
4. Registra mensagens de execução e erros em um arquivo de log.
5. Evita publicar o arquivo final caso a criação do backup falhe.
6. Remove backups antigos de acordo com a retenção configurada, de 30 dias por padrão.

### 3.2 Execução manual

Para executar o script:

```bash
chmod +x scripts/08_backup.sh
DB_NAME=ecommerce DB_USER=postgres ./scripts/08_backup.sh
```

O diretório de saída pode ser personalizado com a variável `BACKUP_DIR`.

## 4. Agendamento com Cron

O agendamento automático é configurado por meio do comando:

```bash
crontab -e
```

A expressão cron utilizada é:

```cron
0 2 * * *
```

Essa expressão representa uma execução diária às 2h da manhã.

A linha completa do agendamento deve conter o caminho absoluto do script e o redirecionamento da saída para um arquivo de log. O usuário responsável pela execução precisa ter as permissões necessárias e uma configuração de autenticação que funcione sem interação manual.

## 5. Teste de restauração

Para testar a recuperação dos dados, deve ser criado um banco de teste vazio. O backup gerado pode então ser restaurado com `pg_restore`.

Exemplo:

```bash
createdb -U postgres ecommerce_teste

pg_restore -U postgres \
    -d ecommerce_teste \
    --verbose \
    "$HOME/backups/postgresql/ARQUIVO.dump"
```

O nome `ARQUIVO.dump` deve ser substituído pelo arquivo efetivamente gerado.

A integridade pode ser verificada comparando as contagens de registros das tabelas originais com as contagens das tabelas restauradas.

Exemplo de consulta:

```sql
SELECT COUNT(*) FROM clientes;
SELECT COUNT(*) FROM produtos;
SELECT COUNT(*) FROM pedidos;
```

O teste deve registrar os resultados obtidos nos dois bancos. A igualdade das contagens nas tabelas verificadas é uma evidência de recuperação dos registros, embora testes adicionais sejam necessários para verificar completamente a integridade do sistema.

## 6. Estratégia de backup 3-2-1

A regra 3-2-1 é uma estratégia recomendada de proteção de dados que estabelece:

- **3 cópias:** manter três cópias dos dados, incluindo os dados originais.
- **2 mídias ou sistemas de armazenamento:** utilizar dois tipos de mídia ou sistemas distintos.
- **1 cópia externa:** manter pelo menos uma cópia fora do ambiente principal.

O script deste projeto realiza a criação e a retenção de backups locais. Para atender integralmente à estratégia 3-2-1, é necessário complementar a rotina com cópias em outro sistema de armazenamento e em um destino externo, preferencialmente com controle de acesso e criptografia.

A realização periódica de testes de restauração também é importante para verificar se os backups podem ser utilizados em uma situação real de recuperação.

## 7. Introdução ao MongoDB

O MongoDB é um banco de dados orientado a documentos. Em vez de organizar todas as informações em tabelas relacionais, utiliza collections que armazenam documentos no formato BSON.

Neste projeto, são utilizadas as collections:

- `clientes`: armazena informações cadastrais dos clientes.
- `produtos`: armazena os dados atuais dos produtos.
- `pedidos`: armazena os pedidos e os dados incorporados relacionados à compra.

O arquivo `mongodb/consultas.js` contém dados fictícios e exemplos de operações para demonstrar a flexibilidade do esquema.

## 8. Modelagem: Embedding e Referencing

### 8.1 Embedding

Embedding consiste em armazenar dados relacionados dentro do mesmo documento.

Neste projeto, os pedidos armazenam os itens comprados e o endereço de entrega como documentos incorporados.

Essa escolha facilita a recuperação de um pedido completo em uma única consulta e permite preservar dados históricos, como o nome e o preço unitário do produto no momento da compra.

### 8.2 Referencing

Referencing consiste em armazenar identificadores que apontam para documentos em outras collections.

No projeto, o campo `cliente_id` dos pedidos referencia o identificador de um cliente. Os campos `produto_id` dos itens também permitem identificar os produtos cadastrados na collection `produtos`.

Essa abordagem reduz a necessidade de duplicar os dados cadastrais atuais de clientes e produtos em todos os pedidos.

As referências precisam ser mantidas pela aplicação, pois o MongoDB não aplica automaticamente restrições de chave estrangeira entre collections.

## 9. Consultas, índices e explain()

O arquivo `mongodb/consultas.js` demonstra:

- Consultas com `find()` e filtros.
- Projeção de campos para retornar somente as informações necessárias.
- Ordenação com `sort()`.
- Índice composto para consultas de pedidos por cliente e data.
- Índice de texto para pesquisar produtos pelo nome ou categoria.
- Análise do plano de execução com `explain("executionStats")`.

Os resultados de `explain()` devem ser analisados após a execução do script. Campos como `winningPlan`, `totalKeysExamined` e `totalDocsExamined` ajudam a entender como a consulta foi executada.

Como o conjunto de dados de demonstração é pequeno, a utilização de um índice pode não produzir uma melhoria de desempenho perceptível. A análise deve considerar os resultados reais, sem presumir que todos os índices serão utilizados em todas as consultas.

## 10. Comparação entre PostgreSQL e MongoDB

### PostgreSQL

O PostgreSQL é adequado para o núcleo transacional do e-commerce, pois oferece relacionamentos explícitos, chaves estrangeiras, restrições de integridade e transações.

Essas características são importantes para manter a consistência entre pedidos, clientes, itens, pagamentos e estoque.

### MongoDB

O MongoDB é adequado para cenários que se beneficiam de documentos flexíveis e dados incorporados, como catálogos com atributos variados, eventos de navegação e documentos de pedidos utilizados principalmente para leitura.

A flexibilidade do esquema pode facilitar a evolução de determinados tipos de dados, mas exige atenção à validação e à consistência das informações.

### Conclusão da comparação

Para este projeto, o PostgreSQL é uma escolha adequada para armazenar os dados transacionais que dependem de relacionamentos e integridade referencial. O MongoDB demonstra uma alternativa para armazenar documentos agregados e informações com estrutura variável.

A escolha definitiva depende dos requisitos da aplicação, das consultas realizadas, do volume de dados e das necessidades de consistência e operação.

## 11. Evidências de execução

Para comprovar a realização da atividade, devem ser incluídas evidências dos seguintes procedimentos:

1. Criação das tabelas PostgreSQL sem erros.
2. Execução manual do script de backup.
3. Geração do arquivo de backup e registro no log.
4. Configuração do agendamento diário no Cron.
5. Restauração do backup em um banco de teste.
6. Comparação das contagens de registros entre os bancos.
7. Inserção dos documentos no MongoDB.
8. Execução das consultas com filtros, projeções e ordenação.
9. Criação dos índices e execução de `explain("executionStats")`.
10. Análise dos resultados efetivamente obtidos.

**Observação:** os exemplos e comandos documentam o procedimento esperado. Os resultados e as evidências devem ser preenchidos após a execução real no ambiente utilizado para o trabalho.

