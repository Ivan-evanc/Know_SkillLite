<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
	include('configs/db_connect.php');
	$data = json_decode(file_get_contents('php://input'), true);
	$cnty = @$data['country'];
	if($cnty!=""){
			$TRANSACT_QUERY="SELECT * FROM counties WHERE country_name='$cnty'";
	$result = mysqli_query($connect,$TRANSACT_QUERY);
	if (mysqli_num_rows($result) > 0) {
		$response['status'] = "200";
		$response["counties"] = array();
		while($row = mysqli_fetch_assoc($result)) {
			$recp = array();

			$recp["code"]= $row["code"];
			$recp["name"]=$row["name"];

			array_push($response["counties"], $recp);


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