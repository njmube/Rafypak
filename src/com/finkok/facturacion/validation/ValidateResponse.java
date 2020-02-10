package com.finkok.facturacion.validation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateResponse", propOrder = {
    "validateResult"
})
@XmlRootElement(name = "validateResponse")
public class ValidateResponse {

	 @XmlElementRef(name = "validateResult", namespace = "http://facturacion.finkok.com/validation", type = JAXBElement.class, required = false)
	    protected JAXBElement<ValidateResult> validateResult;

	public JAXBElement<ValidateResult> getValidateResult() {
		return validateResult;
	}

	public void setValidationResult(JAXBElement<ValidateResult> validationResult) {
		this.validateResult = validationResult;
	}
	 
	 
}
