<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<div align="left">
    <h1 style="color: orangered"><b>Here are all the results matching your search criteria:</b></h1>
    <br>
</div>

<c:forEach items="${list}" var = "current">
    <br>
    <h1 style="color:orangered">  <a href="${current.url}" style="color: orangered"> ${current.title} </a> </h1>
    <img src = ${current.poster} width="275" height = "400" align="left">
    <h3 style="color: darkblue"><b>Overview:</b></h3> ${current.overview} <br>
    <br>
    <h3 style="color: darkblue"><b>Actors:</b></h3> ${current.actors} <br>
    <br>
    <h3 style="color: darkblue"> <b>Genre:</b> </h3>${current.genre}<br>
    <br>
    <h3 style="color: darkblue"> <b>Year:</b> </h3>${current.year}<BR CLEAR="ALL">

</c:forEach>

</body>
</html>
