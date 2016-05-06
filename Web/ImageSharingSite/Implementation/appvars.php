<?php
   function thumbnail($image_path,$thumb_path,$image_name, $desired_width, $desired_height) {
      $filetype = exif_imagetype("$image_path/$image_name");
      $exif = exif_read_data("$image_path/$image_name");
      
      if ($filetype == IMAGETYPE_JPEG) {
         /* read the source image */
         $source_image = imagecreatefromjpeg("$image_path/$image_name");
         if(!empty($exif['Orientation'])) {
            switch($exif['Orientation']) {
               case 8:
                  $source_image = imagerotate($source_image,90,0);
                  break;
               case 3:
                  $source_image = imagerotate($source_image,180,0);
                  break;
               case 6:
                  $source_image = imagerotate($source_image,-90,0);
                  break;
            }
         }
         $width = imagesx($source_image);
         $height = imagesy($source_image);
         
         /* create a new, "virtual" image */
         $virtual_image = imagecreatetruecolor($desired_width, $desired_height);
         
         /* copy source image at a resized size */
         imagecopyresampled($virtual_image, $source_image, 0, 0, 0, 0, $desired_width, $desired_height, $width, $height);
         
         /* create the physical thumbnail image to its destination */
         imagejpeg($virtual_image, "$thumb_path/$image_name", 100);
      }
      elseif ($filetype == IMAGETYPE_PNG){
         $im = ImageCreateFromPNG("$image_path/$image_name"); 
         if(!empty($exif['Orientation'])) {
            switch($exif['Orientation']) {
               case 8:
                  $im = imagerotate($im,90,0);
                  break;
               case 3:
                  $im = imagerotate($im,180,0);
                  break;
               case 6:
                  $im = imagerotate($im,-90,0);
                  break;
            }
         }
         $width = ImageSx($im);              // Original picture width is stored
         $height = ImageSy($im);             // Original picture height is stored
         $newimage=imagecreatetruecolor($desired_width,$desired_height);                 
         imageCopyResized($newimage,$im,0,0,0,0,$desired_width,$desired_height,$width,$height);
         ImagePng($newimage,"$thumb_path/$image_name");
      }
      elseif ($filetype == IMAGETYPE_GIF) {
         $im=ImageCreateFromGIF("$image_path/$image_name");
         if(!empty($exif['Orientation'])) {
            switch($exif['Orientation']) {
               case 8:
                  $im = imagerotate($im,90,0);
                  break;
               case 3:
                  $im = imagerotate($im,180,0);
                  break;
               case 6:
                  $im = imagerotate($im,-90,0);
                  break;
            }
         }
         $width=ImageSx($im);              // Original picture width is stored
         $height=ImageSy($im);                  // Original picture height is stored
         $newimage=imagecreatetruecolor($desired_width,$desired_height);
         imageCopyResized($newimage,$im,0,0,0,0,$desired_width,$desired_height,$width,$height);
         ImageGIF($newimage,"$thumb_path/$image_name");
      }
      else {
         echo "Unsupported file type";
         echo $filetype;
      }
   }
      
      
      
?>
