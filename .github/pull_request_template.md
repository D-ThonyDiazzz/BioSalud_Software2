## Servicio / agregado
<!-- ej: msvc-pacientes -->

## Que se hizo
- [ ] Paquetes `domain/model`, `application/port`, `application/service`, `infrastructure/...`
- [ ] Modelo de dominio sin anotaciones JPA
- [ ] Puerto(s) creados como interfaces
- [ ] Adaptador implementa el puerto y convierte dominio <-> entidad
- [ ] El service depende del puerto, no de JPA ni de Feign
- [ ] Probado en Postman: mismas rutas y mismo JSON que antes

## Notas para quien revisa
