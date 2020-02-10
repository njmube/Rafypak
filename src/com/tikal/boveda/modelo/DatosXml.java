package com.tikal.boveda.modelo;

import java.util.Date;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class DatosXml {
	
	@Id	private Long id;
	
	@Index private String rfcProveedor;
	@Index private String rfcEmpresa; /// rfc del receptor en los xml
	
	private String metodoPago;
	private String formaPago;
	private Integer cantidad;
	private String descripcion;
	private double precio;
	private double importe;
	private String impuestos;
	private String retenciones;
	private double total;
	private double iva;
	private double subtotal;
	@Index private String fechaXml;
	private Date fechaBoveda;
	private String estatusXml;
	private String serie;
	private String folio;
	private String moneda;
	private String lugarExp;
	private boolean timbrado;
	private String valorUnitario;
	@Index	private String uuid;
	private String ordenCompra;
	
	
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getRfcProveedor() {
		return rfcProveedor;
	}
	public void setRfcProveedor(String rfcProveedor) {
		this.rfcProveedor = rfcProveedor;
	}
	public String getRfcEmpresa() {
		return rfcEmpresa;
	}
	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}
	public String getMetodoPago() {
		return metodoPago;
	}
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public String getImpuestos() {
		return impuestos;
	}
	public void setImpuestos(String impuestos) {
		this.impuestos = impuestos;
	}
	public String getRetenciones() {
		return retenciones;
	}
	public void setRetenciones(String retenciones) {
		this.retenciones = retenciones;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getIva() {
		return iva;
	}
	public void setIva(double iva) {
		this.iva = iva;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public String getFechaXml() {
		return fechaXml;
	}
	public void setFechaXml(String fechaXml) {
		this.fechaXml = fechaXml;
	}
	public Date getFechaBoveda() {
		return fechaBoveda;
	}
	public void setFechaBoveda(Date fechaBoveda) {
		this.fechaBoveda = fechaBoveda;
	}
	public String getEstatusXml() {
		return estatusXml;
	}
	public void setEstatusXml(String estatusXml) {
		this.estatusXml = estatusXml;
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
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getLugarExp() {
		return lugarExp;
	}
	public void setLugarExp(String lugarExp) {
		this.lugarExp = lugarExp;
	}
	public boolean isTimbrado() {
		return timbrado;
	}
	public void setTimbrado(boolean timbrado) {
		this.timbrado = timbrado;
	}
	public String getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	
		
	
	
	

}
