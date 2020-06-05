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

<div id="main" style="width: 800px;height:500px;position: absolute;left:20%;top:15%;" class="pull-center"></div>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('main'));
var dataGZ=${website};
var dataBJ=${database};

var dataSH=${software};
console.log(dataBJ);
console.log(dataGZ);
console.log(dataSH);
var schema = [
    {name: 'technical', index: 0, text: '技术开发人员数量'},
    {name: 'researcher', index: 1, text: '总研究人员数量'},
    {name: 'article', index: 2, text: '文献数量'},
    {name: 'application', index: 3, text: '软件开发类文献占比'},
    {name: 'application', index: 4, text: '软件类文献数量'},
    {name: 'application', index: 5, text: '数据库类文献数量'},
    {name: 'application', index: 6, text: '网站类文献数量'}

];
var itemStyle = {
	    opacity: 0.8,
	    shadowBlur: 10,
	    shadowOffsetX: 0,
	    shadowOffsetY: 0,
	    shadowColor: 'rgba(0, 0, 0, 0.5)'
	};

	option = {
	    backgroundColor: '#404a59',
	    color: [
	        '#dd4444', '#fec42c', '#80F1BE'
	    ],
	    legend: {
	        top: 10,
	        data: ['数据库', '网站', '软件'],
	        textStyle: {
	            color: '#fff',
	            fontSize: 16
	        }
	    },
	    grid: {
	        left: '10%',
	        right: 150,
	        top: '18%',
	        bottom: '10%'
	    },
	    tooltip: {
	        padding: 10,
	        backgroundColor: '#222',
	        borderColor: '#777',
	        borderWidth: 1,
	        formatter: function (obj) {
	            var value = obj.value;
	            return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
	                + value[7] + '主题词：'+ ' ' + obj.seriesName +'类开发居多'
	                + '</div>'
	                + schema[0].text + '：' + value[0] + '<br>'
	                + schema[1].text + '：' + value[1] + '<br>'
	                + schema[2].text + '：' + value[2] + '<br>'
	                + schema[3].text + '：' + value[3]+'%' + '<br>'
	                + schema[4].text + '：' + value[4] + '<br>'
	                + schema[5].text + '：' + value[5]+ '<br>'
	                + schema[6].text + '：' + value[6] + '<br>';
	        }
	    },
	    xAxis: {
	        type: 'value',
	        name: '技术开发人员数量',
	        nameGap: 16,
	        nameTextStyle: {
	            color: '#fff',
	            fontSize: 14
	        },
	        max: ${x},
	        splitLine: {
	            show: false
	        },
	        axisLine: {
	            lineStyle: {
	                color: '#eee'
	            }
	        }
	    },
	    yAxis: {
	        type: 'value',
	        name: '研究人员总数',
	        nameLocation: 'end',
	        nameGap: 20,
	        nameTextStyle: {
	            color: '#fff',
	            fontSize: 16
	        },
	        axisLine: {
	            lineStyle: {
	                color: '#eee'
	            }
	        },
	        splitLine: {
	            show: false
	        }
	    },
	    visualMap: [
	        {
	            left: 'right',
	            top: '10%',
	            dimension: 2,
	            min: ${size_min},
	            max: ${size_max},
	            itemWidth: 30,
	            itemHeight: 120,
	            calculable: true,
	            precision: 0.1,
	            text: ['圆形大小：相关文献数量'],
	            textGap: 30,
	            textStyle: {
	                color: '#fff'
	            },
	            inRange: {
	                symbolSize: [10, 70]
	            },
	            outOfRange: {
	                symbolSize: [10, 70],
	                color: ['rgba(255,255,255,.2)']
	            },
	            controller: {
	                inRange: {
	                    color: ['#c23531']
	                },
	                outOfRange: {
	                    color: ['#444']
	                }
	            }
	        },
	        {
	            left: 'right',
	            bottom: '5%',
	            dimension: 3,
	            min: 0,
	            max: ${percentage},
	            itemHeight: 120,

	            precision: 0.1,
	            text: ['明暗：应用开发类文献占比'],
	            textGap: 30,
	            textStyle: {
	                color: '#fff'
	            },
	            inRange: {
	                colorLightness: [1, 0.5]
	            },
	            outOfRange: {
	                color: ['rgba(255,255,255,.2)']
	            },
	            controller: {
	                inRange: {
	                    color: ['#c23531']
	                },
	                outOfRange: {
	                    color: ['#444']
	                }
	            }
	        }
	    ],
	    series: [
	        {
	            name: '数据库',
	            type: 'scatter',
	            itemStyle: itemStyle,
	            data: dataBJ
	        },
	        {
	            name: '网站',
	            type: 'scatter',
	            itemStyle: itemStyle,
	            data: dataSH
	        },
	        {
	            name: '软件',
	            type: 'scatter',
	            itemStyle: itemStyle,
	            data: dataGZ
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