/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package USUARIOS;

import uqroo.comun.Utilerias;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.component.hotkey.Hotkey;

/**
 *
 * @author Genner
 */
@ManagedBean
@SessionScoped
public class usuarioCtrl implements Serializable {

    private Usuario seleccionado;
    private String j_usuario;
    private String j_password;
    private Usuario user;
    private List<Usuario> lista;
    private usuarioDAL udal;
    private Utilerias ut = null;
    private String token;
    private String log;
    private String tac;
    private String mensaje;
    private String urlError;
    private Hotkey hk;

    private String view;

    public usuarioCtrl() {
        udal = new usuarioDAL();
        System.out.println("dentro usuario ctrl");

    }

    public void loguear() {
        //udal = new usuarioDAL();
        ut = new Utilerias();
    }

    public void loguea(ActionEvent event) {
        FacesContext cont = null;
        HttpSession ses = null;
        cont = FacesContext.getCurrentInstance();
        ses = (HttpSession) cont.getExternalContext().getSession(true);
        ses.removeAttribute("usuario");

        user = new Usuario();
        seleccionado = new Usuario();

        user.setEmpl("Jair");
        user.setUsua("Jair");
        user.setMail("jair");
        seleccionado = user;

        ses = (HttpSession) cont.getExternalContext().getSession(true);
        ses.setAttribute("usuario", user);

        try {
            cont.getExternalContext().redirect("web/index.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(usuarioCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void doi() {
        System.out.println("dentro de doi");
    }

    public Usuario getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Usuario seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getJ_password() {
        return j_password;
    }

    public void setJ_password(String j_password) {
        this.j_password = j_password;
    }

    public String getJ_usuario() {
        return j_usuario;
    }

    public void setJ_usuario(String j_usuario) {
        this.j_usuario = j_usuario;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void logout() {

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        usuarioDAL u = null;
        FacesContext cont = null;
        HttpSession ses = null;
        cont = FacesContext.getCurrentInstance();
        try {
            u = new usuarioDAL();
            user = null;
            seleccionado = null;
            ses = (HttpSession) cont.getExternalContext().getSession(true);
            ses.removeAttribute("usuario");
            ((HttpSession) ctx.getSession(false)).invalidate();

            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            ctx.redirect("https://login.windows.net/common/oauth2/logout?post_logout_redirect_uri=http://www.uqroo.mx");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public void validaEntrada() {
        //usuarioDAL udal = null;
        FacesContext cont = FacesContext.getCurrentInstance();
        HttpSession ses = null;
        Utilerias ute = new Utilerias();
        boolean UsuarioCorrecto = false;
        Exception x = null;

        try {
            this.view = null; 
            String view = "web/errores/errores.xhtml";

            HttpServletRequest req = (HttpServletRequest) cont.getExternalContext().getRequest();
            ses = req.getSession(true);
            ses.removeAttribute("usuario");

            user = new Usuario();
            this.seleccionado = user;
            this.seleccionado.setNombre("Jair");

            UsuarioCorrecto = true;

            if (UsuarioCorrecto) {
                view = "uqroo/web/index.xhtml";
                this.mensaje = "";
                ses.setAttribute("usuario", user);
            }

            try {
                cont.getExternalContext().redirect(view);
            } catch (Exception ex) {
                this.mensaje = ex != null ? ex.getMessage() : "";
                Logger.getLogger(usuarioCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            e.printStackTrace();
            x = e;
            UsuarioCorrecto = false;
            ute.mensaje(e.getMessage(), FacesMessage.SEVERITY_WARN);
        }

    }

    public void getListaAlumnos() throws Exception {
        this.view = "alumno/lista.xhtml"; 
        lista = udal.lista(); 
    }

    public void redirect(String modulo) {
        this.view = modulo + ".xhtml";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUrlError() {
        return urlError;
    }

    public void setUrlError(String urlError) {
        this.urlError = urlError;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
