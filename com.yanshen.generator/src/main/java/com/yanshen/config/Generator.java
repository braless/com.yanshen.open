package com.yanshen.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName MybatisPlusGenerator
 * @Description TODO
 * @Author wangpeng
 * @Date 2022/3/29 17:25
 */
public class Generator {

    public  static String  url ="jdbc:mysql://61.139.65.135:31063/com.yanshen.open?useUnicode=true&characterEncoding=utf-8&userSSL=false&serverTimezone=GMT%2B8";
    public static  String user ="root";
    public static  String pwd=  "6d34761495ec289e";  //;/"6d34761495ec289e";

    public static  String db=url;
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create(
                new DataSourceConfig.Builder(db,
                        user,pwd))
                // 全局配置
                .globalConfig((scanner, builder) -> builder
                        // 交互式作用名称
                        //scanner.apply("请输入作者名称？")
                         .author("cyc")
                        // .author("wangpeng")
                        .fileOverride()
                        // 开启swagger模式
                        .enableSwagger()
                        .disableOpenDir() // 执行完毕不打开文件夹
                        // 输出位置
                       //.outputDir(projectPath + "/com.yanshen.generator/src/main/java/com.yanshen/")
                        .outputDir("D:\\com.yanshen.open\\com.yanshen.generator\\src\\main\\java\\com\\yanshen")
                )
                // 包配置
                //scanner.apply("请输入包名？")
//                .packageConfig((scanner, builder) -> builder.parent("")
//                        .service("service")
//                        .serviceImpl("service.Impl")
//                        .mapper("mapper")
//                        .xml("mapper.xml")
//                        .entity("entity")
//                )
                .packageConfig(builder -> {
                    builder.parent("") // 设置父包名
                            // 设置mapperXml生成路径
                            //直接右键复制项目mapper文件夹的绝对路径

                            .pathInfo(Collections.singletonMap(OutputFile.xml,"D:\\github\\com.yanshen.cloud\\com.yanshen.generator\\src\\main\\resources\\mapper"));
                })

                // 策略配置
                //getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))
                // builder.notLikeTable(new LikeTable("act", SqlLike.RIGHT))
                .strategyConfig((scanner, builder) -> builder.addInclude()
                        .notLikeTable(new LikeTable("act", SqlLike.RIGHT))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok()
                        // 下划线转驼峰命名
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .addTableFills(
                                new Column("update_time",FieldFill.INSERT_UPDATE))
                        .addTableFills(
                                new Column("create_time", FieldFill.INSERT)

                        ).build())

                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
//                .templateEngine(new BeetlTemplateEngine())
                .templateConfig((scanner, builder) -> getTemplateConfig())
                .execute();


    }



    public static TemplateConfig getTemplateConfig () {
        //创建一个mybatisplusn逆向生成实体类的方法
        TemplateConfig templateConfig = new TemplateConfig.Builder()
//                .disable(TemplateType.ENTITY)
                .entity("/templates/entity.java.vm")
                .service("/templates/service.java.vm")
                .serviceImpl("/templates/serviceImpl.java.vm")
                .mapper("/templates/mapper.java.vm")
                .xml("/templates/mapper.xml.vm")
                .controller("/templates/controller.java.vm")
                .build();
        return templateConfig;
    }


    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
