package com.tikal.boveda.dao.impl;

/**
 * 
 */

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import com.tikal.boveda.dao.BovedaRenglonDao;
import com.tikal.boveda.modelo.BovedaRenglon;


/**
 * @author Tikal
 *
 */
@Repository
public class BovedaRenglonDaoImpl implements BovedaRenglonDao {

	@Override
	public void guardar(BovedaRenglon r) {
		ofy().save().entity(r).now();

	}

	@Override
	public void guardar(List<BovedaRenglon> lista) {
		ofy().save().entities(lista).now();

	}

	@Override
	public BovedaRenglon consultar(String uuid) {
		return ofy().load().type(BovedaRenglon.class).id(uuid).now();
	}

	@Override
	public List<BovedaRenglon> consultarPeriodo( Date fechaI, Date fechaF) {
		//<BovedaRenglon> renglones = null;
		System.out.println("fechas:reporte:::::"+fechaI+"---" +fechaF);
		return ofy().load().type(BovedaRenglon.class)
					.filter("fechaBoveda >=", fechaI).filter("fechaBoveda <=", fechaF).order("- fechaBoveda").list();
		
	//	return renglones;
	}
	
	@Override
	public List<BovedaRenglon> consultarRfc(String rfcEmisor,  Date fechaI, Date fechaF) {
		List<BovedaRenglon> renglones = null;
		
			renglones = ofy().load().type(BovedaRenglon.class).filter("rfcEmisor", rfcEmisor)
					.filter("fechaBoveda >=", fechaI).filter("fechaBoveda <=", fechaF).list();
		
		return renglones;
	}
	
	@Override
	public List<BovedaRenglon> consultarFecha( Date fechaProg,  Date fechaI, Date fechaF) {
		List<BovedaRenglon> renglones = null;
		
			renglones = ofy().load().type(BovedaRenglon.class).filter("fechaProgramada ", fechaProg)
					.filter("fechaBoveda >=", fechaI).filter("fechaBoveda <=", fechaF).list();
		
		return renglones;
	}

	@Override
	public List<BovedaRenglon> consultarEstatus(String estatus,  Date fechaI, Date fechaF) {
		List<BovedaRenglon> renglones = null;
		
			renglones = ofy().load().type(BovedaRenglon.class).filter("estatus ", estatus)
					.filter("fechaBoveda >=", fechaI).filter("fechaBoveda <=", fechaF).list();
		
		return renglones;
	}
	
	@Override
	public List<BovedaRenglon> consultar( String rfcEmisor, String rfcReceptor, String serie, Date fechaI,
			Date fechaF) {
		List<BovedaRenglon> renglones = ofy().load().type(BovedaRenglon.class).filter("rfcEmisor", rfcEmisor)
				.filter("serie", serie).filter("rfcRec", rfcReceptor).filter("fecha >=", fechaI)
				.filter("fecha <=", fechaF).list();
		return renglones;
	}

	@Override
	public List<BovedaRenglon> consultarPag(String rfcEmisor, int page) {
		List<BovedaRenglon> renglones = ofy().load().type(BovedaRenglon.class).filter("rfcEmisor", rfcEmisor)
				.order("-fechaBoveda").offset(25 * (page - 1)).limit(25).list();
		if (renglones == null) {
			return new ArrayList<>();
		} else {
			return renglones;
		}
	}

	@Override
	public List<BovedaRenglon> consultarPag(String rfcEmisor, String serie, int page) {
		List<BovedaRenglon> renglones = ofy().load().type(BovedaRenglon.class).filter("rfcEmisor", rfcEmisor)
				.filter("serie", serie).order("- fechaBoveda").offset(25 * (page - 1)).limit(25).list();
		if (renglones == null) {
			return new ArrayList<>();
		} else {
			return renglones;
		}
	}
	
	@Override
	public int pags(String rfcEmisor) {
		return ((ofy().load().type(BovedaRenglon.class).filter("rfcEmisor",rfcEmisor).count()-1)/25)+1;
	}

	@Override
	public int pags(String rfc, String serie) {
		return ((ofy().load().type(BovedaRenglon.class).filter("rfcEmisor", rfc).filter("serie", serie).count() - 1)
				/ 25) + 1;
	}

	@Override
	public List<BovedaRenglon> consultarPagRec(String rfcEmisor, String receptor, int page) {
		List<BovedaRenglon> renglones = ofy().load().type(BovedaRenglon.class).filter("rfcEmisor", rfcEmisor)
				.filter("rfcReceptor", receptor).order("- fechaBoveda").offset(25 * (page - 1)).limit(25).list();
		if (renglones == null) {
			return new ArrayList<>();
		} else {
			return renglones;
		}
	}

	@Override
	public int pagsRec(String rfc, String receptor) {
		return ((ofy().load().type(BovedaRenglon.class).filter("rfcEmisor", rfc).filter("rfcReceptor", receptor)
				.count() - 1) / 25) + 1;
	}

	@Override
	public void eliminar(String uuid) {
		ofy().delete().type(BovedaRenglon.class).id(uuid).now();
	}

	@Override
	public List<BovedaRenglon> consultarids(String[] ids) {
		Map<String, BovedaRenglon> mapa = ofy().load().type(BovedaRenglon.class).ids(ids);
		List<BovedaRenglon> lista = new ArrayList<BovedaRenglon>();
		Iterator it = mapa.entrySet().iterator();

	    for (Entry<String, BovedaRenglon> e: mapa.entrySet()) {
	        lista.add(e.getValue());
	    }

		return lista;

	}

	@Override
	public void eliminar(List<BovedaRenglon> lista) {
		ofy().delete().entities(lista).now();
	}

}
