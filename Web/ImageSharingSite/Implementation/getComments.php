<?php
   session_start();
   require("config.php");
?>

<?php
   
   $picId = $_GET['picId'];
   
   $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME)
                     or die('Error connecting to MySQL server.');
   $query = "SELECT * FROM comments WHERE picId = $picId ORDER BY time";
   $result = mysqli_query($dbc, $query);
   
   $commentArray = array();
   
   while ($row = mysqli_fetch_array($result)) {
      
      $userId = $row['userId'];
                  
      //get username
      $query2 = "SELECT * FROM users WHERE id = $userId";
      $result2 = mysqli_query($dbc, $query2);
      $row2 = mysqli_fetch_array($result2);
      $username = $row2['username']; 
      
      $comment = $row['comment'];
      $date = date('D M d/Y h:i A', strtotime($row['time']));
      
      $item = array("username" => $username,
                    "date"     => $date,
                    "comment"  => $comment
                    );

      array_push($commentArray, $item);
   }
   mysqli_close($dbc);
   
   echo json_encode($commentArray);
?>
