# --- Contacts schema

# --- !Ups

create table contacts(
	id 		integer			not null auto_increment,
	name	varchar(50) 	not null default 'new conctact',
	address	varchar(200) 	not null default 'new conctact''s address',
	constraint pk_contact primary key (id)
);
 
# --- !Downs

drop table contacts;