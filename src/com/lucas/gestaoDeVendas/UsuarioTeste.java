package com.lucas.gestaoDeVendas;

import com.lucas.gestaoDeVendas.modelo.dao.UsuarioDao;
import com.lucas.gestaoDeVendas.modelo.dominio.Perfil;
import com.lucas.gestaoDeVendas.modelo.dominio.Usuario;

public class UsuarioTeste {

	public static void main(String[] args) {
		Usuario user = new Usuario(0L, "al", "12324", "al1vadfvfsdgvdf212", Perfil.PADRAO, null, null);
		
		UsuarioDao userDao = new UsuarioDao();
		System.out.println(userDao.salvar(user));
	}
}
