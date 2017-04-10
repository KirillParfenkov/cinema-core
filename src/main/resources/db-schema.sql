CREATE TABLE users (
    id VARCHAR(255),
    email VARCHAR(255),
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    birthday DATE
);

CREATE TABLE events (
    id VARCHAR(255),
    name VARCHAR(255),
    price INT,
    rating VARCHAR(20)
);

CREATE TABLE tickets (
    id VARCHAR(255),
    event_id VARCHAR(255),
    date DATE,
    seats VARCHAR(255),
    user_id VARCHAR(255),
    price INT,
    discount INT
);

CREATE TABLE auditorium_bookings (
    date DATE,
    auditorium_id VARCHAR(255),
    event_id  VARCHAR(255)
);

CREATE TABLE counters (
    name VARCHAR(255),
    counter INT
);