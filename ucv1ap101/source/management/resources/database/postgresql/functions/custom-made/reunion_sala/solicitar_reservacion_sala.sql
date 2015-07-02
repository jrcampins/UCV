create or replace function reunion_sala$solicitar_reservacion_sala$biz(_super$ bigint, _opcion$ bigint) returns integer as $$
declare
    _msg character varying;
--  _log rastro_proceso%ROWTYPE;
    _estado_reservacion RECORD;
    _reunion_sala reunion_sala%ROWTYPE;
    _reunion reunion%ROWTYPE;
    _dow integer;
    _dia integer;
    _mes integer;
    _sabado  boolean;
    _domingo boolean;
    _feriado boolean;
    _disponible boolean;
begin
    raise notice 'reunion_sala$solicitar_reservacion_sala$biz(%, %)', _super$, _opcion$;
    _estado_reservacion := estado_reservacion$enum();
--  select * into _log from rastro_proceso_temporal;
--  if not found then
--      _msg := format(gettext('no existe %s con %s = %s'), 'rastro de proceso temporal', 'id', pg_backend_pid());
--      raise exception using message = _msg;
--  end if;
    update reunion_sala set reservacion_solicitada = true, fecha_hora_solicitud = localtimestamp where id = _opcion$;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'asociación Reunión/Sala', 'id', _opcion$);
        raise exception using message = _msg;
    end if;
    /**/
    select * into _reunion_sala from reunion_sala where id = _opcion$;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'asociación Reunión/Sala', 'id', _opcion$);
        raise exception using message = _msg;
    end if;
    /**/
    select * into _reunion from reunion where id = _reunion_sala.reunion;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reunion', 'id', _reunion_sala.reunion);
        raise exception using message = _msg;
    end if;
    /**/
    _dow := extract(dow from _reunion_sala.fecha_inicio_disponibilidad);
    _dia := extract(day from _reunion_sala.fecha_inicio_disponibilidad);
    _mes := extract(month from _reunion_sala.fecha_inicio_disponibilidad);
    _sabado  := (_dow = 6);
    _domingo := (_dow = 0);
    _feriado :=
        case when exists
                (
                select 1 
                from feriado
                where fecha = _reunion_sala.fecha_inicio_disponibilidad
                or (recurrente is true and extract(day from fecha) = _dia and extract(month from fecha) = _mes)
                )
            then true
            else false
            end;
    _disponible :=
        case when exists
            (
            select 1
            from sala
            where capacidad >= _reunion.asistentes
            and tipo = _reunion.tipo_sala_deseada
            and hora_inicio_servicio <= _reunion_sala.hora_inicio_disponibilidad
            and hora_fin_servicio >= _reunion_sala.hora_fin_disponibilidad
            and (_sabado  is false or disponible_sabado  is true)
            and (_domingo is false or disponible_domingo is true)
            and (_feriado is false or disponible_feriado is true)
            and id = _reunion_sala.sala
            and id not in
                (        
                select distinct sala
                from reservacion
                where reunion <> _reunion_sala.reunion
                and estado = _estado_reservacion.CONFIRMADA
                and fecha_hora_inicio_reservacion < _reunion_sala.fecha_fin_disponibilidad
                and fecha_hora_fin_reservacion > _reunion_sala.fecha_inicio_disponibilidad
                )
            )
            then true
            else false
            end;
    /**/
    if _disponible is false then
        _msg := gettext('la sala ya no esta disponible');
        raise exception using message = _msg;
    end if;
    /**/
    insert into reservacion
        (
        id,
        sala,
        reunion,
        asistentes,
        fecha_inicio_reservacion, 
        hora_inicio_reservacion,
        fecha_fin_reservacion, 
        hora_fin_reservacion
        )
    values
        (
        bigintid(),
        _reunion_sala.sala,
        _reunion_sala.reunion,
        _reunion.asistentes,
        _reunion_sala.fecha_inicio_disponibilidad, 
        _reunion_sala.hora_inicio_disponibilidad,
        _reunion_sala.fecha_fin_disponibilidad, 
        _reunion_sala.hora_fin_disponibilidad
        );
    /**/
    perform reunion$solicitar_reservacion_sala(null, _reunion_sala.reunion, _reunion_sala.sala,
        _reunion_sala.fecha_inicio_disponibilidad, _reunion_sala.hora_inicio_disponibilidad,
        _reunion_sala.fecha_fin_disponibilidad, _reunion_sala.hora_fin_disponibilidad);
    /**/
    return 0;
end;
$$ language plpgsql;
