package views.core.soap.services.apps;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcuseSAT", propOrder = {
		"estado",
		"codigoEstatus"
})
public class AcuseSAT {
		@XmlElementRef(name = "Estado", namespace = "apps.services.soap.core.views", type = JAXBElement.class, required = false)
	    protected JAXBElement<String> estado;
	    @XmlElementRef(name = "CodigoEstatus", namespace = "apps.services.soap.core.views", type = JAXBElement.class, required = false)
	    protected JAXBElement<String> codigoEstatus;
	    
	    public JAXBElement<String> getEstado() {
	        return estado;
	    }
	    
	    public JAXBElement<String> getCodigoEstatus() {
	        return codigoEstatus;
	    }
}
