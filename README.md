# Getting started  
A Spring Boot starter, enhanced [Mybatis Plus](https://github.com/baomidou/mybatis-plus) which looks like Spring Data JPA. 
Feature: 
+ @CreatedBy
+ @CreatedDate
+ @LastModifuedBy
+ @LastModifiedDate
+ @PageableDefault  

## Installation

If you are using Maven just add the following dependency to your pom.xml:
```xml
<dependency>
	<groupId>io.github.sogdata</groupId>
	<artifactId>mybatis-plus-spring-boot-starter</artifactId>
	<version>x.x.x</version>
</dependency>
```

## Configuration

page attribute (@PageDefault, IPage<?>)
```java
@Bean()
PageAttribute pageAttribute() {
	return new PageAttribute.Builder().page("page").size("size").content("content").build();
}
```  

account attribute (@CreatedBy,@LastModifiedBy)
```java
@Bean
AccountAttribute accountAttribute() {
	Map<Creator, Object> map = new HashMap<>();
	map.put(Creator.ID, getId());
	map.put(Creator.NAME, getName());
	return new AccountAttribute.Builder().attributes(map).build();
}
```