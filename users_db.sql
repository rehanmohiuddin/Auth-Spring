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
dob varchar(30) not null,
phone varchar(20) not null,
otp varchar(6),
is_verified varchar(10),
token text not null
);

create table sfuit_otp(
user_id integer not null,
phone varchar(15) not null,
token text not null,
otp varchar(6) not null
);
alter table sfuit_otp add constraint otp_id_fk
foreign key (user_id) references sfuit_users(user_id);

create table sfuit_token(
user_id integer not null,
phone varchar(15) not null,
token_updated text
);
alter table sfuit_token add constraint token_id_fk
foreign key (user_id) references sfuit_users(user_id);

create sequence sfuit_users_seq increment 1 start 1;
