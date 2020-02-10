package com.tikal.boveda.modelo;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.sat.cfd.Comprobante;

/**
 * @author Tikal
 *
 */
@Entity
public class FacturaBoveda {

	@Id
	private String uuid;
	
	@Ignore
	private Comprobante cfdi; // el Datastore no permite guardar este objeto porque tiene atributos BigDecimal que no es un tipo soportado
	
	private String cfdiXML;
	
	private String acuseCancelacionXML;
	
	@Index
	private String rfcEmisor;
	
	private String nombreEmisor;
	
	private String nombreReceptor;
	
	/**	<p>La fecha de certificaci&oacuten del la factura cuando ha sido timbrada.</p>
	 * <p>Si la factura no ha sido timbrada entonces este campo debe tener la fecha de 
	 *    emisi&oacute;n.</p> */
	@Index
	private Date fechaCertificacion;
	
	private Date fechaCancelacion;
	
	private String selloCancelacion;
	
	private String selloDigital;
	
	private byte[] codigoQR;
	
	/**Comentarios que se ingresan en el pdf de la factura */
	private String comentarios;
	
	private Estatus estatus;
	
	@Index private String estatusXml;
	
	private boolean timbrado;
	
	private Date fechaXml;
	
	@Index private Date fechaBoveda;
	
	private boolean valRafipack;
	Validacion valCompras;
	Validacion valCxCobrar;
	
	protected Date fechaProgramada;
	protected String moneda;
	
	 private String total;
	
	 private String rfcReceptor;
	 
	 @Index String proveedor;
	 
	 private String serie;
	 private String folio;
	private String formaPago;
	private String usoCfdi;
	private String ordenCompra;
	
	/**
	 * <p>Constructor vac&iacute;o de una {@code Factura}. <p>
	 * <p>&Eacute;ste constructor es utilizado por <em>objectify</em></p>
	 */
	public FacturaBoveda() { }
	
	public FacturaBoveda(String uuid, String cfdi, String rfcEmisor , String nombreReceptor, Date fecha, String sello, byte[] codigoQR) {
		this.uuid = uuid;
		this.cfdiXML = cfdi;
		//this.cfdi = cfdi;
		this.rfcEmisor = rfcEmisor;
		this.nombreReceptor = nombreReceptor;
		this.fechaCertificacion = fecha;
		this.selloDigital = sello;
		this.codigoQR = codigoQR;
		this.setEstatus();
	}

	
	public String getUuid() {
		return uuid;
	}
	
	/**
	 * @return the cfdi
	 */
	public Comprobante getCfdi() {
		return cfdi;
	}

	/**
	 * @param cfdi the cfdi to set
	 */
	public void setCfdi(Comprobante cfdi) {
		this.cfdi = cfdi;
	}

	/**
	 * @return the cfdiXML
	 */
	public String getCfdiXML() {
		return cfdiXML;
	}

	/**
	 * @param cfdiXML the cfdiXML to set
	 */
	public void setCfdiXML(String cfdiXML) {
		this.cfdiXML = cfdiXML;
	}

	/**
	 * @return the acuseCancelacionXML
	 */
	public String getAcuseCancelacionXML() {
		return acuseCancelacionXML;
	}

	/**
	 * @param acuseCancelacionXML the acuseCancelacionXML to set
	 */
	public void setAcuseCancelacionXML(String acuseCancelacionXML) {
		this.acuseCancelacionXML = acuseCancelacionXML;
	}

	/**
	 * @return the rfcEmisor
	 */
	public String getRfcEmisor() {
		return rfcEmisor;
	}

	/**
	 * @param rfcEmisor the rfcEmisor to set
	 */
	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	/**
	 * @return the nombreReceptor
	 */
	public String getNombreReceptor() {
		return nombreReceptor;
	}

	/**
	 * @param nombreReceptor the nombreReceptor to set
	 */
	public void setNombreReceptor(String nombreReceptor) {
		this.nombreReceptor = nombreReceptor;
	}

	/**
	 * @return the fecha
	 */
	public Date getFechaCertificacion() {
		return fechaCertificacion;
	}
	
	/**
	 * @param fecha the fecha  to set
	 */
	public void setFechaCertificacion (Date fecha) {
		this.fechaCertificacion = fecha;
	}

	/**
	 * @return the fechaCancelacion
	 */
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	/**
	 * @param fechaCancelacion the fechaCancelacion to set
	 */
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	/**
	 * @return the selloCancelacion
	 */
	public String getSelloCancelacion() {
		return selloCancelacion;
	}

	/**
	 * @param selloCancelacion the selloCancelacion to set
	 */
	public void setSelloCancelacion(String selloCancelacion) {
		this.selloCancelacion = selloCancelacion;
	}

	/**
	 * @return the selloDigital
	 */
	public String getSelloDigital() {
		return selloDigital;
	}

	/**
	 * @param selloDigital the selloDigital to set
	 */
	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

	/**
	 * @return the codigoQR
	 */
	public byte[] getCodigoQR() {
		return codigoQR;
	}

	/**
	 * @param codigoQR the codigoQR to set
	 */
	public void setCodigoQR(byte[] codigoQR) {
		this.codigoQR = codigoQR;
	}

	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the estatus
	 */
	public Estatus getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}
	
	private void setEstatus() {
		boolean camposNoTimbrado = (
				this.selloDigital == null && this.codigoQR == null);
		
		boolean camposSiTimbrado = (
			this.selloDigital != null && this.codigoQR != null);
		
		boolean camposGenerado = (this.cfdiXML != null && this.rfcEmisor != null);
		
		if ( camposNoTimbrado && camposGenerado ) {
			this.estatus = Estatus.GENERADO;
		} else if (camposSiTimbrado) {
			this.estatus = Estatus.TIMBRADO;
		}
	}

	public String getEstatusXml() {
		return estatusXml;
	}

	public void setEstatusXml(String estatusXml) {
		this.estatusXml = estatusXml;
	}

	public boolean isTimbrado() {
		return timbrado;
	}

	public void setTimbrado(boolean timbrado) {
		this.timbrado = timbrado;
	}

	public Date getFechaXml() {
		return fechaXml;
	}

	public void setFechaXml(Date fechaXml) {
		this.fechaXml = fechaXml;
	}

	public Date getFechaBoveda() {
		return fechaBoveda;
	}

	public void setFechaBoveda(Date fechaBoveda) {
		this.fechaBoveda = fechaBoveda;
	}

	public boolean getValRafipack() {
		return valRafipack;
	}

	public void setValRafipack(boolean b) {
		this.valRafipack = b;
	}

	public Validacion getValCompras() {
		return valCompras;
	}

	public void setValCompras(Validacion valCompras) {
		this.valCompras = valCompras;
		//this.valCompras.validado=
	}

	public void ValidaCompras(Validacion valCompras) {
		this.valCompras = valCompras;
		this.valCompras.validado=true;
		System.out.println("trueeeee");
	}
	
	public void ValidaCxP(Validacion valCxCobrar) {
		this.valCxCobrar = valCxCobrar;
		this.valCxCobrar.validado=true;
		System.out.println("hddjdh");
	}
	public Validacion getValCxCobrar() {
		return valCxCobrar;
	}

	public void setValCxCobrar(Validacion valCxCobrar) {
		this.valCxCobrar = valCxCobrar;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getNombreEmisor() {
		return nombreEmisor;
	}

	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRfcReceptor() {
		return rfcReceptor;
	}

	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getUsoCfdi() {
		return usoCfdi;
	}

	public void setUsoCfdi(String usoCfdi) {
		this.usoCfdi = usoCfdi;
	}

	public String getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	
	
}