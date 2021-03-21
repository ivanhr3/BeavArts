<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="encargosList">
    <h2>Mis Encargos</h2>

    <table id="encargosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Título</th>
            <th style="width: 200px;">Precio</th>
            <th style="width: 120px">Descripción</th>
            <th style="width: 120px">Fotos</th>
            <th style="width: 120px">Publicado por</th>
        </tr>
        </thead>
        
        <tbody>
        <c:forEach items="${encargos}" var="encargo">
        
            <tr>
                <td>
                    <c:out value="${encargo.titulo}"/></a>
                </td>
                <td>
                    <c:out value="${encargo.precio}"/>
                </td>
                <td>
                	<c:out value="${encargo.descripcion}"/>
                </td>
                <td>
                	<c:out value="${encargo.photo}"/>
                </td>
                <td>
                	<c:out value="${encargo.beaver}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>