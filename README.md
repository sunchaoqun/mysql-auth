# mysql-auth

# 克隆本项目

git clone https://github.com/sunchaoqun/mysql-auth

# 启动一个测试的数据库 (本文以AWS EC2为例)
docker run --name local-mysql -p3306:3306 -e MYSQL_ROOT_PASSWORD='sun1234' -v $HOME/mysql-data:/var/lib/mysql -d mysql:5.7

# 安装一个Mysql Client

sudo yum install -y https://repo.percona.com/yum/percona-release-latest.noarch.rpm
sudo yum install -y mysql-client

# 登录数据测试连通性

mysql -h 127.0.0.1 -uroot -p 

# 根据您的项目名称修改 数据库与Spring对接的参数名

python -> handleMySQL.py 第9行

"/mysqlauth/MySQLAuthApplication/spring.datasource.password"

# 程序会使用AWS的API生成随机密码，如果非AWS环境可以自行实现随机密码

python -> handleMySQL.py 第74行

# 创建一个定时任务每个5分钟执行一次，可以根据密码过期要求设置成30天或者90天

crontab -e

*/5 * * * * /home/ec2-user/mysql-auth/python/changeNewPassword.sh