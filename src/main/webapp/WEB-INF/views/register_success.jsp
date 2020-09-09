<%--
  Created by IntelliJ IDEA.
  User: aprabhat
  Date: 08/09/20
  Time: 11:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success Registration</title>
</head>
<body>
<div align="center">
    <h2>Registration Succeeded!</h2>
    <span>Full name:</span><span>${user.name}</span><br/>
    <span>E-mail:</span><span>${user.email}</span><br/>
    <span>Password:</span><span>${user.password}</span><br/>
    <span>Birthday:</span><span>${user.birthday}</span><br/>
    <span>Gender:</span><span>${user.gender}</span><br/>
    <span>Profession:</span><span>${user.profession}</span><br/>
    <span>Married?:</span><span>${user.married}</span><br/>
    <span>Note:</span><span>${user.note}</span><br/>
</div>

</body>
</html>
