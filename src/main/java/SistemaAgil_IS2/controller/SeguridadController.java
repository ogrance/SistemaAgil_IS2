package SistemaAgil_IS2.controller;

import SistemaAgil_IS2.model.*;
import SistemaAgil_IS2.service.UsuarioService;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
@RequestMapping("/seguridad")
public class SeguridadController {
@Autowired
private UsuarioService usuarioService;

    @RequestMapping(value = "inicio_seguridad",method = RequestMethod.GET)
    public ModelAndView mostrarPaginaSeguridad(){
        ModelAndView mav=new ModelAndView("paginaSeguridad");
        mav.addObject("modulo","Seguridad");
        return mav;
    }
    
    @RequestMapping(value = "/usuarios",method = RequestMethod.GET)
    public ModelAndView muestraUsuarios() {
        ModelAndView mav = new ModelAndView("listaUsuarios");

        try {
            List<Usuario> usuarios = usuarioService.obtenerListaUsuarios();
            mav.addObject("listaUsuarios",usuarios);
        } catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }
    @RequestMapping(value = "/agregar_usuario",method = RequestMethod.GET)
    public ModelAndView muestraFormularioAgregar()  {
        ModelAndView mav=new ModelAndView("formularioAgregarUsuario");
        mav.addObject("usuario", new Usuario());
        return mav;
    }

    @PostMapping(value = "/inserta_usuario")
    public String insertaUsuarioBD(@ModelAttribute("usuario")Usuario user) throws Exception {
        usuarioService.insertarUsuario(user);
        return "redirect:/seguridad/usuarios";
    }
    @GetMapping("/formulario-actualizar-usuario")
    public ModelAndView muestraFormularioActualizar(@RequestParam("idUsuario") Integer idUsuario) throws Exception {
        ModelAndView mav=new ModelAndView();
        Usuario usu=usuarioService.obtenerUsuarioPorId(idUsuario);
        mav.addObject("usuario",usu);
        mav.setViewName("formularioAgregarUsuario");
        return mav;
    }
    @GetMapping("/formulario-eliminar-usuario")
    public String muestraFormularioEliminar(@RequestParam("idUsuario") Integer idUsuario) throws Exception {
        ModelAndView mav=new ModelAndView();
        System.out.println("Recibimos para eliminar ID= "+idUsuario);
        usuarioService.eliminarUsuario(idUsuario);
        return "redirect:/seguridad/usuarios";
    }
    @GetMapping("/formulario-asignar-rol")
    public ModelAndView muestraFormularioAsignarRoles(@RequestParam("idUsuario") Integer idUsuario) throws Exception{
        ModelAndView mav=new ModelAndView("formularioAsignarRoles");
        Usuario user= usuarioService.obtenerUsuarioPorId(idUsuario);
        List<Roles> rol= usuarioService.obtenerRoles();


        RolesDetalle detalle= new RolesDetalle(rol, user,new Roles());
        System.out.println(Arrays.asList(rol));
        mav.addObject("listaRolesDetalle",detalle);
        return mav;
    }
    @RequestMapping(value = "/asignar-rol",method = RequestMethod.GET)
    public ModelAndView  asignarRolUsuarioBD(@RequestParam("idRole") Integer roleId,@RequestParam("idUsuario") Integer idUsuario) throws Exception {
        ModelAndView mav = new ModelAndView("vistaRoles");
        System.out.println("Recibimos de Front"+"Id Role: "+roleId+" "+"Id Usuario: "+idUsuario);
        try{
            usuarioService.asignarRol(idUsuario, roleId);
        }catch(DuplicateKeyException e){

            mav.addObject("ErrorKeyDuplicada", "Se esta intentando asignar un rol que ya pertenecia al Usuario");
        }

        List<RolesDetalle> rolesDetalles = usuarioService.obtenerUsuarioYRol(idUsuario);
        mav.addObject("rolesDetalles",rolesDetalles);
        return mav;
        //return "redirect:/seguridad/roles-usuarios";
    }
    @GetMapping("/formulario-eliminar-rol-usuario")
    public String  muestraFormularioEliminarRol(@RequestParam("idRole") Integer idRole,@RequestParam("idUsuario")Integer idUsuario, Model modelo) throws Exception {
       // ModelAndView mav=new ModelAndView();
        UsuarioRol usuarioRol=new UsuarioRol(idUsuario,idRole);
        try{
            usuarioService.eliminarUsuarioRol(usuarioRol);
            List<RolesDetalle> rolesDetalles=usuarioService.obtenerUsuarioYRol(idUsuario);
            modelo.addAttribute("rolesDetalles",rolesDetalles);
        }catch (Exception e){
            modelo.addAttribute("ErrorDelete", "No se pudo eliminar el rol del usuario"+" "+e.getMessage());
            return "vistaRoles";
        }


        return "vistaRoles";
    }

    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    public ModelAndView muestraRoles() {
        ModelAndView mav = new ModelAndView("listaRoles");

        try {
            List<Roles> listaRoles = usuarioService.obtenerRoles();
            mav.addObject("listaRoles",listaRoles);
        } catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }   
    @RequestMapping(value = "/agregar-rol",method = RequestMethod.GET)
    public ModelAndView muestraFormularioAgregarRol()  {
        ModelAndView mav=new ModelAndView("formularioAgregarRol");
        mav.addObject("roles", new Roles());
        return mav;
    }
    @PostMapping(value = "/inserta-rol")
    public String insertaRolBD(@ModelAttribute("roles")Roles rol) throws Exception {
        usuarioService.insertarRol(rol);
        return "redirect:/seguridad/roles";
    }
    @GetMapping("/formulario-actualizar-rol")
    public ModelAndView muestraFormularioActualizarRol(@RequestParam("idRole") Integer idRol) throws Exception {
        ModelAndView mav=new ModelAndView();
        Roles roles=usuarioService.obtenerRolPorId(idRol);
        mav.addObject("roles",roles);
        mav.setViewName("formularioAgregarRol");
        return mav;
    }
    @GetMapping("/formulario-eliminar-rol")
    public String muestraFormularioEliminarRoles(@RequestParam("idRole") Integer idRol) throws Exception {
        ModelAndView mav=new ModelAndView();
        System.out.println("Recibimos para eliminar ID= "+idRol);
        try{
            usuarioService.eliminarRol(idRol);
        }catch (Exception e){
            mav.addObject("ErrorEliminarRol", "No se pudo eliminar el Rol ");
        }

        return "redirect:/seguridad/roles";
    }
    @RequestMapping(value = "/permisos",method = RequestMethod.GET)
    public ModelAndView muestraPermisos() {
        ModelAndView mav = new ModelAndView("listaPermisos");

        try {
            List<Permisos> listaPermisos = usuarioService.obtenerPermisos();
            mav.addObject("listaPermisos",listaPermisos);
        } catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }
    @RequestMapping(value = "/agregar-permiso",method = RequestMethod.GET)
    public ModelAndView muestraFormularioAgregarPermiso()  {
        ModelAndView mav=new ModelAndView("formularioAgregarPermiso");
        mav.addObject("permisos", new Permisos());
        return mav;
    }
    @PostMapping(value = "/inserta-permiso")
    public String insertaPermisoBD(@ModelAttribute("permisos")Permisos permiso) throws Exception {

        try{
            usuarioService.insertarPermiso(permiso);
        }catch (MysqlDataTruncation e){
            e.printStackTrace();


        }

        return "redirect:/seguridad/permisos";
    }
    @GetMapping("/formulario-actualizar-permiso")
    public ModelAndView muestraFormularioActualizarPermiso(@RequestParam("idPermiso") Integer idPermiso) throws Exception {
        ModelAndView mav=new ModelAndView();
        Permisos permiso=usuarioService.obtenerPermisoPorId(idPermiso);
        mav.addObject("permisos",permiso);
        mav.setViewName("formularioAgregarPermiso");
        return mav;
    }
    @GetMapping("/formulario-eliminar-permiso")
    public String muestraFormularioEliminarPermisos(@RequestParam("idPermiso") Integer idPermiso) throws Exception {
        ModelAndView mav=new ModelAndView();
        System.out.println("Recibimos para eliminar ID= "+idPermiso);
        try{
            usuarioService.eliminarPermiso(idPermiso);
        }catch (Exception e){
            mav.addObject("ErrorEliminarPermiso", "No se pudo eliminar el Permiso ");
        }

        return "redirect:/seguridad/permisos";
    }
    @GetMapping("/formulario-asignar-rol-permiso")
    public ModelAndView muestraFormularioAsignarRolesPermisos(@RequestParam("idPermiso") Integer idPermiso) throws Exception {
        ModelAndView mav = new ModelAndView("formularioAsignarRolesPermisos");
        Permisos permiso = usuarioService.obtenerPermisoPorId(idPermiso);
        List<Roles> rol = usuarioService.obtenerRoles();


        PermisosDetalle detalle = new PermisosDetalle(rol, permiso, new Roles());
        System.out.println(Arrays.asList(permiso));
        mav.addObject("listaPermisosDetalle", detalle);
        return mav;
    }
    @RequestMapping(value = "/asignar-rol-permiso",method = RequestMethod.GET)
    public ModelAndView  asignarRolPermisoBD(@RequestParam("idRole") Integer roleId,@RequestParam("idPermiso") Integer idPermiso) throws Exception {
        ModelAndView mav = new ModelAndView("vistaPermisos");
        System.out.println("Recibimos de Front"+"Id Role: "+roleId+" "+"Id Permiso: "+idPermiso);
        try{
            usuarioService.insertaPermisosAsignados(roleId, idPermiso);
        }catch(DuplicateKeyException e){

            mav.addObject("ErrorKeyDuplicada", "Se esta intentando asignar un permiso que ya pertenecia al Rol");
        }

        List<PermisosDetalle> permisoDetalles = usuarioService.obtenerListaPermisosAsignados(idPermiso);
        mav.addObject("permisoDetalles",permisoDetalles);
        return mav;
        //return "redirect:/seguridad/roles-usuarios";
    }
    @GetMapping("/formulario-eliminar-rol-permiso")
    public String  muestraFormularioEliminarRolPermiso(@RequestParam("idRole") Integer idRole,@RequestParam("idPermiso")Integer idPermiso, Model modelo) throws Exception {
        // ModelAndView mav=new ModelAndView();

        try{
            usuarioService.eliminarAsignacionPermiso(idRole, idPermiso);
            List<PermisosDetalle> permisosDetalles=usuarioService.obtenerListaPermisosAsignados(idPermiso);
            modelo.addAttribute("permisoDetalles",permisosDetalles);
        }catch (Exception e){
            modelo.addAttribute("ErrorDelete", "No se pudo eliminar el permiso del rol"+" "+e.getMessage());
            return "vistaRoles";
        }


        return "vistaPermisos";
    }
    

}
