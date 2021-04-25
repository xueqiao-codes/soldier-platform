CREATE TABLE toa_user_session (
	Fuser_id INT UNSIGNED NOT NULL DEFAULT 0,
	Fsession_key VARCHAR(128) NOT NULL DEFAULT "",
	Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY (Fuser_id)
) ENGINE=MEMORY DEFAULT CHARSET=utf8;

ALTER TABLE ADD COLUMN Fuser_name VARCHAR(64) NOT NULL DEFAULT '';
ALTER TABLE toa_user_session DROP PRIMARY KEY;
ALTER TABLE toa_user_session ADD PRIMARY KEY(Fuser_id, Fuser_name);