/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
/*
 * author: Jorge Campins
 */
drop view if exists reuniones_con_encuesta_pendiente cascade;
create view reuniones_con_encuesta_pendiente as
    select * from reunion 
    where estado = 2 -- CONVOCADA
    and reservacion = 3 -- CONFIRMADA
    and fecha_inicio_pautada < current_date 
    and fecha_hora_correo_encuesta_reunion is null 
    and fecha_hora_encuesta_reunion is null 
    order by id;
;
