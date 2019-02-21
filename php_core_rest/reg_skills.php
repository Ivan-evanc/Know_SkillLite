<?php
if($_SERVER['REQUEST_METHOD']==="POST"){
   include('configs/db_connect.php');

   $data = json_decode(file_get_contents('php://input'), true);

   $user_id =$data['user_id'];
   $country = $data['country'];
   $county = $data['county'];
   $estate = $data['estate'];
   $neigh = $data['neigh'];
   $skills = $data['skills'];
   $idnumber = $data['idnumber'];
   $image = $data['image'];

   $path = "uploads/$idnumber.jpg";
   $actualpath = "http://192.168.43.113/skill_rest_api/$path";

   if(checkIfIdIsPresend($idnumber)){

     $query ="INSERT INTO skills(user_id,country,county,estate,neigh,skills,idnumber,image)
     VALUES('$user_id','$country','$county','$estate','$neigh','$skills','$idnumber','$actualpath')";
     $result = mysqli_query($connect,$query);
     if($result){
        file_put_contents($path,base64_decode($image));
        $row = array(
            'status' => '200',
            'message' => 'Skills Registered Successfully'
        );
    }else{
        $row = array(
            'status' => '201',
            'message' =>'Unable to commit the data'
        );
    }



}else{
 $row = array(
    'status' => '500',
    'message' => 'ID Number entered already exists'
);
}

$data_array = $row;
echo json_encode($data_array);
}
?>

<?php
function checkIfIdIsPresend($id){
    include ('configs/db_connect.php');
    $query_id = mysqli_query($connect, "SELECT * FROM skills WHERE idnumber='$id'");
    if(mysqli_num_rows($query_id)>0){
        return false;
    }else{
        return true;
    }
}



?>