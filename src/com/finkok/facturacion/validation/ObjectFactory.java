package com.finkok.facturacion.validation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.finkok.facturacion.stamp.SignStamp;

@XmlRegistry
public class ObjectFactory {

	private final static QName _validate_QNAME = new QName("http://facturacion.finkok.com/validation", "validate");
	private final static QName _validateResponse_QNAME = new QName("http://facturacion.finkok.com/validation", "validateResponse");
	private final static QName _xmlString_QNAME = new QName("http://facturacion.finkok.com/validation", "xml");
	private final static QName _validatePassword_QNAME = new QName("http://facturacion.finkok.com/validation", "password");
	private final static QName _validateUsername_QNAME = new QName("http://facturacion.finkok.com/validation", "username");
	private final static QName _validateResult_QNAME = new QName("http://facturacion.finkok.com/validation", "validateResult");
	 /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.finkok.facturacion.stamp
     * 
     */
    public ObjectFactory() {
    }
    
    public Validate createValidate() {
    	return new Validate();
    }
	
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignStamp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "validate")
    public JAXBElement<Validate> createValidate(Validate value) {
        return new JAXBElement<Validate>(this._validate_QNAME, Validate.class, null, value);
    }
    
    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "validateResponse")
    public JAXBElement<ValidateResponse> createValidateResponse(ValidateResponse value) {
        return new JAXBElement<ValidateResponse>(this._validateResponse_QNAME, ValidateResponse.class, null, value);
    }
    
//    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "validationResult", scope= ValidateResponse.class)
//    public JAXBElement<ValidationResult> createValidateResponseValidateResult(ValidationResult value) {
//        return new JAXBElement<ValidationResult>(this._validationResult_QNAME, ValidationResult.class, ValidateResponse.class, value);
//    }
    
    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "password", scope= Validate.class)
    public JAXBElement<String> createValidatePassword(String value) {
        return new JAXBElement<String>(this._validatePassword_QNAME, String.class, Validate.class, value);
    }
    
    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "username", scope= Validate.class)
    public JAXBElement<String> createValidateUsername(String value) {
        return new JAXBElement<String>(this._validateUsername_QNAME, String.class, Validate.class, value);
    }
    
    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "xml", scope= Validate.class)
    public JAXBElement<byte[]> createValidateXml(byte[] value) {
        return new JAXBElement<byte[]>(this._xmlString_QNAME, byte[].class, Validate.class, ((byte[])value));
    }
    
//    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "xml", scope= ValidationResult.class)
//    public JAXBElement<Boolean> createValidationResultXml(Boolean value) {
//        return new JAXBElement<Boolean>(this._xmlBoolean_QNAME, Boolean.class, ValidationResult.class, value);
//    }
//    
//    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "sello", scope= ValidationResult.class)
//    public JAXBElement<Boolean> createValidationResultSello(Boolean value) {
//        return new JAXBElement<Boolean>(this._sello_QNAME, Boolean.class, ValidationResult.class, value);
//    }
//    
//    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "selloSat", scope= ValidationResult.class)
//    public JAXBElement<Boolean> createValidationResultSelloSat(Boolean value) {
//        return new JAXBElement<Boolean>(this._selloSat_QNAME, Boolean.class, ValidationResult.class, value);
//    }
//    
//    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "error", scope= ValidationResult.class)
//    public JAXBElement<String> createValidationResultError(String value) {
//        return new JAXBElement<String>(this._error_QNAME, String.class, ValidationResult.class, value);
//    }
    
    @XmlElementDecl(namespace = "http://facturacion.finkok.com/validation", name = "validateResult", scope= ValidateResponse.class)
    public JAXBElement<ValidateResult> createValidateResult(ValidateResult value) {
        return new JAXBElement<ValidateResult>(this._validateResult_QNAME, ValidateResult.class, ValidateResponse.class, value);
    }
}
