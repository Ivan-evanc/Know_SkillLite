<?php
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
	include('configs/db_connect.php');
	$TRANSACT_QUERY="SELECT * FROM expert";
	$result = mysqli_query($connect,$TRANSACT_QUERY);
	if (mysqli_num_rows($result) > 0) {
		$response['status'] = "200";
		$response["expert"] = array();
		while($row = mysqli_fetch_assoc($result)) {
			$recp = array();

			$recp["id"]= $row["id"];
			$recp["name"]=$row["name"];

			array_push($response["expert"], $recp);


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