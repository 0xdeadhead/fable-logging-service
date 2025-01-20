# fable-logging-service
![architecture-diagram](https://raw.githubusercontent.com/0xdeadhead/fable-logging-service/a35be11fe872d5da294f3e6a3fa6c8b07f47dc10/fable-logging.drawio.svg)

# Installation and usage instructions

1. clone and navigate to this git repo's root directory
2. Modify aws client secret,client access key and bucket name(if required) in [properties](https://github.com/0xdeadhead/fable-logging-service/blob/main/src/main/resources/application.properties)
3. execute `docker build -t "fable-log-service" .`
4. Run `docker compose up`
5. Make a post request to ***localhost:8080/api/log/*** with json body


# Todos
1. Remove AWS secret from application.properties
2. Externalize buffer/batch wait time and storage condition
