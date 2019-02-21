<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
	include('configs/db_connect.php');
	 $data = json_decode(file_get_contents('php://input'), true);
	 $user_id = $data['user_id'];
	$TRANSACT_QUERY="SELECT * FROM skills WHERE user_id='$user_id'";
	$result = mysqli_query($connect,$TRANSACT_QUERY);
	if (mysqli_num_rows($result) > 0) {
		$response['status'] = "200";
		$response["skills"] = array();
		while($row = mysqli_fetch_assoc($result)) {
			$recp = array();

			$recp["id"]= $row["id"];
			$recp["skill"]=$row["skills"];

			array_push($response["skills"], $recp);


		}
		$object=array(array_filter((array)$response));  
		$res = json_encode($object);
		echo trim($res,'[]');

	}else{
		$row = array(
			'status' => '201',
			'message' => 'No Records Found'
		);

		$data_array = $row;
		echo json_encode($data_array);
	}
}


?>