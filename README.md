# 本地开发模式
## 启动运行
1. 启动postgresql 
```bash
docker run --name postgres -e POSTGRES_USER=postgres \
           -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=my_data \
           -p 5432:5432 -d postgres:10.5
```
2. 启动命令
```bash
 SPRING_PROFILES_ACTIVE=dev mvn clean spring-boot:run
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

## 运行