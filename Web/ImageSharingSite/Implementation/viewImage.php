<?php
   session_start();
   require("config.php");
?>

<?php include 'domain.php'; ?>

<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="turtle.css">
      <script src="utils.js" type="text/javascript"></script> 
      <script src="addComment.js" type="text/javascript"></script> 
      <title>View Image</title>
   </head>
   <body>
      <?php include 'header.php'; ?>
      <div id="container">
         <?php include 'nav.php'; ?>
         <div id="mainContent">
            <?php
               $picId = $_GET['picId'];
               
               $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_DBNAME) 
                     or die('Error connecting to MySQL server.');
               $query = "SELECT * FROM images WHERE id = $picId";
               $result = mysqli_query($dbc, $query);
               $row = mysqli_fetch_array($result);
               $filename = $row['filename'];
               //display image
               echo '<div class="image">';
               echo '<img src=images/' . $filename . '><br />';
               echo '</div>';
               
               //display admin comment
               $comment = $row['comment'];
               echo '<div class="imageComment">';
               echo '<span class="commentUsername">(uploaded by admin)</span>';
               echo '<span class="commentDate">';
               echo date('D M d/Y h:i A', strtotime($row['time']));
               echo '</span>';
               echo '<br />';
               echo '<p class="commentText">' . $comment . '</p>';
               echo '</div>';
               echo '<h3>Comments:</h3>';
            ?>
            
            <div id="commentPane">
            </div>
            
            <?php 
               if (isset($_SESSION['userId']) && isset($_SESSION['username'])) {   
            ?>
               <a name="bottom"></a>
               <form method="post" action="#">
               <input type="hidden" id="picId" name="picId" value=<?php echo $picId; ?>>
               <div class="reply">
               <div class="userLabel">
               <label for="comment">Reply (as user <?php echo $_SESSION['username']; ?>):</label><br />
               </div>
               <textarea class="imageReply" id = "comment" name="comment"></textarea><br />
               <input class="imageReplyButton" type="button" value="Reply" name="reply" id="reply">
               </div>
               </form>
            <?php 
               }
               else {
                  echo '<a class="loginUrl" href="' . $domain . 'login.php">Login to enter a comment</a>';
               }
            ?>
            
         </div>
      </div>
   </body>
</html>
