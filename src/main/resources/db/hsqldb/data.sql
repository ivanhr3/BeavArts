-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

-- Users

INSERT INTO users(username,password,nombre,apellidos,enabled) VALUES ('user1','user1','Juan','Garcia',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'user1','user');
INSERT INTO beavers(id,email,especialidades,dni,username) VALUES (4,'user1@gmail.com','TEXTIL','29519888N','user1');

INSERT INTO users(username,password,nombre,apellidos,enabled) VALUES ('user2','user2','Ana','Rodriguez',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'user2','user');
INSERT INTO beavers(id,email,especialidades,dni,username) VALUES (5,'user2@gmail.com','ESCULTURA','29519888N','user2');

-- Encargos

INSERT INTO encargos(titulo,precio,disponibilidad,descripcion,photo,beaver) VALUES ('Encargo de prueba', 50.00, true,'Esta es la descripcion', 'https://images-na.ssl-images-amazon.com/images/I/715sEKdVNsL._AC_SY606_.jpg', 4);
INSERT INTO encargos(titulo,precio,disponibilidad,descripcion,photo,beaver) VALUES ('Encargo segundo', 30.00, false,'Este encargo no esta disponible', 'https://www.biografiasyvidas.com/biografia/g/fotos/gogh_sembrador.jpg', 4);

-- Solicitudes

INSERT INTO solicitud(estado,precio,encargo,beaver) VALUES ('PENDIENTE',50.00,'Encargo de prueba',5);