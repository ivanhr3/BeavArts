-- One admin user, named admin1 with passwor 4dm1n and authority admin
--INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
--INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

--Usuarios
INSERT INTO users(username,password,enabled) VALUES ('user1', 'user1', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(1, 'user1', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (1, 'Soy un usuario que intenta un poco de todo');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (1, 'Nombre', 'Apellidos', '12234321Q', 'emailprueba@email.com', 'urlfotoperfil.com', 4.5, 1 , 'user1');
INSERT INTO beaver_especialidades(beaver_id, especialidades) VALUES (1, "PINTURA");
INSERT INTO beaver_especialidades(beaver_id, especialidades) VALUES (1, "TEXTIL");
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (1, "foto1.com");
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (1, "foto2.com");

INSERT INTO users(username,password,enabled) VALUES ('user2', 'user2', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(2, 'user2', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (2, 'Soy un usuario que intenta un poco de todo');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (2, 'Nombre', 'Apellidos', '12234321Q', 'emailprueba@email.com', 'urlfotoperfil.com', 4.5, 2 , 'user2');
INSERT INTO beaver_especialidades(beaver_id, especialidades) VALUES (2, "PINTURA");
INSERT INTO beaver_especialidades(beaver_id, especialidades) VALUES (2, "TEXTIL");
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (2, "foto1.com");
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (2, "foto2.com");

--Encargos

INSERT INTO encargos(id, descripcion, disponibilidad, photo, precio, titulo, beaver_id) VALUES (1, 'Mira que descripcion más larga', true, 'foto1.com', 30.0, 'Titulo de Encargo', 1);

--Solicitud

INSERT INTO solicitud(id, descripcion, estado, precio, beaver_id, encargo_id) VALUES (1, 'Descripcion Solicitud', 'PENDIENTE', 30.0, 2, 1);