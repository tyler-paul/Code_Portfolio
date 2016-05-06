<?php require("config.php"); ?>

<div id="nav">
   <h2>Navigation</h2>
   <p>
      <a href="<?php echo DOMAIN; ?>index.php">Home</a><br />
      <?php
         if (isset($_SESSION['username']) && $_SESSION['username'] == 'admin' ) {
      ?>
      <a href="<?php echo DOMAIN; ?>addCategory.php">Add Category</a><br />
      <a href="<?php echo DOMAIN; ?>addImage.php">Add Image</a><br />
      <a href="<?php echo DOMAIN; ?>removeCategory.php">Remove Category</a><br />
      <a href="<?php echo DOMAIN; ?>removeImage.php">Remove Image</a><br />
      <a href="<?php echo DOMAIN; ?>editCategory.php">Edit Category</a><br />
      <a href="<?php echo DOMAIN; ?>editImage.php">Edit Images</a><br />
      <a href="<?php echo DOMAIN; ?>removeComment.php">Remove Comments</a><br />
      <?php
         }
      ?>
      <a href="<?php echo DOMAIN; ?>addUser.php">Sign Up</a><br />
      <a href="<?php echo DOMAIN; ?>login.php">Login</a><br />
      <?php
         session_start();
         if (isset($_SESSION['userId']))
            echo '<a href="' . DOMAIN . 'logout.php">Logout</a><br />';
      ?>
   </p>  
   <h2>Categories</h2>
   <p>
      <?php
         $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
            or die('Error connecting to MySQL server.');

         $query = "SELECT * FROM categories";
         $result = mysqli_query($dbc, $query);
         while ($row = mysqli_fetch_array($result)) {
            echo '<a href="' . DOMAIN . 'viewCategory.php/?category=' . $row['category'] . '">' . $row['category'] . '</a><br />';
         }
         mysqli_close($dbc);
      ?>
   </p>
</div>
