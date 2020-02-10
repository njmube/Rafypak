package com.tikal.cacao.boveda.springController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.boveda.dao.FacturaBovedaDAO;
import com.tikal.boveda.modelo.FacturaBoveda;
import com.tikal.cacao.boveda.model.UsuarioProveedor;
import com.tikal.cacao.model.Usuario;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.springController.ServicioSesion;
import com.tikal.cacao.springController.UsuarioController;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.EmailSender;
import com.tikal.cacao.util.Util;
import com.tikal.unoconnections.util.JsonConvertidor;

@Controller
@RequestMapping(value= {"proveedores/usuarioProveedor"})
public class UsuarioProveedorController {
	
	@Autowired
	UsuarioDAO<UsuarioProveedor> usuariodao;
	
	@Autowired
	UsuarioDAO<Usuario> usuariodaoi;
	
	@Autowired
	PerfilDAO perfilImp;
	
	@Autowired
	private FacturaBovedaDAO facturaBovedaDao;

	@RequestMapping(value = { "/registro" }, method = RequestMethod.POST, consumes = "Application/Json")
	public void crearUsuario(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		if(ServicioSesion.verificarPermiso(request, usuariodaoi, perfilImp, 18)){
			AsignadorDeCharset.asignar(request, response);
			UsuarioProveedor usuario = (UsuarioProveedor) JsonConvertidor.fromJson(json, UsuarioProveedor.class);
			facturaBovedaDao.enviaUsuarioBoveda(usuario.getEmail(), usuario.getUsername(), usuario.getPassword());
			usuario.setPass(UsuarioController.otroMetodo(usuario.getPass()));
			
			//usuario.setPerfil("proveedor");
			if (usuario.getUsername() == null || usuario.getPassword() == null || usuario.getEmail() == null) {
				response.sendError(400);
			} else {
				// System.out.println(usuario.getUsername()+"YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYy");
				if (!usuariodao.crearUsuario(usuario)) {
					response.sendError(400);
				}
			}
		}else{
			response.sendError(403);
		}
	}

	@RequestMapping(value = { "/consultarTodos" }, method = RequestMethod.GET, produces = "application/json")
	public void consultarUsuarios(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(ServicioSesion.verificarPermiso(request, usuariodaoi, perfilImp, 8)){
			AsignadorDeCharset.asignar(request, response);
			List<UsuarioProveedor> lista = usuariodao.consultarUsuarios();
			response.getWriter().println(JsonConvertidor.toJson(lista));
		}else{
			response.sendError(403);
		}
	}

	@RequestMapping(value = { "/actualiza" }, method = RequestMethod.POST, consumes = "Application/Json")
	public void actualizarUsuario(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		UsuarioProveedor usuario = (UsuarioProveedor) JsonConvertidor.fromJson(json, UsuarioProveedor.class);
		usuariodao.actualizarUsuario(usuario);
	}

	@RequestMapping(value = { "/elimina" }, method = RequestMethod.POST, consumes = "Application/Json")
	public void eliminarUsuario(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		UsuarioProveedor usuario = (UsuarioProveedor) JsonConvertidor.fromJson(json, UsuarioProveedor.class);
		usuariodao.eliminarUsuario(usuario);
	}

	@RequestMapping(value = { "/reset" }, method = RequestMethod.POST, consumes = "Application/Json")
	public void resetearPass(HttpServletRequest request, HttpServletResponse response, @RequestBody String email)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		String correo = (String) JsonConvertidor.fromJson(email, String.class);
		UsuarioProveedor usuario = usuariodao.consultarPorEmail(correo);
		//System.out.println("Printf de UsuarioController = " + usuario.getUsername());
		String user= usuario.getUsername();
		String mail= usuario.getEmail();
		if(usuario.getUsername()==null){
			response.sendError(400);
		}else{
			EmailSender sender = new EmailSender();
			String nuevoPass = Util.randomString(10);
			sender.enviaEmail(mail, user, nuevoPass);
			
			usuario.setPass(UsuarioController.otroMetodo(nuevoPass));
			usuariodao.actualizarUsuario(usuario);
			//System.out.println("Si mando el correo :*");
			
		}
	}
	@RequestMapping(value = {"/check"}, method = RequestMethod.GET)
	public void consultarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession s = request.getSession();
		String user = (String)s.getAttribute("userName");
		if(user == null){
			response.sendError(403);
		}
	}
	
//	@SuppressWarnings("null")
//	@Override
//	public void enviarUsuarioEmail(String email, String usuario, String password) {
//		System.out.println("va a mandar mail");
//		//EmailSender mailero = new EmailSender("", "", "", "");
//	//	FacturaBoveda f=  this.consultar(uuid);
//	//	Comprobante cfdi = Util.unmarshallCFDI33XML(f.getCfdiXML());
//	
//		System.out.println("usuario:"+usuario);
//		System.out.println("password:"+password);
//		System.out.println("email:"+email);
//	
//		enviaFacturaBoveda(email,f.getUuid(),f.getSerie()+"-"+f.getFolio(),new SimpleDateFormat("dd-MM-yyyy").format(f.getFechaProgramada()));
////		String evento = "Se envi�  la factura con id: " + factura.getUuid() + " al correo: " + email;
////		RegistroBitacora registroBitacora = Util.crearRegistroBitacora(sesion, "Operacional", evento);
////		bitacoradao.addReg(registroBitacora);
//	}
}
