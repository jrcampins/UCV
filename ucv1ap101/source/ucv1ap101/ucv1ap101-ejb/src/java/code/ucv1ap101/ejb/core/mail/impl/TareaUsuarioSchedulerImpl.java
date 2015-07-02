/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucv1ap101.ejb.core.mail.impl;

import java.util.Date;
import javax.ejb.Stateless;
import ucv1ap101.ejb.core.mail.TareaUsuarioSchedulerLocal;
import ucv1ap101.ejb.persistence.entity.TareaUsuario;

/**
 * @author Jorge Campins
 */
@Stateless
public class TareaUsuarioSchedulerImpl
    implements TareaUsuarioSchedulerLocal {

    @Override
    public Date getProximoAdvertirAsignar(TareaUsuario tarea) {
//      Date fechaHoraActual = new Date();
//      return DateUtils.addMinutes(fechaHoraActual, 1);
        return tarea.getProximoAdvertirAsignar();
    }

    @Override
    public Date getProximoAdvertirFinalizar(TareaUsuario tarea) {
//      Date fechaHoraActual = new Date();
//      return DateUtils.addMinutes(fechaHoraActual, 2);
        return tarea.getProximoAdvertirFinalizar();
    }

    @Override
    public Date getProximoEscalarAsignar(TareaUsuario tarea) {
//      tarea.setSupervisorSuperior(null);
//      Date fechaHoraActual = new Date();
//      return DateUtils.addMinutes(fechaHoraActual, 3);
        return tarea.getProximoEscalarAsignar();
    }

    @Override
    public Date getProximoEscalarFinalizar(TareaUsuario tarea) {
//      tarea.setSupervisorSuperior(null);
//      Date fechaHoraActual = new Date();
//      return DateUtils.addMinutes(fechaHoraActual, 4);
        return tarea.getProximoEscalarFinalizar();
    }

}
