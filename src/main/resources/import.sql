INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Andres', 'Guzman', 'andres@mail.com', '2017-08-28');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Pedro', 'Sanchez', 'pedro@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samanta', 'Rodriguez', 'samantamail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Dionicio', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Leon', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');
INSERT INTO clientes (id_cliente, nombre, apellido, email, created_at) VALUES (DEFAULT, 'Samuel', 'Larico', 'slarico@mail.com', '2022-12-22');

INSERT INTO productos(nombre, precio, created_at) VALUES ('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos(nombre, precio, created_at) VALUES ('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO productos(nombre, precio, created_at) VALUES ('Apple iPod shuffle', 1499990, NOW());
INSERT INTO productos(nombre, precio, created_at) VALUES ('Sony Notebook Z110', 37990, NOW());
INSERT INTO productos(nombre, precio, created_at) VALUES ('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO productos(nombre, precio, created_at) VALUES ('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO productos(nombre, precio, created_at) VALUES ('Mica Comoda 5 Cajones', 299990, NOW());

INSERT INTO facturas(descripcion, observaciones, id_cliente, created_at) VALUES ('Factura equipos de oficina', NULL, 1, NOW());
INSERT INTO items_factura(cantidad, id_factura, id_producto) VALUES (1, 1, 1);
INSERT INTO items_factura(cantidad, id_factura, id_producto) VALUES (2, 1, 4);
INSERT INTO items_factura(cantidad, id_factura, id_producto) VALUES (1, 1, 5);
INSERT INTO items_factura(cantidad, id_factura, id_producto) VALUES (1, 1, 7);

INSERT INTO facturas(descripcion, observaciones, id_cliente, created_at) VALUES ('Factura Bicicleta', 'Alguna nota importante', 1, NOW());
INSERT INTO items_factura(cantidad, id_factura, id_producto) VALUES (3, 2, 6);

INSERT INTO users (username, password, enabled) VALUES ('samuel', '$2a$10$Uhy4Vj9aibV7LGRMFYo0qu85TBNxy3Vi.JxwJHMyef1DG2ndV5tpK', true);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$ik6bBhlTfljNrDiaZzoRGOl6U175onbKxZQBbDHFTWHvrJ2wBDkMC', true);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');
