package com.finkok.facturacion.validation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validate", propOrder = {
    "xml",
    "username",
    "password"
})
@XmlRootElement(name = "validate")
public class Validate {

	  @XmlElementRef(name = "xml", namespace = "http://facturacion.finkok.com/validation", type = JAXBElement.class, required = false)
	    protected JAXBElement<byte[]> xml;
	    @XmlElementRef(name = "username", namespace = "http://facturacion.finkok.com/validation", type = JAXBElement.class, required = false)
	    protected JAXBElement<String> username;
	    @XmlElementRef(name = "password", namespace = "http://facturacion.finkok.com/validation", type = JAXBElement.class, required = false)
	    protected JAXBElement<String> password;

	    /**
	     * Obtiene el valor de la propiedad xml.
	     * 
	     * @return
	     *     possible object is
	     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
	     *     
	     */
	    public JAXBElement<byte[]> getXml() {
	        return xml;
	    }

	    /**
	     * Define el valor de la propiedad xml.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
	     *     
	     */
	    public void setXml(JAXBElement<byte[]> value) {
	        this.xml = value;
	    }

	    /**
	     * Obtiene el valor de la propiedad username.
	     * 
	     * @return
	     *     possible object is
	     *     {@link JAXBElement }{@code <}{@link String }{@code >}
	     *     
	     */
	    public JAXBElement<String> getUsername() {
	        return username;
	    }

	    /**
	     * Define el valor de la propiedad username.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link JAXBElement }{@code <}{@link String }{@code >}
	     *     
	     */
	    public void setUsername(JAXBElement<String> value) {
	        this.username = value;
	    }

	    /**
	     * Obtiene el valor de la propiedad password.
	     * 
	     * @return
	     *     possible object is
	     *     {@link JAXBElement }{@code <}{@link String }{@code >}
	     *     
	     */
	    public JAXBElement<String> getPassword() {
	        return password;
	    }

	    /**
	     * Define el valor de la propiedad password.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link JAXBElement }{@code <}{@link String }{@code >}
	     *     
	     */
	    public void setPassword(JAXBElement<String> value) {
	        this.password = value;
	    }

}
