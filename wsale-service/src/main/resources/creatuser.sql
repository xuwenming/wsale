CREATE USER 'wsale'@'127.0.0.1' IDENTIFIED BY '123456';
flush privileges;
 create database wsale;
 alter database wsale character set utf8;
 grant all privileges on wsale.* to wsale@localhost identified by '123456';
 flush privileges;
 
 -- GRANT ALL ON wsale.* TO 'wsale'@'%' IDENTIFIED BY '123456' WITH GRANT OPTION;
 -- flush privileges;


 -- 1、修改库字符集
 -- 打开C:\ProgramData\MySQL\MySQL Server 5.6\my.ini,修改属性default-character-set=utf8mb4、character-set-server=utf8mb4，修改完重启mysql服务
 -- 查看是否修改成功执行 show variables like '%char%';

 -- 2、修改表字符集
 -- 执行 ALTER TABLE tuser CHARSET=utf8mb4;

 -- 3、修改表字段字符集
 -- 执行 ALTER TABLE tuser MODIFY `nickname` VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;