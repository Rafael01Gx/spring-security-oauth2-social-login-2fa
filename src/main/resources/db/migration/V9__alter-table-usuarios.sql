ALTER TABLE usuarios ADD COLUMN secret varchar(64),
ADD COLUMN a2f_ativa boolean NOT NULL DEFAULT 0;