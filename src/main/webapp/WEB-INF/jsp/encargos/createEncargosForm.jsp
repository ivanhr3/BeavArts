<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>


<beavarts:layout pageName="encargos">
    <h2>
        <c:if test="${encargo['new']}">Nuevo </c:if> Encargo!
    </h2>
    <form:form modelAttribute="encargo" class="form-horizontal" id="add-encargo-form">
        <div class="form-group has-feedback">
            <beavarts:inputField label="Titulo" name="titulo"/>
            <beavarts:inputField label="Precio" name="precio"/>
            <beavarts:inputField label="Descripcion" name="descripcion"/>
            
            <div class="control-group" >
            	<label class = "col-md-2" for="photo">Disponibilidad: </label>
            	<select class="form-control" v-model="allowMultiple">
                    <option v-bind:value=true>Disponible</option>
                    <option v-bind:value=false>No disponible</option>
                </select>
            </div>
            
            <div class = "form-group row">
                   <label class = "col-md-2" for="photo">Imagen: </label>
                   <input type = "file" name = "file" class = "form-control form-control-sm col-md-6" id = "photo">
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