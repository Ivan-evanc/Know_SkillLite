<?php
if($_SERVER['REQUEST_METHOD']==="POST"){
    include('configs/db_connect.php');

    $data = json_decode(file_get_contents('php://input'), true);
    $firstname = $data['firstname'];
    $lastname = $data['lastname'];
    $username = $data['username'];
    $gender = $data['gender'];
    $phone = $data['phone'];
    $email = $data['email'];
    $password = $data['password'];
    $mdpassword = md5($password);    
    if(checkPhoneIfExist($phone)){
        if(checkIfUserNameExists($username)){
            if(checkIfEmailExists($email)){
                $query ="INSERT INTO users(firstname,lastname,username,gender,phone,email,password)
                VALUES('$firstname','$lastname','$username','$gender','$phone','$email','$mdpassword')";
                $result = mysqli_query($connect,$query);
                if($result){
                    $row = array(
                        'status' => '200',
                        'message' => 'Registration Completed'
                    );
                }else{
                    $row = array(
                        'status' => '201',
                        'message' =>'Unable to commit the data'
                    );
                }

            }else{
                $row = array(
                    'status' => '800',
                    'message' => 'Email already present'
                );
            }

        }else{
         $row = array(
            'status' => '400',
            'message' => 'Username has been taken. Choose another username'
        );
     }

 }else{
   $row = array(
    'status' => '300',
    'message' => 'Phone Number already exists'
);
}

$data_array = $row;
echo json_encode($data_array);
}
?>

<?php
function checkPhoneIfExist($phone){
    global $connect;
    $query_phone = mysqli_query($connect,"SELECT * FROM users WHERE phone='$phone'");
    if(mysqli_num_rows($query_phone)>0){
        return false;
    }else{
        return true;
    }
}

function checkIfUserNameExists($username){
    global $connect;
    $query_username = mysqli_query($connect, "SELECT * FROM users WHERE username='$username'");
    if(mysqli_num_rows($query_username)>0){
        return false;
    }else{
        return true;
    }
}

function checkIfEmailExists($email){
    global $connect;
    $query_email = mysqli_query($connect, "SELECT * FROM users WHERE email='$email'");
    if(mysqli_num_rows($query_email)>0){
        return false;
    }else{
        return true;
    }
}

?>