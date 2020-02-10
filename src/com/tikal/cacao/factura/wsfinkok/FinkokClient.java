package com.tikal.cacao.factura.wsfinkok;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.util.IOUtils;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import com.finkok.facturacion.registration.Add;
import com.finkok.facturacion.registration.AddResponse;
import com.finkok.facturacion.stamp.ObjectFactory;
import com.finkok.facturacion.stamp.SignStamp;
import com.finkok.facturacion.stamp.SignStampResponse;
import com.finkok.facturacion.validation.Validate;
import com.finkok.facturacion.validation.ValidateResponse;
import com.finkok.facturacion.validation.ValidateResult;
import com.tikal.cacao.util.Util;

import localhost.EncodeBase64;
import views.core.soap.services.apps.AcuseRecepcionCFDI;

@Service
public class FinkokClient extends WebServiceGatewaySupport {

	private String password;
	private String user;
	private String uri;
	private String uriRegistration;
	private String uriCancela;
	private String uriValida;
	private EncodeBase64 base64 = new EncodeBase64();
	private ObjectFactory of= new ObjectFactory();
	private com.finkok.facturacion.registration.ObjectFactory ofregistra= new com.finkok.facturacion.registration.ObjectFactory();
//	private cancel.ObjectFactory ofcancela= new cancel.ObjectFactory();
	private views.core.soap.services.apps.ObjectFactory ofc= new views.core.soap.services.apps.ObjectFactory();
	private com.finkok.facturacion.validation.ObjectFactory ofValida= new com.finkok.facturacion.validation.ObjectFactory();

	
	
	public AddResponse getRegistraEmisorResponse(String rfcEmisor, String pass, InputStream cer, InputStream ker) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Add.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
			StringWriter sw = new StringWriter();
			System.out.println(sw.toString());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Add request= ofregistra.createAdd();
			request.setCer(ofregistra.createAddCer(IOUtils.toByteArray(cer)));
			request.setKey(ofregistra.createAddKey(IOUtils.toByteArray(ker)));
			request.setPassphrase(ofregistra.createAddPassphrase(pass));
			request.setResellerPassword(ofregistra.createEditResellerPassword(password));
			request.setResellerUsername(ofregistra.createAddResellerUsername(user));
			request.setTaxpayerId(ofregistra.createAddTaxpayerId(rfcEmisor));
			request.setTypeUser(ofregistra.createAddTypeUser("O"));
			
			JAXBElement<AddResponse> response= (JAXBElement<AddResponse>) getWebServiceTemplate()
					.marshalSendAndReceive(uriRegistration,
					request,
					new SoapActionCallback("http://facturacion.finkok.com/addResponse"));
			
			return response.getValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	private String getByteArrayBase64(InputStream inputStream) {
		return base64.encodeByteArrayIS(inputStream);
	}
	
	
	public FinkokClient() {
		this.uriValida="https://facturacion.finkok.com/servicios/soap/validation.wsdl";
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths("com.finkok.facturacion.registration","com.finkok.facturacion.stamp","cancel", "views.core.soap.services.apps");
		this.setMarshaller(marshaller);
		this.setUnmarshaller(marshaller);
		if (Util.detectarAmbienteProductivo()) {
			uri = "https://facturacion.finkok.com/servicios/soap/stamp.wsdl";
			uriCancela= "https://facturacion.finkok.com/servicios/soap/cancel.wsdl";
			uriRegistration="https://facturacion.finkok.com/servicios/soap/registration.wsdl";
			user = "no.reply.fcon@gmail.com";
			this.password="finkokProd123.";
			this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
		} else {
			uri = "https://demo-facturacion.finkok.com/servicios/soap/stamp.wsdl";
			uriRegistration="https://demo-facturacion.finkok.com/servicios/soap/registration.wsdl";
			uriCancela="https://demo-facturacion.finkok.com/servicios/soap/cancel.wsdl";
			user = "no.reply.fcon@gmail.com";
			this.password = "finkokTest12.";
			this.getWebServiceTemplate().setMessageSenders(this.configurarMessageSenders());
		}
	}
	

	public AcuseRecepcionCFDI getStampResponse(String xml) {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths("com.finkok.facturacion.stamp", "views.core.soap.services.apps");
		this.setMarshaller(marshaller);
		this.setUnmarshaller(marshaller);
		
		ObjectFactory factory = new ObjectFactory();
		SignStamp timbra = factory.createSignStamp();

		timbra.setPassword(factory.createStampedPassword(password));
		timbra.setUsername(factory.createStampedUsername(user));
		timbra.setXml(factory.createStampXml(xml.getBytes()));
		

		try {

			JAXBElement<SignStampResponse> response = (JAXBElement<SignStampResponse>) this.getWebServiceTemplate()
					.marshalSendAndReceive(uri, timbra, new SoapActionCallback("http://facturacion.finkok.com/stampResponse"));
			AcuseRecepcionCFDI acuse = response.getValue().getSignStampResult().getValue();
			return acuse;

		} catch (WebServiceIOException e) {
			System.out.println(e.getMessage());
			AcuseRecepcionCFDI responseError = null;
			return responseError;
		}

	}
	
//	public ValidateResponse valida(String xml) {
//		
//	}
	
	public ValidateResult validar(String xml) {
		
		xml="<?xml version=\"1.0\" encoding=\"utf-8\"?><cfdi:Comprobante xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd\" Certificado=\"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\" CondicionesDePago=\"Contado\" Fecha=\"2018-10-18T09:20:37\" Folio=\"606\" FormaPago=\"03\" LugarExpedicion=\"50090\" MetodoPago=\"PUE\" Moneda=\"MXN\" NoCertificado=\"00001000000402995493\" Sello=\"ITZ1s+q1OV9PcevrGKwVAsXqKNz4pyBxdvsOc5vdnKK/jHf27rNhQWVgLESh1WUhItDjFGcDR0XBZVRPg2esh6dGA0TRjekzsPkeOZvLLK7vbgw5Wb0kTFghUjYPdEDW0EFA/xUVaLXNwAgN4CH9r26lA2eRmC7F82AqcVYejU5lYJ+nqVMzhQWWcmgCjGKYlEby9e4cN8x+TWI0Blto7G7dz+ZvpXsjU9OGS3zycZabFojoHxhvn+1lkfu+kh3V4q6JjuLw95N5VhTwOGnL3N2ExN8lBJbrnhcBlrxvNrWDKtt5H9df3XbSPFiZnuqIGtb/iqmQBQI79R3Z9W9IbQ==\" Serie=\"1\" SubTotal=\"18900\" TipoDeComprobante=\"I\" Total=\"21924\" Version=\"3.3\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\"><cfdi:Emisor Nombre=\"TIKAL, TECNOLOGIAS DE LA INFORMACIÓN\" RegimenFiscal=\"601\" Rfc=\"TTI160614777\" /><cfdi:Receptor Nombre=\"CONTAINER CARE DE MEXICO S.A. DE C.V.\" Rfc=\"CCM930525AA7\" UsoCFDI=\"P01\" /><cfdi:Conceptos><cfdi:Concepto Cantidad=\"1\" ClaveProdServ=\"81161501\" ClaveUnidad=\"E48\" Descripcion=\"Servicios de administración de aplicación de Software (30 horas de asesoría)\" Importe=\"18900\" NoIdentificacion=\"81161501\" Unidad=\"No Aplica\" ValorUnitario=\"18900.00\"><cfdi:Impuestos><cfdi:Traslados><cfdi:Traslado Base=\"18900\" Importe=\"3024\" Impuesto=\"002\" TasaOCuota=\"0.160000\" TipoFactor=\"Tasa\" /></cfdi:Traslados></cfdi:Impuestos></cfdi:Concepto></cfdi:Conceptos><cfdi:Impuestos TotalImpuestosTrasladados=\"3024\"><cfdi:Traslados><cfdi:Traslado Importe=\"3024\" Impuesto=\"002\" TasaOCuota=\"0.160000\" TipoFactor=\"Tasa\" /></cfdi:Traslados></cfdi:Impuestos><cfdi:Complemento><tfd:TimbreFiscalDigital xsi:schemaLocation=\"http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd\" FechaTimbrado=\"2018-10-18T09:20:52\" NoCertificadoSAT=\"00001000000306850881\" RfcProvCertif=\"PFE140312IW8\" SelloCFD=\"ITZ1s+q1OV9PcevrGKwVAsXqKNz4pyBxdvsOc5vdnKK/jHf27rNhQWVgLESh1WUhItDjFGcDR0XBZVRPg2esh6dGA0TRjekzsPkeOZvLLK7vbgw5Wb0kTFghUjYPdEDW0EFA/xUVaLXNwAgN4CH9r26lA2eRmC7F82AqcVYejU5lYJ+nqVMzhQWWcmgCjGKYlEby9e4cN8x+TWI0Blto7G7dz+ZvpXsjU9OGS3zycZabFojoHxhvn+1lkfu+kh3V4q6JjuLw95N5VhTwOGnL3N2ExN8lBJbrnhcBlrxvNrWDKtt5H9df3XbSPFiZnuqIGtb/iqmQBQI79R3Z9W9IbQ==\" SelloSAT=\"xmFKQ6QxySxscrtel8+Kzob6lPlS7OGxyKqOUnR/Z1Yyzunc2SRWRdLv8dVmTWWF3K2OlmrzL6yeILTnRvJgeyFLOC76F9NURrSiJ9UKgtEjdjLigAQSOIEI65oRDCUkWUWV16lkzmKYW+H0k1BgVpR0zbjtr7pWdwldChUzSJY=\" UUID=\"467f2bfe-d218-4c31-83f5-df480926fb83\" Version=\"1.1\" xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\" /></cfdi:Complemento></cfdi:Comprobante>"; 
		byte[] bytes= xml.getBytes();
		
		xml=new String(bytes);
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths("com.finkok.facturacion.validation","views.core.soap.services.apps");
		this.setMarshaller(marshaller);
		this.setUnmarshaller(marshaller);
		
		Validate validate= new Validate();
		validate.setPassword(ofValida.createValidatePassword("finkokProd123."));
		validate.setUsername(ofValida.createValidateUsername("no.reply.fcon@gmail.com"));
		
		
		validate.setXml(ofValida.createValidateXml(bytes));
		try {
			byte[] bytes2= xml.getBytes("utf-8");
			validate.setXml(ofValida.createValidateXml(bytes2));
		} catch (UnsupportedEncodingException e1) {
		}
		
		StringResult result = new StringResult();
		marshaller.marshal(validate, result);
		String peticion = result.toString();
		try {
			
			ByteArrayOutputStream  bytArrayOutputStream = new ByteArrayOutputStream();
			StreamSource source = new StreamSource(new StringReader(peticion));
	        StreamResult results = new StreamResult(bytArrayOutputStream);
	        WebServiceTemplate wst= this.getWebServiceTemplate();
	        wst.setDefaultUri(this.uriValida);
			wst.sendSourceAndReceiveToResult(source, results);
			String olv= new String(bytArrayOutputStream.toByteArray()) ;
			
			JAXBElement<ValidateResponse> response = (JAXBElement<ValidateResponse>) marshaller.unmarshal(new StringSource(olv));
			
//			result = new StringResult();
//			marshaller.marshal(response, result);
//			System.out.println(result.toString());
//			
			ValidateResult acuse = response.getValue().getValidateResult().getValue();
			return acuse;

			
		} catch (WebServiceIOException e) {
			System.out.println(e.getMessage());
			ValidateResult responseError = null;
			return responseError;
		}
	}
	
//	public cancel.CancelaCFDResult cancela(String uuid, String rfcEmisor, String serial){
////		
//		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		marshaller.setContextPaths("cancel", "com.finkok.facturacion.cancel");
//		this.setMarshaller(marshaller);
//		this.setUnmarshaller(marshaller);
//		
//		Cancelacion cancelacionc= new Cancelacion();
//		
//		
//		SignCancelLocal c= new SignCancelLocal();
//		c.setPassword(ofcancela.createCancelPassword(this.password));
//		c.setStorePending(ofcancela.createCancelStorePending(true));
//		c.setTaxpayerId(ofcancela.createCancelTaxpayerId(rfcEmisor));
//		c.setUsername(ofcancela.createCancelUsername(user));
//		c.setSerial(ofcancela.createSignCancelSerial(serial));
//		
//		cancel.UUIDS ids= new cancel.UUIDS();
//		
//		StringArray sa= ofcancela.createStringArray();
//		sa.getString().add(uuid.toLowerCase());
//		ids.setUuids(ofcancela.createUUIDSUuids(sa));
//		c.setUUIDS(ofcancela.createSignCancelUUIDS(ids));
//		
//		Jaxb2Marshaller jaxbMarshaller= (Jaxb2Marshaller) this.getMarshaller();
//		StringResult  sw = new StringResult();
//		
//		jaxbMarshaller.marshal(c, sw);
//
//		String result = sw.toString();
//		
//		System.out.println(sw.toString());
//		
//		try {
//			
//			JAXBElement<SignCancelResponse> response = (JAXBElement<SignCancelResponse>) this.getWebServiceTemplate()
//					.marshalSendAndReceive(uriCancela, c, new SoapActionCallback("http://facturacion.finkok.com/signCancelResponse"));
//			System.out.println("a ver a ver");
//			
////			JAXBContext jaxbContext = JAXBContext.newInstance(SignCancelResponse.class);
////			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
////		
//////			jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
//////				"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
////		
////			Jaxb2Marshaller msh= (Jaxb2Marshaller) this.getMarshaller();
////			StringWriter sw = new StringWriter();
////			
////			msh.getJaxbContext().createMarshaller().marshal(c, sw);
////			jaxbMarshaller.marshal(response, sw);
////			System.out.println(sw.toString());
//			
//			cancel.CancelaCFDResult cresult=response.getValue().getSignCancelResult().getValue();
//			if(cresult.getCodEstatus()!=null) {
//				String status= cresult.getCodEstatus().getValue();
//				if(status.startsWith("Error:") && status.length()< 8) {
//					cresult.getCodEstatus().setValue(cresult.getCodEstatus().getValue()+" "+result);
//				}
//			}
//			return cresult;
//
//		} catch (WebServiceIOException e) {
//			System.out.println(e.getMessage());
//			AcuseRecepcionCFDI responseError = null;
//			return null;
//		}		
//	}

	private HttpComponentsMessageSender crearMessageSender() {
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setConnectionTimeout(60000);
		messageSender.setReadTimeout(60000);
		// messageSender.
		return messageSender;
	}

	private WebServiceMessageSender[] configurarMessageSenders() {
		List<WebServiceMessageSender> messageSenders = new ArrayList<>();
		WebServiceMessageSender httpUrlConnectionMS = this.getWebServiceTemplate().getMessageSenders()[0];
		WebServiceMessageSender httpComponetsMS = this.crearMessageSender();
		messageSenders.add(httpUrlConnectionMS);
		messageSenders.add(httpComponetsMS);

		WebServiceMessageSender[] arregloMessageSenders;
		arregloMessageSenders = messageSenders.toArray(new WebServiceMessageSender[2]);
		return arregloMessageSenders;
	}
	
	private String getBase64CFDI(String xmlCFDI) {
		return base64.encodeString(xmlCFDI);
	}

}
