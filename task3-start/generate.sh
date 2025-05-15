#!/bin/bash

# 检查是否提供了参数
if [ $# -eq 0 ]; then
    echo "用法: ./generate.sh <type> [--temp]"
    echo "  type: 指定要生成的代码类型"
    echo "    - entity: 生成实体类"
    echo "    - mapper: 生成Mapper接口和XML"
    echo "    - mappertest: 生成Mapper测试类"
    echo "    - all: 生成所有代码"
    echo "  --temp: 可选参数，生成到临时文件而不是实际目录"
    exit 1
fi

# 运行代码生成器，禁用消息消费者和web服务器
mvn spring-boot:run \
    -Dspring-boot.run.main-class=com.anycompany.demo.jumping.generator.cli.GeneratorCli \
    -Dspring-boot.run.jvmArguments="-Dapp.message.consumer.enabled=false -Dspring.main.web-application-type=none" \
    -Dspring-boot.run.arguments="$*"
