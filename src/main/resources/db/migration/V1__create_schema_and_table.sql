DO $$
BEGIN
    -- Criar o esquema "technology" se não existir
    IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'technology') THEN
        EXECUTE 'CREATE SCHEMA technology';
    END IF;

    -- Criar a tabela "users" se não existir
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'technology' AND table_name = 'users') THEN
        EXECUTE 'CREATE TABLE technology.users (
            id SERIAL PRIMARY KEY,
            username VARCHAR(50) NOT NULL,
            password VARCHAR(100) NOT NULL,
            role VARCHAR(50),
            status BOOLEAN
        )';
    END IF;

    -- Criar a tabela "products" se não existir
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'technology' AND table_name = 'products') THEN
        EXECUTE 'CREATE TABLE technology.products (
            id SERIAL PRIMARY KEY,
            name VARCHAR(50) NOT NULL,
            description VARCHAR(100) NOT NULL,
            price DECIMAL(10,2),
            quantity INT,
            status VARCHAR(50)
        )';
    END IF;
END $$;
