--ALTER TABLE SA.GIFT ALTER DESCRIPTION SET DATA TYPE CLOB;
--ALTER TABLE SA.USR ALTER WELCOMEMESSAGE SET DATA TYPE CLOB;

/*Data for the table USR */

insert  into USR(id,avatarUrl,birthDate,email,fullName,login,password,role,welcomeMessage) values (1,'/i/data/alex.jpg','1990-01-14','alex@alex.com','Алексей','alex','alex','VIP','Привет всем! :) Люблю получать подарки, порадите что-нибудь :)');
insert  into USR(id,avatarUrl,birthDate,email,fullName,login,password,role,welcomeMessage) values (2,'','1984-06-01','','Татьяна','tanya','tanya','VIP','Обожаю подарки! Очень бы хотела что-то из нижеперечисленного на свой день рождения! :)');
insert  into USR(id,avatarUrl,birthDate,email,fullName,login,password,role,welcomeMessage) values (3,NULL,'2009-09-18','',NULL,'sponsor','sponsor','PRESENTER',NULL);

/*Data for the table `GIFT` */

insert  into GIFT(id,dateAdd,dateTimeSelected,description,imgUrl,name,url,owner_id,presenter_id) values (1,'2009-09-18',NULL,'Хочу себе вот такой девайс. Можно даже БУ :)','/i/data/palm.gif','Palm','http://pleer.ru/_17098.html?3dnews',1,NULL);
insert  into GIFT(id,dateAdd,dateTimeSelected,description,imgUrl,name,url,owner_id,presenter_id) values (2,'2009-09-18',NULL,'<p><u>Барабан - это моя мечта</u>! :)</p><p><font face=\"book antiqua,palatino\"><span class=\"cart\" align=\"left\"><strong>Рамвонг</strong> - национальный тайский танцевальный барабан. Его используют внекоторых регионах Таиланда во время празднеств. Рамвонг (Ramwong)произошел от Ramthone (популярное сезонное развлечение) - Ram-танец иThone-маленький барабан. Ramthone можно перевести как &quot;танец подбарабан&quot;. Рамвонги от MEINL производят подлинный звук, который выможете услышать во время тех церемоний.</span></font> </p>','/i/data/baraban.jpg','Барабан','http://dynatone.ru/info999002608',1,NULL);
insert  into GIFT(id,dateAdd,dateTimeSelected,description,imgUrl,name,url,owner_id,presenter_id) values (3,'2009-09-18','2009-09-18 17:14:24','<font face=\"book antiqua,palatino\" size=\"3\">Нужна лампочка - без нее ничего не видно :)</font>','/i/data/lamp.jpg','Лампочка','http://shop220.ru/product1766.htm',1,3);
insert  into GIFT(id,dateAdd,dateTimeSelected,description,imgUrl,name,url,owner_id,presenter_id) values (4,'2009-09-18',NULL,'Очень бы хотела на свое день рождение домашнего питомца - собачку такую небольшу. Только, <strong>чтобы, когда она выростет, так и осталась небольшой</strong> :)','/i/data/dog.gif','Домашний пес','',2,NULL);
insert  into GIFT(id,dateAdd,dateTimeSelected,description,imgUrl,name,url,owner_id,presenter_id) values (5,'2009-09-18',NULL,'<p>Люблю фотографировать, а потом в темноте заправить плёнку в кассету, установить вкоробку для проявки и залить проявитель на несколько минут, послепромыть дистиллированной водой и готово - повесить сушиться мое творение! </p>','/i/data/camera.gif','Фотоаппарат','',2,NULL);


