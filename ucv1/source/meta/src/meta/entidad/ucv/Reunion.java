/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.ucv;

import adalid.core.AbstractPersistentEntity;
import adalid.core.ExportOperation;
import adalid.core.ProcessOperation;
import adalid.core.ReportOperation;
import adalid.core.Tab;
import adalid.core.View;
import adalid.core.ViewField;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.CharacterDataGen;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTriggers;
import adalid.core.annotations.ExportOperationClass;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.NumericDataGen;
import adalid.core.annotations.NumericField;
import adalid.core.annotations.OperationClass;
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.ReportOperationClass;
import adalid.core.annotations.SegmentProperty;
import adalid.core.annotations.TemporalDataGen;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.DataGenNumericAction;
import adalid.core.enums.DivisorRule;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.SortOption;
import adalid.core.enums.SpecialTemporalValue;
import adalid.core.enums.StandardRelationalOp;
import adalid.core.enums.ViewFieldAggregation;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Check;
import adalid.core.interfaces.Segment;
import adalid.core.interfaces.State;
import adalid.core.parameters.DateParameter;
import adalid.core.parameters.StringParameter;
import adalid.core.parameters.TimeParameter;
import adalid.core.properties.DateProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import adalid.core.properties.TimeProperty;
import adalid.core.properties.TimestampProperty;
import java.lang.reflect.Field;
import meta.entidad.comun.control.acceso.Usuario;

/**
 * Reunion Persistent Entity.
 *
 * @author Jorge
 */
//@EntityDataGen(start = 1, step = 1, stop = 100)
@EntityTableView(inserts = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class Reunion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="code templates">
    //
    // Available entity class annotation code templates:
    //
    // $abstract	Code template for an abstract class annotation
    // $allocover	Code template for a single allocation override annotation
    // $allocovers	Code template for a set of allocation override annotations
    // $console         Code template for an entity console view annotation
    // $delete          Code template for an entity delete operation annotation
    // $detail          Code template for an entity detail view annotation
    // $disval          Code template for a discriminator value annotation
    // $entity          Code template for an entity class annotation
    // $export          Code template for an entity export operation annotation
    // $inhmap          Code template for an entity inheritance mapping annotation
    // $insert          Code template for an entity insert operation annotation
    // $report          Code template for an entity report operation annotation
    // $search          Code template for an entity reference search annotation
    // $select          Code template for an entity select operation annotation
    // $table           Code template for an entity table view annotation
    // $tree            Code template for an entity tree view annotation
    // $triggers	Code template for an entity triggers annotation
    // $update          Code template for an entity update operation annotation
    //
    // Available property field code templates:
    //
    // $bigdecpro	Code template for a big decimal property field
    // $bigintpro	Code template for a big integer property field
    // $boolpro         Code template for a boolean property field
    // $bytepro         Code template for a byte property field
    // $charbizkey	Code template for a character business key property field
    // $charkey         Code template for a character key property field
    // $charpro         Code template for a character property field
    // $code            Code template for a business key property field
    // $datepro         Code template for a date property field
    // $description	Code template for a description property field
    // $doublepro	Code template for a double property field
    // $entpro          Code template for an entity reference property field
    // $filerefpro	Code template for a file reference property field
    // $floatpro	Code template for a float property field
    // $id              Code template for a long primary key property field
    // $inactive	Code template for an inactive indicator property field
    // $intpro          Code template for a integer property field
    // $longpro         Code template for a long property field
    // $many            Code template for a many-to-one entity reference property field
    // $name            Code template for a name property field
    // $number          Code template for an integer primary key property field
    // $numbizkey	Code template for a numeric business key property field
    // $numkey          Code template for a numeric key property field
    // $one             Code template for a one-to-one entity reference property field
    // $owner           Code template for an owner entity reference property field
    // $parent          Code template for a parent entity reference property field
    // $segment         Code template for a segment entity reference property field
    // $shortpro	Code template for a short property field
    // $stringpro	Code template for a string property field
    // $timepro         Code template for a time property field
    // $stamppro        Code template for a timestamp property field
    // $url             Code template for an URL property field
    // $version         Code template for a version property field
    //
    // Available property annotation code templates:
    //
    // $alloc           Code template for an allocation annotation
    // $base            Code template for a base field annotation
    // $bigdec          Code template for a big decimal field annotation
    // $casting         Code template for a casting field annotation
    // $column          Code template for a column field annotation
    // $discol          Code template for a discriminator column annotation
    // $extension	Code template for an extension annotation
    // $fileref         Code template for a file reference annotation
    // $property	Code template for a property field annotation
    // $string          Code template for a string field annotation
    // $time            Code template for a time field annotation
    // $stamp           Code template for a timestamp field annotation
    // $unique          Code template for a unique key annotation
    //
    // Available entity key field code templates:
    //
    // $key             Code template for a key field
    //
    // Available entity tab field code templates:
    //
    // $tab             Code template for a tab field
    //
    // Available entity instance field code templates:
    //
    // $instance	Code template for a instance field
    //
    // Available entity trigger field code templates:
    //
    // $trigger         Code template for a trigger field
    //
    // Available entity expression field code templates:
    //
    // $checkx          Code template for a check expression field
    // $segmentx	Code template for a segment expression field
    // $statex          Code template for a state expression field
    //
    // Available operation class code templates:
    //
    // $exportop        Code template for an export operation
    // $operation       Code template for an operation class annotation
    // $procedure       Code template for a procedure operation
    // $process         Code template for a process operation
    // $reportop        Code template for a report operation
    //
    // Available parameter field code templates:
    //
    // $bigdecpar	Code template for a big decimal parameter field
    // $bigintpar	Code template for a big integer parameter field
    // $boolpar         Code template for a boolean parameter field
    // $bytepar         Code template for a byte parameter field
    // $charpar         Code template for a character parameter field
    // $datepar         Code template for a date parameter field
    // $doublepar	Code template for a double parameter field
    // $entpar          Code template for an entity reference parameter field
    // $filerefpar	Code template for a file reference parameter field
    // $floatpar	Code template for a float parameter field
    // $insrefpar	Code template for an instance reference parameter field
    // $intpar          Code template for a integer parameter field
    // $longpar         Code template for a long parameter field
    // $shortpar	Code template for a short parameter field
    // $stringpar	Code template for a string parameter field
    // $timepar         Code template for a time parameter field
    // $stamppar        Code template for a timestamp parameter field
    //
    // Available parameter annotation code templates:
    //
    // $alloc           Code template for an allocation annotation
    // $bigdec          Code template for a big decimal field annotation
    // $fileref         Code template for a file reference annotation
    // $insref          Code template for an instance reference parameter annotation
    // $parameter	Code template for a parameter field annotation
    // $string          Code template for a string field annotation
    // $time            Code template for a time field annotation
    // $stamp           Code template for a timestamp field annotation
    //
    // Available operation transition field code templates:
    //
    // $transition	Code template for a transition field
    //
    // </editor-fold>
/**/
    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated()
    private Reunion() {
        this(null, null);
    }

    public Reunion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @CharacterDataGen(function = "util.string_codigo_entidad")
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public StringProperty codigo;

    @NameProperty
    @CharacterDataGen(function = "util.string_nombre_entidad")
    public StringProperty asunto;

    @DescriptionProperty
    @CharacterDataGen(function = "util.string_descripcion_entidad")
    public StringProperty agenda;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE)
    public TipoSala tipoSalaDeseada;

    /**
     * integer property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, heading = Kleenean.TRUE)
    @NumericDataGen(nullable = 0, max = "50")
    public IntegerProperty asistentes;

    /**
     * date property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, heading = Kleenean.FALSE)
    @TemporalDataGen(max = "1M", min = "-1M")
    public DateProperty fechaInicioDeseada;

    /**
     * time property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, heading = Kleenean.FALSE)
    @TemporalDataGen(max = "16:00:00", min = "09:00:00")
    public TimeProperty horaInicioDeseada;

    /**
     * date property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    @TemporalDataGen(max = "1M", min = "-1M")
    public TimestampProperty fechaHoraInicioDeseada;

    /**
     * date property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    @TemporalDataGen(max = "1M", min = "-1M")
    public DateProperty fechaFinDeseada;

    /**
     * time property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE)
    @TemporalDataGen(max = "16:00:00", min = "09:00:00")
    public TimeProperty horaFinDeseada;

    /**
     * date property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    @TemporalDataGen(max = "1M", min = "-1M")
    public TimestampProperty fechaHoraFinDeseada;

    /**
     * integer property field
     */
    @NumericDataGen(nullable = 50, min = "1", max = "10", action = DataGenNumericAction.ROUND, factor = "30")
    @NumericField(divisor = 15, divisorRule = DivisorRule.ROUND)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty margen;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public Sala sala;

    /**
     * date property field
     */
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    @TemporalDataGen(max = "1M", min = "-1M")
    public DateProperty fechaInicioPautada;

    /**
     * time property field
     */
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    @TemporalDataGen(max = "16:00:00", min = "09:00:00")
    public TimeProperty horaInicioPautada;

    /**
     * date property field
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    @TemporalDataGen(max = "1M", min = "-1M")
    public TimestampProperty fechaHoraInicioPautada;

    /**
     * date property field
     */
    @PropertyField(hidden = Kleenean.TRUE)
    @TemporalDataGen(max = "1M", min = "-1M")
    public DateProperty fechaFinPautada;

    /**
     * time property field
     */
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    @TemporalDataGen(max = "16:00:00", min = "09:00:00")
    public TimeProperty horaFinPautada;

    /**
     * date property field
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    @TemporalDataGen(max = "1M", min = "-1M")
    public TimestampProperty fechaHoraFinPautada;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, heading = Kleenean.FALSE)
    public EstadoReservacion reservacion;

    /**
     * timestamp property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE)
    public TimestampProperty fechaHoraEstadoReservacion;

    /**
     * many-to-one entity reference property field
     */
    @OwnerProperty
    @SegmentProperty
    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.TRUE, table = Kleenean.TRUE, heading = Kleenean.FALSE)
    public Usuario organizador;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, heading = Kleenean.FALSE)
    public EstadoReunion estado;

    /**
     * timestamp property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE)
    public TimestampProperty fechaHoraEstadoReunion;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 1, maxRound = 0)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE)
    public EscalaEncuesta pregunta1;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 1, maxRound = 0)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE)
    public EscalaEncuesta pregunta2;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 1, maxRound = 0)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE)
    public EscalaEncuesta pregunta3;

    /**
     * string property field
     */
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE)
    public StringProperty pregunta4;

    /**
     * timestamp property field
     */
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE)
    public TimestampProperty fechaHoraCorreoEncuestaReunion;

    /**
     * timestamp property field
     */
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE)
    public TimestampProperty fechaHoraEncuestaReunion;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setInitialValue(id.toCharString());
        codigo.setDefaultValue(id.toCharString());
        asistentes.setMinValue(1);
        asistentes.setMaxValue(100);
        margen.setMinValue(30);
        margen.setMaxValue(300);
        fechaHoraInicioDeseada.setInitialValue(fechaInicioDeseada);
        fechaHoraInicioDeseada.setDefaultValue(fechaInicioDeseada);
        fechaFinDeseada.setInitialValue(fechaInicioDeseada);
        fechaFinDeseada.setDefaultValue(fechaInicioDeseada);
        fechaHoraFinDeseada.setInitialValue(fechaFinDeseada);
        fechaHoraFinDeseada.setDefaultValue(fechaFinDeseada);
        margen.setDefaultDescription("margen en minutos para el inicio y fin de la reunión");
        reservacion.setInitialValue(reservacion.PENDIENTE);
        reservacion.setDefaultValue(reservacion.PENDIENTE);
        fechaHoraEstadoReservacion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraEstadoReservacion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        estado.setInitialValue(estado.PENDIENTE);
        estado.setDefaultValue(estado.PENDIENTE);
        fechaHoraEstadoReunion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraEstadoReunion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        horaFinDeseada.setDefaultDescription("Ej: 09:00 PM");
        horaInicioPautada.setDefaultDescription("Ej: 09:00 AM");
        pregunta1.setDefaultLabel("calidad de los equipos");
        pregunta1.setDefaultDescription("¿La calidad de los equipos de la sala fue lo esperado?");
        pregunta2.setDefaultLabel("calidad de los refrigerios");
        pregunta2.setDefaultDescription("¿La calidad de los refrigerios del evento fue lo esperado?");
        pregunta3.setDefaultLabel("calidad del servicio");
        pregunta3.setDefaultDescription("¿Está conforme con el servicio prestado?");
        pregunta4.setDefaultLabel("proceso de reserva");
        pregunta4.setDefaultDescription("Indique su experiencia en general sobre el proceso de reserva de salas");
    }

    Tab tab101, tab102, tab103, tab104;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab101.setDefaultLabel("genaral");
        tab101.newTabField(agenda, organizador, estado, fechaHoraEstadoReunion);
        tab102.setDefaultLabel("requisitos de sala");
        tab102.newTabField(tipoSalaDeseada, asistentes, fechaInicioDeseada, horaInicioDeseada, fechaFinDeseada, horaFinDeseada, margen);
        tab103.setDefaultLabel("reservación de sala");
        tab103.newTabField(sala, fechaInicioPautada, horaInicioPautada, fechaFinPautada, horaFinPautada, reservacion, fechaHoraEstadoReservacion);
        tab104.setDefaultLabel("encuesta");
        tab104.newTabField(pregunta1, pregunta2, pregunta3, pregunta4);
    }

//  Check check101;
    Check check102;

    Segment reservacionPendiente, reservacionSolicitada, reservacionConfirmada, reservacionRechazada, reservacionCancelada;

    Segment conReservacion, sinReservacion;

    Segment reunionPendiente, reunionConvocada, reunionCancelada;

    State pendiente, pendienteSolicitada, pendienteConfirmada, pendienteRechazada, pendienteCancelada;

    State convocadaSolicitada, convocadaConfirmada, convocadaRechazada, convocadaCancelada;

    State cancelada;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
//      check101 = fechaFinDeseada.isGreaterOrEqualTo(fechaInicioDeseada);
//      check101.setDefaultErrorMessage("la fecha de finalización deseada es menor que la de inicio");
//      check102 = horaFinDeseada.isGreaterThan(horaInicioDeseada).or(fechaFinDeseada.isGreaterThan(fechaInicioDeseada));
        check102 = horaFinDeseada.isGreaterThan(horaInicioDeseada);
        check102.setDefaultErrorMessage("la hora de finalización deseada es menor o igual que la de inicio");
        /**/
        reservacionPendiente = reservacion.isEqualTo(reservacion.PENDIENTE);
        reservacionPendiente.setDefaultErrorMessage("la reservación no está pendiente");
        reservacionSolicitada = reservacion.isEqualTo(reservacion.SOLICITADA);
        reservacionSolicitada.setDefaultErrorMessage("la reservación no está solicitada");
        reservacionConfirmada = reservacion.isEqualTo(reservacion.CONFIRMADA);
        reservacionConfirmada.setDefaultErrorMessage("la reservación no está confirmada");
        reservacionRechazada = reservacion.isEqualTo(reservacion.RECHAZADA);
        reservacionRechazada.setDefaultErrorMessage("la reservación no está rechazada");
        reservacionCancelada = reservacion.isEqualTo(reservacion.CANCELADA);
        reservacionCancelada.setDefaultErrorMessage("la reservación no está cancelada");
        /**/
        conReservacion = reservacionSolicitada.or(reservacionConfirmada);
        conReservacion.setDefaultErrorMessage("la reservación no está solicitada ni confirmada");
        sinReservacion = not(conReservacion);
        sinReservacion.setDefaultErrorMessage("la reservación está solicitada o confirmada");
        /**/
        reunionPendiente = estado.isEqualTo(estado.PENDIENTE);
        reunionPendiente.setDefaultErrorMessage("la reunión no está pendiente");
        reunionConvocada = estado.isEqualTo(estado.CONVOCADA);
        reunionConvocada.setDefaultErrorMessage("la reunión no está convocada");
        reunionCancelada = estado.isEqualTo(estado.CANCELADA);
        reunionCancelada.setDefaultErrorMessage("la reunión no está cancelada");
        /**/
        pendiente = reunionPendiente.and(reservacionPendiente);
        pendienteSolicitada = reunionPendiente.and(reservacionSolicitada);
        pendienteConfirmada = reunionPendiente.and(reservacionConfirmada);
        pendienteRechazada = reunionPendiente.and(reservacionRechazada);
        pendienteCancelada = reunionPendiente.and(reservacionCancelada);
        /**/
        convocadaSolicitada = reunionConvocada.and(reservacionSolicitada);
        convocadaConfirmada = reunionConvocada.and(reservacionConfirmada);
        convocadaRechazada = reunionConvocada.and(reservacionRechazada);
        convocadaCancelada = reunionConvocada.and(reservacionCancelada);
        /**/
        cancelada = reunionCancelada.and(reservacionCancelada);
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        setDeleteFilter(reunionPendiente.and(sinReservacion));
    }

    View v1;

    @Override
    protected void settleViews() {
        super.settleViews();
        ViewField vf;
        v1.setDefaultLabel("Reuniones por Organizador y Tipo de Sala");
        vf = v1.newControlField(organizador.codigoUsuario);
        vf.setDefaultLabel("organizador");
        vf = v1.newHeadingField(organizador.nombreUsuario, organizador.codigoUsuario);
        vf.setDefaultLabel("nombre del organizador");
        vf = v1.newControlField(tipoSalaDeseada.codigo);
        vf.setDefaultLabel("tipo de sala");
        vf = v1.newDetailField(fechaInicioDeseada, SortOption.ASC);
        vf.setDefaultLabel("fecha");
        vf.setPixels(96);
        vf = v1.newDetailField(horaInicioDeseada, SortOption.ASC);
        vf.setDefaultLabel("desde");
        vf.setPixels(64);
        vf = v1.newDetailField(horaFinDeseada, SortOption.ASC);
        vf.setDefaultLabel("hasta");
        vf.setPixels(64);
        v1.newDetailField(asunto);
        v1.newDetailField(codigo, ViewFieldAggregation.COUNT);
        v1.newDetailField(asistentes, ViewFieldAggregation.AVERAGE_DEVIATION_MINIMUM_MAXIMUM);
    }

    protected EmitirInformePorOrganizadorTipoSala emitirInformePorOrganizadorTipoSala;

    @ReportOperationClass(viewField = "v1", detailRowsLimit = 2000, summaryRowsLimit = 3000, chartRowsLimit = 1000)
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class EmitirInformePorOrganizadorTipoSala extends ReportOperation {

        /**
         * entity reference parameter field
         */
        @ParameterField
        protected Usuario organizador;

        /**
         * entity reference parameter field
         */
        @ParameterField
        protected TipoSala tipoSalaDeseada;

        /**
         * date parameter field
         */
        @ParameterField(linkedField = "fechaInicioDeseada", operator = StandardRelationalOp.GTEQ)
        protected DateParameter desdeFecha;

        /**
         * date parameter field
         */
        @ParameterField(linkedField = "fechaInicioDeseada", operator = StandardRelationalOp.LTEQ)
        protected DateParameter hastaFecha;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultLabel("emitir informe por organizador y tipo de sala");
        }

    }

    protected ExportarArchivoPorOrganizadorTipoSala exportarArchivoPorOrganizadorTipoSala;

    @ExportOperationClass(viewField = "v1", detailRowsLimit = 4000, summaryRowsLimit = 5000)
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class ExportarArchivoPorOrganizadorTipoSala extends ExportOperation {

        /**
         * entity reference parameter field
         */
        @ParameterField
        protected Usuario organizador;

        /**
         * entity reference parameter field
         */
        @ParameterField
        protected TipoSala tipoSalaDeseada;

        /**
         * date parameter field
         */
        @ParameterField(linkedField = "fechaInicioDeseada", operator = StandardRelationalOp.GTEQ)
        protected DateParameter desdeFecha;

        /**
         * date parameter field
         */
        @ParameterField(linkedField = "fechaInicioDeseada", operator = StandardRelationalOp.LTEQ)
        protected DateParameter hastaFecha;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultLabel("exportar archivo por organizador y tipo de sala");
        }

    }

    protected ActualizarListaSalasDisponibles actualizarListaSalasDisponibles;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class ActualizarListaSalasDisponibles extends ProcessOperation {

        /**
         * instance reference parameter field
         */
        @InstanceReference
        protected Reunion reunion;

        Check check1;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check1 = reunion.estado.isNotEqualTo(estado.CANCELADA);
            check1.setDefaultErrorMessage("la reunión está cancelada");
        }

    }

    protected SolicitarReservacionSala solicitarReservacionSala;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    @OperationClass(access = OperationAccess.PRIVATE)
    public class SolicitarReservacionSala extends ProcessOperation {

        /**
         * instance reference parameter field
         */
        @InstanceReference
        protected Reunion reunion;

        /**
         * entity reference parameter field
         */
        @ParameterField(linkedField = "sala")
        protected Sala sala;

        /**
         * date parameter field
         */
        @ParameterField(linkedField = "fechaInicioPautada")
        protected DateParameter fechaInicio;

        /**
         * time parameter field
         */
        @ParameterField(linkedField = "horaInicioPautada")
        protected TimeParameter horaInicio;

        /**
         * date parameter field
         */
        @ParameterField(linkedField = "fechaFinPautada")
        protected DateParameter fechaFin;

        /**
         * time parameter field
         */
        @ParameterField(linkedField = "horaFinPautada")
        protected TimeParameter horaFin;

        @Override

        protected void settleParameters() {
            super.settleParameters();
            reunion.reservacion.setCurrentValue(reservacion.SOLICITADA);
            reunion.fechaHoraEstadoReservacion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

    }

    protected ConfirmarReservacionSala confirmarReservacionSala;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    @OperationClass(access = OperationAccess.PRIVATE)
    public class ConfirmarReservacionSala extends ProcessOperation {

        /**
         * instance reference parameter field
         */
        @InstanceReference
        protected Reunion reunion;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            reunion.reservacion.setCurrentValue(reservacion.CONFIRMADA);
            reunion.fechaHoraEstadoReservacion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

    }

    protected RechazarReservacionSala rechazarReservacionSala;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    @OperationClass(access = OperationAccess.PRIVATE)
    public class RechazarReservacionSala extends ProcessOperation {

        /**
         * instance reference parameter field
         */
        @InstanceReference
        protected Reunion reunion;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            reunion.reservacion.setCurrentValue(reservacion.RECHAZADA);
            reunion.fechaHoraEstadoReservacion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

    }

    protected CancelarReservacionSala cancelarReservacionSala;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class CancelarReservacionSala extends ProcessOperation {

        /**
         * instance reference parameter field
         */
        @InstanceReference
        protected Reunion reunion;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            reunion.reservacion.setCurrentValue(reservacion.CANCELADA);
            reunion.fechaHoraEstadoReservacion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

    }

    protected Convocar convocar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class Convocar extends ProcessOperation {

        /**
         * instance reference parameter field
         */
        @InstanceReference
        protected Reunion reunion;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            reunion.estado.setCurrentValue(estado.CONVOCADA);
            reunion.fechaHoraEstadoReunion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

    }

    protected Cancelar cancelar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class Cancelar extends ProcessOperation {

        /**
         * instance reference parameter field
         */
        @InstanceReference
        protected Reunion reunion;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            reunion.estado.setCurrentValue(estado.CANCELADA);
            reunion.fechaHoraEstadoReunion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

    }

    protected LLenarEncuesta llenarEncuesta;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class LLenarEncuesta extends ProcessOperation {

        /**
         * instance reference parameter field
         */
        @InstanceReference
        protected Reunion reunion;

        @ParameterField(linkedField = "pregunta1", required = Kleenean.TRUE)
        protected EscalaEncuesta pregunta1;

        @ParameterField(linkedField = "pregunta2", required = Kleenean.TRUE)
        protected EscalaEncuesta pregunta2;

        @ParameterField(linkedField = "pregunta3", required = Kleenean.TRUE)
        protected EscalaEncuesta pregunta3;

        @ParameterField(linkedField = "pregunta4")
        protected StringParameter pregunta4;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            pregunta1.setDefaultLabel("calidad de los equipos");
            pregunta1.setDefaultDescription("¿La calidad de los equipos de la sala fue lo esperado?");
            pregunta2.setDefaultLabel("calidad de los refrigerios");
            pregunta2.setDefaultDescription("¿La calidad de los refrigerios del evento fue lo esperado?");
            pregunta3.setDefaultLabel("calidad del servicio");
            pregunta3.setDefaultDescription("¿Está conforme con el servicio prestado?");
            pregunta4.setDefaultLabel("proceso de reserva");
            pregunta4.setDefaultDescription("Indique su experiencia en general sobre el proceso de reserva de salas");
            reunion.fechaHoraEncuestaReunion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

    }

    @Override
    protected void settleOperations() {
        super.settleOperations();
        insert.addTransition(null, pendiente);
        /**/
//      convocar.addTransition(pendienteSolicitada, convocadaSolicitada);
        convocar.addTransition(pendienteConfirmada, convocadaConfirmada);
//      convocar.addTransition(pendienteRechazada, convocadaRechazada);
//      convocar.addTransition(pendienteCancelada, convocadaCancelada);
        /**/
        convocar.addTriggerOn(pendienteConfirmada);
        /**/
        cancelar.addTransition(convocadaSolicitada, cancelada);
        cancelar.addTransition(convocadaConfirmada, cancelada);
        cancelar.addTransition(convocadaRechazada, cancelada);
        cancelar.addTransition(convocadaCancelada, cancelada);
        /**/
        solicitarReservacionSala.addTransition(pendiente, pendienteSolicitada);
        solicitarReservacionSala.addTransition(pendienteRechazada, pendienteSolicitada);
        solicitarReservacionSala.addTransition(pendienteCancelada, pendienteSolicitada);
        solicitarReservacionSala.addTransition(convocadaRechazada, convocadaSolicitada);
        solicitarReservacionSala.addTransition(convocadaCancelada, convocadaSolicitada);
        /**/
        solicitarReservacionSala.addTriggerOn(pendiente);
        solicitarReservacionSala.addTriggerOn(pendienteRechazada);
        solicitarReservacionSala.addTriggerOn(pendienteCancelada);
        solicitarReservacionSala.addTriggerOn(convocadaRechazada);
        solicitarReservacionSala.addTriggerOn(convocadaCancelada);
        /**/
        confirmarReservacionSala.addTransition(pendienteSolicitada, pendienteConfirmada);
        confirmarReservacionSala.addTransition(convocadaSolicitada, convocadaConfirmada);
        /**/
        rechazarReservacionSala.addTransition(pendienteSolicitada, pendienteRechazada);
        rechazarReservacionSala.addTransition(convocadaSolicitada, convocadaRechazada);
        /**/
        cancelarReservacionSala.addTransition(pendienteSolicitada, pendienteCancelada);
        cancelarReservacionSala.addTransition(pendienteConfirmada, pendienteCancelada);
        cancelarReservacionSala.addTransition(convocadaSolicitada, convocadaCancelada);
        cancelarReservacionSala.addTransition(convocadaConfirmada, convocadaCancelada);
    }

}
