<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  $data = json_decode(file_get_contents('php://input'), true);
  $connect=mysqli_connect("localhost","root","Betin@12345","skills_db");
  $gender = $data['gender'];
  $country = $data['country'];
  $county = $data['county'];
  $estate = $data['estate'];
  $skills = $data['skills'];

  if($connect){

    $QUERY="SELECT * FROM users JOIN skills ON users.id = skills.user_id WHERE users.gender='$gender' AND skills.country='$country' AND skills.county='$county'AND skills.estate='$estate' AND skills.skills='$skills'";
    $result = mysqli_query($connect,$QUERY);
    if (mysqli_num_rows($result) > 0) {
     $response['status'] ='200';
     $response["List"] = array();
     while($row = mysqli_fetch_assoc($result)) {
       $recp = array();

       $recp["name"] = $row["firstname"]." ".$row["lastname"];
       $recp["gender"] = $row["gender"];
       $recp["country"] = $row["country"];
       $recp["county"] =$row["county"];
       $recp["estate"] = $row["estate"];
       $recp["phone"] = $row["phone"];
       $recp["neigh"] = $row["neigh"];
       $recp["image"] = $row["image"];
       
       $recp["skillname"]= $row["skills"];



       array_push($response["List"], $recp);


     }
     $object=array(array_filter((array)$response));  
     $res = json_encode($object);
     echo trim($res,'[]');

   }else{
      $row = array(
            'status' => '201',
            'message' => 'No Result Found'
        );
            $data_array = $row;
  echo json_encode($data_array);

   }

 }else{
  $row = array(
            'status' => '400',
            'message' => 'No Connection Found'
        );
            $data_array = $row;
  echo json_encode($data_array);
 }
}


?>