create or replace function reunion$actualizar_lista_salas_disponibles$biz(_super$ bigint, _reunion$ bigint) returns integer as $$
declare
    _msg character varying;
--  _log rastro_proceso%ROWTYPE;
    _estado_reservacion RECORD;
    _reunion reunion%ROWTYPE;
    _sala sala%ROWTYPE;
    _dow integer;
    _dia integer;
    _mes integer;
    _sabado  boolean;
    _domingo boolean;
    _feriado boolean;
begin
    raise notice 'reunion$actualizar_lista_salas_disponibles$biz(%, %)', _super$, _reunion$;
    _estado_reservacion := estado_reservacion$enum();
--  select * into _log from rastro_proceso_temporal;
--  if not found then
--      _msg := format(gettext('no existe %s con %s = %s'), 'rastro de proceso temporal', 'id', pg_backend_pid());
--      raise exception using message = _msg;
--  end if;
    select * into _reunion from reunion where id = _reunion$;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reunion', 'id', _reunion$);
        raise exception using message = _msg;
    end if;
    _dow := extract(dow from _reunion.fecha_inicio_deseada);
    _dia := extract(day from _reunion.fecha_inicio_deseada);
    _mes := extract(month from _reunion.fecha_inicio_deseada);
    _sabado  := (_dow = 6);
    _domingo := (_dow = 0);
    _feriado :=
        case when exists
                (
                select 1 
                from feriado
                where fecha = _reunion.fecha_inicio_deseada
                or (recurrente is true and extract(day from fecha) = _dia and extract(month from fecha) = _mes)
                )
            then true
            else false
            end;
    delete from reunion_sala where reunion = _reunion.id;
    insert into reunion_sala
        (
        id,
        reunion,
        sala,
        capacidad,
        fecha_inicio_disponibilidad, 
        hora_inicio_disponibilidad, 
        fecha_fin_disponibilidad,
        hora_fin_disponibilidad
        )
    select
        bigintid(),
        _reunion.id,
        sala.id,
        sala.capacidad,
        _reunion.fecha_inicio_deseada,
        _reunion.hora_inicio_deseada,
        _reunion.fecha_fin_deseada,
        _reunion.hora_fin_deseada
    from sala
    where capacidad >= _reunion.asistentes
    and tipo = _reunion.tipo_sala_deseada
    and hora_inicio_servicio <= _reunion.hora_inicio_deseada
    and hora_fin_servicio >= _reunion.hora_fin_deseada
    and (_sabado  is false or disponible_sabado  is true)
    and (_domingo is false or disponible_domingo is true)
    and (_feriado is false or disponible_feriado is true)
    and sala.id not in
        (        
        select distinct sala
        from reservacion
        where reunion <> _reunion.id
        and estado = _estado_reservacion.CONFIRMADA
        and fecha_hora_inicio_reservacion < _reunion.fecha_hora_fin_deseada
        and fecha_hora_fin_reservacion > _reunion.fecha_hora_inicio_deseada
        )
    order by sala.id;
    return 0;
end;
$$ language plpgsql;
