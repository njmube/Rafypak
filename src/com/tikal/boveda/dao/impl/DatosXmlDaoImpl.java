package com.tikal.boveda.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.boveda.dao.DatosXmlDao;
import com.tikal.boveda.modelo.DatosXml;


@Service("datosXmlDao")
public class DatosXmlDaoImpl implements DatosXmlDao {
		
	@Override
		public void save(DatosXml d) {    	
			 System.out.println("salvando...DatosXmL");
	        ofy().save().entity(d).now();
	    }

	    
	    public void delete(DatosXml d) {
	    	 System.out.println("si esta en daoimpl eliminando DatosXml"+d);
	        ofy().delete().entity(d).now();
	        System.out.println("eliminando...DatosXmL");
	    }


		public void update(DatosXml d) {
		   System.out.print("DatosXml :"+d.getId());
		   DatosXml old = this.consult(d.getId());
		System.out.print("old:"+old);
			if (old != null) {
				old.setCantidad(d.getCantidad());
				old.setEstatusXml(d.getEstatusXml());
				old.setFechaBoveda(d.getFechaBoveda());
				old.setFechaXml(d.getFechaXml());
				old.setFolio(d.getFolio());
				old.setFormaPago(d.getFormaPago());
				old.setImporte(d.getImporte());
				old.setImpuestos(d.getImpuestos());
				old.setIva(d.getIva());
				old.setLugarExp(d.getLugarExp());
				old.setMetodoPago(d.getMetodoPago());
				old.setMoneda(d.getMoneda());
				old.setPrecio(d.getPrecio());
				old.setRetenciones(d.getRetenciones());
				old.setRfcEmpresa(d.getRfcEmpresa());
				old.setRfcProveedor(d.getRfcProveedor());
				old.setSerie(d.getSerie());
				old.setSubtotal(d.getSubtotal());
				old.setTotal(d.getTotal());
				old.setTimbrado(d.isTimbrado());
				
				
			}

				ofy().save().entity(old);
	   }

	    

		public DatosXml consult(Long id) {
		   System.out.println("si esta en daoimpl consultandoDatosXml.."+id);
	      return ofy().load().type(DatosXml.class).id(id).now();
			
		}


		public List<DatosXml> getByProveedor(String rfc){
			  System.out.println("si esta en daoimpl getByProvee.."+rfc);
		      return ofy().load().type(DatosXml.class).filter("rfcProveedor",rfc).list();
			 
		}
	
				
		public List<DatosXml> getByUuid(String uuid){
			  System.out.println("si esta en daoimpl getByUuid.."+uuid);
		      return ofy().load().type(DatosXml.class).filter("uuid",uuid).list();
			 
		}
		
		
	   
		public List<DatosXml> findAll() {
			return ofy().load().type(DatosXml.class).list();
		}


		

		
		
		

	}
