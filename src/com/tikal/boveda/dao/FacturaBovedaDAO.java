package com.tikal.boveda.dao;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.tikal.boveda.modelo.FacturaBoveda;

	
	public interface FacturaBovedaDAO {

		/**
		 * <p>&Eacute;ste m&eacutetodo almacena una factura en un almac&eacute;n de persistencia
		 * que depender&aacute de la implementaci&oacute;n.
		 * @param f la factura timbrada a almacenar 
		 */
		public void guardar(FacturaBoveda f);
		
		/**
		 * <p>&Eacute;ste m&eacutetodo regresa una factura {@code Factura} timbrada 
		 * la cual se identifica por el <em>N&uacute;mero del folio fiscal (UUID)</em> </p>
		 * 
		 * @param uuid N&uacute;mero de folio fiscal de la factura
		 * @return la factura con el <em>uuid</em> especificado.
		 */
		public FacturaBoveda consultar(String uuid);
		
		/**
		 * <p>&Eacute;ste m&eacutetodo regresa un objeto {@code List} con las facturas {@code Factura}
		 * que se han timbrado hasta el momento y han sido emitidos por el emisor cuyo <em>RFC</em>
		 * es el especificado.</p>
		 * 
		 * <p>Si no se encuentran facturas timbradas, el objeto que se debe regresar es un objeto 
		 * {@code List} vac&iacuteo.</p>
		 * 
		 * @param rfcEmisor el RFC del emisor de las facturas
		 * @return una lista con las facturas timbradas hasta el momento
		 */
		public List<FacturaBoveda> consutarTodas(String rfcEmisor);
		
		
		public List<FacturaBoveda> getAll();
		

		/**
		 * <p>&Eacute;ste m&eacutetodo regresa un objeto {@code List} con las facturas {@code Factura}
		 * que se han timbrado hasta el momento y han sido emitidos por el emisor cuyo <em>RFC</em>
		 * es el especificado.</p>
		 * 
		 * <p>Si no se encuentran facturas timbradas, el objeto que se debe regresar es un objeto 
		 * {@code List} vac&iacuteo.</p>
		 * 
		 * @param rfcEmisor el RFC del emisor de las facturas
		 * @param page pagina de facturas
		 * @return una lista con las facturas timbradas hasta el momento
		 */
		public List<FacturaBoveda> consutarTodas( int page);
		
		
		
		/**
		 * <p>&Eacute;ste m&eacutetodo elimina el objeto {@code Factura} <em>f</em> especificado del almac&eacute;n de persistencia</p>
		 * <p>La factura <em>f</em> debe tener en su atributo <em>estatus</em> el valor de {@code Estatus.GENERADO} para que pueda
		 * ser eliminado del almac&eacute;n.</p>
		 * @param f la factura sin timbrar que se va a eliminar del almac&eacute;n de persistencia
		 * @return {@code true} si se elimin&oacute; la factura, {@code false} en caso contrario
		 */
		public boolean eliminar(FacturaBoveda f);
		
		public void eliminar1(String uuid);
		
		public int getPaginasRfc(String rfc);
			
		public int getPaginas();
		
		
		public List<FacturaBoveda> buscar(Date fi, Date ff,String rfc);
		
		public List<FacturaBoveda> getFacByProv(String rfcEmisor, int page);
		
		void enviarEmail(String email, String uuid);
		void enviaUsuarioBoveda(String emailReceptor, String usuario, String password) ;
	}

