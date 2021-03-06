<%@page import="SistemaAgil_IS2.model.Sprint"%>
<%@page import="SistemaAgil_IS2.model.Backlog"%>
<%@page import="SistemaAgil_IS2.dao.DesarrolloDaoImpl"%>
<%@page import="java.util.Iterator"%>
<%@page import="SistemaAgil_IS2.model.Project"%>
<%@page import="java.util.List"%>
<%@page import="SistemaAgil_IS2.dao.ProjectDaoImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Sprints</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/estilo_lista_usuarios.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/navbar.css">
    </head>
    <ul>
        <li><a href="ProyectoController?accion=home">Home</a></li>
        <li><a href="DesarrolloController?accion=Desarrollo">Pag. Desarrollo</a></li>
        <li><a href="DesarrolloController?accion=Gestion de Sprints">Gestion de Sprints</a></li>
        <li style="float:right"><a class="active" href="login">Cerrar Sesion</a></li>
    </ul>
    <body>
        <table class="usuarios-tabla">
            <thead>
                <tr>
                    <th>Sprint Id</th>
                    <th>Project Id</th>
                    <th>Nombre</th>
                    <th>Duracion</th>
                    <th>Estatus</th>
                </tr>
            </thead>
            <%
                DesarrolloDaoImpl dao = new DesarrolloDaoImpl();
                List<Sprint> list = dao.listars(2);
                Iterator<Sprint> iter = list.iterator();
                Sprint sprnt = null;
                while (iter.hasNext()) {
                    sprnt = iter.next();

            %>
            <tbody>
                <tr>
                    <td><%= sprnt.getId_sprint()%></td>
                    <td><%= sprnt.getProject_id()%></td>
                    <td><%= sprnt.getName()%></td>
                    <td><%= sprnt.getDuration()%></td>
                    <td><%= sprnt.getEstatus()%></td>
                </tr>
                <%}%>
            </tbody>

        </table>
        <br/>
    </body>
</html>