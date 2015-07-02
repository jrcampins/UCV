/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.paquete.ucv;

import meta.entidad.ucv.Equipo;
import meta.entidad.ucv.Persona;
import meta.entidad.ucv.Refrigerio;
import meta.paquete.base.PaqueteRegistroBase;

/**
 * @author Jorge Campins
 */
public class RegistroAsociacionReunion extends PaqueteRegistroBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("AsociacionReunion");
        setDefaultLabel("Registro de Equipos, Personas y Refrigerios");
        setDefaultDescription("Registro de Equipos, Personas y Refrigerios");
    }

    Equipo equipo;

    Persona persona;

    Refrigerio refrigerio;

}
