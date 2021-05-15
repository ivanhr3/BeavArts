<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="Especialidades">
<div class="minAlto">
    <h2 class="Roboto"> Editar especialidades: </h2>
    <br/>    
    <form:form modelAttribute="beaver" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
              <div class="form-group">
                <label class="col control-label">Especialidades:</label>
                <div class="col-sm-10">
                  <select id="especialidades" name="especialidades" class="form-control" multiple="multiple" size="8">
                    <option value="ACRILICO">ACRILICO</option>
                    <option value="ESCULTURA">ESCULTURA</option>
                    <option value="FOTOGRAFIA">FOTOGRAFIA</option>
                    <option value="ILUSTRACION">ILUSTRACION</option>
                    <option value="JOYERIA">JOYERIA</option>
                    <option value="RESINA">RESINA</option>
                    <option value="TEXTIL">TEXTIL</option>
                    <option value="OLEO">OLEO</option>
                  </select>
                  <input type="hidden" name="_especialidades" value="1">
                  <span class="form-control-feedback" aria-hidden="true"></span>
                </div>
                <script>
                  window.onmousedown = function (e) {
                    var el = e.target;
                    if (el.tagName.toLowerCase() == 'option' && el.parentNode.hasAttribute('multiple')) {
                        e.preventDefault();

                        // toggle selection
                        if (el.hasAttribute('selected')) el.removeAttribute('selected');
                        else el.setAttribute('selected', '');

                        // hack to correct buggy behavior
                        var select = el.parentNode.cloneNode(true);
                        el.parentNode.parentNode.replaceChild(select, el.parentNode);
                    }
                  }
                </script>
              </div>
          </div>
        <c:if test="${errorUrl != null}">
                        	<div class="alert alert-danger col-sm-10" role="alert">
								<c:out value="${errorUrl}"/>
							</div>
						</c:if>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-primary" type="submit">Actualizar</button>           
            </div>
        </div>
        <div class="control-group hidden" style="display: none">
        	
        	<input name="firstName" value="${beaver.firstName}"><br>
        	<input name="lastName" value="${beaver.lastName}"><br>
        	<input name="dni" value="${beaver.dni}"><br>
        </div>
    </form:form>
    </div>
</petclinic:layout>
