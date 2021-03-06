//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.30 at 12:24:31 PM CST 
//


package mx.com.fact.schema.pepsico;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documento">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="referencia">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="46"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="serie">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="10"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="folio">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="20"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;pattern value="[0-9]+"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="folioUUID">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;length value="36"/>
 *                       &lt;pattern value="[a-f0-9A-F]{8}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{12}"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="tipoDoc" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;totalDigits value="1"/>
 *                       &lt;enumeration value="1"/>
 *                       &lt;enumeration value="2"/>
 *                       &lt;enumeration value="3"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Proveedor">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="idProveedor" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="10"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Recepciones" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Recepcion" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Concepto" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="cantidad" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;whiteSpace value="collapse"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="unidad" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           &lt;whiteSpace value="collapse"/>
 *                                           &lt;minLength value="1"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="descripcion" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           &lt;minLength value="1"/>
 *                                           &lt;whiteSpace value="collapse"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="valorUnitario" use="required" type="{http://www.fact.com.mx/schema/pepsico}t_Importe" />
 *                                     &lt;attribute name="importe" use="required" type="{http://www.fact.com.mx/schema/pepsico}t_Importe" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="idRecepcion">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;whiteSpace value="collapse"/>
 *                                 &lt;minLength value="1"/>
 *                                 &lt;maxLength value="10"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="tipo" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="AddendaPCO" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="2.0" />
 *       &lt;attribute name="idPedido" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;whiteSpace value="collapse"/>
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="10"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="idSolicitudPago">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;whiteSpace value="collapse"/>
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="10"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documento",
    "proveedor",
    "recepciones"
})
@XmlRootElement(name = "RequestCFD")
public class RequestCFD {

    @XmlElement(name = "Documento", required = true)
    protected RequestCFD.Documento documento;
    @XmlElement(name = "Proveedor", required = true)
    protected RequestCFD.Proveedor proveedor;
    @XmlElement(name = "Recepciones")
    protected RequestCFD.Recepciones recepciones;
    @XmlAttribute(name = "tipo", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String tipo;
    @XmlAttribute(name = "version", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String version;
    @XmlAttribute(name = "idPedido", required = true)
    protected String idPedido;
    @XmlAttribute(name = "idSolicitudPago")
    protected String idSolicitudPago;

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCFD.Documento }
     *     
     */
    public RequestCFD.Documento getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCFD.Documento }
     *     
     */
    public void setDocumento(RequestCFD.Documento value) {
        this.documento = value;
    }

    /**
     * Gets the value of the proveedor property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCFD.Proveedor }
     *     
     */
    public RequestCFD.Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * Sets the value of the proveedor property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCFD.Proveedor }
     *     
     */
    public void setProveedor(RequestCFD.Proveedor value) {
        this.proveedor = value;
    }

    /**
     * Gets the value of the recepciones property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCFD.Recepciones }
     *     
     */
    public RequestCFD.Recepciones getRecepciones() {
        return recepciones;
    }

    /**
     * Sets the value of the recepciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCFD.Recepciones }
     *     
     */
    public void setRecepciones(RequestCFD.Recepciones value) {
        this.recepciones = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        if (tipo == null) {
            return "AddendaPCO";
        } else {
            return tipo;
        }
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "2.0";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the idPedido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPedido() {
        return idPedido;
    }

    /**
     * Sets the value of the idPedido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPedido(String value) {
        this.idPedido = value;
    }

    /**
     * Gets the value of the idSolicitudPago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSolicitudPago() {
        return idSolicitudPago;
    }

    /**
     * Sets the value of the idSolicitudPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSolicitudPago(String value) {
        this.idSolicitudPago = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="referencia">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="1"/>
     *             &lt;maxLength value="46"/>
     *             &lt;whiteSpace value="collapse"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="serie">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="1"/>
     *             &lt;maxLength value="10"/>
     *             &lt;whiteSpace value="collapse"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="folio">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="1"/>
     *             &lt;maxLength value="20"/>
     *             &lt;whiteSpace value="collapse"/>
     *             &lt;pattern value="[0-9]+"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="folioUUID">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;whiteSpace value="collapse"/>
     *             &lt;length value="36"/>
     *             &lt;pattern value="[a-f0-9A-F]{8}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{12}"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="tipoDoc" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *             &lt;whiteSpace value="collapse"/>
     *             &lt;totalDigits value="1"/>
     *             &lt;enumeration value="1"/>
     *             &lt;enumeration value="2"/>
     *             &lt;enumeration value="3"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Documento {

        @XmlAttribute(name = "referencia")
        protected String referencia;
        @XmlAttribute(name = "serie")
        protected String serie;
        @XmlAttribute(name = "folio")
        protected String folio;
        @XmlAttribute(name = "folioUUID")
        protected String folioUUID;
        @XmlAttribute(name = "tipoDoc", required = true)
        protected BigInteger tipoDoc;

        /**
         * Gets the value of the referencia property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReferencia() {
            return referencia;
        }

        /**
         * Sets the value of the referencia property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReferencia(String value) {
            this.referencia = value;
        }

        /**
         * Gets the value of the serie property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSerie() {
            return serie;
        }

        /**
         * Sets the value of the serie property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSerie(String value) {
            this.serie = value;
        }

        /**
         * Gets the value of the folio property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFolio() {
            return folio;
        }

        /**
         * Sets the value of the folio property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFolio(String value) {
            this.folio = value;
        }

        /**
         * Gets the value of the folioUUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFolioUUID() {
            return folioUUID;
        }

        /**
         * Sets the value of the folioUUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFolioUUID(String value) {
            this.folioUUID = value;
        }

        /**
         * Gets the value of the tipoDoc property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTipoDoc() {
            return tipoDoc;
        }

        /**
         * Sets the value of the tipoDoc property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTipoDoc(BigInteger value) {
            this.tipoDoc = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="idProveedor" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;whiteSpace value="collapse"/>
     *             &lt;minLength value="1"/>
     *             &lt;maxLength value="10"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Proveedor {

        @XmlAttribute(name = "idProveedor", required = true)
        protected String idProveedor;

        /**
         * Gets the value of the idProveedor property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdProveedor() {
            return idProveedor;
        }

        /**
         * Sets the value of the idProveedor property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdProveedor(String value) {
            this.idProveedor = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Recepcion" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Concepto" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="cantidad" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;whiteSpace value="collapse"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="unidad" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 &lt;whiteSpace value="collapse"/>
     *                                 &lt;minLength value="1"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="descripcion" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 &lt;minLength value="1"/>
     *                                 &lt;whiteSpace value="collapse"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="valorUnitario" use="required" type="{http://www.fact.com.mx/schema/pepsico}t_Importe" />
     *                           &lt;attribute name="importe" use="required" type="{http://www.fact.com.mx/schema/pepsico}t_Importe" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="idRecepcion">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;whiteSpace value="collapse"/>
     *                       &lt;minLength value="1"/>
     *                       &lt;maxLength value="10"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "recepcion"
    })
    public static class Recepciones {

        @XmlElement(name = "Recepcion", required = true)
        protected List<RequestCFD.Recepciones.Recepcion> recepcion;

        /**
         * Gets the value of the recepcion property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the recepcion property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRecepcion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RequestCFD.Recepciones.Recepcion }
         * 
         * 
         */
        public List<RequestCFD.Recepciones.Recepcion> getRecepcion() {
            if (recepcion == null) {
                recepcion = new ArrayList<RequestCFD.Recepciones.Recepcion>();
            }
            return this.recepcion;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Concepto" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="cantidad" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;whiteSpace value="collapse"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="unidad" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       &lt;whiteSpace value="collapse"/>
         *                       &lt;minLength value="1"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="descripcion" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       &lt;minLength value="1"/>
         *                       &lt;whiteSpace value="collapse"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="valorUnitario" use="required" type="{http://www.fact.com.mx/schema/pepsico}t_Importe" />
         *                 &lt;attribute name="importe" use="required" type="{http://www.fact.com.mx/schema/pepsico}t_Importe" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="idRecepcion">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;whiteSpace value="collapse"/>
         *             &lt;minLength value="1"/>
         *             &lt;maxLength value="10"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "concepto"
        })
        public static class Recepcion {

            @XmlElement(name = "Concepto", required = true)
            protected List<RequestCFD.Recepciones.Recepcion.Concepto> concepto;
            @XmlAttribute(name = "idRecepcion")
            protected String idRecepcion;

            /**
             * Gets the value of the concepto property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the concepto property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getConcepto().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RequestCFD.Recepciones.Recepcion.Concepto }
             * 
             * 
             */
            public List<RequestCFD.Recepciones.Recepcion.Concepto> getConcepto() {
                if (concepto == null) {
                    concepto = new ArrayList<RequestCFD.Recepciones.Recepcion.Concepto>();
                }
                return this.concepto;
            }

            /**
             * Gets the value of the idRecepcion property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIdRecepcion() {
                return idRecepcion;
            }

            /**
             * Sets the value of the idRecepcion property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIdRecepcion(String value) {
                this.idRecepcion = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="cantidad" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;whiteSpace value="collapse"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="unidad" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             &lt;whiteSpace value="collapse"/>
             *             &lt;minLength value="1"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="descripcion" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             &lt;minLength value="1"/>
             *             &lt;whiteSpace value="collapse"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="valorUnitario" use="required" type="{http://www.fact.com.mx/schema/pepsico}t_Importe" />
             *       &lt;attribute name="importe" use="required" type="{http://www.fact.com.mx/schema/pepsico}t_Importe" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Concepto {

                @XmlAttribute(name = "cantidad", required = true)
                protected BigDecimal cantidad;
                @XmlAttribute(name = "unidad", required = true)
                protected String unidad;
                @XmlAttribute(name = "descripcion", required = true)
                protected String descripcion;
                @XmlAttribute(name = "valorUnitario", required = true)
                protected BigDecimal valorUnitario;
                @XmlAttribute(name = "importe", required = true)
                protected BigDecimal importe;

                /**
                 * Gets the value of the cantidad property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getCantidad() {
                    return cantidad;
                }

                /**
                 * Sets the value of the cantidad property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setCantidad(BigDecimal value) {
                    this.cantidad = value;
                }

                /**
                 * Gets the value of the unidad property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUnidad() {
                    return unidad;
                }

                /**
                 * Sets the value of the unidad property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUnidad(String value) {
                    this.unidad = value;
                }

                /**
                 * Gets the value of the descripcion property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDescripcion() {
                    return descripcion;
                }

                /**
                 * Sets the value of the descripcion property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDescripcion(String value) {
                    this.descripcion = value;
                }

                /**
                 * Gets the value of the valorUnitario property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getValorUnitario() {
                    return valorUnitario;
                }

                /**
                 * Sets the value of the valorUnitario property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setValorUnitario(BigDecimal value) {
                    this.valorUnitario = value;
                }

                /**
                 * Gets the value of the importe property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getImporte() {
                    return importe;
                }

                /**
                 * Sets the value of the importe property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setImporte(BigDecimal value) {
                    this.importe = value;
                }

            }

        }

    }

}
