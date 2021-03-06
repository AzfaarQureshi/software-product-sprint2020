<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
   String uploadUrl = blobstoreService.createUploadUrl("/my-form-handler");
   System.out.println("BLOB" + uploadUrl);
   %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Azfaar's Portfolio</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
    integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
  <link
    href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
    rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css" rel="stylesheet">
  <link rel="stylesheet" href="style.css">
  <link rel="stylesheet" href="style.css">
  <script src="script.js"></script>
</head>

<body onload="getComments()">
  <div id="container align-items-center pt-5 bg">
    <div class="d-flex p-3 justify-content-center">
      <div class="p-2 justify-content-center">
        <img class="rounded-circle" src="/images/azfaar.jfif" alt="Azfaar's picture" width="200" height="200">
      </div>
      <div class="p-2 justify-content-center align-self-center">
        <h2>Hi, my name is</h2>
        <h1>Azfaar qureshi</h1>
      </div>
    </div>
  </div>
  <div class="d-flex p-3 justify-content-center flex-column">
    <div class="p-2">
      <p>I'm currently a 3rd year Computer Engineering student at the University of Waterloo.
        I have experience with Frontend, Backend and Production engineering.
        I'm graduating in April 2022 and am currently searching for my next internship.
        Take a look at my resume and previous experiences below!</p>
    </div>
    <div class="p-2">
      <p>Click here to get a random greeting:</p>
      <button onclick="addRandomGreeting()">Hello</button>
      <div id="greeting-container"></div>
    </div>
    <h1>COMMENTS BELOW</h1></br>
    <div id="comments">
    </div>
  </div>
  </div>
  <form method="POST" enctype="multipart/form-data" action="/my-form-handler">
    <p>Share your thoughts</p>
    Username: <input type="text" name="user-name"> </br></br>
    comments: <input type="text" name="comment"> </br></br>
    <br/>
    <p>Choose some options:</p>
    <input type="checkbox" name="upper-case" value="true"> Upper-case
    <br/>
    upload: <input type="file" name="blobfile">
    <input type="submit" />
  </form>
</body>
</html>
