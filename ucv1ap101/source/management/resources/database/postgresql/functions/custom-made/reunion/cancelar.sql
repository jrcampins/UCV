create or replace function reunion$cancelar$biz(_super$ bigint, _reunion$ bigint) returns integer as $$
declare
    _msg character varying;
    _estado_reunion RECORD;
    _estado_reservacion RECORD;
    _reunion reunion%ROWTYPE;
    _id bigint;
begin
    raise notice 'reunion$cancelar$biz(%, %)', _super$, _reunion$;
    _estado_reunion := estado_reunion$enum();
    _estado_reservacion := estado_reservacion$enum();
    if (_super$ is not null and _super$ <> 0) then
        select * into _reunion from reunion where id = _reunion$;
        if not found then
            _msg := format(gettext('no existe %s con %s = %s'), 'reunion', 'id', _reunion$);
            raise exception using message = _msg;
        end if;
        if (_reunion.reservacion in (_estado_reservacion.SOLICITADA, _estado_reservacion.CONFIRMADA)) then
            perform reunion$cancelar_reservacion_sala(null, _reunion$);
        end if;
        for _id in
            select id
            from reservacion
            where reunion = _reunion$
            and estado in (_estado_reservacion.SOLICITADA, _estado_reservacion.CONFIRMADA)
        loop
            perform reservacion$cancelar(null, _id);
        end loop;
    end if;
    update reunion set estado = _estado_reunion.CANCELADA, fecha_hora_estado_reunion = localtimestamp where id = _reunion$;
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reunion', 'id', _reunion$);
        raise exception using message = _msg;
    end if;
    return 0;
end;
$$ language plpgsql;
