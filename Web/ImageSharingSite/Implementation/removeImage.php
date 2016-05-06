<?php
  require_once('authorize.php');
  require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Remove Image</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Remove Image</h1>
            <p>Check all images that you want to delete and then click Remove.</p>
            <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
               <?php
                  $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME)
                     or die('Error connecting to MySQL server.');

                  // Delete rows (only if the form has been submitted)
                  if (isset($_POST['submit'])) {
                     foreach ($_POST['todelete'] as $idFileNameBundle) {
                        $pieces = explode(":", $idFileNameBundle);
                        $delete_id = $pieces[0];
                        $filename = $pieces[1];
                        $query = "DELETE FROM images WHERE id = $delete_id";
                        mysqli_query($dbc, $query)
                           or die('Error querying database.');
                        unlink('images/' . $filename);
                        unlink('thumbs/' . $filename);
                        
                        //delete all comments for a given image
                        $query_2 = "DELETE FROM comments WHERE picId = $delete_id";
                        mysqli_query($dbc, $query_2) 
                           or die('Error querying database.'); 
                     } 
                     echo "<p>Selected images were removed.</p>";
                  }

                  // Display the customer rows with checkboxes for deleting
                  $query = "SELECT * FROM images";
                  $result = mysqli_query($dbc, $query);
                  while ($row = mysqli_fetch_array($result)) {
                     echo '<input type="checkbox" value="' . $row['id'] . ':' . $row['filename'] . '" name="todelete[]" />';
                     echo $row['category'] . '<br />';
                     echo '<img src=thumbs/' . $row['filename'] . '><br />';
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
