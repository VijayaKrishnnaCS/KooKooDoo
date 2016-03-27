<html>
<head>
<title>KooKooDoo!</title>
<script src="scripts/jquery-1.10.2.min.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">

$(document).ready(function() {
        
                var appName = 'KooKooDoo';
                $.get('/Voila', {
                	appName : appName
                }, function(responseText) {
                        //$(document.body).appendChild(responseText);
                        document.body.appendChild(document.createTextNode('<pre>' + responseText + '</pre>'));
                });
       
});

</script>
</body>
</html>