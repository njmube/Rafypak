package com.finkok.facturacion.validation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

import views.core.soap.services.apps.AcuseSAT;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateResult", propOrder = {
    "xml",
    "sello",
    "selloSat",
    "error",
    "sat"
})
public class ValidateResult {
	@XmlElementRef(name = "xml", namespace = "apps.services.soap.core.views", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> xml;
    @XmlElementRef(name = "sello", namespace = "apps.services.soap.core.views", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> sello;
    @XmlElementRef(name = "sello_sat", namespace = "apps.services.soap.core.views", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> selloSat;
    @XmlElementRef(name = "error", namespace = "apps.services.soap.core.views", type = JAXBElement.class, required = false)
    protected JAXBElement<String> error;
    @XmlElementRef(name = "sat", namespace = "apps.services.soap.core.views", type = JAXBElement.class, required = false)
    protected JAXBElement<AcuseSAT> sat;
    
    
	public JAXBElement<Boolean> getXml() {
		return xml;
	}
	public void setXml(JAXBElement<Boolean> xml) {
		this.xml = xml;
	}
	public JAXBElement<Boolean> getSello() {
		return sello;
	}
	public void setSello(JAXBElement<Boolean> sello) {
		this.sello = sello;
	}
	public JAXBElement<Boolean> getSelloSat() {
		return selloSat;
	}
	public void setSelloSat(JAXBElement<Boolean> selloSat) {
		this.selloSat = selloSat;
	}
	public JAXBElement<String> getError() {
		return error;
	}
	public void setError(JAXBElement<String> error) {
		this.error = error;
	}
	public JAXBElement<AcuseSAT> getSat() {
		return sat;
	}
	public void setSat(JAXBElement<AcuseSAT> sat) {
		this.sat = sat;
	}
    
}
