
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Login</title>
</head>
<body>
<div class="login-form">
  <form action="login" method="post">
    <h1>Login</h1>
    <div class="content">
      <div class="input-field">
        <label>
          <input name="username" type="text" placeholder="Username" autocomplete="nope" required>
        </label>
      </div>
      <div class="input-field">
        <label>
          <input name="password" type="password" placeholder="Password" autocomplete="new-password" required>
        </label>
      </div>
    </div>
    <div class="action">
      <button>Log in</button>
    </div>
  </form>
</div>

</body>
</html>
