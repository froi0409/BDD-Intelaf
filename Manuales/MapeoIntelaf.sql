CREATE SCHEMA IF NOT EXISTS INTELAF;
USE INTELAF;

CREATE TABLE IF NOT EXISTS TIENDA(
  codigo_tienda VARCHAR(10) NOT NULL,
  nombre VARCHAR(60) NOT NULL,
  direccion VARCHAR(60) NOT NULL,
  telefono1 VARCHAR(8) NOT NULL,
  telefono2 VARCHAR(8),
  correo_electronico VARCHAR(8),
  horario VARCHAR(20),
  PRIMARY KEY(codigo_tienda)
);

CREATE TABLE IF NOT EXISTS EMPLEADO(
  codigo_empleado VARCHAR(10) NOT NULL,
  nombre VARCHAR(60) NOT NULL,
  telefono VARCHAR(8) NOT NULL,
  DPI VARCHAR(15) NOT NULL,
  NIT VARCHAR(26),
  correo_electronico VARCHAR(45),
  direccion VARCHAR(45),
  codigo_tienda VARCHAR(10),
  PRIMARY KEY(codigo_empleado),
  FOREIGN KEY(codigo_tienda) REFERENCES TIENDA(codigo_tienda)
);

CREATE TABLE IF NOT EXISTS PRODUCTO(
  codigo_producto VARCHAR(10) NOT NULL,
  nombre VARCHAR(60) NOT NULL,
  fabricante VARCHAR(45) NOT NULL,
  precio DECIMAL(6a,2) NOT NULL,
  descripcion VARCHAR(300),
  garantia INT,
  PRIMARY KEY(codigo_producto)
);

CREATE TABLE IF NOT EXISTS EXISTENCIAS(
  codigo_existencia VARCHAR(30) NOT NULL,
  cantidad INT NOT NULL,
  codigo_producto VARCHAR(10) NOT NULL,
  codigo_tienda VARCHAR(10) NOT NULL,
  PRIMARY KEY(codigo_existencia),
  FOREIGN KEY(codigo_producto) REFERENCES PRODUCTO(codigo_producto),
  FOREIGN KEY(codigo_tienda) REFERENCES TIENDA(codigo_tienda)
);

CREATE TABLE IF NOT EXISTS CLIENTE(
  NIT VARCHAR(15) NOT NULL,
  DPI VARCHAR(15) NOT NULL,
  nombre VARCHAR(60) NOT NULL,
  telefono VARCHAR(8) NOT NULL,
  credito DECIMAL(6,2),
  correo_electronico VARCHAR(60),
  direccion VARCHAR(600),
  PRIMARY KEY(NIT)
);

CREATE TABLE IF NOT EXISTS TIEMPO_ENVIO(
  tiempo INT NOT NULL,
  tienda1 VARCHAR(10) NOT NULL,
  tienda2 VARCHAR(10) NOT NULL,
  PRIMARY KEY(tiempo, tienda1, tienda2)
);

CREATE TABLE IF NOT EXISTS PEDIDO(
  codigo_pedido VARCHAR(10) NOT NULL,
  numero_descripciones INT NOT NULL,
  anticipo DECIMAL(6,2) NOT NULL,
  precio_final DECIMAL(6,2) NOT NULL,
  fecha VARCHAR(8) NOT NULL,
  bonificacion INT,
  tienda_origen VARCHAR(10) NOT NULL,
  tienda_destino VARCHAR(10) NOT NULL,
  NIT_cliente VARCHAR(15),
  tiempo_envio INT NOT NULL,
  PRIMARY KEY(codigo_pedido),
  FOREIGN KEY(tienda_destino) REFERENCES TIENDA(codigo_tienda),
  FOREIGN KEY(NIT_cliente) REFERENCES CLIENTE(NIT),
  FOREIGN KEY(tiempo_envio) REFERENCES TIEMPO_ENVIO(tiempo)
);

CREATE TABLE IF NOT EXISTS DESCRIPCION_PEDIDO(
  codigo_descripcion VARCHAR(12) NOT NULL,
  total DECIMAL(6,2) NOT NULL,
  cantidad INT NOT NULL,
  codigo_producto VARCHAR(10) NOT NULL,
  codigo_pedido VARCHAR(10) NOT NULL,
  PRIMARY KEY(codigo_descripcion),
  FOREIGN KEY(codigo_producto) REFERENCES PRODUCTO(codigo_producto),
  FOREIGN KEY(codigo_pedido) REFERENCES PEDIDO(codigo_pedido)
);

CREATE TABLE IF NOT EXISTS COMPRA(
  codigo_compra VARCHAR(10) NOT NULL,
  fecha VARCHAR(8) NOT NULL,
  nombre_comprador VARCHAR(60),
  codigo_tienda VARCHAR(10) NOT NULL,
  codigo_pedido VARCHAR(10) NOT NULL,
  NIT_cliente VARCHAR(15),
  PRIMARY KEY(codigo_compra),
  FOREIGN KEY(codigo_tienda) REFERENCES TIENDA(codigo_tienda),
  FOREIGN KEY(codigo_pedido) REFERENCES PEDIDO(codigo_pedido),
  FOREIGN KEY(NIT_cliente) REFERENCES CLIENTE(NIT)
);
