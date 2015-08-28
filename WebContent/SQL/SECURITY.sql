create database security;

create table user(
   name varchar(16) not null PRIMARY KEY,
   pwd  varchar(32) not null,
   role varchar(16) not NULL
)

INSERT into user VALUES("admin","admin","ROLE_ADMIN");
INSERT into user VALUES("ONE","123456","ROLE_USER");

select * from user;