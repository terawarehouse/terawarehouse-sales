[![patreon](https://c5.patreon.com/external/logo/become_a_patron_button.png)](https://www.patreon.com/bePatron?u=12280211)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# Catalog Module

Is a microservice that is a part of the Catalog and Sellout Management System.

## Features

 - It's a REST API that exposes the endpoints for managing sales resources. 
 - It is powered by and use Spring Cloud to fetch the configuration from a remote server and join a client server registration.
 - It saves the data in an SQL database.

## Dockerized

```
docker build -t czetsuya/terawarehouse-sales.
docker run -d -p 8761:8761 czetsuya/terawarehouse-sales
```

Sales should be accessible at http://localhost:8003

## Repositories

 - https://github.com/terawarehouse
 
## Authors

 * **Edward P. Legaspi** - *Java Architect* - [czetsuya](https://github.com/czetsuya)
