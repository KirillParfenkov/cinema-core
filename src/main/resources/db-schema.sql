CREATE TABLE user_accounts (
    id VARCHAR(255),
    amount INT,
    user_id VARCHAR(255)
);

CREATE TABLE users (
    id VARCHAR(255),
    email VARCHAR(255),
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    password VARCHAR(255),
    roles VARCHAR(255),
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
    id VARCHAR(255),
    date DATE,
    auditorium_id VARCHAR(255),
    event_id  VARCHAR(255)
);

CREATE TABLE counters (
    name VARCHAR(255),
    counter INT
);

CREATE TABLE persistent_logins (
    username VARCHAR(64) not null,
    series VARCHAR(64) primary key,
    token VARCHAR(64) not null,
    last_used timestamp not null
);