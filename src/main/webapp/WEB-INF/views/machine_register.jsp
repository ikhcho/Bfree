<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<script type="text/javascript">
	
	function add() {
		var form = document.machine;
		form.action = "machine_register";
		form.submit();
	}
</script>

<body>

	<form name="machine" method="post">

		<input type="text" name="name" placeholder="장비명" required autofocus></br>
		<input type="text" name="ip" placeholder="장비 ip주소 ex)255.255.255.255" required></br>
		<input type="text" name="threshold" placeholder="임계치" required></br>
		<input type="text" name="type" placeholder="장비타입" required> </br>
		<input type="text" name="admin" placeholder="관리자명" required></br>
		<input type="text" name="email" placeholder="관리자 이메일" required></br>
		<input type="button" value="등록" onclick=submit()>
	</form>

</body>
</html>