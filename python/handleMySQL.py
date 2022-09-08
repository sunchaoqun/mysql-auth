import pymysql
import boto3
import json
import base64
from botocore.exceptions import ClientError

region = "ap-southeast-1"
host="%"
passwordKey="/mysqlauth/MySQLAuthApplication/spring.datasource.password"

def generate_random_password(password_length):
    """Generates a random password"""
    secrets_client = boto3.client("secretsmanager", region_name=region)
    response = secrets_client.get_random_password(PasswordLength=password_length, ExcludeCharacters="\"'@/\\")
    return response

# Define a method to create a database connection
def getDatabaseConnection(ipaddress, usr, passwd, charset, curtype):
    sqlCon  = pymysql.connect(host=ipaddress, user=usr, password=passwd, charset=charset, cursorclass=curtype);
    return sqlCon

# Define a method to create MySQL users
def createUser(cursor, userName, password,
               querynum=0, 
               updatenum=0, 
               connection_num=0):
    try:
        sqlCreateUser = "CREATE USER '%s'@'%s' IDENTIFIED BY '%s';"%(userName, host, password)
        cursor.execute(sqlCreateUser)
    except Exception as Ex:
        print("Error creating MySQL User: %s"%(Ex))

def alterUser(cursor, userName, password,
               querynum=0, 
               updatenum=0, 
               connection_num=0):
    try:
        sqlAlterUser = "ALTER USER  '%s'@'%s' IDENTIFIED BY '%s';"%(userName, host, password)
        cursor.execute(sqlAlterUser)
    except Exception as Ex:
        print("Error creating MySQL User: %s"%(Ex))

def dropUser(cursor, userName):
    try:
        sqlDropUser = "DROP USER '%s'@'%s';"%(userName,host)
        cursor.execute(sqlDropUser)
    except Exception as Ex:
        print("Error Deleting MySQL User: %s"%(Ex))

ssm = boto3.client('ssm', region_name=region)
parameter = ssm.get_parameter(Name=passwordKey, WithDecryption=True)
print(parameter['Parameter']['Value'])

password = parameter['Parameter']['Value']

# parameterHello = ssm.get_parameter(Name='hello', WithDecryption=True)

# print(parameterHello)
 
# Connection parameters and access credentials
ipaddress   = "127.0.0.1"  # MySQL server is running on local machine
usr         = "root"       
passwd      = password          
charset     = "utf8mb4"     
curtype    = pymysql.cursors.DictCursor    

mySQLConnection = getDatabaseConnection(ipaddress, usr, passwd, charset, curtype)
mySQLCursor     = mySQLConnection.cursor()

# createUser(mySQLCursor, "test","sun1234")

# dropUser(mySQLCursor,"root")

newPassword=generate_random_password(32)['RandomPassword']

print(newPassword)

alterUser(mySQLCursor, "root", newPassword)
# createUser(mySQLCursor, "test", "sun1234444")

ssm.put_parameter(
    Name=passwordKey,
    Value=newPassword,
    Overwrite=True,
    Type='SecureString'
)

mySqlListUsers = "select host, user from mysql.user;"
mySQLCursor.execute(mySqlListUsers)

# Fetch all the rows
userList = mySQLCursor.fetchall()

# print(get_secret())

print("List of users:")
for user in userList:
    print(user)

