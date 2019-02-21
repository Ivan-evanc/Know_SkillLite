<?php
if($_SERVER['REQUEST_METHOD']==="POST"){
	include('configs/db_connect.php');
	$data = json_decode(file_get_contents('php://input'), true);
	$username = $data['username'];
	$password = $data['password'];
	$mdpassword = md5($password);

	if($connect){
		$sql="SELECT * FROM users WHERE username='$username' AND password='$mdpassword'";
		$result=mysqli_query($connect,$sql);
		if(mysqli_num_rows($result)>0){
			$token = md5($username).md5(date('dmY'));
			mysqli_query($connect, "UPDATE users SET token='$token' WHERE username='$username'");
			$row_data = mysqli_fetch_assoc($result);
			$row = array(
				'status' => '200',
				'token' => $token,
				'message' => 'Login success',
				'id' => $row_data['id'],
				'gender' => $row_data['gender'],
				'username' => $row_data['username'],
				'phone' => $row_data['phone'],
				'name' => $row_data['firstname']." ".$row_data['lastname']

			);
		}else{
			$row = array(
				'status' => '201',
				'message' => 'Login failed'
			);
		}
		$data_array = $row;
		echo json_encode($data_array);
	}
}
?>
