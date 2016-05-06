<?php
  require_once('authorize.php');
  require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Edit Category</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Edit Category</h1>
            <p>To edit an image, change the category and/or edit the comment and click Edit.</p>
               <?php
                  require_once('appvars.php');
                  $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                     or die('Error connecting to MySQL server.');

                  //Change category image
                  if (isset($_POST['submit'])) {
                     $newName = $_POST['category'];
                     $id = $_POST['id'];
                     if (!empty($_FILES['imagefile']['name']) || !empty($newName)) {
                        if (!empty($_FILES['imagefile']['name'])) {
                           $filename = time().$_FILES['imagefile']['name'];
                           
                           //remove old image
                           $query = "SELECT * FROM categories WHERE id = $id";
                           $result = mysqli_query($dbc, $query);
                           $row = mysqli_fetch_array($result);
                           $oldFileName = $row['filename'];
                           unlink('categories/' . $oldFileName);
                           unlink('categories/thumbs/' . $oldFileName);
                           
                           //save new image
                           move_uploaded_file($_FILES['imagefile']['tmp_name'], 'categories/'.$filename);
                           thumbnail('categories/','categories/thumbs/', $filename, 150 , 100);
                           $query = "UPDATE categories SET filename='$filename' WHERE id = $id";
                           $result = mysqli_query($dbc, $query);
                           echo "<p>Image changed.</p>";
                        }
                        if (!empty($newName)) {
                           //get old name
                           $query = "SELECT * FROM categories WHERE id = $id";
                           $result = mysqli_query($dbc, $query);
                           $row = mysqli_fetch_array($result);
                           $oldName = $row['category'];
                           
                           $query = "UPDATE categories SET category='$newName' WHERE id=$id";
                           $result = mysqli_query($dbc, $query);
                           
                           $query = "UPDATE images SET category='$newName' WHERE category='$oldName'";
                           $result = mysqli_query($dbc, $query);
                           echo "<p>Category name changed.</p>";
                        }
                     }
                     else {
                        echo '<p>You did not select a file or enter a new category name.</p>';
                     }
                  }

                  // Display a form for each image to allow the user to edit a comment
                  $query = "SELECT * FROM categories";
                  $result = mysqli_query($dbc, $query);
                  echo '<hr>';
                  while ($row = mysqli_fetch_array($result)) {
                     echo '<form method="post" enctype="multipart/form-data" action="' . $_SERVER['PHP_SELF'] . '">';
                     echo '<input type="hidden" name="id" value=' . $row['id'] . '>';
                     
                     echo '<table class="commentTable"><tr>';
                     echo '<td>';
                        echo $row['category'] . '<br />';
                     echo '</td>';  
                     echo '<td>';
                        echo '<label id="category">New category name:</label><br />';
                        echo '<input type="text" id="category" name="category">';
                     echo '</td>'; 
                     echo '<td>';   
                        echo '<img src=categories/thumbs/' . $row['filename'] . '>';
                     echo '</td>';                       
                     echo '<td>';
                        echo '<input type="file" id="imagefile" name="imagefile" />';
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
