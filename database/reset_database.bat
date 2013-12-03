mysql --user=root --password=test --execute="drop schema kad"
mysql --user=root --password=test < create_initial_database.sql
mysql --user=root --password=test < populate_initial_database.sql