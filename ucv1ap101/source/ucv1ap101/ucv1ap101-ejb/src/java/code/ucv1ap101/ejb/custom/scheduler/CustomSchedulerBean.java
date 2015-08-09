/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package ucv1ap101.ejb.custom.scheduler;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import ucv1ap101.ejb.core.mail.MailerBeanLocal;
import ucv1ap101.ejb.persistence.entity.Reunion;
import ucv1ap101.ejb.persistence.facade.ReunionFacadeLocal;
import ucv1ap101.lib.base.enumeration.EnumTipoQuery;
import ucv1ap101.lib.base.util.TimeUtils;

/**
 * @author Jorge Campins
 */
@Stateless
public class CustomSchedulerBean implements CustomSchedulerLocal {

    @EJB
    private MailerBeanLocal mailer;

    @EJB
    private ReunionFacadeLocal reunionFacade;

    private static final Logger logger = Logger.getLogger(CustomSchedulerBean.class);

    private static final String REUNIONES_CON_ENCUESTA_PENDIENTE = "select * from reuniones_con_encuesta_pendiente";

    @Schedule(hour = "0") // todos los dias, a las 12:00 AM
    @Override
    public void diario() {
        logger.debug(REUNIONES_CON_ENCUESTA_PENDIENTE);
        List<Reunion> reuniones = reunionFacade.findByQuery(REUNIONES_CON_ENCUESTA_PENDIENTE, EnumTipoQuery.NATIVE, true);
        if (reuniones != null && !reuniones.isEmpty()) {
            for (Reunion reunion : reuniones) {
                enviarCorreoEncuesta(reunion);
                reunion.setFechaHoraCorreoEncuestaReunion(TimeUtils.currentTimestamp());
            }
        }
        reunionFacade.flush();

    }

    private void enviarCorreoEncuesta(Reunion reunion) {
        String correo = reunion.getOrganizador().getCorreoElectronico();
        logger.debug(reunion + "/" + correo);
        if (StringUtils.isNotBlank(correo)) {
            String asunto = reunion.getAsunto();
            String nombre = reunion.getOrganizador().getNombreUsuario();
            String mensaje = "Estimado/a " + nombre + ", ";
            mensaje += "\n\nLe invitamos cordialmente a llenar la encuesta de satisfaccion.";
            mensaje += "\n\nHaga clic en este link para ir a la aplicación: http://localhost:8080/ucv1ap101-war?id=" + reunion.getId();
            enviarCorreo(correo, asunto, StringUtils.defaultIfBlank(mensaje, asunto));
        }
    }

    private void enviarCorreo(String address, String subject, String message) {
        logger.debug("address=" + address);
        logger.debug("subject=" + subject);
        logger.debug("message=" + message);
        try {
            mailer.sendMessage(address, subject, message);
        } catch (Exception ex) {
            logger.fatal(ex.getMessage(), ex);
        }
    }

}
