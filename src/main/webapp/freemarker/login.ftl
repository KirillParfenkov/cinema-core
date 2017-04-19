<form action="login" method="post">
	<#if (param.error)??>
		<p>
			Invalid username and password.
		</p>
	</#if>
	<p>
		<label for="username">Username</label>
		<input type="text" id="username" name="username"/>
	</p>
	<p>
		<label for="password">Password</label>
		<input type="password" id="password" name="password"/>
	</p>
	<button type="submit" class="btn">Log in</button>
</form>