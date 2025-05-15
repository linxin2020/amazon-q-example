package com.anycompany.demo.jumping.generator.cli;

import com.anycompany.demo.jumping.generator.core.EntityGenerator;
import com.anycompany.demo.jumping.generator.core.MapperGenerator;
import com.anycompany.demo.jumping.generator.core.MapperTestGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 代码生成器命令行工具
 * 可以通过命令行参数指定要生成的代码类型
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.anycompany.demo.jumping")
public class GeneratorCli implements CommandLineRunner {

    @Autowired
    private EntityGenerator entityGenerator;

    @Autowired
    private MapperGenerator mapperGenerator;

    @Autowired
    private MapperTestGenerator mapperTestGenerator;

    public static void main(String[] args) {
        SpringApplication.run(GeneratorCli.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            // 当没有参数时不显示使用说明，直接返回
            return;
        }

        boolean useTempFile = false;
        String type = args[0].toLowerCase();

        // 检查是否有 --temp 参数
        for (String arg : args) {
            if ("--temp".equals(arg)) {
                useTempFile = true;
                break;
            }
        }

        switch (type) {
            case "entity":
                System.out.println("生成实体类...");
                entityGenerator.generateEntities(useTempFile);
                break;
            case "mapper":
                System.out.println("生成Mapper接口和XML...");
                mapperGenerator.generateMappers(useTempFile);
                break;
            case "mappertest":
                System.out.println("生成Mapper测试类...");
                mapperTestGenerator.generateMapperTests(useTempFile);
                break;
            case "all":
                System.out.println("生成所有代码...");
                entityGenerator.generateEntities(useTempFile);
                mapperGenerator.generateMappers(useTempFile);
                mapperTestGenerator.generateMapperTests(useTempFile);
                break;
            default:
                System.out.println("不支持的生成类型: " + type);
                printUsage();
                break;
        }

        // 执行完毕后退出应用
        System.exit(0);
    }

    private void printUsage() {
        System.out.println("用法: mvn spring-boot:run -Dspring-boot.run.arguments=\"<type> [--temp]\"");
        System.out.println("  type: 指定要生成的代码类型");
        System.out.println("    - entity: 生成实体类");
        System.out.println("    - mapper: 生成Mapper接口和XML");
        System.out.println("    - mappertest: 生成Mapper测试类");
        System.out.println("    - all: 生成所有代码");
        System.out.println("  --temp: 可选参数，生成到临时文件而不是实际目录");
    }
}
