package com.tikal.boveda.dao;

import java.util.Date;
import java.util.List;

import com.tikal.boveda.modelo.BovedaRenglon;

public interface BovedaRenglonDao {
	
	void guardar(BovedaRenglon r);
	
	void guardar(List<BovedaRenglon> lista);
	
	BovedaRenglon consultar(String uuid);
	
	List<BovedaRenglon> consultarPeriodo( Date fechaI, Date fechaF);
	
	List<BovedaRenglon> consultarRfc(String rfcEmisor, Date fechaI, Date fechaF);
	
	List<BovedaRenglon> consultarFecha(Date fechaProg, Date fechaI, Date fechaF);
	
	List<BovedaRenglon> consultarEstatus(String estatus, Date fechaI, Date fechaF);
	
	List<BovedaRenglon> consultar(String rfcEmisor, String rfcReceptor, String serie, Date fechaI, Date fechaF);
	
	List<BovedaRenglon> consultarPag(String rfcEmisor, int page);
	
	List<BovedaRenglon> consultarPag(String rfcEmisor, String serie, int page);
	
	List<BovedaRenglon> consultarPagRec(String rfcEmisor, String receptor, int page);
	
	List<BovedaRenglon> consultarids(String[] ids);
	
	int pags(String rfcEmisor);
	
	int pags(String rfc, String serie);
	
	int pagsRec(String rfc, String receptor);
	
	/**
	 * <h2>Atenci&oacute;n</h2>
	 * <p>El prop&oacute;sito de este m&eacute;todo es borrar los <em>entities<em> {@code ReporteRenglon}
	 * cuyo atributo estatus tiene el valor de <strong>GENERADO</strong> y se creo otro 
	 * <em>entity</em> {@code ReporteRenglon} con un estatus de <strong>TIMBRADO</strong></p>
	 * @param uuid
	 */
	void eliminar(String uuid);
	
	void eliminar(List<BovedaRenglon> lista);
}

