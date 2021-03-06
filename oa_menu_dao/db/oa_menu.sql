CREATE TABLE toa_system_menu (
	Fsystem_menu_id INT UNSIGNED NOT NULL AUTO_INCREMENT ,
	Fsystem_menu_name VARCHAR(16) NOT NULL DEFAULT "" UNIQUE,
	Forder_weight INT UNSIGNED NOT NULL DEFAULT 1,
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY (Fsystem_menu_id)
) AUTO_INCREMENT = 1000 DEFAULT CHARSET utf8;

CREATE TABLE toa_sub_menu (
	Fmenu_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	Fmenu_name VARCHAR(16) NOT NULL DEFAULT "",
	Fmenu_src VARCHAR(512) NOT NULL DEFAULT "",
	Fsystem_menu_id INT UNSIGNED NOT NULL DEFAULT 0,
	Forder_weight INT UNSIGNED NOT NULL DEFAULT 1,
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fmenu_id)
) AUTO_INCREMENT = 1000 DEFAULT CHARSET utf8;