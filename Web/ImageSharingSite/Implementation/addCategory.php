<?php
  require_once('authorize.php');
  require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Add Category</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Add Category</h1>
            <p>Enter a category name, select an image file to represent the category, and click Add to add a category.</p>
            <?php
               require_once('appvars.php');
               if (isset($_POST['submit'])) {
                  $category = $_POST['category'];
                  $filename = $_FILES['imagefile']['name'];
                  
                  $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                     or die('Error connecting to MySQL server.');
                  if (!empty($_FILES['imagefile']['name'])) {
                     $filename = time().$_FILES['imagefile']['name'];
                     echo "<p>Added category $category.</p>";
                     move_uploaded_file($_FILES['imagefile']['tmp_name'], 'categories/'.$filename);
                     thumbnail('categories/','categories/thumbs/', $filename, 150 , 100);
                     
                     $query = "INSERT INTO categories (category, filename) VALUES ('$category', '$filename')";
                     $result = mysqli_query($dbc, $query);
                  }
                  else {
                    echo '<p>You did not select a file.</p>';
                  }
                  mysqli_close($dbc);
               }
            ?>
            <form enctype="multipart/form-data" method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
               <label for="Category">Category:</label> <br />
               <input type="text" id="category" name="category" /><br />
               <input type="file" id="imagefile" name="imagefile" />
               <hr>
               <input type="submit" value="Add" name="submit">
            </form>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
         </div>
      </div>
   </body>
</html>
