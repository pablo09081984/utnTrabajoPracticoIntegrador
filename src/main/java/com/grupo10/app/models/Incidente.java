package com.grupo10.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "incidente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descripciones")
    private String descripcion;
    @Column(name = "tipos_de_problemas")
    private String tipoDeProblema;

    @Column(name = "servicios_reportados")
    @Enumerated(EnumType.STRING)
    private List<String> servicioReportado;

    @Column(name = "estados")
    private Estado estado;

    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
    private Tecnico tecnico;

    @Column(name = "tiempo_resolucion")
    private LocalTime tiempoResolucion;

    @Column(name = "fecha_posible_resolucion")
    private LocalDate fechaPosibleResolucion;

    public Incidente(Long id, String descripcion, String tipoDeProblema, List<String> servicioReportado,
                     Cliente cliente, Tecnico tecnico, LocalTime tiempoResolucion,
                     LocalDate fechaPosibleResolucion) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoDeProblema = tipoDeProblema;
        this.servicioReportado = servicioReportado;
        this.estado = Estado.INGRESADO;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.tiempoResolucion = tiempoResolucion;
        this.fechaPosibleResolucion = fechaPosibleResolucion;
        this.enviarAvisoCliente();
        this.enviarAvisoTecnico();
    }

    private String enviarAvisoCliente() {
        return "Sr/a " + this.cliente + " el incidente ha sido ingresado con éxito. " +
          "La fecha posible de resolución es " + this.fechaPosibleResolucion;
    }

    private String enviarAvisoTecnico() {
       return this.tecnico + " tienes un nuevo incidente para resolver.";
    }

    public void estaSolucionado(Boolean isResuelto){
        if(isResuelto){
            System.out.println("Código para enviar el mail al cliente");
            this.estado = Estado.RESUELTO;
        }
    }

}
