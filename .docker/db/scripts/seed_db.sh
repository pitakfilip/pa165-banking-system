#!/bin/bash

DB_NAME="banking"
USERNAME="root"
PASSWORD="passwd"
# Uncomment for local use
HOST="localhost"

#Set DB password as env variable
export PGPASSWORD="$PASSWORD"


# Wait for PostgreSQL to be ready
until psql -h "$HOST" -U "$USERNAME" -d "$DB_NAME" -c '\q'; do
  >&2 echo "Postgres is unavailable - sleeping"
  sleep 1
done

# Seed tables in the 'bank_user' schema
psql -h "$HOST" -U "$USERNAME" -d "$DB_NAME" -f "/scripts/init/2_generate_account_management_db.sql"

# Seed tables in the 'bank_account' schema
psql -h "$HOST" -U "$USERNAME" -d "$DB_NAME" -f "/scripts/init/2_generate_account_query_db.sql"

# Seed tables in the 'bank_transaction' schema
psql -h "$HOST" -U "$USERNAME" -d "$DB_NAME" -f "/scripts/init/2_generate_transaction_db.sql"
