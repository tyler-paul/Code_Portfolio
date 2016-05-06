<?php
  require_once('authorize.php');
  require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Edit Image</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Edit Image</h1>
            <p>To edit an image, change the category and/or edit the comment and click Edit.</p>
               <?php
                  $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME)
                     or die('Error connecting to MySQL server.');

                  // Edit comment
                  if (isset($_POST['submit'])) {
                     $id = $_POST['id'];
                     $comment = $_POST['comment'];
                     $category = $_POST['categories'];
                     $query = "UPDATE images SET comment='$comment' WHERE id = $id";
                     mysqli_query($dbc, $query)
                        or die('Error querying database.');
                     $query = "UPDATE images SET category='$category' WHERE id = $id";
                     mysqli_query($dbc, $query)
                        or die('Error querying database.');
                     echo "<p>Image edited.</p>";
                  }

                  // Display a form for each image to allow the user to edit a comment
                  $query = "SELECT * FROM images";
                  $result = mysqli_query($dbc, $query);
                  echo '<hr>';
                  while ($row = mysqli_fetch_array($result)) {
                     echo '<form method="post" action="' . $_SERVER['PHP_SELF'] . '">';
                     echo '<input type="hidden" name="id" value=' . $row['id'] . '>';
                     
                     echo '<table class="commentTable"><tr>';
                     echo '<td>';
                        echo $row['category'] . '<br />';
                     echo '</td>';   
                     echo '<td>';
                        $query2 = "SELECT * FROM categories";
                        $result2 = mysqli_query($dbc, $query2);
                        echo '<select name="categories">';
                        while ($row2 = mysqli_fetch_array($result2)) {
                           echo '<option ';
                           if ($row['category'] == $row2['category'])
                              echo 'selected = "selected" ';
                           echo 'value="' . $row2['category'] .'">' . $row2['category'] . '</option>';
                        }
                        echo '</select><br />';
                     echo '</td>'; 
                     echo '<td>';   
                        echo '<img src=thumbs/' . $row['filename'] . '>';
                     echo '</td>';   
                     echo '<td>';    
                        echo '<textarea name="comment" rows="7" cols="30">';
                        echo $row['comment'];
                        echo '</textarea><br />';
                     echo '</td>';
                     echo '<td class="editButton">';   
                        echo '<input type="submit" name="submit" value="Edit" />';
                     echo '</td>';
                     echo '</tr></table>';                       
                     echo '<hr>';
                     echo '</form>';
                  }
                  
                  mysqli_close($dbc);
               ?>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
         </div>
      </div>
   </body>
</html>
