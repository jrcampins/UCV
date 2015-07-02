#!/bin/bash
GLASSFISH_HOME=/opt/glassfish-4.0/glassfish
JBOSS_HOME=/opt/jboss-as-7.1.1.Final
POSTGRESQL_HOME=/opt/PostgreSQL/9.2
POSTGRESQL_DRIVER_VERSION=9.2-1002
# se debe utilizar el driver 9.2-1002 con PostgreSQL 9.2 y con PostgreSQL 9.3
# ya que en algunos casos el driver 9.3-1102 produce un java.sql.SQLException
