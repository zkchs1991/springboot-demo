DROP TABLE IF EXISTS `roles_permissions`;



CREATE TABLE `roles_permissions` (

  `permission` varchar(100) DEFAULT NULL COMMENT '权限',

  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名',

  `id` int(11) NOT NULL COMMENT '主键',

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';



/*Data for the table `roles_permissions` */



insert  into `roles_permissions`(`permission`,`role_name`,`id`) values ('test:users:delete','admin',1),('test:users:update','admin',2);



/*Table structure for table `user_roles` */



DROP TABLE IF EXISTS `user_roles`;



CREATE TABLE `user_roles` (

  `id` int(11) NOT NULL COMMENT '主键',

  `username` varchar(100) NOT NULL COMMENT '用户名',

  `role_name` varchar(100) NOT NULL COMMENT '角色名',

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Data for the table `user_roles` */



insert  into `user_roles`(`id`,`username`,`role_name`) values (0,'Mark','users'),(1,'Mark','admin');



/*Table structure for table `users` */



DROP TABLE IF EXISTS `users`;



CREATE TABLE `users` (

  `username` varchar(100) DEFAULT NULL,

  `password` varchar(100) DEFAULT NULL,

  `id` varchar(100) NOT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='shiro的表';



/*Data for the table `users` */



insert  into `users`(`username`,`password`,`id`) values ('Mark','123456','1');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;