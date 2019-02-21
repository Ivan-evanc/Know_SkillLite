<?php
if($_SERVER['REQUEST_METHOD']==="POST"){
    $data = json_decode(file_get_contents('php://input'), true);

    $username = $data['username'];
    $skill = $data['skill'];

    $connect = mysqli_connect('localhost','root','Betin@12345','skills_db');
    if($connect){
        $query ="INSERT INTO skills(user_id,skillname) VALUES('$username','$skill')";
        $result = mysqli_query($connect,$query);
        if($result){
            $row = array(
				'status' => '200'
			);
        }else{
            $row = array(
				'status' => '201'
			);
        }

    }else{
        $row = array(
			'status' => '201'
		);

	}
	$data_array = $row;
	echo json_encode($data_array);

}
?>