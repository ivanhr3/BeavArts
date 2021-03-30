<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E"%> --%>

<petclinic:layout pageName="solicitudesList">
    <h2>Mis solicitudes</h2>

    <table id="solicitudesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Estado</th>
            <th style="width: 200px;">Precio</th>
            <th style="width: 120px">Titulo</th>
            <th style="width: 120px">Descripcion</th>
            <th style="width: 120px">Usuario</th>
            <th style="width: 120px">Detalles de solicitudes</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${listaSolicitudes}" var="solicitud">

            <tr>
                <td>
                    <c:out value="${solicitud.estado}"/>
                </td>
                <td>
                    <c:out value="${solicitud.precio}"/>
                </td>
                <td>
                    <c:out value="${solicitud.encargo.titulo}"/>
                </td>
                <td>
                    <c:out value="${solicitud.encargo.descripcion}"/>
                </td>
                <td>                 
                 <c:out value="${solicitud.beaver.user.username}"/>
                 </td>  
                 <spring:url value="/solicitudes/solicitudInfo/${solicitud.id}" var="detallesUrl">
                </spring:url>
                <td>
                		<a class="btn btn-default" href="${fn:escapeXml(detallesUrl)}" >Ver solicitud</a>
                </td>
         </tr>
         </c:forEach>
        </tbody>
    </table>
</petclinic:layout>