-- One admin user, named admin1 with passwor 4dm1n and authority admin
--INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
--INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

--Usuarios
INSERT INTO users(username,password,enabled) VALUES ('user1', '$2a$10$QaodejE88za17DUMb8pepuXAR.r8Z.NF.c7DkT7K3SAcPk8NisKTu', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(1, 'user1', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (1, 'Soy un usuario que intenta un poco de todo');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (1, 'Nombre', 'Apellidos', '12234321Q', 'emailprueba@email.com', 'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/doraemon-borracho-1532607482.jpg', 4.5, 1 , 'user1');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (1, 'TEXTIL');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (1, 'ESCULTURA');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (1, 'https://cflvdg.avoz.es/default/2014/05/13/0012_201405G13P57F1jpg/Foto/G13P57F1.jpg');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (1, 'https://estaticos-cdn.elperiodico.com/clip/36cd084f-1cf9-42f8-9db5-4b40ae199a92_alta-libre-aspect-ratio_default_0.jpg');

INSERT INTO users(username,password,enabled) VALUES ('user2', '$2a$10$/9VLfws1dOLXMW.sB4qfWuY2s/KyyB0y2lsaxP3u1Ubh.pbn98H5u', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(2, 'user2', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (2, 'Soy un usuario que intenta un poco de todo');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (2, 'Nombre', 'Apellidos', '12234321Q', 'emailprueba@email.com', 'https://cdn.pixabay.com/photo/2020/10/16/11/02/astronaut-5659227_960_720.png', 4.5, 2 , 'user2');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (2, 'TEXTIL');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (2, 'ESCULTURA');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (2, 'https://cdn.pixabay.com/photo/2020/10/16/11/02/astronaut-5659227_960_720.png');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (2, 'https://cdn.pixabay.com/photo/2021/03/11/13/57/among-us-6087168_960_720.png');

--Encargos

INSERT INTO encargos(id, descripcion, disponibilidad, photo, precio, titulo, beaver_id) VALUES (1, '¿Quieres un retrato de una persona importante para ti y tenerla de recuerdo? Contacta conmigo y te haré un retrato al estilo que quieras.', true, 'https://cdn.pixabay.com/photo/2017/05/09/17/35/portrait-2298842_960_720.jpg', 40.0, 'Retratos de Personas', 1);

--Solicitud

INSERT INTO solicitud(id, descripcion, estado, precio, beaver_id, encargo_id) VALUES (1, 'Descripcion Solicitud', 'PENDIENTE', 30.0, 2, 1);
INSERT INTO solicitud_fotos(solicitud_id, fotos) VALUES (1, 'https://i.imgur.com/V2Y5KIK.png');
INSERT INTO solicitud_fotos(solicitud_id, fotos) VALUES (1, 'https://cdn.pixabay.com/photo/2019/08/28/15/56/anime-girl-4437093_960_720.png');