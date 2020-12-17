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

    <br>
    <h3 class="display-4">CURRENT NUMBER : ${value}</h3>

    <br>
    
      <form class="form-inline" action="/submit" method="post">
        <div class="form-group">
          <ul>
            <li>
              <h3 class="display-4">ENTER NEW NUMBER : </h3>
              <input type="text" class="form-control" id="number" name="number" placeholder="Enter here">
            </li>
          </ul>
        </div>
    <br>
    <br>
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>
    </div>
  </div>

</body>
</html>
