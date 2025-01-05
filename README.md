# bytebankJDBC

1. Descrição Geral do Sistema
O ByteBank é um sistema desenvolvido em Java que utiliza JDBC para a integração com o
banco de dados. Ele oferece funcionalidades para gerenciamento de clientes e contas
bancárias, abrangendo desde a abertura de contas até a aplicação de regras de negócio
específicas.
Este sistema é projetado seguindo boas práticas de programação orientada a objetos, com foco
na separação de responsabilidades e modularidade.

2. Estrutura do Projeto
● Pacote Principal (br.com.bytebank)
○ Contém a classe principal do sistema:
■ BytebankApplication.java: Ponto de entrada da aplicação.
○ Contém a classe ConnectionFactory.java para gerenciar conexões com o banco
de dados.

● Domínio (br.com.bytebank.domain)
○ Representa o núcleo da lógica de negócios:
■ Pacote cliente:
■ Cliente.java: Representa os clientes do banco.
■ DadosCadastroCliente.java: Armazena informações necessárias
para cadastro de um cliente.

■ Pacote conta:
■ Conta.java: Modela as contas bancárias.
■ DadosAberturaConta.java: Dados necessários para abertura de
uma nova conta.
■ ContaDAL.java: Classe de acesso aos dados para manipular
informações de contas no banco de dados.
■ ContaService.java: Contém regras de negócio relacionadas às
contas.

■ RegraDeNegocioException.java: Exceção personalizada para representar
falhas nas regras de negócio.

3. Funcionalidades Principais
● Cadastro de clientes.
● Abertura de contas bancárias.
● Consulta e manipulação de contas e clientes.
● Gerenciamento das regras de negócio do banco.

4. Tecnologias Utilizadas
● Java: Linguagem de programação principal.
● JDBC (Java Database Connectivity): Para interagir diretamente com o banco de
dados.
● Maven: Gerenciamento de dependências.
● Banco de Dados Relacional: (Postgresql, configurado manualmente via
ConnectionFactory).

5. Guia de Configuração e Execução
Pré-requisitos:
1. Java JDK (versão 8 ou superior).
2. Maven instalado.
3. Banco de dados configurado conforme especificado no ConnectionFactory.
6. Detalhamento Técnico
Classes-Chave:
● Cliente:
○ Representa os clientes do banco com atributos como id, nome e cpf.
● Conta:
○ Define uma conta bancária com atributos como id, saldo, e titular.
● ContaService:
○ Contém métodos para realizar operações bancárias e validações.
● ContaDAL:
○ Interface com o banco de dados para persistir e consultar informações de
contas.
● ConnectionFactory:
○ Abstrai a criação de conexões com o banco de dados.

8. Detalhamento dos Comandos SQL
Nesta seção, serão apresentados os comandos SQL utilizados no sistema, acompanhados de
prints do código onde os comandos foram implementados e uma breve explicação sobre sua
funcionalidade.

Explicação:
● Propósito: Adicionar uma nova conta bancária ao banco de dados.
● Tabela Afetada: conta.
● Colunas Manipuladas:
○ numero: Número da conta.
○ saldo: Saldo inicial, sempre configurado como 0.
○ cliente_nome, cliente_cpf, cliente_email: Informações do titular da conta.
● Valores: Os valores são passados como parâmetros preparados para evitar injeções de
SQL.
● Execução: O método preparedStatement.execute() executa o comando.

Explicação:
● Propósito: Buscar todas as contas cadastradas no banco de dados.
● Tabela Afetada: conta.
● Colunas Retornadas: Todas as colunas (*).
● Iteração no Resultado: O ResultSet itera sobre os registros retornados, mapeando os
dados para criar objetos Conta.

Explicação:
● Propósito: Buscar uma conta específica pelo número.
● Tabela Afetada: conta.
● Filtro: A cláusula WHERE numero = ? restringe a busca para retornar apenas a conta
correspondente ao número informado.
● Execução: O número é passado como parâmetro preparado.

Explicação:
● Propósito: Atualizar o saldo de uma conta existente no banco de dados.
● Tabela Afetada: conta.
● Colunas Atualizadas:
○ saldo: Substituído pelo novo valor informado.
● Filtro: A cláusula WHERE numero = ? assegura que apenas a conta correspondente
seja atualizada.
● Transação:
○ A atualização ocorre dentro de uma transação
(connection.setAutoCommit(false)).
○ Em caso de falha, é realizado um rollback.

Explicação:
● Propósito: Remover uma conta do banco de dados.
● Tabela Afetada: conta.
● Filtro: A cláusula WHERE numero = ? assegura que apenas a conta correspondente
seja deletada.
● Execução: O número da conta é passado como parâmetro preparado para evitar
injeções de SQL.
