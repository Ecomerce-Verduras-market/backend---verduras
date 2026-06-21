#!/usr/bin/env bash
set -euo pipefail

PGHOST="${PGHOST:-localhost}"
PGPORT="${PGPORT:-5439}"
PGADMIN_USER="${PGADMIN_USER:-postgres}"
PGDATABASE="${PGDATABASE:-postgres}"

APP_DATABASE="${APP_DATABASE:-verduritas_db}"
APP_USER="${APP_USER:-root}"
APP_PASSWORD="${APP_PASSWORD:-rroot}"

SCHEMAS=(
  auth_service
  user_service
  product_service
  inventory_service
  order_service
  payment_service
)

psql_admin() {
  psql -h "$PGHOST" -p "$PGPORT" -U "$PGADMIN_USER" -d "$PGDATABASE" "$@"
}

psql_app() {
  psql -h "$PGHOST" -p "$PGPORT" -U "$PGADMIN_USER" -d "$APP_DATABASE" "$@"
}

sql_literal() {
  local value="${1//\'/\'\'}"
  printf "'%s'" "$value"
}

role_exists() {
  local role="$1"
  psql_admin -tAc "SELECT 1 FROM pg_roles WHERE rolname = $(sql_literal "$role")" | grep -q 1
}

database_exists() {
  local db="$1"
  psql_admin -tAc "SELECT 1 FROM pg_database WHERE datname = $(sql_literal "$db")" | grep -q 1
}

if role_exists "$APP_USER"; then
  echo "role exists: $APP_USER"
else
  echo "creating role: $APP_USER"
  psql_admin -v ON_ERROR_STOP=1 -c "CREATE USER \"$APP_USER\" WITH PASSWORD $(sql_literal "$APP_PASSWORD");"
fi

if database_exists "$APP_DATABASE"; then
  echo "database exists: $APP_DATABASE"
else
  echo "creating database: $APP_DATABASE"
  psql_admin -v ON_ERROR_STOP=1 -c "CREATE DATABASE \"$APP_DATABASE\" OWNER \"$APP_USER\";"
fi

psql_admin -v ON_ERROR_STOP=1 -c "GRANT ALL PRIVILEGES ON DATABASE \"$APP_DATABASE\" TO \"$APP_USER\";"

for schema in "${SCHEMAS[@]}"; do
  echo "ensuring schema: $schema"
  psql_app -v ON_ERROR_STOP=1 -c "CREATE SCHEMA IF NOT EXISTS \"$schema\" AUTHORIZATION \"$APP_USER\";"
  psql_app -v ON_ERROR_STOP=1 -c "GRANT ALL PRIVILEGES ON SCHEMA \"$schema\" TO \"$APP_USER\";"
done
