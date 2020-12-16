<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Test</title>
  <style>
    input[type='submit'], button, [aria-label]{
      cursor: pointer;
    }
    #spoiler{
      display: none;
    }
  </style>
</head>

<body>
    <div class="col-md-12 mt-1">
    <div class="float-left">

    <h1>ENTER NUMBER : </h1>
    <br>
      <form class="form-inline" action="/submit" method="post">
        <div class="form-group">
          <input type="text" class="form-control" id="number" name="number" placeholder="Enter here">
        </div>
        <br>
        <br>
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>
    </div>
    
  </div>

</body>
</html>
