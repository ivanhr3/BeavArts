--Usuarios
--User1
INSERT INTO users(username,password,enabled) VALUES ('user1', '$2a$10$92y/D.0vHcQtnBgATuJmPeMfCBYI08aNCcCxFV8gtKSyr/Y1zuIw.', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(1, 'user1', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (1, 'Soy un usuario que intenta un poco de todo');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (1, 'Nombre', 'Apellidos', '12234321Q', 'emailprueba@email.com', 'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/doraemon-borracho-1532607482.jpg', 4.5, 1 , 'user1');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (1, 'TEXTIL');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (1, 'ESCULTURA');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (1, 'https://cflvdg.avoz.es/default/2014/05/13/0012_201405G13P57F1jpg/Foto/G13P57F1.jpg');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (1, 'https://estaticos-cdn.elperiodico.com/clip/36cd084f-1cf9-42f8-9db5-4b40ae199a92_alta-libre-aspect-ratio_default_0.jpg');

--User2
INSERT INTO users(username,password,enabled) VALUES ('user2', '$2a$10$/h5dlN5Jmeimr/lkdzt/vel9l9YWftfHqI8Opwg75fih62m0cdJSi', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(2, 'user2', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (2, 'Soy un usuario que intenta un poco de todo');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (2, 'Nombre', 'Apellidos', '12234321Q', 'emailprueba@email.com', 'https://cdn.pixabay.com/photo/2020/10/16/11/02/astronaut-5659227_960_720.png', 4.5, 2 , 'user2');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (2, 'TEXTIL');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (2, 'ESCULTURA');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (2, 'https://cdn.pixabay.com/photo/2020/10/16/11/02/astronaut-5659227_960_720.png');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (2, 'https://cdn.pixabay.com/photo/2021/03/11/13/57/among-us-6087168_960_720.png');

--Admin user
INSERT INTO users(username,password,enabled) VALUES ('adm','$2a$10$B4srWD5ejof0cIiI1CsvseKsIbg01bc9L/40aduSxL9vdKp1fSHKO',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'adm','admin');
INSERT INTO portfolio(id, sobre_mi) VALUES (3, 'Soy un administrador con todas las funcionalidades activas.');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (3, 'Nombre', 'Apellidos', '92234321Q', 'adminprueba@email.com', 'https://cdn.pixabay.com/photo/2013/07/13/13/38/man-161282_960_720.png', 4.5, 3 , 'adm');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (3, 'RESINA');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (3, 'ESCULTURA');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (3, 'https://cdn.pixabay.com/photo/2013/03/29/13/39/system-97634_960_720.png');
INSERT INTO portfolio_photos(portfolio_id, photos) VALUES (3, 'https://cdn.pixabay.com/photo/2013/07/13/01/15/preferences-155386_960_720.png');

--Encargos

INSERT INTO encargos(id, descripcion, disponibilidad, photo, precio, titulo, beaver_id) VALUES (1, '¿Quieres un retrato de una persona importante para ti y tenerla de recuerdo? Contacta conmigo y te haré un retrato al estilo que quieras.', true, 'https://cdn.pixabay.com/photo/2017/05/09/17/35/portrait-2298842_960_720.jpg', 40.0, 'Retratos de Personas', 1);

--Solicitud

INSERT INTO solicitud(id, descripcion, estado, precio, beaver_id, encargo_id) VALUES (1, 'Descripcion Solicitud', 'PENDIENTE', 30.0, 2, 1);
INSERT INTO solicitud_fotos(solicitud_id, fotos) VALUES (1, 'https://i.imgur.com/V2Y5KIK.png');
INSERT INTO solicitud_fotos(solicitud_id, fotos) VALUES (1, 'https://cdn.pixabay.com/photo/2019/08/28/15/56/anime-girl-4437093_960_720.png');

--Usuarios Piloto

-- 1
INSERT INTO users(username,password,enabled) VALUES ('antonioromerocaceres', '$2a$10$R4qdgFE0oP3jLUkHlHB2NOz2U0n6q07Ea/CxcCJneBEcEqOMTy5Ni', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(4, 'antonioromerocaceres', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (4, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (4, 'Antonio', 'Romero', '12234321Q', 'antonioromerocaceres@hotmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 4, 'antonioromerocaceres');

-- 2
INSERT INTO users(username,password,enabled) VALUES ('jaimelucaslozano', '$2a$10$0tv/EDzU4JYmJcxv7FvETOIMOCmtKkvnmtqvI1s5srA0cD24I1ksa', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(5, 'jaimelucaslozano', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (5, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (5, 'Jaime', 'Lucas', '12234321Q', 'jaimelucaslozano@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 5, 'jaimelucaslozano');

-- 3 
INSERT INTO users(username,password,enabled) VALUES ('fjsmdemairena', '$2a$10$r.rLyefiFio1d/emEBumfOMOc3.r2tVRsyiyhn1rOWhNml3CtbmpG', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(6, 'fjsmdemairena', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (6, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (6, 'Francisco Javier', 'Sánchez', '12234321Q', 'fjsmdemairena@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 6, 'fjsmdemairena');

-- 4
INSERT INTO users(username,password,enabled) VALUES ('saaraacm', '$2a$10$F8xg6BV1oMjFvyB6Djc/xOwUtxH.6XFs4kbB0Ib6x7pF4dBumJDoC', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(7, 'saaraacm', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (7, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (7, 'Sara', 'CM', '12234321Q', 'saaraacm@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 7, 'saaraacm');

-- 5
INSERT INTO users(username,password,enabled) VALUES ('loligomezruiz1398', '$2a$10$BmRsd5Ww/6uD2qcnTmqF/eKjvRa2x6wxB/h.plSz96PTOwlN8iF4.', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(8, 'loligomezruiz1398', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (8, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (8, 'Lola', 'Gomez Ruiz', '12234321Q', 'loligomezruiz1398@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 8, 'loligomezruiz1398');

-- 6
INSERT INTO users(username,password,enabled) VALUES ('lizana1709', '$2a$10$TVVauLWk0/YuI8QFrbe0oO/rG4Qa6m.MAAPN6S5YzU/rE7vR/F5x2', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(9, 'lizana1709', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (9, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (9, 'Antonio', 'Lizana Larado', '12234321Q', 'lizana1709@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 9, 'lizana1709');

-- 7
INSERT INTO users(username,password,enabled) VALUES ('fabio_bb', '$2a$10$1y.jbY/M0nZ43rW.YXBLEuRsfnNuY5k8bKuk91SVCffYPeVLy4OVW', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(10, 'fabio_bb', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (10, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (10, 'Fabio', 'Gañán Riesco', '12234321Q', 'fabio_fgr95@hotmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 10, 'fabio_bb');

-- 8
INSERT INTO users(username,password,enabled) VALUES ('zaidafernandezuni', '$2a$10$Jcrk4XZtHGNb0h37GFEPU.dyYg55goDpPVs4o5avUSJbRh4vJE8P6', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(11, 'zaidafernandezuni', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (11, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (11, 'Zaida', 'Fernández', '12234321Q', 'zaidafernandezuni@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 11, 'zaidafernandezuni');

-- 9
INSERT INTO users(username,password,enabled) VALUES ('gonzalolou', '$2a$10$IgrTrRlh6eSTiJUTmp05YuFvpKzK9kXgATEh6qfLvgmr5r94WmGVq', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(12, 'gonzalolou', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (12, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (12, 'Gonzalo', 'López', '12234321Q', 'gonzalolopez9537@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 12, 'gonzalolou');


-- 10
INSERT INTO users(username,password,enabled) VALUES ('leneogomez', '$2a$10$sflI3GkmQHpkMsRd9K85N.Ebht61bZIUkl0F.gUZXRdTlEDJApTSC', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(13, 'leneogomez', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (13, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (13, 'Rodrigo', 'Gómez Romero', '12234321Q', 'leneogomez@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 13, 'leneogomez');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (13, 'ILUSTRACION');

-- 11
INSERT INTO users(username,password,enabled) VALUES ('davidmarcosduran1598', '$2a$10$jF.g1x5dhQe5JmtsoFnptO1CgXDvQEWuignhQMv/06vg10KJ2cA4W', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(14, 'davidmarcosduran1598', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (14, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (14, 'David Marcos', 'Durán', '12234321Q', 'davidmarcosduran1598@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 14, 'davidmarcosduran1598');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (14, 'ILUSTRACION');

-- 12
INSERT INTO users(username,password,enabled) VALUES ('lolaojedamoreno', '$2a$10$CGAYHDDCFHy6gt/baWGGpePnMqtcL5uaPF.qq38MErUj9sWbNgZWa', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(15, 'lolaojedamoreno', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (15, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (15, 'Lola', 'Ojeda Moreno', '12234321Q', 'lolaojedamoreno@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 15, 'lolaojedamoreno');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (15, 'ILUSTRACION');

-- 13
INSERT INTO users(username,password,enabled) VALUES ('andreamarquezbejarano', '$2a$10$kfn9bPfZOcHCLlCxZioqBeazIX8bCmD0CWhHSMwqTimyaul4oVDOu', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(16, 'andreamarquezbejarano', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (16, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (16, 'Andrea', 'Márquez Bejarano', '12234321Q', 'andreamarquezbejarano@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 16, 'andreamarquezbejarano');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (16, 'FOTOGRAFIA');

-- 14
INSERT INTO users(username,password,enabled) VALUES ('paulaperiq', '$2a$10$.7rrkfgw43LMoCc/OLdM2e9kwCyH5l7gaa4jnTkHT/yYfORp3zZwS', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(17, 'paulaperiq', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (17, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (17, 'Paula', 'Periq', '12234321Q', 'paulaperiq@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 17, 'paulaperiq');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (17, 'ILUSTRACION');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (17, 'RESINA');

-- 15
INSERT INTO users(username,password,enabled) VALUES ('valleafanderibera', '$2a$10$9XsaDvfvpdDmkn4TfX39t.ZY5uBxrw.omnfXoWkaD4hPiyDJch97O', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(18, 'valleafanderibera', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (18, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (18, 'Valle', 'Afán de Ribera', '12234321Q', 'valleafanderibera@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 18, 'valleafanderibera');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (18, 'JOYERIA');

-- 16
INSERT INTO users(username,password,enabled) VALUES ('geekroomproject', '$2a$10$RoXbb/aw.uvDVPiI9sOCk.uCZQn4HzmOe67Detpinxgm9qwJEvGvq', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(19, 'geekroomproject', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (19, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (19, 'Geek', 'Room', '12234321Q', 'geekroomproject@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 19, 'geekroomproject');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (19, 'ESCULTURA');

-- 17
INSERT INTO users(username,password,enabled) VALUES ('gloriamanolo9874', '$2a$10$MLRRhxnQMVpfjpiE1D20VeKq7JVTeFUkjDzTPlnI0xREgjU/REyxu', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(20, 'gloriamanolo9874', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (20, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (20, 'Gloria', 'Manolo', '12234321Q', 'gloriamanolo9874@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 20, 'gloriamanolo9874');

-- 17
INSERT INTO users(username,password,enabled) VALUES ('pablopg2001', '$2a$10$Fi7GKZrzhAdv7LekbdqnJeDn6vIxEm1zwZ4axhva78/EgvNlVmeky', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(21, 'pablopg2001', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (21, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (21, 'Pablo', 'PG', '12234321Q', 'pablopg2001@hotmail.es', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 21, 'pablopg2001');

-- 18
INSERT INTO users(username,password,enabled) VALUES ('leon-lara-leilo', '$2a$10$Xe2qXt3WxC8s5LG2uYEwq.iOY.53VRSyTWrhV/APoCy0In5iZasPm', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(22, 'leon-lara-leilo', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (22, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (22, 'Manuel', 'León Lara', '12234321Q', 'leon-lara@hotmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 22, 'leon-lara-leilo');

-- 19
INSERT INTO users(username,password,enabled) VALUES ('mmejiasroldan', '$2a$10$5YGgKj2AzLzzmSkAM5A0SuNW0Naiy.3Ks202sTijuDYSxmU.JWbdG', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(23, 'mmejiasroldan', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (23, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (23, 'María', 'Mejías Roldán', '12234321Q', 'mmejiasroldan@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 23, 'mmejiasroldan');

-- 20
INSERT INTO users(username,password,enabled) VALUES ('mejiasfatima15', '$2a$10$k5Fixl9Qu/YARl53gaWc0.2iiVboSfIPtIyQTF9NxjIJ0CXDg4OjC', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(24, 'mejiasfatima15', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (24, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (24, 'Fátima', 'Mejías Roldán', '12234321Q', 'mejiasfatima15@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 24, 'mejiasfatima15');

-- 21
INSERT INTO users(username,password,enabled) VALUES ('canimejias1963', '$2a$10$RmLfF5BUcwXzNF9ESKCfd.FIMExPGCYkWK1covQr3Z4SPKGuu57RO', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(25, 'canimejias1963', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (25, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (25, 'Francisco', 'Cani Mejías', '12234321Q', 'canimejias1963@hotmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 25, 'canimejias1963');

-- 22
INSERT INTO users(username,password,enabled) VALUES ('5araquell13', '$2a$10$tBlrfatLPBiy.5eq5Kp1EeEk0iMd8iPqFB94PvSoqTSaKN.xGRsge', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(26, '5araquell13', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (26, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (26, 'Raquel', '', '12234321Q', '5araquell13@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 26, '5araquell13');

-- 23
INSERT INTO users(username,password,enabled) VALUES ('saracs20101', '$2a$10$EEXkFnTFqTOuCHUbsNM8WuOIu8xtNBt77ps7xldEV.F97HRiAyEA6', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(27, 'saracs20101', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (27, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (27, 'Sara', 'CS', '12234321Q', 'saracs20101@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 27, 'saracs20101');

-- 24
INSERT INTO users(username,password,enabled) VALUES ('nmigomf', '$2a$10$deuLitKapPc9XXXuGURdbehpbiJXMkJIyZm2MsmnnAZVcVvRPA5z6', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(28, 'nmigomf', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (28, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (28, 'Nmigo', 'MF', '12234321Q', 'nmigomf@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 28, 'nmigomf');

-- 25
INSERT INTO users(username,password,enabled) VALUES ('ukeleleazul', '$2a$10$IudbuSqhyh9eROtUvhtgdOOqRLbq1GiJxodauo5n4QxizQP/Vhd.K', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(29, 'ukeleleazul', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (29, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (29, 'Paula', 'Ukelele', '12234321Q', 'ukeleleazul@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 29, 'ukeleleazul');

-- 26
INSERT INTO users(username,password,enabled) VALUES ('alvaro.cotrina.gomez', '$2a$10$mBnGqdEu6PTK8I.UfW0kUuMQW.hQfzMTOnI6GzGJbFTY5Z2xxyoPi', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(30, 'alvaro.cotrina.gomez', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (30, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (30, 'Álvaro', 'Cotrina Gómez', '12234321Q', 'alvaro.cotrina.gomez@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 30, 'alvaro.cotrina.gomez');

-- 27
INSERT INTO users(username,password,enabled) VALUES ('jonyeld3', '$2a$10$C8Xa/2iOjRPX61EQzQKvqeY3q23IPQLn0dxmPLsxJCpmgWEqOzjGe', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(31, 'jonyeld3', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (31, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (31, 'Juan', 'Pérez', '12234321Q', 'jonyeld3@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 31, 'jonyeld3');

-- 28
INSERT INTO users(username,password,enabled) VALUES ('fernanpm95', '$2a$10$uPjHNQ6UnqMXZ9j5M63eCuBikVsZIBrmXgZl.6O3YonuW/vdyjG42', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(32, 'fernanpm95', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (32, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (32, 'Fernando', 'Peñuela Martín', '12234321Q', 'fernanpm95@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 32, 'fernanpm95');

-- 29
INSERT INTO users(username,password,enabled) VALUES ('dropboxsergio', '$2a$10$UHyDUI4PlGbCSe7dHmM3t.3uA.ryUw5dUR7/UV/WN7E7yO/2iuddG', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(33, 'dropboxsergio', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (33, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (33, 'Sergio', 'Ricor Collado', '12234321Q', 'dropboxsergio@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 33, 'dropboxsergio');

-- 30
INSERT INTO users(username,password,enabled) VALUES ('valeriagarr29', '$2a$10$hOKKiVwWq9umxw0nqUOwwepOGHOaNj7k0mUZ1jO.LyHpQw/j.0uRW', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(34, 'valeriagarr29', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (34, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (34, 'Valeria', 'Rivero Garrido', '12234321Q', 'valeriagarr29@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 34, 'valeriagarr29');

-- 31
INSERT INTO users(username,password,enabled) VALUES ('aleaguvar', '$2a$10$LUnSOZ74UDhwWY7MykIf/uU0./SmyIjtbif6rzxMoMVGo064h8DtK', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(35, 'aleaguvar', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (35, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (35, 'Alejandro', 'Aguilar Vargas', '12234321Q', 'aleaguvar@alum.us.es', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 35, 'aleaguvar');

-- 32
INSERT INTO users(username,password,enabled) VALUES ('juanma.moreno.cen', '$2a$10$7dhpp1iIgNuWIRDNNUVcPu6uh0gj0xG1d5Fdabp3gHi3JYYqRDo/W', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(36, 'juanma.moreno.cen', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (36, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (36, 'Juan Manuel', 'Moreno Cenizo', '12234321Q', 'juanma.moreno.cen@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 36, 'juanma.moreno.cen');

-- 33
INSERT INTO users(username,password,enabled) VALUES ('martaossuna', '$2a$10$gpuU.fdZXtp1Pw5lwRE5/uK43PLhFzNyXc97vsRtB3.jJ0Uo/XJC6', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(37, 'martaossuna', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (37, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (37, 'Marta', 'Osuna Fernández', '12234321Q', 'martaossuna@hotmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 37, 'martaossuna');

-- 34
INSERT INTO users(username,password,enabled) VALUES ('lio_messi2807', '$2a$10$5uHsjf3BYMoCzlkL5UF7Oed7KCFGCIPGh7.8f5dWg1W2mWortYyMa', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(38, 'lio_messi2807', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (38, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (38, 'Miguel', 'Jiménez', '12234321Q', 'lio_messi2807@hotmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 38, 'lio_messi2807');

-- 35
INSERT INTO users(username,password,enabled) VALUES ('aooninoie', '$2a$10$mp/uyXYXpekpCbcNkVkH6OHO5VwVHoXHoBccNmP4KJ38osurJqefi', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(39, 'aooninoie', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (39, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (39, 'Cristóbal', 'Cruz', '12234321Q', 'aooninoie@gmail.com', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 39, 'aooninoie');

-- 36
INSERT INTO users(username,password,enabled) VALUES ('cacprocopiocole4', '$2a$10$i5psi3mmTwwnRGFVUaDr9O33TkhiS4lGjTh5Ir35DSWjEeOwE3ApG', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(40, 'cacprocopiocole4', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (40, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (40, 'Clara', 'Cobián Aparicio', '12234321Q', 'cacprocopiocole4@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 40, 'cacprocopiocole4');

-- 37
INSERT INTO users(username,password,enabled) VALUES ('carmen.ruiz.menea', '$2a$10$e2uUkbNksnYUMHwQIrC6YuiZm6dWUiX9DYqtNo34xbpKk3W9dKKY6', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(41, 'carmen.ruiz.menea', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (41, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (41, 'Carmen', 'Ruiz Menea', '12234321Q', 'carmen.ruiz.menea@gmail.com', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 41, 'carmen.ruiz.menea');

-- 38
INSERT INTO users(username,password,enabled) VALUES ('martaBeaver', '$2a$10$pthnQTdMpIbzSOKn4LjXA.0XLWTyh8/ynCx3fzurM3SrTKDavBOhO', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(42, 'martaBeaver', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (42, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (42, 'Marta', 'Beaver', '12234321Q', '610201846', 'https://www.goodhandbrand.com/willhitegradingnew/wp-content/uploads/2018/07/no-profile-picture-icon-female-3.jpg', 4.5, 42, 'martaBeaver');
INSERT INTO beaver_especialidades(beaver_id, especialidad) VALUES (42, 'TEXTIL');

-- 39
INSERT INTO users(username,password,enabled) VALUES ('manuelBeaver', '$2a$10$gMxEqkOwp77vChVuq7TWzOZqH1tYr779KILVHoTkyyE1rLUCewLUm', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(43, 'manuelBeaver', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (43, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (43, 'Manuel', 'Beaver', '12234321Q', '681330184', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 43, 'manuelBeaver');

-- 40
INSERT INTO users(username,password,enabled) VALUES ('sergioBeaver', '$2a$10$RggHtuShFhiZnonGaWcVmOSAN85qz3z9fn84njPJ3HlH5bmCCgN1a', TRUE);
INSERT INTO authorities(id, username, authority) VALUES(44, 'sergioBeaver', 'user');
INSERT INTO portfolio(id, sobre_mi) VALUES (44, '');
INSERT INTO beavers(id, first_name, last_name, dni, email, url_foto_perfil, valoracion, portfolio_id, username) VALUES (44, 'Segio', 'Beaver', '12234321Q', '670540509', 'https://www.searchpng.com/wp-content/uploads/2019/02/Men-Profile-Image-715x657.png', 4.5, 44, 'sergioBeaver');

