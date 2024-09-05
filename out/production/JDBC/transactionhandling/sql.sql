show databases;
create database mydatabases;

use mydatabases;
show tables;

create table accounts (
accountNumber int auto_increment primary key,
balance decimal not null
);

select * from accounts;

insert into accounts 
values 
(123,10000.00),
(456,5000.00),
(789,8000.00);





