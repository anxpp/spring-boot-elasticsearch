# Spring Boot 集成 Elasticsearch

## Elasticsearch 安装

下载地址：https://www.elastic.co/downloads/elasticsearch

下载地址对应的网页也包含了安装步骤：

- 下载并解压文件
- 运行 /bin/elasticsearch （windows下为 bin\elasticsearch.bat）
- 访问 http://localhost:9200/

## 搭建Spring Boot 项目

    源码：

截止2019.02.25，对应的 starter 最新版本为 2.1.3.RELEASE，对应的 Elasticsearch 版本支持为 6.X

项目使用 Gradle 构建（Maven大同小异），使用了 Jpa。

完整的构建配置为：

```
plugins {
    id 'org.springframework.boot' version '2.1.3.RELEASE'
    id 'java'
}

apply plugin: 'io.spring.dependency-management'

group = 'com.anxpp.demo'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-elasticsearch'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.h2database:h2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

