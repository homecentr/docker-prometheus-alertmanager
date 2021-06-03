FROM prom/alertmanager:v0.22.2 as original

FROM homecentr/base:2.4.3-alpine

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