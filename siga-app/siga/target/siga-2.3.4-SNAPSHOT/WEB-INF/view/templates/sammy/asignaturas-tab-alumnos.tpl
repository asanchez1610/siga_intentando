<% if (Object.prototype.toString.call(alumnos) === '[object Array]' && alumnos.length > 0) { %>

	<ol>
		<% alumnos.forEach(function(item) { %>
			<li><%= item.apellidoPaterno %> <%= item.apellidoMaterno %>, <small><i><%= item.nombres %></i></small></li>
		<% }); %>
	<ol>

<% } else { %>

	<p class="alert">Ocurrió algún error al cargar la lista de alumnos.</p>

<% } %>