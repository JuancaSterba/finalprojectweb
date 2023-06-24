package com.itec1.finalprojectweb.entity;

public enum OrderStatus {

    PENDING, // Pendiente
    PROCESSING, // En proceso
    COMPLETED, // Completada
    CANCELLED, // Cancelada
    WAITING_FOR_DISPATCH, // Esperando despacho
    IN_TRANSIT, // En tránsito
    DELIVERED, // Entregada
    RETURNED; // Devuelta

}
