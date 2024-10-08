CREATE TABLE tbg_pet (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    breed VARCHAR(255),
    age INT,
    additional_info VARCHAR(255)
);
