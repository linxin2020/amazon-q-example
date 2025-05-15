package com.anycompany.demo.jumping.generator.common;

import java.util.Arrays;
import java.util.List;

/**
 * 代码生成器公共常量类
 */
public class GeneratorConstants {
    
    /**
     * 数据库表前缀
     */
    public static final String TABLE_PREFIX = "demo_";
    
    /**
     * 特殊情况下需要支持的findBy字段
     * 这些字段在Mapper中确实有对应的getByXxx方法，但不是主键或唯一索引
     */
    public static final List<String> SPECIAL_SUPPORTED_FIND_BY_FIELDS = Arrays.asList(
        "name",
        "status"
    );
    
    /**
     * Java类输出目录
     */
    public static final String ENTITY_OUTPUT_DIR = "src/main/java/com/anycompany/demo/jumping/model/";
    
    /**
     * Mapper接口输出目录
     */
    public static final String MAPPER_OUTPUT_DIR = "src/main/java/com/anycompany/demo/jumping/mapper/";
    
    /**
     * Mapper XML输出目录
     */
    public static final String XML_OUTPUT_DIR = "src/main/resources/com/anycompany/demo/jumping/mapper/";
    
    /**
     * MapperTest输出目录
     */
    public static final String MAPPER_TEST_OUTPUT_DIR = "src/test/java/com/anycompany/demo/jumping/mapper/";
    
    /**
     * 包名
     */
    public static final String MODEL_PACKAGE_NAME = "com.anycompany.demo.jumping.model";
    public static final String MAPPER_PACKAGE_NAME = "com.anycompany.demo.jumping.mapper";
}
