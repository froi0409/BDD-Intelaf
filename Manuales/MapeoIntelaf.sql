CREATE SCHEMA IF NOT EXISTS INTELAF;
USE INTELAF;

CREATE TABLE IF NOT EXISTS TIENDA(
  codigo_tienda VARCHAR(20) NOT NULL,
  nombre VARCHAR(60) NOT NULL,
  direccion VARCHAR(60) NOT NULL,
  telefono1 VARCHAR(10) NOT NULL,
  telefono2 VARCHAR(10),
  correo_electronico VARCHAR(50),
  horario VARCHAR(20),
  PRIMARY KEY(codigo_tienda)
);

CREATE TABLE IF NOT EXISTS EMPLEADO(
  codigo_empleado VARCHAR(20) NOT NULL,
  nombre VARCHAR(60) NOT NULL,
  telefono VARCHAR(8) NOT NULL,
  DPI VARCHAR(15) NOT NULL,
  NIT VARCHAR(26),
  correo_electronico VARCHAR(45),
  direccion VARCHAR(45),
  PRIMARY KEY(codigo_empleado)
);

CREATE TABLE IF NOT EXISTS PRODUCTO(
  codigo_producto VARCHAR(20) NOT NULL,
  nombre VARCHAR(60) NOT NULL,
  fabricante VARCHAR(45) NOT NULL,
  precio DECIMAL(9,2) NOT NULL,
  descripcion VARCHAR(300),
  garantia INT,
  PRIMARY KEY(codigo_producto)
);

CREATE TABLE IF NOT EXISTS EXISTENCIAS(
  codigo_existencia VARCHAR(40) NOT NULL,
  cantidad INT NOT NULL,
  codigo_producto VARCHAR(10) NOT NULL,
  codigo_tienda VARCHAR(10) NOT NULL,
  PRIMARY KEY(codigo_existencia),
  FOREIGN KEY(codigo_producto) REFERENCES PRODUCTO(codigo_producto),
  FOREIGN KEY(codigo_tienda) REFERENCES TIENDA(codigo_tienda)
);

CREATE TABLE IF NOT EXISTS CLIENTE(
  NIT VARCHAR(15) NOT NULL,
  DPI VARCHAR(15),
  nombre VARCHAR(60) NOT NULL,
  telefono VARCHAR(8) NOT NULL,
  credito DECIMAL(9,2),
  correo_electronico VARCHAR(60),
  direccion VARCHAR(600),
  PRIMARY KEY(NIT)
);

CREATE TABLE IF NOT EXISTS TIEMPO_ENVIO(
  tiempo INT NOT NULL,
  tienda1 VARCHAR(20) NOT NULL,
  tienda2 VARCHAR(20) NOT NULL,
  PRIMARY KEY(tiempo, tienda1, tienda2)
);

CREATE TABLE IF NOT EXISTS PEDIDO(
  codigo_pedido VARCHAR(10) NOT NULL,
  anticipo DECIMAL(9,2) NOT NULL,
  precio_final DECIMAL(9,2) NOT NULL,
  fecha DATE NOT NULL,
  bonificacion INT,
  tienda_origen VARCHAR(10),
  tienda_destino VARCHAR(10) NOT NULL,
  NIT_cliente VARCHAR(15),
  tiempo_envio INT NOT NULL,
  estado VARCHAR(45) DEFAULT 'SIN_ENTREGAR',
  PRIMARY KEY(codigo_pedido),
  FOREIGN KEY(tienda_destino) REFERENCES TIENDA(codigo_tienda),
  FOREIGN KEY(NIT_cliente) REFERENCES CLIENTE(NIT),
  FOREIGN KEY(tiempo_envio) REFERENCES TIEMPO_ENVIO(tiempo)
);

CREATE TABLE IF NOT EXISTS DESCRIPCION_PEDIDO(
  codigo_descripcion INT AUTO_INCREMENT,
  total DECIMAL(9,2) NOT NULL,
  cantidad INT NOT NULL,
  codigo_producto VARCHAR(10) NOT NULL,
  codigo_pedido VARCHAR(10) NOT NULL,
  PRIMARY KEY(codigo_descripcion),
  FOREIGN KEY(codigo_producto) REFERENCES PRODUCTO(codigo_producto),
  FOREIGN KEY(codigo_pedido) REFERENCES PEDIDO(codigo_pedido)
);

CREATE TABLE IF NOT EXISTS COMPRA(
  codigo_compra INT AUTO_INCREMENT,
  fecha DATE NOT NULL,
  nombre_comprador VARCHAR(60),
  codigo_tienda VARCHAR(10) NOT NULL,
  codigo_pedido VARCHAR(10) NOT NULL,
  NIT_cliente VARCHAR(15),
  PRIMARY KEY(codigo_compra),
  FOREIGN KEY(codigo_tienda) REFERENCES TIENDA(codigo_tienda),
  FOREIGN KEY(codigo_pedido) REFERENCES PEDIDO(codigo_pedido),
  FOREIGN KEY(NIT_cliente) REFERENCES CLIENTE(NIT)
);
