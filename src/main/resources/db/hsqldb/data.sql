-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users(username,password,enabled) VALUES ('123','123',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'123','beaver');

/* INSERT INTO users(username,password,enabled) VALUES ('test','test',TRUE);
INSERT INTO beavers(first_name,last_name,dni,email,especialidades,username,password) VALUES ('testName','testLastName','71814254C','valid@gmail.com','barro','test','test') */