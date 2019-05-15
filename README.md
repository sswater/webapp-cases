# Demo Pre-Compiled Webapp 
In order to pack webapp into an exe file using Jar2Exe, we need to pre-compile the webapp.

## Pre-compile webapp using maven plugin
Comparing to [pre-compile webapp using ant script](http://www.jar2exe.com/solutions/webapp-ant), it is a much easier way to use maven plugin:

```xml
  <build>
    <plugins>
    
      <!-- compile jsp source files, and copy & modify `WEB-INF/web.xml` to `target/web.xml` -->
      <plugin>
        <groupId>org.eclipse.jetty</groupId>
        <artifactId>jetty-jspc-maven-plugin</artifactId>
        <version>9.4.4.v20170414</version>
        <executions>
          <execution>
            <id>jspc</id>
            <goals>
              <goal>jspc</goal>
            </goals>
            <configuration>
            </configuration>
          </execution>
        </executions>
      </plugin>
      
      <!-- package war file -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>3.2.2</version>
        <configuration>

          <!-- exclude jsp source files since they are compiled already -->
          <packagingExcludes>
            **/*.jsp,
            **/*.jspx
          </packagingExcludes>

          <!-- use compiled web.xml when `mvn package`, remark this line when debugging webapp in eclipse -->
          <webXml>${project.basedir}/target/web.xml</webXml>

        </configuration>
      </plugin>
      
    </plugins>
  </build>
```

Then `mvn package`, the output file `target/xxxx.war` will be pre-compiled already.

## Make Spring-MVC compatible with Jar2Exe
After encrypted by Jar2Exe, to enumerate files within a jar file is not allowed. So, any kind of `component-scan` will not work.

1. Change the scan of service beans (applicationContext.xml):

  ```xml
    <!-- *********************************
      bean scan is not allowed by Jar2Exe. 
      instead, beans need to be declared explicitly, one by one
    ********************************* -->
    <!-- <context:component-scan base-package="com.playcoding.webapp.service" /> -->
    <bean class="com.playcoding.webapp.service.TestDemoService" />
  ```

2. Change the scan of mapper xml (applicationContext.xml):

  ```xml
    <!-- batis -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="configLocation" value="classpath:mybatis-config.xml" />
      <property name="dataSource" ref="dataSource" />
      <!-- *********************************
        resource scan is not allowed by Jar2Exe.
        instead, mapper xml need to be declared explicitly, one by one
      ********************************* -->
      <!-- <property name="mapperLocations" value="classpath*:mapper/*.xml" /> -->
      <property name="mapperLocations">
        <list>
          <value>classpath*:mapper/TestDemoDao.xml</value>
        </list>
      </property>
    </bean>
  ```

3. Change the scan of mapper interfaces (applicationContext.xml):

  ```xml
    <!-- *********************************
      bean scan is not allowed by Jar2Exe.
    ********************************* -->
    <!-- <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
      <property name="basePackage" value="com.playcoding.webapp.dao" />
    </bean> -->
    <!-- *********************************
      instead, mapper interface need to be declared explicitly, one by one
    ********************************* -->
    <bean id="baseMapper" class="org.mybatis.spring.mapper.MapperFactoryBean" abstract="true" lazy-init="true">
      <property name="sqlSessionFactory" ref="sqlSessionFactory" />
    </bean>
    <bean id="testDemoDao" parent="baseMapper">
      <property name="mapperInterface" value="com.playcoding.webapp.dao.TestDemoDao" />
    </bean>
  ```
	
4. Change the scan of controller beans (spring-mvc.xml):

  ```xml
    <!-- *********************************
      bean scan is not allowed by Jar2Exe. 
      instead, beans need to be declared explicitly, one by one
    ********************************* -->
    <!-- <context:component-scan base-package="com.playcoding.webapp.controller" /> -->
    <bean class="com.playcoding.webapp.controller.TestDemoAction"></bean>
  ```
