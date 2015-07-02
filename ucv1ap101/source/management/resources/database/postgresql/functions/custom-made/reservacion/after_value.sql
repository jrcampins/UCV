create or replace function reservacion$after_value(_new$ reservacion)
returns reservacion as $$
begin
    _new$.fecha_hora_inicio_reservacion := _new$.fecha_inicio_reservacion + _new$.hora_inicio_reservacion;
    _new$.fecha_hora_fin_reservacion := _new$.fecha_fin_reservacion + _new$.hora_fin_reservacion;
 return _new$;
end;
$$ language plpgsql;
