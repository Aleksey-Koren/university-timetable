#!/bin/bash

# Stop on error
set -e


#start postgreSQL
echo "starting postgreSQL ... "

sudo service postgresql restart

echo "started postgreSQL"

java -jar /bin/database-checker.jar

echo "running insert data"

psql university akoren -f /tmp/init.sql 

echo "running insert data complete"

#start tomcat
echo "starting tomcat ... "

#sh /opt/tomcat/bin/catalina.sh stop

sh /opt/tomcat/bin/startup.sh; tail -f /opt/tomcat/logs/catalina.out


echo "started tomcat"

# keep the stdin
/bin/bash