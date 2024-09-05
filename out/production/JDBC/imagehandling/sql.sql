show databases;
create database image_db;
use image_db;

show tables ;
create table image(
id int auto_increment primary key,
imageData longblob not null,
uploadDate timestamp default current_timestamp
);

show tables;
describe iamge;
select * from image;