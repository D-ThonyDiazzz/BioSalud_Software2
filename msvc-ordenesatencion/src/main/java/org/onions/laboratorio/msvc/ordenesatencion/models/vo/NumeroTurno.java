package org.onions.laboratorio.msvc.ordenesatencion.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

//RN: la numeracion de turnos es consecutiva cada dia y se reinicia cada manana.
@Embeddable
public class NumeroTurno {

    @Column(name = "turno_fecha")
    private LocalDate fechaTurno;

    @Column(name = "turno_numero")
    private Integer numero;

    public NumeroTurno() {}

    public NumeroTurno(LocalDate fechaTurno, Integer numero) {
        this.fechaTurno = fechaTurno;
        this.numero = numero;
    }

    public LocalDate getFechaTurno() { return fechaTurno; }
    public void setFechaTurno(LocalDate fechaTurno) { this.fechaTurno = fechaTurno; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
}
