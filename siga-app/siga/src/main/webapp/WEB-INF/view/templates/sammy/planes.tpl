<div class="section-subheader">
	<% if (estudios.length > 1) { %>

		<% if (currentPlan.length === 0) { %>

			<div class="btn-group">
				<button class="btn" disabled="disabled"><%= estudios[0].nombre %> <%= estudios[0].pa !== null? '('+ estudios[0].pa +')'+ add : '' %></button>
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu plan-filter">
					<% estudios.forEach(function(item, index) { if (index !== 0) { %>
						<li><a href="#" data-plan="<%= item.id %>" data-pa="<%= item.pa %>"><%= item.nombre %> <%= item.pa !== null? '('+ item.pa +')'+ add : '' %></a></li>
					<% } }); %>
				</ul>
			</div>

		<% } else { %>

			<div class="btn-group">
				<button class="btn" disabled="disabled">
					<% estudios.forEach(function(item) { if (item.id === currentPlan) { %>
						<%= item.nombre %> <%= item.pa !== null? '('+ item.pa +')'+ add : '' %>
					<% } }); %>
				</button>
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu plan-filter">
					<% estudios.forEach(function(item) { if (item.id !== currentPlan) { %>
						<li><a href="#" data-plan="<%= item.id %>" data-pa="<%= item.pa %>"><%= item.nombre %> <%= item.pa !== null? '('+ item.pa +')'+ add : '' %></a></li>
					<% } }); %>
				</ul>
			</div>

		<% } %>

	<% } else { %>                
		<span class="label unique-plan"><%= estudios[0].nombre %> <%= estudios[0].pa !== null? '('+ estudios[0].pa +')'+ add : '' %></span>

	<% } %>
</div>