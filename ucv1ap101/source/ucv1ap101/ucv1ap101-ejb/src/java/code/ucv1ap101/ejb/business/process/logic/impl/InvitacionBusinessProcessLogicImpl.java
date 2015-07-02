/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ucv1ap101.ejb.business.process.logic.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import ucv1ap101.ejb.business.message.InvitacionAceptarMessage;
import ucv1ap101.ejb.business.message.InvitacionEnviarMessage;
import ucv1ap101.ejb.business.message.InvitacionRechazarMessage;
import ucv1ap101.ejb.business.process.InvitacionBusinessProcessLocal;
import ucv1ap101.ejb.business.process.logic.InvitacionBusinessProcessLogicLocal;
import ucv1ap101.ejb.core.mail.MailerBeanLocal;
import ucv1ap101.ejb.core.mail.NotifierBean;
import ucv1ap101.ejb.persistence.entity.Invitacion;
import ucv1ap101.ejb.persistence.entity.Sala;
import ucv1ap101.ejb.persistence.facade.EstadoInvitacionFacadeLocal;
import ucv1ap101.ejb.persistence.facade.PersonaFacadeLocal;
import ucv1ap101.ejb.persistence.facade.ReunionFacadeLocal;
import ucv1ap101.lib.base.entity.enumeration.EstadoInvitacionEnumeration;
import ucv1ap101.lib.base.util.TimeUtils;
import ucv1ap101.lib.core.app.ExcepcionAplicacion;
import ucv1ap101.lib.core.app.TLC;

/**
 * @author adalid 1.0 (template by Jorge Campins)
 */
@Stateless
public class InvitacionBusinessProcessLogicImpl
    implements InvitacionBusinessProcessLogicLocal {

    @EJB
    private ReunionFacadeLocal reunionFacade;

    @EJB
    private PersonaFacadeLocal personaFacade;

    @EJB
    private EstadoInvitacionFacadeLocal estadoInvitacionFacade;

    @EJB
    private InvitacionBusinessProcessLocal processor;

    @EJB(beanName = "InvitacionBusinessProcessLogicBean")
    private InvitacionBusinessProcessLogicLocal logician;

    @EJB
    private MailerBeanLocal mailer;

    private static final Logger logger = Logger.getLogger(NotifierBean.class);

    /**
     * SYNCHRONOUS INSTANCE PROCESS aceptar.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void aceptar(InvitacionAceptarMessage message, Invitacion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(InvitacionAceptarMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Invitacion.class.getSimpleName());
        }
        boolean rejected = !processor.aceptarAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
//      logician.aceptar(message, instance);
        instance.setEstado(estadoInvitacionFacade.find(EstadoInvitacionEnumeration.ACEPTADA.intValue()));
        instance.setFechaHoraEstado(TimeUtils.currentTimestamp());
    }

    /**
     * SYNCHRONOUS INSTANCE PROCESS enviar.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void enviar(InvitacionEnviarMessage message, Invitacion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(InvitacionEnviarMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Invitacion.class.getSimpleName());
        }
        boolean rejected = !processor.enviarAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
//      logician.enviar(message, instance);
        instance.setEstado(estadoInvitacionFacade.find(EstadoInvitacionEnumeration.ENVIADA.intValue()));
        instance.setFechaHoraEstado(TimeUtils.currentTimestamp());
        /**/
        String correo = instance.getInvitado().getCorreoElectronico();
        if (StringUtils.isNotBlank(correo)) {
            String asunto = instance.getReunion().getAsunto();
            String nombre = instance.getInvitado().getNombre();
            String agenda = instance.getReunion().getAgenda();
            Sala sala = instance.getReunion().getSala();
            String lugar = sala == null ? "Aún no confirmado" : sala.getNombre();
            String fecha = TimeUtils.defaultDateString(instance.getReunion().getFechaInicioPautada());
            String desde = TimeUtils.defaultTimeString(instance.getReunion().getHoraInicioPautada());
            String hasta = TimeUtils.defaultTimeString(instance.getReunion().getHoraFinPautada());
            String mensaje = "Estimado/a " + nombre + ", ";
            mensaje += "\n\nLe invitamos cordialmente a un evento sobre: " + asunto;
            if (agenda != null) {
                mensaje += "\n\nLa agenda del evento es la siguiente:\n\n" + agenda + "\n";
            }
            mensaje += "\n\nLugar: " + lugar;
            mensaje += "\n\nFecha: " + fecha;
            mensaje += "\n\nDesde: " + desde;
            mensaje += "\n\nHasta: " + hasta;
            mensaje += "\n\nHaga clic en este link para aceptar  la invitación: http://localhost:8080/ucv1ap101-war?cr=3&id=" + instance.getId();
            mensaje += "\n\nHaga clic en este link para rechazar la invitación: http://localhost:8080/ucv1ap101-war?cr=4&id=" + instance.getId();
            enviarCorreo(correo, asunto, StringUtils.defaultIfBlank(mensaje, asunto));
        }
    }

    /**
     * SYNCHRONOUS INSTANCE PROCESS rechazar.
     *
     * @param message
     * @param instance
     * @throws java.lang.Exception
     */
    @Override
    public void rechazar(InvitacionRechazarMessage message, Invitacion instance) throws Exception {
        if (message == null) {
            throw new IllegalArgumentException(InvitacionRechazarMessage.class.getSimpleName());
        }
        if (instance == null) {
            throw new IllegalArgumentException(Invitacion.class.getSimpleName());
        }
        boolean rejected = !processor.rechazarAllowed(message, instance);
        if (rejected) {
            throw new ExcepcionAplicacion(TLC.getBitacora().getLogString());
        }
//      logician.rechazar(message, instance);
        instance.setEstado(estadoInvitacionFacade.find(EstadoInvitacionEnumeration.RECHAZADA.intValue()));
        instance.setFechaHoraEstado(TimeUtils.currentTimestamp());
    }

    private void enviarCorreo(String address, String subject, String message) {
        try {
            mailer.sendMessage(address, subject, message);
        } catch (Exception ex) {
            logger.fatal(ex.getMessage(), ex);
        }
    }

}
