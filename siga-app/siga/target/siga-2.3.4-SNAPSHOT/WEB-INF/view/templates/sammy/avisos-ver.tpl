<ul class="meta">
	<li><i class="meta-icon icon-user"></i><strong>Por:</strong> <%= de %></li>
	<li>
		<i class="meta-icon icon-cloud-download"></i>
		<strong>Adjuntos:</strong>
		<% adjuntos.forEach(function(value) { %>
			<a href="<%= value.url %>"><%= value.nombre %></a>
		<% }); %>
	</li>
</ul>
<div class="content"><%= cuerpo %></div>