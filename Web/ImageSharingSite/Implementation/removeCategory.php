<?php
  require_once('authorize.php');
  require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Remove Category</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Remove Category</h1>
            <p>Select a category to delete and then click Remove.</p>
            <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
               <?php
                  $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME)
                     or die('Error connecting to MySQL server.');

                  // Delete rows (only if the form has been submitted)
                  if (isset($_POST['submit'])) {
                     $idFileNameBundle = $_POST['idFileNameBundle'];
                     $pieces = explode(":", $idFileNameBundle);
                     $categoryID = $pieces[0];
                     $filename = $pieces[1];
                     $category = $pieces[2];
                     $query = "DELETE FROM categories WHERE id = '$categoryID'";
                     mysqli_query($dbc, $query)
                        or die('Error querying database.'); 
                     unlink('categories/' . $filename);
                     unlink('categories/thumbs/' . $filename);
                     
                     //delete all images in the category
                     $query_2 = "SELECT * FROM images WHERE category = '$category'";
                     $result_2 = mysqli_query($dbc, $query_2);
                     while ($rowi = mysqli_fetch_array($result_2)) {
                        $id = $rowi['id'];
                        $query_3 = "DELETE FROM images WHERE id = $id";
                        mysqli_query($dbc, $query_3) 
                           or die('Error querying database.'); 
                        unlink('images/' . $rowi['filename']);
                        unlink('thumbs/' . $rowi['filename']);
                        
                        //delete all comments for a given image
                        $query_4 = "DELETE FROM comments WHERE picId = $id";
                        mysqli_query($dbc, $query_4) 
                           or die('Error querying database.'); 
                     }
                     echo "<p>Selected category removed.</p>";
                  }

                  // Display the customer rows with checkboxes for deleting
                  $query = "SELECT * FROM categories";
                  $result = mysqli_query($dbc, $query);
                  while ($row = mysqli_fetch_array($result)) {
                     echo '<input type="radio" value="' . $row['id'] . ':' . $row['filename'] . ':' . $row['category'] . '" name="idFileNameBundle" />';
                     echo $row['category'] . '<br />';
                  }
                  mysqli_close($dbc);
               ?>
               <hr>
               <input type="submit" name="submit" value="Remove" />
            </form>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
         </div>
      </div>
   </body>
</html>
