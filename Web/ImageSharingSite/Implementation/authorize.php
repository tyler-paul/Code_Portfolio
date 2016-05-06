<?php
   session_start();
   if (!($_SESSION['username'] == "admin")) {
      exit('<html><head><style>body {background-color: #98FB98;font-family: Verdena, Arial, sans-serif;color: #006400;}</style></head><body><h1>Access Denied</h1><img src="site_graphics/denied.jpg"></body></html>');
   }
?>
