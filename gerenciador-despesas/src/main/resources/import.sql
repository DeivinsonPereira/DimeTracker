INSERT INTO tb_categoria (nome) VALUES ('Restaurante');
INSERT INTO tb_categoria (nome) VALUES ('Faculdade');
INSERT INTO tb_categoria (nome) VALUES ('Carro');
INSERT INTO tb_categoria (nome) VALUES ('Aluguel');

INSERT INTO tb_usuario (nome, email) VALUES ('Bruno Oliveira', 'bruno@gmail.com');

INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (70.0, TIMESTAMP WITH TIME ZONE '2023-07-12T22:00:00Z',1,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (40.75, TIMESTAMP WITH TIME ZONE '2023-08-10T15:00:00Z',1,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (150.12, TIMESTAMP WITH TIME ZONE '2023-08-17T22:30:00Z',1,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (178.49, TIMESTAMP WITH TIME ZONE '2023-08-27T22:00:00Z',1,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (850.0, TIMESTAMP WITH TIME ZONE '2023-06-15T11:00:00Z',2,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (850.0, TIMESTAMP WITH TIME ZONE '2023-07-15T11:00:00Z',2,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (850.0, TIMESTAMP WITH TIME ZONE '2023-08-15T11:00:00Z',2,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (700.0, TIMESTAMP WITH TIME ZONE '2023-06-10T12:00:00Z',3,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (700.0, TIMESTAMP WITH TIME ZONE '2023-07-10T12:00:00Z',3,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (700.0, TIMESTAMP WITH TIME ZONE '2023-08-10T12:00:00Z',3,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (1200.0, TIMESTAMP WITH TIME ZONE '2023-06-10T17:00:00Z',4,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (1200.0, TIMESTAMP WITH TIME ZONE '2023-07-10T17:00:00Z',4,1);
INSERT INTO tb_despesa (valor, data, categoria_id, usuario_id) VALUES (1200.0, TIMESTAMP WITH TIME ZONE '2023-08-10T17:00:00Z',4,1);
