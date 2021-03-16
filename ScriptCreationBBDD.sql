drop database if exists `beavarts`;
create database `beavarts`;

drop user if exists 'beavarts'@'localhost';
create user 'beavarts'@'localhost' identified by 'beavarts';


grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `beavarts`.* to 'beavarts'@'localhost';