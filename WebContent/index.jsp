<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css">

<title>Home</title>

<link rel="Stylesheet" href="/team5/styles/default.css" />
</head>


<body>

	<div id="pageContainer" class="col-sm-40" class="container">
		<div class="col-sm-12" class="row">
   			 <section class="col-xs-12">
		

			<form lass="col-sm-12" class="form-horizontal" action="/team5/register.action" method="post">
				<div class="form-group">
  					<label class="col-sm-12" for="name">이름</label>
						<input class="form-control" type="text" id="name" name="name"
							placeholder="Name"/>
				</div>
				<div class="form-group">
  					<label class="col-sm-12" for="email">이메일</label>
						<input class="form-control" type="text" id="email" name="email"
							placeholder="Email"/>
							
				</div>			
				<div class="form-group">
  					<label class="col-sm-12" for="passwd">비밀번호</label>
						<input class="form-control" type="password" id="passwd" name="passwd"
							placeholder="Password"/>
				</div>	
					
				 <div class="radio-inline"> 
					<label for="gender">성별 </label>
    					<label class="radio-inline">
    				 <input  name="gender" value=0 checked="checked" type="radio">
    						 남성
   							 </label>

    					<label class="radio-inline">
     					 <input name="gender" value=1 checked="checked" type="radio">
     						 여성
   							 </label>
  						</div>	
  																
				<div class="form-group">
  					<label class="col-sm-12" for="date">생일</label>	
						<input type="date" id="birth" name="birth">
				</div>
				<div class="buttons">
					<input class="btn btn-primary" type="submit" value="가입하기"/>

				</div>
			</form>
		
	
		<br />
		<br />
	

			
	</div>

<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/script.js"></script>
</body>
</html>


