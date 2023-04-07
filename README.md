# Introdução
REST API construida com Spring boot que permite o registro de usuários e seus endereços. 

Utiliza a base H2. 

# API endpoints
Aqui estão listados todos os endpoints da API.

## POST /user/new
Cria um novo usuário. 

**Parâmetros**
| Nome  | Obrigatório | Type | Descrição |
| ------------- | ------------- | ------------- | ------------- |
| name | Sim  | String  | Nome do usuário  |
| birthDate | Sim  | yyyy-MM-dd | Data de nascimento do usuário  |

**Requisição**
```
{
    "name": "Lucas", 
    "birthDate": "2023-01-31"
}
```

**Resposta**
```
{
    "id": 3,
    "name": "Lucas",
    "birthDate": "2023-01-31",
    "addresses": [],
    "mainAddress": null,
    "_links": {
        "self": {
            "href": "http://localhost:8081/user/3"
        }
    }
}
```

## POST /user/create-address/{id}
Cria um endereço para o usuário. 

**Parâmetros**
| Nome  | Obrigatório | Type | Descrição |
| ------------- | ------------- | ------------- | ------------- |
| street | Sim  | String  | Logradouro  |
| zipCode | Sim  | String | CEP |
| number | Sim  | String | Número do endereço |
| city | Sim  | String | Cidade |
| id | Sim  | Path | Id do usuário que irá receber o registro |

**Requisição**
```
{
    "street": "Rua A", 
    "zipCode": "06445520", 
    "city": "Barueri", 
    "number": "14A"
}
```

**Resposta**
```
{
    "id": 1,
    "name": "Lucas",
    "birthDate": "2023-01-01",
    "addresses": [
        {
            "id": 1,
            "street": "Rua A",
            "zipCode": "06445550",
            "number": "14A",
            "city": "Barueri"
        }
    ],
    "mainAddress": null
}
```

## PUT /user/attach-main-address/{userId}/{addressId}
Anexa ao usuário um endereço principal. 

**Parâmetros**
| Nome  | Obrigatório | Type | Descrição |
| ------------- | ------------- | ------------- | ------------- |
| userId | Sim  | Path  | Id do usuário |
| addressId | Sim  | Path | Id do endereço |

**Exemplo após requisição**
```
{
    "id": 1,
    "name": "Lucas",
    "birthDate": "2023-01-01",
    "addresses": [
        {
            "id": 3,
            "street": "Rua A",
            "zipCode": "06445520",
            "number": "14A",
            "city": "Barueri"
        },
        {
            "id": 5,
            "street": "Rua D",
            "zipCode": "06445520",
            "number": "14A",
            "city": "Barueri"
        }
    ],
    "mainAddress": {
        "id": 5,
        "street": "Rua D",
        "zipCode": "06445520",
        "number": "14A",
        "city": "Barueri"
    }
}
```

## PUT /user/detach-main-address/{id}
Remove o endereço principal do usuário. 

**Parâmetros**
| Nome  | Obrigatório | Type | Descrição |
| ------------- | ------------- | ------------- | ------------- |
| userId | Sim  | Path  | Id do usuário |

## DELETE /user/delete-address/{userId}/{addressId}
Deleta um endereço da lista de endereços do usuário. 

**Parâmetros**
| Nome  | Obrigatório | Type | Descrição |
| ------------- | ------------- | ------------- | ------------- |
| userId | Sim  | Path  | Id do usuário |
| addressId | Sim  | Path | Id do endereço |

## PUT /user/edit
Edita um usuário. 

**Parâmetros**
| Nome  | Obrigatório | Type | Descrição |
| ------------- | ------------- | ------------- | ------------- |
| id | Sim  | String  | Id do usuário |
| name | Sim  | String  | Nome do usuário  |
| birthDate | Sim  | yyyy-MM-dd | Data de nascimento do usuário  |


**Requisição**
```
{
    "id": 1,
    "name": "Lucas",
    "birthDate": "2022-01-01"
} 
```

**Resposta**
```
{
    "id": 1,
    "name": "Lucas",
    "birthDate": "2022-01-01",
    "addresses": [
        {
            "id": 3,
            "street": "Rua A",
            "zipCode": "06445520",
            "number": "14A",
            "city": "Barueri"
        },
        {
            "id": 5,
            "street": "Rua D",
            "zipCode": "06445520",
            "number": "14A",
            "city": "Barueri"
        }
    ],
    "mainAddress": {
        "id": 5,
        "street": "Rua D",
        "zipCode": "06445520",
        "number": "14A",
        "city": "Barueri"
    }
}
```

## GET /user/{id}
Lista o usuário e seus endereços. 

**Parâmetros**
| Nome  | Obrigatório | Type | Descrição |
| ------------- | ------------- | ------------- | ------------- |
| id | Sim  | Path  | Id do usuário |

**Resposta**
```
{
    "id": 1,
    "name": "Lucas",
    "birthDate": "2022-01-01",
    "addresses": [
        {
            "id": 3,
            "street": "Rua A",
            "zipCode": "06445520",
            "number": "14A",
            "city": "Barueri"
        },
        {
            "id": 5,
            "street": "Rua D",
            "zipCode": "06445520",
            "number": "14A",
            "city": "Barueri"
        }
    ],
    "mainAddress": {
        "id": 5,
        "street": "Rua D",
        "zipCode": "06445520",
        "number": "14A",
        "city": "Barueri"
    }
}
```

## GET /user/get-all/{pageNumber}/{pageSize}
Listagem de todos os usuários. 

**Parâmetros**
| Nome  | Obrigatório | Type | Descrição |
| ------------- | ------------- | ------------- | ------------- |
| pageNumber | Sim  | Path  | O número da página |
| pageSize | Sim  | Path  | O número de registros em cada pagina |
