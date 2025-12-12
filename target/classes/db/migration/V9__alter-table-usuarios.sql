ALTER TABLE usuarios ADD COLUMN secret varchar(64),
ADD COLUMN a2fAtiva boolean NOT NULL DEFAULT 0;