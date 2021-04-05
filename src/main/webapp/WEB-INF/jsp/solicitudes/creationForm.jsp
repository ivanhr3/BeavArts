<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


<beavarts:layout pageName="solicitud">
    <h2>
        Crear solicitud
    </h2>
    
    <h1><c:out value="Encargo: ${encargo.titulo}"/></h1>
    <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${encargo.beaver.id}"/>
    </spring:url>
                    
    <b>Publicado por: </b><a href="${fn:escapeXml(beaverUrl)}"><c:out value=" ${encargo.beaver.user.username}"/></a>
    <br/>
    <b>Precio: </b><c:out value="${encargo.precio}"/>€
    <br/>
    <br/>
    <h2>Descripción: </h2>
    <b><c:out value="${encargo.descripcion}"/></b>
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <br/>
 <div class="container">   
    <form:form modelAttribute="solicitud" class="form-horizontal" id="add-solicitud-form">
        <div class="form-group has-feedback">
        <div class="form-group" >
            <beavarts:inputField label="*Descripción: " name="descripcion"/> 
            <p style="color:red; margin-top:2px; text-align:right"><c:out value="${descripcion}"/></p>
            <br/>
            
    		<p style="text-align:right">Para introducir varias fotos separe las url por comas sin utilizar espacios.</p>
            <beavarts:inputField label="Fotos: " name="fotos"/>
            <p style="color:red; text-align:right"><c:out value="${errorUrl}"/></p>
                   
         </div>
         </div>         
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Crear solicitud</button>   
            </div>
            <p style="color:red; margin-top:2px"><c:out value="${error}"/></p>
        </div>
    </form:form>
</div>
</beavarts:layout>