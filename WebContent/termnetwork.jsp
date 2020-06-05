<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,DataFormat.*,java.sql.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en" xmlns:og="http://opengraphprotocol.org/schema/" xmlns:fb="http://www.facebook.com/2008/fbml" itemscope itemtype="http://schema.org/Map">

<head>
<title>MeSH Online</title>
<link rel="Shortcut Icon" href="pictures/title.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1,user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />


<!--[if IE]><script type="text/javascript" src="js/excanvas.js"></script><![endif]--> <!-- js/default.js -->
  <script src="js/jquery/jquery.min.js" type="text/javascript"></script>
  <script src="js/sigma/sigma.min.js" type="text/javascript" language="javascript"></script>
    <script src="js/sigma/sigma.parseJson.js" type="text/javascript" language="javascript"></script>
  <script src="js/fancybox/jquery.fancybox.pack.js" type="text/javascript" language="javascript"></script>
  <script src="js/main.js" type="text/javascript" language="javascript"></script>

  <link rel="stylesheet" type="text/css" href="js/fancybox/jquery.fancybox.css"/>
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" media="screen and (max-height: 770px)" href="css/tablet.css" />
  <script src="js/jquery.min.js"></script>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" >
 <script src="js/echarts.min.js"></script>
  <script src="js/dataTool.js"></script>
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
  <div class="sigma-parent">
    <div class="sigma-expand" id="sigma-canvas"></div>
  </div>
<div id="mainpanel">
  <div class="col">
		<div id="maintitle"></div>
    <div id="title"></div>
    <div id="titletext"></div>
    <div class="info cf">
      <dl>
        <dt class="moreinformation"></dt>
        <dd class="line"><a href="#information" class="line fb">More about this term network</a></dd>
      </dl>
    </div>
<div id="legend">
	<div class="box">
		<h2>Legend:</h2>
		<dl>
		<dt class="node"></dt>
		<dd></dd>
		<dt class="edge"></dt>
		<dd></dd>
		<dt class="colours"></dt>
		<dd></dd>		
		</dl>
	</div>
</div> 
    <div class="b1">
    <form>
      <div id="search" class="cf"><h2>Search:</h2>
        <input type="text" name="search" value="Search by name" class="empty"/><div class="state"></div>
        <div class="results"></div>
      </div>
      <div class="cf" id="attributeselect"><h2>Group Selector:</h2>
        <div class="select">Select Group</div>
	<div class="list cf"></div>
      </div>
    </form>
    </div>
  </div>
  <div id="information">
  </div>
</div>
	<div id="zoom">
  		<div class="z" rel="in"></div> <div class="z" rel="out"></div> <div class="z" rel="center"></div>
	</div>
	
<div id="attributepane">
<div class="text">
	<div title="Close" class="left-close returntext"><div class="c cf"><span>Return to the full network</span></div></div>	
<div class="headertext">
	<span>信息面板</span>
</div>	
  <div class="nodeattributes">
    <div class="name"></div>
	<div class="data"></div>
    <div class="p">关联术语:</div>
    <div class="link">
      <ul>
      </ul>
    </div>
  </div>
	</div>
</div>

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-21293169-4']);
  _gaq.push(['_setDomainName', 'none']);
  _gaq.push(['_setAllowLinker', true]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" ></script>
</body>
</html>
