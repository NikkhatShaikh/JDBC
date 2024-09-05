show databases;
create database mydatabases;

use mydatabases;
show tables;

create table employee (
id int auto_increment primary key,
name varchar(100) not null,
jobTitle varchar(100) not null,
salary double not null);

insert into employee(name,jobTitle,salary) values
('zoya','Backend Developer',75000.00),
('Nikkhat','JAVA Developer',80000.00),
('Swapnil','Delivery Head',120000.00),
('Tejas','QA',95000.00);

select * from employee;

