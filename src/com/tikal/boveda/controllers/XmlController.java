package com.tikal.boveda.controllers;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.boveda.dao.BovedaRenglonDao;
import com.tikal.boveda.dao.DatosXmlDao;
import com.tikal.boveda.dao.FacturaBovedaDAO;
import com.tikal.boveda.modelo.BovedaRenglon;
import com.tikal.boveda.modelo.DatosXml;
import com.tikal.boveda.modelo.FacturaBoveda;
import com.tikal.boveda.modelo.Validacion;
import com.tikal.cacao.boveda.model.UsuarioProveedor;
import com.tikal.cacao.dao.DatosDAO;
import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.dao.sql.RegimenFiscalDAO;
import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.dao.sql.UsoDeCFDIDAO;
import com.tikal.cacao.model.Perfil;
import com.tikal.cacao.model.Usuario;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.model.orm.RegimenFiscal;
import com.tikal.cacao.model.orm.TipoDeComprobante;
import com.tikal.cacao.model.orm.UsoDeCFDI;
//import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Complemento;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto.Impuestos.Retenciones;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.springController.ServicioSesion;
//import com.tikal.cacao.util.ReadXML;
import com.tikal.unoconnections.exception.DatosTxtException;
import com.tikal.unoconnections.tralix.Datos;

import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

//import com.tikal.cacao.sat.timbrefiscaldigital.TimbreFiscalDigital;
import com.tikal.cacao.util.*;
import com.tikal.boveda.reporte.ReporteXls;
@Controller

@RequestMapping(value = { "/xml"})
public class XmlController {
	
	
	@Autowired
	PerfilDAO perfilimp;

	@Autowired
	UsuarioDAO<UsuarioProveedor> usuariodao;
	
	@Autowired
	UsuarioDAO<Usuario> usuarioimp;
	
	
	@Autowired
	private DatosXmlDao datosXmlDao;
	
	@Autowired
	private FacturaBovedaDAO facturaBovedaDao;
	
	
	
	@Autowired
	private BovedaRenglonDao bovedaRenglonDao;
	
	@Autowired
	@Qualifier("usoDeCfdiDAOH")
	private UsoDeCFDIDAO usoDeCFDIDAO;

	@Autowired
	@Qualifier("regimenFiscalDAOH")
	private RegimenFiscalDAO regimenFiscalDAO;

	@Autowired
	@Qualifier("formaDePagoDAOH")
	private SimpleHibernateDAO<FormaDePago> formaDePagoDAO;
	
	
	
	@RequestMapping(value={"/prueba"},method = RequestMethod.GET)	
	   
	   public void prueba(HttpServletResponse response, HttpServletRequest request) throws IOException {
		   response.getWriter().println("Prueba del mètodo Venta prueba"); 

	    }

		
	@RequestMapping(value={"/leer2/{userName}"},method = RequestMethod.POST, consumes="application/json", produces="application/json")	
	
	   public void guardar(HttpServletResponse response, HttpServletRequest request, @RequestBody String json, @PathVariable String userName) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		System.out.println("json:"+json);
		String[] args = json.split("CADENADEESCAPE");
		Boolean valido =false;
			
		
		System.out.println("argumentos:"+args[0]);
			List<Datos> lista= new ArrayList<Datos>();
			for(String arg:args){
				System.out.println("argu:"+arg);
	 			try {
	 				//valido=ReadXML(arg);
	 				Datos datos = new Datos(arg);
	 	 			//datos.setRfcEmisor(proveedor);
	 				String[] rengs = arg.split(" ");
	 			//	System.out.println("Datos:"+ar.getTotal());
	 				System.out.println("Datos:"+datos.getFormaPago());
	 				System.out.println("Datos:"+datos.getFolio());
	 				System.out.println("Datos:"+datos.getRFC());
	 	 			lista.add(datos);
	 			} catch (DatosTxtException e) {
	 				Datos datosConExcepetion = new Datos();
	 			//	datosConExcepetion.setRfcEmisor(proveedor);
	 				datosConExcepetion.setError(e.getMessage());
	 				datosConExcepetion.setPausada(true);  
	 				lista.add(datosConExcepetion);
	 			}
	 			
	 		} 
	 		//datosdao.guardar(lista);	 		
	 		response.getWriter().print(JsonConvertidor.toJson(lista));
	    }
	
	///////////////////////////////////////////////////////////////// con rutas
	
	@RequestMapping(value={"/leer/{ordenCompra}/{userName}"},method = RequestMethod.POST, consumes="application/json", produces="application/json")	

	   public void leer2(HttpServletResponse response, HttpServletRequest request, @RequestBody String json, @PathVariable String ordenCompra, @PathVariable String userName) throws IOException, JAXBException {
	//	UsuarioProveedor up= usuariodao.consultarUsuario(userName);
		
			//if (up.getNombre()!=null) {
				AsignadorDeCharset.asignar(request, response);
				System.out.println("json:"+json);
				System.out.println("username:"+userName);
				String[] args = json.split("CADENADEESCAPE");
				Boolean valido =false;
				UsuarioProveedor up= usuariodao.consultarUsuario(userName);
				int dias= up.getDiasCredito();	
				
				System.out.println("argumentos:"+args[0]);
					List<List<DatosXml>> lista= new ArrayList<List<DatosXml>>();
					for(String arg:args){
						System.out.println("argu:"+arg);
			 			//try {
							//ReadXML rxml= new ReadXML();
			 				//valido=rxml.ReadXML(arg);,
							List<DatosXml> concepto= new ArrayList<DatosXml>();
			 				concepto=cargarXml(facturaBovedaDao, bovedaRenglonDao, usoDeCFDIDAO,formaDePagoDAO,ordenCompra, arg, dias, userName);
			 				
			 				//com.tikal.cacao.sat.cfd33.Comprobante c=unmarshallCFDI33XML(arg);	 				
			 				
			 				lista.add(concepto);
			 				
			 		}
		
				
					response.getWriter().println(JsonConvertidor.toJson(lista));
//			} else {
//				response.sendError(403);
//			}

	    }

	 @RequestMapping(value = { "/findAllConceptos" }, method = RequestMethod.GET, produces = "application/json")
		public void findAll(HttpServletResponse response, HttpServletRequest request) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<DatosXml> lista = datosXmlDao.findAll();
			if (lista == null) {
				lista = new ArrayList<DatosXml>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	
	 
	 @RequestMapping(value = { "/paginasRfc/{rfc}" }, method = RequestMethod.GET, produces = "application/json")
		public void cuentaPagRfc(HttpServletResponse response, HttpServletRequest request, @PathVariable String rfc) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			int num = facturaBovedaDao.getPaginasRfc(rfc);			
			response.getWriter().println(JsonConvertidor.toJson(num));
		}
	 
	 @RequestMapping(value = { "/paginas" }, method = RequestMethod.GET, produces = "application/json")
		public void cuentaPagRfc(HttpServletResponse response, HttpServletRequest request) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			int num = facturaBovedaDao.getPaginas();			
			response.getWriter().println(JsonConvertidor.toJson(num));
		}
	 
	 @RequestMapping(value = { "/getAllFac/{pag}" }, method = RequestMethod.GET, produces = "application/json")
		public void findFB(HttpServletResponse response, HttpServletRequest request, @PathVariable int pag) throws IOException {
		AsignadorDeCharset.asignar(request, response);
			List<FacturaBoveda> lista = facturaBovedaDao.consutarTodas(pag);
			if (lista == null) {
				lista = new ArrayList<FacturaBoveda>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	
	 
	 @RequestMapping(value = { "/getFacByProv/{rfcProveedor}/{pag}" }, method = RequestMethod.GET, produces = "application/json")
		public void findFB(HttpServletResponse response, HttpServletRequest request, @PathVariable String rfcProveedor, @PathVariable int pag) throws IOException {
		AsignadorDeCharset.asignar(request, response);
		 System.out.println("si entra "+rfcProveedor);
			List<FacturaBoveda> lista = facturaBovedaDao.getFacByProv(rfcProveedor, pag);
			if (lista == null) {
				lista = new ArrayList<FacturaBoveda>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	 
	 @RequestMapping(value = { "/consultFac/{uuid}" }, method = RequestMethod.GET, produces = "application/json")
		public void consulta(HttpServletResponse response, HttpServletRequest request, @PathVariable String uuid) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			FacturaBoveda fac = facturaBovedaDao.consultar(uuid);
			
			response.getWriter().println(JsonConvertidor.toJson(fac));

		}
	
	 @RequestMapping(value = { "/consultConceptos/{uuid}" }, method = RequestMethod.GET, produces = "application/json")
		public void consultaC(HttpServletResponse response, HttpServletRequest request, @PathVariable String uuid) throws IOException {
		 
		 
		AsignadorDeCharset.asignar(request, response);
			List<DatosXml> lista = datosXmlDao.getByUuid(uuid);
			if (lista == null) {
				lista = new ArrayList<DatosXml>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));
		

		}
	 
	 @RequestMapping(value = { "/deleteFac/{uuid}" }, method = RequestMethod.GET, produces = "application/json")
		public void delete(HttpServletResponse response, HttpServletRequest request, @PathVariable String uuid) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			facturaBovedaDao.eliminar1(uuid);
			
			response.getWriter().println(JsonConvertidor.toJson("ok"));

		}
	 
	 
	 @RequestMapping(value = { "/updateFecha/{uuid}/{userName}/{fecha}" }, method = RequestMethod.GET, produces = "application/json")
		public void delete(HttpServletResponse response, HttpServletRequest request, @PathVariable String uuid, @PathVariable String userName, @PathVariable String fecha) throws IOException {
			AsignadorDeCharset.asignar(request, response);  
			if (ServicioSesion.verificarPermiso(request, usuarioimp, perfilimp, 16)) {
				//String fec=json;
			System.out.println("el fecha"+fecha);
			//System.out.println("el fecha"+fecha.substring(4, 24));
			FacturaBoveda fb= facturaBovedaDao.consultar(uuid);
			
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy"); //HH:mm:ss");
			
			//SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy'T'HH:mm:ss");
		       try {
		            Date date = formatter.parse(fecha);
			        fb.setFechaProgramada( date);
			        facturaBovedaDao.guardar(fb);
		       } catch (ParseException e) {
		            e.printStackTrace();
		        }
				response.getWriter().println(JsonConvertidor.toJson("ok"));

			} else {
				response.sendError(403);
			}	
		}
	 
	 
	 @RequestMapping(value = { "/Permisos/{userName}" }, method = RequestMethod.GET, produces = "application/json")
		public void permiso(HttpServletResponse response, HttpServletRequest request, @PathVariable String userName) throws IOException {
			AsignadorDeCharset.asignar(request, response);  
			String perfil= usuarioimp.consultarUsuario(userName).getPerfil();
			 boolean[]permisos= perfilimp.consultarPerfil(perfil).getPermisos();
			response.getWriter().println(JsonConvertidor.toJson(permisos));

		}
	 
	 
	  public static List<DatosXml> cargarXml(FacturaBovedaDAO facturaBovedaDao, BovedaRenglonDao bovedaRenglonDao,
			  									UsoDeCFDIDAO usoDeCFDIDAO,SimpleHibernateDAO<FormaDePago> formaDePagoDAO, String ordenCompra,			   
			                                String info, int dias, String proveedor) throws FileNotFoundException, JAXBException {
	    	
		  
		  com.tikal.cacao.sat.cfd33.Comprobante root = Util.unmarshallCFDI33XML(info);

		             
		             
		           TimbreFiscalDigital timbre= (TimbreFiscalDigital) root.getComplemento().get(0).getAny().get(0);
		        //   System.out.println("timbre:"+timbre);
		             System.out.println("uuid:"+timbre.getUUID());
		             System.out.println("rfc recep:"+root.getReceptor().getRfc());
		            //FacturaBoveda factura = facturaBovedaDao.consultar("56ca9e67-4382-4e7e-a4f6-4349434cda15");
		           //  Date fec =Util.obtenerFecha(root.getFecha().toString());
		             
		         //  UsoDeCFDI usoCFDIHB = usoDeCFDIDAO.consultarPorId(root.getReceptor().getUsoCFDI().getValor());
		 	//		FormaDePago formaDePago = formaDePagoDAO.consultar(root.getFormaPago().getValor());

		             
		             
		           
		             Date fecha = new Date();
		             fecha.setHours(fecha.getHours()-6);
		             System.out.println("fecha principal:"+root.getFecha());
		             SimpleDateFormat formatoDelTexto = new SimpleDateFormat("MM-dd-yyyy");
		             try{
		            	 
		            	   Date fec = formatoDelTexto.parse(root.getFecha().toString());
			              	Date nueva = fec;
			        		nueva.setHours(nueva.getHours()-6);
			                  FacturaBoveda fb= new FacturaBoveda(timbre.getUUID()," ",root.getEmisor().getNombre() , root.getReceptor().getNombre(),nueva, root.getSello(), null);
		                
		                      
		                  Validacion rafypack= new Validacion(fec,"Sin Observaciones", true); 
		                  Validacion compras= new Validacion(null,"", false); 
				             Validacion cxp= new Validacion(null,"", false); 
				             fb.setRfcEmisor(root.getEmisor().getRfc());
				             fb.setValRafipack(validaRfc(root.getReceptor().getRfc()));
				             
				             if(fb.getValRafipack()==true){
				            	 fb.setEstatusXml("Verificado Rafypak");
				            	 fb.setValRafipack(true);
				             }else{
				            	 
				            	 fb.setEstatusXml("Verificado Sat");
				            	 rafypack= new Validacion(fec,"Sin Observaciones", false); 
				            	 fb.setValRafipack(false);
				             }
				             fb.setValCompras(compras);
				             fb.setValCxCobrar(cxp);
				             fb.setFechaBoveda(fecha);
				             fb.setOrdenCompra(ordenCompra); 
				             fb.setTotal(root.getTotal().toString());
				             fb.setMoneda(root.getMoneda().getValor());
				             fb.setRfcReceptor(root.getReceptor().getRfc());
				             fb.setProveedor(proveedor);
				             fb.setFolio(root.getFolio());
				             fb.setSerie(root.getSerie());
				            
				        //     fb.setFormaPago(formaDePago.getDescripcion());
				         //    fb.setUsoCfdi(usoCFDIHB.getDescripcion());
				            
				             Date fecP = calcularFechaPago(fecha, dias);
				         	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy"); //HH:mm:ss");
				         	//format.parse(fecP.toString());
				         	System.out.println(new SimpleDateFormat("dd-MM-yyyy").format(fecP));
				             
				          //  System.out.println("fecha de pago ksdbvsbdvbsj::::"+format.parse(fecP.toString()));
				            System.out.println("dia de fecha de pago::::"+fecP);
				             fb.setFechaProgramada(fecP);
				             fb.setNombreEmisor(root.getEmisor().getNombre());
				             System.out.println("uuid:"+timbre.getUUID());
				            facturaBovedaDao.guardar(fb);
				            BovedaRenglon b= new BovedaRenglon(fb, root);
				            bovedaRenglonDao.guardar(b);
		             } catch (ParseException ex) {

		            	 		ex.printStackTrace();

		             }
		          
		                         
		            
		             List<DatosXml> list= new ArrayList<DatosXml>();
		             
		             
		        for(int x=0; x<root.getConceptos().getConcepto().size(); x++){
		        	Integer importe=root.getConceptos().getConcepto().get(x).getImporte().intValue();
		            System.out.println(importe);          
		            
		            DatosXml xml= new DatosXml();
		            xml.setCantidad(root.getConceptos().getConcepto().get(x).getCantidad().intValue());		           
		            xml.setDescripcion(root.getConceptos().getConcepto().get(x).getDescripcion());
		          
		            xml.setFechaXml(root.getFecha().toString());  
		            xml.setFolio(root.getFolio());
		            xml.setFormaPago(root.getFormaPago().getValor());
		            xml.setTotal(root.getTotal().doubleValue());
		            xml.setValorUnitario(root.getConceptos().getConcepto().get(x).getValorUnitario().toString());
		            xml.setImporte( importe);
		            xml.setRfcProveedor(root.getEmisor().getRfc());
		            xml.setRfcEmpresa(root.getReceptor().getRfc());
		            xml.setSerie(root.getSerie());
		            xml.setFolio(root.getFolio());
		            xml.setMoneda(root.getMoneda().getValor());
		            xml.setMetodoPago(root.getMetodoPago().getValor().toString());
		            xml.setLugarExp(root.getLugarExpedicion().getValor());
		            xml.setUuid(timbre.getUUID());
		            
		            
		            
		           
		            list.add(xml);
		             ofy().save().entity(xml).now();

		           
		        }
		       
		    
		        return list; 
	        
	  } 
	      
	    
		
		@RequestMapping(value={"/validarCompras/{uuid}"},method = RequestMethod.POST, consumes="application/json", produces="application/json")	
		
		   public void validaC(HttpServletResponse response, HttpServletRequest request, @RequestBody String json, @PathVariable String uuid) throws IOException {
		//	System.out.println("uvalidar compras...yyyyy");
			if (ServicioSesion.verificarPermiso(request, usuarioimp, perfilimp, 15)) {
				
					AsignadorDeCharset.asignar(request, response);
					
					Validacion v= (Validacion) JsonConvertidor.fromJson(json, Validacion.class);
					
					FacturaBoveda f= facturaBovedaDao.consultar(uuid) ;
				
					Date fecha = new Date();
					f.setValCompras(v);
					f.setEstatusXml("Verificado Almacen");
					Validacion va= f.getValCompras();
					va.validado=true;
					f.ValidaCompras(f.getValCompras());
					facturaBovedaDao.guardar(f);
					
					BovedaRenglon b= bovedaRenglonDao.consultar(uuid);
					b.setCompras(true);
			        bovedaRenglonDao.guardar(b);
					
			} else {
				response.sendError(403);
			}
		}
 
		@RequestMapping(value={"/validarCxP/{uuid}"},method = RequestMethod.POST, consumes="application/json", produces="application/json")	
		
		
		   public void validaCxP(HttpServletResponse response, HttpServletRequest request, @RequestBody String json,  @PathVariable String uuid) throws IOException {
				if (ServicioSesion.verificarPermiso(request, usuarioimp, perfilimp, 16)) {
					AsignadorDeCharset.asignar(request, response);
					Validacion v= (Validacion) JsonConvertidor.fromJson(json, Validacion.class);
					FacturaBoveda f= facturaBovedaDao.consultar(uuid) ;
					 Date fecha = new Date();
					 f.setValCxCobrar(v);
					 f.setEstatusXml("Verificado Cuentas por Pagar");
						Validacion vcxp= f.getValCompras();
						vcxp.validado=true;
					 f.ValidaCxP(f.getValCxCobrar());
					facturaBovedaDao.guardar(f); 
					BovedaRenglon b=bovedaRenglonDao.consultar(uuid);
					b.setCxp(true);
					b.setEstatus("Verificado");
			        bovedaRenglonDao.guardar(b);
			        String mail=usuariodao.consultarUsuario(f.getProveedor()).getEmail();
			        System.out.println("mailllll"+mail);
			        enviarEmail(mail, f.getUuid());
				} else {
					response.sendError(403);
				}	
		}
		
		
		 @RequestMapping(value = { "/generaReporteXls/{inicio}/{fin}/{tipo}/{dato}/{user}" }, method = RequestMethod.GET, produces = "application/vnd.ms-excel" )
		 
			public void generaxls_(HttpServletResponse response, HttpServletRequest request,
					@PathVariable String inicio,@PathVariable String fin,@PathVariable String tipo, @PathVariable String dato, @PathVariable String user) throws IOException, ParseException{
			 
		//	  if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 4, sessionDao,userName)){
			 AsignadorDeCharset.asignar(request, response);
//					  System.out.println("si entra: con inicio"+inicio);
//					  System.out.println("si entra: con fin"+fin);
//					  System.out.println("si entra: con tipo"+tipo);
//					  System.out.println("si entra: con dato"+dato);

						SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy"); //HH:mm:ss");
					//	try {
							Date datei = formatter.parse(inicio);
							System.out.println("formatter inicio"+datei);
							Date datef = formatter.parse(fin);
							//System.out.println("formatter fin"+datef);
							Calendar c = Calendar.getInstance();
							c.setTime(datef);
							c.add(Calendar.DATE, 1);
							datef = c.getTime();
							List<BovedaRenglon> regs = new ArrayList<BovedaRenglon>();
							
							if (tipo.compareTo("periodo")==0){
								
								regs = bovedaRenglonDao.consultarPeriodo( datei, datef);
								 System.out.println("regs:"+regs );
							}else{
								 
								if (tipo.compareTo("rfc")==0){
									 
									 regs = bovedaRenglonDao.consultarRfc(dato, datei, datef);
								}
								if (tipo.compareTo("fecha")==0){
									
									SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy"); //HH:mm:ss");
									//	try {
											Date fec = format.parse(dato);
									 regs = bovedaRenglonDao.consultarFecha(fec, datei, datef);
								}
								if (tipo.compareTo("estatus")==0){
									
									 regs = bovedaRenglonDao.consultarEstatus(dato, datei, datef);
								}
							
								
							}
							//List<BovedaRenglon> regs = bovedaRenglonDao.consultar(rfcEmisor, datei, datef);
									if (regs == null) {
										regs = new ArrayList<BovedaRenglon>();
										 System.out.println("No hay registros para ese periodo...");
									}
							
			
							System.out.println("------------existen :"+regs.size()+"envios en total" );
				        System.out.println("empiezo a generar pdf..." );
				   
//				        String perfil=usuarioDao.consultarUsuario(userName).getPerfil();
//				    	if (perfil.equals("Administrador") || perfil.equals("SuperAdministrador")){
				    		
				    		ReporteXls rep = new ReporteXls();
					    	rep.setRenglones(regs);
					    	HSSFWorkbook reporteXls = rep.GetReporteXls(datei.toGMTString(),datef.toGMTString(), user);
							reporteXls.write(response.getOutputStream());
//				    	}else{
				    	//	ReporteSucursal repS = new ReporteSucursal();
//				    		repS.setRenglones(regs);
//				    		HSSFWorkbook reporteSucursal = repS.GetReporteSucursalXls(datei.toGMTString(),datef.toGMTString(), total);
//				    		reporteSucursal.write(response.getOutputStream());
//				    	}
				    
//			  }else{
//					response.sendError(403);
//			   }	
			}
		  
		  public static  Date calcularFechaPago(Date fechaBoveda,int diasCredito){
			 // Date fechaPago=new Date();
			  
			  
			  Calendar calendar = Calendar.getInstance();
			  	
			        calendar.setTime(fechaBoveda); // Configuramos la fecha que se recibe			
			        calendar.add(Calendar.DAY_OF_YEAR, diasCredito);  // numero de días a añadir, o restar en caso de días<007			   
			        System.out.println("fechaProg:"+calendar.getTime());
			      
			        String nd= diaSemana(calendar.getTime());
			        System.out.println("dia semana:::"+nd);
			      
			        int ciclos=0;
			        switch(nd){
			        	case "Lunes":ciclos=1;break; 
			        	case "Martes":ciclos=0;break; 
			        	case "Miercoles":ciclos=6;break; 
			        	case "Jueves":ciclos=5;break; 
			        	case "Viernes":ciclos=4;break; 
			        	case "Sabado":ciclos=3;break; 
			        	case "Domingo":ciclos=2;break; 
			        	default:System.out.println("no entro a ningun case");
			        	}
			        System.out.println("ciclos"+ciclos);
			        for (int i=0; i<ciclos; i++){
			        	System.out.println("numero de dia"+Calendar.DAY_OF_WEEK);			        	
				        	//if(diaSemana(calendar.getTime()).equals("Martes")){		        		
				        	//	System.out.println("entra al for) y al if");
				        		calendar.add(Calendar.DAY_OF_YEAR, 1);
				        		System.out.println("nuevo dia mas uno:"+calendar.getTime());
				        		//System.out.println("dia :"+dia);
				        //	}		 
			        		
			        } 
				        
			        return calendar.getTime(); 
			  
			  
			  // fechaPago;
		  }
		
		  
		  public static String diaSemana(Date fecha) {
			  
				// Creamos una instancia del calendario
			  
			  Calendar now = Calendar.getInstance();
			  	
			        now.setTime(fecha); // Configuramos la fecha que se recibe				
		 
				System.out.println("Fecha actual : " + (now.get(Calendar.MONTH) + 1)
								+ "-"
								+ now.get(Calendar.DATE)
								+ "-"
								+ now.get(Calendar.YEAR));
		 
				// Array con los dias de la semana
				String[] strDays = new String[]{
								"Domingo",
								"Lunes",
								"Martes",
								"Miercoles",
								"Jueves",
								"Viernes",
								"Sabado"};
		 
				// El dia de la semana inicia en el 1 mientras que el array empieza en el 0
				System.out.println("El dia de la semana de esta fecha es : " + strDays[now.get(Calendar.DAY_OF_WEEK) - 1]);
				return strDays[now.get(Calendar.DAY_OF_WEEK) - 1];
			}
		  
		public static boolean validaRfc(String receptor){
			boolean val=true;
			System.out.println("rfc Receptor:"+receptor);
			if (receptor.equals("RAF0111132U5")){
				return val;
			}else{
				val=false;
			return val;
			}
			//return true; 
		}
		

		public void enviarEmail(String email, String uuid) {						
						facturaBovedaDao.enviarEmail(email, uuid);
	
		}
		
		
		
}
