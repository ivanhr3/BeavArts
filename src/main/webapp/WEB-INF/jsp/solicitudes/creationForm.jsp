<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>


<beavarts:layout pageName="solicitud">

<c:if test="${esDeEncargo==true}">
  
    <h1 class="SegoeFont"><c:out value="Encargo: ${encargo.titulo}"/></h1>
    <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${encargo.beaver.id}"/>
    </spring:url>
                  <br/> 
    <b class="SegoeFont">Publicado por: </b><a href="${fn:escapeXml(beaverUrl)}"><c:out value=" ${encargo.beaver.user.username}"/></a>
    <br/>
    <b class="SegoeFont">Precio: </b><c:out value="${encargo.precio}"/>€
    <br/>
    <br/>
    <h2 class="SegoeFont">Descripción: </h2>
    <b><c:out value="${encargo.descripcion}"/></b>
 
 </c:if>
 
 
<c:if test="${esDeEncargo==false}">
  
    <h1 class="SegoeFont"><c:out value="Anuncio: ${anuncio.titulo}"/></h1>
    <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${anuncio.beaver.id}"/>
    </spring:url>
                  <br/> 
    <b class="SegoeFont">Publicado por: </b><a href="${fn:escapeXml(beaverUrl)}"><c:out value=" ${anuncio.beaver.user.username}"/></a>
    <br/>
    <b class="SegoeFont">Especialidad: </b>
    
    <div style="color:black" class="candidate-list-option">
		                          
		                          <c:choose>
	                      
					                      	<c:when test="${anuncio.especialidad == 'TEXTIL'}">
					                      		<i class="fas fa-socks"></i>
					                      	</c:when>
				                     
				                      		<c:when test="${anuncio.especialidad == 'ESCULTURA'}">
					                      		<i class="fas fa-chess-knight"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'ILUSTRACION'}">
					                      		<i class="fas fa-portrait"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'ACRILICO'}">
					                      		<i class="fas fa-paint-brush"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'OLEO'}">
					                      		<i class="fas fa-palette"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'JOYERIA'}">
					                      		<i class="fas fa-gem"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'RESINA'}">
					                      		<i class="fas fa-prescription-bottle"></i>
					                      	</c:when>
					                      	
					                      	<c:when test="${anuncio.especialidad == 'FOTOGRAFIA'}">
					                      		<i class="fas fa-camera-retro"></i>
					                      	</c:when>
				                      
				                      
				                      </c:choose>
		                          
	                    				<c:out value="${anuncio.especialidad} "/> &nbsp;
		                        
		                      </div>
    
    <br/>
    <b class="SegoeFont">Precio: </b><c:out value="${anuncio.precio}"/>€
    <br/>
    <br/>
    <h2 class="SegoeFont">Descripción: </h2>
    <b><c:out value="${anuncio.descripcion}"/></b>
 
 </c:if>
 
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <br/>
 <div class="container">   
    <form:form modelAttribute="solicitud" class="form-horizontal" id="add-solicitud-form">
        <div class="form-group has-feedback">
        <div class="form-group" >
        
 <%--      
        <c:if test="${esDeEncargo==false}">
        <b class="SegoeFont" style="margin-left:15px"> *Precio:</b>
            <beavarts:inputNumberField label="" name="precio"/> 
            <br/>
        </c:if>
        
      --%>    
         
        <b class="SegoeFont" style="margin-left:15px"> *Descripción:</b>
            <beavarts:inputField label="" name="descripcion"/> 
            <c:if test="${vacia}">
            <div class="alert alert-danger col-sm-10" role="alert">
            	<p><c:out value="${descripcion}"/></p>
            </div>
            </c:if>
            <br/>
         <div>
         <b class="SegoeFont" style="margin-left:15px"> Fotos:</b>
            <beavarts:inputField label="" name="fotos"/>
            <p class="SegoeFont" style="text-align:justify">Para introducir varias fotos separe las url por comas sin utilizar espacios.</p>
			<c:if test="${url}">
            <div class="alert alert-danger col-sm-10" role="alert">
            	<p><c:out value="${errorUrl}"/></p>
            </div>
            </c:if>
        </div>          
         </div>
         </div>         
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-primary" type="submit">Crear solicitud</button>   
            </div>
            <br/>
            <c:if test="${pendiente}">
            <div class="alert alert-danger col-sm-10" role="alert">
            	<p><c:out value="${error}"/></p>
            </div>
            </c:if>
        </div>
    </form:form>
</div>
</beavarts:layout>