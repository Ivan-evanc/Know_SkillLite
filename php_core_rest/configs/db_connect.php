<?php
$connect = @mysqli_connect('localhost','root','Betin@12345','skills_db');
if(!$connect){
	$row = array(
		'status' => '505',
		'message' =>'No connection to server'
	);

	$data_array = $row;
	echo json_encode($data_array);

}


?>