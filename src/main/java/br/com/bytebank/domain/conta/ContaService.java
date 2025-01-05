package br.com.bytebank.domain.conta;

import br.com.bytebank.ConnectionFactory;
import br.com.bytebank.domain.RegraDeNegocioException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class ContaService {
    private ConnectionFactory connection;

    public ContaService() {
        this.connection = new ConnectionFactory();
    }

    public Set<Conta> listarContasAbertas() {
        Connection conn = connection.recuperaConexao();
        return new ContaDAL(conn).listar();
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) throws SQLException {
            Connection conn = connection.recuperaConexao();
            new ContaDAL(conn).salvar(dadosDaConta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }

        Connection conn = connection.recuperaConexao();
        new ContaDAL(conn).alterar(conta.getNumero(), valor);

        BigDecimal novoSalvo = conta.getSaldo().subtract(valor);
        alterar(conta, novoSalvo);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        Connection conn = connection.recuperaConexao();
        new ContaDAL(conn).alterar(conta.getNumero(), valor);

        BigDecimal novoSalvo = conta.getSaldo().add(valor);
        alterar(conta, novoSalvo);
    }

    public void encerrar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }

        Connection conn = connection.recuperaConexao();

        new ContaDAL(conn).deletar(numeroDaConta);

    }

    private Conta buscarContaPorNumero(Integer numero) {
        Connection conn = connection.recuperaConexao();
        Conta conta = new ContaDAL(conn).listarPorNumero(numero);
        if(conta != null) {
            return conta;
        } else {
            throw new RegraDeNegocioException("Não existe conta cadastrada com esse número!");
        }
    }

    private void alterar(Conta conta, BigDecimal valor) {
        Connection conn = connection.recuperaConexao();
        new ContaDAL(conn).alterar(conta.getNumero(), valor);
    }

    public void realizarTransferencia(Integer contaOrigem, Integer contaDestino, BigDecimal valor) {
        this.realizarSaque(contaOrigem, valor);
        this.realizarDeposito(contaDestino, valor);
    }
}
