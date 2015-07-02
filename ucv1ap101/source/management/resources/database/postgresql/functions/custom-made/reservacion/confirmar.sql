create or replace function reservacion$confirmar$biz(_super$ bigint, _reservacion$ bigint, _rechazar$ integer) returns integer as $$
declare
    _msg character varying;
    _estado_reservacion RECORD;
    _reservacion reservacion%ROWTYPE;
    _reservaciones_por_rechazar integer;
    _id bigint;
begin
    raise notice 'reservacion$confirmar$biz(%, %, %)', _super$, _reservacion$, _rechazar$;
    _estado_reservacion := estado_reservacion$enum();
    select * into _reservacion from reservacion where id = _reservacion$ and estado = _estado_reservacion.SOLICITADA;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reservacion solicitada', 'id', _reservacion$);
        raise exception using message = _msg;
    end if;
    select count(*)
    into _reservaciones_por_rechazar
    from reservacion
    where sala = _reservacion.sala
    and estado = _estado_reservacion.SOLICITADA
    and fecha_hora_inicio_reservacion < _reservacion.fecha_hora_fin_reservacion
    and fecha_hora_fin_reservacion > _reservacion.fecha_hora_inicio_reservacion
    and reunion <> _reservacion.reunion;
    _reservaciones_por_rechazar := coalesce(_reservaciones_por_rechazar, 0);
    if (_reservaciones_por_rechazar <> coalesce(_rechazar$, 0)) then
        if (_reservaciones_por_rechazar = 1) then
            _msg := gettext('se debe rechazar una reunion');
        else
            _msg := format(gettext('se deben rechazar %s reuniones'), _reservaciones_por_rechazar);
        end if;
        raise exception using message = _msg;
    end if;
    update reservacion set estado = _estado_reservacion.CONFIRMADA, fecha_hora_estado = localtimestamp
    where id = _reservacion$ and estado = _estado_reservacion.SOLICITADA;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reservacion solicitada', 'id', _reservacion$);
        raise exception using message = _msg;
    end if;
    perform reunion$confirmar_reservacion_sala(null, _reservacion.reunion);
    for _id in
        select id
        from reservacion
        where sala = _reservacion.sala
        and estado = _estado_reservacion.SOLICITADA
        and fecha_hora_inicio_reservacion < _reservacion.fecha_hora_fin_reservacion
        and fecha_hora_fin_reservacion > _reservacion.fecha_hora_inicio_reservacion
        and reunion <> _reservacion.reunion
    loop
        perform reservacion$rechazar(null, _id);
    end loop;
    return 0;
end;
$$ language plpgsql;
