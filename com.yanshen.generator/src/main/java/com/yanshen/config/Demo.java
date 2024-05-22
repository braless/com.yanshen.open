package com.yanshen.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("=====================数据库配置=======================");
        System.out.println("请输入 URL");
        String url ="jdbc:mysql://192.168.20.99:3307/isp-boot?useUnicode=true&characterEncoding=utf-8&userSSL=false&serverTimezone=GMT%2B8";
        System.out.println("请输入 username");
        String username = "root";
        System.out.println("请输入 password");
        String password = "zksk666";
        String destniton = System.getProperty("user.dir") + "/com.yanshen.generator/";
        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("=====================全局配置=======================\n请输入作者名称？"))
                        .outputDir(destniton + "/src/main/java")
                        .commentDate("yyyy-MM-dd hh:mm:ss")
                        .dateType(DateType.TIME_PACK)
                        .enableSwagger()
                        .fileOverride()
                        .enableSwagger()
                        .disableOpenDir()
                )
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("=====================包配置=======================\n请输入包名？"))
                        .moduleName(scanner.apply("请输入父包模块名？"))
                        .entity("entity")
                        .service("service")
                        .serviceImpl("serviceImpl")
                        .mapper("mapper")
                        .xml("mapper")
                        .other("utils")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, destniton+"/src/main/resources/mapper"))
                )
                // 策略配置
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(getTables(scanner.apply("=====================策略配置=======================\n请输入表名，多个英文逗号分隔？所有输入 all")))
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()        //实体类策略配置
                            .enableLombok()         //开启 Lombok
                            .disableSerialVersionUID()
                            .logicDeleteColumnName("deleted")        //逻辑删除字段
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .addTableFills(new Column("create_time", FieldFill.INSERT), new Column("modify_time", FieldFill.INSERT_UPDATE))
                            .enableTableFieldAnnotation()       // 开启生成实体时生成字段注解
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .mapperBuilder()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()       //@mapper
                            .formatXmlFileName("%sMapper");
                })
                /*
                模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                .templateEngine(new BeetlTemplateEngine())
                .templateEngine(new FreemarkerTemplateEngine())
                             */
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
