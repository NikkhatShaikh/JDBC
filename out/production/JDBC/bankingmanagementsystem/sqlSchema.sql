create database banking_system;
use banking_system;
create table accounts (
accountNumber  bigint auto_increment primary key,
fullName varchar(100) not null,
email varchar(100) unique not null,
balance decimal not null,
securityPin char(4)
);

create table user(
fullName varchar(100) not null,
email varchar(100) not null primary key,
password varchar(100) not null
);

show tables ;
select * from user;
desc accounts;
select * from accounts;
delete from accounts where accountNumber=2;

