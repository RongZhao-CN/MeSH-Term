<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,DataFormat.*,java.sql.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>



<head>
<meta charset="UTF-8">
<script src="js/jquery.min.js"></script>

 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" >
 <link rel="stylesheet" href="css/myscroll.css" >
  <script src="js/echarts.min.js"></script>
<title>MeSH Online</title>
<link rel="Shortcut Icon" href="pictures/title.ico" type="image/x-icon" />
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="index">MeSHOnline</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="term?input=${parameter}">主页 <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="treeview?input=${parameter}">树图</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="network?input=${parameter}">网络关系图</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="false" aria-expanded="false">
         统计图表
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="time?input=${parameter}">文献总量分析</a>
          <a class="dropdown-item" href="researcher?input=${parameter}">研究人员统计</a>
          
          <a class="dropdown-item" href="journal?input=${parameter}">期刊分析</a> 
        </div>
      </li>
      
    </ul>
    <form class="form-inline my-2 my-lg-0" action="term" method="get">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>
<div class="container">
<div class="row">
<div class="col-xs-6 col-md-2">
<div class="scrollbar" id="style-1" style="position:absolute;left:-150px;width:300px">
<button class="btn btn-info" style="width:290px">
    相关文献数
 </button>
<ul class="list-group" >
<c:forEach items="${timeInfo }" var="year" varStatus="st">
  <li class="list-group-item d-flex justify-content-between align-items-center">
    ${year.getTime()}
    <span class="badge badge-info badge-pill">${year.getNum()}</span>
  </li>
</c:forEach>
</ul>
      <div class="force-overflow"></div>
</div>

</div>
<div class="col-xs-6 col-md-8">
<div class="card text-center">
  <div class="card-header">
    <ul class="nav nav-tabs card-header-tabs">
      <li class="nav-item">
        <a class="nav-link active" href="#">${termInfo.getName()}</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">文献计量</a>
      </li>
     
    </ul>
  </div>
  <div class="card-body">
    <div class="alert alert-warning" role="alert">
    <p class="text-left" style="font-size:14px">英文名 ：${termInfo.getName()}</p>
   <p class="text-left" style="font-size:14px">学科 ：${termInfo.getSubject()}</p>
 <p class="text-left" style="font-size:14px">描述： ${termInfo.getDescription()}</p> 
<p class="text-left" style="font-size:14px">药理作用：${termInfo.getPharmAction()}</p>
<p class="text-left" style="font-size:14px">公布年度： ${termInfo.getDate()}</p>
</div>
</div>
</div>

</div>
<div class="col-md-2 " >

<button class="btn btn-info " style="width:320px">
<svg class="bi bi-bookmark-plus" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M4.5 2a.5.5 0 00-.5.5v11.066l4-2.667 4 2.667V8.5a.5.5 0 011 0v6.934l-5-3.333-5 3.333V2.5A1.5 1.5 0 014.5 1h4a.5.5 0 010 1h-4zm9-1a.5.5 0 01.5.5v2a.5.5 0 01-.5.5h-2a.5.5 0 010-1H13V1.5a.5.5 0 01.5-.5z" clip-rule="evenodd"/>
  <path fill-rule="evenodd" d="M13 3.5a.5.5 0 01.5-.5h2a.5.5 0 010 1H14v1.5a.5.5 0 01-1 0v-2z" clip-rule="evenodd"/>
</svg>    相关术语
 </button>


<c:forEach items="${termlist }" var="lm" varStatus="st">
<div class="accordion" id="accordionExample" style="width: 320px">
  <div class="card">
    <div class="card-header" id="headingOne">
      <h2 class="mb-0">
        <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
          <a  href="term?input=${lm.getName()}">${lm.getName()}</a>
          
        </button>
      </h2>
    </div>
  </div>
</div>
</c:forEach>

</div>
</div>
</div>




 <script src="js/jquery-3.5.1.slim.min.js"></script>
    <script src="js/popper.min.js" ></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" ></script>

</body>
</html>