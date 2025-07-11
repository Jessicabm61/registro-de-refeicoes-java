# Trabalho 1 de BAN 2

Este repositório contém os arquivos e instruções necessários para a execução do **Trabalho 2** da disciplina de **BAN 2**, utilizando o banco de dados NoSQL **Neo4j**.

---

## 💾 Banco de Dados - Neo4j

Este projeto utiliza o **Neo4j** como sistema de gerenciamento de banco de dados orientado a grafos.

### 🔌 Conexão com o Banco

A classe `Conexao` é responsável por estabelecer a conexão com o banco de dados. Nela estão definidas as seguintes variáveis:

- `serverName`: endereço do servidor Neo4j
- `userName` e `password`: credenciais de acesso
- `url`: caminho completo de conexão com o banco

> ⚠️ **Importante:** A biblioteca do driver Neo4j deve estar corretamente configurada no projeto. Ela está disponível no diretório `bibliotecas`. Caso a referência seja perdida, adicione a biblioteca novamente ao classpath.

---

## 🗃️ Dump do Banco de Dados

## 🔄 Restauração do Banco de Dados

O diretório `backup` contém duas formas de restauração:

 **Dump em Cypher (`backup_nutricao_neo4j.txt`)**  
  Arquivo em formato texto contendo comandos Cypher para recriar o banco.  
  Execute no Neo4j Browser ou Neo4j Desktop (com APOC habilitado):

  ```cypher
  CALL apoc.cypher.runFile("caminho/para/backup_nutricao_neo4j.txt", {statistics: false});

 **Cópia física do banco**

  1. Pare o Neo4j, se estiver em execução.  
  2. Substitua a pasta do banco localizada em `data/databases/` pela pasta presente no diretório `backup`.  
  3. Reinicie o Neo4j para carregar o banco restaurado.

### Restaurar no Neo4j Desktop

1. Abra o **Neo4j Desktop**
2. Crie um novo projeto ou abra um existente
3. Restaure o banco de dados (com o mesmo nome do dump ou um nome à sua escolha)
4. Inicie o banco e verifique se os dados foram carregados corretamente

---

## ▶️ Execução do Projeto

A execução da aplicação deve ser iniciada a partir da classe `Main`.

Certifique-se de que:

- O Neo4j está em execução
- As informações de conexão na classe `Conexao` estão corretas
- A biblioteca do driver Neo4j está configurada corretamente

---


