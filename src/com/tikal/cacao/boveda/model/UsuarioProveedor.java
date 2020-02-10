package com.tikal.cacao.boveda.model;

import com.googlecode.objectify.annotation.Entity;
import com.tikal.cacao.model.Usuario;

@Entity
public class UsuarioProveedor extends Usuario{
	
	private String rfc;
	private String nombre;
	private String rfcEmpresa;
	private int diasCredito;
	private String tipo;
	private String cp;
	private String formaDePago;
	private String metodoDePago;
	private double iva;
	private double retiva;
	private double retisr;
	private String contacto;
	
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRfcEmpresa() {
		return rfcEmpresa;
	}
	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}
	public int getDiasCredito() {
		return diasCredito;
	}
	public void setDiasCredito(int diasCredito) {
		this.diasCredito = diasCredito;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getFormaDePago() {
		return formaDePago;
	}
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}
	public String getMetodoDePago() {
		return metodoDePago;
	}
	public void setMetodoDePago(String metodoDePago) {
		this.metodoDePago = metodoDePago;
	}
	public double getIva() {
		return iva;
	}
	public void setIva(double iva) {
		this.iva = iva;
	}
	public double getRetiva() {
		return retiva;
	}
	public void setRetiva(double retiva) {
		this.retiva = retiva;
	}
	public double getRetisr() {
		return retisr;
	}
	public void setRetisr(double retisr) {
		this.retisr = retisr;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	
	
}
