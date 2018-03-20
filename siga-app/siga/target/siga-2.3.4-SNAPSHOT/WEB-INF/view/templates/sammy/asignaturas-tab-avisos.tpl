<% if (Object.prototype.toString.call(avisos) === '[object Array]' && avisos.length > 0) { %>
    <div class="accordion" id="announcements">
            <% avisos.forEach(function(item) { %>                    
                <div data-general="<%= item.general %>" class="accordion-group">
                        <div class="accordion-heading">
                                <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#announcements" href="#announcement-<%= item.id %>">
                                        <span class="pre-title"><%= item.fecha %></span> <span><%= item.titulo %></span>
                                </a>
                        </div>
                        <div id="announcement-<%= item.id %>" data-id="<%= item.id %>" class="accordion-body collapse">                    
                                <div class="accordion-inner">
                                        <ul class="meta">
                                                <li><i class="meta-icon icon-user"></i><strong>Por:</strong> <%= item.persona.apellidoPaterno %> <%= item.persona.nombres %></li>                                                
                                        </ul>
                                        <div class="content"><%! item.descripcion %></div>
                                </div>
                        </div>
                </div>
            <% }); %>
    </div>

<% } else { %>

	<p class="alert">No se han publicado avisos aún.</p>

<% } %>