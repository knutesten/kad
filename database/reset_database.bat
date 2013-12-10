mysql --user=root --password=test --execute="drop schema kad"
cd create_tables
mysql --user=root --password=test < "create_initial_database.sql"
cd ..\populate_tables
mysql --user=root --password=test < "populate_initial_database.sql"
cd ..
