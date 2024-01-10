package com.fcc.PureSync.core.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    //클래스내에서 application.properties 파일에 접근하기위해서
    @Autowired
    ApplicationContext applicationContext;

    //hikari 설정하기  : application.properties 파일에서 히카리 정보 알아오기
    //히카리 : 자바에서 제공하는 디비커넥션풀이다.
    //이걸 사용해야 다량의 디비접속 처리가 가능하고 속도가 빠르다

    @Bean  //@Bean 객체라는 의미임
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
        //application.properties 의 hikari설정을 읽어서 객체를 만들어 던져준다
    }

    @Bean
    public DataSource dataSource() {
        //설정객체값으로 데이터소스를 만들어서 반환한다.
        return new HikariDataSource(hikariConfig());
    }


    //MyBatis factory -> SessionTermplate
    @Bean //객체 생성
    public SqlSessionFactory makeSqlSessionFactory(DataSource dataSource) throws Exception
    {
        //SqlSessionFactory -  Factory 공장객체를 먼저 만들어서 던진다
        final SqlSessionFactoryBean factory
                = new SqlSessionFactoryBean();

        //저객체와 application.properties 파일에 있는  datasource

        factory.setDataSource(dataSource);
        //방법이 여러가지이다.
        //설정파일과 연동하기(mybatis-config.xml)파일과 연동
        PathMatchingResourcePatternResolver resolver
                = new PathMatchingResourcePatternResolver();
        //classpath - src/main/resource
        Resource configLocation =
                resolver.getResource("classpath:mybatis-config.xml");
        //배포버전 만들때 ~.xml로 시작하는 모든 파일은 src/main/resources 에
        //두지 않으면  우리가 프로젝트 위치에 직접 복사붙여넣기를 해야 한더
        factory.setConfigLocation(configLocation);
        return factory.getObject();
    }

    @Bean(name="sm")
    public SqlSessionTemplate makeSqlSession(
            SqlSessionFactory factory)
    {
        return new SqlSessionTemplate(factory);
    }

}