# ByteBank JDBC

**ByteBank JDBC** é um sistema bancário desenvolvido em Java, utilizando JDBC para integração com um banco de dados relacional (PostgreSQL). Ele oferece funcionalidades de gerenciamento de clientes e contas bancárias, implementando desde a abertura de contas até a aplicação de regras de negócio específicas.

O sistema segue boas práticas de programação orientada a objetos, com foco na modularidade e separação de responsabilidades.

## Funcionalidades

- **Cadastro de clientes**: Adicione clientes ao sistema com informações básicas.
- **Abertura de contas bancárias**: Crie contas bancárias para os clientes.
- **Consulta e manipulação de contas e clientes**: Busque, altere e remova contas e clientes.
- **Regras de negócio do banco**: Implementação de validações e operações bancárias.

## Estrutura do Projeto

### Pacote Principal (br.com.bytebank)

- **BytebankApplication.java**: Ponto de entrada da aplicação.
- **ConnectionFactory.java**: Gerencia conexões com o banco de dados.

### Domínio (br.com.bytebank.domain)

#### Cliente

- **Cliente.java**: Representa os clientes do banco.
- **DadosCadastroCliente.java**: Armazena informações necessárias para o cadastro de um cliente.

#### Conta

- **Conta.java**: Modela as contas bancárias.
- **DadosAberturaConta.java**: Armazena os dados necessários para a abertura de uma nova conta.
- **ContaDAL.java**: Classe de acesso aos dados para manipular informações de contas no banco de dados.
- **ContaService.java**: Contém as regras de negócio relacionadas às contas.

- **RegraDeNegocioException.java**: Exceção personalizada para representar falhas nas regras de negócio.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **JDBC**: Para interagir diretamente com o banco de dados.
- **Maven**: Gerenciamento de dependências.
- **PostgreSQL**: Banco de dados relacional.

## Como Rodar o Projeto

1. **Clone este repositório**:

```bash
git clone https://github.com/seuusuario/bytebankJDBC.git
cd bytebankJDBC
