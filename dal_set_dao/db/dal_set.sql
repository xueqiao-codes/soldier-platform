CREATE TABLE tdal_set_host (
	Fhost_name VARCHAR(128) NOT NULL DEFAULT "",
	Fhost_domain VARCHAR(128) NOT NULL DEFAULT "",
	Fhost_port SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	Fhost_desc VARCHAR(512) NOT NULL DEFAULT "",
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fhost_name)
) CHARACTER SET UTF8;

CREATE TABLE tdal_set_user (
	Fuser_key VARCHAR(64) NOT NULL DEFAULT "",
	Fuser_name VARCHAR(128) NOT NULL DEFAULT "",
	Fuser_password VARCHAR(128) NOT NULL DEFAULT "",
	Fuser_desc VARCHAR(512) NOT NULL DEFAULT "",
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fuser_key)
) CHARACTER SET UTF8;

CREATE TABLE tdal_set_table (
	Ftable_prefix_name VARCHAR(128) NOT NULL DEFAULT "",
	Ftable_slice_num SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	Ftable_fill_zero TINYINT UNSIGNED NOT NULL DEFAULT 0,
	Ftable_desc VARCHAR(512) NOT NULL DEFAULT 0,
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Ftable_prefix_name)
) CHARACTER SET UTF8;

CREATE TABLE tdal_set_role (
	Frole_name VARCHAR(64) NOT NULL DEFAULT "",
	Fdb_name VARCHAR(64) NOT NULL DEFAULT "",
	Fdb_type SMALLINT NOT NULL DEFAULT 1,
	Fdesc VARCHAR(256) NOT NULL DEFAULT "",
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Frole_name)
) CHARACTER SET UTF8;

CREATE TABLE tdal_set_role_table_relation (
	Frole_name VARCHAR(64) NOT NULL DEFAULT "",
	Ftable_prefix_name VARCHAR(128) NOT NULL DEFAULT "",
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	INDEX table_prefix_name_index(Ftable_prefix_name),
	PRIMARY KEY(Frole_name, Ftable_prefix_name)
) CHARACTER SET UTF8;

CREATE TABLE tdal_set_role_set_relation (
	Fhost_name VARCHAR(128) NOT NULL DEFAULT "",
	Ftype_in_set SMALLINT UNSIGNED NOT NULL DEFAULT 1,
	Fset_index SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	Fweight INT UNSIGNED DEFAULT 1,
	Frole_name VARCHAR(64) NOT NULL DEFAULT "",
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fset_index, Fhost_name, Frole_name),
	INDEX role_name_index(Frole_name)
) CHARACTER SET UTF8;

CREATE TABLE tdal_set_role_service_relation (
	Fservice_key SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	Finterface_name VARCHAR(128) NOT NULL DEFAULT "",
	Frole_name VARCHAR(64) NOT NULL DEFAULT "",
	Fuser_key VARCHAR(64) NOT NULL DEFAULT "",
	Frelated_type SMALLINT UNSIGNED NOT NULL DEFAULT 1,
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	INDEX role_name_index(Frole_name),
	INDEX interface_name_index(Finterface_name),
	PRIMARY KEY(Fservice_key, Finterface_name, Frole_name)
)CHARACTER SET UTF8;

CREATE TABLE tdal_set_description (
	Fid SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	Fversion INT UNSIGNED NOT NULL DEFAULT 0,
	Fxml MEDIUMTEXT,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fid)
)CHARACTER SET UTF8;

INSERT INTO tdal_set_description SET Fid=1,Fversion=0,Fxml="",Flastmodify_timestamp=UNIX_TIMESTAMP(NOW());
