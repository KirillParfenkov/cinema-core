<html>
    <body>
        <h1>Event List</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Rating</th>
            </tr>
            <#list events as event>
                <tr>
                    <td> ${event.id} </td>
                    <td> <a href="events?name=${event.name}">${event.name}</a> </td>
                    <td> ${event.price} </td>
                    <td> ${event.rating} </td>
                </tr>
            </#list>
        </table>
    </body>
</html>