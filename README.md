# 本地开发模式
## 启动运行
1. 启动postgresql 
```bash
podman run --name postgres -e POSTGRES_USER=postgres \
           -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=my_data \
           -p 5432:5432 -d postgres:10.5
```
或者
```bash
docker run --name postgres -e POSTGRES_USER=postgres \
           -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=my_data \
           -p 5432:5432 -d postgres:10.5
```
相应的连接配置信息:
```properties
DB_USERNAME=postgres
DB_PASSWORD=postgres
MY_DATABASE_SERVICE_HOST=localhost
MY_DATABASE_SERVICE_PORT=5432

spring.datasource.url=jdbc:postgresql://${MY_DATABASE_SERVICE_HOST}:${MY_DATABASE_SERVICE_PORT}/my_data
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

1.1 启动mysql
如有必要, 登陆registry.redhat.io
podman pull registry.redhat.io/rhscl/mysql-57-rhel7:latest


```bash
podman run --name mysql -e MYSQL_USER=mysql -e MYSQL_PASSWORD=mysql -e MYSQL_DATABASE=pms -e MYSQL_ROOT_PASSWORD=p@ssw0rd -p 3306:3306 mysql-80-rhel7:latest
```

> ps: vscode连接mysql 8.0 由于mysql的安全验证方式的变化 会出现如下连接错误
*ER_NOT_SUPPORTED_AUTH_MODE*

所以需要:

1. 用root账号登陆 mysql, 
2. 然后更新mysql.user 表里面的用户名密码信息。
```bash
ALTER USER 'mysql'@'%' IDENTIFIED WITH mysql_native_password BY 'mysql';
```

相应的配置为:
```properties

```
### 程序启动命令
```bash
 mvn clean spring-boot:run 
```
或者 连接mysql数据库
```bash
mvn -Ptest clean spring-boot-run
```
## 编译测试
1. 容器启动postgresql
2. 运行
```bash
mvn clean package
```

## 访问
```bash
curl -X POST  -H 'Content-Type: application/json' -d "{\"title\": \"Mr.\",\"lastName\": \"Ryan\"}" http://localhost:8080/api/people  
```

# 部署到Openshift
## 部署
1. ./devops deploy (只第一次运行需要，用来部署应用资源到Openshift)
2. ./devops push (把本地的binary push到Openshift 并且完成部署)
## 运行
浏览器访问
```bash
oc get route greeting-lab-backend
```


