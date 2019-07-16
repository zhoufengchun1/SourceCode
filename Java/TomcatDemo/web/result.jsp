<%--
  Created by IntelliJ IDEA.
  User: hbtsk
  Date: 2019/7/15
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <%
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies)
            {
                out.print(cookie.getName() + "..." + cookie.getValue() + "<br>");
            }
        %>
    </body>
</html>
