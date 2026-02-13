# Desafio Técnico - Encurtador de URLs

## Sobre o Desafio

Implementar um serviço que permite encurtar URLs longas para torná-las mais compactas e fáceis de compartilhar.

## Exemplo de Uso

### Requisição para Encurtar URL

O seu serviço recebe uma chamada para encurtar uma URL:

```http
[POST] {{host}}/shorten-url

{
    "url": "https://backendbrasil.com.br"
}
```

### Resposta com URL Encurtada

E retorna um JSON com a URL encurtada:

```http
HTTP/1.1 200 OK

{
    "url": "https://xxx.com/DXB6V"
}
```

## Requisitos

1. **Entrada**: O encurtador de URLs recebe uma URL longa como parâmetro inicial.

2. **Formato do Encurtamento**: 
   - Mínimo de 5 caracteres
   - Máximo de 10 caracteres
   - Apenas letras e números são permitidos

3. **Persistência**: A URL encurtada será salva no banco de dados com um prazo de validade (você pode escolher a duração desejada).

4. **Redirecionamento**: 
   - Ao receber uma chamada para a URL encurtada (ex: `https://xxx.com/DXB6V`), você deve fazer o redirecionamento para a URL original salva no banco de dados.
   - Caso a URL não seja encontrada no banco, retorne o código de status HTTP **404 (Not Found)**.

## Funcionalidades Esperadas

- ✅ Endpoint para encurtar URLs
- ✅ Geração de código curto único (5-10 caracteres alfanuméricos)
- ✅ Armazenamento em banco de dados com expiração
- ✅ Endpoint para redirecionamento da URL curta para a URL original
- ✅ Tratamento de URLs expiradas ou não encontradas (HTTP 404)
