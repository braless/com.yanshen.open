package com.zksk.generator.config;

import java.util.Map;

/**
 * @ProjectName: smart-generator
 * @Package: com.smart.generator.config
 * @ClassName: ContextConfig
 * @Author: Administrator
 * @Description: 自定义全局配置-设置了默认值
 * @Date: 2021-09-03 16:23
 * @Version: 1.0
 */
public class ContextConfig {
    /**
     * 项目存储根目录
     */
    private String outputDir = "D://generator/";

    /**
     * 业务模块
     */
    private String modelName="system";

    /**
     * 项目框架 boot单体项目 cloud微服务项目
     */
    private String osa="boot";

    /**
     * 项目前缀
     */
    private String projectPrefix="zksk-boot";

    /**
     * 项目空间路径
     */
    private String project_path = "/src/main/java";

    /**
     * mybatis-plus空间路径
     */
    private String mybatis_path = "/src/main/resources/mapper";

    /**
     * 作者名称
     */
    private String author="zksk";

    /**
     * 表前缀
     */
    private String[] tablePrefix=null;

    /**
     * 表前缀-单个主要用于根据数据表前缀生成特定前缀
     */
    private String tablePrefixStr=null;

    /**
     * 是否按照前缀进行包创建
     */
    private Boolean isPrefix=false;

    /**
     * 项目包名
     */
    private String parent=null;

    /**
     * 数据库相关
     */
    private String dbType="mysqlDb";//数据库类型 mysqlDb或达梦（dmDb） 暂时只支持这两种
    private String dataSourceUser="user"; //数据库账号

    private String dataSourcePwd="pwd"; //数据库密码

    private String dataSourceUrl="ip:port"; //数据库地址与端口

    private String dataSourceBank=""; //数据库名称

    /**
     * 是否生成数据库全表
     */
    private Boolean isAll=false;

    /**
     * 自定义集成父类-实体对象
     */
    private String superEntityClass="com.zksk.common.interceptor.BaseEntity";

    /**
     * 自定义继承父类-controller
     */
    private String superControllerClass="";

    /**
     * 是否开启文件覆盖-默认关闭划分不同文件
     */
    private Map<String,Boolean> fileOverride;



    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String[] getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String[] tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDataSourceUser() {
        return dataSourceUser;
    }

    public void setDataSourceUser(String dataSourceUser) {
        this.dataSourceUser = dataSourceUser;
    }

    public String getDataSourcePwd() {
        return dataSourcePwd;
    }

    public void setDataSourcePwd(String dataSourcePwd) {
        this.dataSourcePwd = dataSourcePwd;
    }

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    public String getDataSourceBank() {
        return dataSourceBank;
    }

    public void setDataSourceBank(String dataSourceBank) {
        this.dataSourceBank = dataSourceBank;
    }

    public Boolean getAll() {
        return isAll;
    }

    public void setAll(Boolean all) {
        isAll = all;
    }

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public void setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
    }

    public String getSuperControllerClass() {
        return superControllerClass;
    }

    public void setSuperControllerClass(String superControllerClass) {
        this.superControllerClass = superControllerClass;
    }

    public String getTablePrefixStr() {
        return tablePrefixStr;
    }

    public void setTablePrefixStr(String tablePrefixStr) {
        this.tablePrefixStr = tablePrefixStr;
    }

    public String getOsa() {
        return osa;
    }

    public void setOsa(String osa) {
        this.osa = osa;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getProject_path() {
        return project_path;
    }

    public void setProject_path(String project_path) {
        this.project_path = project_path;
    }

    public String getMybatis_path() {
        return mybatis_path;
    }

    public void setMybatis_path(String mybatis_path) {
        this.mybatis_path = mybatis_path;
    }

    public Boolean getPrefix() {
        return isPrefix;
    }

    public void setPrefix(Boolean prefix) {
        isPrefix = prefix;
    }

    public String getProjectPrefix() {
        return projectPrefix;
    }

    public void setProjectPrefix(String projectPrefix) {
        this.projectPrefix = projectPrefix;
    }

    public Map<String, Boolean> getFileOverride() {
        return fileOverride;
    }

    public void setFileOverride(Map<String, Boolean> fileOverride) {
        this.fileOverride = fileOverride;
    }
}
