FROM prom/alertmanager:v0.21.0 as original

FROM homecentr/base:3.2.0-alpine

ENV ALERTMANAGER_ARGS=""

COPY --from=original /bin/amtool /bin/amtool
COPY --from=original /bin/alertmanager /bin/alertmanager

COPY ./fs/ /

RUN mkdir /config && \
    mkdir /alertmanager && \
    chmod 777 /alertmanager

EXPOSE 9093

VOLUME /config
VOLUME /alertmanager