#!/bin/bash

CONTAINER_NAME="conf-docker-jenkins-1"

CONTAINER_PATH="/var/jenkins_home/workspace/gestion-empresa-pipeline/gestion-empresa/target/site"

HOST_PATH="/var/www/html"

if [ $(docker ps -q -f name=$CONTAINER_NAME) ]; then
    echo "El contenedor $CONTAINER_NAME está en ejecución. Procediendo a copiar reporte jacoco..."
    sudo docker cp $CONTAINER_NAME:$CONTAINER_PATH $HOST_PATH
    echo "¡Copia completada exitosamente!"
else
    echo "El contenedor $CONTAINER_NAME no está en ejecución o no existe."
fi
