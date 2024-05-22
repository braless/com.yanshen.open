package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if swagger2>
    import io.swagger.annotations.ApiOperation;
</#if>
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* <p>
    * ${table.comment!} 控制层
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
    @RestController
<#else>
    @Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
        public class ${table.controllerName} {
    </#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?lower_case};

    <#if restControllerStyle>
        @GetMapping("/list")
    </#if>
    <#if swagger2>
        @ApiOperation(value = "${table.comment}列表查询",notes = "list")
    </#if>
    public List<${entity}> list(){

    return ${table.serviceName?lower_case}.list();
    }

    <#if restControllerStyle>
        @GetMapping("/get")
    </#if>
    <#if swagger2>
        @ApiOperation(value = "${table.comment}详情查询",notes = "get")
    </#if>
    public ${entity} get(String id){

    return ${table.serviceName?lower_case}.getById(id);
    }

    <#if restControllerStyle>
        @PostMapping("/save")
    </#if>
    <#if swagger2>
        @ApiOperation(value = "${table.comment}保存",notes = "save")
    </#if>
    public void save(${entity} ${entity?lower_case}){

    ${table.serviceName?lower_case}.save(${entity?lower_case});
    }

    <#if restControllerStyle>
        @DeleteMapping("/delete")
    </#if>
    <#if swagger2>
        @ApiOperation(value = "${table.comment}删除",notes = "delete")
    </#if>
    public void delete(List<String> ids){

    ${table.serviceName?lower_case}.removeByIds(ids);
    }
    }
</#if>