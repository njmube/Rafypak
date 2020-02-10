package com.tikal.cacao.security;

import java.util.List;

import com.tikal.cacao.model.Usuario;

public interface UsuarioDAO<T> {
	
	public boolean crearUsuario(T usuario);
	public boolean eliminarUsuario(String usuario);
	public T consultarUsuario(String usuario);
	public List<T> consultarUsuarios();
	public boolean actualizarUsuario(T usuario);
	public boolean eliminarUsuario(T usuario);
	public boolean actualizarUsuarios(String nombrePerfilviejo, String nombrePerfilNuevo);
	public T consultarPorEmail(String email);
	
}