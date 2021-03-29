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
        <c:if test="${!editando}">Registrar </c:if> 
        <c:if test="${editando}">Editar </c:if> Encargo
    </h2>
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <br/>
    <form:form modelAttribute="encargo" class="form-horizontal" id="add-encargo-form">
        <div class="form-group has-feedback">
        <div class="form-group" >
            <beavarts:inputField label="*Título:" name="titulo" readonly="${isDisponible}"/>
            <beavarts:inputField label="*Precio:" name="precio" readonly="${isDisponible}"/>
            <beavarts:inputField label="*Descripción:" name="descripcion" readonly="${isDisponible}"/>
                   
                   
                <c:if test="${encargo.disponibilidad == true}">
	            	<label class = "col-sm-2 control-label" for="disponibilidad">*Disponibilidad: </label>
	            		<div class="col-sm-2">
			            	<select class="form-control" name="disponibilidad">
			                    <option value=true>Disponible</option>
			                    <option value=false>No disponible</option>
			                </select>
	            		</div>
            	</c:if>  
            	          
                <c:if test="${encargo.disponibilidad != true}">        		
	            	<label class = "col-sm-2 control-label" for="disponibilidad">*Disponibilidad: </label>
	            		<div class="col-sm-2">
			            	<select class="form-control" name="disponibilidad">
			                    <option value=false>No Disponible</option>
			                    <option value=true>Disponible</option>
			                </select>
	            		</div>            		
            	</c:if>
            
            		
    		</div>
            <br/>
            <div class = "col-sm-12 form-group">
                   <label class = "col-sm-2 control-label" for="photo">Añade alguna imagen: </label>
                   <input style="margin-top:6px" type = "file" name = "file" id = "photo">                 
            </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${!editando}">
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