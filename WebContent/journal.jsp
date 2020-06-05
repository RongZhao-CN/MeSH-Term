<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,DataFormat.*,java.sql.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<script src="js/jquery.min.js"></script>

 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" >
 <script src="js/echarts.min.js"></script>
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


<div id="main" style="width: 900px;height:500px;position: absolute;" class="pull-center"></div>
<div id="other" style="width: 500px;height:500px;position: absolute;right:1%" class="pull-right"></div>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('main'));
var data=${data};
console.log(data);
option = {
	    dataset: {
	        source: data
	    },
	    grid: {containLabel: true},
	    xAxis: {name: '总数'},
	    yAxis: {type: 'category'},
	    visualMap: {
	        orient: 'horizontal',
	        left: 'center',
	        min: 0,
	        max: 50,
	        text: ['高影响因子', '低影响因子'],
	        // Map the score column to color
	        dimension: 0,
	        inRange: {
	            color: ['#D7DA8B', '#E15457']
	        }
	    },
	    series: [
	        {
	            type: 'bar',
	            encode: {
	                // Map the "amount" column to X axis.
	                x: 'amount',
	                // Map the "product" column to Y axis
	                y: 'journal'
	            }
	        }
	    ]
	};
myChart.setOption(option);
</script>

<script type="text/javascript">
var myChart = echarts.init(document.getElementById('other'));
option = {
	    title: {
	        text: '各数量值段期刊数',
	        subtext: '期刊总数：33476',
	        left: 'center'
	    },
	    tooltip: {
	        trigger: 'item',
	        formatter: '{a} <br/>{b} : {c} ({d}%)'
	    },
	    legend: {
	        left: 'center',
	        top: 'bottom',
	        data: ['<100', '<200', '<300', '<400', '<500', '<1000', '<5000', '5k+']
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            mark: {show: true},
	            dataView: {show: true, readOnly: false},
	            magicType: {
	                show: true,
	                type: ['pie', 'funnel']
	            },
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
	    series: [
	        {
	            name: '期刊数',
	            type: 'pie',
	            radius: [20, 110],
	            center: ['25%', '50%'],
	            roseType: 'radius',
	            label: {
	                show: false
	            },
	            emphasis: {
	                label: {
	                    show: true
	                }
	            },
	            data: [
	                {value: 5980, name: '<100'},
	                {value: 1866, name: '<200'},
	                {value: 1248, name: '<300'},
	                {value: 823, name: '<400'},
	                {value: 720, name: '<500'},
	                {value: 2260, name: '<1000'},
	                {value: 4217, name: '<5000'},
	                {value: 1435, name: '5K+'}
	            ]
	        }
	    ]
	};
myChart.setOption(option);
</script>
 <script src="js/jquery-3.5.1.slim.min.js"></script>
    <script src="js/popper.min.js" ></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" ></script>

</body>
</html>