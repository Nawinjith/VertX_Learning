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
    <h3  class="display-4">CURRENT NUMBER : ${value}</h3>
     <div ></div>
    <br>
    
      <form class="form-inline" action="/submit" method="post">
        <div class="form-group">
          <ul>
            <li>
              <h3 class="display-4">ENTER NEW NUMBER : </h3>
              <input  type="text" class="form-control" id="number" name="number" placeholder="Enter here">
            </li>
          </ul>
        </div>
    <br>
    <br>
        <button id="mybtn" type="submit" class="btn btn-primary">Submit</button>
      </form>
    </div> 
  </div>
 
    <script language="JavaScript">  
      
        document.getElementById("mybtn").addEventListener("click", (evt) => {
          // const data = JSON.parse(evt.data)

          console.log("Hello from Script");
        });


        const sse = new EventSource("/submit")
        const main = document.getElementById("main")

        sse.addEventListener("update", (evt) => {
          console.log("Hello from Script2");
          const data = JSON.parse(evt.data)
          let div = document.getElementById(data.id);
          if (div === null) {
            div = document.createElement("div")
            main.appendChild(div)
          }
          
  })
 
    </script>

</body>
</html>
