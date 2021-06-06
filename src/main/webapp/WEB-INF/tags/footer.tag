<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<%-- Placed at the end of the document so the pages load faster --%>
<spring:url value="/webjars/jquery/2.2.4/jquery.min.js" var="jQuery"/>
<script src="${jQuery}"></script>

<%-- jquery-ui.js file is really big so we only load what we need instead of loading everything --%>
<spring:url value="/webjars/jquery-ui/1.11.4/jquery-ui.min.js" var="jQueryUiCore"/>
<script src="${jQueryUiCore}"></script>

<%-- Bootstrap --%>
<spring:url value="/webjars/bootstrap/4.0.0/js/bootstrap.min.js" var="bootstrapJs"/>
<script src="${bootstrapJs}"></script>


<!-- Footer -->
<footer class="footerBot">

 
  <!-- Copyright -->
  <div class="text-center p-3 footerColor">
  <!-- Twitter -->
      <a
        class="btn btn-link btn-floating btn-lg text-light m-1"        
        href="https://twitter.com/beavarts"
        role="button"
        data-mdb-ripple-color="dark"
        ><i style="color: #ffffff;" class="fab fa-twitter"></i
      ></a>

      <!-- Instagram -->
      <a
        class="btn btn-link btn-floating btn-lg text-light m-1"        
        href="https://www.instagram.com/beavarts_/"
        role="button"
        data-mdb-ripple-color="dark"
        ><i style="color: #ffffff;" class="fab fa-instagram"></i
      ></a>

      <!-- Github -->
      <a
        class="btn btn-link btn-floating btn-lg text-light m-1"       
        href="https://github.com/ivanhr3/BeavArts"
        role="button"
        data-mdb-ripple-color="dark"
        ><i style="color: #ffffff;" class="fab fa-github"></i></a>
    <p class="responsiveFontSmartphoneTexto" style="color: #ffffff;">&copy; <strong>2021</strong>. Designed by Beavarts</p>
    <a class="btn btn-link responsiveFontSmartphoneTexto" href="/terminos" style="color: #ffffff;"><fmt:message key="footer.terminoscondiciones"/></a>

  </div>
  <!-- Copyright -->
</footer>
<!-- Footer -->