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

# Empty tables in the 'bank_user' schema
psql -h "$HOST" -U "$USERNAME" -d "$DB_NAME" -c "TRUNCATE TABLE bank_user.bank_user, bank_user.bank_account, bank_user.scheduled_payment;"

# Empty tables in the 'bank_account' schema
psql -h "$HOST" -U "$USERNAME" -d "$DB_NAME" -c "TRUNCATE TABLE bank_account.balance, bank_account.bal_transaction;"

# Empty tables in the 'bank_transaction' schema
psql -h "$HOST" -U "$USERNAME" -d "$DB_NAME" -c "TRUNCATE TABLE bank_transaction.proc_reg, bank_transaction.proc_transaction;"
