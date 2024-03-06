<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>tt</title>
</head>
<body>

<h1>Add New Content</h1>
<%--<form action="insert-content" method="post">--%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div>
    <table>
        <tr><td>Title:</td><td><label>
            <input type="text" id="title"/>
        </label></td></tr>
        <tr><td>Description:</td><td><label>
            <input type="text" id="description"/>
        </label></td></tr>
        <tr><td>URL:</td><td><label>
            <input type="text" id="url"/>
        </label></td></tr>

    </table>
</div>
<button id="insert-button">add Content</button>


<br/>
<br/>
<button id="submit-button">Submit Contents to Sv</button>
<br/>
<br/>
<br/>
<a href="content-display">view Content</a>
<script src="multipleAdd.js" type="text/javascript"></script>

</body>
</html>