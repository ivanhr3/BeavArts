<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="beavers">
    <h2> Datos de personales: </h2>
    <form:form modelAttribute="beaver" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="firstName"/>
            <petclinic:inputField label="Last Name" name="lastName"/>
            <petclinic:inputField label="Especialidades" name="especialidades"/>
            <petclinic:inputField label="DNI" name="dni"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Username" name="user.username"/>
            <petclinic:inputField label="Password" name="user.password"/>
            
            <h2>Datos de perfil:</h2>
            
            <petclinic:inputField label="Foto de usuario" name="fotoPerfil"/>       
            <petclinic:inputField label="Sobre mi" name="perfil.descripcion"/>
            <petclinic:inputField label="Portfolio" name="perfil.portfolio"/>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-default" type="submit">Actualizar Beaver</button>
                 
            </div>
        </div>
    </form:form>
</petclinic:layout>
