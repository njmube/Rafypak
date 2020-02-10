package com.tikal.boveda.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.google.apphosting.api.ApiProxy.OverQuotaException;
import com.tikal.boveda.dao.FacturaBovedaDAO;
import com.tikal.boveda.modelo.FacturaBoveda;
import com.tikal.cacao.dao.sql.RegimenFiscalDAO;
import com.tikal.cacao.dao.sql.SimpleHibernateDAO;
import com.tikal.cacao.dao.sql.UsoDeCFDIDAO;
//import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.factura.Estatus;
//import com.tikal.cacao.model.Factura;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.model.RegistroBitacora;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.model.orm.RegimenFiscal;
import com.tikal.cacao.model.orm.TipoDeComprobante;
import com.tikal.cacao.model.orm.UsoDeCFDI;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.util.EmailSender;
import com.tikal.cacao.util.Util;

@Service("facturaBovedaDao")


public class FacturaBovedaDaoImpl implements FacturaBovedaDAO{


	@Autowired
	@Qualifier("usoDeCfdiDAOH")
	private UsoDeCFDIDAO usoDeCFDIDAO;

	@Autowired
	@Qualifier("regimenFiscalDAOH")
	private RegimenFiscalDAO regimenFiscalDAO;

	@Autowired
	@Qualifier("formaDePagoDAOH")
	private SimpleHibernateDAO<FormaDePago> formaDePagoDAO;
	
	@Autowired
	@Qualifier("tipoDeComprobanteDAOH")
	private SimpleHibernateDAO<TipoDeComprobante> tipoDeComprobanteDAO;
	
		@Override
		public void guardar(FacturaBoveda f) {
			ofy().save().entity(f).now();
			
		}
		
		@Override
		public FacturaBoveda consultar(String uuid) {
			return ofy().load().type(FacturaBoveda.class).id(uuid).now();
		}
		
		@Override
		public List<FacturaBoveda> consutarTodas(String rfcEmisor) {
			List<FacturaBoveda> facturas = ofy().load().type(FacturaBoveda.class).filter("rfcEmisor", rfcEmisor).order("- fechaCertificacion").list();
			if (facturas == null)
				return new ArrayList<>();
			else
				return facturas;
		}
		
		
		@Override
		public List<FacturaBoveda> getAll() {
			List<FacturaBoveda> facturas = ofy().load().type(FacturaBoveda.class).order("- fechaCertificacion").list();
			if (facturas == null)
				return new ArrayList<>();
			else
				return facturas;
		}
		
		@Override
		public boolean eliminar(FacturaBoveda f) {
			if (f.getEstatus().equals(Estatus.GENERADO)) {
				ofy().delete().entity(f).now();
				return true;
			}
			return false;
		}
		
		@Override
		public void eliminar1(String uuid) {
			ofy().delete().type(FacturaBoveda.class).id(uuid).now();
		}
		
		@Override
		public List<FacturaBoveda> buscar(Date fi, Date ff, String rfc) {
			return ofy().load().type(FacturaBoveda.class).filter("rfcEmisor",rfc).filter("fechaCertificacion >=",fi).filter("fechaCertificacion <=",ff).list();
		}
		
		@Override
		public List<FacturaBoveda> consutarTodas(int page) {
			List<FacturaBoveda> facturas = ofy().load().type(FacturaBoveda.class).order("- fechaCertificacion").offset(25*(page-1)).limit(25).list();
			if (facturas == null){
				return new ArrayList<>();
			}
			else{
				return facturas;
			}
		}
		
		@Override
		public int getPaginasRfc(String rfc) {
			return ((ofy().load().type(FacturaBoveda.class).filter("rfcEmisor",rfc.toUpperCase()).count()-1)/25)+1;
		}
		
		@Override
		public int getPaginas() {
			return ((ofy().load().type(FacturaBoveda.class).count()-1)/25)+1;
		}

		@Override
		public List<FacturaBoveda> getFacByProv(String rfcEmisor, int page) {
			List<FacturaBoveda> facturas = ofy().load().type(FacturaBoveda.class).filter("rfcEmisor", rfcEmisor.toUpperCase()).order("- fechaBoveda").offset(25*(page-1)).limit(25).list();
			if (facturas == null){
				return new ArrayList<>();
			}
			else{
				return facturas;
			}
		}
		
		@SuppressWarnings("null")
		@Override
		public void enviarEmail(String email, String uuid) {
			System.out.println("va a mandar mail");
			//EmailSender mailero = new EmailSender("", "", "", "");
			FacturaBoveda f=  this.consultar(uuid);
		//	Comprobante cfdi = Util.unmarshallCFDI33XML(f.getCfdiXML());
			System.out.println("email:"+email);
			System.out.println("uuid:"+f.getUuid());
			System.out.println("serie:"+f.getSerie());
			System.out.println("folio:"+f.getFolio());
			System.out.println("fecha:"+f.getFechaProgramada());
			enviaFacturaBoveda(email,f.getUuid(),f.getSerie()+"-"+f.getFolio(),new SimpleDateFormat("dd-MM-yyyy").format(f.getFechaProgramada()));
//			String evento = "Se envi�  la factura con id: " + factura.getUuid() + " al correo: " + email;
//			RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
//			bitacoradao.addReg(registroBitacora);
		}

		public void enviaFacturaBoveda(String emailReceptor, String uuid,String numfac, String fechaProgramada) {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			
			try {
				Message msg = new MimeMessage(session);
				
				//append PDF
				Multipart mp = new MimeMultipart();
				MimeBodyPart mbp = new MimeBodyPart();
				mbp.setContent("<h2>Informamos que el pago para su factura con uuid: "+uuid+"y numero de factura:"+numfac+" está programado para el dia: "+fechaProgramada+"</h2>","text/html");
				mp.addBodyPart(mbp);			
				
				msg.setFrom(new InternetAddress(" no.reply.fcon@gmail.com", "Pago de Factura"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, "Empresa"));
				//msg.setSubject("Factura "+factura.getUuid());
				msg.setText("Prueba de correo 2");
				msg.setContent(mp);
				Transport.send(msg);

			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();	
			} catch (IOException e) {
				e.printStackTrace();
			}catch(OverQuotaException e){
				System.out.println("Se alcanzó");
			}
		}


		public void enviaUsuarioBoveda(String emailReceptor, String usuario, String password) {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			
			try {
				Message msg = new MimeMessage(session);
				
				//append PDF
				Multipart mp = new MimeMultipart();
				MimeBodyPart mbp = new MimeBodyPart();
				mbp.setContent("<h4>Por medio del presente le informamos, que ha sido dado de alta como proveedor, en el sistema de programacion de pagos de Rafypack \n"
						+ "la url de acceso es la siguiente: \n https://okueefac.appspot.com/proveedores/#/login  \n"
						+ "y sus datos de acceso son \n usuario:"+usuario+" \n contraseña:"+password+"  </h4>","text/html");
				mp.addBodyPart(mbp);				
				msg.setFrom(new InternetAddress(" no.reply.fcon@gmail.com", "Datos de acceso a provedores de Rafypak"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, "Empresa"));
				//msg.setSubject("Factura "+factura.getUuid());
				msg.setText("Prueba de correo 2");
				msg.setContent(mp);
				Transport.send(msg);

			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();	
			} catch (IOException e) {
				e.printStackTrace();
			}catch(OverQuotaException e){
				System.out.println("Se alcanzó");
			}
		}

}
