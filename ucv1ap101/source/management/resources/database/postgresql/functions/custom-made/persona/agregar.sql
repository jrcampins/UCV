create or replace function persona$agregar$biz(_super$ bigint, _cedula$ character varying, _nombre$ character varying, _movil$ character varying, _correo$ character varying) returns integer as $$
begin
    raise notice 'persona$agregar$biz(%, %, %, %, %)', _super$, _cedula$, _nombre$, _movil$, _correo$;
    insert
    into persona (
        id,
        cedula,
        nombre,
        telefono_movil,
        correo_electronico
    ) values (
        bigintid(),
        _cedula$,
        _nombre$,
        _movil$,
        _correo$
    );
    return 0;
end;
$$ language plpgsql;
