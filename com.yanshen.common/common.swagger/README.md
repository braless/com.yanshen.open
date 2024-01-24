# 整合 Swagger3 和Kenif4j
- [x] 1.整合 Swagger3
- 父pom加入依赖 并指定版本
```xml
        <knife4j.version>3.0.3</knife4j.version>
        <springfox.version>3.0.0</springfox.version>
        <!-- Swagger3-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
            <version>${springfox.version}</version>
        </dependency>
        <!-- knife4j 文档增强 -->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-spring-boot-starter</artifactId>
            <version>${knife4j.version}</version>
        </dependency>
```
- 
