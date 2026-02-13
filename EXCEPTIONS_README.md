# Sistema de Exceptions e Handler

## 📋 Estrutura

A aplicação possui um sistema robusto de tratamento de exceções com as seguintes classes:

### Exceptions Personalizadas

1. **UrlNaoEncontradaException** - Lançada quando uma URL encurtada não existe no banco
   - Status HTTP: **404 (Not Found)**
   - Uso: Quando o usuário tenta acessar uma URL que não está cadastrada

2. **UrlExpiradaException** - Lançada quando a URL encurtada passou da data de validade
   - Status HTTP: **410 (Gone)**
   - Uso: Quando a URL existe mas já expirou

3. **UrlInvalidaException** - Lançada quando a URL fornecida é inválida
   - Status HTTP: **400 (Bad Request)**
   - Uso: Validação de entrada (formato inválido, URL malformada, etc)

4. **ErroInternoException** - Lançada para erros internos do servidor
   - Status HTTP: **500 (Internal Server Error)**
   - Uso: Problemas ao gerar código, salvar no banco, etc

### Handler Global

**GlobalExceptionHandler** - Captura todas as exceptions e retorna respostas padronizadas

## 📄 Formato de Resposta de Erro

```json
{
    "status": 404,
    "mensagem": "URL encurtada não encontrada: ABC123",
    "timestamp": "13/02/2026 19:45:30",
    "path": "/ABC123"
}
```

## 🔧 Como Usar no Service

```java
// Exemplo 1: URL não encontrada
Url url = urlRepository.findByUrlEncurtada(codigo)
    .orElseThrow(() -> new UrlNaoEncontradaException(codigo));

// Exemplo 2: URL expirada
if (url.getDataExpiracao().isBefore(LocalDateTime.now())) {
    throw new UrlExpiradaException(codigo);
}

// Exemplo 3: URL inválida
if (urlOriginal == null || urlOriginal.isEmpty()) {
    throw new UrlInvalidaException("A URL não pode ser vazia");
}

// Exemplo 4: Erro interno
try {
    // código que pode falhar
} catch (Exception e) {
    throw new ErroInternoException("Erro ao processar URL", e);
}
```

## ✅ Benefícios

- **Respostas padronizadas**: Todas as respostas de erro seguem o mesmo formato
- **Códigos HTTP corretos**: Cada tipo de erro retorna o status HTTP apropriado
- **Mensagens claras**: O usuário sabe exatamente o que aconteceu
- **Fácil manutenção**: Adicionar novos tipos de erro é simples
- **Profissional**: Segue as melhores práticas de desenvolvimento REST API
