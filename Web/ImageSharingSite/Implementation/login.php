<?php
   session_start();
   require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Login</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <?php
               if (!isset($_SESSION['userId']) && !isset($_SESSION['username'])) {
                  echo '<h1>Login</h1>';
                  echo '<p>Enter a username and password and click Login to login to your account.</p>';
                  
                  if (isset($_POST['submit'])) {
                     $username = $_POST['username'];
                     $password = $_POST['password'];
                     
                     if (!empty($username) && !empty($password)) {
                        $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                              or die('Error connecting to MySQL server.');
                              
                        //check if password is correct
                        $query = "SELECT * FROM users WHERE username='$username'";
                        $result = mysqli_query($dbc, $query);
                        $num_results = mysqli_num_rows($result); 
                        if ($num_results != 0) {
                           $row = mysqli_fetch_array($result);
                           if ($row['password'] == $password) {
                              echo "<p>You are logged in as $username.</p>";
                              $_SESSION['username'] = $username;
                              $_SESSION['userId'] = $row['id'];
                             
                              $url = $_POST['referer'];
                              //allow user to go back to image
                              if (strpos($url, 'viewImage') !== false)
                                 echo '<a class= "webUrl" href="' . $url . '">Return to previous page</a>';
                              echo '<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>';
                              exit();
                           }
                           else {
                              echo "<p>You have entered an incorrect password.</p>";
                           }
                        }
                        else {
                           echo "<p>There does not exist a user with this username.</p>";
                        }
                        mysqli_close($dbc);
                     }
                     else {
                        echo '<p>Type all the required fields before submitting.</p>';
                     }
                  }
            ?>
                  <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
                     <input type="hidden" name="referer" value="<?php echo $_SERVER['HTTP_REFERER']; ?>">
                     <label for="username">Username:</label> <br />
                     <input type="text" id="username" name="username" /><br />
                     <label for="password">Password:</label> <br />
                     <input type="password" id="password" name="password" /><br />
                     <hr>
                     <input type="submit" value="Login" name="submit">
                  </form>
                  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
            <?php
               }
               else {
                  echo '<p>You are already logged in as ' . $_SESSION['username'] .'.</p>';
                  echo '<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>';
               }
            ?>
         </div>
      </div>
   </body>
</html>
