package com.zksk.generator.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.converts.DmTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.querys.DMQuery;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author: baohao-jia
 * @date: 2023年09月01日 9:57
 * @ClassName: CodeGenerationConfig
 * @Description: 代码生成器相关配置
 */
public class CodeGenerationConfig {

    private void bootMpGeneration(ContextConfig config) {
        /**
         * 数据源配置
         * 1、数据源
         * 2、数据查询配置
         * 3、数据库schema(部分数据库适用)
         * 4、数据库类型转换器
         * 5、数据库关键字处理器
         */
        //根据数据库类型进行不同链接配置-当前版本只支持达梦和mysql两种数据库
        String urlStr = StrUtil.EMPTY;
        if (config.getDbType().equals("dm")) {
            urlStr = StrUtil.format("jdbc:dm://{}?schema={}", config.getDataSourceUrl(), config.getDataSourceBank());
        } else {
            urlStr = StrUtil.format("jdbc:mysql://{}/{}?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8", config.getDataSourceUrl(), config.getDataSourceBank());
        }
        //判定数据库类型
        DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
                .Builder(urlStr, config.getDataSourceUser(), config.getDataSourcePwd())
                .dbQuery((config.getDbType().equals("dm")) ? (new DMQuery()) : (new MySqlQuery()))
                .schema("jdbc.schema")
                .typeConvert((config.getDbType().equals("dm")) ? (new DmTypeConvert()) : (new MySqlTypeConvert())) //数据库类型转换器
                .keyWordsHandler((config.getDbType().equals("dm")) ? null : (new MySqlKeyWordsHandler())); //数据库关键字处理器;
        //自定义模板，用于生成Dto、Vo等自定义文件
        List<CustomFile> customFiles = new ArrayList<>();
        /**
         * 生成器参数设置
         */
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                //全局配置-快速生成模式
                .globalConfig(builder -> {
                    builder.author(config.getAuthor())
                            .disableOpenDir()
                            //.enableKotlin()
                            //.enableSpringdoc()
                            //.enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd")
                            .build();
                })
                //设置包-快速生成模式
                .packageConfig(builder -> {
                    //设置包路径
                    Map<OutputFile, String> outputFileStringMap = new HashMap<>();
                    String str = StrUtil.replaceIgnoreCase(config.getParent(), StrUtil.DOT, "/");
                    builder.parent(config.getParent());//设置父、项目包名
                    customFiles.add(new CustomFile.Builder().fileName("Dto.java").templatePath("templates/boot/dto.java.vm")
                            .filePath(StrUtil.format("{}/{}/{}/{}/{}", (config.getProjectPrefix().equals("zksk-boot") ? StrUtil.format("{}/{}-domain", config.getOutputDir(), config.getProjectPrefix()) : config.getOutputDir()), config.getProject_path(), str, "domain", config.getModelName())).packageName("dto").build());
                    customFiles.add(new CustomFile.Builder().fileName("Vo.java").templatePath("templates/boot/vo.java.vm")
                            .filePath(StrUtil.format("{}/{}/{}/{}/{}", (config.getProjectPrefix().equals("zksk-boot") ? StrUtil.format("{}/{}-domain", config.getOutputDir(), config.getProjectPrefix()) : config.getOutputDir()), config.getProject_path(), str, "domain", config.getModelName())).packageName("vo").build());
                    //针对框架进行特定化处理
                    builder.entity(StrUtil.format("domain.{}", config.getModelName()))
                            .mapper(StrUtil.format("mapper.{}", config.getModelName()))
                            .service(StrUtil.format("business.{}", config.getModelName()))
                            .serviceImpl(StrUtil.format("business.{}.impl", config.getModelName()))
                            .controller(StrUtil.format("web.controller.{}", config.getModelName()));
                    //获取项目前缀
                    outputFileStringMap.put(OutputFile.entity, StrUtil.format("{}/{}/{}/{}/{}", (config.getProjectPrefix().equals("zksk-boot") ? StrUtil.format("{}/{}-domain", config.getOutputDir(), config.getProjectPrefix()) : config.getOutputDir()), config.getProject_path(), str, "domain", config.getModelName()));
                    outputFileStringMap.put(OutputFile.mapper, StrUtil.format("{}/{}/{}/{}/{}", (config.getProjectPrefix().equals("zksk-boot") ? StrUtil.format("{}/{}-domain", config.getOutputDir(), config.getProjectPrefix()) : config.getOutputDir()), config.getProject_path(), str, "mapper", config.getModelName()));
                    outputFileStringMap.put(OutputFile.xml, StrUtil.format("{}/{}/{}", (config.getProjectPrefix().equals("zksk-boot") ? StrUtil.format("{}/{}-domain", config.getOutputDir(), config.getProjectPrefix()) : config.getOutputDir()), config.getMybatis_path(), config.getModelName()));
                    outputFileStringMap.put(OutputFile.service, StrUtil.format("{}/{}/{}/{}/{}", (config.getProjectPrefix().equals("zksk-boot") ? StrUtil.format("{}/{}-business", config.getOutputDir(), config.getProjectPrefix()) : config.getOutputDir()), config.getProject_path(), str, "business", config.getModelName()));
                    outputFileStringMap.put(OutputFile.serviceImpl, StrUtil.format("{}/{}/{}/{}/{}/{}", (config.getProjectPrefix().equals("zksk-boot") ? StrUtil.format("{}/{}-business", config.getOutputDir(), config.getProjectPrefix()) : config.getOutputDir()), config.getProject_path(), str, "business", config.getModelName(), "impl"));
                    outputFileStringMap.put(OutputFile.controller, StrUtil.format("{}/{}/{}/{}/{}/{}", (config.getProjectPrefix().equals("zksk-boot") ? StrUtil.format("{}/{}-web", config.getOutputDir(), config.getProjectPrefix()) : config.getOutputDir()), config.getProject_path(), str, "web", "controller", config.getModelName()));
                    builder.pathInfo(outputFileStringMap).build();
                })
                //模板配置-快速生成模式
                .templateConfig(builder -> {
                    //禁用系统模板-使用自定义模板
                    builder
                            //.disable()
                            .entity("templates/boot/entity.java")
                            .mapper("templates/boot/mapper.java")
                            .xml("templates/boot/mapper.xml")
                            .service("templates/boot/service.java")
                            .serviceImpl("templates/boot/serviceImpl.java")
                            .controller("templates/boot/controller.java");
                })
                //策略配置-交互生成模式自定义操作
                .strategyConfig(builder -> {
                            builder.entityBuilder().superClass(config.getSuperEntityClass())
                                    .enableLombok()
                                    .enableRemoveIsPrefix()
                                    .enableTableFieldAnnotation()
                                    .disableSerialVersionUID()
                                    .versionColumnName("version")
                                    .logicDeleteColumnName("del_flag")
                                    .naming(NamingStrategy.underline_to_camel)
                                    .columnNaming(NamingStrategy.underline_to_camel)
                                    .addSuperEntityColumns("create_by", "create_time", "update_by", "update_time", "del_flag", "`status`", "status", "version", "remark")
                                    .idType(IdType.ASSIGN_ID)
                                    //.formatFileName("%sEntity")
                                    .mapperBuilder().superClass("")
                                    .enableMapperAnnotation()
                                    .enableBaseResultMap()
                                    .formatMapperFileName("%sMapper")
                                    .formatXmlFileName("%sMapper")
                                    .serviceBuilder().formatServiceFileName("I%sService")
                                    .formatServiceImplFileName("%sServiceImpl")
                                    .controllerBuilder().superClass(config.getSuperControllerClass())
                                    .enableRestStyle();
                            //判定是否进行文件覆盖设置
                            if (!config.getFileOverride().isEmpty()) {
                                if (config.getFileOverride().containsKey(OutputFile.entity.name()) && config.getFileOverride().get(OutputFile.entity.name())) {
                                    builder.entityBuilder().enableFileOverride();
                                }
                                if (config.getFileOverride().containsKey(OutputFile.mapper.name()) && config.getFileOverride().get(OutputFile.mapper.name())) {
                                    builder.mapperBuilder().enableFileOverride();
                                }
                                if (config.getFileOverride().containsKey(OutputFile.service.name()) && config.getFileOverride().get(OutputFile.service.name())) {
                                    builder.serviceBuilder().enableFileOverride();
                                }
                                if (config.getFileOverride().containsKey(OutputFile.controller.name()) && config.getFileOverride().get(OutputFile.controller.name())) {
                                    builder.controllerBuilder().enableFileOverride();
                                }
                            }
                            //牵扯到交互配置的需要单独设置
                            if (!config.getAll()) {
                                //判定当前是否需要全表生成-不建议全表生成
                                //启动控制台输入框进行表名输入
                                builder.addInclude(scanner("表名，多个英文逗号分割").split(","));

                            } else {
                                //过滤前缀不为空则启动sql模糊过滤功能-指定生成表此项配置不生效
                                if (StringUtils.isNotEmpty(config.getTablePrefixStr()) && config.getAll()) {
                                    builder.likeTable(new LikeTable(config.getTablePrefixStr()));
                                } else {
                                    //当不按照前缀过滤时，则进行非以下前缀的全表生成【xxl_、FLW_、ACT_】
                                    List<String> include = new ArrayList<>();
                                    include.add("^xxl_.*");//任务调度表
                                    include.add("^FLW_.*");//流程引擎
                                    include.add("^ACT_.*");//流程引擎
                                    //指定前缀不进行代码生成
                                    builder.addExclude(include);
                                }
                            }
                            //表前缀生成时剔除掉
                            builder.addTablePrefix(config.getTablePrefix());
                            builder.build();
                        }

                )
                // 注入配置-快速生成模式
                .injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, objectMap) -> {
                                System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                            }).customMap(Collections.singletonMap("modelName", config.getModelName()))
                            .customFile(customFiles)
                            //.customFile(new CustomFile.Builder().fileName("DTO.java").templatePath("templates/boot/dto.java.vm").filePath("aaaa").packageName("dto").build())
                            .build();

                })
                // 模板引擎-template模式
                .templateEngine(new VelocityTemplateEngine())
                //执行队列构建操作
                .execute();

    }

    public void doMpGeneration(ContextConfig config) {
        if (config.getOsa().equals("boot")) {
            bootMpGeneration(config);
        } else {
            cloudMpGeneration(config);
        }
    }

    public void cloudMpGeneration(ContextConfig config) {
        /**
         * 数据源配置
         * 1、数据源
         * 2、数据查询配置
         * 3、数据库schema(部分数据库适用)
         * 4、数据库类型转换器
         * 5、数据库关键字处理器
         */
        //根据数据库类型进行不同链接配置-当前版本只支持达梦和mysql两种数据库
        String urlStr = StrUtil.EMPTY;
        if (config.getDbType().equals("dm")) {
            urlStr = StrUtil.format("jdbc:dm://{}?schema={}", config.getDataSourceUrl(), config.getDataSourceBank());
        } else {
            urlStr = StrUtil.format("jdbc:mysql://{}/{}?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8", config.getDataSourceUrl(), config.getDataSourceBank());
        }
        //判定数据库类型
        DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
                .Builder(urlStr, config.getDataSourceUser(), config.getDataSourcePwd())
                .dbQuery((config.getDbType().equals("dm")) ? (new DMQuery()) : (new MySqlQuery()))
                .schema("jdbc.schema")
                .typeConvert((config.getDbType().equals("dm")) ? (new DmTypeConvert()) : (new MySqlTypeConvert())) //数据库类型转换器
                .keyWordsHandler((config.getDbType().equals("dm")) ? null : (new MySqlKeyWordsHandler())); //数据库关键字处理器;
        /**
         * 生成器参数设置
         */
        //自定义模板，用于生成Dto、Vo等自定义文件
        List<CustomFile> customFiles = new ArrayList<>();
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                //全局配置-快速生成模式
                .globalConfig(builder -> {
                    builder.author(config.getAuthor())
                            .disableOpenDir()
                            //.enableKotlin()
                            //.enableSpringdoc()
                            //.enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd")
                            .build();
                })
                //设置包-快速生成模式
                .packageConfig(builder -> {
                    //设置包路径
                    Map<OutputFile, String> outputFileStringMap = new HashMap<>();
                    outputFileStringMap.put(OutputFile.entity, config.getOutputDir() + "src/main/java/com/zksk/" + config.getModelName() + "/domain");
                    outputFileStringMap.put(OutputFile.mapper, config.getOutputDir() + "src/main/java/com/zksk/" + config.getModelName() + "/mapper");
                    outputFileStringMap.put(OutputFile.service, config.getOutputDir() + "src/main/java/com/zksk/" + config.getModelName() + "/service");
                    outputFileStringMap.put(OutputFile.serviceImpl, config.getOutputDir() + "src/main/java/com/zksk/" + config.getModelName() + "/service/impl");
                    outputFileStringMap.put(OutputFile.controller, config.getOutputDir() + "src/main/java/com/zksk/" + config.getModelName() + "/controller");
                    outputFileStringMap.put(OutputFile.xml, config.getOutputDir() + "src/main/resources/mapper/");
                    customFiles.add(new CustomFile.Builder().fileName("Dto.java").templatePath("templates/cloud/dto.java.vm")
                            .filePath(config.getOutputDir() + "src/main/java/com/zksk/" + config.getModelName() + "/domain").packageName("dto").build());
                    customFiles.add(new CustomFile.Builder().fileName("Vo.java").templatePath("templates/cloud/vo.java.vm")
                            .filePath(config.getOutputDir() + "src/main/java/com/zksk/" + config.getModelName() + "/domain").packageName("vo").build());
                    builder.entity("domain")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .parent(config.getParent())
                            .moduleName(config.getModelName())
                            .pathInfo(outputFileStringMap).build();
                })
                //模板配置-快速生成模式
                .templateConfig(builder -> {
                    //禁用系统模板-使用自定义模板
                    builder
                            //.disable()
                            .entity("templates/cloud/entity.java")
                            .mapper("templates/cloud/mapper.java")
                            .xml("templates/cloud/mapper.xml")
                            .service("templates/cloud/service.java")
                            .serviceImpl("templates/cloud/serviceImpl.java")
                            .controller("templates/cloud/controller.java");
                })
                //策略配置-交互生成模式自定义操作
                .strategyConfig(builder -> {
                            builder.entityBuilder().superClass(config.getSuperEntityClass())
                                    .enableLombok()
                                    .enableRemoveIsPrefix()
                                    .enableTableFieldAnnotation()
                                    .disableSerialVersionUID()
                                    .versionColumnName("version")
                                    .logicDeleteColumnName("del_flag")
                                    .naming(NamingStrategy.underline_to_camel)
                                    .columnNaming(NamingStrategy.underline_to_camel)
                                    .addSuperEntityColumns("create_by", "create_time", "update_by", "update_time", "del_flag", "`status`", "status", "version", "remark")
                                    .idType(IdType.ASSIGN_ID)
                                    //.formatFileName("%sEntity")
                                    .mapperBuilder().superClass("")
                                    .enableMapperAnnotation()
                                    .enableBaseResultMap()
                                    .formatMapperFileName("%sMapper")
                                    .formatXmlFileName("%sMapper")
                                    .serviceBuilder().formatServiceFileName("I%sService")
                                    .formatServiceImplFileName("%sServiceImpl")
                                    .controllerBuilder().superClass(config.getSuperControllerClass())
                                    .enableRestStyle();
                            //判定是否进行文件覆盖设置
                            if (!config.getFileOverride().isEmpty()) {
                                if (config.getFileOverride().containsKey(OutputFile.entity.name()) && config.getFileOverride().get(OutputFile.entity.name())) {
                                    builder.entityBuilder().enableFileOverride();
                                }
                                if (config.getFileOverride().containsKey(OutputFile.mapper.name()) && config.getFileOverride().get(OutputFile.mapper.name())) {
                                    builder.mapperBuilder().enableFileOverride();
                                }
                                if (config.getFileOverride().containsKey(OutputFile.service.name()) && config.getFileOverride().get(OutputFile.service.name())) {
                                    builder.serviceBuilder().enableFileOverride();
                                }
                                if (config.getFileOverride().containsKey(OutputFile.controller.name()) && config.getFileOverride().get(OutputFile.controller.name())) {
                                    builder.controllerBuilder().enableFileOverride();
                                }
                            }
                            //牵扯到交互配置的需要单独设置
                            if (!config.getAll()) {
                                //判定当前是否需要全表生成-不建议全表生成
                                //启动控制台输入框进行表名输入
                                builder.addInclude(scanner("表名，多个英文逗号分割").split(","));

                            } else {
                                //过滤前缀不为空则启动sql模糊过滤功能-指定生成表此项配置不生效
                                if (StringUtils.isNotEmpty(config.getTablePrefixStr()) && config.getAll()) {
                                    builder.likeTable(new LikeTable(config.getTablePrefixStr()));
                                } else {
                                    //当不按照前缀过滤时，则进行非以下前缀的全表生成【xxl_、FLW_、ACT_】
                                    List<String> include = new ArrayList<>();
                                    include.add("^xxl_.*");//任务调度表
                                    include.add("^FLW_.*");//流程引擎
                                    include.add("^ACT_.*");//流程引擎
                                    //指定前缀不进行代码生成
                                    builder.addExclude(include);
                                }
                            }
                            //表前缀生成时剔除掉
                            builder.addTablePrefix(config.getTablePrefix());
                            builder.build();
                        }
                )
                // 注入配置-快速生成模式
                .injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, objectMap) -> {
                                System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                            }).customFile(customFiles)
                            .build();

                })
                // 模板引擎-template模式
                .templateEngine(new VelocityTemplateEngine())
                //执行队列构建操作
                .execute();
    }

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
