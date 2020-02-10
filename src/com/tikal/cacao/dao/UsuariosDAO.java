/**
 * 
 */
package com.tikal.cacao.dao;

/**
 * @author Tikal
 *
 */
public interface UsuariosDAO<T> {
	
	/**
	 * 
	 * @param usuario
	 */
	public boolean crear(T usuario);
	
	/**
	 * 
	 * @param usuario
	 */
	public void actualizar(T usuario);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public T consultar(String id);
	
	/**
	 * 
	 * @param usuario
	 */
	public void eliminar(T usuario);
	

}
