window.onload = initPage;

function initPage() {
   document.getElementById("reply").onclick = addComment;
   refreshComments();
   setInterval(refreshComments, 1000);
}

function addComment() {
   commentRequest = createRequest();
   if (commentRequest == null) {
      alert("Unable to create request.");
   }
   else {
      var url = "processComment.php";
      commentRequest.open("POST", url, true);
      commentRequest.onreadystatechange = commentProcessed;
           
      var requestData = "picId=" + 
         escape(document.getElementById("picId").value) + "&comment=" +
         escape(document.getElementById("comment").value);
      commentRequest.setRequestHeader("Content-Type",
         "application/x-www-form-urlencoded");
      commentRequest.send(requestData);
  }
}
function commentProcessed() {
   if (commentRequest.readyState == 4) {
      if (commentRequest.status == 200) {
         refreshComments();
         document.getElementById("comment").value = "";
         window.location.hash = '#bottom';
      }
   }
}
function refreshComments() {
   request = createRequest();
   if (request == null) {
      alert("Unable to create request.");
   }
   else {
      var picId = document.getElementById("picId").value;
      var url = "getComments.php?picId=" + picId;
      request.open("GET", url, true);
      request.onreadystatechange = displayComments;
      request.send(null);
  }
}
function displayComments() {
   if (request.readyState == 4) {
      if (request.status == 200) {
         var commentDetails = eval(request.responseText);
         var commentPane = document.getElementById("commentPane");
         
         // Remove existing item details (if any)
         for (var i = commentPane.childNodes.length; i > 0; i--) {
            commentPane.removeChild(commentPane.childNodes[i-1]);
         }
         
         
         for (i = 0; i < commentDetails.length ; i++) {
            var commentDiv = document.createElement("div");
            commentDiv.className = "imageComment";
            
            var usernameSpan = document.createElement("span");
            usernameSpan.className = "commentUsername";
            usernameSpan.appendChild(document.createTextNode(commentDetails[i].username));
            commentDiv.appendChild(usernameSpan);
            
            var dateSpan = document.createElement("span");
            dateSpan.className = "commentDate";
            dateSpan.appendChild(document.createTextNode(commentDetails[i].date));
            commentDiv.appendChild(dateSpan);
            
            var br = document.createElement("br");
            commentDiv.appendChild(br);
            
            var p = document.createElement("p");
            p.className = "commentText";
            p.appendChild(document.createTextNode(commentDetails[i].comment));
            commentDiv.appendChild(p);
            
            commentPane.appendChild(commentDiv);
         }
      }
   }
}
