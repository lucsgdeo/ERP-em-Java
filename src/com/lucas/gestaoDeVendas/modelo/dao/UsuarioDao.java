package com.lucas.gestaoDeVendas.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lucas.gestaoDeVendas.modelo.dao.conexao.Conexao;
import com.lucas.gestaoDeVendas.modelo.dao.conexao.ConexaoMysql;
import com.lucas.gestaoDeVendas.modelo.dominio.Perfil;
import com.lucas.gestaoDeVendas.modelo.dominio.Usuario;

public class UsuarioDao {
	private final Conexao conectar;
	
	public UsuarioDao() {
		this.conectar = new ConexaoMysql();
	}
	
	public String salvar(Usuario usuario) {
		return usuario.getId() == 0 ? adicionar(usuario) : editar(usuario);
	}

	private String adicionar(Usuario usuario) {
		String sql = "INSERT INTO usuario(nome, usuario, senha, perfil, estado) VALUES (?, ?, ?, ?, ?)";
		
		Usuario usuarioTemp = buscarUsuarioPeloUsuario(usuario.getUsuario());
		
		if(usuarioTemp != null) {
			return String.format("Error: usuário %s já existe no banco.", usuarioTemp.getUsuario());
		}
		
		try {
			PreparedStatement preparedStatement = conectar.obterConexao().prepareStatement(sql);
			preencherValoresNoPreparedStatement(preparedStatement, usuario);
			int resultado = preparedStatement.executeUpdate();

			return resultado == 1 ? "Usuário adicionado com sucesso" : "Não foi possível adicionar usuário";
		} catch (SQLException e) {
			return e.getMessage();
		}
	}


	private String editar(Usuario usuario) {
		String sql = "UPDATE usuario SET nome = ?, usuario = ?, senha = ?, perfil = ?, estado = ? WHERE id = ?";
		try {
			PreparedStatement preparedStatement = conectar.obterConexao().prepareStatement(sql);
			preencherValoresNoPreparedStatement(preparedStatement, usuario);
			int resultado = preparedStatement.executeUpdate();
			
			return resultado == 1 ? "Usuário editado com sucesso" : "Não foi possível editar usuário";			
		} catch (SQLException e) {
			return e.getMessage();
		}
	}
	
	private void preencherValoresNoPreparedStatement(PreparedStatement preparedStatement, Usuario usuario) throws SQLException {
		preparedStatement.setString(1, usuario.getNome());
		preparedStatement.setString(2, usuario.getUsuario());
		preparedStatement.setString(3, usuario.getSenha());
		preparedStatement.setString(4, usuario.getPerfil().name());
		preparedStatement.setBoolean(5, usuario.isEstado());
		
		if(usuario.getId() != 0L) {
			preparedStatement.setLong(6, usuario.getId());
		}
	}
	
	public List<Usuario> buscarTodosUsuarios() {
		String sql = "SELECT * FROM usuario";
		List<Usuario> usuarios = new ArrayList<>();
		
		try {
			ResultSet resultset = conectar.obterConexao().prepareStatement(sql).executeQuery();
			
			while(resultset.next()) {
				usuarios.add(getUsuario(resultset));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return usuarios;
	}
	
	public Usuario buscarUsuarioPeloId(Long id) {
		String sql = String.format("SELECT * FROM usuario WHERE id = %d", id);
		try {
			ResultSet resultset = conectar.obterConexao().prepareStatement(sql).executeQuery();
			
			if(resultset.next()) {
				return getUsuario(resultset);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public Usuario buscarUsuarioPeloUsuario(String usuario) {
		String sql = String.format("SELECT * FROM usuario WHERE usuario = %s", usuario);
		try {
			ResultSet resultset = conectar.obterConexao().prepareStatement(sql).executeQuery();
			
			if(resultset.next()) {
				return getUsuario(resultset);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	private Usuario getUsuario(ResultSet resultSet) throws SQLException {
		Usuario usuario = new Usuario();
		
		usuario.setId(resultSet.getLong("id"));
		usuario.setNome(resultSet.getString("nome"));
		usuario.setSenha(resultSet.getString("senha"));
		usuario.setUsuario(resultSet.getString("usuario"));
		usuario.setPerfil(resultSet.getObject("perfil", Perfil.class));
		usuario.setEstado(resultSet.getBoolean("estado"));
		usuario.setDataHoraCriacao(resultSet.getObject("data_hora_criacao", LocalDateTime.class));
		usuario.setUltimoLogin(resultSet.getObject("ultimo_login", LocalDateTime.class));
		
		return usuario;
		
	}
	
	
}
