<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://coverity.com/security" prefix="cov" %>
<html>
<head>
    <title>List page</title>
</head>
<body>

<h1>Add a User</h1>
<form:form method="post" modelAttribute="user" action="/blog/users">
  <script>var name='${user.name}';</script> <%-- XSS when overriding the User bean in UsersController.getUsers --%>

  <%-- We don't have XSSs here since it's HTML escaped by Spring --%>
  Name: <form:input path="name" /> <br />
  Email: <form:input path="email" /> <br />
  Password: <form:input path="password" /> <br />
  <input type="submit" value="Add user"/>
</form:form>

<c:if test="${not empty latestUser.name}">
  <h1>Latest User</h1>
  <ul id="all-users">
    <li data-id="${cov:htmlEscape(latestUser.email)}">${latestUser.name}</li> <%-- XSS with `latestUser.name` --%>
  </ul>
</c:if>


<h1>Selected Users (trace)</h1>
<ul id="results"></ul>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
  function loadUser(email) {
    $.get("/blog/users/search", {query: email}, function(data) {
      if (data.length < 1)
        return;

      for (var i=0; i<data.length; i++) {
        $("#results")
        .append(
          $("<li>").text(data[i].email) // No XSS here
        );
      }
    });
  }

  $("#all-users > li").bind(
    'click',
    function() {
       var elmt = $(this);
       loadUser(elmt.attr("data-id"));
    }
  );
});
</script>
</body>
</html>
