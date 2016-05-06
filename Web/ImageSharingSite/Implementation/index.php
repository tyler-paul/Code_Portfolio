<?php
   require("config.php");
   session_start();
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Pond</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Categories</h1>
            <?php
               $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                  or die('Error connecting to MySQL server.');
               $query = "SELECT * FROM categories";
               $result = mysqli_query($dbc, $query);
               while ($row = mysqli_fetch_array($result)) {
                  echo '<span class="imageBox">';
                  echo '<table><tr><td>';
                  echo '<a href="viewCategory.php?category=' . $row['category'] . '">';
                  echo '<img src="categories/thumbs/' . $row['filename'] . '">';
                  echo '</a>';
                  echo '</td></tr><tr><td>';
                  echo '<h3>';
                  echo $row['category'];
                  echo '</h3>';
                  echo '</td></tr></table>';
                  echo '</span>';
               }
            ?>
            <h1>Most Recent Images</h1>
            <?php
               $query = "SELECT * FROM images ORDER BY time DESC";
               $result = mysqli_query($dbc, $query);
               $i_max = 10;
               $i = 0;
               while ($row = mysqli_fetch_array($result) and $i < $i_max) {
                  $id = $row['id'];
                  echo "<a href=../viewImage?picId=$id>";
                  echo '<img src=thumbs/' . $row['filename'] . '>';
                  echo '</a>';
                  $i = $i + 1;
               }
               mysqli_close($dbc);
            ?>
            <br/><br/>
         </div>
      </div>
   </body>
</html>
