<?php
   require("config.php");
?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <title>Add User</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <h1>Add User</h1>
            <p>Enter a username and password and click Create to create an account.</p>
            <?php
               if (isset($_POST['submit'])) {
                  $username = $_POST['username'];
                  $password = $_POST['password'];
                  $confirmPassword = $_POST['confirmPassword'];
                  
                  if ($password == $confirmPassword) {
                     if(!empty($username) && !empty($password)) {
                        $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                           or die('Error connecting to MySQL server.');
                           
                        //check if username is unique
                        $query = "SELECT * FROM users WHERE username='$username'";
                        $result = mysqli_query($dbc, $query);
                        $num_results = mysqli_num_rows($result); 
                        if ($num_results == 0) {
                           $query = "INSERT INTO users (username, password) VALUES ('$username', '$password')";
                           $result = mysqli_query($dbc, $query);
                           echo "<p>Created user.</p>";
                           echo '<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>';
                           exit();
                        }
                        else {
                           echo "<p>An existing user already has the given username.</p>";
                        }
                        mysqli_close($dbc);
                     }
                     else {
                        echo '<p>Type all the required fields before submitting.</p>';
                     }
                  }
                  else {
                     echo '<p>Your confirm password does not match your password.</p>';
                  }
               }
            ?>
            <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
               <label for="username">Username:</label> <br />
               <input type="text" id="username" name="username" /><br />
               <label for="password">Password:</label> <br />
               <input type="password" id="password" name="password" /><br />
               <label for="confirmPassword">Confirm Password:</label> <br />
               <input type="password" id="confirmPassword" name="confirmPassword" /><br />
               <hr>
               <input type="submit" value="Create" name="submit">
            </form>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
         </div>
      </div>
   </body>
</html>
