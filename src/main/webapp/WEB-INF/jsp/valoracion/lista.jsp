<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


<beavarts:layout pageName="valoracionesList">

    <h2>Beaver: <c:out value="${beaver.user.username}"/></h2>
    <br/>
    <h3>Especialidades: 
	<c:forEach items="${beaver.especialidades}" var="especialidad">
            <c:out value="${especialidad} "/>
    </c:forEach>    
    </h3>
    <br/>
    <br/>
    <h3>Valoraciones: </h3>
    
   	
<c:if test="${hayValoraciones==false}">
	<h4>No hay valoraciones</h4>
</c:if>

<ul class="list-group">
	
    <c:forEach items="${valoraciones}" var="valoracion">
	<li class="list-group-item">
		<div id=izquierda style="text-align: left;">                     
                <h6 class="list-group-item-heading">Puntuación: <c:out value="${valoracion.puntuacion}"/></h6>
           
            	<h6 class="list-group-item-text"> Comentario: <c:out value="${valoracion.comentario}"/></h6>
    	</div>
    </li>
   	</c:forEach>
</ul>
</beavarts:layout>