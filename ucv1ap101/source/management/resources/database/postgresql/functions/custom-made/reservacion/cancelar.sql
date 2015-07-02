create or replace function reservacion$cancelar$biz(_super$ bigint, _reservacion$ bigint) returns integer as $$
declare
    _msg character varying;
    _estado_reservacion RECORD;
    _reservacion reservacion%ROWTYPE;
begin
    raise notice 'reservacion$cancelar$biz(%, %)', _super$, _reservacion$;
    _estado_reservacion := estado_reservacion$enum();
    select * into _reservacion from reservacion where id = _reservacion$ and estado in (_estado_reservacion.SOLICITADA, _estado_reservacion.CONFIRMADA);
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reservacion solicitada o confirmada', 'id', _reservacion$);
        raise exception using message = _msg;
    end if;
    update reservacion set estado = _estado_reservacion.CANCELADA, fecha_hora_estado = localtimestamp
    where id = _reservacion$ and estado in (_estado_reservacion.SOLICITADA, _estado_reservacion.CONFIRMADA);
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reservacion solicitada o confirmada', 'id', _reservacion$);
        raise exception using message = _msg;
    end if;
    if (_super$ is not null and _super$ <> 0) then
        perform reunion$cancelar_reservacion_sala(null, _reservacion.reunion);
    end if;
    return 0;
end;
$$ language plpgsql;
