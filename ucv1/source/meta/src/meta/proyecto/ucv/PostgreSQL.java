/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.ucv;

import meta.modulo.ucv.Reservaciones;
import adalid.commons.enums.LoggingLevel;
import adalid.core.annotations.ProjectModule;
import adalid.core.enums.Kleenean;
import java.util.Locale;
import meta.proyecto.base.ProyectoBase;
import meta.proyecto.comun.Auditoria;
import meta.proyecto.comun.ControlAcceso;
import meta.proyecto.comun.ControlPruebas;

/**
 * @author Jorge Campins
 */
public class PostgreSQL extends ProyectoBase {

    public static void main(String[] args) {
//      Maestro.setLocale(Locale.ENGLISH);
        PostgreSQL.setLocale(Locale.forLanguageTag("es"));
        PostgreSQL.setAlertLoggingLevel(LoggingLevel.OFF);
//      Maestro.setDetailLoggingLevel(LoggingLevel.INFO);
//      Maestro.setTrackingLoggingLevel(LoggingLevel.INFO);
        PostgreSQL maestro = new PostgreSQL();
        if (maestro.build()) {
            maestro.putEnvironmentVariable(VERSION_JAVA, "1.8.0_25");
            maestro.putEnvironmentVariable(VERSION_GLASSFISH, "4.1");
            maestro.putEnvironmentVariable(VERSION_JBOSS, "7.1.1.Final");
            maestro.putEnvironmentVariable(VERSION_POSTGRESQL, "9.3");
//          maestro.setSecurityRealmType(SecurityRealmType.LDAP);
//          maestro.setRoleBasedAccessControllerName("LDAP");
            maestro.setAlias("ucv1ap101");
            maestro.generate("postgresql");
        }
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Servicio de Sala de Reuniones");
        setDefaultDescription("Gestión del Servicio de Sala de Reuniones");
    }

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Reservaciones mod1;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    ControlAcceso modx;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Auditoria mody;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    ControlPruebas modz;

}
