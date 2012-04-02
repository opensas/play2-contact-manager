# --- sample contacts

# --- !Ups

insert into contacts(id, name, address) values (1, 'Paul', 'Boston');
insert into contacts(id, name, address) values (2, 'Pablo', 'Buenos Aires');
insert into contacts(id, name, address) values (3, 'Paolo', 'Roma');
insert into contacts(id, name, address) values (4, 'Paulain', 'Paris');

# --- !Downs

delete from contacts;