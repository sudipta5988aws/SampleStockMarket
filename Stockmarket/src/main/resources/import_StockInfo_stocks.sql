
create table `stock_info`(

`id` varchar(32) not null default '',
`code` varchar(10) not null default '',
`stock_type` varchar(10) not null default 'COMMON',
`last_dividend` DOUBLE PRECISION not null default '0.0'
`fixed_dividend` INT default null,
`per_value` INT default '100',
`curr_price` DOUBLE PRECISION not null default '0.0',
`creation_date` timestamp not null default CURRENT_TIMESTAMP,
`modified_date` timestamp NULL default NULL on UPDATE CURRENT_TIMESTAMP,

PRIMARY KEY(`id`),
UNIQUE KEY `code` (`code`)
) DEFAULT CHARSET=utf8;



create table `stock_transaction`(

`id` varchar(32) not null default '',
`stock_info_id` varchar(32) not null default '',
`transaction_type` varchar(10) not null default '',
`unit_count` INT default '0',
`price_per_unit` DOUBLE PRECISION not null default '0.0'
`creation_date` timestamp not null default CURRENT_TIMESTAMP,
`modified_date` timestamp NULL default NULL on UPDATE CURRENT_TIMESTAMP,
CONSTRAINT `FKSTKID` FOREIGN KEY (`stock_id`) REFERENCES `stock_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
PRIMARY KEY(`id`),
) DEFAULT CHARSET=utf8;



create table `application_property`(
`key` varchar(64) not null default '',
`value` varchar(64) not null default '',
UNIQUE KEY `key` (`key`)
)DEFAULT CHARSET=utf8;


create table `polling`(
`id` varchar(32) not null default '',
`status` varchar(10) not null default '',
`result` varchar(64),
`metadata` varchar(32) not null default '',
`percentage` INT default '0',
`creation_date` timestamp not null default CURRENT_TIMESTAMP,
`modified_date` timestamp NULL default NULL on UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY(`id`),
) DEFAULT CHARSET=utf8;