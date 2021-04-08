<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Placed at the end of the document so the pages load faster --%>
<spring:url value="/webjars/jquery/2.2.4/jquery.min.js" var="jQuery"/>
<script src="${jQuery}"></script>

<%-- jquery-ui.js file is really big so we only load what we need instead of loading everything --%>
<spring:url value="/webjars/jquery-ui/1.11.4/jquery-ui.min.js" var="jQueryUiCore"/>
<script src="${jQueryUiCore}"></script>

<%-- Bootstrap --%>
<spring:url value="/webjars/bootstrap/4.0.0/js/bootstrap.min.js" var="bootstrapJs"/>
<script src="${bootstrapJs}"></script>


<footer class="text-center text-white navbar navbar-fixed-bottom" style="background-color: #7E3E08; height: 80px;">
  <!-- Grid container -->
  <div class="container pt-4">
    <!-- Section: Social media -->
    <section class="mb-4">

      <!-- Twitter -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        style="color: #FFFFFF;"
        href="https://twitter.com/beavarts"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fa fa-twitter"></i
      ></a>

      <!-- Instagram -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        style="color: #FFFFFF;"
        href="https://www.instagram.com/beavarts_/"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fa fa-instagram"></i
      ></a>

      <!-- Github -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        style="color: #FFFFFF;"
        href="https://github.com/ivanhr3/BeavArts"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fa fa-github"></i
      ></a>
    </section>
    <!-- Section: Social media -->
  </div>
  <!-- Grid container -->

  <!-- Copyright -->
  <p style="color: #FFFFFF;">&copy; <strong>2021</strong>. Designed by Beavarts</p>
  <!-- Copyright -->
</footer>

