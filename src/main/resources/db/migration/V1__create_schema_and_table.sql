DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'technology') THEN
        EXECUTE 'CREATE SCHEMA technology';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'technology' AND table_name = 'users') THEN
        EXECUTE 'CREATE TABLE technology.users (
            id SERIAL PRIMARY KEY,
            username VARCHAR(50) NOT NULL,
            password VARCHAR(100) NOT NULL,
            role VARCHAR(50),
            status BOOLEAN
        )';

        EXECUTE 'COMMENT ON TABLE technology.users IS ''Tabela de usuários do sistema''';
        EXECUTE 'COMMENT ON COLUMN technology.users.id IS ''ID único do usuário''';
        EXECUTE 'COMMENT ON COLUMN technology.users.username IS ''Nome de usuário para login''';
        EXECUTE 'COMMENT ON COLUMN technology.users.password IS ''Senha do usuário''';
        EXECUTE 'COMMENT ON COLUMN technology.users.role IS ''Papel do usuário no sistema (ex: admin, user)''';
        EXECUTE 'COMMENT ON COLUMN technology.users.status IS ''Status do usuário (ativo/inativo)''';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'technology' AND table_name = 'products') THEN

        EXECUTE 'CREATE TABLE technology.products (
            id SERIAL PRIMARY KEY,
            name VARCHAR(50) NOT NULL,
            description VARCHAR(100),
            price DECIMAL(10,2),
            quantity INT,
            status VARCHAR(50)
        )';

        EXECUTE 'UPDATE technology.products SET description = ''No description'' WHERE description IS NULL';

        EXECUTE 'ALTER TABLE technology.products ALTER COLUMN description SET NOT NULL';

        EXECUTE 'COMMENT ON TABLE technology.products IS ''Tabela de produtos do sistema''';
        EXECUTE 'COMMENT ON COLUMN technology.products.id IS ''ID único do produto''';
        EXECUTE 'COMMENT ON COLUMN technology.products.name IS ''Nome do produto''';
        EXECUTE 'COMMENT ON COLUMN technology.products.description IS ''Descrição do produto''';
        EXECUTE 'COMMENT ON COLUMN technology.products.price IS ''Preço do produto''';
        EXECUTE 'COMMENT ON COLUMN technology.products.quantity IS ''Quantidade em estoque''';
        EXECUTE 'COMMENT ON COLUMN technology.products.status IS ''Status do produto (disponível/esgotado)''';
    END IF;
END $$;