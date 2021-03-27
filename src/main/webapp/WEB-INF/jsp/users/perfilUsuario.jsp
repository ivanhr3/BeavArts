<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
<petclinic:layout pageName="miPerfil">
<h2>${beaver.user.username}</h2>

    <table class="table table-striped">
        <tr>
            <th>Especialidades: </th>
            <td><c:out value="${beaver.especialidades}"/></td>
        </tr>

        <tr>
            <th>Sobre mi: </th>
            <td><c:out value="${beaver.perfil.descripcion}"/></td>
        </tr>

        <tr>
            <th>Valoración: </th>
            <td>3/5 Estrellas - DECENTE</td>
        </tr>

       <tr>
            <th>Portfolio:</th>
            <td><img width=200px height= auto src="/resources/images/imagenes/${encargo.photo}" alt ="Foto" /></td>
        </tr>
        
    </table>
    
    <div>
        <spring:url value="/beavers/{beaverId}/encargos/list" var="listUrl">
                        <spring:param name="beaverId" value="${beaver.id}"/>
                </spring:url>  
        <b>¿Te gusta mi trabajo? Puedes solicitar uno de mis </b><a href="${fn:escapeXml(listUrl)}"><b>Encargos</b></a>
        </div>
        
    <div>
    <c:if test="${beaver.user.username != principalUsername}">
				<a class="btn btn-default">Valorar</a>
     </c:if>
    </div>
    
    <br/>
    <br/>


</petclinic:layout>