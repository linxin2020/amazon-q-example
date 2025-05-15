package com.anycompany.demo.jumping.generator.core;

import com.anycompany.demo.jumping.generator.common.GeneratorConstants;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyBatis Entity生成器
 * 根据数据库表结构自动生成Entity类
 */
@Component
public class EntityGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String TEMPLATE_PATH = "templates/entity.vm";
    private static final String OUTPUT_DIR = GeneratorConstants.ENTITY_OUTPUT_DIR;
    private static final String PACKAGE_NAME = GeneratorConstants.MODEL_PACKAGE_NAME;
    private static final String TABLE_PREFIX = GeneratorConstants.TABLE_PREFIX;

    /**
     * 生成Entity类
     * @param useTempFile 是否使用临时文件（true用于测试，false用于生产）
     */
    public void generateEntities(boolean useTempFile) {
        try {
            // 初始化Velocity引擎
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngine.init();

            // 获取数据库中的所有表
            List<String> tables = getTables();
            
            for (String tableName : tables) {
                // 只处理带有前缀的表
                if (!tableName.startsWith(TABLE_PREFIX)) {
                    continue;
                }
                
                // 获取表结构信息
                List<Map<String, Object>> columns = getTableColumns(tableName);
                
                // 生成类名（去掉前缀，转为驼峰命名）
                String className = convertToClassName(tableName.substring(TABLE_PREFIX.length()));
                
                // 创建Velocity上下文
                VelocityContext context = new VelocityContext();
                context.put("packageName", PACKAGE_NAME);
                context.put("className", className);
                context.put("tableName", tableName);
                context.put("columns", columns);
                context.put("serialVersionUID", "1L");
                
                // 获取模板
                Template template = velocityEngine.getTemplate(TEMPLATE_PATH);
                
                // 渲染模板
                StringWriter writer = new StringWriter();
                template.merge(context, writer);
                String content = writer.toString();
                
                // 输出到文件
                String outputPath;
                if (useTempFile) {
                    // 使用临时文件
                    Path tempFile = Files.createTempFile(className, ".java");
                    outputPath = tempFile.toString();
                    System.out.println("Generated temp file: " + outputPath);
                } else {
                    // 使用实际输出路径
                    outputPath = OUTPUT_DIR + className + ".java";
                    // 确保目录存在
                    File dir = new File(OUTPUT_DIR);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                }
                
                try (FileWriter fileWriter = new FileWriter(outputPath)) {
                    fileWriter.write(content);
                }
                
                System.out.println("Generated entity for table: " + tableName + " -> " + className + ".java");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库中的所有表
     */
    private List<String> getTables() {
        String sql = "SHOW TABLES";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    /**
     * 获取表的列信息
     */
    private List<Map<String, Object>> getTableColumns(String tableName) {
        String sql = "SHOW FULL COLUMNS FROM " + tableName;
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        
        List<Map<String, Object>> columns = new ArrayList<>();
        for (Map<String, Object> row : result) {
            Map<String, Object> column = new HashMap<>();
            
            String fieldName = (String) row.get("Field");
            String fieldType = (String) row.get("Type");
            String comment = (String) row.get("Comment");
            
            column.put("columnName", fieldName);
            column.put("propertyName", convertToCamelCase(fieldName));
            column.put("javaType", getJavaType(fieldType));
            column.put("comment", comment);
            column.put("isPrimaryKey", "PRI".equals(row.get("Key")));
            
            columns.add(column);
        }
        
        return columns;
    }

    /**
     * 将下划线命名转换为驼峰命名
     */
    private String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(c);
                }
            }
        }
        
        return result.toString();
    }

    /**
     * 将表名转换为类名
     */
    private String convertToClassName(String tableName) {
        StringBuilder result = new StringBuilder();
        boolean nextUpper = true;
        
        for (int i = 0; i < tableName.length(); i++) {
            char c = tableName.charAt(i);
            
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(c);
                }
            }
        }
        
        return result.toString();
    }

    /**
     * 将MySQL数据类型转换为Java数据类型
     */
    private String getJavaType(String mysqlType) {
        mysqlType = mysqlType.toLowerCase();
        
        if (mysqlType.contains("int")) {
            if (mysqlType.contains("bigint")) {
                return "Long";
            }
            return "Integer";
        } else if (mysqlType.contains("varchar") || mysqlType.contains("text") || mysqlType.contains("char")) {
            return "String";
        } else if (mysqlType.contains("datetime") || mysqlType.contains("timestamp")) {
            return "Date";
        } else if (mysqlType.contains("decimal") || mysqlType.contains("numeric")) {
            return "BigDecimal";
        } else if (mysqlType.contains("double")) {
            return "Double";
        } else if (mysqlType.contains("float")) {
            return "Float";
        } else if (mysqlType.contains("boolean") || mysqlType.contains("bit")) {
            return "Boolean";
        } else if (mysqlType.contains("blob")) {
            return "byte[]";
        } else {
            return "String";
        }
    }
}
