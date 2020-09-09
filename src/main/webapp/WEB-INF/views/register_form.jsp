<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: aprabhat
  Date: 08/09/20
  Time: 11:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ecos XML Consumption Demo</title>
</head>
<body>
<div align="center">
    <h2>Select XML input</h2>
    <form:form action="register_success" method="post" modelAttribute="xmlList">
        Select an input:&nbsp;
        <form:select path="profession" name="xmlInput">
            <c:forEach items="${userData.inputList}" var="xmlItem">
                <option value="${xmlItem.value}">${xmlItem.index}</option>
            </c:forEach>
        </form:select>
        <br/><br/>
        <input type="submit" value="Submit" />
    </form:form>

    <%--<h2>User Registration</h2>--%>
    <%--<form:form action="register" method="post" modelAttribute="userData">--%>
        <%--<form:label path="name"></form:label>--%>
        <%--<form:input path="name"/><br/>--%>

        <%--<form:label path="email">E-mail:</form:label>--%>
        <%--<form:input path="email"/><br/>--%>

        <%--<form:label path="password">Password:</form:label>--%>
        <%--<form:password path="password"/><br/>--%>

        <%--<form:label path="birthday">Birthday (yyyy-mm-dd):</form:label>--%>
        <%--<form:input path="birthday"/><br/>--%>

        <%--<form:label path="gender">Gender:</form:label>--%>
        <%--<form:radiobutton path="gender" value="Male"/>Male--%>
        <%--<form:radiobutton path="gender" value="Female"/>Female<br/>--%>

        <%--<form:label path="profession">Profession:</form:label>--%>
        <%--<form:select path="profession" items="${userData.map}" /><br/>--%>

        <%--<form:label path="note">Xml input</form:label>--%>
        <%--<form:textarea path="note" cols="25" rows="5"/><br/>--%>

        <%--<form:button>Register</form:button>--%>
    <%--</form:form>--%>
</div>
</body>
</html>
