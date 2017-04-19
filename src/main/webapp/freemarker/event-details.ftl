<html>
    <body>
        <div> Id: ${event.id} </div>
        <div> Name: ${event.name} </div>
        <div> Price: ${event.price} </div>
        <div> Rating ${event.rating} </div>
        <h2>Auditorium Books: </h2>
        <#if (audBooks)??>
            <#list audBooks as audBook>
                <tr>
                    <td> Date: ${audBook.date} </td>
                    <td> ${audBook.auditorium} </td>
                    <td> <a href="booking?audBookId=${audBook.id}">Book</a> </td>
                    <td> <a href="booking/showAllTickets?audBookId=${audBook.id}">Show Tickets</a> </td>
                </tr>
            </#list>
        </#if>
    </body>
</html>