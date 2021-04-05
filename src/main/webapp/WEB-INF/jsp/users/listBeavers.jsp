<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="beavers">
    <h2>Beavers</h2>

    <table id="beaversTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre de Usuario</th>
            <th style="width: 200px;">Especialidades</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${beavers}" var="beaver">
            <tr>
                <td>
                    <spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                        <spring:param name="beaverId" value="${beaver.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(beaverUrl)}"><c:out value="${beaver.user.username}"/></a>
                </td>
                <td>
                        <c:out value="${beaver.especialidades}"/>
                </td>              
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</beavarts:layout>