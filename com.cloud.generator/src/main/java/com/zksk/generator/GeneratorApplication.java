package com.zksk.generator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.zksk.generator.config.CodeGenerationConfig;
import com.zksk.generator.config.ContextConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GeneratorApplication {

    /**
     * @Description: 执行代码生成器方法
     * @Param: [args]
     * @return: void
     * @Author: baohao-jia
     * @Date: 9:57 2023-09-01
     */
    public static void main(String[] args) {
        CodeGenerationConfig codeGenerationConfig = new CodeGenerationConfig();

        //全局变量配置
        ContextConfig config = new ContextConfig();
        //生成代码文件存储根目录-为空使用默认目录，如需要生成到执行项目下则需要项目绝对路径
        config.setOutputDir("D:\\com.yanshen.open\\com.cloud.generator\\");
        //模块名称
        config.setModelName("admin");
        //项目框架-默认单体项目（针对公司框架特定配置项，单体项目此像配置可以注释掉）
        config.setOsa("cloud");
        //项目前缀-用于针对单体模块可直接生成到指定模块下
        config.setProjectPrefix("zksk-boot");
        //作者
        config.setAuthor("Yanshen");
        //前缀取消-用于进行生成时的表前缀取消,可以配置多个 格式：sys_
        config.setTablePrefix(new String[]{""});
        //前缀过滤-可根据指定前缀生成指定表的代码,与前缀取消冲突，如此项配置了值则只能生成指定前缀的数据库表对应代码 格式：sys_
        config.setTablePrefixStr("");
        //按照前缀设置包名开关-默认关闭
        config.setPrefix(false);
        //项目包路径 格式：com.zksk
        config.setParent("com.yanshen");
        ////数据库类型 mysqlDb或达梦（dmDb） 暂时只支持这两种-可针对数据库类型进行数据源切换设置
        config.setDbType("mysqlDb");
        //数据库配置相关
        config.setDataSourceUser("root");
        config.setDataSourcePwd("6d34761495ec289e");
        config.setDataSourceUrl("192.168.1.47:3306");
        config.setDataSourceBank("com.yanshen.open");
        //继承类绝对路径
        config.setSuperControllerClass("com.yanshen.common.web.BasePlusController");
        //继承类绝对路径
        config.setSuperEntityClass("com.yanshen.common.core.web.domain.BasePlusEntity");
        //文件覆盖开关
        Map<String,Boolean> fileOverrideMap=new HashMap<>();
        fileOverrideMap.put(OutputFile.entity.name(),true);
        fileOverrideMap.put(OutputFile.mapper.name(),true);
        fileOverrideMap.put(OutputFile.service.name(),true);
        fileOverrideMap.put(OutputFile.controller.name(),true);
        config.setFileOverride(fileOverrideMap);
        //标记是否生成当前库的所有表
        config.setAll(false);
        codeGenerationConfig.doMpGeneration(config);
    }

}
