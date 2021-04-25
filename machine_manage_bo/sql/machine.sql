CREATE TABLE tmachine (
	Fhost_name VARCHAR(64) NOT NULL DEFAULT "",
	Fhost_inner_ip VARCHAR(128) NOT NULL DEFAULT "" UNIQUE KEY,
	Fhost_desc  VARCHAR(256) NOT NULL DEFAULT "",
	Fhost_admin VARCHAR(128) NOT NULL DEFAULT "",
	Froot_password VARCHAR(128) NOT NULL DEFAULT "",
	Fmachine_properties TEXT NOT NULL DEFAULT "",
	Frelated_screen_id  VARCHAR(128) NOT NULL DEFAULT "",
	Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY(Fhost_name)
) CHARACTER SET UTF8;