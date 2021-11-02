drop database userdb;
drop user authadmin;
create user authadmin with password 'password';
create database userdb with template=template0 owner=authadmin;
\connect userdb;
alter default privileges grant all on tables to authadmin;
alter default privileges grant all on sequences to authadmin;

create table sfuit_users(
user_id integer primary key not null,
email varchar(30) not null,
name varchar(20) not null,
password text not null,
dob varchar(30) not null,
phone varchar(20) not null,
otp varchar(5),
is_verified varchar(10) not null
);

create sequence sfuit_users_seq increment 1 start 1;
