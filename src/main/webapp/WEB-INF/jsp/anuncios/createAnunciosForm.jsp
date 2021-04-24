<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<link rel=“less” type=“text/css” href=“petclinic.scss”/>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="crearAnuncios">
    <h2 class="SegoeFont">
       	<c:if test="${!editando}">Registrar </c:if> 
        <c:if test="${editando}">Editar </c:if> Anuncio
    </h2>
    
    <p style="color:red; margin-top:10px"><c:out value=" Los campos señalados con * son obligatorios"/></p>
    <div class="container justify-content-center" style="display:block;">
    <form:form modelAttribute="anuncio" class="form-horizontal" id="add-anuncio-form">
        <div class="form-group has-feedback">
        <div class="form-group">
        	<b style="margin-left:15px">*Título:</b>
            <beavarts:inputField label="" name="titulo"/>
            <b style="margin-left:15px">*Precio:</b>
			<beavarts:inputNumberField label="" name="precio" placeholder="0.00"/>
			<b style="margin-left:15px">*Descripción:</b>
			<beavarts:inputField label="" name="descripcion"/>
            <div class="control-group">
            <b style="margin-left:15px">Seleccione una categoría</b>
            	<beavarts:selectField name="especialidad" label="" names="${types}" size="8"/>
            </div>
            <b style="margin-left:15px">Introduce URL de la foto:</b>
            <beavarts:inputField label="" name="photo"/>
           </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${!editando}">
                        <button class="btn btn-primary" type="submit">Crear anuncio</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary" type="submit">Actualizar anuncio</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </div>
</beavarts:layout>