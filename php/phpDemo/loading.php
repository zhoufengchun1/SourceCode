<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>登录界面</title>
		<style type="text/css">
			.main{
				margin: 0 auto;
				padding: 10px;
				width: 250px;
				height: 200px;
				background: cornflowerblue;
			}
			.leftbar{
				width: 30%;
				padding-bottom: 15px;
				display: inline-block;
				text-align: right;
			}
			.bottom{
				padding-bottom: 15px;
			}
		</style>
	</head>
	<body>
		
		<form action="登录验证.php" method="post">
			
			<div id="main" class="main">
				<h3>
					请输入用户名
				</h3>
				<div>
					<label><div class="leftbar">用户名：</div><input type="text" name="userName" /></label>
					<label><div class="leftbar">密码：</div><input type="text" name="userPassword" /></label>
				</div>
				<div class="bottom">
					<div class="leftbar"></div><input type="radio" name="remmber"  />记住我一周
				</div>
				<div class="bottom">
					<div class="leftbar"></div><input type="submit" name="submit" value="登录" />
				</div>
				
			</div>
			
		</form>	
	</body>
</html>
 
 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>登录验证</title>
		<style type="text/css">
			.main{
				margin: 0 auto;
				width: 350px;
				height: 100px;
				background: cornflowerblue;
				padding: 20px;
			}
		</style>
	</head>
	<body>
		<div class="main">
			<?php
 
				$passname=$_POST['userName'];//账号
				$password=$_POST['userPassword'];//密码
 
                if($passname==null||$password==null){
					header("location:loading.html");//直接打开该php文件，跳转到登录界面
				}
				
				
//				require_once('登录验证数据库连接.php');
//				$db=new connectDB();
//				$db->getConn();
			
				//	$db=@new mysqli('localhost','root','root');//！！！！
				//	if ($db->connect_error)
				//	 die('错误: '. $db->connect_error);
				//	$db->select_db('不能连接数据库');//***
			    require_once "connet.php";
		      
		        $db=@new mysqli('localhost','root','root');
		       
				if ($db->connect_error)
				 die('错误: '. $db->connect_error);
					$sql='SELECT * FROM pass WHERE name='."'{$name}'AND psw="."'$password';";//！！！！！
					$result=$db->query($sql);
					$num_users=$result->num_rows;//在数据库中搜索到符合的用户
					if($num_users!=0)
						echo "<h3>欢迎您</h3>";
					else{
						echo "用户名或密码错误";
					}
			?>
		</div>
	</body>
</html>
 
