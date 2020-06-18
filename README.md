[![Project status](https://badgen.net/badge/project%20status/stable%20%26%20actively%20maintaned?color=green)](https://github.com/homecentr/docker-prometheus-alertmanager/graphs/commit-activity) [![](https://badgen.net/github/label-issues/homecentr/docker-prometheus-alertmanager/bug?label=open%20bugs&color=green)](https://github.com/homecentr/docker-prometheus-alertmanager/labels/bug) [![](https://badgen.net/github/release/homecentr/docker-prometheus-alertmanager)](https://hub.docker.com/repository/docker/homecentr/prometheus-alertmanager)
[![](https://badgen.net/docker/pulls/homecentr/prometheus-alertmanager)](https://hub.docker.com/repository/docker/homecentr/prometheus-alertmanager) 
[![](https://badgen.net/docker/size/homecentr/prometheus-alertmanager)](https://hub.docker.com/repository/docker/homecentr/prometheus-alertmanager)

![CI/CD on master](https://github.com/homecentr/docker-prometheus-alertmanager/workflows/CI/CD%20on%20master/badge.svg)
![Regular Docker image vulnerability scan](https://github.com/homecentr/docker-prometheus-alertmanager/workflows/Regular%20Docker%20image%20vulnerability%20scan/badge.svg)


# HomeCentr - prometheus-alertmanager

This Docker image is a repack of the original [Prometheus Alertmanager](https://github.com/prometheus/alertmanager) with the usual Homecentr bells and whistles.


## Usage

```yml
version: "3.7"
services:
  prometheus-alertmanager:
    build: .
    image: homecentr/prometheus-alertmanager:local
    environment:
      ALERTMANAGER_ARGS: "--log.level=debug"
    volumes:
      - ./example:/config
    ports:
      - 9093:9093
    restart: unless-stopped
```

## Environment variables

| Name | Default value | Description |
|------|---------------|-------------|
| PUID | 7077 | UID of the user prometheus-alertmanager should be running as. |
| PGID | 7077 | GID of the user prometheus-alertmanager should be running as. |
| ALERTMANAGER_ARGS | | Additional command line arguments passed to the Alertmanager executable. Do not specify the `--config.file` and `--storage.path` these are already set by the image. |

## Exposed ports

| Port | Protocol | Description |
|------|------|-------------|
| 9093 | TCP | Alert manager API |

## Volumes

| Container path | Description |
|------------|---------------|
| /config | [Alertmanager Configuration](https://github.com/prometheus/alertmanager#example). The configuration should be in `/config/alertmanager.yml`. |
| /alertmanager | Alertmanager state in case it is restarted. Make sure the PUID/PGID grants the process the rights to write into this directory. |

## Security
The container is regularly scanned for vulnerabilities and updated. Further info can be found in the [Security tab](https://github.com/homecentr/docker-prometheus-alertmanager/security).

### Container user
The container supports privilege drop. Even though the container starts as root, it will use the permissions only to perform the initial set up. The prometheus-alertmanager process runs as UID/GID provided in the PUID and PGID environment variables.

:warning: Do not change the container user directly using the `user` Docker compose property or using the `--user` argument. This would break the privilege drop logic.