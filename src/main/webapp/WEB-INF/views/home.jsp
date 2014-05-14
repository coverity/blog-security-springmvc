<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://coverity.com/security" prefix="cov" %>
<html>
<head>
	<title>Welcome</title>
</head>
<body>
<c:choose>
  <c:when test="${not empty current_user.name}">
    Hello ${cov:htmlEscape(current_user.name)}! <%-- No XSS here --%>
  </c:when>
  <c:otherwise>
    The bean field `name` is just reflected, such as <a href="/blog/index?name=TheDoctor">here</a>.
  </c:otherwise>
</c:choose>
</body>
</html>
