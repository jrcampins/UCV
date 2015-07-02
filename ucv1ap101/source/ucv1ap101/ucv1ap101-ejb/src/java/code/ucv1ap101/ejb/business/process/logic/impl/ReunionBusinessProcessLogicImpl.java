/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ucv1ap101.ejb.business.process.logic.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import ucv1ap101.ejb.business.message.ReunionActualizarListaSalasDisponiblesMessage;
import ucv1ap101.ejb.business.message.ReunionCancelarMessage;
import ucv1ap101.ejb.business.message.ReunionCancelarReservacionSalaMessage;
import ucv1ap101.ejb.business.message.ReunionConfirmarReservacionSalaMessage;
import ucv1ap101.ejb.business.message.ReunionConvocarMessage;
import ucv1ap101.ejb.business.message.ReunionRechazarReservacionSalaMessage;
import ucv1ap101.ejb.business.message.ReunionSolicitarReservacionSalaMessage;
import ucv1ap101.ejb.business.process.ReunionBusinessProcessLocal;
import ucv1ap101.ejb.business.process.logic.ReunionBusinessProcessLogicLocal;
import ucv1ap101.ejb.core.mail.MailerBeanLocal;
import ucv1ap101.ejb.core.mail.NotifierBean;
import ucv1ap101.ejb.persistence.entity.Invitacion;
import ucv1ap101.ejb.persistence.entity.Persona;
import ucv1ap101.ejb.persistence.entity.Reunion;
import ucv1ap101.ejb.persistence.facade.EstadoInvitacionFacadeLocal;
import ucv1ap101.ejb.persistence.facade.EstadoReservacionFacadeLocal;
import ucv1ap101.ejb.persistence.facade.EstadoReunionFacadeLocal;
import ucv1ap101.ejb.persistence.facade.InvitacionFacadeLocal;
import ucv1ap101.ejb.persistence.facade.SalaFacadeLocal;
import ucv1ap101.lib.base.entity.enumeration.EstadoInvitacionEnumeration;
import ucv1ap101.lib.base.entity.enumeration.EstadoReservacionEnumeration;
import ucv1ap101.lib.base.entity.enumeration.EstadoReunionEnumeration;
import ucv1ap101.lib.base.enumeration.EnumTipoQuery;
import ucv1ap101.lib.base.util.TimeUtils;
import ucv1ap101.lib.core.app.ExcepcionAplicacion;
import ucv1ap101.lib.core.app.TLC;

/**
 * @author Jorge Campins
 */
@Stateless
public class ReunionBusinessProcessLogicImpl
    implements ReunionBusinessProcessLogicLocal {

    @EJB
    private SalaFacadeLocal salaFacade;

    @EJB
    private EstadoReservacionFacadeLocal estadoReservacionFacade;

    @EJB
    private EstadoReunionFacadeLocal estadoReunionFacade;

    @EJB
    private EstadoInvitacionFacadeLocal estadoInvitacionFacade;

    @EJB
    private ReunionBusinessProcessLocal processor;

    @EJB
    private InvitacionFacadeLocal facade;

    @EJB(beanName = "ReunionBusinessProcessLogicBean")
    private ReunionBusinessProcessLogicLocal logician;

    @EJB
    private MailerBeanLocal mailer;

    private static final Logger logger = Logger.getLogger(NotifierBean.class);

    /**
     * SYNCHRONOUS INSTANCE PROCESS actualizarListaSalasDisponibles.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void actualizarListaSalasDisponibles(ReunionActualizarListaSalasDisponiblesMessage message, Reunion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(ReunionActualizarListaSalasDisponiblesMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Reunion.class.getSimpleName());
        }
        boolean rejected = !processor.actualizarListaSalasDisponiblesAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
        logician.actualizarListaSalasDisponibles(message, instance);
    }

    /**
     * SYNCHRONOUS INSTANCE PROCESS cancelar.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void cancelar(ReunionCancelarMessage message, Reunion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(ReunionCancelarMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Reunion.class.getSimpleName());
        }
        boolean rejected = !processor.cancelarAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
        logician.cancelar(message, instance);
    }

    /**
     * SYNCHRONOUS INSTANCE PROCESS cancelarReservacionSala.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void cancelarReservacionSala(ReunionCancelarReservacionSalaMessage message, Reunion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(ReunionCancelarReservacionSalaMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Reunion.class.getSimpleName());
        }
        boolean rejected = !processor.cancelarReservacionSalaAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
        logician.cancelarReservacionSala(message, instance);
    }

    /**
     * SYNCHRONOUS INSTANCE PROCESS confirmarReservacionSala.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void confirmarReservacionSala(ReunionConfirmarReservacionSalaMessage message, Reunion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(ReunionConfirmarReservacionSalaMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Reunion.class.getSimpleName());
        }
        boolean rejected = !processor.confirmarReservacionSalaAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
//      logician.confirmarReservacionSala(message, instance);
        instance.setReservacion(estadoReservacionFacade.find(EstadoReservacionEnumeration.CONFIRMADA.intValue()));
        instance.setFechaHoraEstadoReservacion(TimeUtils.currentTimestamp());
    }

    /**
     * SYNCHRONOUS INSTANCE PROCESS convocar.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void convocar(ReunionConvocarMessage message, Reunion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(ReunionConvocarMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Reunion.class.getSimpleName());
        }
        boolean rejected = !processor.convocarAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
//      logician.convocar(message, instance);
        instance.setEstado(estadoReunionFacade.find(EstadoReunionEnumeration.CONVOCADA.intValue()));
        instance.setFechaHoraEstadoReunion(TimeUtils.currentTimestamp());
        /**/
        String asunto = instance.getAsunto();
        String agenda = instance.getAgenda();
        enviarCorreos(instance, asunto, StringUtils.defaultIfBlank(agenda, asunto));
    }

    /**
     * SYNCHRONOUS INSTANCE PROCESS rechazarReservacionSala.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void rechazarReservacionSala(ReunionRechazarReservacionSalaMessage message, Reunion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(ReunionRechazarReservacionSalaMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Reunion.class.getSimpleName());
        }
        boolean rejected = !processor.rechazarReservacionSalaAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
//      logician.rechazarReservacionSala(message, instance);
        instance.setReservacion(estadoReservacionFacade.find(EstadoReservacionEnumeration.RECHAZADA.intValue()));
        instance.setFechaHoraEstadoReservacion(TimeUtils.currentTimestamp());
    }

    /**
     * SYNCHRONOUS INSTANCE PROCESS solicitarReservacionSala.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void solicitarReservacionSala(ReunionSolicitarReservacionSalaMessage message, Reunion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(ReunionSolicitarReservacionSalaMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Reunion.class.getSimpleName());
        }
        boolean rejected = !processor.solicitarReservacionSalaAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
//      logician.solicitarReservacionSala(message, instance);
        instance.setSala(salaFacade.find(message.getArgumentoSala()));
        instance.setFechaInicioPautada(message.getArgumentoFechaInicio());
        instance.setHoraInicioPautada(message.getArgumentoHoraInicio());
        instance.setFechaFinPautada(message.getArgumentoFechaFin());
        instance.setHoraFinPautada(message.getArgumentoHoraFin());
        instance.setReservacion(estadoReservacionFacade.find(EstadoReservacionEnumeration.SOLICITADA.intValue()));
        instance.setFechaHoraEstadoReservacion(TimeUtils.currentTimestamp());
    }

    private void enviarCorreos(Reunion instance, String subject, String message) {
        List<String> direcciones = direccionesDeCorreoDeTodosLosInvitados(instance);
        if (direcciones.isEmpty()) {
            return;
        }
        String addressList = StringUtils.join(direcciones, ",");
        try {
            mailer.sendMessage(addressList, subject, message);
        } catch (Exception ex) {
            logger.fatal(ex.getMessage(), ex);
        }
    }

    private static final String SELECT_INVITACIONES = "select * from invitacion where reunion=";

    private List<String> direccionesDeCorreoDeTodosLosInvitados(Reunion instance) {
        List<String> direcciones = new ArrayList<>();
        List<Invitacion> invitaciones = facade.findByQuery(SELECT_INVITACIONES + instance.getId(), EnumTipoQuery.NATIVE, true);
        if (invitaciones.isEmpty()) {
            return direcciones;
        }
        String correo;
        Persona invitado;
        for (Invitacion invitacion : invitaciones) {
            invitado = invitacion.getInvitado();
            if (invitado != null) {
                correo = invitado.getCorreoElectronico();
                if (StringUtils.isNotBlank(correo) && !direcciones.contains(correo)) {
                    invitacion.setEstado(estadoInvitacionFacade.find(EstadoInvitacionEnumeration.ENVIADA.intValue()));
                    invitacion.setFechaHoraEstado(TimeUtils.currentTimestamp());
                    direcciones.add(correo);
                }
            }
        }
        return direcciones;
    }

}
