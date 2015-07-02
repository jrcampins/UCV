create or replace function reservacion$rechazar$biz(_super$ bigint, _reservacion$ bigint) returns integer as $$
declare
    _msg character varying;
    _estado_reservacion RECORD;
    _reservacion reservacion%ROWTYPE;
begin
    raise notice 'reservacion$rechazar$biz(%, %)', _super$, _reservacion$;
    _estado_reservacion := estado_reservacion$enum();
    select * into _reservacion from reservacion where id = _reservacion$ and estado = _estado_reservacion.SOLICITADA;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reservacion solicitada', 'id', _reservacion$);
        raise exception using message = _msg;
    end if;
    update reservacion set estado = _estado_reservacion.RECHAZADA, fecha_hora_estado = localtimestamp
    where id = _reservacion$ and estado = _estado_reservacion.SOLICITADA;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reservacion solicitada', 'id', _reservacion$);
        raise exception using message = _msg;
    end if;
    perform reunion$rechazar_reservacion_sala(null, _reservacion.reunion);
    return 0;
end;
$$ language plpgsql;
