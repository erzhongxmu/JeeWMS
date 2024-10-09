<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhouxi
  Date: 2021/7/15
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <t:base type="jquery,easyui,tools"></t:base>
<%--    <script type="text/javascript" charset="utf-8" src="webpage/com/zzjee/wmjs/jquery.jqprint.js"></script>--%>
    <script type="text/javascript"  charset="utf-8" src="webpage/com/zzjee/wmjs/qrcode.min.js"></script>
    <script type="text/javascript" src="webpage/com/zzjee/wmjs/jquery.min.js"></script>
    <style type="text/css">
        table
        {
            border-collapse: collapse;
            margin: 0 auto;
            text-align: center;
        }
        table td, table th
        {
            border: 1px solid #cad9ea;
            color: #666;
            height: 30px;
            width: 200px;
        }
        table thead th
        {
            background-color: #CCE8EB;
            width: 100px;
        }
        table tr:nth-child(odd)
        {
            background: #fff;
        }
        table tr:nth-child(even)
        {
            background: #F5FAFA;
        }
    </style>

</head>
<body>
<div style="margin-top: 100px">
    <div style="text-align: right">
        <button onclick="submit()">确定</button>
    </div>

    <table class="table">
        <tr>
            <td><input type="checkbox" name="checkall" /></td>
            <td>零件名称</td>
            <td>零件编码</td>
            <td>数量</td>
            <td>单位</td>
        </tr>
        <c:forEach items="${listitem}" var="goodsItem">
            <tr>
                <td><input type="checkbox" name="checkbox" onclick="selectId(this,'${goodsItem.id}')" value="${goodsItem.id}"/></td>
                <td>${goodsItem.goodsName}</td>
                <td>${goodsItem.goodsId}</td>
                <td>${goodsItem.baseGoodscount}</td>
                <td>${goodsItem.goodsUnit}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>

<script type="text/javascript">
  let idArray = []
  let arrryList = '${listitem}';
  function selectId(checkbox,id) {
    if ( checkbox.checked == true){
      idArray.push(id)
      console.log(222,idArray)
    }else{
      let index = idArray.indexOf(id)
      idArray.splice(index,1)
      console.log(222,idArray)
    }
  }
  $(function() {
    $('input[name="checkall"]').on("click",function(){
      idArray = []
      if($(this).is(':checked')){
        $('input[name="checkbox"]').each(function(){
          $(this).prop("checked",true);
          idArray.push($(this).val())
        });
        console.log(idArray)
      }else{
        $('input[name="checkbox"]').each(function(){
          $(this).prop("checked",false);
        });
      }
    });
  })

  function submit(){
    console.log("11111111111111111")
    var url = "wmOmNoticeHController.do?doSelectPrintckd&itemId="+idArray.join(",")+"&id="+'${id}';

    window.open(url);
  }



</script>
</html>
