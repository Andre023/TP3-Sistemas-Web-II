-- Cria o usuário (role) para o banco de dados de investimentos, se ele não existir
DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'invest_user') THEN

      CREATE ROLE "invest_user" WITH LOGIN PASSWORD 'invest_password';
   END IF;
END
$do$;

-- Cria o banco de dados, se não existir (executado externamente pelo Docker)
-- e define o usuário criado como proprietário
ALTER DATABASE "investments_db" OWNER TO "invest_user";
