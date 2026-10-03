package org.onions.laboratorio.msvc.ordenesatencion.domain.vo;

import java.time.LocalDate;

public class NumeroTurno {

    private LocalDate fechaTurno;
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