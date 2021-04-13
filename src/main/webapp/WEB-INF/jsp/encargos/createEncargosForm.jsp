<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<link rel=“less” type=“text/css” href=“petclinic.scss”/>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="Encargos">
    <h2 class="SegoeFont">
        <c:if test="${!editando}">Registrar </c:if> 
        <c:if test="${editando}">Editar </c:if> Encargo
    </h2>
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <div class="container justify-content-center" style="display:block;">
    <form:form modelAttribute="encargo" class="form-horizontal" id="add-encargo-form">
        <div class="form-group has-feedback">
        <div class="form-group">
        	<b class="SegoeFont" style="margin-left:15px"> *Título:</b>
            <beavarts:inputField label="" name="titulo" readonly="${isDisponible}"/>
            <b class="SegoeFont" style="margin-left:15px"> *Precio:</b>
            <beavarts:inputNumberField label="" name="precio" placeholder="0.00" readonly="${isDisponible}"/>
            <b class="SegoeFont" style="margin-left:15px"> *Descripción:</b>
            <beavarts:inputField label="" name="descripcion" readonly="${isDisponible}"/>
                   
             <div class="form-group">     
                <c:if test="${encargo.disponibilidad == true}">
                <b class="SegoeFont" style="margin-left:15px"> *Disponibilidad:</b>
                <br/>
	            	<label class = "col-sm-2 control-label" for="disponibilidad"> </label>
	            		<div class="col-sm-3">
			            	<select class="form-control" name="disponibilidad">
			                    <option value=true>Disponible</option>
			                    <option value=false>No disponible</option>
			                </select>
	            		</div>
            	</c:if>  
            	          
                <c:if test="${encargo.disponibilidad != true}">
                 <b class="SegoeFont" style="margin-left:15px"> *Disponibilidad:</b>
                 <br/>
	            	<label class = "col-sm-2 control-label" for="disponibilidad"></label>
	            		<div class="col-sm-3">
			            	<select class="form-control" name="disponibilidad">
			            	<option value=false>No Disponible</option>
			            		<option value=true>Disponible</option>
			                </select>
	            		</div>            		
            	</c:if>
    		</div>
            <br/>
            <b class="SegoeFont" style="margin-left:15px"> Introduce URL de la foto:</b>
           <beavarts:inputField label="" name="photo" readonly="${isDisponible}"/>
           </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${!editando}">
                        <button class="btn btn-primary" type="submit">Crear encargo</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary" type="submit">Actualizar encargo</button>
                        <br/>
                        <c:if test="${url == true}">
                        	<div class="alert alert-danger" role="alert">
								<c:out value="${errorDisponibilidad}"/>
							</div>
						</c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </div>
</beavarts:layout>