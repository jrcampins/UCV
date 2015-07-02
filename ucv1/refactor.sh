#!/bin/bash
fart() {
    if [ "$1" != "$2" ]; then
        echo regex=$1
        echo value=$2
        echo files=$3
        echo ""
        sed -i "s|${1}|${2}|g" $3
    fi
}

scriptname=$(basename "$BASH_SOURCE")
scriptpath=`cd $(dirname "$BASH_SOURCE"); pwd`
me=$scriptname
xs=$scriptpath/variables.sh
unset variables
[ -x "$xs" ] && source "$xs"
[ -z "$variables" ] && exit 100 # environment variables not set
xs=$scriptpath/variables-home.sh
[ -x "$xs" ] && source "$xs"
[ -x "$xs" ] || exit 100 # environment variables not set
echo $me refactoriza el proyecto de plantilla
read -p "ejecutar $me? (s/n): " -n 1; echo ""; REPLY=`echo $REPLY|tr '[:upper:]' '[:lower:]'`
[ "$REPLY" != "s" ] && exit 101 # cancelled by user

[ -d "$adalid_dir" ]      && cp -ru $project_source_dir/development/resources/projects/adalid.project       $adalid_dir/.project
[ -d "$GLASSFISH_HOME" ]  && cp -ru $project_source_dir/development/resources/projects/glassfish.project    $GLASSFISH_HOME/.project
[ -d "$JBOSS_HOME" ]      && cp -ru $project_source_dir/development/resources/projects/jboss.project        $JBOSS_HOME/modules/.project
[ -d "$third_party_dir" ] && cp -ru $project_source_dir/development/resources/projects/third-party.project  $third_party_dir/.project
echo ""

ows="/opt/workspace"
ogh="/opt/glassfish-4.0/glassfish"
ojh="/opt/jboss-as-7.1.1.Final"
oph="/opt/PostgreSQL/9.2"
opv="9.2-1002"
opc="jee1ap101"

nws="${workspace:-$(echo $ows)}"
ngh="${GLASSFISH_HOME:-$(echo $ogh)}"
njh="${JBOSS_HOME:-$(echo $ojh)}"
nph="${POSTGRESQL_HOME:-$(echo $oph)}"
npv="${POSTGRESQL_DRIVER_VERSION:-$(echo $opv)}"
npc=`echo $project|tr '[:upper:]' '[:lower:]'`
npc="${npc}ap101"

cd $project_source_dir/development/resources/libraries/eclipse
pwd
fart "$opv" "$npv" "*.userlibraries"

cd $project_source_dir/development/resources/libraries/netbeans/linux
pwd
fart "$ows" "$nws" "*.xml"
fart "$ogh" "$ngh" "*.xml"
fart "$ojh" "$njh" "*.xml"
fart "$oph" "$nph" "*.xml"
fart "$opv" "$npv" "*.xml"

cd $project_source_dir/meta/src/meta/proyecto
pwd
fart "$opc" "$npc" "*.java"

cd $project_source_dir/meta/src/meta/util
pwd
fart "$opc" "$npc" "*.java"

unset fart
