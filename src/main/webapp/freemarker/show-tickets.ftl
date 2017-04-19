<html>
    <body>
        <h1>Ticket List</h1>
        <#if (tickets)??>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Event</th>
                    <th>Date</th>
                    <th>User</th>
                    <th>Price</th>
                </tr>
                <#list tickets as ticket>
                    <tr>
                        <td> ${ticket.id} </td>
                        <td> ${ticket.event}</td>
                        <td> ${ticket.date} </td>
                        <td> ${ticket.user} </td>
                        <td> ${ticket.price} </td>
                    </tr>
                </#list>
            </table>
        </#if>
    </body>
</html>