<html>
    <body>
        <h1>Auditorium List</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Number Seats</th>
            </tr>
            <#list auditoriums as auditorium>
                <tr>
                    <td> ${auditorium.id} </td>
                    <td> ${auditorium.name} </td>
                    <td> ${auditorium.numberSeats} </td>
                </tr>
            </#list>
        </table>
    </body>
</html>