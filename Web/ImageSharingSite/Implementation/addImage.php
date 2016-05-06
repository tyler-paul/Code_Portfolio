<?php
  require_once('authorize.php');
  require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Add Image</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Add Image</h1>
            <p>Select a file and image category, write a comment, and click Add to add an image.</p>
            <?php
               require_once('appvars.php');
               if (isset($_POST['submit'])) {
                  $category = $_POST['categories'];
                  $comment = $_POST['comment'];
                  
                  $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                     or die('Error connecting to MySQL server.');
                  if (!empty($_FILES['imagefile']['name'])) {
                     $filename = time().$_FILES['imagefile']['name'];
                     echo "<p>Added $filename to category $category.</p>";
                     move_uploaded_file($_FILES['imagefile']['tmp_name'], 'images/'.$filename);
                     
                     thumbnail('images/','thumbs/', $filename, 150 , 100);
                     
                     $query = "INSERT INTO images (category, filename, time, comment) VALUES ('$category', '$filename', NOW(), '$comment')";
                     $result = mysqli_query($dbc, $query);
                  }
                  else {
                    echo '<p>You did not select a file.</p>';
                  }
               }
            ?>
            <form enctype="multipart/form-data" method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
               <input type="hidden" name="MAX_FILE_SIZE" value="30000000" />
               <label for="categories">Category:</label><br />
               <select name="categories">
               <?php
                  $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                     or die('Error connecting to MySQL server.');
                  $query = "SELECT * FROM categories";
                  $result = mysqli_query($dbc, $query);
                  
                  while ($row = mysqli_fetch_array($result)) {
                     echo '<option value="' . $row['category'] .'">' . $row['category'] . '</option>';
                  }
                  mysqli_close($dbc);
                  echo '</select><br />';
               ?>
               <label for="comment">Comment:</label><br />
               <textarea id="comment" name="comment"></textarea><br />
               <input type="file" id="imagefile" name="imagefile" />
               <hr>
               <input type="submit" value="Add" name="submit">
            </form>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
         </div>
      </div>
   </body>
</html>
