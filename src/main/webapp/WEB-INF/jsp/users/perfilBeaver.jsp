<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
<petclinic:layout pageName="perfil">

<div>

	<div style="margin-bottom:20px" class="izquierda">
		<img width=150px height=130px hspace="20"; src="${beaver.urlFotoPerfil}"/>
	</div>
	
	<div class="izquierda">
		<h1>${beaver.user.username}</h1> 
		<p style="font-weight:bold">Especialidades: <c:forEach items="${beaver.especialidades}" var="especialidad">
	                    		<c:out value="${especialidad} "/>
	                		</c:forEach></p>
		<p style="font-weight:bold">Sobre mi: <c:out value="${beaver.portfolio.sobreMi}"/></p>
		<p style="font-weight:bold">Valoración: 3/5 Estrellas - DECENTE</p>
		
	</div>
	
</div>
<div class="izquierda2">
  
            <h1>Portfolio:</h1> 
            
            <c:forEach items="${beaver.portfolio.photos}" var="foto">	                    		
	                    		<img width=150px height=130px hspace="20"; src="${foto}"/>
	         </c:forEach>

     <br/>
   
    <c:if test="${beaver.user.username == principalUsername}"> 
    	<br/>   
    	<h2>Datos Personales:</h2>    
     </c:if>
     
 </div>
      
 <c:if test="${beaver.user.username == principalUsername}"> 
 
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
            <th>Dni: </th>
            <td><c:out value="${beaver.dni}"/></td>
        </tr>
       
         <tr>
            <th>Email: </th>
            <td><c:out value="${beaver.email}"/></td>
        </tr>
        
        

           
    </table>
     
    </c:if>
 
    
        
    <div class="izquierda2">
    	<br/>
    	<c:if test="${beaver.user.username != principalUsername}">
	    	<div>
		        <spring:url value="/beavers/{beaverId}/encargos/list" var="listUrl">
		                        <spring:param name="beaverId" value="${beaver.id}"/>
	            </spring:url>  
	        	<b style="font-size:20px">¿Te gusta mi trabajo? Puedes solicitar uno de mis </b><a href="${fn:escapeXml(listUrl)}"><b style="font-size:20px">Encargos</b></a>
	        </div>
	        
	        <br/>
	        <a class="btn btn-default">Valorar</a>
		</c:if>
		<c:if test="${beaver.user.username == principalUsername}">
			<a class="btn btn-default" href='<spring:url value="/beavers/beaverInfo/${beaver.id}/portfolio/edit" htmlEscape="true"/>'>Editar perfil</a>
				
			<a class="btn btn-default">Borrar Perfil</a>
		</c:if>
	</div>
</petclinic:layout>