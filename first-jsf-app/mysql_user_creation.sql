
CREATE USER 'my_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON * . * TO 'my_user'@'localhost';
FLUSH PRIVILEGES;