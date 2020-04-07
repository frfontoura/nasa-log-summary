# Nasa Log Summary

Back-end desenvolvido para o processamento dos dados dos logs da NASA Kennedy Space Center.

### Requisitos

* Java (desenvolvido utilizando a versão 11.0.6)
* Maven (desenvolvido utilizando a versão 3.6.3)

### Instalação

1- Executar o comando para baixar as dependencias e criar o executável:

```
mvn package
```

2- Iniciar o servidor:

```
mvn spring-boot:run
```

### Testando a aplicação

Para testar a aplicação há duas maneiras, inicializando o servidor front-end ou fazendo uma chamada direta a API:

```
http://localhost:8080/logs/process [GET]
```


_**OBS: Verifique se o servidor front-end esta inicializado no host http://localhost:3000, caso seja outro atualizar a propriedade security.allowed-origins no arquivo application.properties**_