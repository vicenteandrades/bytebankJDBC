package br.com.bytebank.domain.conta;

import br.com.bytebank.domain.cliente.Cliente;
import br.com.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAL {

    private Connection connection;
    public ContaDAL(Connection connection) {
        this.connection = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta){
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), BigDecimal.ZERO ,cliente);

        String sqlInsert = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email)" +
                "VALUES (?, ?, ?, ?, ?)";


        try{
            PreparedStatement preparedStatement =  connection.prepareStatement(sqlInsert);
            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, dadosDaConta.dadosCliente().nome());
            preparedStatement.setString(4, dadosDaConta.dadosCliente().cpf());
            preparedStatement.setString(5, dadosDaConta.dadosCliente().email());

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listar(){
        Set<Conta> contas = new HashSet<>();
        String sql = "SELECT * FROM conta";


        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while(set.next()){
                Integer numero = set.getInt(1);
                BigDecimal saldo = set.getBigDecimal(2);
                String nome = set.getString(3);
                String cpf = set.getString(4);
                String email = set.getString(5);

                DadosCadastroCliente dadosCadastroCliente =
                        new DadosCadastroCliente(nome,cpf,email);

                Cliente cliente = new Cliente(dadosCadastroCliente);
                contas.add( new Conta(numero,saldo, cliente));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return contas;
    }

    public Conta listarPorNumero(Integer numero) {
        String sql = "SELECT * FROM conta WHERE numero = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, numero);
            ResultSet set = ps.executeQuery();

            if (set.next()) {
                Integer numeroConta = set.getInt(1);
                BigDecimal saldo = set.getBigDecimal(2);
                String nome = set.getString(3);
                String cpf = set.getString(4);
                String email = set.getString(5);

                DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(nome, cpf, email);
                Cliente cliente = new Cliente(dadosCadastroCliente);


                return new Conta(numeroConta, saldo ,cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return null;
    }


    public void alterar(Integer numero, BigDecimal valor) {
        PreparedStatement ps;
        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";

        try {
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, valor);
            ps.setInt(2, numero);

            ps.execute();
            ps.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer numero)
    {
        String sql = "DELETE FROM conta WHERE numero = ?";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,  numero);
            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
