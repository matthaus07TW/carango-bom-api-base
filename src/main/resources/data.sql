INSERT INTO public.account(username, password) VALUES
('leo', '$2a$10$sFKmbxbG4ryhwPNx/l3pgOJSt.fW1z6YcUnuE2X8APA/Z3NI/oSpq'),
('matthaus', '$2a$10$By4IojTGJfsDSgQFnVZq7.ip50nAFImAau1/Ws9/BVe1eFiIEoKuK'),
('renan', '$2a$10$hjF2fHjyMkX0dyBgnJAiUuhyUaf7T/XNKQ/VxkrSt5aSiTVa4bk.2'),
('yanka', '$2a$10$hjF2fHjyMkX0dyBgnJAiUuhyUaf7T/XNKQ/VxkrSt5aSiTVa4bk.2');


INSERT INTO public.brand(name) VALUES('VW'),('Audi'), ('FIAT');


INSERT INTO public.vehicle(model, value, year, brand_id) VALUES
('Fusca', 20000.00 , 1985, 1), 
('Gol', 60000.00 , 2019, 1),
('Parati', 30000.00 , 2015, 1), 
('A4', 100000.00 , 2017, 2),
('A5', 140000.00 , 2016, 2), 
('A3', 120000.00 , 2019, 2),
('Strada', 40000.00 , 1985, 3), 
('500', 200000.00 , 2019, 3),
('Palio', 20000.00 , 2017, 3);
