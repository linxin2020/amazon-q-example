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
 * MyBatis MapperTest生成器
 * 根据数据库表结构自动生成Mapper测试类
 */
@Component
public class MapperTestGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String MAPPER_TEST_TEMPLATE_PATH = "templates/mapper-test.vm";
    private static final String MAPPER_TEST_OUTPUT_DIR = GeneratorConstants.MAPPER_TEST_OUTPUT_DIR;
    private static final String PACKAGE_NAME = GeneratorConstants.MAPPER_PACKAGE_NAME;
    private static final String MODEL_PACKAGE_NAME = GeneratorConstants.MODEL_PACKAGE_NAME;
    private static final String TABLE_PREFIX = GeneratorConstants.TABLE_PREFIX;

    /**
     * 生成MapperTest类
     * @param useTempFile 是否使用临时文件（true用于测试，false用于生产）
     */
    public void generateMapperTests(boolean useTempFile) {
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
                String mapperTestName = className + "MapperTest";
                String entityVar = className.substring(0, 1).toLowerCase() + className.substring(1);
                
                // 获取主键列
                Map<String, Object> primaryKey = getPrimaryKey(columns);
                if (primaryKey == null) {
                    System.out.println("警告: 表 " + tableName + " 没有主键，跳过生成MapperTest");
                    continue;
                }
                
                // 标记需要生成findBy方法的字段
                markFindByFields(columns);
                
                // 为每个列添加表名，用于hasStatusField方法判断
                for (Map<String, Object> column : columns) {
                    column.put("tableName", tableName);
                }
                
                // 检查是否有status字段
                boolean hasStatusField = hasStatusField(columns);
                
                // 创建Velocity上下文
                VelocityContext context = new VelocityContext();
                context.put("packageName", PACKAGE_NAME);
                context.put("modelPackageName", MODEL_PACKAGE_NAME);
                context.put("className", className);
                context.put("entityVar", entityVar);
                context.put("tableName", tableName);
                context.put("columns", columns);
                context.put("primaryKey", primaryKey);
                context.put("tableComment", getTableComment(tableName));
                context.put("hasStatusField", hasStatusField);
                
                // 生成MapperTest类
                generateMapperTestClass(velocityEngine, context, mapperTestName, useTempFile);
            }
            
            System.out.println("MapperTest生成完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成MapperTest类
     */
    private void generateMapperTestClass(VelocityEngine velocityEngine, VelocityContext context, String mapperTestName, boolean useTempFile) throws Exception {
        // 获取模板
        Template template = velocityEngine.getTemplate(MAPPER_TEST_TEMPLATE_PATH, "UTF-8");
        
        // 渲染模板
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String content = writer.toString();
        
        // 输出到文件
        String outputPath;
        if (useTempFile) {
            // 使用临时文件
            Path tempFile = Files.createTempFile(mapperTestName, ".java");
            outputPath = tempFile.toString();
            System.out.println("Generated temp MapperTest file: " + outputPath);
        } else {
            // 使用实际输出路径
            outputPath = MAPPER_TEST_OUTPUT_DIR + mapperTestName + ".java";
            // 确保目录存在
            File dir = new File(MAPPER_TEST_OUTPUT_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        
        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(content);
        }
        
        System.out.println("Generated MapperTest for table: " + context.get("tableName") + " -> " + mapperTestName + ".java");
    }

    /**
     * 获取数据库中的所有表
     */
    private List<String> getTables() {
        String sql = "SHOW TABLES";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    /**
     * 获取表的所有列信息
     * @param tableName 表名
     * @return 列信息列表
     */
    private List<Map<String, Object>> getTableColumns(String tableName) {
        List<Map<String, Object>> columns = new ArrayList<>();
        
        // 查询表的所有列
        String sql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT, " +
                "COLUMN_KEY, IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE " +
                "FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? " +
                "ORDER BY ORDINAL_POSITION";
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, tableName);
        
        // 获取表的唯一索引信息
        Map<String, Boolean> uniqueColumns = getUniqueColumns(tableName);
        
        for (Map<String, Object> row : rows) {
            String columnName = (String) row.get("COLUMN_NAME");
            String dataType = (String) row.get("DATA_TYPE");
            String columnComment = (String) row.get("COLUMN_COMMENT");
            String columnKey = (String) row.get("COLUMN_KEY");
            String isNullable = (String) row.get("IS_NULLABLE");
            
            // 处理字段长度
            Object charLength = row.get("CHARACTER_MAXIMUM_LENGTH");
            Object numPrecision = row.get("NUMERIC_PRECISION");
            Object numScale = row.get("NUMERIC_SCALE");
            
            String length = "";
            if (charLength != null) {
                length = charLength.toString();
            } else if (numPrecision != null) {
                length = numPrecision.toString();
                if (numScale != null && !numScale.toString().equals("0")) {
                    length += "," + numScale.toString();
                }
            }
            
            // 转换为Java类型
            String javaType = getJavaType(dataType, length);
            
            // 转换为Java属性名
            String propertyName = convertToPropertyName(columnName);
            
            // 判断是否为主键
            boolean isPrimaryKey = "PRI".equals(columnKey);
            
            // 判断是否为唯一索引
            boolean isUnique = uniqueColumns.getOrDefault(columnName, false);
            
            Map<String, Object> column = new HashMap<>();
            column.put("columnName", columnName);
            column.put("dataType", dataType);
            column.put("columnComment", columnComment);
            column.put("javaType", javaType);
            column.put("propertyName", propertyName);
            column.put("isPrimaryKey", isPrimaryKey);
            column.put("isNullable", "YES".equals(isNullable));
            column.put("length", length);
            column.put("isUnique", isUnique);
            column.put("tableName", tableName); // 添加表名，用于hasStatusField方法判断
            
            columns.add(column);
        }
        
        return columns;
    }
    
    /**
     * 获取表的唯一索引字段
     * @param tableName 表名
     * @return 唯一索引字段映射
     */
    private Map<String, Boolean> getUniqueColumns(String tableName) {
        Map<String, Boolean> uniqueColumns = new HashMap<>();
        
        // 查询表的唯一索引
        String sql = "SELECT COLUMN_NAME, INDEX_NAME " +
                "FROM INFORMATION_SCHEMA.STATISTICS " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? " +
                "AND NON_UNIQUE = 0 AND INDEX_NAME != 'PRIMARY'";
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, tableName);
        
        for (Map<String, Object> row : rows) {
            String columnName = (String) row.get("COLUMN_NAME");
            String indexName = (String) row.get("INDEX_NAME");
            uniqueColumns.put(columnName, true);
            System.out.println("表 " + tableName + " 中发现唯一索引: " + indexName + " -> 字段: " + columnName);
        }
        
        return uniqueColumns;
    }

    /**
     * 标记需要生成getBy方法和findBy方法的字段
     * @param columns 表字段列表
     */
    private void markFindByFields(List<Map<String, Object>> columns) {
        for (Map<String, Object> column : columns) {
            String columnName = (String) column.get("columnName");
            boolean needGetBy = false;
            boolean needFindBy = false;
            
            // 检查是否是主键
            boolean isPrimaryKey = (Boolean) column.get("isPrimaryKey");
            if (isPrimaryKey) {
                continue; // 主键不需要生成getBy方法，因为已经有getById
            }
            
            // 检查是否是唯一索引 - 唯一索引使用getByXXX方法
            boolean isUnique = (Boolean) column.getOrDefault("isUnique", false);
            if (isUnique) {
                needGetBy = true;
                System.out.println("发现唯一索引字段: " + columnName + "，将生成getBy" + convertToPropertyName(columnName) + "方法");
            }
            
            // 检查是否是特殊支持的字段
            if (GeneratorConstants.SPECIAL_SUPPORTED_FIND_BY_FIELDS.contains(columnName)) {
                // 如果是唯一索引，只需要getBy方法，不需要findBy方法
                if (!isUnique) {
                    needFindBy = true;
                    System.out.println("发现特殊支持字段: " + columnName + "，将生成findBy" + convertToPropertyName(columnName) + "方法");
                } else {
                    System.out.println("发现特殊支持字段且为唯一索引: " + columnName + "，只生成getBy" + convertToPropertyName(columnName) + "方法");
                }
            }
            
            column.put("needGetBy", needGetBy);
            column.put("needFindBy", needFindBy);
        }
    }

    /**
     * 检查是否有status字段
     */
    private boolean hasStatusField(List<Map<String, Object>> columns) {
        for (Map<String, Object> column : columns) {
            String columnName = (String) column.get("columnName");
            if ("status".equals(columnName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取表的主键列
     */
    private Map<String, Object> getPrimaryKey(List<Map<String, Object>> columns) {
        for (Map<String, Object> column : columns) {
            if ((Boolean) column.get("isPrimaryKey")) {
                return column;
            }
        }
        return null;
    }

    /**
     * 获取表注释
     */
    private String getTableComment(String tableName) {
        String sql = "SELECT table_comment FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ?";
        String comment = jdbcTemplate.queryForObject(sql, String.class, tableName);
        return comment != null && !comment.isEmpty() ? comment : tableName.substring(TABLE_PREFIX.length());
    }

    /**
     * 将下划线命名转换为驼峰命名（属性名）
     */
    private String convertToPropertyName(String name) {
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
                    if (i == 0) {
                        result.append(Character.toLowerCase(c));
                    } else {
                        result.append(c);
                    }
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
    private String getJavaType(String dataType, String length) {
        dataType = dataType.toLowerCase();
        
        if (dataType.contains("int")) {
            if (dataType.equals("bigint")) {
                return "Long";
            }
            return "Integer";
        } else if (dataType.equals("varchar") || dataType.equals("char") || dataType.equals("text") || dataType.equals("mediumtext") || dataType.equals("longtext")) {
            return "String";
        } else if (dataType.equals("datetime") || dataType.equals("timestamp")) {
            return "Date";
        } else if (dataType.equals("decimal")) {
            return "BigDecimal";
        } else if (dataType.equals("double")) {
            return "Double";
        } else if (dataType.equals("float")) {
            return "Float";
        } else if (dataType.equals("boolean") || dataType.equals("bit") || (dataType.equals("tinyint") && length.equals("1"))) {
            return "Boolean";
        } else if (dataType.equals("blob") || dataType.equals("mediumblob") || dataType.equals("longblob")) {
            return "byte[]";
        } else if (dataType.equals("date")) {
            return "Date";
        } else if (dataType.equals("time")) {
            return "Time";
        } else {
            return "String";
        }
    }
}
