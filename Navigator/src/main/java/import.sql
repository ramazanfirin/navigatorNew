INSERT INTO Authority VALUES (1, 'ROLE_ADMIN');
INSERT INTO Authority VALUES (2, 'ROLE_USER');
INSERT INTO Users VALUES (1, 'admin','admin',1,'admin','admin','mail','phone',1,1,1);
INSERT INTO Users VALUES (2, 'user','user',1,'user','user','mail','phone',2,1,1);


INSERT INTO Branch VALUES (1, 'talas',1,1,1);


INSERT INTO OrderStatus VALUES (1, 'Yeni',1);
INSERT INTO OrderStatus VALUES (2, 'Basladi',1);
INSERT INTO OrderStatus VALUES (3, 'Operator iptal etti',1);
INSERT INTO OrderStatus VALUES (4, 'Kullanici iptal etti',1);
INSERT INTO OrderStatus VALUES (5, 'Tamamlandi',1);

INSERT INTO OrderPriority VALUES (1, 'Yuksek',1);
INSERT INTO OrderPriority VALUES (2, 'Orta',1);
INSERT INTO OrderPriority VALUES (3, 'Dusuk',1);



INSERT INTO Poi VALUES (1,'Meydan','38.7340','35.4676',1);
INSERT INTO Poi VALUES (2,'Duvenonu','38.7350','35.4686',1);
INSERT INTO Poi VALUES (3,'Serceonu','38.7360','35.4696',1);

INSERT INTO Device VALUES (1, '552555555','080027cc79b0','APA91bFMlKcoiJWV2M_AoGeT5lRr6gt_yyAJZ69xGOsRjbkFDoT0kgVfjuKgbOboKnJoOTmGF71bDIXRjZ_DaRYHFsZudXNlbTDknquQ-yz8A5qlHZRWjRfH8c-ky2l4Ojv0tBhxzm3oD6ajCh0ugSIuu1GOpx-N0w',1);
INSERT INTO Vehicle VALUES (1, '340001',1,1,1);


INSERT INTO Orders VALUES (1,'38.762015920509376','35.76968807349913','2014-11-12','test','Kocasinan','Istasyon Mh.','Kaldirim Caddesi','',1,1,1,1,1);

INSERT INTO TrackItem VALUES (1,'38.7320','35.4696','2014-11-12',1,1);
INSERT INTO TrackItem VALUES (2,'38.7330','35.4686','2014-11-12',1,1);
INSERT INTO TrackItem VALUES (3,'38.7340','35.4676','2014-11-12',1,1);