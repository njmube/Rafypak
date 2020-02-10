package com.tikal.boveda.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.factura.VersionCFDI;
import com.tikal.cacao.model.Factura;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.util.Util;

@Entity
public class BovedaRenglon {
	@Id
	protected String uuid;
	@Index
	protected Date fechaCertificacion; //antes fecha  y tipo String //Probar reporte de Isra
	
	@Ignore
	protected String strFechaCertificacion;
	@Index protected Date fechaBoveda;
	@Index
	protected String rfcEmisor;
	protected String emisor;
	@Index
	protected String serie;
	protected String folio;
	protected String nombreRec;
	@Index
	protected String rfcReceptor; //antes rfcRec
	protected String lugar;
	
	protected String subtotal;
	protected String iva;
	protected String total;
	@Index protected String estatus; //antes status
	
	protected Boolean rafy;
	protected Boolean compras;
	protected Boolean cxp;
	protected Date fechaProgramada;
	protected String moneda;
	
	protected boolean tieneComplementoPago;
	
	protected String ordenCompra;

	
	@Index
	protected boolean pagado;
	
	@Index 
	protected VersionCFDI version;
	
	public BovedaRenglon(){}
	
	public BovedaRenglon(FacturaBoveda f,com.tikal.cacao.sat.cfd33.Comprobante c){
	    //SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    
		//this.fechaCertificacion=formatter.format(f.getFechaCertificacion());
		this.fechaBoveda=f.getFechaBoveda();
	   this.fechaCertificacion = f.getFechaCertificacion();
		//Comprobante c = (Comprobante) Util.unmarshallXML(f.getCfdiXML());
		this.rfcEmisor = c.getEmisor().getRfc();
		this.emisor=c.getEmisor().getNombre();
		this.serie=c.getSerie();
		this.folio=c.getFolio();
		this.nombreRec=c.getReceptor().getNombre();
		this.rfcReceptor=c.getReceptor().getRfc();
		//this.lugar= c.getLugarExpedicion();
		this.uuid=f.getUuid();
		this.subtotal=c.getSubTotal()+"";
		this.iva=c.getImpuestos().getTotalImpuestosTrasladados()+"";
		this.total=c.getTotal()+"";
		this.estatus="";
		if(f.getEstatusXml()!=null){
			this.estatus=f.getEstatusXml();
		}
		this.rafy=f.getValRafipack();
		this.compras=f.valCompras.isValidado();
		this.cxp=f.valCxCobrar.isValidado();
		this.moneda=f.getMoneda();
		this.fechaProgramada=f.getFechaProgramada();
		this.ordenCompra=f.getOrdenCompra();
		
	}
	
//	public BovedaRenglon(FacturaVTT f) {
//		this.fechaCertificacion = f.getFechaCertificacion();
//		com.tikal.cacao.sat.cfd33.Comprobante cfdi = Util.unmarshallCFDI33XML(f.getCfdiXML());
//		this.rfcEmisor = cfdi.getEmisor().getRfc();
//		this.emisor = cfdi.getEmisor().getNombre();
//		this.serie = cfdi.getSerie();
//		this.folio = cfdi.getFolio();
//		this.nombreRec = cfdi.getReceptor().getNombre();
//		this.rfcReceptor = cfdi.getReceptor().getRfc();
//		this.lugar = cfdi.getLugarExpedicion().getValor();
//		this.uuid = f.getUuid();
//		this.subtotal=cfdi.getSubTotal()+"";
//		this.iva=cfdi.getImpuestos().getTotalImpuestosTrasladados()+"";
//		this.total=cfdi.getTotal()+"";
//		this.estatus="";
//		if(f.getEstatus()!=null){
//			this.estatus=f.getEstatus().name();
//		}
//		this.version = VersionCFDI.V3_3;
//		
//		if (cfdi.getMetodoPago().getValor().contentEquals("PPD")) {
//			this.pagado = false;
//		} else if (cfdi.getMetodoPago().getValor().contentEquals("PUE")
//				&& !cfdi.getFormaPago().getValor().contentEquals("99")) {
//			this.pagado = true;
//		}
//		this.tieneComplementoPago=false;
//	}
//	
	public String getStrFechaCertificacion() {
		return strFechaCertificacion;
	}
	
	public void setStrFechaCertificacion(String strFechaCertificacion) {
		this.strFechaCertificacion = strFechaCertificacion;
	}
	
	public Date getFecha() {
		return fechaCertificacion;
	}
	public void setFecha(Date fecha) {
		this.fechaCertificacion = fecha;
	}
	public String getRfcEmisor() {
		return rfcEmisor;
	}
	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNombreRec() {
		return nombreRec;
	}
	public void setNombreRec(String nombreRec) {
		this.nombreRec = nombreRec;
	}
	public String getRfcRec() {
		return rfcReceptor;
	}
	public void setRfcRec(String rfcRec) {
		this.rfcReceptor = rfcRec;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getFolioFiscal() {
		return uuid;
	}
	public void setFolioFiscal(String folioFiscal) {
		this.uuid = folioFiscal;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getStatus() {
		return estatus;
	}
	public void setStatus(String status) {
		this.estatus = status;
	}

	public void llenarRenglon(HSSFRow r){
		for(int i=0;i<12;i++){
			r.createCell(i);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy"); //HH:mm:ss");
		
//	       try {
//	            Date fcert = formatter.parse(fecha.substring(4, 24));
//	            Date fcert = formatter.parse(fecha.substring(4, 24));
		r.getCell(0).setCellValue(this.emisor);
		r.getCell(1).setCellValue(this.rfcEmisor);
		r.getCell(2).setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(this.fechaProgramada));
		r.getCell(3).setCellValue(this.serie+" "+this.folio);
		r.getCell(4).setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(this.fechaCertificacion));
		r.getCell(5).setCellValue(this.subtotal);
		r.getCell(6).setCellValue(this.iva);
		r.getCell(7).setCellValue(this.total);
		r.getCell(8).setCellValue(this.moneda);
		r.getCell(9).setCellValue(this.uuid);
		r.getCell(10).setCellValue(this.estatus);
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public boolean isTieneComplementoPago() {
		return tieneComplementoPago;
	}

	public void setTieneComplementoPago(boolean tieneComplementoPago) {
		this.tieneComplementoPago = tieneComplementoPago;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getFechaCertificacion() {
		return fechaCertificacion;
	}

	public void setFechaCertificacion(Date fechaCertificacion) {
		this.fechaCertificacion = fechaCertificacion;
	}

	public Date getFechaBoveda() {
		return fechaBoveda;
	}

	public void setFechaBoveda(Date fechaBoveda) {
		this.fechaBoveda = fechaBoveda;
	}

	public String getRfcReceptor() {
		return rfcReceptor;
	}

	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Boolean getRafy() {
		return rafy;
	}

	public void setRafy(Boolean rafy) {
		this.rafy = rafy;
	}

	public Boolean getCompras() {
		return compras;
	}

	public void setCompras(Boolean compras) {
		this.compras = compras;
	}

	public Boolean getCxp() {
		return cxp;
	}

	public void setCxp(Boolean cxp) {
		this.cxp = cxp;
	}

	public Date getFechaProgramada() {
		return fechaProgramada;
	}

	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public VersionCFDI getVersion() {
		return version;
	}

	public void setVersion(VersionCFDI version) {
		this.version = version;
	}

	public String getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	
	
}