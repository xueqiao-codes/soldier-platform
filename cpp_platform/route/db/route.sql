CREATE TABLE troute_info(
	Fservice_key SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	Fservice_name VARCHAR(64) NOT NULL DEFAULT "",
	Fip_list VARCHAR(320) NOT NULL DEFAULT "",
	Froute_type SMALLINT UNSIGNED NOT NULL DEFAULT 1,
	Fservice_desc VARCHAR(128) NOT NULL DEFAULT "",
	Fservice_admin VARCHAR(256) NOT NULL DEFAULT "",
	Fidl_relative_path VARCHAR(256) NOT NULL DEFAULT "",
	Frelated_screen_id VARCHAR(128) NOT NULL DEFAULT "",
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fservice_key)
);

CREATE TABLE troute_version (
	Fid SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	Fversion INT UNSIGNED NOT NULL DEFAULT 1,
	Fconfig MEDIUMTEXT NOT NULL,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fid)
);

INSERT INTO troute_version SET Fid=1,Fversion=1,Fconfig="",Flastmodify_timestamp=UNIX_TIMESTAMP(NOW());

INSERT INTO troute_info set Fservice_key=20,Fservice_name='route_dao',Fip_list='127.0.0.1',
Fservice_desc='平台组件路由管理dao',Fcreate_timestamp=UNIX_TIMESTAMP(NOW()), Flastmodify_timestamp=UNIX_TIMESTAMP(NOW());

