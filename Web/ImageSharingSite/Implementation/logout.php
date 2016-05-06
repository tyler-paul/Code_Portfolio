<?php require("config.php"); ?>

<?php
  // If the user is logged in, delete the session vars to log them out
  session_start();
  if (isset($_SESSION['userId'])) {
    // Delete the session vars by clearing the $_SESSION array
    $_SESSION = array();

    // Delete the session cookie by setting its expiration to an hour ago (3600)
    if (isset($_COOKIE[session_name()])) {
      setcookie(session_name(), '', time() - 3600);
    }

    // Destroy the session
    session_destroy();
  }

  // Redirect to the home page
  header('Location: ' . DOMAIN);
?>
