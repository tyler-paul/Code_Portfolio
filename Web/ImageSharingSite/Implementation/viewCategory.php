<?php
   session_start();
   require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="../turtle.css">
      <title>View Categories</title>
   </head>
   <body>
      <div id="header">
         <img src="../site_graphics/turtleHeader.jpg">
      </div>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <?php
               $category = $_GET['category'];
               $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME)
                  or die('Error connecting to MySQL server.');

               echo '<h1>' . $category . '</h1>';
               $query = 'SELECT * FROM images WHERE category="' . $category .'"';
               $result = mysqli_query($dbc, $query);

               while ($row = mysqli_fetch_array($result)) {
                  $filename = $row['filename'];
                  $comment = $row['comment'];
                  $id = $row['id'];
                  echo '<span class="imageBox">';
                     echo '<table><tr><td>';
                     echo "<a href=../viewImage?picId=$id><img src=../thumbs/$filename></a><br />";
                     echo '</td></tr><tr><td>';
                     echo '<textarea rows="7" cols="30" readonly>';
                     echo $comment;
                     echo '</textarea>';
                     echo '</td></tr></table>';
                  echo '</span>';
               }
               mysqli_close($dbc);
            ?>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
         </div>
      </div>
   </body>
</html>
