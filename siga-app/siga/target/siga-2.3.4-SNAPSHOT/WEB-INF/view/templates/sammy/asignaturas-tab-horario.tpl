<table class="schedule table table-condensed table-bordered">
	<thead>
		<tr>
			<th></th>
			<th>Lunes</th>
			<th>Martes</th>
			<th>Miércoles</th>
			<th>Jueves</th>
			<th>Viernes</th>
			<th>Sábado</th>
		</tr>
	</thead>
	<tbody>
            <% for ( var hour = 1; hour < 17; hour++ ) { %>
                    <tr data-row="<%= hour %>">
                            <td class="hour"><%= hour + 6 %>:00</td>
                            <% for ( var day = 1; day < 7; day++ ) { %>
                                    <td id="ge-<%= hour %>-<%= day %>" class="day" data-col="<%= day %>"></td>
                            <% } %>
                    </tr>
            <% } %>
	</tbody>		
</table>
