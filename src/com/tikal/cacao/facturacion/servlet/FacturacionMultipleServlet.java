package com.tikal.cacao.facturacion.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tikal.cacao.dao.BitacoraDAO;
import com.tikal.cacao.dao.ConceptosDAO;
import com.tikal.cacao.dao.DatosDAO;
import com.tikal.cacao.dao.DomicilioCEDAO;
import com.tikal.cacao.dao.EmisorDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.dao.ImagenDAO;
import com.tikal.cacao.dao.ReporteRenglonDAO;
import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.factura.FormatoFecha;
import com.tikal.cacao.factura.ws.WSClient;
import com.tikal.cacao.factura.ws.WSClientCfdi33;
import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.sat.cfd.catalogos.C_Pais;
import com.tikal.cacao.sat.cfd.catalogos.C_TipoRelacion;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_ClaveUnidad;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_CodigoPostal;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_FormaDePago;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Impuesto;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_MetodoDePago;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Moneda;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_RegimenFiscal;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_TipoDeComprobante;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_TipoFactor;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_UsoCFDI;
import com.tikal.cacao.sat.cfd.catalogos.dyn.comext.C_FraccionArancelaria;
//import com.tikal.cacao.sat.cfd.catalogos.dyn.comext.C_FraccionArancelaria;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Addenda;
import com.tikal.cacao.sat.cfd33.Comprobante.CfdiRelacionados;
import com.tikal.cacao.sat.cfd33.Comprobante.CfdiRelacionados.CfdiRelacionado;
import com.tikal.cacao.sat.cfd33.Comprobante.Complemento;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto.Impuestos.Traslados;
import com.tikal.cacao.service.FacturaVTTService;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteVO;
import com.tikal.cacao.util.Util;
import com.tikal.unoconnections.tralix.AddendaFactory;
import com.tikal.unoconnections.tralix.Datos;
import com.tikal.unoconnections.tralix.DatosConcepto;
import com.tikal.unoconnections.tralix.DatosConceptoFraccion;

import mx.gob.sat.comercioexterior11.ComercioExterior;
import mx.gob.sat.comercioexterior11.ComercioExterior.Mercancias.Mercancia;
import mx.gob.sat.sitio_internet.cfd.catalogos.comext.CClavePedimento;
import mx.gob.sat.sitio_internet.cfd.catalogos.comext.CINCOTERM;
import mx.gob.sat.sitio_internet.cfd.catalogos.comext.CTipoOperacion;
import mx.gob.sat.sitio_internet.cfd.catalogos.comext.CUnidadAduana;

public class FacturacionMultipleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private WSClient client;

	@Autowired
	private WSClientCfdi33 client33;

	@Autowired
	EmpresasDAO empresasDAO;

	@Autowired
	EmisorDAO emisorDAO;

	@Autowired
	SerialDAO serialDAO;

	@Autowired
	FacturaDAO facturaDAO;

	@Autowired
	BitacoraDAO bitacoradao;

	@Autowired
	ImagenDAO imagenDAO;

	@Autowired
	ReporteRenglonDAO repRenglonDAO;

	@Autowired
	FacturaVTTService servicioFact;

	@Autowired
	DatosDAO datosdao;

	@Autowired
	ConceptosDAO conceptodao;

	@Autowired
	DomicilioCEDAO domdao;

	@Autowired
	@Qualifier("formaDePagoDAOH")
	SimpleHibernateDAO<FormaDePago> formaDePagoDAO;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Datos> lista = datosdao.todos();
		for (Datos fr : lista) {
			String respuesta = this.timbrarDatos(fr, request.getSession());
			if (respuesta.compareTo("¡La factura se timbró con éxito!") == 0) {
				datosdao.elimiar(fr);
			} else {
				
					fr.setPausada(true);
					fr.setError(respuesta);
					datosdao.guardar(fr);
				
			}
		}
		response.getWriter().print("OK");

	}

	private String timbrarDatos(Datos f, HttpSession sesion) {

		int tipo = this.tipoComprobante(f.getSerie());
		Comprobante c = new Comprobante();
		Comprobante.Emisor emisor = new Comprobante.Emisor();
		Empresa empresa = empresasDAO.consultar(f.getRfcEmisor());
		emisor.setNombre(empresa.getNombre());
		emisor.setRfc(empresa.getRFC());
		emisor.setRegimenFiscal(new C_RegimenFiscal("601"));
		c.setEmisor(emisor);
		Comprobante.Receptor receptor = new Comprobante.Receptor();
		if (tipo != 1) {
			c.setTipoDeComprobante(new C_TipoDeComprobante("I"));
		} else {
			c.setTipoDeComprobante(new C_TipoDeComprobante("E"));
			CfdiRelacionados relacionados= new Comprobante.CfdiRelacionados();
			relacionados.setTipoRelacion(C_TipoRelacion.VALUE_1);
			CfdiRelacionado relacionado= new Comprobante.CfdiRelacionados.CfdiRelacionado();
			relacionado.setUUID(f.getUuidRelacionado());
			relacionados.getCfdiRelacionado().add(relacionado);
			c.setCfdiRelacionados(relacionados);
		}
		
		if (f.getRFC().compareTo("XEXX010101000") == 0) {
			receptor.setNumRegIdTrib(f.getNumRegIdTrib());
			tipo = 2;
		}
		receptor.setNombre(f.getNombreReceptor());
		receptor.setRfc(f.getRFC());
		receptor.setUsoCFDI(new C_UsoCFDI(f.getUsoCFDI()));
		receptor.setResidenciaFiscal(new com.tikal.cacao.sat.cfd.catalogos.dyn.C_Pais(f.getPais()));

		// receptor.setDomicilio(recept.getDomicilio());
		c.setReceptor(receptor);
		c.setVersion("3.3");
		Date fechaTxt = Util.obtenerFecha(f.getFecha_Hora(), new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss"));
		if (fechaTxt != null) {
			c.setFecha(Util.getXMLDate(fechaTxt, FormatoFecha.COMPROBANTE));
		} else {
			c.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));
		}
		

		c.setLugarExpedicion(new C_CodigoPostal(empresa.getDireccion().getCodigoPostal()));
		
		c.setFormaPago(new C_FormaDePago(this.regresaClaveFormaDePago(f.getMetodoPago().toUpperCase())));
		c.setMetodoPago(new C_MetodoDePago("PPD"));
		//if (f.getFormaPago().toLowerCase().contains("sola")) {
		if (f.getCondPago().toLowerCase().contains("sola")) {
			c.setMetodoPago(new C_MetodoDePago("PUE"));
		}

		// c.setMoneda(new C_Moneda("MXN"));
		c.setMoneda(new C_Moneda(f.getMoneda()));
		if (!c.getMoneda().getValor().contentEquals("MXN")) {
			c.setTipoCambio(new BigDecimal(Double.toString(f.getTipoCambio())));
			
		}

		com.tikal.cacao.sat.cfd33.Comprobante.Impuestos imps = new com.tikal.cacao.sat.cfd33.Comprobante.Impuestos();
		Traslados t = new Traslados();
		com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Traslados.Traslado tras = new com.tikal.cacao.sat.cfd33.Comprobante.Impuestos.Traslados.Traslado();
//		if(f.getMoneda().equals("USD")){}
//		else{
			tras.setImpuesto(new C_Impuesto("IVA"));
			if (tipo == 0) {
				tras.setTasaOCuota(new BigDecimal(0.16).setScale(2, RoundingMode.HALF_UP));
			} else {
				tras.setTasaOCuota(new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP));
			}
	//	}
		
		
	//	tras.setTipoFactor(new C_TipoFactor("Tasa"));
//		if(f.getMoneda().equals("USD")){
//			tras.setTipoFactor(new C_TipoFactor("Excento"));
//			tras.setImpuesto(new C_Impuesto("003"));
//		}else{
			tras.setTipoFactor(new C_TipoFactor("Tasa"));
			tras.setImpuesto(new C_Impuesto("002"));
	//	}
		Conceptos conceptos = new Conceptos();
		com.tikal.cacao.model.Conceptos cps = conceptodao.consultar(f.getRfcEmisor());
		Map<String, com.tikal.cacao.model.Concepto> mapa = new HashMap<String, com.tikal.cacao.model.Concepto>();
		for (com.tikal.cacao.model.Concepto conce : cps.getConceptos()) {
			mapa.put(conce.getNoIdentificacion(), conce);
		}
		double total = 0;
		for (DatosConcepto d : f.getConceptos()) {
			Comprobante.Conceptos.Concepto con = new Comprobante.Conceptos.Concepto();
			com.tikal.cacao.model.Concepto conce = mapa.get(d.getClave());

			if(conce==null){
				return "No se encontró el concepto: "+d.getClave();
			}
			
			if(f.getRFC().compareTo("MME080729180")==0){
				con.setCantidad(Util.redondearBigD(new BigDecimal(d.getCantidad()).setScale(3, RoundingMode.HALF_UP), 3));
			}else{
				con.setCantidad(Util.redondearBigD(new BigDecimal(d.getCantidad()).setScale(2, RoundingMode.HALF_UP), 2));
			}
			con.setClaveProdServ(conce.getClaveProdServ());
			con.setUnidad(d.getUnidadMed());
			con.setClaveUnidad(new C_ClaveUnidad(conce.getClaveUnidad()));
			con.setClaveProdServ(conce.getClaveProdServ());
			con.setDescripcion(d.getDescripcion());
			con.setValorUnitario(Util.redondearBigD(new BigDecimal(d.getValorUnit()), 2));
			con.setImporte(Util.redondearBigD(new BigDecimal(d.getImporte()), 2));
			con.setNoIdentificacion(d.getClave());
			
			if (conce.getUnidadAduana() != null) {
				if(d.getUnidadAduana()==null){
					d.setUnidadAduana(conce.getUnidadAduana());
				}
			} else if (f.getRFC().compareTo("XEXX010101000") == 0) {
				return "No se encontró la unidad de aduana en el concepto " + con.getNoIdentificacion();
			}
			

			Comprobante.Conceptos.Concepto.Impuestos impuestos = new Comprobante.Conceptos.Concepto.Impuestos();
			Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
			Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
			traslado.setBase(con.getImporte());
			if (tipo == 0 && !d.getClave().contains("SERVICIO_R")) {
				traslado.setImporte(Util.redondearBigD(new BigDecimal(con.getImporte().doubleValue() * 0.16), 2)); // cambio de decimales 6 a 2
				traslado.setTasaOCuota(new BigDecimal(f.getTasa() / 100));
			} else {
//				if (f.getMoneda().equals("USD")){}
//				else{}
				traslado.setImporte(Util.redondearBigD(new BigDecimal(con.getImporte().floatValue() * (f.getTasa() / 100f)), 2));
				traslado.setTasaOCuota(new BigDecimal(f.getTasa() / 100));
			}
		
//			if(f.getMoneda().equals("USD")){
//				traslado.setImpuesto(new C_Impuesto("003"));
//				traslado.setTipoFactor(new C_TipoFactor("Excento"));
//			}else{
				traslado.setImpuesto(new C_Impuesto("002"));
				traslado.setTipoFactor(new C_TipoFactor("Tasa"));
		//}
			//traslado.setImpuesto(new C_Impuesto("002"));
		//	traslado.setTipoFactor(new C_TipoFactor("Tasa"));
			traslados.getTraslado().add(traslado);
			impuestos.setTraslados(traslados);
			

			total += d.getValorUnit() * d.getCantidad();
			
			f.setSubtotal(total);
			con.setImpuestos(impuestos);
			conceptos.getConcepto().add(con);
			// t.getTraslado().add(traslado);

		}

		if(f.getDescuento()!=null){
			double descuento= Double.parseDouble(f.getDescuento());
			c.setDescuento(Util.redondearBigD(new BigDecimal(descuento),2));
			double porcientoDes= descuento/total;
			
			for(Concepto con: conceptos.getConcepto()){
				double descuentin = con.getImporte().doubleValue()* porcientoDes;
				descuento = descuento - descuentin;
				con.setDescuento(Util.redondearBigD(new BigDecimal(descuentin), 2));
				con.getImpuestos().getTraslados().getTraslado().get(0).setBase(Util.redondearBigD(new BigDecimal(con.getImporte().doubleValue() -descuentin), 2));
				con.getImpuestos().getTraslados().getTraslado().get(0).setImporte(Util.redondearBigD(new BigDecimal((con.getImporte().doubleValue() -descuentin)* 0.16), 2));
			}
			if(descuento > 0){
				Concepto con= conceptos.getConcepto().get(conceptos.getConcepto().size()-1);
				double descuentin= con.getDescuento().doubleValue();
				descuentin+= descuento;
				con.setDescuento(Util.redondearBigD(new BigDecimal(descuentin), 2));
				con.getImpuestos().getTraslados().getTraslado().get(0).setBase(Util.redondearBigD(new BigDecimal(con.getImporte().doubleValue() -descuentin), 2));
				con.getImpuestos().getTraslados().getTraslado().get(0).setImporte(Util.redondearBigD(new BigDecimal((con.getImporte().doubleValue() -descuentin)* 0.16), 2));
			}
			
//			total -= descuento;
//			traslado.setBase(Util.redondearBigD(new BigDecimal(con.getImporte().doubleValue() -descuento), 2));
//			traslado.setImporte(Util.redondearBigD(new BigDecimal((con.getImporte().doubleValue() -descuento)* 0.16), 2));
//			con.setDescuento(Util.redondearBigD(new BigDecimal(descuento),2));
			
//			f.setSubtotal(total);
			CfdiRelacionados cr= new Comprobante.CfdiRelacionados();
			cr.setTipoRelacion(C_TipoRelacion.CFDI_POR_APLICACION_DE_ANTICIPO);
			CfdiRelacionado crel= new Comprobante.CfdiRelacionados.CfdiRelacionado();
			crel.setUUID(f.getUuidRelacionado());
			cr.getCfdiRelacionado().add(crel);
			c.setCfdiRelacionados(cr);
		}
		

		c.setConceptos(conceptos);
		
		Comprobante.Impuestos.Traslados trask = new Comprobante.Impuestos.Traslados();
		Comprobante.Impuestos.Traslados.Traslado trasl = new Comprobante.Impuestos.Traslados.Traslado();
		BigDecimal importeTras = new BigDecimal( Util.redondear( f.getImp() ) );
		importeTras = Util.redondearBigD(importeTras, 2);
		trasl.setImporte(Util.redondearBigD(importeTras, 2));
		
//		if(f.getMoneda().equals("USD")){
//			trasl.setImpuesto(new C_Impuesto("003"));
//			trasl.setTipoFactor(new C_TipoFactor("Excento"));
//		}else{
			trasl.setImpuesto(new C_Impuesto("002"));
			trasl.setTipoFactor(new C_TipoFactor("Tasa"));
	//	}
		
	//	trasl.setImpuesto(new C_Impuesto("002"));
	//	trasl.setTipoFactor(new C_TipoFactor("Tasa"));
	//	if (tipo == 0) {
			trasl.setTasaOCuota( Util.redondearBigD( new BigDecimal(f.getTasa() / 100), 6));
	//	} else {
//			if (f.getMoneda().equals("USD")){}
//			else{
	//			trasl.setTasaOCuota( Util.redondearBigD( new BigDecimal(f.getTasa() / 100), 6));
			//}
	//	}

		trask.getTraslado().add(trasl);

		imps.setTraslados(trask);
		imps.setTotalImpuestosTrasladados(Util.redondearBigD(new BigDecimal(f.getImpTrasladados()), 2));
		c.setImpuestos(imps);

		c.setSerie(f.getSerie());
		c.setFolio(f.getFolio());

		c.setTotal(Util.redondearBigD(new BigDecimal(f.getTotal()), 2));
		c.setSubTotal(Util.redondearBigD(new BigDecimal(f.getSubtotal()), 2));

		BigDecimal sumaTotal = c.getSubTotal().add( c.getImpuestos().getTotalImpuestosTrasladados() );
		if(f.getDescuento()!=null){
			sumaTotal=sumaTotal.subtract(c.getDescuento());
		}
		if (c.getTotal().compareTo(sumaTotal) != 0) {
			c.setTotal(sumaTotal);
		}
		
		if (tipo == 2) {
			this.agregarComercioExterno(c, f);
		}

		
		ComprobanteVO vo = new ComprobanteVO();
		vo.setComprobante(c);

		FacturaVTT factura = new FacturaVTT();
		FacturaVTT.DatosExtra extra = factura.getDatosExtra();
		extra.setIdCliente(f.getIdCFD());
		extra.setIdShip(f.getIdShip());
		extra.setCondicionesPago(f.getCondPago());
		extra.setImporteichon(f.getTotalLetra());
		extra.setNuestroPedido(f.getnPedido());
		extra.setRepresentante(f.getRepVentas());
		extra.setShipCalle(f.getShipCalle());
		extra.setShipLocalidad(f.getShipColonia());
		extra.setShipPais(f.getShipPais());
		extra.setShipPostCode(f.getShipEstado());
		extra.setSuPedido(f.getsPedido());
		extra.setViaEmbarque(f.getViaEmbarque());
		extra.setSoldTo(f.getDireccion());
		
		RegistroBitacora b= new RegistroBitacora();
		b.setEvento("Generando addenda: "+ Util.marshallComprobante33(c));
		b.setFecha(new Date());
		this.bitacoradao.addReg(b);
		
		this.addAddenda(c, f);

		//Timbrado del comprobante
		String respuesta = servicioFact.timbrar(vo, sesion, true, extra);
		RegistroBitacora bit = new RegistroBitacora();
		bit.setEvento(f.getSerie()+f.getFolio()+ " "+respuesta);
		bit.setTipo("Operacional");
		bit.setUsuario("Proceso Back");
		bit.setFecha(new Date());
		bitacoradao.addReg(bit);
		return respuesta;

	}

	private String regresaClaveFormaDePago(String formaDePago) {
		List<FormaDePago> listaFormaPago = formaDePagoDAO.consultarTodos();
		for (FormaDePago formaDePagoHB : listaFormaPago) {
			if ( formaDePago.toUpperCase().contains( formaDePagoHB.getDescripcion().toUpperCase() ) ) {
				return formaDePagoHB.getId();
			}
		}
		switch (formaDePago.toUpperCase()) {
		case "EFECTIVO":
			return "01";
		case "CHEQUE NOMINATIVO":
			return "02";
		case "TRANSFERENCIA ELECTRÓNICA DE FONDOS":
			return "03";
		case "TARJETA DE CRÉDITO":
			return "04";
		case "TARJETA DE DÉBITO":
			return "28";
		case "POR DEFINIR":
			return "99";
		}
		return "";
	}

	private void agregarComercioExterno(Comprobante c, Datos d) {
		mx.gob.sat.comercioexterior11.ObjectFactory of = new mx.gob.sat.comercioexterior11.ObjectFactory();
		ComercioExterior com = of.createComercioExterior();

		if(d.getIncoterm()!=null){
			com.setIncoterm(CINCOTERM.valueOf(d.getIncoterm()));
		}
		com.setNumeroExportadorConfiable(d.getNumExportConfiable());
		com.setObservaciones(d.getObservaciones());
		if (d.getSubdiv() != null) {
			com.setSubdivision(Integer.parseInt(d.getSubdiv()));
		}
		com.setTipoCambioUSD(Util.redondearBigD(new BigDecimal(d.getTipoCambio()), 4));
		com.setTotalUSD(Util.redondearBigD(new BigDecimal(d.getTotalUSD()), 2));
		if (d.getNumCertOrigen() == null) {
			com.setCertificadoOrigen(0);
		} else {
			if (d.getCertOrigen() != null) {
				com.setNumCertificadoOrigen(d.getNumCertOrigen());
				com.setCertificadoOrigen(Integer.parseInt(d.getCertOrigen()));
			} else {
				com.setCertificadoOrigen(0);
			}
		}
		com.setClaveDePedimento(CClavePedimento.A_1);

		mx.gob.sat.comercioexterior11.ComercioExterior.Emisor emisor = of.createComercioExteriorEmisor();
		mx.gob.sat.comercioexterior11.ComercioExterior.Emisor.Domicilio domicilio = of
				.createComercioExteriorEmisorDomicilio();

		emisor.setCurp(d.getCURP());

		emisor.setDomicilio(
				(mx.gob.sat.comercioexterior11.ComercioExterior.Emisor.Domicilio) domdao.get(d.getRfcEmisor()));
		com.setEmisor(emisor);
		

		mx.gob.sat.comercioexterior11.ComercioExterior.Receptor receptor = of.createComercioExteriorReceptor();
		mx.gob.sat.comercioexterior11.ComercioExterior.Receptor.Domicilio domrec = of
				.createComercioExteriorReceptorDomicilio();

		Direccion direccion = d.getDireccion();
		domrec.setCalle(direccion.getCalle());
		domrec.setCodigoPostal(direccion.getCodigoPostal());
		domrec.setColonia(direccion.getColonia());
		domrec.setEstado(direccion.getEstado());
		domrec.setLocalidad(direccion.getColonia());
		domrec.setNumeroExterior(direccion.getNumExterior());
		domrec.setPais(C_Pais.valueOf(d.getPais()));

		// receptor.setNumRegIdTrib(d.getNumRegIdTrib());
		receptor.setDomicilio(domrec);
		com.setReceptor(receptor);

		mx.gob.sat.comercioexterior11.ComercioExterior.Mercancias mercs = of.createComercioExteriorMercancias();

		List<mx.gob.sat.comercioexterior11.ComercioExterior.Mercancias.Mercancia> lista = mercs.getMercancia();

		List<Concepto> listac = c.getConceptos().getConcepto();

		for (int i = 0; i < d.getConceptos().size(); i++) {

			Concepto conc = listac.get(i);
		
				
			
				DatosConcepto de = d.getConceptos().get(i);
				
				
				Mercancia m = of.createComercioExteriorMercanciasMercancia();
				if(de.getCantidadAduana()==0){
					m.setCantidadAduana(Util.redondearBigD(conc.getCantidad(), 2));
					m.setValorUnitarioAduana(Util.redondearBigD(conc.getValorUnitario(), 2));
				}else{
					m.setCantidadAduana(Util.redondearBigD(new BigDecimal(de.getCantidadAduana()), 2));
					m.setValorUnitarioAduana(Util.redondearBigD(new BigDecimal(de.getValorUnitAduana()), 2));
				}
	//			esta parte se descomenta para comercio exterior
//				if(!de.getUnidadAduana().equals("99")){
//					//C_FraccionArancelaria fa=new C_fraccionarancelaria(de.getfraccionarancelaria());
//					m.setFraccionArancelaria(new C_FraccionArancelaria(de.getFraccionArancelaria()));
//				}
			
				m.setFraccionArancelaria(new C_FraccionArancelaria(de.getFraccionArancelaria()));//para comercio exterior se comenta
				m.setNoIdentificacion(conc.getNoIdentificacion());
				String cua = de.getUnidadMed();
				if (cua.length() == 1) {
					cua = "0" + cua;
				}
				m.setUnidadAduana(CUnidadAduana.fromValue(de.getUnidadAduana()));
				m.setValorDolares(Util.redondearBigD(conc.getImporte(), 2));
				lista.add(m);
				// m.setCantidadAduana(con.getCantidad());
				// m.setFraccionArancelaria();
			
		}
		com.setMercancias(mercs);
		com.setVersion("1.1");
		com.setTipoOperacion(CTipoOperacion.VALUE_1);
		com.setSubdivision(0);
		Complemento compl = new Comprobante.Complemento();
		compl.getAny().add(com);
		c.getComplemento().add(compl);
	}

	private void addAddenda(Comprobante c, Datos d) {
		Addenda ad = new Comprobante.Addenda();
		Object adden = AddendaFactory.getAdenda(d.getRFC(), d, c);
		ad.getAny().add(adden);
		if (adden != null) {
			c.setAddenda(ad);
		}
	}

	private int tipoComprobante(String formaDePago) {
		switch (formaDePago.toUpperCase()) {
		case "ATL":
			return 0;
		case "ATNC":
			return 1;

		}
		return 2;
	}
}
