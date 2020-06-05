<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,DataFormat.*,java.sql.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
 
<script src="js/jquery.min.js"></script>

 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" >
 <script src="js/echarts.min.js"></script>
  <script src="js/dataTool.js"></script>

 
<head>
<meta charset="UTF-8">
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

<div id="main" style="width: 1400px;height:550px;position: absolute;left:5%;top:10%" class="pull-center"></div>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('main'));

myChart.showLoading();

$.get('pictures/termnetwork-simple.gexf', function (xml) {
    myChart.hideLoading();
    console.log(xml);
    
    var graph =echarts.dataTool.gexf.parse(xml);
    
    var categories = [];
    categories[0]={name:'database'};
    categories[1]={name:'software'};
    categories[2]={name:'website'};
    categories[3]={name:'other'};
    
    graph.nodes.forEach(function (node) {
        node.itemStyle = null;
        node.value = node.symbolSize;
        
        node.label = {
            show: node.symbolSize > 10
        };
        node.category = node.attributes.modularity_class;
    });
    option = {
        title: {
            text: 'term network',
            subtext: 'Default layout',
            top: 'bottom',
            left: 'right'
        },
        tooltip: {},
        legend: [{
            // selectedMode: 'single',
            data: categories.map(function (a) {
                return a.name;
            })
        }],
        animationDuration: 1500,
        animationEasingUpdate: 'quinticInOut',
        series : [
            {
                name: 'core of term network',
                type: 'graph',
                layout: 'circular',
                circular:{
                	rotateLabel:true
                },
                data: graph.nodes,
                links: graph.links,
                categories: categories,
                roam: true,
                label: {
                    position: 'right',
                    formatter: '{b}'
                },
                lineStyle: {
                    color: 'source',
                    curveness: 0.3
                }
                
            }
        ]
    };

    myChart.setOption(option);
}, 'xml');
</script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" ></script>
    
</body>
</html>