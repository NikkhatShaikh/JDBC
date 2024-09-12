show databases;
create database hospital_management_system;
use hospital_management_system;

create table patients(
id int auto_increment primary key,
name varchar(50) not null,
age int (10),
gender varchar (1)
);

create table doctors(
id int auto_increment primary key,
name varchar(50) not null,
specialization varchar(200) not null
);

create table appointments(
id int auto_increment primary key,
patient_id int not null,
doctor_id int not null,
appointment_date date not null,
foreign key (patient_id) references patients(id),
foreign key(doctor_id) references doctors(id)
);

show tables;

insert into doctors(name,specialization) 
values ('Nitish Jain','Physician'),
('Pankaj Gupta','Neurosurgen');

select * from doctors;
