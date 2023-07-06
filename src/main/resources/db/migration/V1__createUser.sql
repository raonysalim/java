create table Proj.Users (

    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    firstName varchar(100) not null,
    lastName varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(100) not null
)