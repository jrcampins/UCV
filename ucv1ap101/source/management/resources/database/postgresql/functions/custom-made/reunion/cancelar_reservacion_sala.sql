create or replace function reunion$cancelar_reservacion_sala$biz(_super$ bigint, _reunion$ bigint) returns integer as $$
declare
    _msg character varying;
    _estado_reservacion RECORD;
    _id bigint;
begin
    raise notice 'reunion$cancelar_reservacion_sala$biz(%, %)', _super$, _reunion$;
    _estado_reservacion := estado_reservacion$enum();
    update reunion set reservacion = _estado_reservacion.CANCELADA, fecha_hora_estado_reservacion = localtimestamp
    where id = _reunion$ and reservacion in (_estado_reservacion.SOLICITADA, _estado_reservacion.CONFIRMADA);
    if not found then
        _msg := format(gettext('no existe %s con %s = %s'), 'reunion con reservacion solicitada o confirmada', 'id', _reunion$);
        raise exception using message = _msg;
    end if;
    if (_super$ is not null and _super$ <> 0) then
        for _id in
            select id
            from reservacion
            where reunion = _reunion$
            and estado in (_estado_reservacion.SOLICITADA, _estado_reservacion.CONFIRMADA)
        loop
            perform reservacion$cancelar(null, _id);
        end loop;
    end if;
    return 0;
end;
$$ language plpgsql;
