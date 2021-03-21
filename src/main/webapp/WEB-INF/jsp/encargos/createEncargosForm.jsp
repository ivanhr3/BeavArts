<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="encargos">
    <h2>
        <c:if test="${encargo['new']}">Nuevo </c:if> Encargo!
    </h2>
    <form:form modelAttribute="encargo" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Titulo" name="titulo"/>
            <petclinic:inputField label="Precio" name="precio"/>
            <petclinic:inputField label="Disponibilidad" name="disponibilidad"/>
            <!-- <petclinic:selectField label="Disponibilidad" name="disponibilidad" names="${booleanList}" size="2"/> -->
            <petclinic:inputField label="Descripcion" name="descripcion"/>
            <petclinic:inputField label="Foto" name="photo"/>
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
</petclinic:layout>
