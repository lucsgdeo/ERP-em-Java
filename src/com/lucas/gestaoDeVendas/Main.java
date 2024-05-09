package com.lucas.gestaoDeVendas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucas.gestaoDeVendas.modelo.dao.conexao.Conexao;
import com.lucas.gestaoDeVendas.modelo.dao.conexao.ConexaoMysql;
import com.lucas.gestaoDeVendas.modelo.dominio.Categoria;

public class Main {

	public static void main(String[] args) throws SQLException {
		String sql = "select * from categoria";
		String sql2 = "insert into categoria(nome, descricao) values (?, ?)";
		Conexao conexao = new ConexaoMysql();
		Categoria categoria = new Categoria(null, "Bebida Java", "Inserção no Eclipse");
		
		PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql2);
		
		preparedStatement.setString(1, categoria.getNome());
		preparedStatement.setString(2, categoria.getDescricao());
		
		int res = preparedStatement.executeUpdate();
		System.out.println(res);
		
		ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
		
		while(result.next()) {
			System.out.println(result.getString("nome"));
		}
		
	}
}
