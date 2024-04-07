docker run --name mysql -e MYSQL_ROOT_PASSWORD=1234 -d -p 3306:3306 mysql:8.0
docker run --name redis --rm -it -d -p 6379:6379 redis:6.2