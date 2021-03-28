<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


<beavarts:layout pageName="encargos">
    <h2>
        <c:if test="${encargo['new']}">Registrar </c:if> Encargo
    </h2>
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    
    <form:form modelAttribute="encargo" class="form-horizontal" id="add-encargo-form">
        <div class="form-group has-feedback">
        <div class="form-group" >
            <beavarts:inputField label="*Título:" name="titulo"/>
            <beavarts:inputField label="*Precio:" name="precio"/>
            <beavarts:inputField label="*Descripción:" name="descripcion"/>
        </div>    
            <div class="form-group" >
            	<label class = "col-sm-2 control-label" for="disponibilidad">*Disponibilidad: </label>
            	<select class="control-group">
                    <option value=true>Disponible</option>
                    <option value=false>No disponible</option>
                </select>
            </div>
            <br/>
            <div class = "col-sm-12 form-group">
                   <label class = "col-sm-2 control-label" for="photo">Añade alguna imagen </label>
                   <input type = "file" name = "file" class = "control-group" id = "photo">
            </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${encargo['new']}">
                        <button class="btn btn-default" type="submit">Crear encargo</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar encargo</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</beavarts:layout>