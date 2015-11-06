# esb-playground
Playground ESB

The maven project will create all the resources and deploy all the artifacts using the wildfly-maven-plugin

To run this:
 - Download Wildfly http://wildfly.org/downloads/
 - Unpack Wildfly and create an admin account
 - Run wildfly as $WILDFLY_HOME/bin/standalone.sh -c standalone-full.xml
 - From project home, run 'mvn clean install'
 - Navigate to http://localhost:8080/esb-test/api/publish/productinfo/{count} where count is the number of messages to send
