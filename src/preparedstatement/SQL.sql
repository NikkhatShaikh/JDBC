show databases;
create database employee;
use employee;
create table employee (
id int(100)  auto_increment primary key,
name varchar(100) not null,
job_title varchar(100) not null

);
show tables;
describe employee;
insert into employee (name,job_title) values 
('nikkhat','JAVA Developer'),
('zoya','Front End Developer');

select * from employee;
select id,name, job_title from employee where name='zoya';
