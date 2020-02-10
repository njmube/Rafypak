package com.tikal.cacao.boveda.dao.impl;

import com.tikal.cacao.boveda.model.UsuarioProveedor;
import com.tikal.cacao.dao.UsuariosDAO;
import com.tikal.cacao.model.Usuario;
import com.tikal.cacao.security.UsuarioDAO;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

public class UsuarioProveedorDAOImpl implements UsuarioDAO<UsuarioProveedor>{

	@Override
	public boolean crearUsuario(UsuarioProveedor usuario) {
		if(this.consultarPorEmail(usuario.getEmail()).getEmail()!=null){
			return false;
		}
		if (this.consultarUsuario(usuario.getUsername())==null) {
			ofy().save().entity(usuario).now();
		} else {
			return false;
		}

		return true;
	}

	@Override
	public boolean eliminarUsuario(String usuario) {
		ofy().delete().entities(this.consultarUsuario(usuario)).now();
		return true;
	}

	@Override
	public UsuarioProveedor consultarUsuario(String usuario) {
		List<UsuarioProveedor> usu = ofy().load().type(UsuarioProveedor.class).filter("usuario", usuario).list();
		if (usu.size() == 0) {
			return null;
		}
		UsuarioProveedor nuevo = usu.get(0);
		return nuevo;
	}

	@Override
	public List<UsuarioProveedor> consultarUsuarios() {
		return ofy().load().type(UsuarioProveedor.class).list();
	}

	@Override
	public boolean actualizarUsuario(UsuarioProveedor usuario) {
		ofy().save().entity(usuario).now();
		return true;
	}

	@Override
	public boolean eliminarUsuario(UsuarioProveedor usuario) {
		ofy().delete().entity(usuario).now();
		return true;
	}

	@Override
	public boolean actualizarUsuarios(String nombrePerfilviejo, String nombrePerfilNuevo) {
		List<UsuarioProveedor> lista =  ofy().load().type(UsuarioProveedor.class).filter("perfil", nombrePerfilviejo).list();
		for(int i = 0; i < lista.size(); i++){
			lista.get(i).setPerfil(nombrePerfilNuevo);
		}
		ofy().save().entities(lista).now();
		return true;
	}

	@Override
	public UsuarioProveedor consultarPorEmail(String email) {
		List<UsuarioProveedor> lista = ofy().load().type(UsuarioProveedor.class).filter("email", email).list();	
//		System.out.println("Tama�o de la lista: " + lista.size());
		UsuarioProveedor usuario = new UsuarioProveedor();
		if(lista.size()>0){
			return lista.get(0);
		}
		return usuario;	
	}


}
