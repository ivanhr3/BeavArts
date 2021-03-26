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
            <th>Nombre: </th>
            <td><c:out value="${beaver.firstName}"/></td>
        </tr>
        <tr>
            <th>Apellidos: </th>
            <td><c:out value="${beaver.lastName}"/></td>
        </tr>
        <tr>
            <th>Especialidades: </th>
            <td><c:out value="${beaver.especialidades}"/></td>
        </tr>

        <tr>
            <th>Sobre mi: </th>
            <td>AQUI VA EL TEXTO DE DESCRIPCIÓN DE LA PERSONA (POR IMPLEMENTAR)</td>
        </tr>

       <tr>
            <th>Portfolio:</th>
            <td><img width=200px height= auto src="/resources/images/imagenes/${encargo.photo}" alt ="Foto" />
            <c:if test="${beaver.user.username == principalUsername}">
       			<a class="btn btn-default">Añadir imágenes al Portfolio</a>
       		</c:if></td>
       		
        </tr>
        
        
        
    </table>
       
    <c:if test="${beaver.user.username == principalUsername}">
			<a class="btn btn-default">Editar Perfil</a>
			<a class="btn btn-default">Borrar Perfil</a>	
    </c:if>
    
    
    <br/>
    <br/>


</petclinic:layout>