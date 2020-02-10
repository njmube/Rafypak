package com.tikal.cacao.boveda.springController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.boveda.model.UsuarioProveedor;
import com.tikal.cacao.dao.UsuariosDAO;
import com.tikal.cacao.model.Perfil;
import com.tikal.cacao.model.Usuario;
import com.tikal.cacao.security.PerfilDAO;
import com.tikal.cacao.security.UsuarioDAO;
import com.tikal.cacao.springController.UsuarioController;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value= {"proveedores/sesionp"})
public class ServicioSesionProveedor {

	@Autowired
	UsuarioDAO<UsuarioProveedor> usuariodao;

	@RequestMapping(value = { "/user" }, method = RequestMethod.GET, produces = "application/json")
	public void user(HttpServletResponse res, HttpServletRequest req) throws IOException {
		String auti = req.getHeader("authorization");
		auti = auti.substring(5);
		byte[] dec = Base64Utils.decodeFromString(auti);

		String c = "";
		for (byte b : dec) {
			c += (char) b;
		}
		String[] parts = c.split(":");
		String u = parts[0];
		String p = UsuarioController.otroMetodo(parts[1]);
		UsuarioProveedor usuario = usuariodao.consultarUsuario(u);
		// Verificar que el usuario y contrase�a coincidan
		if (usuario == null || (usuario.getPassword().equals(p) == false)) {
			res.sendError(403);
		} else {
			usuario.resetPassword();
			req.getSession().setAttribute("userName", usuario.getUsername());
			res.getWriter().println(JsonConvertidor.toJson(usuario));
		}
	}

	// currentSession

	@RequestMapping(value = { "/currentSession" }, method = RequestMethod.GET, produces = "application/json")
	public void currentUser(HttpServletResponse res, HttpServletRequest req) throws IOException {
		HttpSession s = req.getSession();
		String n = (String) s.getAttribute("userName");
		if (n == null) {
			res.sendError(400);
		}
	}
	
	public static boolean verificarPermiso(HttpServletRequest request, UsuarioDAO usuariodao, PerfilDAO  perfildao, int per){
		HttpSession s = request.getSession();
		String nombreUsuario = (String) s.getAttribute("userName");
		if(nombreUsuario == null){
			return false;
		}else{
			Usuario usuario = (Usuario) usuariodao.consultarUsuario(nombreUsuario);
			Perfil perfil = perfildao.consultarPerfil(usuario.getPerfil());
			if(perfil.getPermisos()[per]==true){
				return true;
			}
		}
		return false;
	}
	
	public static boolean verificarPermiso(HttpServletRequest request, UsuariosDAO usuariodao, PerfilDAO  perfildao, int per){
		HttpSession s = request.getSession();
		String nombreUsuario = (String) s.getAttribute("userName");
		if(nombreUsuario == null){
			return false;
		}else{
			Usuario usuario = (Usuario) usuariodao.consultar(nombreUsuario);
			Perfil perfil = perfildao.consultarPerfil(usuario.getPerfil());
			if(perfil.getPermisos()[per]==true){
				return true;
			}
		}
		return false;
	}
	
}

