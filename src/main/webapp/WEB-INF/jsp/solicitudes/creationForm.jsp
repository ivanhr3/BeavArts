<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


<beavarts:layout pageName="solicitud">
    <h1>
        Crear solicitud
    </h1>
    
    <h2><c:out value="${encargo.titulo}"/></h2>
    <h3>Publicado por: </h3><c:out value="${encargo.beaver.user.username}"/>
    <h3>Precio: </h3><c:out value="${encargo.precio}"/>
    <h2>Descripción: </h2>
    <b><c:out value="${encargo.descripcion}"/></b>
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <br/>
 <div class="container">   
    <form:form modelAttribute="solicitud" class="form-horizontal" id="add-solicitud-form">
        <div class="form-group has-feedback">
        <div class="form-group" >
            <beavarts:inputField label="*Descripción: " name="descripcion"/> 
            <p style="color:red; margin-top:2px"><c:out value="${descripcion}"/></p>
            <br/>
            <p style="color:black; margin-top:10px"><c:out value="Asegurese de escribir Urls en este campo."/></p>
    		<p style="color:black; margin-top:10px"><c:out value="Para añadir varias escriba: url1,url2 sin separar cada url con espacios"/></p>
            <beavarts:inputField label="Fotos: " name="fotos"/>
                   
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