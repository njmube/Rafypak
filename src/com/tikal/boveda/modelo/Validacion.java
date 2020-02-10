package com.tikal.boveda.modelo;

import java.util.Date;

public class Validacion {
	public boolean validado;
	private Date fecha;
	private String observaciones;
	
	
	public Validacion() { }
	
	public Validacion(Date fecha, String obs, boolean valido){
		this.fecha=fecha;
		this.observaciones= obs;
		this.validado=valido;
	}
	
	public boolean isValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
