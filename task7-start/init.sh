export SQS_ENDPOINT=$(aws sqs get-queue-url --queue-name jumping-queue --region us-east-1 --query 'QueueUrl' --output text)
echo "SQS: $SQS_ENDPOINT"
export MYSQL_ENDPOINT=$(aws rds describe-db-clusters --db-cluster-identifier jumping-aurora-cluster --region us-east-1 --query 'DBClusters[0].Endpoint' --output text)
echo "MySQL: $MYSQL_ENDPOINT"
export REDIS_ENDPOINT=$(aws elasticache describe-replication-groups --replication-group-id jumping-valkey --region us-east-1 --query 'ReplicationGroups[0].NodeGroups[0].PrimaryEndpoint.Address' --output text)
echo "Redis: $REDIS_ENDPOINT"

export MYSQL_PWD=jumping123
mysql -h $MYSQL_ENDPOINT -u admin -e "DROP DATABASE IF EXISTS demo;"
mysql -h $MYSQL_ENDPOINT -u admin -e "CREATE DATABASE demo;"
mysql -h $MYSQL_ENDPOINT -u admin demo < ./src/main/resources/db/user.sql
mysql -h $MYSQL_ENDPOINT -u admin demo < ./src/main/resources/db/feedback.sql
mysql -h $MYSQL_ENDPOINT -u admin demo < ./src/main/resources/db/init-schema.sql
mysql -h $MYSQL_ENDPOINT -u admin demo < ./src/main/resources/db/gamedata.sql

cp ./src/main/resources/application.yml.template ./src/main/resources/application.yml

sed -i "s|url: #jdbc-url|url: jdbc:mysql://${MYSQL_ENDPOINT}:3306/demo?useSSL=false\&serverTimezone=Asia/Shanghai\&characterEncoding=utf-8\&allowPublicKeyRetrieval=true|g" ./src/main/resources/application.yml
sed -i "s|username: #username|username: admin|g" ./src/main/resources/application.yml
sed -i "s|password: #password|password: jumping123|g" ./src/main/resources/application.yml
sed -i "s|host: #redis-endpoint|host: ${REDIS_ENDPOINT}|g" ./src/main/resources/application.yml
sed -i "s|port: #redis-port|port: 6379|g" ./src/main/resources/application.yml
sed -i "s|endpoint: #sqs-endpoint|endpoint: ${SQS_ENDPOINT}|g" ./src/main/resources/application.yml
code-server ./src/main/resources/application.yml

mkdir -p ./.amazonq/rules && cat > ./.amazonq/rules/rule.md << 'EOF'
# 规则

## 响应
1. 使用中文进行响应。

## 代码编写
1. 阅读README了解项目信息
2. 编写代码之前，你会阅读一次specification，找出需要遵守的规范内容，供代码产生时候使用。
3. 尽量不要修改不相关的代码
EOF
