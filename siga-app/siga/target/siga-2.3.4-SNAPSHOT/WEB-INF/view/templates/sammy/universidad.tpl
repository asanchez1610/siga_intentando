<% if (Object.prototype.toString.call(categoriaList) === '[object Array]' && categoriaList.length > 0) { %>

    <% categoriaList.forEach( function( categoria ) { %>
        <% if (categoria.documentoInfoList.length > 0) { %>
            <h3><%= categoria.nombre %></h3>
            <ul class="list-unstyled">
                <% categoria.documentoInfoList.forEach( function( doc ) { %>
                    <li><i class="icon-download"></i> <a href="json/universidad/download/<%= doc.url %>"><%= doc.nombre %></a></li>      
                <% }); %>
            </ul>
        <% } %>
    <% }); %>
<% } %>