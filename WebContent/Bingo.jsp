<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>

<c:import url="file.xml" var="xml"/>
<x:parse xml="${xml}" varDom="dom"/>
<x:out select="${dom}"/>
