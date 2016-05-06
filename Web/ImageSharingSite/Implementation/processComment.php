<?php
   session_start();
   require("config.php");
   
   $picId = $_POST['picId'];
   $userId = $_SESSION['userId'];
   $comment = $_POST['comment'];
   if (!empty($comment)) {
      $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME)
         or die('Error connecting to MySQL server.');     
      //add comment to DB
      $query = "INSERT INTO comments (userId, picId, time, comment) VALUES ($userId, $picId, NOW(), '$comment')";
      $result = mysqli_query($dbc, $query);
      mysqli_close($dbc);
   }
?>
