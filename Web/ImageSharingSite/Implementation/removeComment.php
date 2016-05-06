<?php
  require_once('authorize.php');
  require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Remove Comments</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Remove Comments</h1>
            <p>Check all comments that you want to delete and then click Remove.</p>
            <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
               <?php
                  $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                     or die('Error connecting to MySQL server.');

                  // Delete rows (only if the form has been submitted)
                  if (isset($_POST['submit'])) {
                     foreach ($_POST['todelete'] as $id) {
                        $query = "DELETE FROM comments WHERE id = $id";
                        mysqli_query($dbc, $query)
                           or die('Error querying database.');
                     } 
                     echo "<p>Selected comments were removed.</p>";
                  }

                  // Display the customer rows with checkboxes for deleting
                  $query = "SELECT * FROM images";
                  $result = mysqli_query($dbc, $query);
                  echo '<hr />';
                  while ($row = mysqli_fetch_array($result)) {
                     $picId = $row['id'];
                     $query2 = "SELECT * FROM comments WHERE picId=$picId";
                     $result2 = mysqli_query($dbc, $query2);
                     //display image
                     echo '<img src=thumbs/' . $row['filename'] . '><br />';
                     //display comments for image
                     while ($row2 = mysqli_fetch_array($result2)) {
                        echo '<p>';
                        echo '<input type="checkbox" value=' . $row2['id'] . ' name= todelete[]" />';
                        
                        //get username
                        $userId = $row2['userId'];
                        $query3 = "SELECT * FROM users WHERE id=$userId";
                        $result3 = mysqli_query($dbc, $query3);
                        $row3 = mysqli_fetch_array($result3);
                        $username = $row3['username'];
                        
                        
                        echo '<strong>user</strong>: ' . $username;
                        echo ' / <strong>comment</strong>: ' . $row2['comment'];
                        echo '</p>';
                     }
                     echo '<hr />';
                  }
                  mysqli_close($dbc);
               ?>
               <input type="submit" name="submit" value="Remove" />
            </form>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
         </div>
      </div>
   </body>
</html>
