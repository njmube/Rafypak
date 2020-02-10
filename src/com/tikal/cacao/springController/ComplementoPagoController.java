package com.tikal.cacao.springController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.dao.ComplementoRenglonDAO;
import com.tikal.cacao.dao.PagosFacturaVttDAO;
import com.tikal.cacao.factura.RespuestaWebServicePersonalizada;
import com.tikal.cacao.factura.wsfinkok.FinkokClient;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.PagosFacturaVTT;
import com.tikal.cacao.model.Usuario;
import com.tikal.cacao.reporte.ComplementoRenglon;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_ClaveUnidad;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Moneda;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_TipoDeComprobante;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_UsoCFDI;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.Comprobante.Complemento;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd33.Comprobante.Receptor;
import com.tikal.cacao.sat.cfd33.ObjectFactoryComprobante33;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.service.PagosFacturaVTTService;
import com.tikal.cacao.service.impl.FacturaVTTServiceImpl;
import com.tikal.cacao.springController.viewObjects.v33.ComprobanteConComplementoPagosVO;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.JsonConvertidor;
import com.tikal.cacao.util.Util;

import mx.gob.sat.pagos.Pagos;
import mx.gob.sat.pagos.Pagos.Pago;

@Controller
@RequestMapping(value={"/complementos"})
public class ComplementoPagoController {
	
	@Autowired
	FacturaVTTServiceImpl fvttservice;
	
	@Autowired
	ComplementoRenglonDAO complementosDAO;
	
	@Autowired
	PagosFacturaVTTService pagoService;
	
	@Autowired
	UsuarioDAO<Usuario> usuarioDAO;
	
	@Autowired
	PerfilDAO perfilDAO;
	
	@Autowired
	PagosFacturaVttDAO pagosFacturaDAO;
	
	@Autowired
	FinkokClient finkok;
	
	@RequestMapping(value={"timbrar"}, method= RequestMethod.POST, consumes="application/json")
	public void timbrar(HttpServletResponse res, HttpServletRequest req, @RequestBody String json) throws UnsupportedEncodingException{
			AsignadorDeCharset.asignar(req, res);
			ComprobanteConComplementoPagosVO cVO=(ComprobanteConComplementoPagosVO) JsonConvertidor.fromJson(json, ComprobanteConComplementoPagosVO.class);
			FacturaVTT factura= fvttservice.consultar(cVO.getUuid());
			Comprobante c= Util.unmarshallCFDI33XML(factura.getCfdiXML());
			c.setSerie(cVO.getSerie().getSerie());
			c= this.prepararComprobante(c);
			
			Pagos complementoPagos= cVO.getComplementoPagos();
			Pago pago=complementoPagos.getPago().get(0);
			pago.getDoctoRelacionado().get(0).setFolio(c.getFolio());
			pago.getDoctoRelacionado().get(0).setSerie(c.getSerie());
			Complemento complemento= new Comprobante.Complemento();
//			c.setFecha(pago.getFechaPago());
			RespuestaWebServicePersonalizada respuesta=	pagoService.timbrar(cVO, c, cVO.getUuid());
			
			
			System.out.println(respuesta.getMensajeRespuesta());
	}
	
	@RequestMapping(value = "/cancelarAck", method = RequestMethod.POST)
	public void cancelarConAcuse(HttpServletRequest req, HttpServletResponse res, @RequestBody String body) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				AsignadorDeCharset.asignar(req, res);
				String[] uuidYrfc = body.split(",");
				String textoRespuesta = pagoService.cancelarAck(uuidYrfc[0], uuidYrfc[1], req.getSession());
				res.getWriter().println(textoRespuesta);
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value={"timbrarManual"}, method= RequestMethod.POST, consumes="application/json")
	public void timbrar2(HttpServletResponse res, HttpServletRequest req, @RequestBody String json) throws IOException{
			AsignadorDeCharset.asignar(req, res);
			ComprobanteConComplementoPagosVO cVO=(ComprobanteConComplementoPagosVO) JsonConvertidor.fromJson(json, ComprobanteConComplementoPagosVO.class);
			
			Comprobante c= this.nuevoComplemento(cVO.getCfdi());
			
			Pagos complementoPagos= cVO.getComplementoPagos();
			
			Pago pago=complementoPagos.getPago().get(0);
			Complemento complemento= new Comprobante.Complemento();
//			c.setFecha(pago.getFechaPago());
			c.setSerie(cVO.getSerie().getSerie());
			RespuestaWebServicePersonalizada respuesta=	pagoService.timbrar(cVO, c, cVO.getUuid());
			
			res.getWriter().print(respuesta.getMensajeRespuesta());
			System.out.print(respuesta);
	}
	
	@RequestMapping(value = "/consultar/{rfc}/{page}", method = RequestMethod.GET)
	public void byrfc(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc,@PathVariable int page) throws IOException {
		AsignadorDeCharset.asignar(req, res);
		List<ComplementoRenglon> listaR = complementosDAO.consultarPag(rfc, page);
		res.getWriter().println(JsonConvertidor.toJson(listaR));
		
	}
	
	@RequestMapping(value = "/obtenerPDF/{uuid}", method = RequestMethod.GET, produces = "application/pdf")
	public void obtenerPDF(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				
				PagosFacturaVTT factura = pagosFacturaDAO.consultarUUID(uuid);
				PdfWriter pdfWriter = pagoService.obtenerPDF(factura, res.getOutputStream());
				if (pdfWriter != null) {
					res.setContentType("Application/Pdf");
					res.getOutputStream().flush();
					res.getOutputStream().close();
				} // FIN IF CUANDO EXISTE LA FACTURA
				
				// SI NO EXISTE LA FACTURA CON EL UUID ESPECIFICADO
				else {
					AsignadorDeCharset.asignar(req, res);
					PrintWriter writer = res.getWriter();
					writer.println(
							"El Número de Folio Fiscal (UUID): ".concat(uuid).concat(" no pertenece a ninguna factura"));
				}
				
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/obtenerXML/{uuid}", method = RequestMethod.GET, produces = "text/xml")
	public void obtenerXML(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		try {
			if (ServicioSesion.verificarPermiso(req, usuarioDAO, perfilDAO, 11)) {
				AsignadorDeCharset.asignar(req, res);
				PagosFacturaVTT factura = pagosFacturaDAO.consultarUUID(uuid);
				PrintWriter writer = res.getWriter();
				
				if (factura != null) {
					res.setContentType("text/xml");
					switch (factura.getEstatus()) {
						case TIMBRADO:
						case GENERADO:
							writer.println(factura.getCfdiXML());
							break;
						case CANCELADO:
							writer.println(factura.getAcuseCancelacionXML());
							break;
					}
				} else {
					writer.println("La factuca con el folio fiscal (uuid) ".concat(uuid).concat(" no existe"));
				}
			} else {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Comprobante nuevoComplemento(Comprobante c){
		ObjectFactoryComprobante33 factory= new ObjectFactoryComprobante33();
		Comprobante aux=factory.createComprobante();
		Conceptos conceptos= c.getConceptos();
		conceptos= factory.createComprobanteConceptos();
		Concepto conceptoPago= factory.createComprobanteConceptosConcepto();
		conceptoPago.setCantidad(new BigDecimal(1));
		conceptoPago.setClaveProdServ("84111506");
		conceptoPago.setClaveUnidad(new C_ClaveUnidad("ACT"));
		conceptoPago.setDescripcion("Pago");
		conceptoPago.setValorUnitario(new BigDecimal(0));
		conceptoPago.setImporte(new BigDecimal(0));
		conceptos.getConcepto().add(conceptoPago);
		aux.setEmisor(c.getEmisor());
		aux.setCertificado(null);
		aux.setTotal(new BigDecimal(0d));
		aux.setSubTotal(new BigDecimal(0d));
		aux.setImpuestos(null);
		aux.setNoCertificado(null);
		aux.setSello(null);
		aux.setConceptos(conceptos);
//		aux.setCondicionesDePago(c.getCondicionesDePago());
		aux.setReceptor(c.getReceptor());
		aux.setFecha(c.getFecha());
		aux.setFolio(c.getFolio());
//		aux.setFormaPago(c.getFormaPago());
		aux.setLugarExpedicion(c.getLugarExpedicion());
//		aux.setMetodoPago(c.getMetodoPago());
		aux.setMoneda(new C_Moneda("XXX"));
		aux.setSerie(c.getSerie());
		aux.setTipoDeComprobante(new C_TipoDeComprobante("P"));
		aux.setVersion("3.3");
		return aux;
	}
	
	private Comprobante prepararComprobante(Comprobante c){
		ObjectFactoryComprobante33 factory= new ObjectFactoryComprobante33();
		Comprobante aux=factory.createComprobante();
		Conceptos conceptos= c.getConceptos();
		conceptos= factory.createComprobanteConceptos();
		Concepto conceptoPago= factory.createComprobanteConceptosConcepto();
		conceptoPago.setCantidad(new BigDecimal(1));
		conceptoPago.setClaveProdServ("84111506");
		conceptoPago.setClaveUnidad(new C_ClaveUnidad("ACT"));
		conceptoPago.setDescripcion("Pago");
		conceptoPago.setValorUnitario(new BigDecimal(0));
		conceptoPago.setImporte(new BigDecimal(0));
		conceptos.getConcepto().add(conceptoPago); 	
		c.setCertificado(null);
		c.setTotal(new BigDecimal(0d));
		c.setSubTotal(new BigDecimal(0d));
		c.setImpuestos(null);
		c.setNoCertificado(null);
		c.setSello(null);
		aux.setConceptos(conceptos);
//		aux.setCondicionesDePago(c.getCondicionesDePago());
		aux.setEmisor(c.getEmisor());
		aux.setFecha(c.getFecha());
		aux.setFolio(c.getFolio());
//		aux.setFormaPago(c.getFormaPago());
		aux.setLugarExpedicion(c.getLugarExpedicion());
//		aux.setMetodoPago(c.getMetodoPago());
		aux.setMoneda(new C_Moneda("XXX"));
		Receptor receptor= c.getReceptor();
		receptor.setUsoCFDI(new C_UsoCFDI("P01"));
		aux.setReceptor(c.getReceptor());
		aux.setSerie(c.getSerie());
		aux.setSubTotal(c.getSubTotal());
		aux.setTipoCambio(c.getTipoCambio());
		aux.setTipoDeComprobante(new C_TipoDeComprobante("P"));
		aux.setTotal(c.getTotal());
		aux.setVersion(c.getVersion());
		
		return aux;
	}
	
	@RequestMapping(value = "/paginas/{rfc}", method = RequestMethod.GET,produces="text/xml")
	public void numpags(HttpServletRequest req, HttpServletResponse res, @PathVariable String rfc) throws IOException {
		int paginas=complementosDAO.pags(rfc);
		res.getWriter().print(paginas);
	}

	
	@RequestMapping(value = "/pruebita", method = RequestMethod.GET,produces="text/xml")
	public void pruebita(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cfdi:Comprobante Version=\"3.3\" Serie=\"co\" Folio=\"82\" Fecha=\"2018-10-15T08:16:21\" Sello=\"S0jTidZ5KTdvj3Vp49ZN/Lu7p4b4ew0Z3dbPvE937AiEWxGC2/I+cU3OIne0FF0wYL5ti3yXxBESPZycJxbh0DEMjY47ycmCp8sskQqa63+x7UPhIWq+H1YxlRUrPHCa3Xzrh/zLP9riM+M+DKB2oNfFA7/HvpBik/BX1r0heQPnasASeI6Lzm5pDKoGCBBS2RX+7p7TW++Pltoethk3utxlqssEomUGPMwyQfi1KjYrttIlNnuq7ToBpeRAw5+6xTgLYe1WgnW0drV8q5+xH49bNFbQBTNnjuClPwh+q0R9p226UFiPfgT1lPUHzBV7c6foNRINOcFAqdki8USI4g==\" NoCertificado=\"00001000000409323161\" Certificado=\"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\" SubTotal=\"0\" Moneda=\"XXX\" Total=\"0\" TipoDeComprobante=\"P\" LugarExpedicion=\"50450\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:pago10=\"http://www.sat.gob.mx/Pagos\" xmlns:ns3=\"http://www.sat.gob.mx/TimbreFiscalDigital\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:Emisor Rfc=\"RAF0111132U5\" Nombre=\"Rafypak S.A de C.V.\" RegimenFiscal=\"601\"/><cfdi:Receptor Rfc=\"SAB730510K44\" Nombre=\"SABRITAS, S. DE R.L. DE C.V.\" UsoCFDI=\"P01\"/><cfdi:Conceptos><cfdi:Concepto ClaveProdServ=\"84111506\" Cantidad=\"1\" ClaveUnidad=\"ACT\" Descripcion=\"Pago\" ValorUnitario=\"0\" Importe=\"0\"/></cfdi:Conceptos><cfdi:Complemento><pago10:Pagos Version=\"1.0\"><pago10:Pago FechaPago=\"2018-09-17T08:16:21\" FormaDePagoP=\"03\" MonedaP=\"MXN\" Monto=\"549845.80\" RfcEmisorCtaOrd=\"BNM840515VB1\" NomBancoOrdExt=\"BANCO NACIONAL DE MEXICO SA\" CtaOrdenante=\"0029165033\" RfcEmisorCtaBen=\"BNM840515VB1\" CtaBeneficiario=\"002426082481272028\"><pago10:DoctoRelacionado IdDocumento=\"546af62d-7c74-498a-a470-971ebd18b1b9\" Serie=\"ATL\" Folio=\"16864\" MonedaDR=\"MXN\" MetodoDePagoDR=\"PPD\" NumParcialidad=\"1\" ImpSaldoAnt=\"549845.80\" ImpPagado=\"549845.80\" ImpSaldoInsoluto=\"0\"/></pago10:Pago></pago10:Pagos><ns3:TimbreFiscalDigital Version=\"1.1\" UUID=\"89910b05-6f8e-4e63-9ee1-7dd801c657a8\" FechaTimbrado=\"2018-10-17T08:21:29\" RfcProvCertif=\"PFE140312IW8\" SelloCFD=\"S0jTidZ5KTdvj3Vp49ZN/Lu7p4b4ew0Z3dbPvE937AiEWxGC2/I+cU3OIne0FF0wYL5ti3yXxBESPZycJxbh0DEMjY47ycmCp8sskQqa63+x7UPhIWq+H1YxlRUrPHCa3Xzrh/zLP9riM+M+DKB2oNfFA7/HvpBik/BX1r0heQPnasASeI6Lzm5pDKoGCBBS2RX+7p7TW++Pltoethk3utxlqssEomUGPMwyQfi1KjYrttIlNnuq7ToBpeRAw5+6xTgLYe1WgnW0drV8q5+xH49bNFbQBTNnjuClPwh+q0R9p226UFiPfgT1lPUHzBV7c6foNRINOcFAqdki8USI4g==\" NoCertificadoSAT=\"00001000000306850881\" SelloSAT=\"L5igUGZyyZbReEvnhB0nwmkIJYwQM7aDbUqHZbwH5P9OdAFfCjASIBOVYtltujnSdQuE8Rh76INMYvJ2hkWbHHCE+ad+ESdLda2B3vzjZR+TN/ZaE7hoPM3nrOD22p3PfukyxeF5gFeLEo2Luhnaa6b+RY4Nq1cCZG6TuzS8ZQo=\"/></cfdi:Complemento></cfdi:Comprobante>\r\n"; 
		
		this.finkok.validar(xml);
		
		
	}
}
