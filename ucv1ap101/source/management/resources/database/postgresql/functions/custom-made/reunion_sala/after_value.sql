create or replace function reunion_sala$after_value(_new$ reunion_sala)
returns reunion_sala as $$
begin
    _new$.fecha_hora_inicio_disponibilidad := _new$.fecha_inicio_disponibilidad + _new$.hora_inicio_disponibilidad;
    _new$.fecha_hora_fin_disponibilidad := _new$.fecha_fin_disponibilidad + _new$.hora_fin_disponibilidad;
 return _new$;
end;
$$ language plpgsql;
