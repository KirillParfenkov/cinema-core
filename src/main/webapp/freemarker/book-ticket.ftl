<html>
    <head>
        <title>Book Ticket</title>
    </head>
    <body>
        <h2>Book Ticket</h2>
        <form method="post" action="booking">
            <label>Date: ${audBook.date}</label><br>
            <label>Event: ${audBook.event}</label><br>
            <label>Auditorium: ${audBook.auditorium}</label><br>
            <input type="text" name="seats"/> <br>
            <input type="hidden" name="audBookId" value="${audBook.id}"/> <br>
            <input type="submit"/>
        </form>
    </body>
</html>