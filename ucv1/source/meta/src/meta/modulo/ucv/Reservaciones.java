/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.modulo.ucv;

import adalid.core.Project;
import adalid.core.annotations.ProjectModule;
import meta.entidad.ucv.Equipo;
import meta.entidad.ucv.Feriado;
import meta.entidad.ucv.Invitacion;
import meta.entidad.ucv.Persona;
import meta.entidad.ucv.Refrigerio;
import meta.entidad.ucv.Reservacion;
import meta.entidad.ucv.Reunion;
import meta.entidad.ucv.ReunionEquipo;
import meta.entidad.ucv.ReunionRefrigerio;
import meta.entidad.ucv.ReunionSala;
import meta.entidad.ucv.Sala;
import meta.paquete.ucv.RegistroAsociacionReunion;

/**
 * @author Jorge Campins
 */
@ProjectModule(helpFile = "/help/reservaciones.pdf")
public class Reservaciones extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Reservación de Salas");
        setDefaultDescription("Módulo de Reservacion de Salas");
    }

    Feriado diaFeriado;

    Equipo equipo;

    Invitacion invitacion;

    Persona persona;

    Refrigerio refrigerio;

    Reservacion reservacionSala;

    Reunion reunion;

    ReunionEquipo reunionEquipo;

    ReunionRefrigerio reunionRefrigerio;

    ReunionSala reunionSala;

    Sala sala;

    RegistroAsociacionReunion asociacionReunion;

}
