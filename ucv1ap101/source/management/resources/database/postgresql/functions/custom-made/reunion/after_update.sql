create or replace function reunion$after_update_row$100() returns trigger as $$
declare
    _sin_cambio boolean;
begin
    _sin_cambio := new.asistentes = old.asistentes
        and new.tipo_sala_deseada = old.tipo_sala_deseada
        and new.fecha_hora_inicio_deseada = old.fecha_hora_inicio_deseada
        and new.fecha_hora_fin_deseada = old.fecha_hora_fin_deseada;
    if _sin_cambio is true then
    else
        perform reunion$actualizar_lista_salas_disponibles(null, new.id);
    end if;
    return null;
end;
$$ language plpgsql;
