CREATE TABLE tweb_config_info (
	Fweb_project_name VARCHAR(64) NOT NULL DEFAULT "",
	Fdeploy_type SMALLINT UNSIGNED NOT NULL DEFAULT 1,
	Fip_list VARCHAR(320) NOT NULL DEFAULT "",
	Fport INT UNSIGNED NOT NULL DEFAULT 8080,
	Fdomain_list VARCHAR(256) NOT NULL DEFAULT "",
	Findex_path VARCHAR(256) NOT NULL DEFAULT "/",
	Fdesc VARCHAR(128) NOT NULL DEFAULT "",
	Fserver_options VARCHAR(1024) NOT NULL DEFAULT "",
	Flocation_options VARCHAR(1024) NOT NULL DEFAULT "", 
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fweb_project_name)
) DEFAULT CHARSET utf8;

CREATE TABLE tweb_nginx_config (
	Fid SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	Fversion INT UNSIGNED NOT NULL DEFAULT 0,
	Fconfig MEDIUMTEXT NOT NULL ,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fid)
) DEFAULT CHARSET utf8;

INSERT INTO tweb_nginx_config SET Fid=1,Fversion=0,Fconfig="",Flastmodify_timestamp=UNIX_TIMESTAMP(NOW());
INSERT INTO tweb_nginx_config SET Fid=2,Fversion=0,Fconfig="",Flastmodify_timestamp=UNIX_TIMESTAMP(NOW());

ALTER TABLE tweb_config_info ADD COLUMN Fhttps_cert_name VARCHAR(64) NOT NULL DEFAULT '';
ALTER TABLE tweb_config_info ADD COLUMN Fdisable_http TINYINT NOT NULL DEFAULT 0;